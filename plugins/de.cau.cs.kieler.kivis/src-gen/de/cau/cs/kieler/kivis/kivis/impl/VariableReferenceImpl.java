/**
 */
package de.cau.cs.kieler.kivis.kivis.impl;

import de.cau.cs.kieler.kivis.kivis.KivisPackage;
import de.cau.cs.kieler.kivis.kivis.ModelReference;
import de.cau.cs.kieler.kivis.kivis.VariableReference;

import de.cau.cs.kieler.prom.kibuild.ArrayIndex;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.VariableReferenceImpl#getModel <em>Model</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.VariableReferenceImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.VariableReferenceImpl#getArrayIndex <em>Array Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariableReferenceImpl extends MinimalEObjectImpl.Container implements VariableReference
{
  /**
   * The cached value of the '{@link #getModel() <em>Model</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModel()
   * @generated
   * @ordered
   */
  protected ModelReference model;

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
   * The cached value of the '{@link #getArrayIndex() <em>Array Index</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getArrayIndex()
   * @generated
   * @ordered
   */
  protected ArrayIndex arrayIndex;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VariableReferenceImpl()
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
    return KivisPackage.Literals.VARIABLE_REFERENCE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelReference getModel()
  {
    return model;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetModel(ModelReference newModel, NotificationChain msgs)
  {
    ModelReference oldModel = model;
    model = newModel;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KivisPackage.VARIABLE_REFERENCE__MODEL, oldModel, newModel);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setModel(ModelReference newModel)
  {
    if (newModel != model)
    {
      NotificationChain msgs = null;
      if (model != null)
        msgs = ((InternalEObject)model).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KivisPackage.VARIABLE_REFERENCE__MODEL, null, msgs);
      if (newModel != null)
        msgs = ((InternalEObject)newModel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KivisPackage.VARIABLE_REFERENCE__MODEL, null, msgs);
      msgs = basicSetModel(newModel, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KivisPackage.VARIABLE_REFERENCE__MODEL, newModel, newModel));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KivisPackage.VARIABLE_REFERENCE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ArrayIndex getArrayIndex()
  {
    return arrayIndex;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetArrayIndex(ArrayIndex newArrayIndex, NotificationChain msgs)
  {
    ArrayIndex oldArrayIndex = arrayIndex;
    arrayIndex = newArrayIndex;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KivisPackage.VARIABLE_REFERENCE__ARRAY_INDEX, oldArrayIndex, newArrayIndex);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setArrayIndex(ArrayIndex newArrayIndex)
  {
    if (newArrayIndex != arrayIndex)
    {
      NotificationChain msgs = null;
      if (arrayIndex != null)
        msgs = ((InternalEObject)arrayIndex).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KivisPackage.VARIABLE_REFERENCE__ARRAY_INDEX, null, msgs);
      if (newArrayIndex != null)
        msgs = ((InternalEObject)newArrayIndex).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KivisPackage.VARIABLE_REFERENCE__ARRAY_INDEX, null, msgs);
      msgs = basicSetArrayIndex(newArrayIndex, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KivisPackage.VARIABLE_REFERENCE__ARRAY_INDEX, newArrayIndex, newArrayIndex));
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
      case KivisPackage.VARIABLE_REFERENCE__MODEL:
        return basicSetModel(null, msgs);
      case KivisPackage.VARIABLE_REFERENCE__ARRAY_INDEX:
        return basicSetArrayIndex(null, msgs);
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
      case KivisPackage.VARIABLE_REFERENCE__MODEL:
        return getModel();
      case KivisPackage.VARIABLE_REFERENCE__NAME:
        return getName();
      case KivisPackage.VARIABLE_REFERENCE__ARRAY_INDEX:
        return getArrayIndex();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case KivisPackage.VARIABLE_REFERENCE__MODEL:
        setModel((ModelReference)newValue);
        return;
      case KivisPackage.VARIABLE_REFERENCE__NAME:
        setName((String)newValue);
        return;
      case KivisPackage.VARIABLE_REFERENCE__ARRAY_INDEX:
        setArrayIndex((ArrayIndex)newValue);
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
      case KivisPackage.VARIABLE_REFERENCE__MODEL:
        setModel((ModelReference)null);
        return;
      case KivisPackage.VARIABLE_REFERENCE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case KivisPackage.VARIABLE_REFERENCE__ARRAY_INDEX:
        setArrayIndex((ArrayIndex)null);
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
      case KivisPackage.VARIABLE_REFERENCE__MODEL:
        return model != null;
      case KivisPackage.VARIABLE_REFERENCE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case KivisPackage.VARIABLE_REFERENCE__ARRAY_INDEX:
        return arrayIndex != null;
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
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //VariableReferenceImpl