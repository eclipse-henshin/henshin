/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.criticalpair;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Critical Pair</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getFirstRule <em>First Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getSecondRule <em>Second Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getMinimalModel <em>Minimal Model</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalpairPackage#getCriticalPair()
 * @model
 * @generated
 */
public interface CriticalPair extends EObject {
	/**
	 * Returns the value of the '<em><b>First Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>First Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First Rule</em>' containment reference.
	 * @see #setFirstRule(Rule)
	 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalpairPackage#getCriticalPair_FirstRule()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Rule getFirstRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getFirstRule <em>First Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Rule</em>' containment reference.
	 * @see #getFirstRule()
	 * @generated
	 */
	void setFirstRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Second Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Second Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Second Rule</em>' containment reference.
	 * @see #setSecondRule(Rule)
	 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalpairPackage#getCriticalPair_SecondRule()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Rule getSecondRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getSecondRule <em>Second Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Second Rule</em>' containment reference.
	 * @see #getSecondRule()
	 * @generated
	 */
	void setSecondRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Minimal Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Minimal Model</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Minimal Model</em>' containment reference.
	 * @see #setMinimalModel(EPackage)
	 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalpairPackage#getCriticalPair_MinimalModel()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EPackage getMinimalModel();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getMinimalModel <em>Minimal Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minimal Model</em>' containment reference.
	 * @see #getMinimalModel()
	 * @generated
	 */
	void setMinimalModel(EPackage value);

} // CriticalPair
