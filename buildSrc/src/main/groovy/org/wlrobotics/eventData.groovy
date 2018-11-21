class EventData extends Object {

  String eventKey = null
  def columns = []
  def matches = []
  EventData (String key) {
    this.eventKey = key
  }
  def addMatch (Object match) {
    matches.add (match)
  }

  def setColumns (cols){
    columns = [this.eventKey, "matchKey", "color", "score"]
    column.addAll(cols)
  }

  void printThem (){
      String header = ""
      columns.each { col ->
        header = header + "," + col
      }
      println header
      matches.each { m ->
        println (m.eventKey + "," + m.matchKey + "," + m.color + "," + m.score + "," + m.teams)
      }
  }

}
