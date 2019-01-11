package main.groovy.org.wlrobotics
import org.wlrobotics.common.*

class Match2018PowerUpData extends BaseMatchData {

  Match2018PowerUpData (String eventKey, String matchKey) {
    this.eventKey = eventKey
    this.matchKey = matchKey
  }

  void add_newColumns () {

  }
  
  void translateMatchData () {
    matchData.each { k,v ->
      if (k.startsWith("autoRobot")) { translate_autoRobot()}
      if (k.startsWith("endgameRobot")) {translate_endgameRobot()}
    }
  }


  void translate_autoRobot() {
    def fields = ["autoRobot1", "autoRobot2", "autoRobot3"]
    fields.each {f ->
      if (matchData."$f" == "None") {
        matchData."$f" = 0
      }
      else if (matchData."$f" == "AutoRun"){
        matchData."$f" = 5
      }
    }
  }

  void translate_endgameRobot () {
    def fields = ["endgameRobot1", "endgameRobot2", "endgameRobot3"]
    fields.each {f ->
      if (matchData."$f" == "None") {
        matchData."$f" = 0
      }
      else if (matchData."$f" == "Levitate"){
        matchData."$f" = 30
      }
      else if (matchData."$f" == "Climbing"){
        matchData."$f" = 30
      }
      else if (matchData."$f" == "Parking"){
        matchData."$f" = 5
      }
    }
  }

}
