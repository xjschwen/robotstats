package test.groovy.org.wlrobotics
import main.groovy.org.wlrobotics.ToaRestClient
import main.groovy.org.wlrobotics.ToaEventProcessor



class testToa_event {

    def main () {
        def eventProcessor = new ToaEventProcessor(buildDir.toString())
        eventProcessor.process("1819-FIM-Q3")
    }
}