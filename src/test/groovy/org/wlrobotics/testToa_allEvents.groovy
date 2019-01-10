import org.wlrobotics.ToaRestClient
import org.wlrobotics.ToaEventProcessor
import org.wlrobotics.ToaGlobalSettings


class testToa_allEvents {

    def main () {
        def eventProcessor = new ToaEventProcessor(buildDir.toString())
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