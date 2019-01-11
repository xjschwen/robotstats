package org.wlrobotics.toa
 
class GetTOASeasons extends Object {
    public static void main(String[] args) {
        def toa = new ToaRestClient()
        toa.getSeasons().each {s ->
            s.each {k,v  ->
                print ("$k:  $v".padRight(34))
            }
            println ""
        }
    }
}