/**
 */
package de.cau.cs.kieler.sccharts.legacy.kexpressions.kext.impl;

import de.cau.cs.kieler.sccharts.legacy.annotations.AnnotationsPackage;

import de.cau.cs.kieler.sccharts.legacy.kexpressions.KExpressionsPackage;

import de.cau.cs.kieler.sccharts.legacy.kexpressions.keffects.KEffectsPackage;

import de.cau.cs.kieler.sccharts.legacy.kexpressions.kext.AnnotatedExpression;
import de.cau.cs.kieler.sccharts.legacy.kexpressions.kext.KExtFactory;
import de.cau.cs.kieler.sccharts.legacy.kexpressions.kext.KExtPackage;
import de.cau.cs.kieler.sccharts.legacy.kexpressions.kext.Kext;
import de.cau.cs.kieler.sccharts.legacy.kexpressions.kext.TestEntity;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class KExtPackageImpl extends EPackageImpl implements KExtPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass kextEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass testEntityEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass annotatedExpressionEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see de.cau.cs.kieler.sccharts.legacy.kexpressions.kext.KExtPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private KExtPackageImpl() {
        super(eNS_URI, KExtFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link KExtPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static KExtPackage init() {
        if (isInited) return (KExtPackage)EPackage.Registry.INSTANCE.getEPackage(KExtPackage.eNS_URI);

        // Obtain or create and register package
        KExtPackageImpl theKExtPackage = (KExtPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof KExtPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new KExtPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        KEffectsPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theKExtPackage.createPackageContents();

        // Initialize created meta-data
        theKExtPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theKExtPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(KExtPackage.eNS_URI, theKExtPackage);
        return theKExtPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getKext() {
        return kextEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getKext_Declarations() {
        return (EReference)kextEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getKext_Entities() {
        return (EReference)kextEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTestEntity() {
        return testEntityEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTestEntity_Effect() {
        return (EReference)testEntityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTestEntity_Expression() {
        return (EReference)testEntityEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAnnotatedExpression() {
        return annotatedExpressionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAnnotatedExpression_Expression() {
        return (EReference)annotatedExpressionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public KExtFactory getKExtFactory() {
        return (KExtFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        kextEClass = createEClass(KEXT);
        createEReference(kextEClass, KEXT__DECLARATIONS);
        createEReference(kextEClass, KEXT__ENTITIES);

        testEntityEClass = createEClass(TEST_ENTITY);
        createEReference(testEntityEClass, TEST_ENTITY__EFFECT);
        createEReference(testEntityEClass, TEST_ENTITY__EXPRESSION);

        annotatedExpressionEClass = createEClass(ANNOTATED_EXPRESSION);
        createEReference(annotatedExpressionEClass, ANNOTATED_EXPRESSION__EXPRESSION);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        KExpressionsPackage theKExpressionsPackage = (KExpressionsPackage)EPackage.Registry.INSTANCE.getEPackage(KExpressionsPackage.eNS_URI);
        KEffectsPackage theKEffectsPackage = (KEffectsPackage)EPackage.Registry.INSTANCE.getEPackage(KEffectsPackage.eNS_URI);
        AnnotationsPackage theAnnotationsPackage = (AnnotationsPackage)EPackage.Registry.INSTANCE.getEPackage(AnnotationsPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        annotatedExpressionEClass.getESuperTypes().add(theAnnotationsPackage.getAnnotatable());

        // Initialize classes and features; add operations and parameters
        initEClass(kextEClass, Kext.class, "Kext", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getKext_Declarations(), theKExpressionsPackage.getDeclaration(), null, "declarations", null, 0, -1, Kext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getKext_Entities(), this.getTestEntity(), null, "entities", null, 0, -1, Kext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(testEntityEClass, TestEntity.class, "TestEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getTestEntity_Effect(), theKEffectsPackage.getEffect(), null, "effect", null, 0, 1, TestEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getTestEntity_Expression(), this.getAnnotatedExpression(), null, "expression", null, 0, 1, TestEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(annotatedExpressionEClass, AnnotatedExpression.class, "AnnotatedExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAnnotatedExpression_Expression(), theKExpressionsPackage.getExpression(), null, "expression", null, 0, 1, AnnotatedExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);
    }

} //KExtPackageImpl
