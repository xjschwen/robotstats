
import org.wlrobotics.ToaRestClient
import org.wlrobotics.ToaEventProcessor



class testToa_event {

    def main () {


        def eventProcessor = new ToaEventProcessor(buildDir.toString())
        eventProcessor.process("1819-FIM-Q3")

    }
}