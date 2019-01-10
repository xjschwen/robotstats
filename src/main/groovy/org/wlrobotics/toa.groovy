package main.groovy.org.wlrobotics

import groovyx.net.http.HTTPBuilder
import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
import groovyx.net.http.URIBuilder
import groovyx.net.http.ContentType


class ToaRestClient extends RESTClient {
  def ToaRestClient() {
    String base = "https://theorangealliance.org/api/"
    String apiKey = "G+tJm5ehGpprwrq262bUdeXEyx7JBtfDoU5L8xX941S6SAXywfY+iQ=="
    def headers  = [:]
    JsonSlurper slurper = new JsonSlurper()
    headers.put("X-TOA-Key", apiKey)
    headers.put("X-Application-Origin", "http://www.wlrobotics.org/")
    headers.put ("Content-Type", "application/json")
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

  def getRegions (){
    this.get("regions")
  }

  def getSeasons (){
    this.get("seasons")
  }

  def getTeams (String eventKey){
    return this.get("event/${eventKey}/teams")
  }

  def getEvents(){
    return this.get("event")
  }

  def getEvent( String eventKey){
    return this.get("event/${eventKey}")
  }
  def getMatches(String eventKey){
    return this.get("event/${eventKey}/matches")
  }

  def getMatchesDetails(String eventKey){
    return this.get("event/${eventKey}/matches/details")
  }
}
