package org.wlrobotics.common

class EventData extends Object {

  String eventKey = null
  def columns = []
  def matches = []
  def teams = [:]
  String city = null
  OprCalculator oprCalc = null

  EventData (String key) {
    this.eventKey = key
  }


  def addMatch (Object match) {
    this.matches.add (match)
  }

  void calculateOPRs(){
    this.oprCalc = new OprCalculator(matches, teams)
    
  }


  String toString (){
    //the toString method will possibly add columns to the data that need to be
    // collected in the header.
    matches.each { m ->
      m.add_newColumns ()
    }
    String retValue = ""
    try {
      retValue = matches[0].getHeader() + "\n"
    }
    catch (java.lang.NullPointerException e){
      return "No Data for ${eventKey}"
    }
    matches.each { m ->
      retValue +=  m.toString() + "\n"
    }
    return retValue
  }

  void write (String writeInThisDir) {
    File f = new File (writeInThisDir, "${this.eventKey}_${this.city}.csv")
    f.write (this.toString())
  }
}
