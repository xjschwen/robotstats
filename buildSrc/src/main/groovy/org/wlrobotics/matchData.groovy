class Match2018Data extends Object {

  String eventKey = null
  String matchKey = null
  def teams = [:]
  def matchData = null
  int score = 0
  String color = "purple"

  Match2018Data (String event, String key) {
    this.eventKey = event
    this.matchKey = key
  }

  def setMatchData (teams = [:], score, color, matchData){
    this.teams = teams
    this.score = score
    this.color = color
    this.matchData = matchData
  }
}
