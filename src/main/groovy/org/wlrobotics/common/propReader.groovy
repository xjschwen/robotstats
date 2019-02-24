#!/usr/bin/groovy

import java.nio.file.Paths

class PropReader {

  def defaultLocations (String propFileName){

    def env = System.getenv()

    String [] propfileLocations = [
      Paths.get(propFileName),
      Paths.get(env["HOME"] + "/${propFileName}"),
      Paths.get(env["USERPROFILE"] + "/${propFileName}"),
      Paths.get(env["USERPROFILE"] + "/Documents/${propFileName}"),

      Paths.get(System.getProperty("HOME") + "/${propFileName}"),
      Paths.get(System.getProperty("USERPROFILE") + "/${propFileName}"),
      Paths.get(System.getProperty("USERPROFILE") + "/Documents/${propFileName}"),

    ]
    return propfileLocations
  }

  def props (String [] locations){
    groovy.util.ConfigObject p
    for (fileName in locations) {
      try {
        //println "looking for ${fileName}"
        p = new ConfigSlurper().parse(new File(fileName).toURL())
        println ("FOUND: Propery file ${fileName}")
        break
      }
      catch (FileNotFoundException e) {
        //println "FileNotFound: ${fileName}"
      }
    }
    String locString = "\n"
    if ( p == null ) {
      locations.each {  fileName ->
        locString = locString + "\t FileNotFound: ${fileName}\n"
      }
      throw new FileNotFoundException ("ERROR: Property file not found in any of the listed locations. " + locString)
    }
    return p
  }

}