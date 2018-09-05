/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.cau.cs.kieler.kexpressions.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.cau.cs.kieler.kexpressions.Expression;
import de.cau.cs.kieler.kexpressions.KExpressionsPackage;
import de.cau.cs.kieler.kexpressions.ScheduleObjectReference;
import de.cau.cs.kieler.kexpressions.ValuedObject;
import de.cau.cs.kieler.kexpressions.ValuedObjectReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Valued Object Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.kexpressions.impl.ValuedObjectReferenceImpl#getSchedule <em>Schedule</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.impl.ValuedObjectReferenceImpl#getValuedObject <em>Valued Object</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.impl.ValuedObjectReferenceImpl#getIndices <em>Indices</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.impl.ValuedObjectReferenceImpl#getSubReference <em>Sub Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ValuedObjectReferenceImpl extends EObjectImpl implements ValuedObjectReference {
    /**
     * The cached value of the '{@link #getSchedule() <em>Schedule</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSchedule()
     * @generated
     * @ordered
     */
    protected EList<ScheduleObjectReference> schedule;

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
     * The cached value of the '{@link #getSubReference() <em>Sub Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSubReference()
     * @generated
     * @ordered
     */
    protected ValuedObjectReference subReference;

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
    public EList<ScheduleObjectReference> getSchedule() {
        if (schedule == null) {
            schedule = new EObjectContainmentEList<ScheduleObjectReference>(ScheduleObjectReference.class, this, KExpressionsPackage.VALUED_OBJECT_REFERENCE__SCHEDULE);
        }
        return schedule;
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
    public ValuedObjectReference getSubReference() {
        return subReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSubReference(ValuedObjectReference newSubReference, NotificationChain msgs) {
        ValuedObjectReference oldSubReference = subReference;
        subReference = newSubReference;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KExpressionsPackage.VALUED_OBJECT_REFERENCE__SUB_REFERENCE, oldSubReference, newSubReference);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSubReference(ValuedObjectReference newSubReference) {
        if (newSubReference != subReference) {
            NotificationChain msgs = null;
            if (subReference != null)
                msgs = ((InternalEObject)subReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KExpressionsPackage.VALUED_OBJECT_REFERENCE__SUB_REFERENCE, null, msgs);
            if (newSubReference != null)
                msgs = ((InternalEObject)newSubReference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KExpressionsPackage.VALUED_OBJECT_REFERENCE__SUB_REFERENCE, null, msgs);
            msgs = basicSetSubReference(newSubReference, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, KExpressionsPackage.VALUED_OBJECT_REFERENCE__SUB_REFERENCE, newSubReference, newSubReference));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__SCHEDULE:
                return ((InternalEList<?>)getSchedule()).basicRemove(otherEnd, msgs);
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__INDICES:
                return ((InternalEList<?>)getIndices()).basicRemove(otherEnd, msgs);
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__SUB_REFERENCE:
                return basicSetSubReference(null, msgs);
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
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__SCHEDULE:
                return getSchedule();
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__VALUED_OBJECT:
                if (resolve) return getValuedObject();
                return basicGetValuedObject();
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__INDICES:
                return getIndices();
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__SUB_REFERENCE:
                return getSubReference();
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
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__SCHEDULE:
                getSchedule().clear();
                getSchedule().addAll((Collection<? extends ScheduleObjectReference>)newValue);
                return;
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__VALUED_OBJECT:
                setValuedObject((ValuedObject)newValue);
                return;
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__INDICES:
                getIndices().clear();
                getIndices().addAll((Collection<? extends Expression>)newValue);
                return;
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__SUB_REFERENCE:
                setSubReference((ValuedObjectReference)newValue);
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
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__SCHEDULE:
                getSchedule().clear();
                return;
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__VALUED_OBJECT:
                setValuedObject((ValuedObject)null);
                return;
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__INDICES:
                getIndices().clear();
                return;
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__SUB_REFERENCE:
                setSubReference((ValuedObjectReference)null);
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
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__SCHEDULE:
                return schedule != null && !schedule.isEmpty();
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__VALUED_OBJECT:
                return valuedObject != null;
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__INDICES:
                return indices != null && !indices.isEmpty();
            case KExpressionsPackage.VALUED_OBJECT_REFERENCE__SUB_REFERENCE:
                return subReference != null;
        }
        return super.eIsSet(featureID);
    }
    
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer();
        result.append("ValuedObjectReferenceImpl");
        result.append("@");
        result.append(String.format("%08x", this.hashCode()));
        result.append(" ");
        if (valuedObject == null) {
            result.append("null");
        } else {
            result.append(valuedObject.getName());
        }
        return result.toString();
    }
    

} //ValuedObjectReferenceImpl
