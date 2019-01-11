package org.wlrobotics.toa
 
class GetRegions extends Object {
    public static void main(String[] args) {
        def toa = new ToaRestClient()
        toa.getRegions().each {r ->
            r.each {k,v  ->
                print "$k:  $v".padRight(20)
            }
            println ""
        }
    }
}