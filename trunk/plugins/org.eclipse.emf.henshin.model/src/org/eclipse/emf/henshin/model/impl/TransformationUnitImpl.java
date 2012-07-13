/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc --> 
 * An implementation of the model object '<em><b>Transformation Unit</b></em>'. 
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationUnitImpl#isActivated <em>Activated</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationUnitImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationUnitImpl#getParameterMappings <em>Parameter Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class TransformationUnitImpl extends NamedElementImpl implements
		TransformationUnit {
	
	/**
	 * The default value of the '{@link #isActivated() <em>Activated</em>}' attribute.
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @see #isActivated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ACTIVATED_EDEFAULT = true;
	
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
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameters;
	
	/**
	 * The cached value of the '{@link #getParameterMappings()
	 * <em>Parameter Mappings</em>}' containment reference list. <!--
	 * begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * 
	 * @see #getParameterMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterMapping> parameterMappings;
	
	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransformationUnitImpl() {
		super();
	}
	
	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.TRANSFORMATION_UNIT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED, oldActivated, activated));
	}
	
	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentWithInverseEList<Parameter>(Parameter.class, this, HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS, HenshinPackage.PARAMETER__UNIT);
		}
		return parameters;
	}
	
	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParameterMapping> getParameterMappings() {
		if (parameterMappings == null) {
			parameterMappings = new EObjectContainmentEList<ParameterMapping>(ParameterMapping.class, this, HenshinPackage.TRANSFORMATION_UNIT__PARAMETER_MAPPINGS);
		}
		return parameterMappings;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * Get the direct subUnits of this unit. Subclasses must implement this method.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected abstract EList<TransformationUnit> getSubUnits();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public final EList<TransformationUnit> getSubUnits(boolean deep) {
		EList<TransformationUnit> result = new BasicEList<TransformationUnit>();
		result.addAll(getSubUnits());
		if (deep) {
			boolean changed;
			do {
				changed = false;
				int count = result.size();
				for (int i=0; i<count; i++) {
					for (TransformationUnit unit : 
						((TransformationUnitImpl) result.get(i)).getSubUnits()) {
						if (!result.contains(unit)) {
							result.add(unit);
							changed = true;
						}
					}
				}
			} while (changed);
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Parameter getParameter(String parameterName) {
		for (Parameter parameter : getParameters()) {
			if (parameterName==null) {
				if (parameter.getName()==null) {
					return parameter;
				}
			} else {
				if (parameterName.equals(parameter.getName())) {
					return parameter;
				}
			}
		}
		return null;
	}
	
	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getParameters()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
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
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETER_MAPPINGS:
				return ((InternalEList<?>)getParameterMappings()).basicRemove(otherEnd, msgs);
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
			case HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED:
				return isActivated();
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS:
				return getParameters();
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETER_MAPPINGS:
				return getParameterMappings();
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
			case HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED:
				setActivated((Boolean)newValue);
				return;
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>)newValue);
				return;
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETER_MAPPINGS:
				getParameterMappings().clear();
				getParameterMappings().addAll((Collection<? extends ParameterMapping>)newValue);
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
			case HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED:
				setActivated(ACTIVATED_EDEFAULT);
				return;
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS:
				getParameters().clear();
				return;
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETER_MAPPINGS:
				getParameterMappings().clear();
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
			case HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED:
				return activated != ACTIVATED_EDEFAULT;
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETER_MAPPINGS:
				return parameterMappings != null && !parameterMappings.isEmpty();
		}
		return super.eIsSet(featureID);
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
		result.append(" (activated: ");
		result.append(activated);
		result.append(')');
		return result.toString();
	}
	
} // TransformationUnitImpl
