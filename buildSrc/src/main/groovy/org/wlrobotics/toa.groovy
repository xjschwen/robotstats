import groovyx.net.http.HTTPBuilder


import groovyx.net.http.RESTClient
import groovyx.net.http.URIBuilder
import groovyx.net.http.ContentType


class ToaRestClient extends RESTClient {
  def ToaRestClient() {
    String base = "https://theorangealliance.org/api"
    String apiKey = "G+tJm5ehGpprwrq262bUdeXEyx7JBtfDoU5L8xX941S6SAXywfY+iQ=="

    def headers  = [:]
    headers.put("X-TOA-Key", apiKey)
    headers.put("X-Application-Origin", "8492-OPR-App")
    headers.put ("ContentType", "application/json")
    this.defaultURI = new URIBuilder(base)
    this.setHeaders(headers)
    //this.contentType = ContentType.JSON
  }

  //overload the get to only return the json elements
  def get (String path) {
    super.get(path:path)  {response, json ->
      return json
    }
  }

  def getMatchKeys(String event_key){
    return (this.get("event/${event_key}/matches/keys"))
  }

  def getMatchData(String match_key){
    return (this.get("match/${match_key}"))
  }
}
