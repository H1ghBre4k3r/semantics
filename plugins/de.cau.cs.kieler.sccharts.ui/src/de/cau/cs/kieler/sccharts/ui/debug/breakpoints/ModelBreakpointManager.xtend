/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright ${year} by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.sccharts.ui.debug.breakpoints

import com.google.inject.Guice
import com.google.inject.Inject
import de.cau.cs.kieler.sccharts.SCCharts
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.Transition
import de.cau.cs.kieler.sccharts.extensions.SCChartsScopeExtensions
import de.cau.cs.kieler.sccharts.processors.statebased.DebugAnnotations
import de.cau.cs.kieler.sccharts.ui.debug.highlighting.DebugHighlighter
import java.util.List
import java.util.regex.Pattern
import org.eclipse.core.resources.IMarker
import org.eclipse.core.resources.IResource
import org.eclipse.debug.core.model.IBreakpoint
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.debug.core.IJavaBreakpoint
import org.eclipse.jdt.ui.JavaUI
import org.eclipse.swt.widgets.Display
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.texteditor.ITextEditor
import de.cau.cs.kieler.sccharts.extensions.SCChartsCoreExtensions
import org.eclipse.jdt.debug.core.IJavaThread

/**
 * Central class to synchronize and manage all breakpoints generated by the debugger.
 * 
 * This class is responsible for coordinating Java breakpoints, visualization highlights
 * and their relationships. It keeps track of breakpoints per state as well as actual Java breakpoints
 * it set itself to be able to remove them later.
 * Functionality of registering breakpoint hits is separated in class @link{JavaBreakpointListener}.
 * 
 * @author stu121235
 *
 */
class ModelBreakpointManager {
    
    public static val MODEL_ELEMENT = "Model Element"
    
    // Keep track of the model elements with breakpoints for quick access 
    val statesWithBreakpoint = <State> newHashSet 
    val transitionsWithBreakpoint = <Transition> newHashSet
    val transitionsWithCheckBreakpoint = <Transition> newHashSet
    
    // Keep map of breakpoints associated with model elements for later removal
    val stateToBreakpoint = <State, List<IJavaBreakpoint>> newHashMap
    val stateToWatchBreakpoints = <State, List<IJavaBreakpoint>> newHashMap
    val transitionToBreakpoint = <Transition, List<IJavaBreakpoint>> newHashMap
    val transitionToCheckBreakpoint = <Transition, List<IJavaBreakpoint>> newHashMap
    
    @Inject extension SCChartsScopeExtensions
    @Inject extension SCChartsCoreExtensions
    
    var DebugHighlighter highlighter
    
    var IJavaBreakpoint lastBreakpoint
    var IJavaThread lastThread
    
    new(DebugHighlighter hl) {
        Guice.createInjector.injectMembers(this)
        this.highlighter = hl
    }
    
    /**
     * Toggles all breakpoints associated with the given state.
     * Appropriate positions in the editor for setting the breakpoint(s)
     * will be determined here.
     */
    def toggleBreakpoint(State state) {
        if (statesWithBreakpoint.contains(state)) {
            // State already has a breakpoint, remove all associated Java breakpoints
            statesWithBreakpoint.remove(state)
            highlighter.removeBreakpointDecorator(state)
            state.clearBreakpoints
            stateToBreakpoint.remove(state)
            stateToWatchBreakpoints.remove(state)
        } else {
            // No breakpoint yet, need to find all appropriate breakpoint locations and add them
            statesWithBreakpoint.add(state)
            highlighter.addBreakpointDecorator(state)
            val stateBreakpoints = <IJavaBreakpoint> newLinkedList
            for (line : state.findStateLines) {
                val bp = createBreakpointOnLine(line, BreakpointType.STATE_BREAKPOINT, state, null)
                stateBreakpoints.add(bp)
            }
            stateToBreakpoint.put(state, stateBreakpoints)
            
            // Add a TransitionWatchBreakpoint to each outgoing transition
            val transitionWatchBreakpoints = <IJavaBreakpoint> newLinkedList
            for (transition : state.outgoingTransitions) {
                for (line : transition.findTransitionLines) {
                    val bp = createBreakpointOnLine(line, BreakpointType.TRANSITION_WATCH_BREAKPOINT, null, transition)
                    println(bp)
                    transitionWatchBreakpoints.add(bp)
                }
            }
            stateToWatchBreakpoints.put(state, transitionWatchBreakpoints)
        }
    }
    
    /**
     * Toggles all regular breakpoints associated with the given transition.
     * Appropriate positions in the editor for setting the breakpoint(s)
     * will be determined here.
     */
    def toggleBreakpoint(Transition transition) {
        if (transitionsWithBreakpoint.contains(transition)) {
            // Transition already has a breakpoint, remove all associated Java breakpoints
            transitionsWithBreakpoint.remove(transition)
            highlighter.removeBreakpointDecorator(transition)
            transition.clearBreakpoints
            transitionToBreakpoint.remove(transition)
        } else {
            // No breakpoint yet, need to find all appropriate breakpoint locations and add them
            transitionsWithBreakpoint.add(transition)
            highlighter.addBreakpointDecorator(transition)
            val breakpoints = <IJavaBreakpoint> newLinkedList
            for (line : transition.findTransitionLines) {
                val bp = createBreakpointOnLine(line, BreakpointType.TRANSITION_TAKEN_BREAKPOINT, null, transition)
                breakpoints.add(bp)
            }
            transitionToBreakpoint.put(transition, breakpoints)
        }
    }
    
    /**
     * Toggles all check breakpoints associated with the given transition.
     * Appropriate positions in the editor for setting the breakpoint(s)
     * will be determined here.
     */
    def toggleCheckBreakpoint(Transition transition) {
        if (transitionsWithCheckBreakpoint.contains(transition)) {
            // Transition already has a breakpoint, remove all associated Java breakpoints
            transitionsWithCheckBreakpoint.remove(transition)
            highlighter.removeCheckBreakpointDecorator(transition)
            transition.clearCheckBreakpoints
            transitionToCheckBreakpoint.remove(transition)
        } else {
            // No breakpoint yet, need to find all appropriate breakpoint locations and add them
            transitionsWithCheckBreakpoint.add(transition)
            highlighter.addCheckBreakpointDecorator(transition)
            val breakpoints = <IJavaBreakpoint> newLinkedList
            for (line : transition.findTransitionCheckLines) {
                val bp = createBreakpointOnLine(line, BreakpointType.TRANSITION_CHECK_BREAKPOINT, null, transition)
                breakpoints.add(bp)
            }
            transitionToCheckBreakpoint.put(transition, breakpoints)
        }
    }
    
    /**
     * Checks a loaded breakpoint and registers it if this hasn't happened yet.
     * Required since the registered breakpoints are lost after restarting Eclipse, but the markers remain there.
     */
    def presentBreakpoint(IJavaBreakpoint breakpoint, SCCharts model) {
        
        if (breakpoint instanceof TransitionBreakpoint) {
            var transition = breakpoint.transition
            if (transition === null) {
                // the transition reference was lost over the restart of Eclipse.
                // use name hash to recover it.
                val hash = breakpoint.marker.attributes.get(MODEL_ELEMENT) as Integer
                val allStates = model.rootStates.map[allStates].map[toList].flatten
                val allTransitions = allStates.map[outgoingTransitions].map[toList].flatten
                transition = allTransitions.findFirst[DebugAnnotations.getFullNameHash(it) == hash]
                // Can't find the matching transition; this bp belongs to another model.
                if (transition === null) {
                    return false
                }
                breakpoint.setTransition(transition)
            }
            if (transition.root != model) {
                // The transition does not belong to this model.
                return false
            }
            if (breakpoint instanceof TransitionCheckBreakpoint) {
                if (!transitionsWithCheckBreakpoint.contains(transition)) {
                    // There are no breakpoints registered for this transition yet
                    transitionsWithCheckBreakpoint.add(transition)
                    transitionToCheckBreakpoint.put(transition, newLinkedList(breakpoint))
                    highlighter.addCheckBreakpointDecorator(transition)
                } else {
                    // else, check whether this one is registered and if not, add it to the list
                    val breakpoints = transitionToCheckBreakpoint.get(transition)
                    if (breakpoints !== null && !breakpoints.contains(breakpoint)) {
                        // null check required to catch the initialization case
                        breakpoints.add(breakpoint)
                    }
                }
            } else if (breakpoint instanceof TransitionTakenBreakpoint) {
                if (!transitionsWithBreakpoint.contains(transition)) {
                    // There are no breakpoints registered for this transition yet
                    transitionsWithBreakpoint.add(transition)
                    transitionToBreakpoint.put(transition, newLinkedList(breakpoint))
                    highlighter.addBreakpointDecorator(transition)
                } else {
                    // else, check whether this one is registered and if not, add it to the list
                    val breakpoints = transitionToBreakpoint.get(transition)
                    if (breakpoints !== null && !breakpoints.contains(breakpoint)) {
                        // null check required to catch the initialization case
                        breakpoints.add(breakpoint)
                    }
                }
            } else if (breakpoint instanceof TransitionWatchBreakpoint) {
                val state = breakpoint.transition.sourceState
                if (!stateToWatchBreakpoints.containsKey(state)) {
                    stateToWatchBreakpoints.put(state, newLinkedList(breakpoint))
                } else {
                    stateToWatchBreakpoints.get(state).add(breakpoint)
                }
            }
        } else if (breakpoint instanceof StateBreakpoint) {
            breakpoint.enabled = true
            var state = breakpoint.state
            if (state === null) {
                // the state reference was lost over the restart of Eclipse.
                // use name hash to recover it.
                val hash = breakpoint.marker.attributes.get(MODEL_ELEMENT) as Integer
                val allStates = model.rootStates.map[allStates].map[toList].flatten
                state = allStates.findFirst[DebugAnnotations.getFullNameHash(it as State) == hash]
                // Can't find the matching state; this bp belongs to another model.
                if (state === null) {
                    return false
                }
                breakpoint.setState(state)
            }
            if (!statesWithBreakpoint.contains(state)) {
                // There are no breakpoints registered for this state yet
                statesWithBreakpoint.add(state)
                stateToBreakpoint.put(state, newLinkedList(breakpoint))
                highlighter.addBreakpointDecorator(state)
                
            } else {
                // else, check whether this one is registered and if not, add it to the list
                val breakpoints = stateToBreakpoint.get(state)
                if (breakpoints !== null && !breakpoints.contains(breakpoint)) {
                    // null check required to catch the initialization case
                    breakpoints.add(breakpoint)   
                }
            }
        }
        return true
    }
    
    /**
     * Reapplies all breakpoint decorators currently registered.
     * This is used after the model has been reloaded in the view without any changes to the breakpoints list.
     */
    def reAddBreakpointDecorators() {
        for (state : statesWithBreakpoint) {
            highlighter.addBreakpointDecorator(state)
        }
        for (transition : transitionsWithBreakpoint) {
            highlighter.addBreakpointDecorator(transition)
        }
        for (transition : transitionsWithCheckBreakpoint) {
            highlighter.addCheckBreakpointDecorator(transition)
        }
    }
    
    /**
     * Disables all java breakpoints associated with this state.
     * This is used to ensure that the same state only suspends once
     * until it is left and re-entered-
     */
    def disableStateBreakpoints(StateBreakpoint breakpoint) {
        val state = breakpoint.state
        for (bp : stateToBreakpoint.get(state)) {
            bp.enabled = false
        }
    }
    
    /**
     * Re-enables all breakpoints associated with the state.
     */
    def watchBreakpointHit(TransitionWatchBreakpoint breakpoint) {
        // TODO may make more sense to save the state, not the transition here.
        val state = breakpoint.transition.sourceState
        if (statesWithBreakpoint.contains(state)) {
            for (bp : stateToBreakpoint.get(state)) {
                bp.enabled = true
            }
        }
    }
    
    /**
     * Saves the breakpoint and associated thread to apply the highlighting after the editor has been opened
     */
    def breakpointHit(IJavaThread thread, IJavaBreakpoint breakpoint) {
        lastBreakpoint = breakpoint
        lastThread = thread
    }
    
    /**
     * Returns and clears the last breakpoint.
     */
    def getLastBreakpoint() {
        val bp = lastBreakpoint
        lastBreakpoint = null
        return bp        
    }
    
    /**
     * Returns and clears the last thread.
     */
    def getLastThread() {
        val t = lastThread
        lastThread = null
        return t
    }
    
    /**
     * Helper method to create a breakpoint of the given type on the given line.
     * Either one of @code{state} or @code{transition} must be non-null.
     */
    def IJavaBreakpoint createBreakpointOnLine(int line, BreakpointType breakpointType, State state, Transition transition) {
        
        // Retrieve resource and associated type
        val editor = activeEditor
        val resource = editor.editorInput.getAdapter(IResource)
        val typeRoot = JavaUI.getEditorInputTypeRoot(editor.editorInput)
        val compilationUnit = (typeRoot.getAdapter(ICompilationUnit) as ICompilationUnit)
        val type = compilationUnit.types.head
        
        /*
         * -1, -1: No associated start / end characters
         * 0: Always suspend (after 0 hits)
         * true: Register in breakpoint manager
         */
        val attributes = <String, Object> newHashMap

        attributes.put(IBreakpoint.ENABLED, true) // Start with an enabled breakpoint
        attributes.put(IBreakpoint.PERSISTED, true) // conserve this breakpoint across Eclipse restarts
        attributes.put(IMarker.LINE_NUMBER, line) // associated line number for the marker
        var IJavaBreakpoint breakpoint
         
        switch(breakpointType) {
            case TRANSITION_CHECK_BREAKPOINT:
                {
                    // Add transition name hash for mapping after restart
                    attributes.put(MODEL_ELEMENT, DebugAnnotations.getFullNameHash(transition))
                    breakpoint = new TransitionCheckBreakpoint(resource, type.fullyQualifiedName, line + 1, -1, -1, 0, true, attributes, transition)
                } 
            case STATE_BREAKPOINT:
                {
                    // Add state name hash for mapping after restart
                    attributes.put(MODEL_ELEMENT, DebugAnnotations.getFullNameHash(state))
                    breakpoint =  new StateBreakpoint(resource, type.fullyQualifiedName, line + 1, -1, -1, 0, true, attributes, state)
                }
            case TRANSITION_TAKEN_BREAKPOINT:
                {
                    // Add transition name hash for mapping after restart
                    attributes.put(MODEL_ELEMENT, DebugAnnotations.getFullNameHash(transition))
                    breakpoint = new TransitionTakenBreakpoint(resource, type.fullyQualifiedName, line + 1, -1, -1, 0, true, attributes, transition)
                }
            case TRANSITION_WATCH_BREAKPOINT:
                {
                    // Add transition name hash for mapping after restart
                    attributes.put(MODEL_ELEMENT, DebugAnnotations.getFullNameHash(transition))
                    breakpoint = new TransitionWatchBreakpoint(resource, type.fullyQualifiedName, line + 1, -1, -1, 0, true, attributes, transition)
                }
        } 
        
        // Update editor to display new breakpoints
        // TODO may be unnecessary
        Display.^default.asyncExec(new Runnable() {
            override run() {
                Display.^default.update
            }
        })
       
        return breakpoint
    }
    
    /**
     * Find all lines where check breakpoints for the given transition need to be set.
     * For simple transition, this will just be one, however more complex ones
     * (e.g. hierarchical abort) will require multiple breakpoints
     */
    private def findTransitionCheckLines(Transition transition) {
        
        val editor = getActiveEditor
        val transitionLines = <Integer> newLinkedList
        val document = editor.documentProvider.getDocument(editor.editorInput)
        val nameHash = DebugAnnotations.getFullNameHash(transition)
        
        // Use Regex to find transition marker comment format
        val matcher = Pattern.compile("// Transition [a-zA-Z]+ \\(Priority \\d+\\) -> [a-zA-Z]+ \\(" + nameHash + "\\)")
            .matcher(document.get)
        
        // Record all lines with matching comment in them
        while(matcher.find) {
            transitionLines.add(document.getLineOfOffset(matcher.start))
        }
        
        return transitionLines
    }
    
    /**
     * Find all lines where breakpoints for the given transition need to be set.
     * For simple transition, this will just be one, however more complex ones
     * (e.g. hierarchical abort) will require multiple breakpoints
     */
    private def findTransitionLines(Transition transition) {
        val editor = getActiveEditor
        val transitionLines = <Integer> newLinkedList
        val document = editor.documentProvider.getDocument(editor.editorInput)
        val nameHash = DebugAnnotations.getFullNameHash(transition)
        
        // Use Regex to find transition marker comment format
        val matcher = Pattern.compile("// Transition [a-zA-Z]+ \\(Priority \\d+\\) -> [a-zA-Z]+ \\(" + nameHash + "\\)")
            .matcher(document.get)
        
        // Record all lines with matching comment in them
        while(matcher.find) {
            transitionLines.add(document.getLineOfOffset(matcher.start) + 1)
        }
        
        return transitionLines
    }
    
    /**
     * Find all lines where breakpoints for the given state need to be set.
     * Due to transformations adding more states (e.g. entry / exit actions), 
     * there are generally multiple breakpoints required per original model state. 
     */
    private def findStateLines(State state) {
        
        val editor = getActiveEditor
        val stateLines = <Integer> newLinkedList
        val document = editor.documentProvider.getDocument(editor.editorInput)
        val nameHash = DebugAnnotations.getFullNameHash(state)
        
        // Use Regex to find state marker comment format
        val matcher = Pattern.compile("\\* State [a-zA-Z0-9]+ \\(" + nameHash + "\\)")
            .matcher(document.get)
            
        while(matcher.find) {
            // Comment format always has a closing comment marker in the next line.
            // After that comes the method signature and the breakpoint needs to be set on the line after that,
            // thus matching line + 3.
            stateLines.add(document.getLineOfOffset(matcher.start) + 3)
        }    
        
        return stateLines
    }
    
    /**
     * Clears all breakpoints for the associated model.
     */
    def forgetAllBreakpoints() {
        transitionToBreakpoint.clear
        transitionToCheckBreakpoint.clear
        stateToBreakpoint.clear
        stateToWatchBreakpoints.clear
        statesWithBreakpoint.clear
        transitionsWithBreakpoint.clear
        transitionsWithCheckBreakpoint.clear
    }
    
    /**
     * Clears all regular breakpoints for the given transition. 
     */
    private def clearBreakpoints(Transition transition) {
        for (breakpoint : transitionToBreakpoint.get(transition)) {
            breakpoint.delete
        }
    }
    
    /**
     * Clears all check breakpoints for the given transition.
     */
    private def clearCheckBreakpoints(Transition transition) {
        for (breakpoint : transitionToCheckBreakpoint.get(transition)) {
            breakpoint.delete
        }
    }
    
    /**
     * Clears all breakpoints for the given state.
     */
    private def clearBreakpoints(State state) {
        
        // remove state breakpoints
        for (breakpoint : stateToBreakpoint.get(state)) {
            breakpoint.delete
        }
        
        // remove associated transition watch breakpoints
        for (breakpoint : stateToWatchBreakpoints.get(state)) {
            breakpoint.delete
        }
    }
    
    def setHighlighter(DebugHighlighter highlighter) {
        this.highlighter = highlighter
    }
    
    /**
     * Helper method to retrieve the active editor
     */
    private def getActiveEditor() {
        // TODO why not do this in DebugBreakpointManager?
        val ITextEditor[] editorArr = newArrayOfSize(1)

        Display.^default.syncExec(new Runnable() {
            override run() {
                editorArr.set(0, PlatformUI.workbench?.activeWorkbenchWindow?.activePage?.activeEditor as ITextEditor)
            }
        })

        return editorArr.head
    }
    
}