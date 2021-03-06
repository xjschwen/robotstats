package org.wlrobotics.tba
import org.wlrobotics.common.BaseMatchData

class Match2017SteamWorksData extends BaseMatchData {

  Match2017SteamWorksData (String eventKey, String matchKey) {
    this.eventKey = eventKey
    this.matchKey = matchKey
  }

  void translateMatchData () {
    matchData.each { k,v ->
      if (k.startsWith("autoRobot")) { translate_autoRobot()}
    }
  }

  void add_newColumns () {

  }
  
  void translate_autoRobot() {
    def fields = ["robot1Auto",	"robot2Auto",	"robot3Auto"]
    fields.each {f ->

      if (matchData."$f" == "None") {
        matchData."$f" = 0
      }
      else if (matchData."$f" == "Mobility"){
        matchData."$f" = 5
      }
    }
  }
}
