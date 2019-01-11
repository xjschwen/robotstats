package org.wlrobotics.toa

import org.wlrobotics.toa.ToaRestClient


import org.wlrobotics.toa.ToaTestSettings

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class testToa {
    def toa = new ToaRestClient()
    @Test
    void getRegions (){
        println ("\ngetRegions\n")
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
        toa.getTeams("1819-FIM-Q4").each {t ->
            println ("${t.team_number}".padLeft(6) + ":\t" + t.team.team_name_short)
        }
    }
 
    @Test 
    void getEvents(){
        println ("\ngetEvents\n")
        toa.getEvents().each {e ->
            if (e.event_key.contains ("FIM")) {
                println ("${e.event_key}")
            }
        }
    }


    @Test    
    void getEvent(){
        println ("\ngetEvent\n")
        def e = (toa.getEvent(ToaTestSettings.testEventKey))
        println ("event_key $e.event_key")
        println ("season_key $e.season_key")
        println ("region_key $e.region_key")
        println ("event_name $e.event_name")

    }


    @Test
    void getMatches (){
        println ("\ngetMatches\n")
        toa.getMatches(ToaTestSettings.testEventKey).each {m ->
            println ("${m.match_key}".padRight(20) + 
                    "\tblue:" + "${m.blue_score}".padRight(3) + 
                    "\tred:" + "${m.red_score}".padRight(3))
        }
    }

    @Test
    void  getMatchesDetails(){
        println ("\ngetMatchesDetails\n")
        def matchesDetails = toa.getMatchesDetails(ToaTestSettings.testEventKey)

        matchesDetails.each {m ->
            print ("match_detail_key: ${m.match_detail_key}")
            print ("\tred_auto_land: ${m.red_auto_land}")
            print ("\tblue_auto_land: ${m.blue_auto_land}\n")
        }

    }

    void testRateLimit () {
        for (int i = 0; i < toa.TOACall_limit + 2; i++ ) {
            toa.getSeasons()
        }
    }

}