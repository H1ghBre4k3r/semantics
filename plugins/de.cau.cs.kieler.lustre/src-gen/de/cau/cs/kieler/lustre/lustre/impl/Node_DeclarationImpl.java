/**
 * generated by Xtext 2.10.0
 */
package de.cau.cs.kieler.lustre.lustre.impl;

import de.cau.cs.kieler.lustre.lustre.Assertion;
import de.cau.cs.kieler.lustre.lustre.Automaton;
import de.cau.cs.kieler.lustre.lustre.Constant_Declaration;
import de.cau.cs.kieler.lustre.lustre.Equation;
import de.cau.cs.kieler.lustre.lustre.LustrePackage;
import de.cau.cs.kieler.lustre.lustre.Node_Declaration;
import de.cau.cs.kieler.lustre.lustre.Variable_Declaration;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.impl.Node_DeclarationImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.impl.Node_DeclarationImpl#getReturned <em>Returned</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.impl.Node_DeclarationImpl#getConstants <em>Constants</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.impl.Node_DeclarationImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.impl.Node_DeclarationImpl#getEquations <em>Equations</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.impl.Node_DeclarationImpl#getAssertions <em>Assertions</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.impl.Node_DeclarationImpl#getAutomatons <em>Automatons</em>}</li>
 * </ul>
 *
 * @generated
 */
public class Node_DeclarationImpl extends Entity_DeclarationImpl implements Node_Declaration
{
  /**
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected EList<Variable_Declaration> parameters;

  /**
   * The cached value of the '{@link #getReturned() <em>Returned</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReturned()
   * @generated
   * @ordered
   */
  protected EList<Variable_Declaration> returned;

  /**
   * The cached value of the '{@link #getConstants() <em>Constants</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstants()
   * @generated
   * @ordered
   */
  protected EList<Constant_Declaration> constants;

  /**
   * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVariables()
   * @generated
   * @ordered
   */
  protected EList<Variable_Declaration> variables;

  /**
   * The cached value of the '{@link #getEquations() <em>Equations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEquations()
   * @generated
   * @ordered
   */
  protected EList<Equation> equations;

  /**
   * The cached value of the '{@link #getAssertions() <em>Assertions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAssertions()
   * @generated
   * @ordered
   */
  protected EList<Assertion> assertions;

  /**
   * The cached value of the '{@link #getAutomatons() <em>Automatons</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAutomatons()
   * @generated
   * @ordered
   */
  protected EList<Automaton> automatons;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected Node_DeclarationImpl()
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
    return LustrePackage.Literals.NODE_DECLARATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Variable_Declaration> getParameters()
  {
    if (parameters == null)
    {
      parameters = new EObjectContainmentEList<Variable_Declaration>(Variable_Declaration.class, this, LustrePackage.NODE_DECLARATION__PARAMETERS);
    }
    return parameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Variable_Declaration> getReturned()
  {
    if (returned == null)
    {
      returned = new EObjectContainmentEList<Variable_Declaration>(Variable_Declaration.class, this, LustrePackage.NODE_DECLARATION__RETURNED);
    }
    return returned;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Constant_Declaration> getConstants()
  {
    if (constants == null)
    {
      constants = new EObjectContainmentEList<Constant_Declaration>(Constant_Declaration.class, this, LustrePackage.NODE_DECLARATION__CONSTANTS);
    }
    return constants;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Variable_Declaration> getVariables()
  {
    if (variables == null)
    {
      variables = new EObjectContainmentEList<Variable_Declaration>(Variable_Declaration.class, this, LustrePackage.NODE_DECLARATION__VARIABLES);
    }
    return variables;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Equation> getEquations()
  {
    if (equations == null)
    {
      equations = new EObjectContainmentEList<Equation>(Equation.class, this, LustrePackage.NODE_DECLARATION__EQUATIONS);
    }
    return equations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Assertion> getAssertions()
  {
    if (assertions == null)
    {
      assertions = new EObjectContainmentEList<Assertion>(Assertion.class, this, LustrePackage.NODE_DECLARATION__ASSERTIONS);
    }
    return assertions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Automaton> getAutomatons()
  {
    if (automatons == null)
    {
      automatons = new EObjectContainmentEList<Automaton>(Automaton.class, this, LustrePackage.NODE_DECLARATION__AUTOMATONS);
    }
    return automatons;
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
      case LustrePackage.NODE_DECLARATION__PARAMETERS:
        return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
      case LustrePackage.NODE_DECLARATION__RETURNED:
        return ((InternalEList<?>)getReturned()).basicRemove(otherEnd, msgs);
      case LustrePackage.NODE_DECLARATION__CONSTANTS:
        return ((InternalEList<?>)getConstants()).basicRemove(otherEnd, msgs);
      case LustrePackage.NODE_DECLARATION__VARIABLES:
        return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
      case LustrePackage.NODE_DECLARATION__EQUATIONS:
        return ((InternalEList<?>)getEquations()).basicRemove(otherEnd, msgs);
      case LustrePackage.NODE_DECLARATION__ASSERTIONS:
        return ((InternalEList<?>)getAssertions()).basicRemove(otherEnd, msgs);
      case LustrePackage.NODE_DECLARATION__AUTOMATONS:
        return ((InternalEList<?>)getAutomatons()).basicRemove(otherEnd, msgs);
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
      case LustrePackage.NODE_DECLARATION__PARAMETERS:
        return getParameters();
      case LustrePackage.NODE_DECLARATION__RETURNED:
        return getReturned();
      case LustrePackage.NODE_DECLARATION__CONSTANTS:
        return getConstants();
      case LustrePackage.NODE_DECLARATION__VARIABLES:
        return getVariables();
      case LustrePackage.NODE_DECLARATION__EQUATIONS:
        return getEquations();
      case LustrePackage.NODE_DECLARATION__ASSERTIONS:
        return getAssertions();
      case LustrePackage.NODE_DECLARATION__AUTOMATONS:
        return getAutomatons();
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
      case LustrePackage.NODE_DECLARATION__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends Variable_Declaration>)newValue);
        return;
      case LustrePackage.NODE_DECLARATION__RETURNED:
        getReturned().clear();
        getReturned().addAll((Collection<? extends Variable_Declaration>)newValue);
        return;
      case LustrePackage.NODE_DECLARATION__CONSTANTS:
        getConstants().clear();
        getConstants().addAll((Collection<? extends Constant_Declaration>)newValue);
        return;
      case LustrePackage.NODE_DECLARATION__VARIABLES:
        getVariables().clear();
        getVariables().addAll((Collection<? extends Variable_Declaration>)newValue);
        return;
      case LustrePackage.NODE_DECLARATION__EQUATIONS:
        getEquations().clear();
        getEquations().addAll((Collection<? extends Equation>)newValue);
        return;
      case LustrePackage.NODE_DECLARATION__ASSERTIONS:
        getAssertions().clear();
        getAssertions().addAll((Collection<? extends Assertion>)newValue);
        return;
      case LustrePackage.NODE_DECLARATION__AUTOMATONS:
        getAutomatons().clear();
        getAutomatons().addAll((Collection<? extends Automaton>)newValue);
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
      case LustrePackage.NODE_DECLARATION__PARAMETERS:
        getParameters().clear();
        return;
      case LustrePackage.NODE_DECLARATION__RETURNED:
        getReturned().clear();
        return;
      case LustrePackage.NODE_DECLARATION__CONSTANTS:
        getConstants().clear();
        return;
      case LustrePackage.NODE_DECLARATION__VARIABLES:
        getVariables().clear();
        return;
      case LustrePackage.NODE_DECLARATION__EQUATIONS:
        getEquations().clear();
        return;
      case LustrePackage.NODE_DECLARATION__ASSERTIONS:
        getAssertions().clear();
        return;
      case LustrePackage.NODE_DECLARATION__AUTOMATONS:
        getAutomatons().clear();
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
      case LustrePackage.NODE_DECLARATION__PARAMETERS:
        return parameters != null && !parameters.isEmpty();
      case LustrePackage.NODE_DECLARATION__RETURNED:
        return returned != null && !returned.isEmpty();
      case LustrePackage.NODE_DECLARATION__CONSTANTS:
        return constants != null && !constants.isEmpty();
      case LustrePackage.NODE_DECLARATION__VARIABLES:
        return variables != null && !variables.isEmpty();
      case LustrePackage.NODE_DECLARATION__EQUATIONS:
        return equations != null && !equations.isEmpty();
      case LustrePackage.NODE_DECLARATION__ASSERTIONS:
        return assertions != null && !assertions.isEmpty();
      case LustrePackage.NODE_DECLARATION__AUTOMATONS:
        return automatons != null && !automatons.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //Node_DeclarationImpl