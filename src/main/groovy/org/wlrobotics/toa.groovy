package main.groovy.org.wlrobotics

import java.util.Date
import groovyx.net.http.HTTPBuilder
import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
import groovyx.net.http.URIBuilder
import groovyx.net.http.ContentType


class ToaRestClient extends RESTClient {

  static def lastCalls_queue = [] as Queue
  
  // The Orange Alliance limits people to 30 calls per minute... 
  // the values below limit things to 30 calls in 61 seconds...
  final int TOACall_limit = 30
  final int TOACall_limit_mSec = 61000
  
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

  boolean check_TOACalls(long currTime) {
    lastCalls_queue.each {t ->
      if (currTime > (t + TOACall_limit_mSec)){
        lastCalls_queue.remove()
      }
    }
    return (lastCalls_queue.size() < TOACall_limit)
  }

  void waitCallLimit (long currTime) {
    while (! check_TOACalls(currTime)){
      println "INFO: Waiting on TOA Call Limit to be less than ${TOACall_limit}"
      sleep(1000)
    }
  }


  //overload the get to only return the json elements
  def get (String path) {
    def d = new Date()
    def currTime = d.getTime()
    waitCallLimit (currTime)
    lastCalls_queue.add (currTime)
    // There is still a chance that we will exceed the TOA Rate Limit if the program
    // is run or testest 2 times in the same minute.
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
