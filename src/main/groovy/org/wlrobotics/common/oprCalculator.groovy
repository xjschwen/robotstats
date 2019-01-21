
package org.wlrobotics.common
import org.jblas.*

import groovy.json.JsonSlurper

class OprCalculator extends Object {

  def  teamsIndex = [:]
  def matchData = null
  def teamsMatrix = null
  
  int [] scoreMatrix = []

  OprCalculator (def matches) {
      def slurper =new JsonSlurper()
      this.matchData = slurper.parseText(matches)

      this.populateTeamMatrix()
  }

  def populateScoreMatrix (def matches, def column ) {
    this.scoreMatrix = new float [matches.size()]
    
    int idx = 0
    matches.each {m ->
      this.scoreMatrix [idx] = m."${column}" 
      //println ("scoreMatrix[${idx}] =  " + this.scoreMatrix[idx] )
      idx += 1
    }
  }


  def populateTeamMatrix (){

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
   
    // subtract 1 because java indexes start at 0 not 1 
    
    println "matchData.size()  = " + matchData.size()
    println "teamsIndex.size() = " + teamsIndex.size()

    teamsMatrix = new int [matchData.size()] [teamsIndex.size()] 
    
    matchData.eachWithIndex { m, j ->
      //print   ("$j\t" + m."team 1" + ":" + teamsIndex[m."team 1"])
      //print   ("\t"   + m."team 2" + ":" + teamsIndex[m."team 2"])
      //println ("\t"   + m."team 3" + ":" + teamsIndex[m."team 3"])

      teamsMatrix [j] [teamsIndex[m."team 1"]] = 1
      teamsMatrix [j] [teamsIndex[m."team 2"]] = 1
      teamsMatrix [j] [teamsIndex[m."team 3"]] = 1
    }

    //for (int mdx = 0; mdx < matchData.size(); mdx++){
    //  for (int tmx = 0; tmx< teamsIndex.size(); tmx++) {
    //    print (teamsMatrix [mdx][tmx])
    //  }
    //  println ("")
    //}
   
  }
}
