
package org.wlrobotics.common
import org.jblas.Solve
import org.jblas.DoubleMatrix
//import org.jblas.*

import groovy.json.JsonSlurper

class OprCalculator extends Object {

  def teamsData = [:]
  def teamsIndex = [:]
  def matchData = null
  DoubleMatrix teamsMatrix = null
  DoubleMatrix scoreMatrix = null

  def fields = ["Score",
    	 "Opponents Score",
       //"adjustPoints",
       "autoOwnershipPoints",
       "autoPoints",
       //"autoQuestRankingPoint",
       //"autoRobot1",
       //"autoRobot2",
       //"autoRobot3",
       "autoRunPoints",
       "autoScaleOwnershipSec",
       "autoSwitchOwnershipSec",
       "endgamePoints",
       //"endgameRobot1",
       //"endgameRobot2",
       //"endgameRobot3",
       "foulCount",
       "foulPoints",
       "teleopOwnershipPoints",
       "teleopPoints",
       "teleopScaleBoostSec",
       "teleopScaleForceSec",
       "teleopScaleOwnershipSec",
       "teleopSwitchBoostSec",
       "teleopSwitchForceSec",
       "teleopSwitchOwnershipSec",
       "totalPoints",
       "vaultBoostPlayed",
       "vaultBoostTotal",
       "vaultForcePlayed",
       "vaultForceTotal",
       "vaultLevitatePlayed",
       "vaultLevitateTotal",
       "vaultPoints"]

  OprCalculator (def matches) {
    def slurper =new JsonSlurper()
    matchData = slurper.parseText(matches)
    populateTeamMatrix()

    fields.each { f -> 
      println ("calculating $f")
      populateScoreMatrix (f.toLowerCase())
      def oprs = solve()
      teamsIndex.eachWithIndex { k, v, idx -> 
        teamsData[k].addOPRData(f, oprs.get(idx))
      }
    }
  }


  def populateScoreMatrix (String column ) {
    // takes a field from the matchData and loads it into 
    // scoreMatrix as a way to hand off to jblas for the math
    scoreMatrix = DoubleMatrix.zeros(matchData.size())
    matchData.eachWithIndex { m, idx ->
    scoreMatrix.put(idx, 0, m."${column}".toInteger())
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
      teamsData[k] = new SimpleTeamData(k)
      i++
    }
   
    println "matchData.size()  = " + matchData.size()
    println "teamsIndex.size() = " + teamsIndex.size()

    teamsMatrix = DoubleMatrix.zeros(matchData.size(),teamsIndex.size())

    matchData.eachWithIndex { m, j ->
      teamsMatrix.put(j, teamsIndex[m."team 1"], 1)
      teamsMatrix.put(j, teamsIndex[m."team 2"], 1)
      teamsMatrix.put(j, teamsIndex[m."team 3"], 1)
    }
  }

  def solve () {
    return (Solve.solveLeastSquares (teamsMatrix, scoreMatrix))
  }

}
