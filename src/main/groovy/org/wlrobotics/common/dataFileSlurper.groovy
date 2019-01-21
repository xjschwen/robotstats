package org.wlrobotics.common

//reads a data file on matches and slurps the data up
//into groovy objects
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

// implement this type of solution https://gist.github.com/mpas/58497115057068f15751
class DataFileSlurper extends Object {

    static String convert (String fName) {
        Integer maxNumberOfRows = new Integer(0)
        String separator = ","
        def dataList = []
        def lineCounter = 0
        def headers = []
        def slurper = new JsonSlurper()
        new File(fName).eachLine() { line ->
        // skip the header; header is line 1
            if(maxNumberOfRows == 0 || lineCounter <= maxNumberOfRows) {
                if (lineCounter == 0) {
                    headers = line.split(separator).collect{it.trim()}.collect{it.toLowerCase()}
                } else {
                    def dataItem = [:]
                    def row = line.split(separator).collect{it.trim()}.collect{it.toLowerCase()}

                    headers.eachWithIndex() { header, index ->
                        dataItem.put("${header}", "${row[index]}")
                    }
                    dataList.add(dataItem)
                }
            }
            lineCounter = lineCounter + 1
        }
        return (JsonOutput.toJson(dataList))
    }
}

