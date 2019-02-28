/**
 */
package de.cau.cs.kieler.kexpressions.keffects.impl;

import de.cau.cs.kieler.annotations.impl.AnnotatableImpl;
import de.cau.cs.kieler.kexpressions.Call;
import de.cau.cs.kieler.kexpressions.Expression;
import de.cau.cs.kieler.kexpressions.FunctionCall;
import de.cau.cs.kieler.kexpressions.KExpressionsPackage;
import de.cau.cs.kieler.kexpressions.Parameter;

import de.cau.cs.kieler.kexpressions.Schedulable;
import de.cau.cs.kieler.kexpressions.ScheduleObjectReference;
import de.cau.cs.kieler.kexpressions.keffects.FunctionCallEffect;
import de.cau.cs.kieler.kexpressions.keffects.KEffectsPackage;

import de.cau.cs.kieler.kexpressions.keffects.Link;
import de.cau.cs.kieler.kexpressions.keffects.Linkable;
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
 * An implementation of the model object '<em><b>Function Call Effect</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.kexpressions.keffects.impl.FunctionCallEffectImpl#getSchedule <em>Schedule</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.keffects.impl.FunctionCallEffectImpl#getOutgoingLinks <em>Outgoing Links</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.keffects.impl.FunctionCallEffectImpl#getIncomingLinks <em>Incoming Links</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.keffects.impl.FunctionCallEffectImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.keffects.impl.FunctionCallEffectImpl#getFunctionName <em>Function Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FunctionCallEffectImpl extends AnnotatableImpl implements FunctionCallEffect {
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
     * The cached value of the '{@link #getOutgoingLinks() <em>Outgoing Links</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutgoingLinks()
     * @generated
     * @ordered
     */
    protected EList<Link> outgoingLinks;

    /**
     * The cached value of the '{@link #getIncomingLinks() <em>Incoming Links</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncomingLinks()
     * @generated
     * @ordered
     */
    protected EList<Link> incomingLinks;

    /**
     * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameters()
     * @generated
     * @ordered
     */
    protected EList<Parameter> parameters;

    /**
     * The default value of the '{@link #getFunctionName() <em>Function Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFunctionName()
     * @generated
     * @ordered
     */
    protected static final String FUNCTION_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFunctionName() <em>Function Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFunctionName()
     * @generated
     * @ordered
     */
    protected String functionName = FUNCTION_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FunctionCallEffectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return KEffectsPackage.Literals.FUNCTION_CALL_EFFECT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ScheduleObjectReference> getSchedule() {
        if (schedule == null) {
            schedule = new EObjectContainmentEList<ScheduleObjectReference>(ScheduleObjectReference.class, this, KEffectsPackage.FUNCTION_CALL_EFFECT__SCHEDULE);
        }
        return schedule;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Link> getOutgoingLinks() {
        if (outgoingLinks == null) {
            outgoingLinks = new EObjectContainmentEList<Link>(Link.class, this, KEffectsPackage.FUNCTION_CALL_EFFECT__OUTGOING_LINKS);
        }
        return outgoingLinks;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Link> getIncomingLinks() {
        if (incomingLinks == null) {
            incomingLinks = new EObjectWithInverseResolvingEList<Link>(Link.class, this, KEffectsPackage.FUNCTION_CALL_EFFECT__INCOMING_LINKS, KEffectsPackage.LINK__TARGET);
        }
        return incomingLinks;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFunctionName(String newFunctionName) {
        String oldFunctionName = functionName;
        functionName = newFunctionName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, KEffectsPackage.FUNCTION_CALL_EFFECT__FUNCTION_NAME, oldFunctionName, functionName));
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
            case KEffectsPackage.FUNCTION_CALL_EFFECT__INCOMING_LINKS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomingLinks()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Parameter> getParameters() {
        if (parameters == null) {
            parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, KEffectsPackage.FUNCTION_CALL_EFFECT__PARAMETERS);
        }
        return parameters;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case KEffectsPackage.FUNCTION_CALL_EFFECT__SCHEDULE:
                return ((InternalEList<?>)getSchedule()).basicRemove(otherEnd, msgs);
            case KEffectsPackage.FUNCTION_CALL_EFFECT__OUTGOING_LINKS:
                return ((InternalEList<?>)getOutgoingLinks()).basicRemove(otherEnd, msgs);
            case KEffectsPackage.FUNCTION_CALL_EFFECT__INCOMING_LINKS:
                return ((InternalEList<?>)getIncomingLinks()).basicRemove(otherEnd, msgs);
            case KEffectsPackage.FUNCTION_CALL_EFFECT__PARAMETERS:
                return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
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
            case KEffectsPackage.FUNCTION_CALL_EFFECT__SCHEDULE:
                return getSchedule();
            case KEffectsPackage.FUNCTION_CALL_EFFECT__OUTGOING_LINKS:
                return getOutgoingLinks();
            case KEffectsPackage.FUNCTION_CALL_EFFECT__INCOMING_LINKS:
                return getIncomingLinks();
            case KEffectsPackage.FUNCTION_CALL_EFFECT__PARAMETERS:
                return getParameters();
            case KEffectsPackage.FUNCTION_CALL_EFFECT__FUNCTION_NAME:
                return getFunctionName();
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
            case KEffectsPackage.FUNCTION_CALL_EFFECT__SCHEDULE:
                getSchedule().clear();
                getSchedule().addAll((Collection<? extends ScheduleObjectReference>)newValue);
                return;
            case KEffectsPackage.FUNCTION_CALL_EFFECT__OUTGOING_LINKS:
                getOutgoingLinks().clear();
                getOutgoingLinks().addAll((Collection<? extends Link>)newValue);
                return;
            case KEffectsPackage.FUNCTION_CALL_EFFECT__INCOMING_LINKS:
                getIncomingLinks().clear();
                getIncomingLinks().addAll((Collection<? extends Link>)newValue);
                return;
            case KEffectsPackage.FUNCTION_CALL_EFFECT__PARAMETERS:
                getParameters().clear();
                getParameters().addAll((Collection<? extends Parameter>)newValue);
                return;
            case KEffectsPackage.FUNCTION_CALL_EFFECT__FUNCTION_NAME:
                setFunctionName((String)newValue);
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
            case KEffectsPackage.FUNCTION_CALL_EFFECT__SCHEDULE:
                getSchedule().clear();
                return;
            case KEffectsPackage.FUNCTION_CALL_EFFECT__OUTGOING_LINKS:
                getOutgoingLinks().clear();
                return;
            case KEffectsPackage.FUNCTION_CALL_EFFECT__INCOMING_LINKS:
                getIncomingLinks().clear();
                return;
            case KEffectsPackage.FUNCTION_CALL_EFFECT__PARAMETERS:
                getParameters().clear();
                return;
            case KEffectsPackage.FUNCTION_CALL_EFFECT__FUNCTION_NAME:
                setFunctionName(FUNCTION_NAME_EDEFAULT);
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
            case KEffectsPackage.FUNCTION_CALL_EFFECT__SCHEDULE:
                return schedule != null && !schedule.isEmpty();
            case KEffectsPackage.FUNCTION_CALL_EFFECT__OUTGOING_LINKS:
                return outgoingLinks != null && !outgoingLinks.isEmpty();
            case KEffectsPackage.FUNCTION_CALL_EFFECT__INCOMING_LINKS:
                return incomingLinks != null && !incomingLinks.isEmpty();
            case KEffectsPackage.FUNCTION_CALL_EFFECT__PARAMETERS:
                return parameters != null && !parameters.isEmpty();
            case KEffectsPackage.FUNCTION_CALL_EFFECT__FUNCTION_NAME:
                return FUNCTION_NAME_EDEFAULT == null ? functionName != null : !FUNCTION_NAME_EDEFAULT.equals(functionName);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == Schedulable.class) {
            switch (derivedFeatureID) {
                case KEffectsPackage.FUNCTION_CALL_EFFECT__SCHEDULE: return KExpressionsPackage.SCHEDULABLE__SCHEDULE;
                default: return -1;
            }
        }
        if (baseClass == Linkable.class) {
            switch (derivedFeatureID) {
                case KEffectsPackage.FUNCTION_CALL_EFFECT__OUTGOING_LINKS: return KEffectsPackage.LINKABLE__OUTGOING_LINKS;
                case KEffectsPackage.FUNCTION_CALL_EFFECT__INCOMING_LINKS: return KEffectsPackage.LINKABLE__INCOMING_LINKS;
                default: return -1;
            }
        }
        if (baseClass == Expression.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == Call.class) {
            switch (derivedFeatureID) {
                case KEffectsPackage.FUNCTION_CALL_EFFECT__PARAMETERS: return KExpressionsPackage.CALL__PARAMETERS;
                default: return -1;
            }
        }
        if (baseClass == FunctionCall.class) {
            switch (derivedFeatureID) {
                case KEffectsPackage.FUNCTION_CALL_EFFECT__FUNCTION_NAME: return KExpressionsPackage.FUNCTION_CALL__FUNCTION_NAME;
                default: return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == Schedulable.class) {
            switch (baseFeatureID) {
                case KExpressionsPackage.SCHEDULABLE__SCHEDULE: return KEffectsPackage.FUNCTION_CALL_EFFECT__SCHEDULE;
                default: return -1;
            }
        }
        if (baseClass == Linkable.class) {
            switch (baseFeatureID) {
                case KEffectsPackage.LINKABLE__OUTGOING_LINKS: return KEffectsPackage.FUNCTION_CALL_EFFECT__OUTGOING_LINKS;
                case KEffectsPackage.LINKABLE__INCOMING_LINKS: return KEffectsPackage.FUNCTION_CALL_EFFECT__INCOMING_LINKS;
                default: return -1;
            }
        }
        if (baseClass == Expression.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == Call.class) {
            switch (baseFeatureID) {
                case KExpressionsPackage.CALL__PARAMETERS: return KEffectsPackage.FUNCTION_CALL_EFFECT__PARAMETERS;
                default: return -1;
            }
        }
        if (baseClass == FunctionCall.class) {
            switch (baseFeatureID) {
                case KExpressionsPackage.FUNCTION_CALL__FUNCTION_NAME: return KEffectsPackage.FUNCTION_CALL_EFFECT__FUNCTION_NAME;
                default: return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(" (functionName: ");
        result.append(functionName);
        result.append(')');
        return result.toString();
    }

} //FunctionCallEffectImpl
