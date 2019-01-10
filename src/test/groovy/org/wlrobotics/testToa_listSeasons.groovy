package test.groovy.org.wlrobotics
import main.groovy.org.wlrobotics.ToaRestClient

class testToaListSeasons {
    
    def main () {
        def toa = new ToaRestClient()
        toa.getSeasons().each {s ->
            s.each {k,v  ->
                print ("$k:  $v".padRight(34))
            }
        println ""
        }
    }
}

