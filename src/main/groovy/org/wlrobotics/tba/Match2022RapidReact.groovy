package org.wlrobotics.tba
import org.wlrobotics.common.BaseMatchData

class Match2022RapidReactData extends BaseMatchData {

  Match2022RapidReactData (String eventKey, String matchKey) {
    this.eventKey = eventKey
    this.matchKey = matchKey
  }

  void add_newColumns () {

  }
  
  void translateMatchData () {

    //def bayRocketRegEx = /bay[1-8]{1}|.*LeftRocket.*|.*RightRocket.*/  
    //def habLevelRegEx = /endgameRobot[1-3]{1}|preMatchLevelRobot1[1-3]{1}/  
    
    matchData.each { k,v ->
      print (k,v)
      //if (k =~bayRocketRegEx) { translate_bay_and_rockets()}
      //if (k =~habLevelRegEx) { translate_habLeveRobot()}
      //if (k.startsWith("completeRocketRankingPoint")) {translate_RocketRank()}
      //if (k.startsWith("habDockingRankingPoint")) {translate_habDockingRankingPoint()}
      //if (k.startsWith("habLineRobot")) {translate_habLineRobot()}
      //if (k.startsWith("preMatchBay")) {translate_preMatchBay()}
    }
  }

  void translate_preMatchBay() {
    def fields = ["preMatchBay1", "preMatchBay2", "preMatchBay3", "preMatchBay4", "preMatchBay5", "preMatchBay6", "preMatchBay7", "preMatchBay8"]
    fields.each {f ->
      if (matchData."$f" == "Cargo") {
        matchData."$f" = 2
      }
      else if (matchData."$f" == "Panel"){
        matchData."$f" = 2
      }
      else if (matchData."$f" == "None"){
        matchData."$f" = 0
      }
    }
  }


  void translate_bay_and_rockets() {
    def fields = ["bay1", "bay2", "bay3", "bay4", "bay5", "bay6", "bay7", "bay8"]
    def lowRockets = ["lowLeftRocketFar",	"lowLeftRocketNear", "lowRightRocketFar",	"lowRightRocketNear"]
    def midRockets = ["midLeftRocketFar",	"midLeftRocketNear", "midRightRocketFar",	"midRightRocketNear"]
    def topRockets = ["topLeftRocketFar",	"topLeftRocketNear", "topRightRocketFar", "topRightRocketNear"]

    fields += lowRockets + midRockets + topRockets

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


  void translate_habLeveRobot () {
    //endgameRobot1	endgameRobot2	endgameRobot3
    def fields = ["endgameRobot1",	"endgameRobot2",	"endgameRobot3"]
    def preMatchLevel = ["preMatchLevelRobot1", "preMatchLevelRobot2", "preMatchLevelRobot3"]
    fields += preMatchLevel

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
