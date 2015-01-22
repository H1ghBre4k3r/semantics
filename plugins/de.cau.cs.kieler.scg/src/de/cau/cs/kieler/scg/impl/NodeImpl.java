/**
 */
package de.cau.cs.kieler.scg.impl;

import de.cau.cs.kieler.core.annotations.impl.AnnotatableImpl;

import de.cau.cs.kieler.scg.Dependency;
import de.cau.cs.kieler.scg.Link;
import de.cau.cs.kieler.scg.Node;
import de.cau.cs.kieler.scg.ScgPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.cau.cs.kieler.scg.impl.NodeImpl#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.NodeImpl#isIsInitial <em>Is Initial</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.NodeImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.NodeImpl#getPrioID <em>Prio ID</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.NodeImpl#getTsID <em>Ts ID</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.NodeImpl#getNodePriority <em>Node Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeImpl extends AnnotatableImpl implements Node {
    /**
     * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncoming()
     * @generated
     * @ordered
     */
    protected EList<Link> incoming;

    /**
     * The default value of the '{@link #isIsInitial() <em>Is Initial</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsInitial()
     * @generated
     * @ordered
     */
    protected static final boolean IS_INITIAL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsInitial() <em>Is Initial</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsInitial()
     * @generated
     * @ordered
     */
    protected boolean isInitial = IS_INITIAL_EDEFAULT;

    /**
     * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDependencies()
     * @generated
     * @ordered
     */
    protected EList<Dependency> dependencies;

    /**
     * The default value of the '{@link #getPrioID() <em>Prio ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrioID()
     * @generated
     * @ordered
     */
    protected static final Integer PRIO_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPrioID() <em>Prio ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrioID()
     * @generated
     * @ordered
     */
    protected Integer prioID = PRIO_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getTsID() <em>Ts ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTsID()
     * @generated
     * @ordered
     */
    protected static final Integer TS_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTsID() <em>Ts ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTsID()
     * @generated
     * @ordered
     */
    protected Integer tsID = TS_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getNodePriority() <em>Node Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNodePriority()
     * @generated
     * @ordered
     */
    protected static final Integer NODE_PRIORITY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNodePriority() <em>Node Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNodePriority()
     * @generated
     * @ordered
     */
    protected Integer nodePriority = NODE_PRIORITY_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected NodeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ScgPackage.Literals.NODE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Link> getIncoming() {
        if (incoming == null) {
            incoming = new EObjectWithInverseResolvingEList<Link>(Link.class, this, ScgPackage.NODE__INCOMING, ScgPackage.LINK__TARGET);
        }
        return incoming;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isIsInitial() {
        return isInitial;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIsInitial(boolean newIsInitial) {
        boolean oldIsInitial = isInitial;
        isInitial = newIsInitial;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ScgPackage.NODE__IS_INITIAL, oldIsInitial, isInitial));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Dependency> getDependencies() {
        if (dependencies == null) {
            dependencies = new EObjectContainmentEList<Dependency>(Dependency.class, this, ScgPackage.NODE__DEPENDENCIES);
        }
        return dependencies;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getPrioID() {
        return prioID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPrioID(Integer newPrioID) {
        Integer oldPrioID = prioID;
        prioID = newPrioID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ScgPackage.NODE__PRIO_ID, oldPrioID, prioID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getTsID() {
        return tsID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTsID(Integer newTsID) {
        Integer oldTsID = tsID;
        tsID = newTsID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ScgPackage.NODE__TS_ID, oldTsID, tsID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getNodePriority() {
        return nodePriority;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNodePriority(Integer newNodePriority) {
        Integer oldNodePriority = nodePriority;
        nodePriority = newNodePriority;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ScgPackage.NODE__NODE_PRIORITY, oldNodePriority, nodePriority));
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
            case ScgPackage.NODE__INCOMING:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncoming()).basicAdd(otherEnd, msgs);
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
            case ScgPackage.NODE__INCOMING:
                return ((InternalEList<?>)getIncoming()).basicRemove(otherEnd, msgs);
            case ScgPackage.NODE__DEPENDENCIES:
                return ((InternalEList<?>)getDependencies()).basicRemove(otherEnd, msgs);
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
            case ScgPackage.NODE__INCOMING:
                return getIncoming();
            case ScgPackage.NODE__IS_INITIAL:
                return isIsInitial();
            case ScgPackage.NODE__DEPENDENCIES:
                return getDependencies();
            case ScgPackage.NODE__PRIO_ID:
                return getPrioID();
            case ScgPackage.NODE__TS_ID:
                return getTsID();
            case ScgPackage.NODE__NODE_PRIORITY:
                return getNodePriority();
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
            case ScgPackage.NODE__INCOMING:
                getIncoming().clear();
                getIncoming().addAll((Collection<? extends Link>)newValue);
                return;
            case ScgPackage.NODE__IS_INITIAL:
                setIsInitial((Boolean)newValue);
                return;
            case ScgPackage.NODE__DEPENDENCIES:
                getDependencies().clear();
                getDependencies().addAll((Collection<? extends Dependency>)newValue);
                return;
            case ScgPackage.NODE__PRIO_ID:
                setPrioID((Integer)newValue);
                return;
            case ScgPackage.NODE__TS_ID:
                setTsID((Integer)newValue);
                return;
            case ScgPackage.NODE__NODE_PRIORITY:
                setNodePriority((Integer)newValue);
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
            case ScgPackage.NODE__INCOMING:
                getIncoming().clear();
                return;
            case ScgPackage.NODE__IS_INITIAL:
                setIsInitial(IS_INITIAL_EDEFAULT);
                return;
            case ScgPackage.NODE__DEPENDENCIES:
                getDependencies().clear();
                return;
            case ScgPackage.NODE__PRIO_ID:
                setPrioID(PRIO_ID_EDEFAULT);
                return;
            case ScgPackage.NODE__TS_ID:
                setTsID(TS_ID_EDEFAULT);
                return;
            case ScgPackage.NODE__NODE_PRIORITY:
                setNodePriority(NODE_PRIORITY_EDEFAULT);
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
            case ScgPackage.NODE__INCOMING:
                return incoming != null && !incoming.isEmpty();
            case ScgPackage.NODE__IS_INITIAL:
                return isInitial != IS_INITIAL_EDEFAULT;
            case ScgPackage.NODE__DEPENDENCIES:
                return dependencies != null && !dependencies.isEmpty();
            case ScgPackage.NODE__PRIO_ID:
                return PRIO_ID_EDEFAULT == null ? prioID != null : !PRIO_ID_EDEFAULT.equals(prioID);
            case ScgPackage.NODE__TS_ID:
                return TS_ID_EDEFAULT == null ? tsID != null : !TS_ID_EDEFAULT.equals(tsID);
            case ScgPackage.NODE__NODE_PRIORITY:
                return NODE_PRIORITY_EDEFAULT == null ? nodePriority != null : !NODE_PRIORITY_EDEFAULT.equals(nodePriority);
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
        result.append(" (isInitial: ");
        result.append(isInitial);
        result.append(", prioID: ");
        result.append(prioID);
        result.append(", tsID: ");
        result.append(tsID);
        result.append(", nodePriority: ");
        result.append(nodePriority);
        result.append(')');
        return result.toString();
    }

} //NodeImpl
