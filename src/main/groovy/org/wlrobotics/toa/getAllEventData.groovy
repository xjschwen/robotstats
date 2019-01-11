package org.wlrobotics.toa
 
class GetAllEventData extends Object {
    public static void main(String[] args) {
        // args [0] = directory to write the cvs file
        // args [1] = filter for event key to write.
        def eventProcessor = new ToaEventProcessor(args[0])
        def toa = new ToaRestClient ()
        toa.getEvents().each {e ->
            if (e.event_key.contains (args[1])) {
                println ("fetching ${e.event_key}")
                eventProcessor.process(e.event_key)
                sleep(8000)
            }
        } 
    }
}