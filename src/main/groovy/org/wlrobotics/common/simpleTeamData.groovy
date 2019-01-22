package org.wlrobotics.common
class SimpleTeamData extends Object {

    String teamKey = ""
    String jSon = ""
    def oprData = [:]

    SimpleTeamData (def jSon ){
        teamKey = jSon.key
        this.jSon = jSon
    }    

    SimpleTeamData (String key) {
        teamKey = key
    }

    def addOPRData (String k, Double v) {
        oprData [k] = v
    }

    def getOPRData (){
        return (oprData)
    }
}