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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Port;
import org.eclipse.emf.henshin.model.PortMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.Variable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#isActivated <em>Activated</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getPortMappings <em>Port Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getLhs <em>Lhs</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getRhs <em>Rhs</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getAttributeConditions <em>Attribute Conditions</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getMappings <em>Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getTransformationSystem <em>Transformation System</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getVariables <em>Variables</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleImpl extends DescribedElementImpl implements Rule {
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
	 * The default value of the '{@link #isActivated() <em>Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActivated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ACTIVATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isActivated() <em>Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActivated()
	 * @generated
	 * @ordered
	 */
	protected boolean activated = ACTIVATED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> ports;

	/**
	 * The cached value of the '{@link #getPortMappings() <em>Port Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<PortMapping> portMappings;

	/**
	 * The cached value of the '{@link #getLhs() <em>Lhs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhs()
	 * @generated
	 * @ordered
	 */
	protected Graph lhs;

	/**
	 * The cached value of the '{@link #getRhs() <em>Rhs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRhs()
	 * @generated
	 * @ordered
	 */
	protected Graph rhs;

	/**
	 * The cached value of the '{@link #getAttributeConditions() <em>Attribute Conditions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConditions()
	 * @generated
	 * @ordered
	 */
	protected EList<AttributeCondition> attributeConditions;

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
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> variables;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivated(boolean newActivated) {
		boolean oldActivated = activated;
		activated = newActivated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__ACTIVATED, oldActivated, activated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentWithInverseEList<Port>(Port.class, this, HenshinPackage.RULE__PORTS, HenshinPackage.PORT__UNIT);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PortMapping> getPortMappings() {
		if (portMappings == null) {
			portMappings = new EObjectContainmentEList<PortMapping>(PortMapping.class, this, HenshinPackage.RULE__PORT_MAPPINGS);
		}
		return portMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getLhs() {
		return lhs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getRhs() {
		return rhs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeCondition> getAttributeConditions() {
		if (attributeConditions == null) {
			attributeConditions = new EObjectContainmentWithInverseEList<AttributeCondition>(AttributeCondition.class, this, HenshinPackage.RULE__ATTRIBUTE_CONDITIONS, HenshinPackage.ATTRIBUTE_CONDITION__RULE);
		}
		return attributeConditions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Mapping> getMappings() {
		if (mappings == null) {
			mappings = new EObjectContainmentEList<Mapping>(Mapping.class, this, HenshinPackage.RULE__MAPPINGS);
		}
		return mappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationSystem getTransformationSystem() {
		if (eContainerFeatureID() != HenshinPackage.RULE__TRANSFORMATION_SYSTEM) return null;
		return (TransformationSystem)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTransformationSystem(TransformationSystem newTransformationSystem, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newTransformationSystem, HenshinPackage.RULE__TRANSFORMATION_SYSTEM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransformationSystem(TransformationSystem newTransformationSystem) {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Variable> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentWithInverseEList<Variable>(Variable.class, this, HenshinPackage.RULE__VARIABLES, HenshinPackage.VARIABLE__RULE);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.RULE__PORTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPorts()).basicAdd(otherEnd, msgs);
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAttributeConditions()).basicAdd(otherEnd, msgs);
			case HenshinPackage.RULE__TRANSFORMATION_SYSTEM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetTransformationSystem((TransformationSystem)otherEnd, msgs);
			case HenshinPackage.RULE__VARIABLES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getVariables()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.RULE__PORTS:
				return ((InternalEList<?>)getPorts()).basicRemove(otherEnd, msgs);
			case HenshinPackage.RULE__PORT_MAPPINGS:
				return ((InternalEList<?>)getPortMappings()).basicRemove(otherEnd, msgs);
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
			case HenshinPackage.RULE__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case HenshinPackage.RULE__TRANSFORMATION_SYSTEM:
				return eInternalContainer().eInverseRemove(this, HenshinPackage.TRANSFORMATION_SYSTEM__RULES, TransformationSystem.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.RULE__NAME:
				return getName();
			case HenshinPackage.RULE__ACTIVATED:
				return isActivated();
			case HenshinPackage.RULE__PORTS:
				return getPorts();
			case HenshinPackage.RULE__PORT_MAPPINGS:
				return getPortMappings();
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
			case HenshinPackage.RULE__VARIABLES:
				return getVariables();
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
			case HenshinPackage.RULE__NAME:
				setName((String)newValue);
				return;
			case HenshinPackage.RULE__ACTIVATED:
				setActivated((Boolean)newValue);
				return;
			case HenshinPackage.RULE__PORTS:
				getPorts().clear();
				getPorts().addAll((Collection<? extends Port>)newValue);
				return;
			case HenshinPackage.RULE__PORT_MAPPINGS:
				getPortMappings().clear();
				getPortMappings().addAll((Collection<? extends PortMapping>)newValue);
				return;
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
			case HenshinPackage.RULE__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends Variable>)newValue);
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
			case HenshinPackage.RULE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case HenshinPackage.RULE__ACTIVATED:
				setActivated(ACTIVATED_EDEFAULT);
				return;
			case HenshinPackage.RULE__PORTS:
				getPorts().clear();
				return;
			case HenshinPackage.RULE__PORT_MAPPINGS:
				getPortMappings().clear();
				return;
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
			case HenshinPackage.RULE__VARIABLES:
				getVariables().clear();
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
			case HenshinPackage.RULE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case HenshinPackage.RULE__ACTIVATED:
				return activated != ACTIVATED_EDEFAULT;
			case HenshinPackage.RULE__PORTS:
				return ports != null && !ports.isEmpty();
			case HenshinPackage.RULE__PORT_MAPPINGS:
				return portMappings != null && !portMappings.isEmpty();
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
			case HenshinPackage.RULE__VARIABLES:
				return variables != null && !variables.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				case HenshinPackage.RULE__NAME: return HenshinPackage.NAMED_ELEMENT__NAME;
				default: return -1;
			}
		}
		if (baseClass == TransformationUnit.class) {
			switch (derivedFeatureID) {
				case HenshinPackage.RULE__ACTIVATED: return HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED;
				case HenshinPackage.RULE__PORTS: return HenshinPackage.TRANSFORMATION_UNIT__PORTS;
				case HenshinPackage.RULE__PORT_MAPPINGS: return HenshinPackage.TRANSFORMATION_UNIT__PORT_MAPPINGS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				case HenshinPackage.NAMED_ELEMENT__NAME: return HenshinPackage.RULE__NAME;
				default: return -1;
			}
		}
		if (baseClass == TransformationUnit.class) {
			switch (baseFeatureID) {
				case HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED: return HenshinPackage.RULE__ACTIVATED;
				case HenshinPackage.TRANSFORMATION_UNIT__PORTS: return HenshinPackage.RULE__PORTS;
				case HenshinPackage.TRANSFORMATION_UNIT__PORT_MAPPINGS: return HenshinPackage.RULE__PORT_MAPPINGS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", activated: ");
		result.append(activated);
		result.append(')');
		return result.toString();
	}

} //RuleImpl
