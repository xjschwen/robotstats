import groovyx.net.http.HTTPBuilder


import groovyx.net.http.RESTClient
import groovyx.net.http.URIBuilder
import groovyx.net.http.ContentType


class TbaRestClient extends RESTClient {
  def TbaRestClient() {
    String base = "https://www.thebluealliance.com/api/v3/"
    String apiKey = "BgCq88QxszmGQPOlKllZEBE8CEMaTO4fbNJl8QvGBl1Vrd3OHTAuC74hGHaDE3B4"
    def headers  = [:]
    headers.put("X-TBA-Auth-Key", apiKey)
    //headers.put("X-Application-Origin", "8492-OPR-App")
    this.defaultURI = new URIBuilder(base)
    this.setHeaders(headers)
    this.contentType = ContentType.JSON

  }

  //overload the get to only return the json elements
  def get (String path) {
    super.get(path:path)  {response, json ->
      return json
    }
  }

  def getDistricts (){
    return (this.get("districts/2018"))
  }

  def getEvent (String eventKey) {
    return (this.get("event/${eventKey}"))
  }

  def getEvents (String district_key){
    return (this.get("district/${district_key}/events"))
  }

  def getMatchKeys(String event_key){
    return (this.get("event/${event_key}/matches/keys"))
  }

  def getMatchData(String match_key){
    return (this.get("match/${match_key}"))
  }

  def getMatchDataAll (String eventKey){
    this.get ("event/${eventKey}/matches")
  }

}
