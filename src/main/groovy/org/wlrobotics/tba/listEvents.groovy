package org.wlrobotics.tba

class ListEvents extends Object {
    public static void main(String[] args) {
        // args [0] = directory to write the cvs file
        // args [1] = filter for event key to write.
        def tba = new TbaRestClient ()
        def year = args[0]
        tba.getEventKeys(year).each {e ->
            if (e.contains (args[1])) {
                println ("${e}")
            }
        }
    }
}