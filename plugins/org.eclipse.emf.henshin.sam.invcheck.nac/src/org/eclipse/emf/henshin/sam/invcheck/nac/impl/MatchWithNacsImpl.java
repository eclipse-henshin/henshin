/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.invcheck.nac.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.invcheck.nac.MatchWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage;
import org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchEntryImpl;
import org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Match With Nacs</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.MatchWithNacsImpl#getNacMatching <em>Nac Matching</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MatchWithNacsImpl extends MatchImpl implements MatchWithNacs {
	/**
	 * The cached value of the '{@link #getNacMatching() <em>Nac Matching</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNacMatching()
	 * @generated
	 * @ordered
	 */
	protected EMap<NegativeApplicationCondition, NegativeApplicationCondition> nacMatching;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MatchWithNacsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NacPackage.Literals.MATCH_WITH_NACS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<NegativeApplicationCondition, NegativeApplicationCondition> getNacMatching() {
		if (nacMatching == null) {
			nacMatching = new EcoreEMap<NegativeApplicationCondition,NegativeApplicationCondition>(SamtracePackage.Literals.MATCH_ENTRY, MatchEntryImpl.class, this, NacPackage.MATCH_WITH_NACS__NAC_MATCHING);
		}
		return nacMatching;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void clear() {
		this.getNodeMatching().clear();
		this.getEdgeMatching().clear();
		this.getNacMatching().clear();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MatchWithNacs copy() {
		MatchWithNacs result = NacFactory.eINSTANCE.createMatchWithNacs();
		result.getNodeMatching().putAll(this.getNodeMatching());
		result.getEdgeMatching().putAll(this.getEdgeMatching());
		result.getNacMatching().putAll(this.getNacMatching());
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case NacPackage.MATCH_WITH_NACS__NAC_MATCHING:
				return ((InternalEList<?>)getNacMatching()).basicRemove(otherEnd, msgs);
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
			case NacPackage.MATCH_WITH_NACS__NAC_MATCHING:
				if (coreType) return getNacMatching();
				else return getNacMatching().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case NacPackage.MATCH_WITH_NACS__NAC_MATCHING:
				((EStructuralFeature.Setting)getNacMatching()).set(newValue);
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
			case NacPackage.MATCH_WITH_NACS__NAC_MATCHING:
				getNacMatching().clear();
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
			case NacPackage.MATCH_WITH_NACS__NAC_MATCHING:
				return nacMatching != null && !nacMatching.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MatchWithNacsImpl
