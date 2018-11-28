class Match1819RoverRuckusData extends BaseMatchData {

  Match1819RoverRuckusData (String eventKey, String matchKey) {
    this.eventKey = eventKey
    this.matchKey = matchKey
  }

  void translateMatchData () {
      //translate_teams()
      translate_auto_land()
      translate_auto_samp()
      translate_auto_claim()
      translate_auto_park()
      translate_tele_gold()
      translate_tele_silver()
      translate_tele_depot()
      translate_end_Latch()
      translate_end_in()
      translate_end_comp()
      translate_min_pen()
      translate_maj_pen()
  }

  void translate_teams() {

  }
  void translate_auto_land() {
    matchData.auto_land = matchData.auto_land * 30
  }

  void translate_auto_samp() {
    matchData.auto_samp = matchData.auto_samp * 25
  }
  void translate_auto_claim() {
    matchData.auto_claim = matchData.auto_claim * 15
  }

  void translate_auto_park() {
    matchData.auto_park = matchData.auto_park * 10
  }

  void translate_tele_gold() {
    matchData.tele_gold = matchData.tele_gold * 5
  }

  void translate_tele_silver() {
    matchData.tele_silver = matchData.tele_silver * 5
  }
  void translate_tele_depot() {
    matchData.tele_depot = matchData.tele_depot * 2
  }

  void translate_end_Latch() {
    matchData.end_latch = matchData.end_latch * 50
  }

  void translate_end_in() {
    matchData.end_in = matchData.end_in * 15
  }
  void translate_end_comp() {
    matchData.end_comp = matchData.end_comp * 25
  }

  void translate_min_pen() {
    matchData.min_pen = matchData.min_pen * 10
  }
  void translate_maj_pen() {
    matchData.maj_pen = matchData.maj_pen * 40
  }
}
