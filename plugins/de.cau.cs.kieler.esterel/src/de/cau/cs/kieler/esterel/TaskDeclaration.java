/**
 */
package de.cau.cs.kieler.esterel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.esterel.TaskDeclaration#getTasks <em>Tasks</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.esterel.EsterelPackage#getTaskDeclaration()
 * @model
 * @generated
 */
public interface TaskDeclaration extends EsterelDeclaration {
    /**
     * Returns the value of the '<em><b>Tasks</b></em>' containment reference list.
     * The list contents are of type {@link de.cau.cs.kieler.esterel.Task}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Tasks</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Tasks</em>' containment reference list.
     * @see de.cau.cs.kieler.esterel.EsterelPackage#getTaskDeclaration_Tasks()
     * @model containment="true"
     * @generated
     */
    EList<Task> getTasks();

} // TaskDeclaration