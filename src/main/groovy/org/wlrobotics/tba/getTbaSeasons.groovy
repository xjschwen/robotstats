package org.wlrobotics.tba
 
class GetTBASeasons extends Object {
    public static void main(String[] args) {
        def tba = new TbaRestClient()
        tba.getSeasons().each {s ->
            s.each {k,v  ->
                print ("$k:  $v".padRight(34))
            }
            println ""
        }
    }
}