/**
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2013 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.scgsched.util;

import de.cau.cs.kieler.scg.SCGraph;
import de.cau.cs.kieler.scgbb.SCGraphBB;
import de.cau.cs.kieler.scgdep.SCGraphDep;
import de.cau.cs.kieler.scgsched.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.cau.cs.kieler.scgsched.ScgschedPackage
 * @generated
 */
public class ScgschedSwitch<T> extends Switch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static ScgschedPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScgschedSwitch() {
        if (modelPackage == null) {
            modelPackage = ScgschedPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @parameter ePackage the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case ScgschedPackage.SC_GRAPH_SCHED: {
                SCGraphSched scGraphSched = (SCGraphSched)theEObject;
                T result = caseSCGraphSched(scGraphSched);
                if (result == null) result = caseSCGraphBB(scGraphSched);
                if (result == null) result = caseSCGraphDep(scGraphSched);
                if (result == null) result = caseSCGraph(scGraphSched);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgschedPackage.EMPTY_EXPRESSION: {
                EmptyExpression emptyExpression = (EmptyExpression)theEObject;
                T result = caseEmptyExpression(emptyExpression);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgschedPackage.GUARD_EXPRESSION: {
                GuardExpression guardExpression = (GuardExpression)theEObject;
                T result = caseGuardExpression(guardExpression);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgschedPackage.SCHEDULE: {
                Schedule schedule = (Schedule)theEObject;
                T result = caseSchedule(schedule);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgschedPackage.ANALYSIS: {
                Analysis analysis = (Analysis)theEObject;
                T result = caseAnalysis(analysis);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgschedPackage.ALTERATION: {
                Alteration alteration = (Alteration)theEObject;
                T result = caseAlteration(alteration);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgschedPackage.ASSIGNMENT_ADDITION: {
                AssignmentAddition assignmentAddition = (AssignmentAddition)theEObject;
                T result = caseAssignmentAddition(assignmentAddition);
                if (result == null) result = caseAlteration(assignmentAddition);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgschedPackage.CONDITIONAL_ADDITION: {
                ConditionalAddition conditionalAddition = (ConditionalAddition)theEObject;
                T result = caseConditionalAddition(conditionalAddition);
                if (result == null) result = caseAlteration(conditionalAddition);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>SC Graph Sched</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>SC Graph Sched</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSCGraphSched(SCGraphSched object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Empty Expression</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Empty Expression</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEmptyExpression(EmptyExpression object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Guard Expression</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Guard Expression</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseGuardExpression(GuardExpression object) {
        return null;
    }

				/**
     * Returns the result of interpreting the object as an instance of '<em>Schedule</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Schedule</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSchedule(Schedule object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Analysis</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Analysis</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseAnalysis(Analysis object) {
        return null;
    }

				/**
     * Returns the result of interpreting the object as an instance of '<em>Alteration</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Alteration</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseAlteration(Alteration object) {
        return null;
    }

				/**
     * Returns the result of interpreting the object as an instance of '<em>Assignment Addition</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Assignment Addition</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseAssignmentAddition(AssignmentAddition object) {
        return null;
    }

				/**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Addition</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Addition</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseConditionalAddition(ConditionalAddition object) {
        return null;
    }

				/**
     * Returns the result of interpreting the object as an instance of '<em>SC Graph</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>SC Graph</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSCGraph(SCGraph object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>SC Graph Dep</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>SC Graph Dep</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSCGraphDep(SCGraphDep object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>SC Graph BB</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>SC Graph BB</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSCGraphBB(SCGraphBB object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} //ScgschedSwitch
