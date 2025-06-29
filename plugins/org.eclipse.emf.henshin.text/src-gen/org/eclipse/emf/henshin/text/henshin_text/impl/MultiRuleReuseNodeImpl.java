/**
 * generated by Xtext 2.16.0
 */
package org.eclipse.emf.henshin.text.henshin_text.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.henshin.text.henshin_text.Attribute;
import org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage;
import org.eclipse.emf.henshin.text.henshin_text.MultiRuleReuseNode;
import org.eclipse.emf.henshin.text.henshin_text.Node;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Rule Reuse Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.impl.MultiRuleReuseNodeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.impl.MultiRuleReuseNodeImpl#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiRuleReuseNodeImpl extends GraphElementsImpl implements MultiRuleReuseNode
{
  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected Node name;

  /**
   * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAttribute()
   * @generated
   * @ordered
   */
  protected EList<Attribute> attribute;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MultiRuleReuseNodeImpl()
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
    return Henshin_textPackage.Literals.MULTI_RULE_REUSE_NODE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Node getName()
  {
    if (name != null && name.eIsProxy())
    {
      InternalEObject oldName = (InternalEObject)name;
      name = (Node)eResolveProxy(oldName);
      if (name != oldName)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, Henshin_textPackage.MULTI_RULE_REUSE_NODE__NAME, oldName, name));
      }
    }
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Node basicGetName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(Node newName)
  {
    Node oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, Henshin_textPackage.MULTI_RULE_REUSE_NODE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Attribute> getAttribute()
  {
    if (attribute == null)
    {
      attribute = new EObjectContainmentEList<Attribute>(Attribute.class, this, Henshin_textPackage.MULTI_RULE_REUSE_NODE__ATTRIBUTE);
    }
    return attribute;
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
      case Henshin_textPackage.MULTI_RULE_REUSE_NODE__ATTRIBUTE:
        return ((InternalEList<?>)getAttribute()).basicRemove(otherEnd, msgs);
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
      case Henshin_textPackage.MULTI_RULE_REUSE_NODE__NAME:
        if (resolve) return getName();
        return basicGetName();
      case Henshin_textPackage.MULTI_RULE_REUSE_NODE__ATTRIBUTE:
        return getAttribute();
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
      case Henshin_textPackage.MULTI_RULE_REUSE_NODE__NAME:
        setName((Node)newValue);
        return;
      case Henshin_textPackage.MULTI_RULE_REUSE_NODE__ATTRIBUTE:
        getAttribute().clear();
        getAttribute().addAll((Collection<? extends Attribute>)newValue);
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
      case Henshin_textPackage.MULTI_RULE_REUSE_NODE__NAME:
        setName((Node)null);
        return;
      case Henshin_textPackage.MULTI_RULE_REUSE_NODE__ATTRIBUTE:
        getAttribute().clear();
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
      case Henshin_textPackage.MULTI_RULE_REUSE_NODE__NAME:
        return name != null;
      case Henshin_textPackage.MULTI_RULE_REUSE_NODE__ATTRIBUTE:
        return attribute != null && !attribute.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //MultiRuleReuseNodeImpl
