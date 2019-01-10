package test.groovy.org.wlrobotics
import main.groovy.org.wlrobotics.ToaRestClient

class testToa_listRegions {

    def main () {
        def toa = new ToaRestClient()
        toa.getRegions().each {r ->
            r.each {k,v  ->
                print "$k:  $v".padRight(20)
            }
        println ""
        }
    }
}