/**
 * generated by Xtext 2.10.0
 */
package de.cau.cs.kieler.ehelp.eHelp;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Keyword</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.ehelp.eHelp.Keyword#getText <em>Text</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.ehelp.eHelp.EHelpPackage#getKeyword()
 * @model
 * @generated
 */
public interface Keyword extends Content
{
  /**
   * Returns the value of the '<em><b>Text</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Text</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Text</em>' attribute.
   * @see #setText(String)
   * @see de.cau.cs.kieler.ehelp.eHelp.EHelpPackage#getKeyword_Text()
   * @model
   * @generated
   */
  String getText();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.ehelp.eHelp.Keyword#getText <em>Text</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Text</em>' attribute.
   * @see #getText()
   * @generated
   */
  void setText(String value);

} // Keyword