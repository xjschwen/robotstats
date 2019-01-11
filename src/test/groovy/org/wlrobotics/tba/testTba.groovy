package test.groovy.org.wlrobotics

import main.groovy.org.wlrobotics.*
import main.groovy.org.wlrobotics.common.*

import test.groovy.org.wlrobotics.tba.tbaTestSettings

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class testTba {
    def tba = new TbaRestClient()

    @Test
    void getDistricts (){
        println ("\ngetDistricts\n")
        tba.getDistricts().each { d->
            print ("abbreviation: ${d.abbreviation}")
            //print ("\tdisplay_name:\t ${d.display_name}")
            print ("\tkey: ${d.key}")
            print ("\tyear ${d.year}\n")
        }
    }

    @Test
    void getEvent () {
        println ("\ngetEvent\n")
        def e = tba.getEvent(tbaTestSettings.testEventKey)   

        print ("key:\t${e.key}")    
        print ("name:\t${e.name}")
        print ("event_code:\t${e.event_code}\n")
        

    }

    @Test
    void getEvents (){
        // return (this.get("district/${district_key}/events"))
        println ("\ngetEvents\n")
        def events = tba.getEvents(tbaTestSettings.testDistrictKey)   
        events.each {e -> 
            
            print("key: ${e.key}\t")
            print("name: ${e.name}\n")

        }
    }

    @Test
    void getMatchKeys(){
        // return (this.get("event/${event_key}/matches/keys"))
        println ("\ngetMatchKeys\n")

        def matchKeys = tba.getMatchKeys(tbaTestSettings.testEventKey)   
        matchKeys.each { mk ->
            println (mk)
        }
    }

  
    @Test
    void getMatchData(){
        // return (this.get("match/${match_key}"))
        println ("\ngetMatchData\n")
        def md = tba.getMatchData(tbaTestSettings.testMatchKey)
        def blue = md.alliances.blue
        def red = md.alliances.red
        print ("${blue.score}\t")
        blue.team_keys.each {t ->
            print "${t}\t"
        }
        println ()       
        
        print ("${red.score}\t")
        red.team_keys.each {t ->
            print "${t}\t"
        }
        println ()
    }


    @Test
    void getMatchDataAll (){
        //this.get ("event/${eventKey}/matches")
        println ("\ngetMatchDataAll\n")
        def md = tba.getMatchDataAll(tbaTestSettings.testEventKey)
        md.each {m ->
            def blue = m.alliances.blue
            def red = m.alliances.red

            println "Match: ${m.key}"
            print ("\tBlue:\t${blue.score}\t")
            blue.team_keys.each {t ->
                print "${t}\t"
            }
            println ()       
        
            print ("\tRed:\t${red.score}\t")
            red.team_keys.each {t ->
                print "${t}\t"
            }
            println ()
        }

    }
    @Test
    void  getEventTeamsSimple (){
        //this.get ("event/${eventKey}/teams/simple")
        println ("\ngetMatchDataAll\n")
        def teams = tba.getEventTeamsSimple(tbaTestSettings.testEventKey)
        println "Teams at ${tbaTestSettings.testEventKey}"
        teams.each { t ->
            println "\t${t.team_number}:\t${t.nickname}"
        }
    }

}