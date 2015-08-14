/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.invcheck.nac.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.henshin.sam.invcheck.nac.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NacFactoryImpl extends EFactoryImpl implements NacFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NacFactory init() {
		try {
			NacFactory theNacFactory = (NacFactory)EPackage.Registry.INSTANCE.getEFactory(NacPackage.eNS_URI);
			if (theNacFactory != null) {
				return theNacFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NacFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NacFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case NacPackage.NEGATIVE_APPLICATION_CONDITION: return createNegativeApplicationCondition();
			case NacPackage.MATCH_WITH_NACS: return createMatchWithNacs();
			case NacPackage.PATTERN_NODE: return createPatternNode();
			case NacPackage.PATTERN_EDGE: return createPatternEdge();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NegativeApplicationCondition createNegativeApplicationCondition() {
		NegativeApplicationConditionImpl negativeApplicationCondition = new NegativeApplicationConditionImpl();
		return negativeApplicationCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MatchWithNacs createMatchWithNacs() {
		MatchWithNacsImpl matchWithNacs = new MatchWithNacsImpl();
		return matchWithNacs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PatternNode createPatternNode() {
		PatternNodeImpl patternNode = new PatternNodeImpl();
		return patternNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PatternEdge createPatternEdge() {
		PatternEdgeImpl patternEdge = new PatternEdgeImpl();
		return patternEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NacPackage getNacPackage() {
		return (NacPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NacPackage getPackage() {
		return NacPackage.eINSTANCE;
	}

} //NacFactoryImpl
