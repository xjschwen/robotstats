package main.groovy.org.wlrobotics

import org.wlrobotics.common.*

class ToaMatchDataFactory extends Object {
  String yearKey = null
  String eventKey = null
  ToaMatchDataFactory (String eventKey) {
    this.eventKey = eventKey
    this.yearKey = eventKey[0..3]
  }

  def getMatch (String matchKey) {
    def matchData = null
    if (yearKey == "1819") {
      matchData = new Match1819RoverRuckusData (eventKey, matchKey)
    }
    else {
      matchData = new MatchXXXXData (eventKey, matchKey)
    }
    return matchData
  }
}
