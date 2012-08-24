/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc --> 
 * An implementation of the model object '<em><b>Nested Condition</b></em>'. 
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.NestedConditionImpl#getConclusion <em>Conclusion</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.NestedConditionImpl#getMappings <em>Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NestedConditionImpl extends EObjectImpl implements NestedCondition {
	
	/**
	 * The cached value of the '{@link #getConclusion() <em>Conclusion</em>}' containment reference.
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @see #getConclusion()
	 * @generated
	 * @ordered
	 */
	protected Graph conclusion;
	
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
	protected NestedConditionImpl() {
		super();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.NESTED_CONDITION;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getConclusion() {
		return conclusion;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConclusion(Graph newConclusion, NotificationChain msgs) {
		Graph oldConclusion = conclusion;
		conclusion = newConclusion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.NESTED_CONDITION__CONCLUSION, oldConclusion, newConclusion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConclusion(Graph newConclusion) {
		if (newConclusion != conclusion) {
			NotificationChain msgs = null;
			if (conclusion != null)
				msgs = ((InternalEObject)conclusion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.NESTED_CONDITION__CONCLUSION, null, msgs);
			if (newConclusion != null)
				msgs = ((InternalEObject)newConclusion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.NESTED_CONDITION__CONCLUSION, null, msgs);
			msgs = basicSetConclusion(newConclusion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.NESTED_CONDITION__CONCLUSION, newConclusion, newConclusion));
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public MappingList getMappings() {
		if (mappings == null) {
			mappings = new MappingListImpl(this, HenshinPackage.NESTED_CONDITION__MAPPINGS);
		}
		return (MappingList) mappings;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isPAC() {
		EObject container = eContainer();
		while (container instanceof Formula) {
			if (!(container instanceof And)) {
				return false;
			}
			container = container.eContainer();
		}
		return (container instanceof Graph) && (((Graph) container).isLhs());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isNAC() {
		EObject container = eContainer();
		if (!(container instanceof Not)) {
			return false;
		}
		container = container.eContainer();
		while (container instanceof Formula) {
			if (!(container instanceof And)) {
				return false;
			}
			container = container.eContainer();
		}
		return (container instanceof Graph) && (((Graph) container).isLhs());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isTrue() {
		// Conclusion not set?
		if (conclusion==null) {
			return false;
		}
		for (Node node : conclusion.getNodes()) {
			// Not the image of a mapping? Or a different type?
			Node nodeOrigin = getMappings().getOrigin(node);
			if (nodeOrigin==null || nodeOrigin.getType()!=node.getType()) {
				return false;
			}
			// Check the attributes of this node as well.
			for (Attribute attribute : node.getAttributes()) {
				Attribute attOrigin = getMappings().getOrigin(attribute);
				if (attOrigin==null || !valueEquals(attribute.getValue(), attOrigin.getValue())) {
					return false;
				}
			}
		}
		for (Edge edge : conclusion.getEdges()) {
			// Not the image of a mapping? Or a different type?
			Edge edgeOrigin = getMappings().getOrigin(edge);
			if (edgeOrigin==null || edgeOrigin.getType()!=edge.getType()) {
				return false;
			}
		}
		// Otherwise it is definitely always true:
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isFalse() {
		// There seems to be no situation where we can definitely say it is always false.
		return false;
	}
	
	/*
	 * Check if to attribute values are equal.
	 */
	static boolean valueEquals(String v1, String v2) {
		if (v1==null) {
			return (v2==null);
		}
		if (v2==null) {
			return false;
		}
		return v1.trim().equals(v2.trim());
	}

	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule getRule() {
		EObject container = eContainer();
		while (container!=null) {
			if (container instanceof Rule) {
				return (Rule) container;
			}
			container = container.eContainer();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.NESTED_CONDITION__CONCLUSION:
				return basicSetConclusion(null, msgs);
			case HenshinPackage.NESTED_CONDITION__MAPPINGS:
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.NESTED_CONDITION__CONCLUSION:
				return getConclusion();
			case HenshinPackage.NESTED_CONDITION__MAPPINGS:
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HenshinPackage.NESTED_CONDITION__CONCLUSION:
				setConclusion((Graph)newValue);
				return;
			case HenshinPackage.NESTED_CONDITION__MAPPINGS:
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case HenshinPackage.NESTED_CONDITION__CONCLUSION:
				setConclusion((Graph)null);
				return;
			case HenshinPackage.NESTED_CONDITION__MAPPINGS:
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HenshinPackage.NESTED_CONDITION__CONCLUSION:
				return conclusion != null;
			case HenshinPackage.NESTED_CONDITION__MAPPINGS:
				return mappings != null && !mappings.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String toString() {
		if (conclusion==null) {
			return null;
		}
		if (conclusion.getFormula()==null) {
			return conclusion.getName();
		} else {
			return "(" + conclusion.getName() + " && " + conclusion.getFormula() + ")";
		}		
	}
	
} // NestedConditionImpl
