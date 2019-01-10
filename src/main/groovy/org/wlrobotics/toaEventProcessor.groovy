import groovy.json.JsonSlurper

class ToaEventProcessor extends Object {

  String buildDir = null
  def matchesRaw = null
  def matchesDetailsRaw = null
  def eventRaw = null

  enum Alliance {
    RED,
    BLUE
  }

  ToaEventProcessor (String outDir ) {
    buildDir = outDir

  }

  def process(String eventKey) {

    def toa = new ToaRestClient()
    def event =  new EventData (eventKey)
    def matchFactory = new ToaMatchDataFactory(event.eventKey)
    event.teams = toa.getTeams(event.eventKey)
    //event.oprCalc = new OprCalculator(event.teams)
    this.eventRaw = toa.getEvent(event.eventKey)
    this.matchesRaw = toa.getMatches(event.eventKey)
    this.matchesDetailsRaw =toa.getMatchesDetails(event.eventKey)
    event.city = this.eventRaw.city.toString()[1..-2].replace(" ","")
    this.matchesRaw.each {mr ->
      def matchDataRed = matchFactory.getMatch(mr.match_key)
      def matchDataBlue = matchFactory.getMatch(mr.match_key)
      matchDataBlue.teams =  getTeams(mr.participants, "B")
      matchDataBlue.color = "blue"
      matchDataBlue.score = mr.blue_score
      matchDataBlue.opponents_score = mr.red_score
      //println matchesDetailRaw
      matchDataBlue.matchData = getMatchDetails(mr.match_key, "B")

      event.addMatch(matchDataBlue)


      matchDataRed.teams =  getTeams(mr.participants, "R")
      matchDataRed.color = "red"
      matchDataRed.score = mr.red_score
      matchDataRed.opponents_score = mr.blue_score
      matchDataRed.matchData = getMatchDetails(mr.match_key, "R")
      event.addMatch(matchDataRed)
    }
    event.write(buildDir)
    this.matchesRaw = null
    this.matchesDetailsRaw = null

  }


  def getTeams (participants, String colorCode) {

    def teams = []
    def regex = ["R": /-R[1-3]{1}/, "B": /-B[1-3]{1}/]

    participants.each {p ->
      if (p.match_participant_key =~ regex[colorCode]) {
        teams.add (p.team_key)
        // println "ColorCode: ${colorCode}  ${p.team_key}"
      }
    }
    return teams
  }

  def getMatchDetails (String matchKey, String colorCode){

    def retValues = [:]
    def regex = ["R": /red_/, "B": /blue_/]
    this.matchesDetailsRaw.each {mdr ->
      if (mdr.match_key == matchKey) {
        mdr.each { k,v ->
          if (k =~ regex[colorCode]) {
            retValues[k.replace(regex[colorCode],"")] = v
          }
        }
      }
    }
    return retValues
  }

}
