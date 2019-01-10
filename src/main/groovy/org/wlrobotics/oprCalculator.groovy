
package main.groovy.org.wlrobotics
import org.jblas.*
// import groovyx.org.slf4j

class OprCalculator extends Object {

  def teamsIndex = [:]
  def matches = [:]
  int [][] teamMatrix =[][]
  
  int [] scoreMatrix = []

  OprCalculator (def matches, def teams) {
    this.teamMatrix = new int [matches.size()][teams.size()]
    this.scoreMatrix = new int [matches.size]

    this.matches = matches
    populateTeamMatrix(teams, matches)
    populateScoreMatrix(matches, "score")
    
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


  def populateTeamMatrix (def teams, def matches){
    int lcv = 0
    teams.each {t ->
      this.teamsIndex[t.key] = lcv
      lcv += 1
    }

    int i = 0
    matches.each { m ->
      this.teamsIndex.each {k,v ->
        
        if (k in m.teams){ 
          this.teamMatrix[i][v] = 1
          //print ("1")
        }
        else {
          this.teamMatrix[i][v] = 0
          //print ("0")
        }
      }
      //println()
      i += 1
    }
  }
}
