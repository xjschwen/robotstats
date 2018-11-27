abstract class BaseMatchData extends Object {

  String eventKey = null
  String matchKey = null
  def teams = [:]
  def matchData = null
  int score = 0
  String color = "purple"

  def setMatchData (teams = [:], score, color, matchData){
    this.teams = teams
    this.score = score
    this.color = color
    this.matchData = matchData
  }

  String getHeader () {
    String retValue = "MatchKey"
    retValue += "," + "Color"
    retValue += "," + "Team 1"
    retValue += "," + "Team 2"
    retValue += "," + "Team 3"
    retValue += "," + "Score"
    matchData.each {k,v ->
      retValue += "," + k
    }
    return retValue
  }

  String toString() {
    String retValue = matchKey
    retValue += "," + color
    retValue += "," + teams[0]// [3..-1]
    retValue += "," + teams[1]// [3..-1]
    if (teams[2] != null) {
      retValue += "," + teams[2] // [3..-1]
    } else {
      retValue += "," + ""
    }

    retValue += "," + score
    translateMatchData()
    matchData.each {k,v ->
      retValue += "," + v
    }
    return retValue
  }

  abstract void translateMatchData ()
}
