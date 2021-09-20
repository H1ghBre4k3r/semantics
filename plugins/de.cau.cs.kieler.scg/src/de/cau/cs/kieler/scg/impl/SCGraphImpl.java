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
package de.cau.cs.kieler.scg.impl;

import de.cau.cs.kieler.annotations.AnnotationsPackage;
import de.cau.cs.kieler.annotations.Nameable;
import de.cau.cs.kieler.annotations.NamedObject;
import de.cau.cs.kieler.annotations.impl.AnnotatableImpl;
import de.cau.cs.kieler.kexpressions.Declaration;
import de.cau.cs.kieler.kexpressions.kext.DeclarationScope;
import de.cau.cs.kieler.kexpressions.kext.KExtPackage;
import de.cau.cs.kieler.scg.BasicBlock;
import de.cau.cs.kieler.scg.Guard;
import de.cau.cs.kieler.scg.Node;
import de.cau.cs.kieler.scg.SCGraph;
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
 * An implementation of the model object '<em><b>SC Graph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.scg.impl.SCGraphImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.SCGraphImpl#getDeclarations <em>Declarations</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.SCGraphImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.SCGraphImpl#getBasicBlocks <em>Basic Blocks</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.SCGraphImpl#getGuards <em>Guards</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.impl.SCGraphImpl#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SCGraphImpl extends AnnotatableImpl implements SCGraph {
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
     * The cached value of the '{@link #getDeclarations() <em>Declarations</em>}' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getDeclarations()
     * @generated
     * @ordered
     */
	protected EList<Declaration> declarations;

    /**
     * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getNodes()
     * @generated
     * @ordered
     */
	protected EList<Node> nodes;

	/**
     * The cached value of the '{@link #getBasicBlocks() <em>Basic Blocks</em>}' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getBasicBlocks()
     * @generated
     * @ordered
     */
	protected EList<BasicBlock> basicBlocks;

	/**
     * The cached value of the '{@link #getGuards() <em>Guards</em>}' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getGuards()
     * @generated
     * @ordered
     */
	protected EList<Guard> guards;

	/**
     * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getLabel()
     * @generated
     * @ordered
     */
	protected static final String LABEL_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getLabel()
     * @generated
     * @ordered
     */
	protected String label = LABEL_EDEFAULT;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected SCGraphImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return ScgPackage.Literals.SC_GRAPH;
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
            eNotify(new ENotificationImpl(this, Notification.SET, ScgPackage.SC_GRAPH__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public EList<Node> getNodes() {
        if (nodes == null) {
            nodes = new EObjectContainmentEList<Node>(Node.class, this, ScgPackage.SC_GRAPH__NODES);
        }
        return nodes;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public EList<Declaration> getDeclarations() {
        if (declarations == null) {
            declarations = new EObjectContainmentEList<Declaration>(Declaration.class, this, ScgPackage.SC_GRAPH__DECLARATIONS);
        }
        return declarations;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public EList<BasicBlock> getBasicBlocks() {
        if (basicBlocks == null) {
            basicBlocks = new EObjectContainmentEList<BasicBlock>(BasicBlock.class, this, ScgPackage.SC_GRAPH__BASIC_BLOCKS);
        }
        return basicBlocks;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public EList<Guard> getGuards() {
        if (guards == null) {
            guards = new EObjectContainmentEList<Guard>(Guard.class, this, ScgPackage.SC_GRAPH__GUARDS);
        }
        return guards;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public String getLabel() {
        return label;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public void setLabel(String newLabel) {
        String oldLabel = label;
        label = newLabel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ScgPackage.SC_GRAPH__LABEL, oldLabel, label));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ScgPackage.SC_GRAPH__DECLARATIONS:
                return ((InternalEList<?>)getDeclarations()).basicRemove(otherEnd, msgs);
            case ScgPackage.SC_GRAPH__NODES:
                return ((InternalEList<?>)getNodes()).basicRemove(otherEnd, msgs);
            case ScgPackage.SC_GRAPH__BASIC_BLOCKS:
                return ((InternalEList<?>)getBasicBlocks()).basicRemove(otherEnd, msgs);
            case ScgPackage.SC_GRAPH__GUARDS:
                return ((InternalEList<?>)getGuards()).basicRemove(otherEnd, msgs);
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
            case ScgPackage.SC_GRAPH__NAME:
                return getName();
            case ScgPackage.SC_GRAPH__DECLARATIONS:
                return getDeclarations();
            case ScgPackage.SC_GRAPH__NODES:
                return getNodes();
            case ScgPackage.SC_GRAPH__BASIC_BLOCKS:
                return getBasicBlocks();
            case ScgPackage.SC_GRAPH__GUARDS:
                return getGuards();
            case ScgPackage.SC_GRAPH__LABEL:
                return getLabel();
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
            case ScgPackage.SC_GRAPH__NAME:
                setName((String)newValue);
                return;
            case ScgPackage.SC_GRAPH__DECLARATIONS:
                getDeclarations().clear();
                getDeclarations().addAll((Collection<? extends Declaration>)newValue);
                return;
            case ScgPackage.SC_GRAPH__NODES:
                getNodes().clear();
                getNodes().addAll((Collection<? extends Node>)newValue);
                return;
            case ScgPackage.SC_GRAPH__BASIC_BLOCKS:
                getBasicBlocks().clear();
                getBasicBlocks().addAll((Collection<? extends BasicBlock>)newValue);
                return;
            case ScgPackage.SC_GRAPH__GUARDS:
                getGuards().clear();
                getGuards().addAll((Collection<? extends Guard>)newValue);
                return;
            case ScgPackage.SC_GRAPH__LABEL:
                setLabel((String)newValue);
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
            case ScgPackage.SC_GRAPH__NAME:
                setName(NAME_EDEFAULT);
                return;
            case ScgPackage.SC_GRAPH__DECLARATIONS:
                getDeclarations().clear();
                return;
            case ScgPackage.SC_GRAPH__NODES:
                getNodes().clear();
                return;
            case ScgPackage.SC_GRAPH__BASIC_BLOCKS:
                getBasicBlocks().clear();
                return;
            case ScgPackage.SC_GRAPH__GUARDS:
                getGuards().clear();
                return;
            case ScgPackage.SC_GRAPH__LABEL:
                setLabel(LABEL_EDEFAULT);
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
            case ScgPackage.SC_GRAPH__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case ScgPackage.SC_GRAPH__DECLARATIONS:
                return declarations != null && !declarations.isEmpty();
            case ScgPackage.SC_GRAPH__NODES:
                return nodes != null && !nodes.isEmpty();
            case ScgPackage.SC_GRAPH__BASIC_BLOCKS:
                return basicBlocks != null && !basicBlocks.isEmpty();
            case ScgPackage.SC_GRAPH__GUARDS:
                return guards != null && !guards.isEmpty();
            case ScgPackage.SC_GRAPH__LABEL:
                return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
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
        if (baseClass == Nameable.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == NamedObject.class) {
            switch (derivedFeatureID) {
                case ScgPackage.SC_GRAPH__NAME: return AnnotationsPackage.NAMED_OBJECT__NAME;
                default: return -1;
            }
        }
        if (baseClass == DeclarationScope.class) {
            switch (derivedFeatureID) {
                case ScgPackage.SC_GRAPH__DECLARATIONS: return KExtPackage.DECLARATION_SCOPE__DECLARATIONS;
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
        if (baseClass == Nameable.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == NamedObject.class) {
            switch (baseFeatureID) {
                case AnnotationsPackage.NAMED_OBJECT__NAME: return ScgPackage.SC_GRAPH__NAME;
                default: return -1;
            }
        }
        if (baseClass == DeclarationScope.class) {
            switch (baseFeatureID) {
                case KExtPackage.DECLARATION_SCOPE__DECLARATIONS: return ScgPackage.SC_GRAPH__DECLARATIONS;
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
        result.append(" (name: ");
        result.append(name);
        result.append(", label: ");
        result.append(label);
        result.append(')');
        return result.toString();
    }

} //SCGraphImpl
