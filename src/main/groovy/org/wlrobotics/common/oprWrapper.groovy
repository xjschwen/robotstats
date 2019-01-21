package org.wlrobotics.common

import org.wlrobotics.common.DataFileSlurper

class OprWrapper extends Object {
    
    
    public static void main(String[] args) {
        println ("We are Wapp'n the OPRs.")

        OprCalculator oprCalc = new OprCalculator (DataFileSlurper.convert(args[0]))
    }


}