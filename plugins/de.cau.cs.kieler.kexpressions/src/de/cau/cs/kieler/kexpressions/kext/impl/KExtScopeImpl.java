/**
 */
package de.cau.cs.kieler.kexpressions.kext.impl;

import de.cau.cs.kieler.annotations.Annotatable;
import de.cau.cs.kieler.annotations.Annotation;
import de.cau.cs.kieler.annotations.AnnotationsPackage;

import de.cau.cs.kieler.kexpressions.Declaration;
import de.cau.cs.kieler.kexpressions.Identifiable;
import de.cau.cs.kieler.kexpressions.KExpressionsPackage;
import de.cau.cs.kieler.kexpressions.Referenceable;

import de.cau.cs.kieler.kexpressions.kext.KExtPackage;
import de.cau.cs.kieler.kexpressions.kext.KExtScope;
import de.cau.cs.kieler.kexpressions.kext.TestEntity;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scope</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.kexpressions.kext.impl.KExtScopeImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.kext.impl.KExtScopeImpl#getId <em>Id</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.kext.impl.KExtScopeImpl#getEntities <em>Entities</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.kext.impl.KExtScopeImpl#getScopes <em>Scopes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class KExtScopeImpl extends DeclarationScopeImpl implements KExtScope {
    /**
     * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAnnotations()
     * @generated
     * @ordered
     */
    protected EList<Annotation> annotations;

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * The cached value of the '{@link #getEntities() <em>Entities</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntities()
     * @generated
     * @ordered
     */
    protected EList<TestEntity> entities;

    /**
     * The cached value of the '{@link #getScopes() <em>Scopes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScopes()
     * @generated
     * @ordered
     */
    protected EList<KExtScope> scopes;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected KExtScopeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return KExtPackage.Literals.KEXT_SCOPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Annotation> getAnnotations() {
        if (annotations == null) {
            annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, KExtPackage.KEXT_SCOPE__ANNOTATIONS);
        }
        return annotations;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, KExtPackage.KEXT_SCOPE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<TestEntity> getEntities() {
        if (entities == null) {
            entities = new EObjectContainmentEList<TestEntity>(TestEntity.class, this, KExtPackage.KEXT_SCOPE__ENTITIES);
        }
        return entities;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<KExtScope> getScopes() {
        if (scopes == null) {
            scopes = new EObjectContainmentEList<KExtScope>(KExtScope.class, this, KExtPackage.KEXT_SCOPE__SCOPES);
        }
        return scopes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case KExtPackage.KEXT_SCOPE__ANNOTATIONS:
                return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
            case KExtPackage.KEXT_SCOPE__ENTITIES:
                return ((InternalEList<?>)getEntities()).basicRemove(otherEnd, msgs);
            case KExtPackage.KEXT_SCOPE__SCOPES:
                return ((InternalEList<?>)getScopes()).basicRemove(otherEnd, msgs);
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
            case KExtPackage.KEXT_SCOPE__ANNOTATIONS:
                return getAnnotations();
            case KExtPackage.KEXT_SCOPE__ID:
                return getId();
            case KExtPackage.KEXT_SCOPE__ENTITIES:
                return getEntities();
            case KExtPackage.KEXT_SCOPE__SCOPES:
                return getScopes();
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
            case KExtPackage.KEXT_SCOPE__ANNOTATIONS:
                getAnnotations().clear();
                getAnnotations().addAll((Collection<? extends Annotation>)newValue);
                return;
            case KExtPackage.KEXT_SCOPE__ID:
                setId((String)newValue);
                return;
            case KExtPackage.KEXT_SCOPE__ENTITIES:
                getEntities().clear();
                getEntities().addAll((Collection<? extends TestEntity>)newValue);
                return;
            case KExtPackage.KEXT_SCOPE__SCOPES:
                getScopes().clear();
                getScopes().addAll((Collection<? extends KExtScope>)newValue);
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
            case KExtPackage.KEXT_SCOPE__ANNOTATIONS:
                getAnnotations().clear();
                return;
            case KExtPackage.KEXT_SCOPE__ID:
                setId(ID_EDEFAULT);
                return;
            case KExtPackage.KEXT_SCOPE__ENTITIES:
                getEntities().clear();
                return;
            case KExtPackage.KEXT_SCOPE__SCOPES:
                getScopes().clear();
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
            case KExtPackage.KEXT_SCOPE__ANNOTATIONS:
                return annotations != null && !annotations.isEmpty();
            case KExtPackage.KEXT_SCOPE__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case KExtPackage.KEXT_SCOPE__ENTITIES:
                return entities != null && !entities.isEmpty();
            case KExtPackage.KEXT_SCOPE__SCOPES:
                return scopes != null && !scopes.isEmpty();
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
        if (baseClass == Annotatable.class) {
            switch (derivedFeatureID) {
                case KExtPackage.KEXT_SCOPE__ANNOTATIONS: return AnnotationsPackage.ANNOTATABLE__ANNOTATIONS;
                default: return -1;
            }
        }
        if (baseClass == Identifiable.class) {
            switch (derivedFeatureID) {
                case KExtPackage.KEXT_SCOPE__ID: return KExpressionsPackage.IDENTIFIABLE__ID;
                default: return -1;
            }
        }
        if (baseClass == Referenceable.class) {
            switch (derivedFeatureID) {
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
        if (baseClass == Annotatable.class) {
            switch (baseFeatureID) {
                case AnnotationsPackage.ANNOTATABLE__ANNOTATIONS: return KExtPackage.KEXT_SCOPE__ANNOTATIONS;
                default: return -1;
            }
        }
        if (baseClass == Identifiable.class) {
            switch (baseFeatureID) {
                case KExpressionsPackage.IDENTIFIABLE__ID: return KExtPackage.KEXT_SCOPE__ID;
                default: return -1;
            }
        }
        if (baseClass == Referenceable.class) {
            switch (baseFeatureID) {
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (id: ");
        result.append(id);
        result.append(')');
        return result.toString();
    }

} //KExtScopeImpl
