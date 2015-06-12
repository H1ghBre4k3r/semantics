/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.cau.cs.kieler.core.kexpressions.impl;

import de.cau.cs.kieler.core.kexpressions.Expression;
import de.cau.cs.kieler.core.kexpressions.KExpressionsPackage;
import de.cau.cs.kieler.core.kexpressions.ValuedObject;
import de.cau.cs.kieler.core.kexpressions.ValuedObjectReference;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Valued Object Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.cau.cs.kieler.core.kexpressions.impl.ValuedObjectReferenceImpl#getValuedObject <em>Valued Object</em>}</li>
 *   <li>{@link de.cau.cs.kieler.core.kexpressions.impl.ValuedObjectReferenceImpl#getIndices <em>Indices</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ValuedObjectReferenceImpl extends ExpressionImpl implements ValuedObjectReference {
    /**
	 * The cached value of the '{@link #getValuedObject() <em>Valued Object</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getValuedObject()
	 * @generated
	 * @ordered
	 */
    protected ValuedObject valuedObject;

    /**
	 * The cached value of the '{@link #getIndices() <em>Indices</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getIndices()
	 * @generated
	 * @ordered
	 */
    protected EList<Expression> indices;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected ValuedObjectReferenceImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return KExpressionsPackage.Literals.VALUED_OBJECT_REFERENCE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ValuedObject getValuedObject() {
		if (valuedObject != null && valuedObject.eIsProxy()) {
			InternalEObject oldValuedObject = (InternalEObject)valuedObject;
			valuedObject = (ValuedObject)eResolveProxy(oldValuedObject);
			if (valuedObject != oldValuedObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, KExpressionsPackage.VALUED_OBJECT_REFERENCE__VALUED_OBJECT, oldValuedObject, valuedObject));
			}
		}
		return valuedObject;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ValuedObject basicGetValuedObject() {
		return valuedObject;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setValuedObject(ValuedObject newValuedObject) {
		ValuedObject oldValuedObject = valuedObject;
		valuedObject = newValuedObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KExpressionsPackage.VALUED_OBJECT_REFERENCE__VALUED_OBJECT, oldValuedObject, valuedObject));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<Expression> getIndices() {
		if (indices == null) {
			indices = new EObjectContainmentEList<Expression>(Expression.class, this, KExpressionsPackage.VALUED_OBJECT_REFERENCE__INDICES);
		}
		return indices;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case KExpressionsPackage.VALUED_OBJECT_REFERENCE__INDICES:
				return ((InternalEList<?>)getIndices()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case KExpressionsPackage.VALUED_OBJECT_REFERENCE__VALUED_OBJECT:
				if (resolve) return getValuedObject();
				return basicGetValuedObject();
			case KExpressionsPackage.VALUED_OBJECT_REFERENCE__INDICES:
				return getIndices();
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
			case KExpressionsPackage.VALUED_OBJECT_REFERENCE__VALUED_OBJECT:
				setValuedObject((ValuedObject)newValue);
				return;
			case KExpressionsPackage.VALUED_OBJECT_REFERENCE__INDICES:
				getIndices().clear();
				getIndices().addAll((Collection<? extends Expression>)newValue);
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
			case KExpressionsPackage.VALUED_OBJECT_REFERENCE__VALUED_OBJECT:
				setValuedObject((ValuedObject)null);
				return;
			case KExpressionsPackage.VALUED_OBJECT_REFERENCE__INDICES:
				getIndices().clear();
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
			case KExpressionsPackage.VALUED_OBJECT_REFERENCE__VALUED_OBJECT:
				return valuedObject != null;
			case KExpressionsPackage.VALUED_OBJECT_REFERENCE__INDICES:
				return indices != null && !indices.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ValuedObjectReferenceImpl
