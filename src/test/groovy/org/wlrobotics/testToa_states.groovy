package test.groovy.org.wlrobotics

import main.groovy.org.wlrobotics.ToaRestClient
import main.groovy.org.wlrobotics.ToaEventProcessor


class testToa_states {

    def main (){
        def eventProcessor = new ToaEventProcessor(buildDir.toString())
        def toa = new ToaRestClient ()
        toa.getEvents().each {e ->
            if (e.event_key.contains ("1819-FIM-CMP")) {
                println ("fetching ${e.event_key}")
                eventProcessor.process(e.event_key)
                sleep(ToaGlobalSettings.globalSleepTime)
            }
        }
    }
}