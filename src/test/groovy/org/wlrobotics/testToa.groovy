package test.groovy.org.wlrobotics

import main.groovy.org.wlrobotics.ToaRestClient
import main.groovy.org.wlrobotics.ToaEventProcessor
import main.groovy.org.wlrobotics.ToaGlobalSettings

import test.groovy.org.wlrobotics.toaTestSettings

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class testToa {
    
    @Test
    void getRegions (){
        println ("\ngetRegions\n")
        sleep(toaTestSettings.globalToaSleepTime)
        def toa = new ToaRestClient()
        toa.getRegions().each {r ->
            r.each {k,v  ->
                print "$k:  $v".padRight(20)
            }
        println ""
        }
    }

    @Test
    void getSeasons (){
        println ("\ngetSeasons\n")
        sleep(toaTestSettings.globalToaSleepTime)
        def toa = new ToaRestClient()
        toa.getSeasons().each {s ->
            s.each {k,v  ->
                print ("$k:  $v".padRight(34))
            }
        println ""
        }
    }

    @Test
    void getTeams (){
        println ("\ngetTeams\n")
        sleep(toaTestSettings.globalToaSleepTime)
        def toa = new ToaRestClient()
        toa.getTeams("1819-FIM-Q4").each {t ->
            println ("${t.team_number}".padLeft(6) + ":\t" + t.team.team_name_short)
        }
    }
 
    @Test 
    void getEvents(){
        println ("\ngetEvents\n")
        sleep(toaTestSettings.globalToaSleepTime)
        def toa = new ToaRestClient ()
        toa.getEvents().each {e ->
            if (e.event_key.contains ("FIM")) {
                println ("${e.event_key}")
            }
        }
    }


    @Test    
    void getEvent(){
        println ("\ngetEvent\n")
        sleep(toaTestSettings.globalToaSleepTime)     
        def toa = new ToaRestClient ()
        def e = (toa.getEvent(toaTestSettings.testEventKey))
        println ("event_key $e.event_key")
        println ("season_key $e.season_key")
        println ("region_key $e.region_key")
        println ("event_name $e.event_name")

    }


    @Test
    void getMatches (){
        println ("\ngetMatches\n")
        sleep(toaTestSettings.globalToaSleepTime)
        def toa = new ToaRestClient ()
        toa.getMatches(toaTestSettings.testEventKey).each {m ->
            println ("${m.match_key}".padRight(20) + 
                    "\tblue:" + "${m.blue_score}".padRight(3) + 
                    "\tred:" + "${m.red_score}".padRight(3))
        }
    }

    @Test
    void  getMatchesDetails(){
        println ("\ngetMatchesDetails\n")
        sleep(toaTestSettings.globalToaSleepTime)     
        def toa = new ToaRestClient ()
        def matchesDetails = toa.getMatchesDetails(toaTestSettings.testEventKey)

        matchesDetails.each {m ->
            print ("match_detail_key: ${m.match_detail_key}")
            print ("\tred_auto_land: ${m.red_auto_land}")
            print ("\tblue_auto_land: ${m.blue_auto_land}\n")
        }

    }
}