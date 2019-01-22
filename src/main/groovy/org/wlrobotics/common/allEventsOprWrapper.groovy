package org.wlrobotics.common

import org.wlrobotics.common.DataFileSlurper
import groovy.io.FileType

class AllEventsOprWrapper extends Object {
    static String oprFileString = "_oprs.csv"
    
    public static void main(String[] args) {
        def workingDir = new File (args[0])

        def files2Process = new FileNameByRegexFinder().getFileNames(args[0], args[1])

        files2Process.reverseEach { f ->
            if (! f.contains(oprFileString)) {
                println "Processing ${f}"
                OprCalculator oprCalc = new OprCalculator (DataFileSlurper.convert(f))
                String outFileName = f.replaceAll(".csv", oprFileString)
                writeResults (outFileName, oprCalc)
            }
        }
    }

    static void writeResults (String fName, OprCalculator oprCalcObj){

        File f = new File (fName)
        f.delete()
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