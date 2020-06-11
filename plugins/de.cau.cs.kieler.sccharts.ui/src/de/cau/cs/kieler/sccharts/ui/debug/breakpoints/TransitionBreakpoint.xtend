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

import org.eclipse.jdt.internal.debug.core.breakpoints.JavaLineBreakpoint
import de.cau.cs.kieler.sccharts.Transition
import org.eclipse.core.resources.IResource
import java.util.Map
import de.cau.cs.kieler.sccharts.processors.statebased.DebugAnnotations
import org.eclipse.debug.core.DebugException

/**
 * Abstract superclass for all breakpoints placed on transitions.
 * 
 * @author peu
 */
abstract class TransitionBreakpoint extends JavaLineBreakpoint {
    
    var Transition transition
    var int transitionHash
    
    new() {
    }
    
    new(IResource resource, String typeName,
            int lineNumber, int charStart, int charEnd, int hitCount,
            boolean add, Map<String, Object> attributes, String type, Transition transition) throws DebugException {
        super(resource, typeName, lineNumber, charStart, charEnd, hitCount, add,
                attributes, type);
        this.transition = transition
        this.transitionHash = DebugAnnotations.getFullNameHash(transition)
    }
    
    def setTransition(Transition transition) {
        this.transition = transition
        this.transitionHash = DebugAnnotations.getFullNameHash(transition)
    }
    
    def getTransition() {
        return transition
    }
    
    def getTransitionHash() {
        return transitionHash
    }
}