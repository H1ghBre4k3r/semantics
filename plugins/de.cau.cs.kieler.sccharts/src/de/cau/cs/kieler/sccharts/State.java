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
package de.cau.cs.kieler.sccharts;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A State is the main entity in a SyncChart. States are located in a {@link Region}.
 * A SyncChart itself is represented by a top-level root Region that contains
 * exactly one root State. Every State may contain multiple parallel Regions
 * whereas a Region contains multiple States. This allows nested State
 * machines with parallelism.
 * <p>
 * A State may be initial or final or both. An initial State is the entry State of a Region.
 * sccharts may only have one initial State per Region. A final State indcates that
 * its parent Region has terminated and nothing needs to be done in that Region
 * and its parent State may be left via a normal termination transition.
 * <p>
 * A State is also a Scope and hence may carry an interface declaration and is
 * identified by id and label.
 * <p>
 * States have a type to indicate different properties of States. See {@link StateType}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.sccharts.State#getParentRegion <em>Parent Region</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.State#getRegions <em>Regions</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.State#isInitial <em>Initial</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.State#isFinal <em>Final</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.State#isViolation <em>Violation</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.State#isConnector <em>Connector</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.State#getOutgoingTransitions <em>Outgoing Transitions</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.State#getIncomingTransitions <em>Incoming Transitions</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.State#getBaseStates <em>Base States</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.sccharts.SCChartsPackage#getState()
 * @model
 * @generated
 */
public interface State extends Scope {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "KIELER - Kiel Integrated Environment for Layout Eclipse RichClient\r\n\r\nhttp://www.informatik.uni-kiel.de/rtsys/kieler/\r\n\r\nCopyright 2013 by\r\n+ Kiel University\r\n  + Department of Computer Science\r\n    + Real-Time and Embedded Systems Group\r\n\r\nThis code is provided under the terms of the Eclipse Public License (EPL).\r\nSee the file epl-v10.html for the license text.";

    /**
     * Returns the value of the '<em><b>Parent Region</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link de.cau.cs.kieler.sccharts.ControlflowRegion#getStates <em>States</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parent Region</em>' container reference.
     * @see #setParentRegion(ControlflowRegion)
     * @see de.cau.cs.kieler.sccharts.SCChartsPackage#getState_ParentRegion()
     * @see de.cau.cs.kieler.sccharts.ControlflowRegion#getStates
     * @model opposite="states" transient="false"
     * @generated
     */
    ControlflowRegion getParentRegion();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.sccharts.State#getParentRegion <em>Parent Region</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parent Region</em>' container reference.
     * @see #getParentRegion()
     * @generated
     */
    void setParentRegion(ControlflowRegion value);

    /**
     * Returns the value of the '<em><b>Regions</b></em>' containment reference list.
     * The list contents are of type {@link de.cau.cs.kieler.sccharts.Region}.
     * It is bidirectional and its opposite is '{@link de.cau.cs.kieler.sccharts.Region#getParentState <em>Parent State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Regions</em>' containment reference list.
     * @see de.cau.cs.kieler.sccharts.SCChartsPackage#getState_Regions()
     * @see de.cau.cs.kieler.sccharts.Region#getParentState
     * @model opposite="parentState" containment="true"
     * @generated
     */
    EList<Region> getRegions();

    /**
     * Returns the value of the '<em><b>Initial</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Initial</em>' attribute.
     * @see #setInitial(boolean)
     * @see de.cau.cs.kieler.sccharts.SCChartsPackage#getState_Initial()
     * @model
     * @generated
     */
    boolean isInitial();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.sccharts.State#isInitial <em>Initial</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Initial</em>' attribute.
     * @see #isInitial()
     * @generated
     */
    void setInitial(boolean value);

    /**
     * Returns the value of the '<em><b>Final</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Final</em>' attribute.
     * @see #setFinal(boolean)
     * @see de.cau.cs.kieler.sccharts.SCChartsPackage#getState_Final()
     * @model
     * @generated
     */
    boolean isFinal();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.sccharts.State#isFinal <em>Final</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Final</em>' attribute.
     * @see #isFinal()
     * @generated
     */
    void setFinal(boolean value);

    /**
     * Returns the value of the '<em><b>Violation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Violation</em>' attribute.
     * @see #setViolation(boolean)
     * @see de.cau.cs.kieler.sccharts.SCChartsPackage#getState_Violation()
     * @model
     * @generated
     */
    boolean isViolation();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.sccharts.State#isViolation <em>Violation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Violation</em>' attribute.
     * @see #isViolation()
     * @generated
     */
    void setViolation(boolean value);

    /**
     * Returns the value of the '<em><b>Connector</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Connector</em>' attribute.
     * @see #setConnector(boolean)
     * @see de.cau.cs.kieler.sccharts.SCChartsPackage#getState_Connector()
     * @model
     * @generated
     */
    boolean isConnector();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.sccharts.State#isConnector <em>Connector</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Connector</em>' attribute.
     * @see #isConnector()
     * @generated
     */
    void setConnector(boolean value);

    /**
     * Returns the value of the '<em><b>Outgoing Transitions</b></em>' containment reference list.
     * The list contents are of type {@link de.cau.cs.kieler.sccharts.Transition}.
     * It is bidirectional and its opposite is '{@link de.cau.cs.kieler.sccharts.Transition#getSourceState <em>Source State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Outgoing Transitions</em>' containment reference list.
     * @see de.cau.cs.kieler.sccharts.SCChartsPackage#getState_OutgoingTransitions()
     * @see de.cau.cs.kieler.sccharts.Transition#getSourceState
     * @model opposite="sourceState" containment="true"
     * @generated
     */
    EList<Transition> getOutgoingTransitions();

    /**
     * Returns the value of the '<em><b>Incoming Transitions</b></em>' reference list.
     * The list contents are of type {@link de.cau.cs.kieler.sccharts.Transition}.
     * It is bidirectional and its opposite is '{@link de.cau.cs.kieler.sccharts.Transition#getTargetState <em>Target State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Incoming Transitions</em>' reference list.
     * @see de.cau.cs.kieler.sccharts.SCChartsPackage#getState_IncomingTransitions()
     * @see de.cau.cs.kieler.sccharts.Transition#getTargetState
     * @model opposite="targetState"
     * @generated
     */
    EList<Transition> getIncomingTransitions();

    /**
     * Returns the value of the '<em><b>Base States</b></em>' reference list.
     * The list contents are of type {@link de.cau.cs.kieler.sccharts.State}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Base States</em>' reference list.
     * @see de.cau.cs.kieler.sccharts.SCChartsPackage#getState_BaseStates()
     * @model
     * @generated
     */
    EList<State> getBaseStates();

} // State
