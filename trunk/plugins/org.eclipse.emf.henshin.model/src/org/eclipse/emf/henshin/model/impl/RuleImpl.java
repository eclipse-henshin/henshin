/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.UnaryFormula;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Rule</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getLhs <em>Lhs</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getRhs <em>Rhs</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getAttributeConditions <em>Attribute Conditions</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getMappings <em>Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getTransformationSystem <em>Transformation System</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleImpl extends TransformationUnitImpl implements Rule {
	/**
	 * The cached value of the '{@link #getLhs() <em>Lhs</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLhs()
	 * @generated
	 * @ordered
	 */
	protected Graph lhs;

	/**
	 * The cached value of the '{@link #getRhs() <em>Rhs</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRhs()
	 * @generated
	 * @ordered
	 */
	protected Graph rhs;

	/**
	 * The cached value of the '{@link #getAttributeConditions()
	 * <em>Attribute Conditions</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAttributeConditions()
	 * @generated
	 * @ordered
	 */
	protected EList<AttributeCondition> attributeConditions;

	/**
	 * The cached value of the '{@link #getMappings() <em>Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> mappings;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.RULE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getLhs() {
		return lhs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLhs(Graph newLhs, NotificationChain msgs) {
		Graph oldLhs = lhs;
		lhs = newLhs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__LHS, oldLhs, newLhs);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhs(Graph newLhs) {
		if (newLhs != lhs) {
			NotificationChain msgs = null;
			if (lhs != null)
				msgs = ((InternalEObject)lhs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.RULE__LHS, null, msgs);
			if (newLhs != null)
				msgs = ((InternalEObject)newLhs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.RULE__LHS, null, msgs);
			msgs = basicSetLhs(newLhs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__LHS, newLhs, newLhs));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getRhs() {
		return rhs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRhs(Graph newRhs, NotificationChain msgs) {
		Graph oldRhs = rhs;
		rhs = newRhs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__RHS, oldRhs, newRhs);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setRhs(Graph newRhs) {
		if (newRhs != rhs) {
			NotificationChain msgs = null;
			if (rhs != null)
				msgs = ((InternalEObject)rhs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.RULE__RHS, null, msgs);
			if (newRhs != null)
				msgs = ((InternalEObject)newRhs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.RULE__RHS, null, msgs);
			msgs = basicSetRhs(newRhs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__RHS, newRhs, newRhs));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeCondition> getAttributeConditions() {
		if (attributeConditions == null) {
			attributeConditions = new EObjectContainmentWithInverseEList<AttributeCondition>(AttributeCondition.class, this, HenshinPackage.RULE__ATTRIBUTE_CONDITIONS, HenshinPackage.ATTRIBUTE_CONDITION__RULE);
		}
		return attributeConditions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Mapping> getMappings() {
		if (mappings == null) {
			mappings = new EObjectContainmentEList<Mapping>(Mapping.class, this, HenshinPackage.RULE__MAPPINGS);
		}
		return mappings;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationSystem getTransformationSystem() {
		if (eContainerFeatureID() != HenshinPackage.RULE__TRANSFORMATION_SYSTEM) return null;
		return (TransformationSystem)eContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTransformationSystem(
			TransformationSystem newTransformationSystem, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newTransformationSystem, HenshinPackage.RULE__TRANSFORMATION_SYSTEM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransformationSystem(
			TransformationSystem newTransformationSystem) {
		if (newTransformationSystem != eInternalContainer() || (eContainerFeatureID() != HenshinPackage.RULE__TRANSFORMATION_SYSTEM && newTransformationSystem != null)) {
			if (EcoreUtil.isAncestor(this, newTransformationSystem))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTransformationSystem != null)
				msgs = ((InternalEObject)newTransformationSystem).eInverseAdd(this, HenshinPackage.TRANSFORMATION_SYSTEM__RULES, TransformationSystem.class, msgs);
			msgs = basicSetTransformationSystem(newTransformationSystem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__TRANSFORMATION_SYSTEM, newTransformationSystem, newTransformationSystem));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Node getNodeByName(String nodename, boolean isLhs) {
		for (Node node : (isLhs) ? lhs.getNodes() : rhs.getNodes()) {
			if (nodename.equals(node.getName()))
				return node;
		}

		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean containsMapping(Node sourceNode, Node targetNode) {

		for (Mapping m : getMappings()) {
			if (m.getOrigin().equals(sourceNode)
					&& m.getImage().equals(targetNode))
				return true;
		}// for

		if (this.getLhs().getFormula() != null) {
			List<NestedCondition> listNc = new ArrayList<NestedCondition>();
			collectNestedConditions(listNc, this.getLhs().getFormula());
			for (NestedCondition nc : listNc) {
				for (Mapping m : nc.getMappings()) {
					if (m.getOrigin().equals(sourceNode)
							&& m.getImage().equals(targetNode))
						return true;
				}// for
			}// for
		}// if
		return false;
	}// containsMapping

	/**
	 * Collects all NestedConditions contained in <code>f</code>. If
	 * <code>f</code> is of such type as well, it is contained in the returned
	 * list.<br>
	 * Remark: This method may be outsourced to FormulaImpl. (TODO)
	 * 
	 * @param list
	 * @param f
	 */
	private void collectNestedConditions(List<NestedCondition> list, Formula f) {
		if (f instanceof NestedCondition) {
			list.add((NestedCondition) f);
		} else {
			if (f instanceof UnaryFormula) {
				collectNestedConditions(list, ((UnaryFormula) f).getChild());
			} else if (f instanceof BinaryFormula) {
				collectNestedConditions(list, ((BinaryFormula) f).getLeft());
				collectNestedConditions(list, ((BinaryFormula) f).getRight());
			}// if elseif
		}// if else
	}// collectNestedConditions

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public EList<TransformationUnit> getAllSubUnits() {
		return (EList<TransformationUnit>) ECollections.EMPTY_ELIST;
	}// getAllSubUnits

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAttributeConditions()).basicAdd(otherEnd, msgs);
			case HenshinPackage.RULE__TRANSFORMATION_SYSTEM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetTransformationSystem((TransformationSystem)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.RULE__LHS:
				return basicSetLhs(null, msgs);
			case HenshinPackage.RULE__RHS:
				return basicSetRhs(null, msgs);
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				return ((InternalEList<?>)getAttributeConditions()).basicRemove(otherEnd, msgs);
			case HenshinPackage.RULE__MAPPINGS:
				return ((InternalEList<?>)getMappings()).basicRemove(otherEnd, msgs);
			case HenshinPackage.RULE__TRANSFORMATION_SYSTEM:
				return basicSetTransformationSystem(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(
			NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case HenshinPackage.RULE__TRANSFORMATION_SYSTEM:
				return eInternalContainer().eInverseRemove(this, HenshinPackage.TRANSFORMATION_SYSTEM__RULES, TransformationSystem.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.RULE__LHS:
				return getLhs();
			case HenshinPackage.RULE__RHS:
				return getRhs();
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				return getAttributeConditions();
			case HenshinPackage.RULE__MAPPINGS:
				return getMappings();
			case HenshinPackage.RULE__TRANSFORMATION_SYSTEM:
				return getTransformationSystem();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HenshinPackage.RULE__LHS:
				setLhs((Graph)newValue);
				return;
			case HenshinPackage.RULE__RHS:
				setRhs((Graph)newValue);
				return;
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				getAttributeConditions().clear();
				getAttributeConditions().addAll((Collection<? extends AttributeCondition>)newValue);
				return;
			case HenshinPackage.RULE__MAPPINGS:
				getMappings().clear();
				getMappings().addAll((Collection<? extends Mapping>)newValue);
				return;
			case HenshinPackage.RULE__TRANSFORMATION_SYSTEM:
				setTransformationSystem((TransformationSystem)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case HenshinPackage.RULE__LHS:
				setLhs((Graph)null);
				return;
			case HenshinPackage.RULE__RHS:
				setRhs((Graph)null);
				return;
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				getAttributeConditions().clear();
				return;
			case HenshinPackage.RULE__MAPPINGS:
				getMappings().clear();
				return;
			case HenshinPackage.RULE__TRANSFORMATION_SYSTEM:
				setTransformationSystem((TransformationSystem)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HenshinPackage.RULE__LHS:
				return lhs != null;
			case HenshinPackage.RULE__RHS:
				return rhs != null;
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				return attributeConditions != null && !attributeConditions.isEmpty();
			case HenshinPackage.RULE__MAPPINGS:
				return mappings != null && !mappings.isEmpty();
			case HenshinPackage.RULE__TRANSFORMATION_SYSTEM:
				return getTransformationSystem() != null;
		}
		return super.eIsSet(featureID);
	}

} // RuleImpl
