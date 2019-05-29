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
package de.cau.cs.kieler.sccharts.impl;

import de.cau.cs.kieler.sccharts.ControlflowRegion;
import de.cau.cs.kieler.sccharts.Region;
import de.cau.cs.kieler.sccharts.SCChartsPackage;
import de.cau.cs.kieler.sccharts.State;
import de.cau.cs.kieler.sccharts.Transition;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.StateImpl#getParentRegion <em>Parent Region</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.StateImpl#getRegions <em>Regions</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.StateImpl#isInitial <em>Initial</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.StateImpl#isFinal <em>Final</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.StateImpl#isViolation <em>Violation</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.StateImpl#isConnector <em>Connector</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.StateImpl#getOutgoingTransitions <em>Outgoing Transitions</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.StateImpl#getIncomingTransitions <em>Incoming Transitions</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.StateImpl#getBaseStates <em>Base States</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StateImpl extends ScopeImpl implements State {
    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static final String copyright = "KIELER - Kiel Integrated Environment for Layout Eclipse RichClient\r\n\r\nhttp://www.informatik.uni-kiel.de/rtsys/kieler/\r\n\r\nCopyright 2013 by\r\n+ Kiel University\r\n  + Department of Computer Science\r\n    + Real-Time and Embedded Systems Group\r\n\r\nThis code is provided under the terms of the Eclipse Public License (EPL).\r\nSee the file epl-v10.html for the license text.";

				/**
     * The cached value of the '{@link #getRegions() <em>Regions</em>}' containment reference list.
     * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
     * @see #getRegions()
     * @generated
     * @ordered
     */
   protected EList<Region> regions;

    /**
     * The default value of the '{@link #isInitial() <em>Initial</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isInitial()
     * @generated
     * @ordered
     */
    protected static final boolean INITIAL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isInitial() <em>Initial</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isInitial()
     * @generated
     * @ordered
     */
    protected boolean initial = INITIAL_EDEFAULT;

    /**
     * The default value of the '{@link #isFinal() <em>Final</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isFinal()
     * @generated
     * @ordered
     */
    protected static final boolean FINAL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isFinal() <em>Final</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isFinal()
     * @generated
     * @ordered
     */
    protected boolean final_ = FINAL_EDEFAULT;

    /**
     * The default value of the '{@link #isViolation() <em>Violation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isViolation()
     * @generated
     * @ordered
     */
    protected static final boolean VIOLATION_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isViolation() <em>Violation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isViolation()
     * @generated
     * @ordered
     */
    protected boolean violation = VIOLATION_EDEFAULT;

    /**
     * The default value of the '{@link #isConnector() <em>Connector</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #isConnector()
     * @generated
     * @ordered
     */
	protected static final boolean CONNECTOR_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isConnector() <em>Connector</em>}' attribute.
     * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
     * @see #isConnector()
     * @generated
     * @ordered
     */
protected boolean connector = CONNECTOR_EDEFAULT;

    /**
     * The cached value of the '{@link #getOutgoingTransitions() <em>Outgoing Transitions</em>}' containment reference list.
     * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
     * @see #getOutgoingTransitions()
     * @generated
     * @ordered
     */
   protected EList<Transition> outgoingTransitions;

    /**
     * The cached value of the '{@link #getIncomingTransitions() <em>Incoming Transitions</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncomingTransitions()
     * @generated
     * @ordered
     */
    protected EList<Transition> incomingTransitions;

    /**
     * The cached value of the '{@link #getBaseStates() <em>Base States</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBaseStates()
     * @generated
     * @ordered
     */
    protected EList<State> baseStates;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected StateImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SCChartsPackage.Literals.STATE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<Region> getRegions() {
        if (regions == null) {
            regions = new EObjectContainmentWithInverseEList<Region>(Region.class, this, SCChartsPackage.STATE__REGIONS, SCChartsPackage.REGION__PARENT_STATE);
        }
        return regions;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ControlflowRegion getParentRegion() {
        if (eContainerFeatureID() != SCChartsPackage.STATE__PARENT_REGION) return null;
        return (ControlflowRegion)eInternalContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetParentRegion(ControlflowRegion newParentRegion, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newParentRegion, SCChartsPackage.STATE__PARENT_REGION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setParentRegion(ControlflowRegion newParentRegion) {
        if (newParentRegion != eInternalContainer() || (eContainerFeatureID() != SCChartsPackage.STATE__PARENT_REGION && newParentRegion != null)) {
            if (EcoreUtil.isAncestor(this, newParentRegion))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newParentRegion != null)
                msgs = ((InternalEObject)newParentRegion).eInverseAdd(this, SCChartsPackage.CONTROLFLOW_REGION__STATES, ControlflowRegion.class, msgs);
            msgs = basicSetParentRegion(newParentRegion, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SCChartsPackage.STATE__PARENT_REGION, newParentRegion, newParentRegion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isInitial() {
        return initial;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setInitial(boolean newInitial) {
        boolean oldInitial = initial;
        initial = newInitial;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SCChartsPackage.STATE__INITIAL, oldInitial, initial));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isFinal() {
        return final_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setFinal(boolean newFinal) {
        boolean oldFinal = final_;
        final_ = newFinal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SCChartsPackage.STATE__FINAL, oldFinal, final_));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isViolation() {
        return violation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setViolation(boolean newViolation) {
        boolean oldViolation = violation;
        violation = newViolation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SCChartsPackage.STATE__VIOLATION, oldViolation, violation));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public boolean isConnector() {
        return connector;
    }

				/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public void setConnector(boolean newConnector) {
        boolean oldConnector = connector;
        connector = newConnector;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SCChartsPackage.STATE__CONNECTOR, oldConnector, connector));
    }

				/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<Transition> getOutgoingTransitions() {
        if (outgoingTransitions == null) {
            outgoingTransitions = new EObjectContainmentWithInverseEList<Transition>(Transition.class, this, SCChartsPackage.STATE__OUTGOING_TRANSITIONS, SCChartsPackage.TRANSITION__SOURCE_STATE);
        }
        return outgoingTransitions;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<Transition> getIncomingTransitions() {
        if (incomingTransitions == null) {
            incomingTransitions = new EObjectWithInverseResolvingEList<Transition>(Transition.class, this, SCChartsPackage.STATE__INCOMING_TRANSITIONS, SCChartsPackage.TRANSITION__TARGET_STATE);
        }
        return incomingTransitions;
    }

                /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<State> getBaseStates() {
        if (baseStates == null) {
            baseStates = new EObjectResolvingEList<State>(State.class, this, SCChartsPackage.STATE__BASE_STATES);
        }
        return baseStates;
    }

                /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case SCChartsPackage.STATE__PARENT_REGION:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetParentRegion((ControlflowRegion)otherEnd, msgs);
            case SCChartsPackage.STATE__REGIONS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getRegions()).basicAdd(otherEnd, msgs);
            case SCChartsPackage.STATE__OUTGOING_TRANSITIONS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingTransitions()).basicAdd(otherEnd, msgs);
            case SCChartsPackage.STATE__INCOMING_TRANSITIONS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomingTransitions()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case SCChartsPackage.STATE__PARENT_REGION:
                return basicSetParentRegion(null, msgs);
            case SCChartsPackage.STATE__REGIONS:
                return ((InternalEList<?>)getRegions()).basicRemove(otherEnd, msgs);
            case SCChartsPackage.STATE__OUTGOING_TRANSITIONS:
                return ((InternalEList<?>)getOutgoingTransitions()).basicRemove(otherEnd, msgs);
            case SCChartsPackage.STATE__INCOMING_TRANSITIONS:
                return ((InternalEList<?>)getIncomingTransitions()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
        switch (eContainerFeatureID()) {
            case SCChartsPackage.STATE__PARENT_REGION:
                return eInternalContainer().eInverseRemove(this, SCChartsPackage.CONTROLFLOW_REGION__STATES, ControlflowRegion.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case SCChartsPackage.STATE__PARENT_REGION:
                return getParentRegion();
            case SCChartsPackage.STATE__REGIONS:
                return getRegions();
            case SCChartsPackage.STATE__INITIAL:
                return isInitial();
            case SCChartsPackage.STATE__FINAL:
                return isFinal();
            case SCChartsPackage.STATE__VIOLATION:
                return isViolation();
            case SCChartsPackage.STATE__CONNECTOR:
                return isConnector();
            case SCChartsPackage.STATE__OUTGOING_TRANSITIONS:
                return getOutgoingTransitions();
            case SCChartsPackage.STATE__INCOMING_TRANSITIONS:
                return getIncomingTransitions();
            case SCChartsPackage.STATE__BASE_STATES:
                return getBaseStates();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case SCChartsPackage.STATE__PARENT_REGION:
                setParentRegion((ControlflowRegion)newValue);
                return;
            case SCChartsPackage.STATE__REGIONS:
                getRegions().clear();
                getRegions().addAll((Collection<? extends Region>)newValue);
                return;
            case SCChartsPackage.STATE__INITIAL:
                setInitial((Boolean)newValue);
                return;
            case SCChartsPackage.STATE__FINAL:
                setFinal((Boolean)newValue);
                return;
            case SCChartsPackage.STATE__VIOLATION:
                setViolation((Boolean)newValue);
                return;
            case SCChartsPackage.STATE__CONNECTOR:
                setConnector((Boolean)newValue);
                return;
            case SCChartsPackage.STATE__OUTGOING_TRANSITIONS:
                getOutgoingTransitions().clear();
                getOutgoingTransitions().addAll((Collection<? extends Transition>)newValue);
                return;
            case SCChartsPackage.STATE__INCOMING_TRANSITIONS:
                getIncomingTransitions().clear();
                getIncomingTransitions().addAll((Collection<? extends Transition>)newValue);
                return;
            case SCChartsPackage.STATE__BASE_STATES:
                getBaseStates().clear();
                getBaseStates().addAll((Collection<? extends State>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case SCChartsPackage.STATE__PARENT_REGION:
                setParentRegion((ControlflowRegion)null);
                return;
            case SCChartsPackage.STATE__REGIONS:
                getRegions().clear();
                return;
            case SCChartsPackage.STATE__INITIAL:
                setInitial(INITIAL_EDEFAULT);
                return;
            case SCChartsPackage.STATE__FINAL:
                setFinal(FINAL_EDEFAULT);
                return;
            case SCChartsPackage.STATE__VIOLATION:
                setViolation(VIOLATION_EDEFAULT);
                return;
            case SCChartsPackage.STATE__CONNECTOR:
                setConnector(CONNECTOR_EDEFAULT);
                return;
            case SCChartsPackage.STATE__OUTGOING_TRANSITIONS:
                getOutgoingTransitions().clear();
                return;
            case SCChartsPackage.STATE__INCOMING_TRANSITIONS:
                getIncomingTransitions().clear();
                return;
            case SCChartsPackage.STATE__BASE_STATES:
                getBaseStates().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case SCChartsPackage.STATE__PARENT_REGION:
                return getParentRegion() != null;
            case SCChartsPackage.STATE__REGIONS:
                return regions != null && !regions.isEmpty();
            case SCChartsPackage.STATE__INITIAL:
                return initial != INITIAL_EDEFAULT;
            case SCChartsPackage.STATE__FINAL:
                return final_ != FINAL_EDEFAULT;
            case SCChartsPackage.STATE__VIOLATION:
                return violation != VIOLATION_EDEFAULT;
            case SCChartsPackage.STATE__CONNECTOR:
                return connector != CONNECTOR_EDEFAULT;
            case SCChartsPackage.STATE__OUTGOING_TRANSITIONS:
                return outgoingTransitions != null && !outgoingTransitions.isEmpty();
            case SCChartsPackage.STATE__INCOMING_TRANSITIONS:
                return incomingTransitions != null && !incomingTransitions.isEmpty();
            case SCChartsPackage.STATE__BASE_STATES:
                return baseStates != null && !baseStates.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (initial: ");
        result.append(initial);
        result.append(", final: ");
        result.append(final_);
        result.append(", violation: ");
        result.append(violation);
        result.append(", connector: ");
        result.append(connector);
        result.append(')');
        return result.toString();
    }

} //StateImpl
