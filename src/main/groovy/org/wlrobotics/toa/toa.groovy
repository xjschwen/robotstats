package org.wlrobotics.toa

import java.util.Date
import groovyx.net.http.HTTPBuilder
import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
import groovyx.net.http.URIBuilder
import groovyx.net.http.ContentType


class ToaRestClient extends RESTClient {

  def lastCalls_queue = [] as Queue
  
  // The Orange Alliance limits people to 30 calls per minute... 
  // the values below limit things to 30 calls in 61 seconds...
  final long TOACall_limit = 30
  final long TOACall_limit_Sec = 61000
  
  def ToaRestClient() {
    String base = "https://theorangealliance.org/api/"
    def pr = new PropReader () 
    def props = pr.props (pr.defaultLocations("robotstats.groovy"))
    String apiKey = props.robotstats.toa.apiKey

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
    int qSize = lastCalls_queue.size()
    int i = 0

    if (lastCalls_queue.size() > 0 ){
      if (currTime > (lastCalls_queue.peek() + TOACall_limit_Sec)){
        lastCalls_queue.remove()
      }
    }

    return (lastCalls_queue.size() < TOACall_limit)
  }

  void waitCallLimit () {
    def d = new Date ()
    long currTime = d.getTime()
    int loopCounter = 0
    while (! check_TOACalls(currTime)){
      loopCounter ++
      if (loopCounter > 9){
          println "INFO: Waiting on TOA Call Limit.  ${d.toString()}"
          loopCounter = 0
      }
      sleep(1000)
      d = new Date ()
      currTime = d.getTime()
    }
    lastCalls_queue.add (currTime)
  }

  //overload the get to only return the json elements
  def get (String path) {

    waitCallLimit ()
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
