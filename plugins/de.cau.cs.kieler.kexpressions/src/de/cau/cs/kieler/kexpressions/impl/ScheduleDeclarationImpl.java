/**
 * generated with EMF ecore
 */
package de.cau.cs.kieler.kexpressions.impl;

import de.cau.cs.kieler.kexpressions.KExpressionsPackage;
import de.cau.cs.kieler.kexpressions.PriorityProtocol;
import de.cau.cs.kieler.kexpressions.ScheduleDeclaration;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Schedule Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.kexpressions.impl.ScheduleDeclarationImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.impl.ScheduleDeclarationImpl#getGlobal <em>Global</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.impl.ScheduleDeclarationImpl#getPriorities <em>Priorities</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ScheduleDeclarationImpl extends DeclarationImpl implements ScheduleDeclaration {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getGlobal() <em>Global</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGlobal()
     * @generated
     * @ordered
     */
    protected static final PriorityProtocol GLOBAL_EDEFAULT = PriorityProtocol.CONFLICT;

    /**
     * The cached value of the '{@link #getGlobal() <em>Global</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGlobal()
     * @generated
     * @ordered
     */
    protected PriorityProtocol global = GLOBAL_EDEFAULT;

    /**
     * The cached value of the '{@link #getPriorities() <em>Priorities</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPriorities()
     * @generated
     * @ordered
     */
    protected EList<PriorityProtocol> priorities;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ScheduleDeclarationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return KExpressionsPackage.Literals.SCHEDULE_DECLARATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, KExpressionsPackage.SCHEDULE_DECLARATION__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public PriorityProtocol getGlobal() {
        return global;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setGlobal(PriorityProtocol newGlobal) {
        PriorityProtocol oldGlobal = global;
        global = newGlobal == null ? GLOBAL_EDEFAULT : newGlobal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, KExpressionsPackage.SCHEDULE_DECLARATION__GLOBAL, oldGlobal, global));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<PriorityProtocol> getPriorities() {
        if (priorities == null) {
            priorities = new EDataTypeEList<PriorityProtocol>(PriorityProtocol.class, this, KExpressionsPackage.SCHEDULE_DECLARATION__PRIORITIES);
        }
        return priorities;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case KExpressionsPackage.SCHEDULE_DECLARATION__NAME:
                return getName();
            case KExpressionsPackage.SCHEDULE_DECLARATION__GLOBAL:
                return getGlobal();
            case KExpressionsPackage.SCHEDULE_DECLARATION__PRIORITIES:
                return getPriorities();
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
            case KExpressionsPackage.SCHEDULE_DECLARATION__NAME:
                setName((String)newValue);
                return;
            case KExpressionsPackage.SCHEDULE_DECLARATION__GLOBAL:
                setGlobal((PriorityProtocol)newValue);
                return;
            case KExpressionsPackage.SCHEDULE_DECLARATION__PRIORITIES:
                getPriorities().clear();
                getPriorities().addAll((Collection<? extends PriorityProtocol>)newValue);
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
            case KExpressionsPackage.SCHEDULE_DECLARATION__NAME:
                setName(NAME_EDEFAULT);
                return;
            case KExpressionsPackage.SCHEDULE_DECLARATION__GLOBAL:
                setGlobal(GLOBAL_EDEFAULT);
                return;
            case KExpressionsPackage.SCHEDULE_DECLARATION__PRIORITIES:
                getPriorities().clear();
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
            case KExpressionsPackage.SCHEDULE_DECLARATION__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case KExpressionsPackage.SCHEDULE_DECLARATION__GLOBAL:
                return global != GLOBAL_EDEFAULT;
            case KExpressionsPackage.SCHEDULE_DECLARATION__PRIORITIES:
                return priorities != null && !priorities.isEmpty();
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
        result.append(" (name: ");
        result.append(name);
        result.append(", global: ");
        result.append(global);
        result.append(", priorities: ");
        result.append(priorities);
        result.append(')');
        return result.toString();
    }

} //ScheduleDeclarationImpl
