/**
 * generated by Xtext 2.10.0
 */
package de.cau.cs.kieler.ehelp.eHelp;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.ehelp.eHelp.Table#getWidth <em>Width</em>}</li>
 *   <li>{@link de.cau.cs.kieler.ehelp.eHelp.Table#getSpacing <em>Spacing</em>}</li>
 *   <li>{@link de.cau.cs.kieler.ehelp.eHelp.Table#isStretch <em>Stretch</em>}</li>
 *   <li>{@link de.cau.cs.kieler.ehelp.eHelp.Table#getRows <em>Rows</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.ehelp.eHelp.EHelpPackage#getTable()
 * @model
 * @generated
 */
public interface Table extends Content
{
  /**
   * Returns the value of the '<em><b>Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Width</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Width</em>' attribute.
   * @see #setWidth(int)
   * @see de.cau.cs.kieler.ehelp.eHelp.EHelpPackage#getTable_Width()
   * @model
   * @generated
   */
  int getWidth();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.ehelp.eHelp.Table#getWidth <em>Width</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Width</em>' attribute.
   * @see #getWidth()
   * @generated
   */
  void setWidth(int value);

  /**
   * Returns the value of the '<em><b>Spacing</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Spacing</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Spacing</em>' attribute.
   * @see #setSpacing(int)
   * @see de.cau.cs.kieler.ehelp.eHelp.EHelpPackage#getTable_Spacing()
   * @model
   * @generated
   */
  int getSpacing();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.ehelp.eHelp.Table#getSpacing <em>Spacing</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Spacing</em>' attribute.
   * @see #getSpacing()
   * @generated
   */
  void setSpacing(int value);

  /**
   * Returns the value of the '<em><b>Stretch</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Stretch</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Stretch</em>' attribute.
   * @see #setStretch(boolean)
   * @see de.cau.cs.kieler.ehelp.eHelp.EHelpPackage#getTable_Stretch()
   * @model
   * @generated
   */
  boolean isStretch();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.ehelp.eHelp.Table#isStretch <em>Stretch</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Stretch</em>' attribute.
   * @see #isStretch()
   * @generated
   */
  void setStretch(boolean value);

  /**
   * Returns the value of the '<em><b>Rows</b></em>' containment reference list.
   * The list contents are of type {@link de.cau.cs.kieler.ehelp.eHelp.TableRow}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Rows</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Rows</em>' containment reference list.
   * @see de.cau.cs.kieler.ehelp.eHelp.EHelpPackage#getTable_Rows()
   * @model containment="true"
   * @generated
   */
  EList<TableRow> getRows();

} // Table