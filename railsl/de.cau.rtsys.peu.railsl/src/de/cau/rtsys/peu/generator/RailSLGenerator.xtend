/*
 * THIS FILE GENERATES PURE TEXTUAL CODE FOR SCCHARTS. WILL CHANGE TO M2M CONVERSIONS.
 */
package de.cau.rtsys.peu.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import de.cau.rtsys.peu.railSL.Statement
import de.cau.rtsys.peu.railSL.TimeWaitStatement
import de.cau.rtsys.peu.railSL.Block
import de.cau.rtsys.peu.railSL.ContactWaitStatement
import de.cau.rtsys.peu.railSL.SetTrackStatement
import de.cau.rtsys.peu.railSL.SetPointStatement
import de.cau.rtsys.peu.railSL.LightStatement
import com.google.inject.Inject
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.ControlflowRegion
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsCreateExtensions
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsDeclarationExtensions
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValuedObjectExtensions
import de.cau.cs.kieler.kexpressions.ValueType
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValueExtensions
import de.cau.cs.kieler.annotations.extensions.AnnotationsExtensions
import de.cau.cs.kieler.kexpressions.OperatorType
import java.util.HashMap
import de.cau.cs.kieler.kexpressions.ValuedObject
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.emf.ecore.EObject
import de.cau.rtsys.peu.railSL.Program
import java.util.Map
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.common.notify.Notification
import java.util.ArrayList
import org.eclipse.emf.common.util.TreeIterator
import java.util.List
import de.cau.rtsys.peu.railSL.ConditionalStatement
import de.cau.cs.kieler.kexpressions.Expression

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
class RailSLGenerator extends AbstractGenerator {

    /**
     * The number of segments that exist in the railway installation.
     */
    final static val NUM_OF_SEGMENTS = 48
    
    /**
     * The number of points that exist in the railway installation.
     */
    final static val NUM_OF_POINTS = 30
    
    /**
     * The number of lights that exist in the railway installation.
     */
    final static val NUM_OF_LIGHTS = 24

    /*************************************************************************
     * I N J E C T I O N S ***************************************************
     *************************************************************************/
    @Inject
    extension SCChartsExtension

    @Inject
    extension KExpressionsCreateExtensions

    @Inject
    extension KExpressionsDeclarationExtensions

    @Inject
    extension KExpressionsValuedObjectExtensions

    @Inject
    extension AnnotationsExtensions

    static var nextRegionID = 0;
    static var nextStateID = 0;

    static var valObjects = new HashMap<String, ValuedObject>()

    final static val SPEED_SLOW = 45;
    final static val SPEED_FULL = 120;

    /**
     * Transforms an instance of the RailSL metamodel to an instance of the SCCharts metamodel.
     */
    def State railSLtoSCChart(Program model) {

        return generateSCChart(model.blocks)
    }

    /**
     * Generates static code required by PROM.
     * This method is called by XText whenever the editor is saved.
     */
    override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
        generateHeaders(fsa)
        generateSnippets(fsa)
    }

    /*************************************************************************
     * S T A T I C   C O D E   G E N E R A T I O N ***************************
     *************************************************************************/
     
    /**
     * Generates the header files 'kicking.h' and 'railway.h'
     */
    def void generateHeaders(IFileSystemAccess fsa) {
        var railwayH = '//===========================================================================//
//==             R T S Y S   M O D E L   R A I L W A Y   A P I             ==//
//==                   R A I L W A Y   I N T E R F A C E                   ==//
//==                                                                       ==//
//==   by Nis Boerge Wechselberg, Christian-Albrechts-University Kiel       ==//
//==                                                                       ==//
//==   Artwork by:                                                         ==//
//==   by Christian Motika, SS 2014, Christian-Albrechts-University Kiel   ==//
//===========================================================================//
//==                                                                       ==//
//==                                     (@@@)     (@@@@@)                 ==//
//==                               (@@)     (@@@@@@@)        (@@@@@@@)     ==//
//==                         (@@@@@@@)   (@@@@@)       (@@@@@@@@@@@)       ==//
//==                    (@@@)     (@@@@@@@)   (@@@@@@)             (@@@)   ==//
//==               (@@@@@@)    (@@@@@@)                (@)                 ==//
//==           (@@@)  (@@@@)           (@@)                                ==//
//==        (@@)              (@@@)                                        ==//
//==       .-.                                                             ==//
//==       ] [    .-.      _    .-----.                                    ==//
//==     ."   """"   """""" """"| .--`                                     ==//
//==    (:--:--:--:--:--:--:--:-| [___    .------------------------.       ==//
//==     |     :  :  :  :  :  : [_9_] |\'=\'|.----------------------.|       ==//
//==    /|.___________________________|___|\'--.___.--.___.--.___.-\'|       ==//
//==   / ||_.--.______.--.______.--._ |---\\\'--\\-.-/==\\-.-/==\\-.-/-\'/--     ==//
//==  /__;^=(==)======(==)======(==)=^~^^^ ^^^^(-)^^^^(-)^^^^(-)^^^        ==//
//==  ~~~^~~~~^~~~^~~~^~~~^~~~^~~~^~~~^~~~^~~~^~~~^~~~^~~~^~~~^~~~^~~~^~~~ ==//
//==                                                                       ==//
//===========================================================================//
#ifndef RAILWAY_H
#define RAILWAY_H

#include "addressing.h"
#include "railPi.h"

/* Kept for compatibility purpose */
struct railway_hardware {};

/* Main data structure that contains all information and control structures
   needed to operate the system. This structure will be used like a handle for
   all other functions. */
struct railway_system {
  portConfig_t        *addresses;
  railPi_connector_t  railPi[4];
};
typedef struct railway_system railway_system_t;

/* Initialize the control structure to communicate with a given railway system
   described by (hardware). */
struct railway_system *railway_initsystem(struct railway_hardware *hardware);

/* Open all nodelinks required to operate the system with one function
   call. (hostformat) is a printf format string that forms the common base for
   all hostnames. A single %i is allowed in this string and will be expanded
   to the node number (example: "node%02i.rtsys"). (device) is the name of the
   remote serial port. This function will return 0 on success, in case of a
   failure it will print an error message, return -1/errno and close all
   previously opened links. */
int railway_openlinks_udp(struct railway_system *railway, char *hostformat, char *device);

/* Start the system (and the control thread) after all nodelinks have been
   registered. The thread will try to keep the cycle execution time between
   mincycle and maxcycle microseconds. Both values may be set to zero, this
   will cause the thread to execute cycles as fast as possible and to use the
   nodelink\'s timeouts as an upper limit. Returns 0 on success and -1/errno on
   failure. */
int railway_startcontrol(struct railway_system *railway, unsigned mincycle, unsigned maxcycle);

/* Stop the system (and the control thread), send disconnect commands to all
   nodes and reset them if desired. Be careful with this option: if the the
   system stops uncontrolled, there might be trains standing on points. A
   reset (or restart) in this situation might force such trains off the
   track. Returns 0 on success and -1/errno on failure. */
int railway_stopcontrol(struct railway_system *railway, int reset);

/* Test if the system is alive. The system may shut itself down if there are
   too many communication errors. An application should poll this flag in
   regular intervals and react if the system is not alive any more. */
int railway_alive(struct railway_system *railway);

/* Close all nodelinks that are registered with the control system. The system
   must be stopped for this to work, all successfully closed nodelinks will be
   unregistered (set to NULL) in the linktable. Returns 0 on success and -1 on
   errors, errno is set by the last registered error. */
int railway_closelinks(struct railway_system *railway);

/* Destroy the control structure, free used memory. This function may only be
   called if the system is not running. Nodelinks are not closed, this must be
   done prior to this call by railway_closelinks or at any other time by the
   application itself. */
int railway_donesystem(struct railway_system *railway);


/* ---- high level peripheral access -------------------------------------- */

/* Query if a specific block signal exists */
int signalexists(struct railway_system *railway, int block, int signaln);

/* Set one or more signals to a given color combination.

     (lights)  bitwise or of the constants RED, YELLOW and GREEN
     (block)   is the number of the block containing the signal
     (signal)  the number inside the block (0=beginning, 1=end of the
               block regarding the main direction)

   A value of -1 for (block) and/or (signal) acts as a wildcard and makes it
   possible to set a group of signals with only one command. */
#define RED     4
#define YELLOW  2
#define GREEN   1
void setsignal(struct railway_system *railway, int block, int signaln, int lights);

/* Read the current color combination of a signal.

     (block)   is the number of the block containing the signal
     (signal)  the number inside the block (0=beginning, 1=end of the
               block regarding the main direction)

   A value of -1 for (block) and/or (signal) acts as a wildcard. The function
   will return the state of the first matching signal in this case. */
unsigned getsignal(struct railway_system *railway, int block, int signaln);

/* Set one or more traffic lights at a crossing.

     (signal)    signal number
     (lights)    bitwise or of the constants RED, YELLOW and GREEN

   A value of -1 for (signal) acts as a wildcard and makes it possible to set
   a group of traffic lights with only one command. */
void setgatesignal(struct railway_system *railway, int signaln, int lights);

/* Get the status one traffic light at a crossing.

     (signal)    signal number

   A value of -1 for (signal) acts as a wildcard. The function will return the
   state of the first matching traffic light in this case. */
unsigned getgatesignal(struct railway_system *railway, int signaln);

/* Query if a specific contact exists */
int contactexists(struct railway_system *railway, int block, int contact);

/* Read the status of one contact pair in the system.

   (block)    block  number containing the signal
   (contact)  contact number inside the block (0=beginning, 1=end of the
              block regarding the main direction)
   (clear)    a nonzero value will remove the contact event from the buffer
              after reading

   Return values are NONE, FWD, REV or UNI as described below.

     NONE - not triggered
     FWD  - a train has passed the contact while driving forward
     REV  - dito, but with reverse direction
     UNI  - triggered, but the direction could not be detected (caused by
            unidirectional sensors, reed contact failures and too long
      polling intervals, see below)

   It is possible to use a value of -1 for (block) and/or (contact), the
   function will scan all contacts for an event in this case and return the
   first match. It doesn\'t make much sense to do this with function getcontact
   because it cannot return the location of the contact. Use the otherwise
   identical scancontact for such cases, which will set the call by reference
   parameters to the contact location.

   The function getcontactref works mostly like getcontact, but it uses a user
   supplied reference to determine which events are new. This allows an
   alternate control flow to parse events that the main program has already
   processed and cleared. The reference must be initialized to zero and will
   be updated by any call to this function. The caller should be aware that
   the history of directions is only one level deep. Events that are older
   will be reported as UNI instead of FWD or REV. */
#define NONE   0
#define FWD    1
#define REV    2
#define UNI    3
unsigned getcontact(struct railway_system *railway, int block, int contact, int clear);

/* Remove all contact events from the buffer for a given contact pair. It is
   also possible to use -1 as a wildcard for (block) and (contact) to clear
   whole groups of contacts. */
void clearcontact(struct railway_system *railway, int block, int contact);

/* Sensors at the gates of crossings can detect if the gate is open or the
   boom is down, and they are used exactly like the reed contact
   sensors. Whenever the gate is completely closed, the sensor returns a FWD
   reading. If the boom leaves this position, the sensor reports REV. Keep in
   mind that the gate sensors are filtered like contacts and ignore events
   that happen too fast. So the gate must stay open or closed for a short
   period of time before new events can be detected. */
unsigned getgatesensor(struct railway_system *railway, int gatesensor, int clear);
void cleargatesensor(struct railway_system *railway, int gatesensor);

/* Set the state of one or more track drivers.
     (block)   block number of the track
     (mode)    driver mode, one of TOFF,TFWD,TREV,TBRAKE
     (target)  speed setting, bitwise or of one of the constants TPWM or
               TSPEED and a speed value between 0 and 127.
   A value of -1 for (block) acts as a wildcard and makes it possible to set
   all tracks with only one command. */
// #define OFF    0
// #define BRAKE  3
// #define PWM    0
// #define SPEED  128
void settrack(struct railway_system *railway, int track, unsigned mode, unsigned target);

/* Get the state of one track driver. A (block) value of -1 will return the
   state of the first matching track. */
void gettrack(struct railway_system *railway, int track, unsigned *mode, unsigned *target);

/* Set the state of one or more points.
   (point)   the point number
   (state)   new state, STRAIGHT (=0) or BRANCH (!=0)
   A value of -1 for (point) acts as a wildcard and makes it possible to set
   all points with only one command. */
#define STRAIGHT  1
#define BRANCH    0
void setpoint(struct railway_system *railway, int point, int state);

/* Get the state of one point. A (point) value of -1 will return the state of
   the first matching point. */
int getpoint(struct railway_system *railway, int point);

/* Set the state of one or more lights.

   (light)   the light number
   (state)   new state, OFF (=0) or ON (!=0)

   A value of -1 for (light) acts as a wildcard and makes it possible to set
   all lights with only one command. */
#define OFF       1
#define ON        0
void setlight(struct railway_system *railway, int light, int state);

/* Get the state of one light. A (light) value of -1 will return the state of
   the first matching light. */
int getlight(struct railway_system *railway, int light);

/* Set the state of one or more crossing gates.

   (gate)      gate number
   (state)     new state, UP (=0) or DOWN (!=0)

   A value of -1 (gate) acts as a wildcard and makes it possible to set a
   group of gates with only one command. */
#define UP    1
#define DOWN  0
void setgate(struct railway_system *railway, int gate, int state);

/* Get the state of one gate. A value of -1 for (gate) acts as a wildcard. The
   function will return the state of the first matching gate in this case. */
int getgate(struct railway_system *railway, int gate);

/* Set the state of one or more bells.

   (bell)      bell number
   (state)     new state, OFF (=0) or ON (!=0)

   A value of -1 for (bell) acts as a wildcard and makes it possible to set a
   group of bells with only one command. */
void setbell(struct railway_system *railway, int bell, int state);

/* Get the state of one bell. A value of -1 for (bell) acts as a wildcard. The
   function will return the state of the first matching bell in this case. */
int getbell(struct railway_system *railway, int bell);

#endif'

        fsa.generateFile('railway.h', railwayH)

        val kickingH = '/* ==========================================================================

    kicking.h

    Railway layout definition. This file has been created automatically by a
    script. Do not edit this code, use the corresponding CSV file instead.

    Copyright 2005 Stephan Hoehrmann, stephan@hoehrmann.de

    This is free software, released under the terms of the GNU General
    Public License.

 ========================================================================= */

#ifndef _KICKING_H_

#include "railway.h"

#define IC_JCT_0     0
#define IC_LN_0      1
#define IC_LN_1      2
#define IC_LN_2      3
#define IC_LN_3      4
#define IC_LN_4      5
#define IC_LN_5      6
#define IC_ST_0      7
#define IC_ST_1      8
#define IC_ST_2      9
#define IC_ST_3      10
#define IC_ST_4      11
#define IO_LN_0      12
#define IO_LN_1      13
#define IO_LN_2      14
#define KH_LN_0      15
#define KH_LN_1      16
#define KH_LN_2      17
#define KH_LN_3      18
#define KH_LN_4      19
#define KH_LN_5      20
#define KH_LN_6      21
#define KH_LN_7      22
#define KH_LN_8      23
#define KH_ST_0      24
#define KH_ST_1      25
#define KH_ST_2      26
#define KH_ST_3      27
#define KH_ST_4      28
#define KH_ST_5      29
#define KH_ST_6      30
#define KIO_LN_0     31
#define KIO_LN_1     32
#define OC_JCT_0     33
#define OC_LN_0      34
#define OC_LN_1      35
#define OC_LN_2      36
#define OC_LN_3      37
#define OC_LN_4      38
#define OC_LN_5      39
#define OC_ST_0      40
#define OC_ST_1      41
#define OC_ST_2      42
#define OC_ST_3      43
#define OC_ST_4      44
#define OI_LN_0      45
#define OI_LN_1      46
#define OI_LN_2      47

struct railway_hardware kicking;

#define _KICKING_H_
#endif'

        fsa.generateFile("kicking.h", kickingH)

    }

    /**
     * Generates all snippets required for a compilation via PROM.
     */
    def void generateSnippets(IFileSystemAccess fsa) {
        fsa.generateFile('contacts.ftl', generateContactsSnippet())
        fsa.generateFile('lights.ftl', generateLightsSnippet())
        fsa.generateFile('points.ftl', generatePointsSnippet())
        fsa.generateFile('signals.ftl', generateSignalsSnippet())
        fsa.generateFile('tracks.ftl', generateTracksSnippet())
        fsa.generateFile('ControllerMain.ftl', generateMainSnippet())
    }


    /**
     * Generate the static PROM main code snippet 
     */
    def String generateMainSnippet() {
        var result = 
'/******************************************************************
 * T E S T   H E A D E R                                          *
 *                                                                *
 * C O O L   S T U F F   H E R E                                  *
 ******************************************************************/

#include "kicking.h"
#include "railway.h"

// Basic dimension constants
#define NUM_OF_TRACKS 48
#define NUM_OF_POINTS 30
#define NUM_OF_LIGHTS 24


int main(int *argn, char *argv[]) {

    // Setup and establishing connection to the railway
    struct railway_system *railway;
    railway = railway_initsystem(&kicking);
    railway_openlinks_udp(railway,"node%02i","/dev/ttyS0");
    railway_startcontrol(railway,0,0);

    reset();
    
    while(1) {
    
${inputs}
        
        tick();
        
${outputs}
    
    }
    
    // Shut down the connection to the railway
    railway_stopcontrol(railway,1);
    railway_closelinks(railway);
    railway_donesystem(railway);
    
    return 0;
}'
        return result
    }

    /**
     * Generate a static PROM code snippet as wrapper for the @code{tracks} variable.
     */
    def String generateTracksSnippet() {
        var result = '<#-- T R A C K S -->
<#macro tracks>
    <@output>
        // Set the tracks to the appropriate speed
        for (int i = 0; i < NUM_OF_TRACKS; i++) {
            settrack(railway, i, ${varname}[i][0], ${varname}[i][1]);
        }
    </@>
</#macro>'
        return result
    }

    /**
     * Generate a static PROM code snippet as wrapper for the @code{signals} variable.
     */
    def String generateSignalsSnippet() {
        var result = '<#-- S I G N A L S -->
<#macro signals>
    <@output>
        // Set all the signals appropriately
        for (int i = 0; i < NUM_OF_TRACKS; i++) {
            if ((i != KH_ST_0) && (i != KH_ST_6) && (i != IC_ST_0) && (i != IC_ST_4) && \\
                (i != OC_ST_0) && (i != OC_ST_4) && (i != IC_JCT_0) && (i != OC_JCT_0) {
                setsignal(railway, i, 0, ${varname}[i][0];
                setsignal(railway, i, 1, ${varname}[i][1];
            }
        }
    </@>
</#macro>'
        return result
    }


    /**
     * Generate a static PROM code snippet as wrapper for the @code{points} variable.
     */
    def String generatePointsSnippet() {
        var result = '<#-- S W I T C H   P O I N T S -->
<#macro points>
    <@output>
        // Set all the switch points appropriately
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            setpoint(railway, i, ${varname}[i]);
        }
    </@>
</#macro>'
        return result
    }


    /**
     * Generate a static PROM code snippet as wrapper for the @code{lights} variable.
     */
    def String generateLightsSnippet() {
        var result = '<#-- L I G H T S -->
<#macro lights>
    <@output>
        // Set all the lights appropriately
        for (int i = 0; i < NUM_OF_LIGHTS; i++) {
            setlight(railway, i, ${varname}[i];
        }
    </@>
</#macro>'

        return result
    }
    
    /**
     * Generate a static PROM code snippet as wrapper for the @code{contacts} variable.
     */
    def String generateContactsSnippet() {
        var result = '<#-- C O N T A C T S -->
<#macro contacts>
    <@input>
        // Scan the contacts at the beginning of each tick
        for (int i = 0; i < NUM_OF_TRACKS, i++) {
            if ((i != KH_ST_0) && (i != KH_ST_6) && (i != IC_ST_0) && (i != IC_ST_4) && \\
                (i != OC_ST_0) && (i != OC_ST_4) && (i != IC_JCT_0) && (i != OC_JCT_0)) {
                ${varname}[i][0] = scancontact(railway, i, 0, 1);
                ${varname}[i][1] = scancontact(railway, i, 1, 1);   
            }
        }
    </@>
</#macro>'
        return result
    }

    /**************************************************************************
     * T R A N S F O R M A T I O N S ******************************************
     **************************************************************************/
     
    /**
     * Transforms the model into an SCCharts model
     */
    def State generateSCChart(List<Block> blocks) {

        var chart = createSCChart();

        // I N T E R F A C E
        // input bool contacts[48][2];
        val contactsDecl = createDeclaration(ValueType.BOOL)
        contactsDecl.input = true;
        val contacts = createValuedObject("contacts")
        contacts.cardinalities.add(createIntValue(NUM_OF_SEGMENTS))
        contacts.cardinalities.add(createIntValue(2))
        contactsDecl.attach(contacts)
        contactsDecl.annotations.add(createStringAnnotation("Wrapper", "contacts"));

        chart.declarations.add(contactsDecl)
        valObjects.put("contacts", contacts)

        // output int tracks[48][2];
        val tracksDecl = createDeclaration(ValueType.INT)
        tracksDecl.output = true;
        val tracks = createValuedObject("tracks")
        tracks.cardinalities.add(createIntValue(NUM_OF_SEGMENTS))
        tracks.cardinalities.add(createIntValue(2))
        tracksDecl.attach(tracks)
        tracksDecl.annotations.add(createStringAnnotation("Wrapper", "tracks"))

        chart.declarations.add(tracksDecl)
        valObjects.put("tracks", tracks)

        // output bool points[30];
        val pointsDecl = createDeclaration(ValueType.BOOL)
        pointsDecl.output = true
        val points = createValuedObject("points")
        points.cardinalities.add(createIntValue(NUM_OF_POINTS))
        pointsDecl.attach(points)
        pointsDecl.annotations.add(createStringAnnotation("Wrapper", "points"))

        chart.declarations.add(pointsDecl)
        valObjects.put("points", points)

        // output int signals[48][2];
        val signalsDecl = createDeclaration(ValueType.INT)
        signalsDecl.output = true
        val signals = createValuedObject("signals")
        signals.cardinalities.add(createIntValue(NUM_OF_SEGMENTS))
        signalsDecl.attach(signals)
        signalsDecl.annotations.add(createStringAnnotation("Wrapper", "signals"))

        chart.declarations.add(signalsDecl)
        valObjects.put("signals", signals)

        // output bool lights[24];
        val lightsDecl = createDeclaration(ValueType.BOOL)
        lightsDecl.output = true
        val lights = createValuedObject("lights")
        lights.cardinalities.add(createIntValue(NUM_OF_LIGHTS))
        lightsDecl.attach(lights)
        lightsDecl.annotations.add(createStringAnnotation("Wrapper", "lights"))

        chart.declarations.add(lightsDecl)
        valObjects.put("lights", lights)

        // input bool second;
        val secondDecl = createDeclaration(ValueType.BOOL)
        secondDecl.input = true
        val second = createValuedObject("second")
        secondDecl.attach(second)
        secondDecl.annotations.add(createStringAnnotation("Wrapper", "second"))

        chart.declarations.add(secondDecl)
        valObjects.put("second", second);

        // A C T U A L   D I A G R A M   S Y N T H E S I S
        nextRegionID = 0
        for (block : blocks) {
            nextStateID = 0
            block.compile(chart)
        }

        return chart;

    }

    /**
     * Compiles a block into a region in the given state.
     * <p>
     * Blocks are represented by purely sequential regions within the same superstate.
     * Each statement is translated to a state within the region along with an initial and a
     * final state. All statements are connected by termination transitions.
     * 
     */
    def void compile(Block block, State chart) {

        var region = chart.createControlflowRegion("Thread " + getRegionID())
        var currentState = region.createInitialState("init")

        for (statement : block.statements) {
            var state = statement.compile(region)
            if (currentState != region.initialState) {
                var connectorState = region.createState("");
                var termTransition = currentState.createTransitionTo(connectorState)
                var transition = connectorState.createTransitionTo(state)
                termTransition.setTypeTermination
            } else {
                var transition = currentState.createTransitionTo(state)
                transition.setImmediate
            }
            
            currentState = state
        }
        
        var state = region.createState("")
        var term = currentState.createTransitionTo(state)
        term.setTypeTermination
        currentState = state
        
        if (block.end.equals("End.")) {
            val done = region.createFinalState("done")
            currentState.createTransitionTo(done)
        } else {
            currentState.createTransitionTo(region.initialState)
        }
    }

    /**
     * Generates a state representing a statement.
     * 
     * The state will be located within the region passed as an argument.
     * The state will in itself contain various simple states.
     * <p>
     * It is ensured that all states generated by this method will have
     * an internal final state that will always be reached.
     */
    def State compile(Statement statement, ControlflowRegion region) {

        var state = region.createState("");

        if (statement instanceof TimeWaitStatement) {
            state.makeTimeWaitStatement(statement as TimeWaitStatement)
        } else if (statement instanceof ContactWaitStatement) {
            state.makeContactWaitStatement(statement as ContactWaitStatement)
        } else if (statement instanceof SetTrackStatement) {
            state.makeSetTrackStatement(statement as SetTrackStatement)
        } else if (statement instanceof SetPointStatement) {
            state.makeSetPointStatement(statement as SetPointStatement)
        } else if (statement instanceof LightStatement) {
            state.makeLightStatement(statement as LightStatement)
        } else if (statement instanceof ConditionalStatement) {
            state.makeConditionalStatement(statement as ConditionalStatement)
        }

        return state
    }

    /**
     * Transforms an empty state into a ConditionalStatement.
     * 
     * Each line is represented by its own state with internal behavior.
     */
    def void makeConditionalStatement(State state, ConditionalStatement cStatement) {
        state.label = "_" + getStateID() + "_Conditional"
        var i = 0;
        val contacts = valObjects.get("contacts")
        var expressions = new ArrayList<Expression>()

        val region = state.createControlflowRegion("")
        region.createInitialState("init")
        region.createFinalState("done")

        // create trigger expressions for each statement
        for (line : cStatement.lines) {
            val contactIndex = if(line.contact.equals("first")) 0 else 1
            val trackIndex = line.segName.parseTrackSegment

            expressions.add(i, createOperatorExpression(OperatorType.EQ) => [
                subExpressions += contacts.reference => [
                    indices += createIntValue(trackIndex)
                    indices += createIntValue(contactIndex)
                ]
                subExpressions += createBoolValue(true)
            ])
            i++
        }
        
        // create states for each line
        var j = 0
        for (line : cStatement.lines) {
            var currentState = region.createState("Alternative " + j)
            line.block.compile(currentState)
            val trans = region.initialState.createImmediateTransitionTo(currentState)
            trans.trigger = expressions.get(j)
            trans.priority = j + 1
            val termTrans = currentState.createTransitionTo(region.finalState)
            termTrans.setTypeTermination
            termTrans.setNotImmediate
            j++
        }
    }

    /**
     * Transforms an empty state into a light statement state.
     * 
     * This state sets one or multiple lights to a certain setting.
     */
    def void makeLightStatement(State state, LightStatement lStatement) {
        state.label = "_" + getStateID() + "_Light"

        val region = state.createControlflowRegion("Set_lights_to_" + lStatement.state)

        val setting = parseLightMode(lStatement)

        var currentState = region.createInitialState("init")
        var i = 0

        val lights = valObjects.get("lights")

        for (light : lStatement.lights) {
            var nextState = region.createState("_S" + i)
            var transition = currentState.createImmediateTransitionTo(nextState)
            transition.addEffect(lights.assign(createIntValue(setting)) => [
                indices += createIntValue(light)
            ])
            currentState = nextState
            i++
        }
        currentState.final = true
    }

    /**
     * Transforms an empty state into a point statement state.
     * 
     * This state sets one or multiple points to a certain setting.
     */
    def void makeSetPointStatement(State state, SetPointStatement spStatement) {
        state.label = "_" + getStateID() + "_SetPoint"

        val region = state.createControlflowRegion("Set_points_to_" + spStatement.orientation)

        val direction = parsePointSetting(spStatement)

        var currentState = region.createInitialState("init")
        var i = 0

        val points = valObjects.get("points")

        for (segment : spStatement.points) {
            var nextState = region.createState("_S" + i)
            var transition = currentState.createImmediateTransitionTo(nextState)
            transition.addEffect(points.assign(createIntValue(direction)) => [
                indices += createIntValue(segment)
            ])
            currentState = nextState
            i++
        }
        currentState.final = true
    }

    /**
     * Transforms an empty state into a track statement state.
     * 
     * This state sets one or multiple tracks to a certain setting.
     * 
     * @TODO Make this set signals as well
     */
    def void makeSetTrackStatement(State state, SetTrackStatement stStatement) {
        state.label = "_" + getStateID() + "_SetTrack"

        val region = state.createControlflowRegion("Set_tracks_to_" + stStatement.mode)

        val speed = parseSpeed(stStatement)
        val direction = parseDirection(stStatement)

        var currentState = region.createInitialState("init")
        var i = 0

        val tracks = valObjects.get("tracks")

        for (segment : stStatement.segments) {
            var nextState = region.createState("_S" + i)
            var transition = currentState.createImmediateTransitionTo(nextState)

            val trackIndex = segment.parseTrackSegment

            transition.addEffect(tracks.assign(createIntValue(speed)) => [
                indices += createIntValue(trackIndex)
                indices += createIntValue(0)
            ])
            transition.addEffect(tracks.assign(createIntValue(direction)) => [
                indices += createIntValue(trackIndex)
                indices += createIntValue(1)
            ])
            currentState = nextState
            i++
        }
        currentState.final = true
    }

    /**
     * Transforms an empty state into a contact wait statement state.
     * 
     * This state will terminate once a certain contact event has occured.
     */
    def void makeContactWaitStatement(State state, ContactWaitStatement cwStatement) {
        state.label = "_" + getStateID() + "_ContactWait"

        // Parse information from statement object 
        val trackIndex = cwStatement.segName.parseTrackSegment
        val contactIndex = (if(cwStatement.contactIndex.equals("first")) 0 else 1)
        val delay = (if(cwStatement.event.equals("Reach")) 1 else 2)

        // Create all required states
        var region = state.createControlflowRegion(cwStatement.event + "_contact_" + contactIndex +
            cwStatement.segName);
        var init = region.createInitialState("init")
        var done = region.createFinalState("done");
        var transition = init.createImmediateTransitionTo(done)
        transition.delay = delay

        // Get the root state's variable called "contacts", which is a bool array 
        val contacts = valObjects.get("contacts")

        transition.trigger = createOperatorExpression(OperatorType.EQ) => [
            subExpressions += contacts.reference => [
                indices += createIntValue(trackIndex)
                indices += createIntValue(contactIndex)
            ]
            subExpressions += createBoolValue(true)
        ]
    }

    /**
     * Transforms an empty state into a time wait statement state.
     * 
     * This state will terminate once a certain amount of time has passed.
     */
    def void makeTimeWaitStatement(State state, TimeWaitStatement twStatement) {
        state.label = "_" + getStateID() + "_TimeWait"
        var region = state.createControlflowRegion("Wait " + twStatement.time)
        var init = region.createInitialState("init")
        var done = region.createFinalState("done")
        var transition = init.createImmediateTransitionTo(done)
        transition.delay = twStatement.time

        val second = valObjects.get("second")

        transition.trigger = createOperatorExpression(OperatorType.EQ) => [
            subExpressions += second.reference
            subExpressions += createBoolValue(true)
        ]
    }

    /*****************************************************************************************
     * H E L P E R   M E T H O D S ***********************************************************
     *****************************************************************************************/
    
    /**
     * Helper method providing unique state IDs
     */
    def String getStateID() {
        nextStateID++;
        return "" + (nextStateID - 1)
    }

    /**
     * Helper method providing unique region IDs
     */
    def String getRegionID() {
        nextRegionID++;
        return "" + (nextRegionID - 1);
    }

    /**
     * Helper method to determine the light state
     */
    def int parseLightMode(LightStatement lStatement) {
        if (lStatement.state.equals("on")) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Helper method to determine the setting for a point
     */
    def int parsePointSetting(SetPointStatement spStatement) {
        if (spStatement.orientation.equals("straight")) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Helper method to determine the speed to which a track segment should be set.
     */
    def int parseSpeed(SetTrackStatement stStatement) {
        if (stStatement.mode.contains("stop")) {
            return 0;
        } else if (stStatement.mode.contains("slow")) {
            return SPEED_SLOW;
        } else {
            return SPEED_FULL;
        }
    }

    /**
     * Helper method to determine the direction of travel.
     */
    def int parseDirection(SetTrackStatement stStatement) {
        if (stStatement.mode.contains("reverse")) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * Helper method to translate track segment names to integers.
     * Defined according to the <Railway.h> header file.
     */
    def int parseTrackSegment(String string) {
        return switch (string) {
            case "IC_JCT_0": 0
            case "IC_LN_0": 1
            case "IC_LN_1": 2
            case "IC_LN_2": 3
            case "IC_LN_3": 4
            case "IC_LN_4": 5
            case "IC_LN_5": 6
            case "IC_ST_0": 7
            case "IC_ST_1": 8
            case "IC_ST_2": 9
            case "IC_ST_3": 10
            case "IC_ST_4": 11
            case "IO_LN_0": 12
            case "IO_LN_1": 13
            case "IO_LN_2": 14
            case "KH_LN_0": 15
            case "KH_LN_1": 16
            case "KH_LN_2": 17
            case "KH_LN_3": 18
            case "KH_LN_4": 19
            case "KH_LN_5": 20
            case "KH_LN_6": 21
            case "KH_LN_7": 22
            case "KH_LN_8": 23
            case "KH_ST_0": 24
            case "KH_ST_1": 25
            case "KH_ST_2": 26
            case "KH_ST_3": 27
            case "KH_ST_4": 28
            case "KH_ST_5": 29
            case "KH_ST_6": 30
            case "KIO_LN_0": 31
            case "KIO_LN_1": 32
            case "OC_JCT_0": 33
            case "OC_LN_0": 34
            case "OC_LN_1": 35
            case "OC_LN_2": 36
            case "OC_LN_3": 37
            case "OC_LN_4": 38
            case "OC_LN_5": 39
            case "OC_ST_0": 40
            case "OC_ST_1": 41
            case "OC_ST_2": 42
            case "OC_ST_3": 43
            case "OC_ST_4": 44
            case "OI_LN_0": 45
            case "OI_LN_1": 46
            case "OI_LN_2": 47
            default: -1
        };
    }
}