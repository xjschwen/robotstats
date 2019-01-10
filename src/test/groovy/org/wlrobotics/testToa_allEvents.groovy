package test.groovy.org.wlrobotics

import main.groovy.org.wlrobotics.ToaRestClient
import main.groovy.org.wlrobotics.ToaEventProcessor
import main.groovy.org.wlrobotics.ToaGlobalSettings

import test.groovy.org.wlrobotics.toaTestSettings

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class testToa_allEvents {

    void junitTest () {
        def eventProcessor = new ToaEventProcessor(toaTestSettings.dataDir.toString())
        def toa = new ToaRestClient ()
        toa.getEvents().each {e ->
            if (e.event_key.contains ("FIM")) {
                println ("fetching ${e.event_key}")
                eventProcessor.process(e.event_key)
                sleep(ToaGlobalSettings.globalSleepTime)
            }
        }
    }
}