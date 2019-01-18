package org.wlrobotics.tba
import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail
import org.wlrobotics.tba.TbaRestClient
import org.wlrobotics.common.DataFileSlurper
import org.wlrobotics.tba.TbaEventProcessor

class TestDataFileSlurper {


    
    @Test
    void slurp () {        
        // args [0] = directory to write the cvs file
        // args [1] = filter for event key to write.
        def tba = new TbaRestClient ()
        TbaTestSettings.dataDir.mkdirs()
        def eventProcessor = new TbaEventProcessor(TbaTestSettings.dataDir.toString(), tba)
        eventProcessor.process(TbaTestSettings.testEventKey)

        def slurper = new DataFileSlurper()
        slurper.convert (TbaTestSettings.dataDir.toString() + "/${TbaTestSettings.testFileName}")
    }
}