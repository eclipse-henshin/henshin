/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionFactory
 * @model kind="package"
 * @generated
 */
public interface SamgraphconditionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "samgraphcondition";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2015/Henshin/sam/samgraphcondition";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "samgraphcondition";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SamgraphconditionPackage eINSTANCE = org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.ProxyNodeImpl <em>Proxy Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.ProxyNodeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getProxyNode()
	 * @generated
	 */
	int PROXY_NODE = 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_NODE__ATTRIBUTES = SamgraphPackage.NODE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_NODE__ANNOTATIONS = SamgraphPackage.NODE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_NODE__INSTANCE_OF = SamgraphPackage.NODE__INSTANCE_OF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_NODE__NAME = SamgraphPackage.NODE__NAME;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_NODE__OUTGOING = SamgraphPackage.NODE__OUTGOING;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_NODE__INCOMING = SamgraphPackage.NODE__INCOMING;

	/**
	 * The feature id for the '<em><b>Referenced Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_NODE__REFERENCED_NODE = SamgraphPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Proxy Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_NODE_FEATURE_COUNT = SamgraphPackage.NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition <em>Graph Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getGraphCondition()
	 * @generated
	 */
	int GRAPH_CONDITION = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_CONDITION__ANNOTATIONS = SamtypegraphPackage.TYPE_GRAPH_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_CONDITION__NAME = SamtypegraphPackage.TYPE_GRAPH_CONDITION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Graph Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_CONDITION_FEATURE_COUNT = SamtypegraphPackage.TYPE_GRAPH_CONDITION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.LogicalGCCouplingImpl <em>Logical GC Coupling</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.LogicalGCCouplingImpl
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getLogicalGCCoupling()
	 * @generated
	 */
	int LOGICAL_GC_COUPLING = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_GC_COUPLING__ANNOTATIONS = GRAPH_CONDITION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_GC_COUPLING__NAME = GRAPH_CONDITION__NAME;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_GC_COUPLING__OPERATOR = GRAPH_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operands</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_GC_COUPLING__OPERANDS = GRAPH_CONDITION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Logical GC Coupling</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_GC_COUPLING_FEATURE_COUNT = GRAPH_CONDITION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.QuantificationImpl <em>Quantification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.QuantificationImpl
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getQuantification()
	 * @generated
	 */
	int QUANTIFICATION = 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFICATION__ANNOTATIONS = GRAPH_CONDITION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFICATION__NAME = GRAPH_CONDITION__NAME;

	/**
	 * The feature id for the '<em><b>Quantor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFICATION__QUANTOR = GRAPH_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nesting</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFICATION__NESTING = GRAPH_CONDITION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFICATION__CONTEXT = GRAPH_CONDITION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attribute Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFICATION__ATTRIBUTE_CONDITION = GRAPH_CONDITION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Quantification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFICATION_FEATURE_COUNT = GRAPH_CONDITION_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.TerminationConditionImpl <em>Termination Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.TerminationConditionImpl
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getTerminationCondition()
	 * @generated
	 */
	int TERMINATION_CONDITION = 4;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINATION_CONDITION__ANNOTATIONS = GRAPH_CONDITION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINATION_CONDITION__NAME = GRAPH_CONDITION__NAME;

	/**
	 * The feature id for the '<em><b>Is True</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINATION_CONDITION__IS_TRUE = GRAPH_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Termination Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINATION_CONDITION_FEATURE_COUNT = GRAPH_CONDITION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.NegatedConditionImpl <em>Negated Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.NegatedConditionImpl
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getNegatedCondition()
	 * @generated
	 */
	int NEGATED_CONDITION = 5;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATED_CONDITION__ANNOTATIONS = GRAPH_CONDITION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATED_CONDITION__NAME = GRAPH_CONDITION__NAME;

	/**
	 * The feature id for the '<em><b>Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATED_CONDITION__OPERAND = GRAPH_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Negated Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATED_CONDITION_FEATURE_COUNT = GRAPH_CONDITION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantor <em>Quantor</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantor
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getQuantor()
	 * @generated
	 */
	int QUANTOR = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator <em>Logical Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getLogicalOperator()
	 * @generated
	 */
	int LOGICAL_OPERATOR = 7;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.ProxyNode <em>Proxy Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proxy Node</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.ProxyNode
	 * @generated
	 */
	EClass getProxyNode();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.ProxyNode#getReferencedNode <em>Referenced Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Referenced Node</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.ProxyNode#getReferencedNode()
	 * @see #getProxyNode()
	 * @generated
	 */
	EReference getProxyNode_ReferencedNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition <em>Graph Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graph Condition</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition
	 * @generated
	 */
	EClass getGraphCondition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition#getName()
	 * @see #getGraphCondition()
	 * @generated
	 */
	EAttribute getGraphCondition_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling <em>Logical GC Coupling</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Logical GC Coupling</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling
	 * @generated
	 */
	EClass getLogicalGCCoupling();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling#getOperator()
	 * @see #getLogicalGCCoupling()
	 * @generated
	 */
	EAttribute getLogicalGCCoupling_Operator();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling#getOperands <em>Operands</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operands</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling#getOperands()
	 * @see #getLogicalGCCoupling()
	 * @generated
	 */
	EReference getLogicalGCCoupling_Operands();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification <em>Quantification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Quantification</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification
	 * @generated
	 */
	EClass getQuantification();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getQuantor <em>Quantor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Quantor</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getQuantor()
	 * @see #getQuantification()
	 * @generated
	 */
	EAttribute getQuantification_Quantor();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getNesting <em>Nesting</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Nesting</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getNesting()
	 * @see #getQuantification()
	 * @generated
	 */
	EReference getQuantification_Nesting();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Context</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getContext()
	 * @see #getQuantification()
	 * @generated
	 */
	EReference getQuantification_Context();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getAttributeCondition <em>Attribute Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Condition</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getAttributeCondition()
	 * @see #getQuantification()
	 * @generated
	 */
	EReference getQuantification_AttributeCondition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.TerminationCondition <em>Termination Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Termination Condition</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.TerminationCondition
	 * @generated
	 */
	EClass getTerminationCondition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.TerminationCondition#isIsTrue <em>Is True</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is True</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.TerminationCondition#isIsTrue()
	 * @see #getTerminationCondition()
	 * @generated
	 */
	EAttribute getTerminationCondition_IsTrue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition <em>Negated Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Negated Condition</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition
	 * @generated
	 */
	EClass getNegatedCondition();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition#getOperand <em>Operand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Operand</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition#getOperand()
	 * @see #getNegatedCondition()
	 * @generated
	 */
	EReference getNegatedCondition_Operand();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantor <em>Quantor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Quantor</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantor
	 * @generated
	 */
	EEnum getQuantor();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator <em>Logical Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Logical Operator</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator
	 * @generated
	 */
	EEnum getLogicalOperator();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SamgraphconditionFactory getSamgraphconditionFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.ProxyNodeImpl <em>Proxy Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.ProxyNodeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getProxyNode()
		 * @generated
		 */
		EClass PROXY_NODE = eINSTANCE.getProxyNode();

		/**
		 * The meta object literal for the '<em><b>Referenced Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROXY_NODE__REFERENCED_NODE = eINSTANCE.getProxyNode_ReferencedNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition <em>Graph Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getGraphCondition()
		 * @generated
		 */
		EClass GRAPH_CONDITION = eINSTANCE.getGraphCondition();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPH_CONDITION__NAME = eINSTANCE.getGraphCondition_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.LogicalGCCouplingImpl <em>Logical GC Coupling</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.LogicalGCCouplingImpl
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getLogicalGCCoupling()
		 * @generated
		 */
		EClass LOGICAL_GC_COUPLING = eINSTANCE.getLogicalGCCoupling();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGICAL_GC_COUPLING__OPERATOR = eINSTANCE.getLogicalGCCoupling_Operator();

		/**
		 * The meta object literal for the '<em><b>Operands</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOGICAL_GC_COUPLING__OPERANDS = eINSTANCE.getLogicalGCCoupling_Operands();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.QuantificationImpl <em>Quantification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.QuantificationImpl
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getQuantification()
		 * @generated
		 */
		EClass QUANTIFICATION = eINSTANCE.getQuantification();

		/**
		 * The meta object literal for the '<em><b>Quantor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUANTIFICATION__QUANTOR = eINSTANCE.getQuantification_Quantor();

		/**
		 * The meta object literal for the '<em><b>Nesting</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFICATION__NESTING = eINSTANCE.getQuantification_Nesting();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFICATION__CONTEXT = eINSTANCE.getQuantification_Context();

		/**
		 * The meta object literal for the '<em><b>Attribute Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFICATION__ATTRIBUTE_CONDITION = eINSTANCE.getQuantification_AttributeCondition();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.TerminationConditionImpl <em>Termination Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.TerminationConditionImpl
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getTerminationCondition()
		 * @generated
		 */
		EClass TERMINATION_CONDITION = eINSTANCE.getTerminationCondition();

		/**
		 * The meta object literal for the '<em><b>Is True</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TERMINATION_CONDITION__IS_TRUE = eINSTANCE.getTerminationCondition_IsTrue();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.NegatedConditionImpl <em>Negated Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.NegatedConditionImpl
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getNegatedCondition()
		 * @generated
		 */
		EClass NEGATED_CONDITION = eINSTANCE.getNegatedCondition();

		/**
		 * The meta object literal for the '<em><b>Operand</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NEGATED_CONDITION__OPERAND = eINSTANCE.getNegatedCondition_Operand();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantor <em>Quantor</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantor
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getQuantor()
		 * @generated
		 */
		EEnum QUANTOR = eINSTANCE.getQuantor();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator <em>Logical Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator
		 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl#getLogicalOperator()
		 * @generated
		 */
		EEnum LOGICAL_OPERATOR = eINSTANCE.getLogicalOperator();

	}

} //SamgraphconditionPackage
