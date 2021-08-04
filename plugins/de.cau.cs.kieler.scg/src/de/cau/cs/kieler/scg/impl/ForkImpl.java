/**
 */
package de.cau.cs.kieler.scg.impl;

import de.cau.cs.kieler.scg.ControlFlow;
import de.cau.cs.kieler.scg.Fork;
import de.cau.cs.kieler.scg.ForkType;
import de.cau.cs.kieler.scg.Join;
import de.cau.cs.kieler.scg.ScgPackage;

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
 * An implementation of the model object '<em><b>Fork</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.scg.impl.ForkImpl#getJoin <em>Join</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.ForkImpl#getNext <em>Next</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.ForkImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ForkImpl extends NodeImpl implements Fork {
    /**
     * The cached value of the '{@link #getJoin() <em>Join</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJoin()
     * @generated
     * @ordered
     */
    protected Join join;

    /**
     * The cached value of the '{@link #getNext() <em>Next</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNext()
     * @generated
     * @ordered
     */
    protected EList<ControlFlow> next;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final ForkType TYPE_EDEFAULT = ForkType.PARALLEL;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected ForkType type = TYPE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ForkImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ScgPackage.Literals.FORK;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Join getJoin() {
        if (join != null && join.eIsProxy()) {
            InternalEObject oldJoin = (InternalEObject)join;
            join = (Join)eResolveProxy(oldJoin);
            if (join != oldJoin) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ScgPackage.FORK__JOIN, oldJoin, join));
            }
        }
        return join;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Join basicGetJoin() {
        return join;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetJoin(Join newJoin, NotificationChain msgs) {
        Join oldJoin = join;
        join = newJoin;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScgPackage.FORK__JOIN, oldJoin, newJoin);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setJoin(Join newJoin) {
        if (newJoin != join) {
            NotificationChain msgs = null;
            if (join != null)
                msgs = ((InternalEObject)join).eInverseRemove(this, ScgPackage.JOIN__FORK, Join.class, msgs);
            if (newJoin != null)
                msgs = ((InternalEObject)newJoin).eInverseAdd(this, ScgPackage.JOIN__FORK, Join.class, msgs);
            msgs = basicSetJoin(newJoin, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ScgPackage.FORK__JOIN, newJoin, newJoin));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ControlFlow> getNext() {
        if (next == null) {
            next = new EObjectContainmentEList<ControlFlow>(ControlFlow.class, this, ScgPackage.FORK__NEXT);
        }
        return next;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ForkType getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setType(ForkType newType) {
        ForkType oldType = type;
        type = newType == null ? TYPE_EDEFAULT : newType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ScgPackage.FORK__TYPE, oldType, type));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ScgPackage.FORK__JOIN:
                if (join != null)
                    msgs = ((InternalEObject)join).eInverseRemove(this, ScgPackage.JOIN__FORK, Join.class, msgs);
                return basicSetJoin((Join)otherEnd, msgs);
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
            case ScgPackage.FORK__JOIN:
                return basicSetJoin(null, msgs);
            case ScgPackage.FORK__NEXT:
                return ((InternalEList<?>)getNext()).basicRemove(otherEnd, msgs);
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
            case ScgPackage.FORK__JOIN:
                if (resolve) return getJoin();
                return basicGetJoin();
            case ScgPackage.FORK__NEXT:
                return getNext();
            case ScgPackage.FORK__TYPE:
                return getType();
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
            case ScgPackage.FORK__JOIN:
                setJoin((Join)newValue);
                return;
            case ScgPackage.FORK__NEXT:
                getNext().clear();
                getNext().addAll((Collection<? extends ControlFlow>)newValue);
                return;
            case ScgPackage.FORK__TYPE:
                setType((ForkType)newValue);
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
            case ScgPackage.FORK__JOIN:
                setJoin((Join)null);
                return;
            case ScgPackage.FORK__NEXT:
                getNext().clear();
                return;
            case ScgPackage.FORK__TYPE:
                setType(TYPE_EDEFAULT);
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
            case ScgPackage.FORK__JOIN:
                return join != null;
            case ScgPackage.FORK__NEXT:
                return next != null && !next.isEmpty();
            case ScgPackage.FORK__TYPE:
                return type != TYPE_EDEFAULT;
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
        result.append(" (type: ");
        result.append(type);
        result.append(')');
        return result.toString();
    }

} //ForkImpl
