/**
 */
package de.cau.cs.kieler.core.kexpressions.text.kext.impl;

import de.cau.cs.kieler.core.annotations.Annotation;

import de.cau.cs.kieler.core.annotations.impl.AnnotatableImpl;
import de.cau.cs.kieler.core.kexpressions.Expression;

import de.cau.cs.kieler.core.kexpressions.text.kext.AnnotatedExpression;
import de.cau.cs.kieler.core.kexpressions.text.kext.KextPackage;

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
 * An implementation of the model object '<em><b>Annotated Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.cau.cs.kieler.core.kexpressions.text.kext.impl.AnnotatedExpressionImpl#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AnnotatedExpressionImpl extends AnnotatableImpl implements AnnotatedExpression
{
  /**
     * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
     * @see #getExpression()
     * @generated
     * @ordered
     */
  protected Expression expression;

  /**
     * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
     * @generated
     */
  protected AnnotatedExpressionImpl()
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
        return KextPackage.Literals.ANNOTATED_EXPRESSION;
    }

  /**
     * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
     * @generated
     */
  public Expression getExpression()
  {
        return expression;
    }

  /**
     * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
     * @generated
     */
  public NotificationChain basicSetExpression(Expression newExpression, NotificationChain msgs)
  {
        Expression oldExpression = expression;
        expression = newExpression;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KextPackage.ANNOTATED_EXPRESSION__EXPRESSION, oldExpression, newExpression);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

  /**
     * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
     * @generated
     */
  public void setExpression(Expression newExpression)
  {
        if (newExpression != expression) {
            NotificationChain msgs = null;
            if (expression != null)
                msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KextPackage.ANNOTATED_EXPRESSION__EXPRESSION, null, msgs);
            if (newExpression != null)
                msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KextPackage.ANNOTATED_EXPRESSION__EXPRESSION, null, msgs);
            msgs = basicSetExpression(newExpression, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, KextPackage.ANNOTATED_EXPRESSION__EXPRESSION, newExpression, newExpression));
    }

  /**
     * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
     * @generated
     */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
        switch (featureID) {
            case KextPackage.ANNOTATED_EXPRESSION__EXPRESSION:
                return basicSetExpression(null, msgs);
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
        switch (featureID) {
            case KextPackage.ANNOTATED_EXPRESSION__EXPRESSION:
                return getExpression();
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
        switch (featureID) {
            case KextPackage.ANNOTATED_EXPRESSION__EXPRESSION:
                setExpression((Expression)newValue);
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
        switch (featureID) {
            case KextPackage.ANNOTATED_EXPRESSION__EXPRESSION:
                setExpression((Expression)null);
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
        switch (featureID) {
            case KextPackage.ANNOTATED_EXPRESSION__EXPRESSION:
                return expression != null;
        }
        return super.eIsSet(featureID);
    }

} //AnnotatedExpressionImpl
