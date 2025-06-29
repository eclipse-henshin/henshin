/**
 * generated by Xtext 2.16.0
 */
package org.eclipse.emf.henshin.text.henshin_text.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.henshin.text.henshin_text.ConditionEdge;
import org.eclipse.emf.henshin.text.henshin_text.ConditionNodeTypes;
import org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Condition Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.impl.ConditionEdgeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.impl.ConditionEdgeImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.impl.ConditionEdgeImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionEdgeImpl extends MinimalEObjectImpl.Container implements ConditionEdge
{
  /**
   * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSource()
   * @generated
   * @ordered
   */
  protected ConditionNodeTypes source;

  /**
   * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTarget()
   * @generated
   * @ordered
   */
  protected ConditionNodeTypes target;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected EReference type;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConditionEdgeImpl()
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
    return Henshin_textPackage.Literals.CONDITION_EDGE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConditionNodeTypes getSource()
  {
    if (source != null && source.eIsProxy())
    {
      InternalEObject oldSource = (InternalEObject)source;
      source = (ConditionNodeTypes)eResolveProxy(oldSource);
      if (source != oldSource)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, Henshin_textPackage.CONDITION_EDGE__SOURCE, oldSource, source));
      }
    }
    return source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConditionNodeTypes basicGetSource()
  {
    return source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setSource(ConditionNodeTypes newSource)
  {
    ConditionNodeTypes oldSource = source;
    source = newSource;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, Henshin_textPackage.CONDITION_EDGE__SOURCE, oldSource, source));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConditionNodeTypes getTarget()
  {
    if (target != null && target.eIsProxy())
    {
      InternalEObject oldTarget = (InternalEObject)target;
      target = (ConditionNodeTypes)eResolveProxy(oldTarget);
      if (target != oldTarget)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, Henshin_textPackage.CONDITION_EDGE__TARGET, oldTarget, target));
      }
    }
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConditionNodeTypes basicGetTarget()
  {
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTarget(ConditionNodeTypes newTarget)
  {
    ConditionNodeTypes oldTarget = target;
    target = newTarget;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, Henshin_textPackage.CONDITION_EDGE__TARGET, oldTarget, target));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getType()
  {
    if (type != null && type.eIsProxy())
    {
      InternalEObject oldType = (InternalEObject)type;
      type = (EReference)eResolveProxy(oldType);
      if (type != oldType)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, Henshin_textPackage.CONDITION_EDGE__TYPE, oldType, type));
      }
    }
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference basicGetType()
  {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setType(EReference newType)
  {
    EReference oldType = type;
    type = newType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, Henshin_textPackage.CONDITION_EDGE__TYPE, oldType, type));
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
      case Henshin_textPackage.CONDITION_EDGE__SOURCE:
        if (resolve) return getSource();
        return basicGetSource();
      case Henshin_textPackage.CONDITION_EDGE__TARGET:
        if (resolve) return getTarget();
        return basicGetTarget();
      case Henshin_textPackage.CONDITION_EDGE__TYPE:
        if (resolve) return getType();
        return basicGetType();
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
      case Henshin_textPackage.CONDITION_EDGE__SOURCE:
        setSource((ConditionNodeTypes)newValue);
        return;
      case Henshin_textPackage.CONDITION_EDGE__TARGET:
        setTarget((ConditionNodeTypes)newValue);
        return;
      case Henshin_textPackage.CONDITION_EDGE__TYPE:
        setType((EReference)newValue);
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
      case Henshin_textPackage.CONDITION_EDGE__SOURCE:
        setSource((ConditionNodeTypes)null);
        return;
      case Henshin_textPackage.CONDITION_EDGE__TARGET:
        setTarget((ConditionNodeTypes)null);
        return;
      case Henshin_textPackage.CONDITION_EDGE__TYPE:
        setType((EReference)null);
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
      case Henshin_textPackage.CONDITION_EDGE__SOURCE:
        return source != null;
      case Henshin_textPackage.CONDITION_EDGE__TARGET:
        return target != null;
      case Henshin_textPackage.CONDITION_EDGE__TYPE:
        return type != null;
    }
    return super.eIsSet(featureID);
  }

} //ConditionEdgeImpl
