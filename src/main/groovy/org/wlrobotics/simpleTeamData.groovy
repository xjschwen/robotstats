
class SimpleTeamData extends Object {

String teamKey = ""
String jSon = ""
def oprData = []

SimpleTeamData (def jSon ){
    teamKey = jSon.key
    this.jSon = jSon

}    



}