# Project Robot Stats

This project is to compute OPR values for as many data fields as possibe from data that can be downloaded from https://www.thebluealliance.com/ (TBA) or https://theorangealliance.org/ (TOA)


This project uses a combonation of Groovy and Gradle to pull data and compute an OPR on as many columns as possible for each team. 

It is based on events so it is ideal use is for picking alliance partners that might otherwise be overlooked due to having a harder schedule or just unlucky partners.

It ONLY pulls data from qualifications because including elimination matches substantially changes the OPR values because once you are working in Elimination matches the average score goes up quite a bit..


Basic use case will be to pull the data after one day of competion and do focused scouting on the teams with the higher OPR values in the categories that you are instreseted in.   

For 2019 Destination Deep Space there are 54 columns of data.  Some of them are more useful than others.

# Tech Details
This section will cover the more technical details of the program.   If you are going to run it locally then you **MUST** have a property file with your **own** TBA or TOA api keys.


## API key storage
Since the API keys are linked to MY personal accounts on these websites I have choosen to invalidate them and store new copies.   They should not be stored in GitHub or published.   That is why they are read from a groovy property file and stored in the following locations.

The property file default locations are searched in the following order.
    
    1. The current working directory
    2. The HOME environment vairable is common on Linux/Unix systems
    3. The USERPROFILE environment variable is common on Windows systems.
    4. The Documents directory just below each of these locations is also checked for the property file.

The script will use the first property file that it locates... Having the file listed in more than one of these locations is NOT recommened.

The property file name is **robotstats.groovy**

~~~~
    Paths.get(propFileName),
    Paths.get(env["HOME"] + "/${propFileName}"),
    Paths.get(env["USERPROFILE"] + "/${propFileName}"),
    Paths.get(env["USERPROFILE"] + "/Documents/${propFileName}"),
    Paths.get(env["HOME"] + "/Documents/${propFileName}"),
~~~~

The file needs to have this format.   Please put your API keys in the correct fields.

~~~~~groovy
  robotstats {
    tba.apiKey = "<<< The Blue Alliance API key goes here >>>"
    toa.apiKey = "<<< The Oraange Alliance API key goes here >>>"
  }
~~~~~

## jBlas
The actual OPRs are calculated using the Java implementation for linear algabra http://www.jblas.org/.   This is lightning fast and on a modern laptop it has taken longer to download the data than it does to calcuate the solution for the OPRs.


Runnnig the **gradlew test_jblas** task will test if jBlas is correctly installed and operational on your laptop.

Success will give you output simular to what is listed below.

~~~~
 Task :test_jblas
-- org.jblas INFO jblas version is 1.2.4Simple benchmark for jblas


Running sanity benchmarks.

checking vector addition... ok
-- org.jblas CONFIG BLAS native library not found in path. Copying native library from the archive. Consider installing the library somewhere in the path (for Windows: PATH, for Linux: LD_LIBRARY_PATH).
-- org.jblas CONFIG ArchFlavor native library not found in path. Copying native library libjblas_arch_flavor from the archive. Consider installing the library somewhere in the path (for Windows: PATH, for Linux: LD_LIBRARY_PATH).
-- org.jblas CONFIG Loading libjblas_arch_flavor.so from /lib/static/Linux/amd64/, copying to libjblas_arch_flavor.so.
-- org.jblas CONFIG Loading libjblas.so from /lib/static/Linux/amd64/sse3/, copying to libjblas.so.
checking matrix multiplication... ok
checking existence of dsyev...... ok
[-0.210656, -0.640445, -0.451188; -0.509085, -0.116445, 0.796815; -0.807515, 0.407556, -0.398408; 0.210656, 0.640445, -0.052780]
[17.233688; 1.414214; 0.000000]
[-0.470605, 0.782218, 0.408248; -0.571449, 0.082339, -0.816497; -0.672293, -0.617540, 0.408248]
[17.233688; 1.414214; 0.000000]
checking existence of dgesvd...... ok
Checking complex return values... (z = -21.0 + 88.0i)
Check whether we're catching XERBLA errors. If you see something like "** On entry to DGEMM  parameter number  4 had an illegal value", it didn't work!
checking XERBLA... ok
Sanity checks passed.

Each benchmark will take about 5 seconds...

Running benchmark "Java matrix multiplication, double precision".
n = 10   :  2.495 GFLOPS (6237597 iterations in 5.0 seconds)
n = 100  :  3.305 GFLOPS (8264 iterations in 5.0 seconds)
n = 1000 :  3.077 GFLOPS (8 iterations in 5.2 seconds)

Running benchmark "Java matrix multiplication, single precision".
n = 10   :  2.489 GFLOPS (6223152 iterations in 5.0 seconds)
n = 100  :  3.314 GFLOPS (8286 iterations in 5.0 seconds)
n = 1000 :  3.394 GFLOPS (9 iterations in 5.3 seconds)

Running benchmark "native matrix multiplication, double precision".
n = 10   :  2.040 GFLOPS (5100731 iterations in 5.0 seconds)
n = 100  :  7.861 GFLOPS (19652 iterations in 5.0 seconds)
n = 1000 : 13.215 GFLOPS (34 iterations in 5.1 seconds)

Running benchmark "native matrix multiplication, single precision".
n = 10   :  2.201 GFLOPS (5503186 iterations in 5.0 seconds)
n = 100  : 15.597 GFLOPS (38993 iterations in 5.0 seconds)
n = 1000 : 25.066 GFLOPS (63 iterations in 5.0 seconds)
-- org.jblas INFO Deleting /tmp/jblas2578407818034450513/libjblas.so
-- org.jblas INFO Deleting /tmp/jblas2578407818034450513/libjblas_arch_flavor.so
-- org.jblas INFO Deleting /tmp/jblas2578407818034450513

BUILD SUCCESSFUL in 1m 3s
2 actionable tasks: 2 executed

~~~~

## Gradle
The project uses gradle to perform 2 operations.  The first operation is to compile the groovy code.   It is done each time there are changes to the source code.   The second operation for gradle is to define the various entry points into the applicaiton.   Each operation has a operator class that is called.   These operator classes are linked and called from a gradle task. 

### Data Locations

The data is written out to the $buildDir/data/tba and $buildDir/data/toa directories.


### tasks
To have gradle list out all of the available tasks use the command **gradlew tasks** command it should give output simular to below.

~~~~
jas@Kudu:~/workspace/robotstats$ ./gradlew tasks

> Task :tasks

------------------------------------------------------------
All tasks runnable from root project
------------------------------------------------------------

Build tasks
-----------
assemble - Assembles the outputs of this project.
build - Assembles and tests this project.
buildDependents - Assembles and tests this project and all projects that depend on it.
buildNeeded - Assembles and tests this project and all projects it depends on.
classes - Assembles main classes.
clean - Deletes the build directory.
jar - Assembles a jar archive containing the main classes.
testClasses - Assembles test classes.

Build Setup tasks
-----------------
init - Initializes a new Gradle build.
wrapper - Generates Gradle wrapper files.

Documentation tasks
-------------------
groovydoc - Generates Groovydoc API documentation for the main source code.
javadoc - Generates Javadoc API documentation for the main source code.

FRC - The TroBots tasks
-----------------------
tba - Testing only for various parts of the software 
tba_listEvents - Prints Event Keys for this season.
tba_oprevent - Calculates all OPRS for the given Event
tba_pullallevents - Pulls data for a all events for this season.
tba_pullevent - Pulls data for a one event for this season.

FTC - Titainum Trojans tasks
----------------------------
toa - Test task for testing new features and calculations.
toa_allevents - lists all FIM events from TOA.
toa_event - lists all FIM events from TOA.
toa_regions - lists all of the FTC regions in the TOA
toa_seasons - lists all of the FTC seasons in the TOA
toa_states - lists all FIM events from TOA.

Help tasks
----------
buildEnvironment - Displays all buildscript dependencies declared in root project 'robotstats'.
components - Displays the components produced by root project 'robotstats'. [incubating]
dependencies - Displays all dependencies declared in root project 'robotstats'.
dependencyInsight - Displays the insight into a specific dependency in root project 'robotstats'.
dependentComponents - Displays the dependent components of components in root project 'robotstats'. [incubating]
help - Displays a help message.
model - Displays the configuration model of root project 'robotstats'. [incubating]
projects - Displays the sub-projects of root project 'robotstats'.
properties - Displays the properties of root project 'robotstats'.
tasks - Displays the tasks runnable from root project 'robotstats'.

Jblas tasks
-----------
test_jblas - Executes a JBlas benchmark which makes sure that jblas can run without error

Verification tasks
------------------
check - Runs all checks.
test - Runs the unit tests.

Rules
-----
Pattern: clean<TaskName>: Cleans the output files of a task.
Pattern: build<ConfigurationName>: Assembles the artifacts of a configuration.
Pattern: upload<ConfigurationName>: Assembles and uploads the artifacts belonging to a configuration.

To see all tasks and more detail, run gradlew tasks --all

To see more detail about a task, run gradlew help --task <task>

BUILD SUCCESSFUL in 0s
1 actionable task: 1 executed
jas@Kudu:~/workspace/robotstats$ 
~~~~



## TBA gradle tasks

This is where some of the user friendlyness of the programs starts to wear down.

This program is fully on the command line.   There is NO gui.   To change events being pulled and OPRs calculated you must make some minor edits to the tba.gradle file.

I have yet to create the logic to read these values form the command line or as properties... It should be simple... Just have not gotten around to it.


### TBA Specific tasks


#### tba - Testing only for various parts of the software
I have used this task as a testing task and shortcut.   It should be be used for anything other than saving key strokes while testing functionality. 
~~~~
gradlew tba 
~~~~


#### tba_listEvents - Prints Event Keys for this season.

I use this task to determine which event keys we need to pull.
There are 2 arguments set in this task year and filter.   The year reflects which year or game you wish to pull data for.  The filter will
limit the reported events based on the key.   

A filter off **2019mi** pulls all events reported for Michigan in 2019.

A filter of **2019** will pull all FRC events.   Please be warned that sometimes The Blue alliance does not have all data and it gives back data that can and will crash this program.   

~~~~gradle
task tba_listEvents (type: JavaExec) {
  group "FRC - The TroBots"
  description "Prints Event Keys for this season."
  classpath = sourceSets.main.runtimeClasspath
  main = 'org.wlrobotics.tba.ListEvents'
  // args [0] = Year
  // args [1] = filter 
  args "2019", "2019mi"
}
~~~~

#### tba_oprevent - Calculates all OPRS for the given Event

Once the data is downloaded from The Blue Alliance it can be processed.  We process it with the task **tba_oprevent** I was concerned that on some lower end laptops that processing large amounts of data might take a while and therefore it would be useful to sperate the pulling of the data from Blue Alliance from calucation of the OPRs.   Turns out this was not a problem but we are stuck with this design choice for now.

To change the event that you want oprs from edit tba.gradle and put in the datafile that will source the oprs.

In the example below the data source file is **"/2019week0_Merrimack.csv"**

~~~~gradle
task tba_oprevent (type: JavaExec) {
 group "FRC - The TroBots"
  description "Calculates all OPRS for the given Event"
  dependsOn makeTbaDataDir
  classpath = sourceSets.main.runtimeClasspath
  main = 'org.wlrobotics.common.EventOprWrapper'
  args dataDirTba + "/2019week0_Merrimack.csv"
}
~~~~

#### tba_oprevent - Calculates all OPRS for all event data

This task will calculate the OPR spread sheet for every data sheet that is in the build directory.   You alter the file filter to change which files are being used to source the OPR data.   My experiance is that each events worth of data can be processed in just a second or two, thus I just reprocess all csv files in the **../build/data/tba** directory.

~~~~gradle
task tba_oprallevents (type: JavaExec) {
 group "FRC - The TroBots"
  description "Calculates all OPRS for the given Event"
  dependsOn makeTbaDataDir
  classpath = sourceSets.main.runtimeClasspath
  main = 'org.wlrobotics.common.AllEventsOprWrapper'
  arg [0] = dataoutput directory
  arg [1] = include all csv files
  args dataDirTba, /.*\.csv/
}
~~~~

#### tba_pullallevents - Pulls data for a all events for this season.

This gradle task will pull data for every event that matches the filter key.

In the example below it only pulls the data for the one event that matches **2019week0** the offical FRC week 0 event based in New Hampshire.

~~~~gradle
task tba_pullevent (type: JavaExec) {
  group "FRC - The TroBots"
  description "Pulls data for a one event for this season."
  dependsOn makeTbaDataDir
  classpath = sourceSets.main.runtimeClasspath
  main = 'org.wlrobotics.tba.GetAllEventData'
  // args [0] = directory to write the cvs file
  // args [1] = filter for event key to write.
  args dataDirTba, "2019week0"
}

~~~~


#### tba_pullevent - Pulls data for a one event for this season.

This gradle task will pull data based on a filter.   In this case the filer is set for 2019 Michigan events.  By changing the filer you can pull data for a different set of events.


~~~~gradle
task tba_pullallevents (type: JavaExec) {
  group "FRC - The TroBots"
  description "Pulls data for a all events for this season."
  dependsOn makeTbaDataDir
  classpath = sourceSets.main.runtimeClasspath
  main = 'org.wlrobotics.tba.GetAllEventData'
  // args [0] = directory to write the cvs file
  // args [1] = filter for event key to write.
  args dataDirTba, "2019mi"
}
~~~~


