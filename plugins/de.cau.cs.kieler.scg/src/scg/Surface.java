/**
 */
package scg;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Surface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link scg.Surface#getDepth <em>Depth</em>}</li>
 * </ul>
 * </p>
 *
 * @see scg.ScgPackage#getSurface()
 * @model
 * @generated
 */
public interface Surface extends Node {
    /**
     * Returns the value of the '<em><b>Depth</b></em>' reference.
     * It is bidirectional and its opposite is '{@link scg.Depth#getSurface <em>Surface</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Depth</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Depth</em>' reference.
     * @see #setDepth(Depth)
     * @see scg.ScgPackage#getSurface_Depth()
     * @see scg.Depth#getSurface
     * @model opposite="surface" required="true"
     * @generated
     */
    Depth getDepth();

    /**
     * Sets the value of the '{@link scg.Surface#getDepth <em>Depth</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Depth</em>' reference.
     * @see #getDepth()
     * @generated
     */
    void setDepth(Depth value);

} // Surface
