/**
 */
package de.cau.cs.kieler.scl;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.cau.cs.kieler.scl.SCLPackage
 * @generated
 */
public interface SCLFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    SCLFactory eINSTANCE = de.cau.cs.kieler.scl.impl.SCLFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Program</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Program</em>'.
     * @generated
     */
    SCLProgram createSCLProgram();

    /**
     * Returns a new object of class '<em>Module</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Module</em>'.
     * @generated
     */
    Module createModule();

    /**
     * Returns a new object of class '<em>Pause</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Pause</em>'.
     * @generated
     */
    Pause createPause();

    /**
     * Returns a new object of class '<em>Label</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Label</em>'.
     * @generated
     */
    Label createLabel();

    /**
     * Returns a new object of class '<em>Goto</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Goto</em>'.
     * @generated
     */
    Goto createGoto();

    /**
     * Returns a new object of class '<em>Assignment</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Assignment</em>'.
     * @generated
     */
    Assignment createAssignment();

    /**
     * Returns a new object of class '<em>Conditional</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Conditional</em>'.
     * @generated
     */
    Conditional createConditional();

    /**
     * Returns a new object of class '<em>Parallel</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Parallel</em>'.
     * @generated
     */
    Parallel createParallel();

    /**
     * Returns a new object of class '<em>Module Call</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Module Call</em>'.
     * @generated
     */
    ModuleCall createModuleCall();

    /**
     * Returns a new object of class '<em>Thread</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Thread</em>'.
     * @generated
     */
    Thread createThread();

    /**
     * Returns a new object of class '<em>Scope Statement</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Scope Statement</em>'.
     * @generated
     */
    ScopeStatement createScopeStatement();

    /**
     * Returns a new object of class '<em>Else Scope</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Else Scope</em>'.
     * @generated
     */
    ElseScope createElseScope();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    SCLPackage getSCLPackage();

} //SCLFactory
