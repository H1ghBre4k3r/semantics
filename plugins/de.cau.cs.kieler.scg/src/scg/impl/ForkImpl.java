/**
 */
package scg.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import scg.Fork;
import scg.Join;
import scg.Node;
import scg.ScgPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Fork</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link scg.impl.ForkImpl#getJoin <em>Join</em>}</li>
 *   <li>{@link scg.impl.ForkImpl#getNext <em>Next</em>}</li>
 * </ul>
 * </p>
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
     * The cached value of the '{@link #getNext() <em>Next</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNext()
     * @generated
     * @ordered
     */
    protected EList<Node> next;

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
    public EList<Node> getNext() {
        if (next == null) {
            next = new EObjectResolvingEList<Node>(Node.class, this, ScgPackage.FORK__NEXT);
        }
        return next;
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
                getNext().addAll((Collection<? extends Node>)newValue);
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
        }
        return super.eIsSet(featureID);
    }

} //ForkImpl
