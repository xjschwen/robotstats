

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

  String toString (){
      String retValue = matches[0].getHeader() + "\n"
      matches.each { m ->
        retValue +=  m.toString() + "\n"
      }
      return retValue
  }

  void write (String writeInThisDir) {
    File f = new File (writeInThisDir, this.eventKey + ".csv")
    f.write (this.toString())
  }

}
