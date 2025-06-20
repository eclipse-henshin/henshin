/**
 * generated by Xtext 2.16.0
 */
package org.eclipse.emf.henshin.text.henshin_text.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage;
import org.eclipse.emf.henshin.text.henshin_text.Rollback;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rollback</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.impl.RollbackImpl#isRollback <em>Rollback</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RollbackImpl extends SequentialPropertiesImpl implements Rollback
{
  /**
   * The default value of the '{@link #isRollback() <em>Rollback</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isRollback()
   * @generated
   * @ordered
   */
  protected static final boolean ROLLBACK_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isRollback() <em>Rollback</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isRollback()
   * @generated
   * @ordered
   */
  protected boolean rollback = ROLLBACK_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected RollbackImpl()
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
    return Henshin_textPackage.Literals.ROLLBACK;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isRollback()
  {
    return rollback;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setRollback(boolean newRollback)
  {
    boolean oldRollback = rollback;
    rollback = newRollback;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, Henshin_textPackage.ROLLBACK__ROLLBACK, oldRollback, rollback));
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
      case Henshin_textPackage.ROLLBACK__ROLLBACK:
        return isRollback();
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
      case Henshin_textPackage.ROLLBACK__ROLLBACK:
        setRollback((Boolean)newValue);
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
      case Henshin_textPackage.ROLLBACK__ROLLBACK:
        setRollback(ROLLBACK_EDEFAULT);
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
      case Henshin_textPackage.ROLLBACK__ROLLBACK:
        return rollback != ROLLBACK_EDEFAULT;
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

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (rollback: ");
    result.append(rollback);
    result.append(')');
    return result.toString();
  }

} //RollbackImpl
