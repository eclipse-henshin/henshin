/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.codegen.model.GenHenshinPackage;
import org.eclipse.emf.henshin.codegen.model.GenRule;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class GenRuleImpl extends GenUnitImpl implements GenRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenHenshinPackage.Literals.GEN_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule getRule() {
		return (Rule) unit;
	}

} //GenRuleImpl
