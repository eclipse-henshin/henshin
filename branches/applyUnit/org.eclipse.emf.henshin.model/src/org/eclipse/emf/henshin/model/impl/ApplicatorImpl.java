/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.henshin.model.Applicator;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Applicator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ApplicatorImpl extends TransformationUnitImpl implements Applicator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicatorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.APPLICATOR;
	}

	@Override
	protected EList<TransformationUnit> getSubUnits() {
		return ECollections.emptyEList();
	}

} //ApplicatorImpl
