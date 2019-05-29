/**
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2013 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.scg.util;

import de.cau.cs.kieler.annotations.Annotatable;

import de.cau.cs.kieler.annotations.Nameable;
import de.cau.cs.kieler.annotations.NamedObject;
import de.cau.cs.kieler.annotations.Pragmatable;
import de.cau.cs.kieler.kexpressions.Schedulable;
import de.cau.cs.kieler.kexpressions.keffects.Dependency;
import de.cau.cs.kieler.kexpressions.keffects.Effect;
import de.cau.cs.kieler.kexpressions.keffects.Link;
import de.cau.cs.kieler.kexpressions.keffects.Linkable;
import de.cau.cs.kieler.kexpressions.kext.DeclarationScope;
import de.cau.cs.kieler.scg.*;
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
 * @see de.cau.cs.kieler.scg.ScgPackage
 * @generated
 */
public class ScgSwitch<T> extends Switch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static ScgPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScgSwitch() {
        if (modelPackage == null) {
            modelPackage = ScgPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param ePackage the package in question.
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
            case ScgPackage.SC_GRAPHS: {
                SCGraphs scGraphs = (SCGraphs)theEObject;
                T result = caseSCGraphs(scGraphs);
                if (result == null) result = casePragmatable(scGraphs);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.SC_GRAPH: {
                SCGraph scGraph = (SCGraph)theEObject;
                T result = caseSCGraph(scGraph);
                if (result == null) result = caseAnnotatable(scGraph);
                if (result == null) result = caseNamedObject(scGraph);
                if (result == null) result = caseDeclarationScope(scGraph);
                if (result == null) result = caseNameable(scGraph);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.NODE: {
                Node node = (Node)theEObject;
                T result = caseNode(node);
                if (result == null) result = caseAnnotatable(node);
                if (result == null) result = caseNamedObject(node);
                if (result == null) result = caseLinkable(node);
                if (result == null) result = caseNameable(node);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.CONDITIONAL: {
                Conditional conditional = (Conditional)theEObject;
                T result = caseConditional(conditional);
                if (result == null) result = caseNode(conditional);
                if (result == null) result = caseAnnotatable(conditional);
                if (result == null) result = caseNamedObject(conditional);
                if (result == null) result = caseLinkable(conditional);
                if (result == null) result = caseNameable(conditional);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.SURFACE: {
                Surface surface = (Surface)theEObject;
                T result = caseSurface(surface);
                if (result == null) result = caseNode(surface);
                if (result == null) result = caseAnnotatable(surface);
                if (result == null) result = caseNamedObject(surface);
                if (result == null) result = caseLinkable(surface);
                if (result == null) result = caseNameable(surface);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.DEPTH: {
                Depth depth = (Depth)theEObject;
                T result = caseDepth(depth);
                if (result == null) result = caseNode(depth);
                if (result == null) result = caseAnnotatable(depth);
                if (result == null) result = caseNamedObject(depth);
                if (result == null) result = caseLinkable(depth);
                if (result == null) result = caseNameable(depth);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.ASSIGNMENT: {
                Assignment assignment = (Assignment)theEObject;
                T result = caseAssignment(assignment);
                if (result == null) result = caseNode(assignment);
                if (result == null) result = caseKEffects_Assignment(assignment);
                if (result == null) result = caseNamedObject(assignment);
                if (result == null) result = caseEffect(assignment);
                if (result == null) result = caseAnnotatable(assignment);
                if (result == null) result = caseNameable(assignment);
                if (result == null) result = caseLinkable(assignment);
                if (result == null) result = caseSchedulable(assignment);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.FORK: {
                Fork fork = (Fork)theEObject;
                T result = caseFork(fork);
                if (result == null) result = caseNode(fork);
                if (result == null) result = caseAnnotatable(fork);
                if (result == null) result = caseNamedObject(fork);
                if (result == null) result = caseLinkable(fork);
                if (result == null) result = caseNameable(fork);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.JOIN: {
                Join join = (Join)theEObject;
                T result = caseJoin(join);
                if (result == null) result = caseNode(join);
                if (result == null) result = caseAnnotatable(join);
                if (result == null) result = caseNamedObject(join);
                if (result == null) result = caseLinkable(join);
                if (result == null) result = caseNameable(join);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.ENTRY: {
                Entry entry = (Entry)theEObject;
                T result = caseEntry(entry);
                if (result == null) result = caseNode(entry);
                if (result == null) result = caseAnnotatable(entry);
                if (result == null) result = caseNamedObject(entry);
                if (result == null) result = caseLinkable(entry);
                if (result == null) result = caseNameable(entry);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.EXIT: {
                Exit exit = (Exit)theEObject;
                T result = caseExit(exit);
                if (result == null) result = caseNode(exit);
                if (result == null) result = caseAnnotatable(exit);
                if (result == null) result = caseNamedObject(exit);
                if (result == null) result = caseLinkable(exit);
                if (result == null) result = caseNameable(exit);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.BASIC_BLOCK: {
                BasicBlock basicBlock = (BasicBlock)theEObject;
                T result = caseBasicBlock(basicBlock);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.SCHEDULING_BLOCK: {
                SchedulingBlock schedulingBlock = (SchedulingBlock)theEObject;
                T result = caseSchedulingBlock(schedulingBlock);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.PREDECESSOR: {
                Predecessor predecessor = (Predecessor)theEObject;
                T result = casePredecessor(predecessor);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.GUARD: {
                Guard guard = (Guard)theEObject;
                T result = caseGuard(guard);
                if (result == null) result = caseNode(guard);
                if (result == null) result = caseKEffects_Assignment(guard);
                if (result == null) result = caseNamedObject(guard);
                if (result == null) result = caseEffect(guard);
                if (result == null) result = caseAnnotatable(guard);
                if (result == null) result = caseNameable(guard);
                if (result == null) result = caseLinkable(guard);
                if (result == null) result = caseSchedulable(guard);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.CONTROL_FLOW: {
                ControlFlow controlFlow = (ControlFlow)theEObject;
                T result = caseControlFlow(controlFlow);
                if (result == null) result = caseLink(controlFlow);
                if (result == null) result = caseAnnotatable(controlFlow);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.EXPRESSION_DEPENDENCY: {
                ExpressionDependency expressionDependency = (ExpressionDependency)theEObject;
                T result = caseExpressionDependency(expressionDependency);
                if (result == null) result = caseDependency(expressionDependency);
                if (result == null) result = caseLink(expressionDependency);
                if (result == null) result = caseAnnotatable(expressionDependency);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.GUARD_DEPENDENCY: {
                GuardDependency guardDependency = (GuardDependency)theEObject;
                T result = caseGuardDependency(guardDependency);
                if (result == null) result = caseDependency(guardDependency);
                if (result == null) result = caseLink(guardDependency);
                if (result == null) result = caseAnnotatable(guardDependency);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.SCHEDULE_DEPENDENCY: {
                ScheduleDependency scheduleDependency = (ScheduleDependency)theEObject;
                T result = caseScheduleDependency(scheduleDependency);
                if (result == null) result = caseDependency(scheduleDependency);
                if (result == null) result = caseLink(scheduleDependency);
                if (result == null) result = caseAnnotatable(scheduleDependency);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ScgPackage.TICK_BOUNDARY_DEPENDENCY: {
                TickBoundaryDependency tickBoundaryDependency = (TickBoundaryDependency)theEObject;
                T result = caseTickBoundaryDependency(tickBoundaryDependency);
                if (result == null) result = caseDependency(tickBoundaryDependency);
                if (result == null) result = caseLink(tickBoundaryDependency);
                if (result == null) result = caseAnnotatable(tickBoundaryDependency);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>SC Graphs</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>SC Graphs</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSCGraphs(SCGraphs object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Node</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Node</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNode(Node object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditional(Conditional object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Surface</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Surface</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSurface(Surface object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Depth</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Depth</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDepth(Depth object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Assignment</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Assignment</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssignment(Assignment object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Fork</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Fork</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFork(Fork object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Join</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Join</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseJoin(Join object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Entry</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Entry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEntry(Entry object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Exit</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Exit</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExit(Exit object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Control Flow</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Control Flow</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseControlFlow(ControlFlow object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Basic Block</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Basic Block</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBasicBlock(BasicBlock object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Scheduling Block</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Scheduling Block</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSchedulingBlock(SchedulingBlock object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Predecessor</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Predecessor</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePredecessor(Predecessor object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Expression Dependency</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Expression Dependency</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseExpressionDependency(ExpressionDependency object) {
        return null;
    }

				/**
     * Returns the result of interpreting the object as an instance of '<em>Guard Dependency</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Guard Dependency</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseGuardDependency(GuardDependency object) {
        return null;
    }

				/**
     * Returns the result of interpreting the object as an instance of '<em>Schedule Dependency</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Schedule Dependency</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseScheduleDependency(ScheduleDependency object) {
        return null;
    }

				/**
     * Returns the result of interpreting the object as an instance of '<em>Tick Boundary Dependency</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Tick Boundary Dependency</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTickBoundaryDependency(TickBoundaryDependency object) {
        return null;
    }

                /**
     * Returns the result of interpreting the object as an instance of '<em>Pragmatable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Pragmatable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePragmatable(Pragmatable object) {
        return null;
    }

                /**
     * Returns the result of interpreting the object as an instance of '<em>Guard</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Guard</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGuard(Guard object) {
        return null;
    }

                /**
     * Returns the result of interpreting the object as an instance of '<em>Annotatable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Annotatable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAnnotatable(Annotatable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Nameable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Nameable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNameable(Nameable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Object</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Object</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedObject(NamedObject object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Declaration Scope</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Declaration Scope</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeclarationScope(DeclarationScope object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Linkable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Linkable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLinkable(Linkable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Schedulable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Schedulable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSchedulable(Schedulable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Effect</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Effect</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseEffect(Effect object) {
        return null;
    }

																/**
     * Returns the result of interpreting the object as an instance of '<em>Assignment</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Assignment</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseKEffects_Assignment(de.cau.cs.kieler.kexpressions.keffects.Assignment object) {
        return null;
    }

				/**
     * Returns the result of interpreting the object as an instance of '<em>Link</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Link</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLink(Link object) {
        return null;
    }

                                                                /**
     * Returns the result of interpreting the object as an instance of '<em>Dependency</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dependency</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDependency(Dependency object) {
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

} //ScgSwitch
