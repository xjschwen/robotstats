package org.wlrobotics.tba

class GetAllEventData extends Object {
    public static void main(String[] args) {
        // args [0] = directory to write the cvs file
        // args [1] = filter for event key to write.
        def tba = new TbaRestClient ()
        def year = args[1].substring(0,4)
        def eventProcessor = new TbaEventProcessor(args[0], tba)
        tba.getEventKeys(year).each {e ->
            if (e.contains (args[1])) {
                println ("fetching ${e}")
                eventProcessor.process(e)
            }
        }
    }
}
