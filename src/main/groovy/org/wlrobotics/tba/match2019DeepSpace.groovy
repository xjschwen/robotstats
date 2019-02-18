package org.wlrobotics.tba
import org.wlrobotics.common.BaseMatchData

class Match2019DeepSpaceData extends BaseMatchData {

  Match2019DeepSpaceData (String eventKey, String matchKey) {
    this.eventKey = eventKey
    this.matchKey = matchKey
  }

  void add_newColumns () {

  }
  
  void translateMatchData () {
    matchData.each { k,v ->
      if (k.startsWith("bay")) { translate_bay()}
      if (k.startsWith("completeRocketRankingPoint")) {translate_RocketRank()}
      if (k.startsWith("endgameRobot")) {translate_endgameRobot()}
      if (k.startsWith("habDockingRankingPoint")) {translate_habDockingRankingPoint()}
      if (k.startsWith("habLineRobot")) {translate_habLineRobot()}
    }
  }


  void translate_bay() {
    def fields = ["bay1", "bay2", "bay3", "bay4", "bay5", "bay6", "bay7", "bay8"]
    fields.each {f ->
        
      if (matchData."$f" == "PanelAndCargo") {
        matchData."$f" = 5
      }
      else if (matchData."$f" == "Panel"){
        matchData."$f" = 2
      }
      else if (matchData."$f" == "None"){
        matchData."$f" = 0
      }
    }
  }

  void translate_RocketRank () {
    //completeRocketRankingPoint	completedRocketFar	completedRocketNear
    def fields = ["completeRocketRankingPoint", "completedRocketFar", "completedRocketNear"]
    fields.each {f ->
      if (matchData."$f" == false) {
        matchData."$f" = 0
      }
      else if (matchData."$f" == true) {
        matchData."$f" = 30
      }
    }
  }


  void translate_endgameRobot () {
    //endgameRobot1	endgameRobot2	endgameRobot3
    def fields = ["endgameRobot1",	"endgameRobot2",	"endgameRobot3"]
    fields.each {f ->
      if (matchData."$f" == "None") {
        matchData."$f" = 0
      }
      else if (matchData."$f" == "HabLevel1"){
        matchData."$f" = 3
      }
      else if (matchData."$f" == "HabLevel2") {
        matchData."$f" = 6
      }
      else if (matchData."$f" == "HabLevel3") {
        matchData."$f" = 12
      }
    }
  }

  void translate_habDockingRankingPoint () {
    //habDockingRankingPoint
    def fields = ["habDockingRankingPoint"]
    fields.each {f ->
      println (f+"="+matchData."$f")
      
      if (matchData."$f" == false) {
        matchData."$f" = 0
      }
      else if (matchData."$f" == true) {
        matchData."$f" = 30
      }
    }
  }

  void translate_habLineRobot () {
    //habLineRobot1	habLineRobot2	habLineRobot3
    //CrossedHabLineInSandstorm
    //None
    //CrossedHabLineInTeleop

    def fields = ["habLineRobot1", "habLineRobot2", "habLineRobot3"]
    fields.each {f ->
      if (matchData."$f" == "None") {
        matchData."$f" = 0
      }
      else if (matchData."$f" == "CrossedHabLineInSandstorm") {
        matchData."$f" = 5
      }
      else if (matchData."$f" == "CrossedHabLineInTeleop") {
        matchData."$f" = 3
      }
    }
  }


}
