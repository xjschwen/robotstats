package org.wlrobotics.tba

import org.wlrobotics.common.EventData
import org.wlrobotics.common.SimpleTeamData

class TbaEventProcessor extends Object {

  String buildDir = null
  def eventRaw = null
  TbaRestClient tba = null

  TbaEventProcessor (String outDir, TbaRestClient t ) {
    buildDir = outDir
    this.tba = t
  }

  def process(String eventKey) {


    def event =  new EventData (eventKey)
    def matchFactory = new TbaMatchDataFactory(event.eventKey)
    eventRaw = this.tba.getEvent(event.eventKey)

    this.tba.getEventTeamsSimple (event.eventKey).each { t ->
      event.teams[t.key] = new SimpleTeamData (t)
    }


    event.city = eventRaw.city
    event.city = this.eventRaw.city.toString().replace(" ","")
    tba.getMatchDataAll(event.eventKey).each { matchDataRaw ->
      // get the match data from the internet
      //def matchDataRaw = tba.getMatchData(mk)
      // println (matchDataRaw.key)
      if (matchDataRaw.key =~ /_qm[0-9]{1,3}/) {

        def matchDataBlue = matchFactory.getMatch (matchDataRaw.key)
        matchDataBlue.teams = matchDataRaw?.alliances?.blue?.team_keys
        matchDataBlue.score = matchDataRaw?.alliances?.blue?.score
        matchDataBlue.color = "blue"
        matchDataBlue.matchData = matchDataRaw?.score_breakdown?.blue
        matchDataBlue.opponents_score = matchDataRaw?.alliances?.red?.score
        if (matchDataBlue?.score > -1) {
          event.addMatch(matchDataBlue)
        }

        def matchDataRed = matchFactory.getMatch(matchDataRaw.key)
        matchDataRed.teams = matchDataRaw?.alliances?.red?.team_keys
        matchDataRed.score = matchDataRaw?.alliances?.red?.score
        matchDataRed.opponents_score = matchDataRaw?.alliances?.blue?.score
        matchDataRed.color = "red"
        matchDataRed.matchData = matchDataRaw?.score_breakdown?.red
        if (matchDataRed?.score > -1) {
          event.addMatch(matchDataRed)
        }
      }
    }
    event.write(buildDir)
  }
}
