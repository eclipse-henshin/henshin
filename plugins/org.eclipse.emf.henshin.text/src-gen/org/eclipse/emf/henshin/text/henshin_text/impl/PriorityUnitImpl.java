/**
 * generated by Xtext 2.16.0
 */
package org.eclipse.emf.henshin.text.henshin_text.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage;
import org.eclipse.emf.henshin.text.henshin_text.List;
import org.eclipse.emf.henshin.text.henshin_text.PriorityUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Priority Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.impl.PriorityUnitImpl#getListOfLists <em>List Of Lists</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PriorityUnitImpl extends UnitElementImpl implements PriorityUnit
{
  /**
   * The cached value of the '{@link #getListOfLists() <em>List Of Lists</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getListOfLists()
   * @generated
   * @ordered
   */
  protected EList<List> listOfLists;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PriorityUnitImpl()
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
    return Henshin_textPackage.Literals.PRIORITY_UNIT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<List> getListOfLists()
  {
    if (listOfLists == null)
    {
      listOfLists = new EObjectContainmentEList<List>(List.class, this, Henshin_textPackage.PRIORITY_UNIT__LIST_OF_LISTS);
    }
    return listOfLists;
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
      case Henshin_textPackage.PRIORITY_UNIT__LIST_OF_LISTS:
        return ((InternalEList<?>)getListOfLists()).basicRemove(otherEnd, msgs);
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
      case Henshin_textPackage.PRIORITY_UNIT__LIST_OF_LISTS:
        return getListOfLists();
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
      case Henshin_textPackage.PRIORITY_UNIT__LIST_OF_LISTS:
        getListOfLists().clear();
        getListOfLists().addAll((Collection<? extends List>)newValue);
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
      case Henshin_textPackage.PRIORITY_UNIT__LIST_OF_LISTS:
        getListOfLists().clear();
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
      case Henshin_textPackage.PRIORITY_UNIT__LIST_OF_LISTS:
        return listOfLists != null && !listOfLists.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //PriorityUnitImpl
