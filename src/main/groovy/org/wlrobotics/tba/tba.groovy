package org.wlrobotics.tba


import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import groovyx.net.http.URIBuilder
import groovyx.net.http.ContentType


class TbaRestClient extends RESTClient {
  def TbaRestClient() {
    
   
    String base = "https://www.thebluealliance.com/api/v3/"
    def pr = new PropReader () 
    def props = pr.props (pr.defaultLocations("robotstats.groovy"))
     

    String apiKey = props.robotstats.tba.apiKey

    def headers  = [:]
    headers.put("X-TBA-Auth-Key", apiKey)
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

  def getEventKeys (String year){
    return (this.get("events/${year}/keys"))
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

  def getEventTeamsSimple (String event_key){
    this.get ("event/${event_key}/teams/simple")
  }

  def getEventSimple(String event_key){
    this.get ("event/${event_key}/simple")
  }

}
