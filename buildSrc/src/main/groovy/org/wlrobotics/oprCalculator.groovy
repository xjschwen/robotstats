
class OprCalculator extends Object {

  def teams = [:]
  def matches = [:]
  int [][] teamMatrix =[][]
  int [] scoreMatrix = []

  OprCalculator (teams) {

    int index = 0
    teams.each { t ->
      this.teams["FTC-" + t.team_key] = index
      index += 1
    }
  }

  def addMatch (match) {
    this.matches["${match.matchKey}-${match.color}"] = this.matches.size()

    this.teams.each { k, v ->
      println ("$k=$v")
      match.teams.each { t ->
        println ("t = FTC-${t}")
        if (k == t) {
          this.teamMatrix[this.matches["${match.matchKey}-${match.color}"], this.teams[t]] = 1
        }
        else {
          println ("matches Key " + this.matches["${match.matchKey}-${match.color}"])
          println ("t=FTC-${t} idx=" + this.teams["FTC-${t}"])
          this.teamMatrix[this.matches["${match.matchKey}-${match.color}"], this.teams["FTC-"+t]] = 0
        }
      }
    }
    println ("jeff")
    println (this.teamMatrix)
  }
}
