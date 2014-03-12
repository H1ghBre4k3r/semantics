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
package de.cau.cs.kieler.sccharts.impl;

import de.cau.cs.kieler.sccharts.SCChartsPackage;
import de.cau.cs.kieler.sccharts.Scope;
import de.cau.cs.kieler.sccharts.Substitution;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Substitution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.SubstitutionImpl#getParentScope <em>Parent Scope</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.SubstitutionImpl#getFormal <em>Formal</em>}</li>
 *   <li>{@link de.cau.cs.kieler.sccharts.impl.SubstitutionImpl#getActual <em>Actual</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SubstitutionImpl extends EObjectImpl implements Substitution {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static final String copyright = "KIELER - Kiel Integrated Environment for Layout Eclipse RichClient\n\nhttp://www.informatik.uni-kiel.de/rtsys/kieler/\n\nCopyright 2013 by\n+ Christian-Albrechts-University of Kiel\n  + Department of Computer Science\n    + Real-Time and Embedded Systems Group\n\nThis code is provided under the terms of the Eclipse Public License (EPL).\nSee the file epl-v10.html for the license text.";

    /**
	 * The default value of the '{@link #getFormal() <em>Formal</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFormal()
	 * @generated
	 * @ordered
	 */
    protected static final String FORMAL_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getFormal() <em>Formal</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFormal()
	 * @generated
	 * @ordered
	 */
    protected String formal = FORMAL_EDEFAULT;

    /**
	 * The default value of the '{@link #getActual() <em>Actual</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getActual()
	 * @generated
	 * @ordered
	 */
    protected static final String ACTUAL_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getActual() <em>Actual</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getActual()
	 * @generated
	 * @ordered
	 */
    protected String actual = ACTUAL_EDEFAULT;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected SubstitutionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return SCChartsPackage.Literals.SUBSTITUTION;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Scope getParentScope() {
		if (eContainerFeatureID() != SCChartsPackage.SUBSTITUTION__PARENT_SCOPE) return null;
		return (Scope)eInternalContainer();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetParentScope(Scope newParentScope, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentScope, SCChartsPackage.SUBSTITUTION__PARENT_SCOPE, msgs);
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setParentScope(Scope newParentScope) {
		if (newParentScope != eInternalContainer() || (eContainerFeatureID() != SCChartsPackage.SUBSTITUTION__PARENT_SCOPE && newParentScope != null)) {
			if (EcoreUtil.isAncestor(this, newParentScope))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentScope != null)
				msgs = ((InternalEObject)newParentScope).eInverseAdd(this, SCChartsPackage.SCOPE__RENAMINGS, Scope.class, msgs);
			msgs = basicSetParentScope(newParentScope, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SCChartsPackage.SUBSTITUTION__PARENT_SCOPE, newParentScope, newParentScope));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getFormal() {
		return formal;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setFormal(String newFormal) {
		String oldFormal = formal;
		formal = newFormal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SCChartsPackage.SUBSTITUTION__FORMAL, oldFormal, formal));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getActual() {
		return actual;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setActual(String newActual) {
		String oldActual = actual;
		actual = newActual;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SCChartsPackage.SUBSTITUTION__ACTUAL, oldActual, actual));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SCChartsPackage.SUBSTITUTION__PARENT_SCOPE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentScope((Scope)otherEnd, msgs);
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
			case SCChartsPackage.SUBSTITUTION__PARENT_SCOPE:
				return basicSetParentScope(null, msgs);
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
			case SCChartsPackage.SUBSTITUTION__PARENT_SCOPE:
				return eInternalContainer().eInverseRemove(this, SCChartsPackage.SCOPE__RENAMINGS, Scope.class, msgs);
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
			case SCChartsPackage.SUBSTITUTION__PARENT_SCOPE:
				return getParentScope();
			case SCChartsPackage.SUBSTITUTION__FORMAL:
				return getFormal();
			case SCChartsPackage.SUBSTITUTION__ACTUAL:
				return getActual();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SCChartsPackage.SUBSTITUTION__PARENT_SCOPE:
				setParentScope((Scope)newValue);
				return;
			case SCChartsPackage.SUBSTITUTION__FORMAL:
				setFormal((String)newValue);
				return;
			case SCChartsPackage.SUBSTITUTION__ACTUAL:
				setActual((String)newValue);
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
			case SCChartsPackage.SUBSTITUTION__PARENT_SCOPE:
				setParentScope((Scope)null);
				return;
			case SCChartsPackage.SUBSTITUTION__FORMAL:
				setFormal(FORMAL_EDEFAULT);
				return;
			case SCChartsPackage.SUBSTITUTION__ACTUAL:
				setActual(ACTUAL_EDEFAULT);
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
			case SCChartsPackage.SUBSTITUTION__PARENT_SCOPE:
				return getParentScope() != null;
			case SCChartsPackage.SUBSTITUTION__FORMAL:
				return FORMAL_EDEFAULT == null ? formal != null : !FORMAL_EDEFAULT.equals(formal);
			case SCChartsPackage.SUBSTITUTION__ACTUAL:
				return ACTUAL_EDEFAULT == null ? actual != null : !ACTUAL_EDEFAULT.equals(actual);
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (formal: ");
		result.append(formal);
		result.append(", actual: ");
		result.append(actual);
		result.append(')');
		return result.toString();
	}

} //SubstitutionImpl
