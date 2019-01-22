package org.wlrobotics.common

import org.wlrobotics.common.DataFileSlurper

class OprWrapper extends Object {
    
    
    public static void main(String[] args) {
        OprCalculator oprCalc = new OprCalculator (DataFileSlurper.convert(args[0]))
        String outFileName = args[0].replaceAll(".csv", "_oprs.csv")
        writeResults (outFileName, oprCalc)
    }

    static void writeResults (String fName, OprCalculator oprCalcObj){

        File f = new File (fName)
        f.text = ""
        String fields = ""
        String data = ""

        oprCalcObj.teamsData.each { k, v  ->
            data = "${k},"
            def oprData = oprCalcObj.teamsData[k].getOPRData()
            // collect the fields that we are tracking into a fields string
            if (fields == "") {
                fields = "Team,"
                oprData.each {key,value ->
                    fields += "$key,"
                }
                f << fields + "\n"
            }    
            oprData.each {key,value ->
                data +=  String.format ("%.2f,", value)
            }
            f << data + "\n"
        }
    }
}