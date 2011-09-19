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
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Amalgamation Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamationUnit#getKernelRule <em>Kernel Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamationUnit#getMultiRules <em>Multi Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamationUnit#getLhsMappings <em>Lhs Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamationUnit#getRhsMappings <em>Rhs Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamationUnit()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='kernelLhsNodesMapped \r\nkernelRhsNodesMapped \r\nkernelLhsEdgesMapped \r\nkernelRhsEdgesMapped\r\nlhsMappingsFromKernelToMulti\r\nrhsMappingsFromKernelToMulti\r\nnoAdditionalMappingsFromMappedKernel'"
 *        annotation="http://www.eclipse.org/emf/2010/Henshin/OCL kernelLhsNodesMapped='kernelRule.lhs.nodes->forAll(\r\n\tnodeKL : Node\t\r\n\t| multiRules->forAll( \r\n\t\truleM : Rule \r\n\t\t| lhsMappings->one(\r\n\t\t\tlhsMapping: Mapping \r\n\t\t\t| lhsMapping.origin = nodeKL \r\n\t\t\tand ruleM.lhs.nodes->includes(lhsMapping.image)\r\n\t\t\t)\r\n\t\t)\r\n\t)' kernelLhsNodesMapped.Msg='_Ocl_Msg_AmalgamationUnit_kernelLhsNodesMapped' kernelRhsNodesMapped='kernelRule.rhs.nodes->forAll(\r\n\tnodeKR : Node\t\r\n\t| multiRules->forAll( \r\n\t\truleM : Rule  \r\n\t\t| rhsMappings->one(\r\n\t\t\trhsMapping: Mapping \r\n\t\t\t| rhsMapping.origin = nodeKR \r\n\t\t\tand ruleM.rhs.nodes->includes(rhsMapping.image)\r\n\t\t\t)\r\n\t\t)\r\n\t)' kernelRhsNodesMapped.Msg='_Ocl_Msg_AmalgamationUnit_kernelRhsNodesMapped' kernelLhsEdgesMapped='kernelRule.lhs.edges->forAll( kernelEdge : Edge | \r\n\tmultiRules->forAll( multiRule : Rule| \r\n\t\tmultiRule.lhs.edges->exists( multiEdge : Edge | \r\n\r\n\t\t\tmultiEdge.type = kernelEdge.type \r\n\t\t\tand \r\n\t\t\tlhsMappings->exists( sourceMapping : Mapping | \r\n\t\t\t\tsourceMapping.origin = kernelEdge.source \r\n\t\t\t\tand \r\n\t\t\t\tsourceMapping.image = multiEdge.source \r\n\t\t\t\t) \r\n\t\t\tand \r\n\t\t\tlhsMappings->exists( targetMapping : Mapping | \r\n\t\t\t\ttargetMapping.origin = kernelEdge.target \r\n\t\t\t\tand \r\n\t\t\t\ttargetMapping.image = multiEdge.target \r\n\t\t\t\t)\r\n\r\n\t\t\t)\r\n\t\t)\r\n\t)' kernelLhsEdgesMapped.Msg='_Ocl_Msg_AmalgamationUnit_kernelLhsEdgesMapped' kernelRhsEdgesMapped='kernelRule.rhs.edges->forAll( kernelEdge : Edge | \r\n\tmultiRules->forAll( multiRule : Rule| \r\n\t\tmultiRule.rhs.edges->exists( multiEdge : Edge | \r\n\r\n\t\t\tmultiEdge.type = kernelEdge.type \r\n\t\t\tand \r\n\t\t\trhsMappings->exists( sourceMapping : Mapping | \r\n\t\t\t\tsourceMapping.origin = kernelEdge.source \r\n\t\t\t\tand \r\n\t\t\t\tsourceMapping.image = multiEdge.source \r\n\t\t\t\t) \r\n\t\t\tand \r\n\t\t\trhsMappings->exists( targetMapping : Mapping | \r\n\t\t\t\ttargetMapping.origin = kernelEdge.target \r\n\t\t\t\tand \r\n\t\t\t\ttargetMapping.image = multiEdge.target \r\n\t\t\t\t)\r\n\r\n\t\t\t)\r\n\t\t)\r\n\t)' kernelRhsEdgesMapped.Msg='_Ocl_Msg_AmalgamationUnit_kernelRhsEdgesMapped' lhsMappingsFromKernelToMulti='lhsMappings->forAll(mapping : Mapping | \r\n\tkernelRule.lhs.nodes->includes(mapping.origin)\r\n\tand\r\n\tmultiRules->exists(mRule : Rule |\r\n\t\tmRule.lhs.nodes->includes(mapping.image)\r\n\t )\r\n\t\r\n)' lhsMappingsFromKernelToMulti.Msg='_Ocl_Msg_AmalgamationUnit_lhsMappingsFromKernelToMulti' rhsMappingsFromKernelToMulti='rhsMappings->forAll(mapping : Mapping | \r\n\tkernelRule.rhs.nodes->includes(mapping.origin)\r\n\tand\r\n\tmultiRules->exists(mRule : Rule |\r\n\t\tmRule.rhs.nodes->includes(mapping.image)\r\n\t )\r\n\t\r\n)' rhsMappingsFromKernelToMulti.Msg='_Ocl_Msg_AmalgamationUnit_rhsMappingsFromKernelToMulti' noAdditionalMappingsFromMappedKernel='multiRules->forAll( mRule : Rule | \r\n\tmRule.mappings->forAll(mMapping : Mapping | \r\n\t\tlhsMappings->forAll(lMapping : Mapping| \r\n\t\t\tmMapping.origin = lMapping.image \r\n\t\t\timplies\t\r\n\t\t\trhsMappings->exists(rMapping :Mapping |\r\n\t\t\t\trMapping.image = mMapping.image\r\n \t\t\t\tand\t\t\t\t\r\n\t\t\t\tkernelRule.mappings->exists(kMapping : Mapping | \r\n\t\t\t\t\tkMapping.origin = lMapping.origin\r\n\t\t\t\t\tand\r\n\t\t\t\t\tkMapping.image = rMapping.origin\r\n\t\t\t\t)\r\n\t\t\t)\r\n\t\t)\r\n\t\tand\r\n\t\trhsMappings->forAll(rMapping : Mapping | \r\n\t\t\tmMapping.image = rMapping.image \r\n\t\t\timplies\t\r\n\t\t\tlhsMappings->exists(lMapping :Mapping |\r\n\t\t\t\tlMapping.image = mMapping.origin\r\n \t\t\t\tand\t\t\t\t\r\n\t\t\t\tkernelRule.mappings->exists(kMapping : Mapping | \r\n\t\t\t\t\tkMapping.origin = lMapping.origin\r\n\t\t\t\t\tand\r\n\t\t\t\t\tkMapping.image = rMapping.origin\r\n\t\t\t\t)\r\n\t\t\t)\r\n\t\t)\r\n\t)\r\n)' noAdditionalMappingsFromMappedKernel.Msg='_Ocl_Msg_AmalgamationUnit_noAdditionalMappingsFromMappedKernel'"
 * @generated
 */
public interface AmalgamationUnit extends TransformationUnit {
	/**
	 * Returns the value of the '<em><b>Kernel Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kernel Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kernel Rule</em>' reference.
	 * @see #setKernelRule(Rule)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamationUnit_KernelRule()
	 * @model required="true"
	 * @generated
	 */
	Rule getKernelRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.AmalgamationUnit#getKernelRule <em>Kernel Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kernel Rule</em>' reference.
	 * @see #getKernelRule()
	 * @generated
	 */
	void setKernelRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Multi Rules</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Rule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Rules</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Rules</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamationUnit_MultiRules()
	 * @model required="true"
	 * @generated
	 */
	EList<Rule> getMultiRules();

	/**
	 * Returns the value of the '<em><b>Lhs Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lhs Mappings</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhs Mappings</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamationUnit_LhsMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getLhsMappings();

	/**
	 * Returns the value of the '<em><b>Rhs Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rhs Mappings</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rhs Mappings</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamationUnit_RhsMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getRhsMappings();

} // AmalgamationUnit
