
package org.wlrobotics.common
import org.jblas.*

import groovy.json.JsonSlurper

class OprCalculator extends Object {

  def  teamsIndex = [:]
  def matchData = null
  def teamsMatrix = null
  
  def scoreMatrix = null

  OprCalculator (def matches) {
      def slurper =new JsonSlurper()
      matchData = slurper.parseText(matches)
      populateTeamMatrix()
      populateScoreMatrix ("score")

  }

  def populateScoreMatrix (String column ) {
    // takes a field from the matchData and loads it into 
    // scoreMatrix as a way to hand off to jblas for the math
    
    scoreMatrix = new int [matchData.size()]
    matchData.eachWithIndex { m, idx ->
      scoreMatrix [idx] = m."${column}".toInteger()
    }
  }

  def populateTeamMatrix (){
    // creates the team matrix which is #number of matches rows
    // and number of teams colums to hand off to jblax to solve
    // the matrix math.
        
    // add the team to the index as value does not matter at this point
    matchData.eachWithIndex { d, i ->
      teamsIndex[d."team 1"] = i
      teamsIndex[d."team 2"] = i
      teamsIndex[d."team 3"] = i
    }

    // now give them unquie values... 0-39 is common
    int i = 0
    teamsIndex.each {k,v ->
      teamsIndex[k] = i
      i++
    }
   
    println "matchData.size()  = " + matchData.size()
    println "teamsIndex.size() = " + teamsIndex.size()

    teamsMatrix = new int [matchData.size()] [teamsIndex.size()] 
    
    matchData.eachWithIndex { m, j ->
      teamsMatrix [j] [teamsIndex[m."team 1"]] = 1
      teamsMatrix [j] [teamsIndex[m."team 2"]] = 1
      teamsMatrix [j] [teamsIndex[m."team 3"]] = 1
    }
  }

}
