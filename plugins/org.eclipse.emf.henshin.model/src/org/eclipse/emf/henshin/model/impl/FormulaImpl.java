/**
 * <copyright>
 * </copyright>
 *
 * $Id: FormulaImpl.java,v 1.1 2009/10/28 10:38:14 enrico Exp $
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Formula</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class FormulaImpl extends EObjectImpl implements Formula {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FormulaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.FORMULA;
	}
	
	/**
	 * Renames all occurrences of a variable's name in this formula (i.e. in
	 * attribute values of related graphs) from the old name to the new name.
	 * This is performed for contained formulas as well.
	 * 
	 * @param oldVariableName
	 *            Old name of the variable
	 * @param newVariableName
	 *            New name of the variable
	 */
	protected abstract void updateVariableName(String oldVariableName, String newVariableName);
	
} //FormulaImpl
