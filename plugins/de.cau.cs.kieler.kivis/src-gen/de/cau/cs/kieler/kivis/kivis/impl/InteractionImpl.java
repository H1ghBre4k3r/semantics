/**
 */
package de.cau.cs.kieler.kivis.kivis.impl;

import de.cau.cs.kieler.kivis.kivis.Action;
import de.cau.cs.kieler.kivis.kivis.Condition;
import de.cau.cs.kieler.kivis.kivis.Event;
import de.cau.cs.kieler.kivis.kivis.Interaction;
import de.cau.cs.kieler.kivis.kivis.KivisPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interaction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.InteractionImpl#getEvent <em>Event</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.InteractionImpl#isAfterTick <em>After Tick</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.InteractionImpl#isBeforeTick <em>Before Tick</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.InteractionImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.impl.InteractionImpl#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InteractionImpl extends MinimalEObjectImpl.Container implements Interaction
{
  /**
   * The cached value of the '{@link #getEvent() <em>Event</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEvent()
   * @generated
   * @ordered
   */
  protected Event event;

  /**
   * The default value of the '{@link #isAfterTick() <em>After Tick</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isAfterTick()
   * @generated
   * @ordered
   */
  protected static final boolean AFTER_TICK_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isAfterTick() <em>After Tick</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isAfterTick()
   * @generated
   * @ordered
   */
  protected boolean afterTick = AFTER_TICK_EDEFAULT;

  /**
   * The default value of the '{@link #isBeforeTick() <em>Before Tick</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isBeforeTick()
   * @generated
   * @ordered
   */
  protected static final boolean BEFORE_TICK_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isBeforeTick() <em>Before Tick</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isBeforeTick()
   * @generated
   * @ordered
   */
  protected boolean beforeTick = BEFORE_TICK_EDEFAULT;

  /**
   * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getActions()
   * @generated
   * @ordered
   */
  protected EList<Action> actions;

  /**
   * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCondition()
   * @generated
   * @ordered
   */
  protected Condition condition;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InteractionImpl()
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
    return KivisPackage.Literals.INTERACTION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Event getEvent()
  {
    return event;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetEvent(Event newEvent, NotificationChain msgs)
  {
    Event oldEvent = event;
    event = newEvent;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KivisPackage.INTERACTION__EVENT, oldEvent, newEvent);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEvent(Event newEvent)
  {
    if (newEvent != event)
    {
      NotificationChain msgs = null;
      if (event != null)
        msgs = ((InternalEObject)event).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KivisPackage.INTERACTION__EVENT, null, msgs);
      if (newEvent != null)
        msgs = ((InternalEObject)newEvent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KivisPackage.INTERACTION__EVENT, null, msgs);
      msgs = basicSetEvent(newEvent, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KivisPackage.INTERACTION__EVENT, newEvent, newEvent));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isAfterTick()
  {
    return afterTick;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAfterTick(boolean newAfterTick)
  {
    boolean oldAfterTick = afterTick;
    afterTick = newAfterTick;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KivisPackage.INTERACTION__AFTER_TICK, oldAfterTick, afterTick));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isBeforeTick()
  {
    return beforeTick;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBeforeTick(boolean newBeforeTick)
  {
    boolean oldBeforeTick = beforeTick;
    beforeTick = newBeforeTick;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KivisPackage.INTERACTION__BEFORE_TICK, oldBeforeTick, beforeTick));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Action> getActions()
  {
    if (actions == null)
    {
      actions = new EObjectContainmentEList<Action>(Action.class, this, KivisPackage.INTERACTION__ACTIONS);
    }
    return actions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Condition getCondition()
  {
    return condition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCondition(Condition newCondition, NotificationChain msgs)
  {
    Condition oldCondition = condition;
    condition = newCondition;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KivisPackage.INTERACTION__CONDITION, oldCondition, newCondition);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCondition(Condition newCondition)
  {
    if (newCondition != condition)
    {
      NotificationChain msgs = null;
      if (condition != null)
        msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KivisPackage.INTERACTION__CONDITION, null, msgs);
      if (newCondition != null)
        msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KivisPackage.INTERACTION__CONDITION, null, msgs);
      msgs = basicSetCondition(newCondition, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KivisPackage.INTERACTION__CONDITION, newCondition, newCondition));
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
      case KivisPackage.INTERACTION__EVENT:
        return basicSetEvent(null, msgs);
      case KivisPackage.INTERACTION__ACTIONS:
        return ((InternalEList<?>)getActions()).basicRemove(otherEnd, msgs);
      case KivisPackage.INTERACTION__CONDITION:
        return basicSetCondition(null, msgs);
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
      case KivisPackage.INTERACTION__EVENT:
        return getEvent();
      case KivisPackage.INTERACTION__AFTER_TICK:
        return isAfterTick();
      case KivisPackage.INTERACTION__BEFORE_TICK:
        return isBeforeTick();
      case KivisPackage.INTERACTION__ACTIONS:
        return getActions();
      case KivisPackage.INTERACTION__CONDITION:
        return getCondition();
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
      case KivisPackage.INTERACTION__EVENT:
        setEvent((Event)newValue);
        return;
      case KivisPackage.INTERACTION__AFTER_TICK:
        setAfterTick((Boolean)newValue);
        return;
      case KivisPackage.INTERACTION__BEFORE_TICK:
        setBeforeTick((Boolean)newValue);
        return;
      case KivisPackage.INTERACTION__ACTIONS:
        getActions().clear();
        getActions().addAll((Collection<? extends Action>)newValue);
        return;
      case KivisPackage.INTERACTION__CONDITION:
        setCondition((Condition)newValue);
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
      case KivisPackage.INTERACTION__EVENT:
        setEvent((Event)null);
        return;
      case KivisPackage.INTERACTION__AFTER_TICK:
        setAfterTick(AFTER_TICK_EDEFAULT);
        return;
      case KivisPackage.INTERACTION__BEFORE_TICK:
        setBeforeTick(BEFORE_TICK_EDEFAULT);
        return;
      case KivisPackage.INTERACTION__ACTIONS:
        getActions().clear();
        return;
      case KivisPackage.INTERACTION__CONDITION:
        setCondition((Condition)null);
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
      case KivisPackage.INTERACTION__EVENT:
        return event != null;
      case KivisPackage.INTERACTION__AFTER_TICK:
        return afterTick != AFTER_TICK_EDEFAULT;
      case KivisPackage.INTERACTION__BEFORE_TICK:
        return beforeTick != BEFORE_TICK_EDEFAULT;
      case KivisPackage.INTERACTION__ACTIONS:
        return actions != null && !actions.isEmpty();
      case KivisPackage.INTERACTION__CONDITION:
        return condition != null;
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
    result.append(" (afterTick: ");
    result.append(afterTick);
    result.append(", beforeTick: ");
    result.append(beforeTick);
    result.append(')');
    return result.toString();
  }

} //InteractionImpl