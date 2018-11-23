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

  String toString (){
      String retValue = matches[0].getHeader() + "\n"
      matches.each { m ->
        retValue +=  m.toString() + "\n"
      }
      return retValue
  }

}
