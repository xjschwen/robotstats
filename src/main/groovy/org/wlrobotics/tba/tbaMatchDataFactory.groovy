package org.wlrobotics.tba

import org.wlrobotics.common.MatchXXXXData

class TbaMatchDataFactory extends Object {
  String yearKey = null
  String eventKey = null
  TbaMatchDataFactory (String eventKey) {
    this.eventKey = eventKey
    this.yearKey = eventKey[0..3]

  }

  def getMatch (String matchKey) {
    def matchData = null
    if (yearKey == "2018") {
      matchData = new Match2018PowerUpData (eventKey, matchKey)
    }
    else if (yearKey == "2017") {
      matchData = new Match2017SteamWorksData (eventKey, matchKey)
    }

    else {
      matchData = new MatchXXXXData (eventKey, matchKey)
    }

    return matchData
  }
}
