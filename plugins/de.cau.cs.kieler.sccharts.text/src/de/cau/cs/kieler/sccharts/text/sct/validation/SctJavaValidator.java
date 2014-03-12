/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 *
 * Copyright 2010 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 *
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sccharts.text.sct.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.xtext.validation.Check;




import org.eclipse.emf.ecore.EPackage;

import de.cau.cs.kieler.core.model.validation.CustomEValidator;
import de.cau.cs.kieler.sccharts.Region;
import de.cau.cs.kieler.sccharts.SCChartsPackage;
import de.cau.cs.kieler.sccharts.Transition;
import de.cau.cs.kieler.sccharts.TransitionType;
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension;

/**
 * SCCharts Validation Rules
 * 
 * @author cmot
 * @kieler.design 2014-01-20 proposed 
 * @kieler.rating 2014-01-20 proposed yellow
 */
public class SctJavaValidator extends AbstractSctJavaValidator implements
        CustomEValidator {

    public static final String BAD_ID = "bad_id";

    public static final String MISSING_PRIO = "missing_prio";
    public static final String NON_UNIQUE_PRIO = "non_unique_prio";

    public static final String NON_SUCCEEDING_PRIOS = "non_succeeding_prios";
    public static final String NO_PRIO_1_TRANSITION = "no_prio_1_transition";
    public static final String UNSORTED_PRIOS = "unsorted_prios";
    
    public static final String REGION_NO_INITIAL_STATE = "Every region must have an initial state";
    public static final String REGION_TWO_MANY_INITIAL_STATES = "Every region must not have more than one initial state";
    public static final String REGION_NO_FINAL_STATE = "Every region should have a final state whenever its parent state has a termination transition";
    public static final String STATE_NOT_REACHABLE = "The state is not reachable";
    
    public static final SCChartsExtension sCChartExtension = new SCChartsExtension();

    @Override
    protected List<EPackage> getEPackages() {
        List<EPackage> result = new ArrayList<EPackage>();
         result.add(de.cau.cs.kieler.sccharts.SCChartsPackage.eINSTANCE);
        return result;
    }

    
    // -------------------------------------------------------------------------
    
    /**
     * Check if there is exactly ONE initial state per region.
     *
     * @param region the region
     */
    @Check
    public void checkInitialState(final de.cau.cs.kieler.sccharts.Region region) {
        // Do not consider the root region == SCChart
        if (region.getParentState() != null) {
            // check if parent state has declard any REAL region not only a
            // dummy region for entry/during/exit actions or suspends
            de.cau.cs.kieler.sccharts.State parentState = region.getParentState();
            int foundInitial = 0;
            if ((parentState.getLocalActions().size() > 0) && (parentState.getRegions().size() == 1)
                    && parentState.getRegions().get(0).getStates().size() == 0
                    && parentState.getRegions().get(0).getId().equals("")) {
                foundInitial = 1;
            }
            for (de.cau.cs.kieler.sccharts.State state : region.getStates()) {
                if (state.isInitial()) {
                    foundInitial++;
                }
            }
            if (foundInitial == 0) {
                error(REGION_NO_INITIAL_STATE, region, null, -1);
            } else if (foundInitial > 1) {
                error(REGION_TWO_MANY_INITIAL_STATES, region, null, -1);
            }
        }
    }

    // -------------------------------------------------------------------------

    /**
     * A state with a termination transition should have final states in all its
     * inner regions.
     *
     * @param state the state
     */
    @Check
    public void checkFinalStates(final de.cau.cs.kieler.sccharts.State state) {
        // Only consider macro states
        if (state.getRegions().size() > 0) {
            boolean foundTermination = false;
            for (Transition transition : state.getOutgoingTransitions()) {
                if (transition.getType() == TransitionType.TERMINATION) {
                    foundTermination = true;
                    break;
                }
            }
            if (foundTermination) {
                // Now test for every region
                for (Region region : state.getRegions()) {
                    boolean foundFinal = false;
                    for (de.cau.cs.kieler.sccharts.State innerState : region.getStates()) {
                        if (innerState.isFinal()) {
                            foundFinal = true;
                            break;
                        }
                    }
                    if (!foundFinal) {
                        warning(REGION_NO_FINAL_STATE, region, null, -1);
                    }
                }
            }
        }
    }

    // -------------------------------------------------------------------------

//    // Must ensure not to loop forever when having cycles in the model
//    ArrayList<de.cau.cs.kieler.sccharts.State> visited = new ArrayList<de.cau.cs.kieler.sccharts.State>();
//    
//    private boolean checkReachableStates(final de.cau.cs.kieler.sccharts.State originalState, final de.cau.cs.kieler.sccharts.State state) {
//        if (visited.contains(state)) {
//            return false;
//        }
//        visited.add(state);
//        if (state.isInitial()) {
//            return true;
//        }
//        else {
//            for (Transition transition : state.getIncomingTransitions()) {
//            		if (checkReachableStates(originalState, transition.getSourceState())) {
//                            return true;
//            		}
//            }
//        }
//        return false;
//    }

    // -------------------------------------------------------------------------

    /**
     * A state should be reachable from an initial state.
     *
     * @param state the state
     */
    @Check
    public void checkReachableStates(final de.cau.cs.kieler.sccharts.State state) {
//        visited.clear();
        if (!sCChartExtension.isStateReachable(state)) {
           warning(STATE_NOT_REACHABLE, state, null, -1);
        }
    }

    
    // -------------------------------------------------------------------------

    
    // @Check
    public void checkTypeNameStartsWithCapital(
            final de.cau.cs.kieler.sccharts.State s) {
        if (!Character.isUpperCase(s.getId().charAt(0))) {
            warning("Name should start with a capital", SCChartsPackage.eINSTANCE.getScope_Id(),
                    SCChartsPackage.SCOPE__ID, BAD_ID);
        }
    }

//    @Check
    public void checkTransitionPriorities(
            final de.cau.cs.kieler.sccharts.State s) {

        if (s.getOutgoingTransitions().isEmpty()
                || s.getOutgoingTransitions().size() == 1) {
            return;
        }

        Set<Integer> prios = new HashSet<Integer>();
        int count = s.getOutgoingTransitions().size();
        boolean subsequent = true;
        boolean startsWithOne = false;
        boolean sorted = true;

        Transition highestPrioTransition = s.getOutgoingTransitions().get(0);

        for (Transition t : s.getOutgoingTransitions()) {
            if (t.getPriority() == 1) {
                startsWithOne = true;
            }
            if (t.getPriority() == 0) {
                error("Priority must be assigned.",
                        SCChartsPackage.eINSTANCE.getTransition_Priority(),
                        SCChartsPackage.TRANSITION__PRIORITY, MISSING_PRIO);
            } else {
                if (t.getPriority() < highestPrioTransition.getPriority()) {
                    highestPrioTransition = t;
                }
                if (prios.contains(Integer.valueOf(t.getPriority()))) {
                    error("Priority is not unique.",
                            SCChartsPackage.eINSTANCE.getTransition_Priority(),
                            SCChartsPackage.TRANSITION__PRIORITY,
                            NON_UNIQUE_PRIO);
                } else {
                    if (t.getPriority() > count) {
                        subsequent = false;
                    }
                    if (s.getOutgoingTransitions().indexOf(t) + 1 != t
                            .getPriority()) {
                        sorted = false;
                    }
                }
                prios.add(Integer.valueOf(t.getPriority()));
            }
        }

        if (!startsWithOne) {
            warning("Transition priorities should start with value 1",
                    SCChartsPackage.eINSTANCE.getTransition_Priority(),
                    SCChartsPackage.STATE__OUTGOING_TRANSITIONS,
                    NO_PRIO_1_TRANSITION);
        } else {
            if (!subsequent) {
                warning("Priorities are not subsequent.",
                        SCChartsPackage.eINSTANCE.getTransition_Priority(),
                        SCChartsPackage.STATE__OUTGOING_TRANSITIONS,
                        NON_SUCCEEDING_PRIOS);
            } else {
                if (!sorted) {
                    warning("Transition are not sorted according to their priorities.",
                            SCChartsPackage.eINSTANCE.getTransition_Priority(),
                            SCChartsPackage.STATE__OUTGOING_TRANSITIONS,
                            UNSORTED_PRIOS);
                }
            }
        }
    }
}
