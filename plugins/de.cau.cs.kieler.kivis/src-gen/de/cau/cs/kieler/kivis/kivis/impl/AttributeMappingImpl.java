/**
 */
package de.cau.cs.kieler.kivis.kivis.impl;

import de.cau.cs.kieler.kivis.kivis.AttributeMapping;
import de.cau.cs.kieler.kivis.kivis.KivisPackage;
import de.cau.cs.kieler.kivis.kivis.Mapping;

import de.cau.cs.kieler.prom.kibuild.Literal;

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
 * An implementation of the model object '<em><b>Attribute Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.AttributeMappingImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.AttributeMappingImpl#isCurrentValue <em>Current Value</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.AttributeMappingImpl#getLiteral <em>Literal</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.AttributeMappingImpl#getMappings <em>Mappings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeMappingImpl extends de.cau.cs.kieler.prom.kibuild.impl.AttributeMappingImpl implements AttributeMapping
{
  /**
   * The default value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAttribute()
   * @generated
   * @ordered
   */
  protected static final String ATTRIBUTE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAttribute()
   * @generated
   * @ordered
   */
  protected String attribute = ATTRIBUTE_EDEFAULT;

  /**
   * The default value of the '{@link #isCurrentValue() <em>Current Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isCurrentValue()
   * @generated
   * @ordered
   */
  protected static final boolean CURRENT_VALUE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isCurrentValue() <em>Current Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isCurrentValue()
   * @generated
   * @ordered
   */
  protected boolean currentValue = CURRENT_VALUE_EDEFAULT;

  /**
   * The cached value of the '{@link #getLiteral() <em>Literal</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLiteral()
   * @generated
   * @ordered
   */
  protected Literal literal;

  /**
   * The cached value of the '{@link #getMappings() <em>Mappings</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMappings()
   * @generated
   * @ordered
   */
  protected EList<Mapping> mappings;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AttributeMappingImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return KivisPackage.Literals.ATTRIBUTE_MAPPING;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getAttribute()
  {
    return attribute;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAttribute(String newAttribute)
  {
    String oldAttribute = attribute;
    attribute = newAttribute;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KivisPackage.ATTRIBUTE_MAPPING__ATTRIBUTE, oldAttribute, attribute));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isCurrentValue()
  {
    return currentValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCurrentValue(boolean newCurrentValue)
  {
    boolean oldCurrentValue = currentValue;
    currentValue = newCurrentValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KivisPackage.ATTRIBUTE_MAPPING__CURRENT_VALUE, oldCurrentValue, currentValue));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Literal getLiteral()
  {
    return literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLiteral(Literal newLiteral, NotificationChain msgs)
  {
    Literal oldLiteral = literal;
    literal = newLiteral;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KivisPackage.ATTRIBUTE_MAPPING__LITERAL, oldLiteral, newLiteral);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLiteral(Literal newLiteral)
  {
    if (newLiteral != literal)
    {
      NotificationChain msgs = null;
      if (literal != null)
        msgs = ((InternalEObject)literal).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KivisPackage.ATTRIBUTE_MAPPING__LITERAL, null, msgs);
      if (newLiteral != null)
        msgs = ((InternalEObject)newLiteral).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KivisPackage.ATTRIBUTE_MAPPING__LITERAL, null, msgs);
      msgs = basicSetLiteral(newLiteral, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KivisPackage.ATTRIBUTE_MAPPING__LITERAL, newLiteral, newLiteral));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Mapping> getMappings()
  {
    if (mappings == null)
    {
      mappings = new EObjectContainmentEList<Mapping>(Mapping.class, this, KivisPackage.ATTRIBUTE_MAPPING__MAPPINGS);
    }
    return mappings;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case KivisPackage.ATTRIBUTE_MAPPING__LITERAL:
        return basicSetLiteral(null, msgs);
      case KivisPackage.ATTRIBUTE_MAPPING__MAPPINGS:
        return ((InternalEList<?>)getMappings()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case KivisPackage.ATTRIBUTE_MAPPING__ATTRIBUTE:
        return getAttribute();
      case KivisPackage.ATTRIBUTE_MAPPING__CURRENT_VALUE:
        return isCurrentValue();
      case KivisPackage.ATTRIBUTE_MAPPING__LITERAL:
        return getLiteral();
      case KivisPackage.ATTRIBUTE_MAPPING__MAPPINGS:
        return getMappings();
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
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case KivisPackage.ATTRIBUTE_MAPPING__ATTRIBUTE:
        setAttribute((String)newValue);
        return;
      case KivisPackage.ATTRIBUTE_MAPPING__CURRENT_VALUE:
        setCurrentValue((Boolean)newValue);
        return;
      case KivisPackage.ATTRIBUTE_MAPPING__LITERAL:
        setLiteral((Literal)newValue);
        return;
      case KivisPackage.ATTRIBUTE_MAPPING__MAPPINGS:
        getMappings().clear();
        getMappings().addAll((Collection<? extends Mapping>)newValue);
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
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case KivisPackage.ATTRIBUTE_MAPPING__ATTRIBUTE:
        setAttribute(ATTRIBUTE_EDEFAULT);
        return;
      case KivisPackage.ATTRIBUTE_MAPPING__CURRENT_VALUE:
        setCurrentValue(CURRENT_VALUE_EDEFAULT);
        return;
      case KivisPackage.ATTRIBUTE_MAPPING__LITERAL:
        setLiteral((Literal)null);
        return;
      case KivisPackage.ATTRIBUTE_MAPPING__MAPPINGS:
        getMappings().clear();
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
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case KivisPackage.ATTRIBUTE_MAPPING__ATTRIBUTE:
        return ATTRIBUTE_EDEFAULT == null ? attribute != null : !ATTRIBUTE_EDEFAULT.equals(attribute);
      case KivisPackage.ATTRIBUTE_MAPPING__CURRENT_VALUE:
        return currentValue != CURRENT_VALUE_EDEFAULT;
      case KivisPackage.ATTRIBUTE_MAPPING__LITERAL:
        return literal != null;
      case KivisPackage.ATTRIBUTE_MAPPING__MAPPINGS:
        return mappings != null && !mappings.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (attribute: ");
    result.append(attribute);
    result.append(", currentValue: ");
    result.append(currentValue);
    result.append(')');
    return result.toString();
  }

} //AttributeMappingImpl