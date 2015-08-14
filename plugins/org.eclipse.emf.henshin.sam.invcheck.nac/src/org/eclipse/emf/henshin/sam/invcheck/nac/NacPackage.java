/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.invcheck.nac;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage;

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
 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory
 * @model kind="package"
 * @generated
 */
public interface NacPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "nac";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.emf.henshin.sam.invcheck.nac";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.henshin.sam.invcheck.nac";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NacPackage eINSTANCE = org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.NegativeApplicationConditionImpl <em>Negative Application Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NegativeApplicationConditionImpl
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacPackageImpl#getNegativeApplicationCondition()
	 * @generated
	 */
	int NEGATIVE_APPLICATION_CONDITION = 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_APPLICATION_CONDITION__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_APPLICATION_CONDITION__GRAPH = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_APPLICATION_CONDITION__EDGES = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_APPLICATION_CONDITION__NODES = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_APPLICATION_CONDITION__NAME = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Negative Application Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_APPLICATION_CONDITION_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 4;


	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.MatchWithNacsImpl <em>Match With Nacs</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.MatchWithNacsImpl
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacPackageImpl#getMatchWithNacs()
	 * @generated
	 */
	int MATCH_WITH_NACS = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_WITH_NACS__ANNOTATIONS = SamtracePackage.MATCH__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Node Matching</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_WITH_NACS__NODE_MATCHING = SamtracePackage.MATCH__NODE_MATCHING;

	/**
	 * The feature id for the '<em><b>Edge Matching</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_WITH_NACS__EDGE_MATCHING = SamtracePackage.MATCH__EDGE_MATCHING;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_WITH_NACS__SIZE = SamtracePackage.MATCH__SIZE;

	/**
	 * The feature id for the '<em><b>Nac Matching</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_WITH_NACS__NAC_MATCHING = SamtracePackage.MATCH_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Match With Nacs</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_WITH_NACS_FEATURE_COUNT = SamtracePackage.MATCH_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs <em>Graph With Nacs</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacPackageImpl#getGraphWithNacs()
	 * @generated
	 */
	int GRAPH_WITH_NACS = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_WITH_NACS__ANNOTATIONS = SamgraphPackage.GRAPH__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_WITH_NACS__NODES = SamgraphPackage.GRAPH__NODES;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_WITH_NACS__EDGES = SamgraphPackage.GRAPH__EDGES;

	/**
	 * The feature id for the '<em><b>Typed Over</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_WITH_NACS__TYPED_OVER = SamgraphPackage.GRAPH__TYPED_OVER;

	/**
	 * The feature id for the '<em><b>Attribute Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_WITH_NACS__ATTRIBUTE_CONDITION = SamgraphPackage.GRAPH__ATTRIBUTE_CONDITION;

	/**
	 * The feature id for the '<em><b>Nacs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_WITH_NACS__NACS = SamgraphPackage.GRAPH_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Graph With Nacs</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_WITH_NACS_FEATURE_COUNT = SamgraphPackage.GRAPH_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternNodeImpl <em>Pattern Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternNodeImpl
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacPackageImpl#getPatternNode()
	 * @generated
	 */
	int PATTERN_NODE = 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_NODE__ATTRIBUTES = SamgraphPackage.NODE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_NODE__ANNOTATIONS = SamgraphPackage.NODE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_NODE__INSTANCE_OF = SamgraphPackage.NODE__INSTANCE_OF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_NODE__NAME = SamgraphPackage.NODE__NAME;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_NODE__OUTGOING = SamgraphPackage.NODE__OUTGOING;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_NODE__INCOMING = SamgraphPackage.NODE__INCOMING;

	/**
	 * The feature id for the '<em><b>Same In Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_NODE__SAME_IN_RULE = SamgraphPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Same In Prop</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_NODE__SAME_IN_PROP = SamgraphPackage.NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Pattern Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_NODE_FEATURE_COUNT = SamgraphPackage.NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternEdgeImpl <em>Pattern Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternEdgeImpl
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacPackageImpl#getPatternEdge()
	 * @generated
	 */
	int PATTERN_EDGE = 4;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_EDGE__ATTRIBUTES = SamgraphPackage.EDGE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_EDGE__ANNOTATIONS = SamgraphPackage.EDGE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_EDGE__SOURCE = SamgraphPackage.EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_EDGE__TARGET = SamgraphPackage.EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_EDGE__INSTANCE_OF = SamgraphPackage.EDGE__INSTANCE_OF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_EDGE__NAME = SamgraphPackage.EDGE__NAME;

	/**
	 * The feature id for the '<em><b>Same In Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_EDGE__SAME_IN_RULE = SamgraphPackage.EDGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Same In Prop</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_EDGE__SAME_IN_PROP = SamgraphPackage.EDGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Pattern Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_EDGE_FEATURE_COUNT = SamgraphPackage.EDGE_FEATURE_COUNT + 2;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition <em>Negative Application Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Negative Application Condition</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition
	 * @generated
	 */
	EClass getNegativeApplicationCondition();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Graph</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition#getGraph()
	 * @see #getNegativeApplicationCondition()
	 * @generated
	 */
	EReference getNegativeApplicationCondition_Graph();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edges</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition#getEdges()
	 * @see #getNegativeApplicationCondition()
	 * @generated
	 */
	EReference getNegativeApplicationCondition_Edges();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition#getNodes()
	 * @see #getNegativeApplicationCondition()
	 * @generated
	 */
	EReference getNegativeApplicationCondition_Nodes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition#getName()
	 * @see #getNegativeApplicationCondition()
	 * @generated
	 */
	EAttribute getNegativeApplicationCondition_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.invcheck.nac.MatchWithNacs <em>Match With Nacs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Match With Nacs</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.MatchWithNacs
	 * @generated
	 */
	EClass getMatchWithNacs();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.henshin.sam.invcheck.nac.MatchWithNacs#getNacMatching <em>Nac Matching</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Nac Matching</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.MatchWithNacs#getNacMatching()
	 * @see #getMatchWithNacs()
	 * @generated
	 */
	EReference getMatchWithNacs_NacMatching();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs <em>Graph With Nacs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graph With Nacs</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs
	 * @generated
	 */
	EClass getGraphWithNacs();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs#getNacs <em>Nacs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Nacs</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs#getNacs()
	 * @see #getGraphWithNacs()
	 * @generated
	 */
	EReference getGraphWithNacs_Nacs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode <em>Pattern Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pattern Node</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode
	 * @generated
	 */
	EClass getPatternNode();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode#getSameInRule <em>Same In Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Same In Rule</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode#getSameInRule()
	 * @see #getPatternNode()
	 * @generated
	 */
	EReference getPatternNode_SameInRule();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode#getSameInProp <em>Same In Prop</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Same In Prop</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode#getSameInProp()
	 * @see #getPatternNode()
	 * @generated
	 */
	EReference getPatternNode_SameInProp();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge <em>Pattern Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pattern Edge</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge
	 * @generated
	 */
	EClass getPatternEdge();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge#getSameInRule <em>Same In Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Same In Rule</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge#getSameInRule()
	 * @see #getPatternEdge()
	 * @generated
	 */
	EReference getPatternEdge_SameInRule();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge#getSameInProp <em>Same In Prop</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Same In Prop</em>'.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge#getSameInProp()
	 * @see #getPatternEdge()
	 * @generated
	 */
	EReference getPatternEdge_SameInProp();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NacFactory getNacFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.NegativeApplicationConditionImpl <em>Negative Application Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NegativeApplicationConditionImpl
		 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacPackageImpl#getNegativeApplicationCondition()
		 * @generated
		 */
		EClass NEGATIVE_APPLICATION_CONDITION = eINSTANCE.getNegativeApplicationCondition();

		/**
		 * The meta object literal for the '<em><b>Graph</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NEGATIVE_APPLICATION_CONDITION__GRAPH = eINSTANCE.getNegativeApplicationCondition_Graph();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NEGATIVE_APPLICATION_CONDITION__EDGES = eINSTANCE.getNegativeApplicationCondition_Edges();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NEGATIVE_APPLICATION_CONDITION__NODES = eINSTANCE.getNegativeApplicationCondition_Nodes();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NEGATIVE_APPLICATION_CONDITION__NAME = eINSTANCE.getNegativeApplicationCondition_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.MatchWithNacsImpl <em>Match With Nacs</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.MatchWithNacsImpl
		 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacPackageImpl#getMatchWithNacs()
		 * @generated
		 */
		EClass MATCH_WITH_NACS = eINSTANCE.getMatchWithNacs();

		/**
		 * The meta object literal for the '<em><b>Nac Matching</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MATCH_WITH_NACS__NAC_MATCHING = eINSTANCE.getMatchWithNacs_NacMatching();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs <em>Graph With Nacs</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs
		 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacPackageImpl#getGraphWithNacs()
		 * @generated
		 */
		EClass GRAPH_WITH_NACS = eINSTANCE.getGraphWithNacs();

		/**
		 * The meta object literal for the '<em><b>Nacs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH_WITH_NACS__NACS = eINSTANCE.getGraphWithNacs_Nacs();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternNodeImpl <em>Pattern Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternNodeImpl
		 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacPackageImpl#getPatternNode()
		 * @generated
		 */
		EClass PATTERN_NODE = eINSTANCE.getPatternNode();

		/**
		 * The meta object literal for the '<em><b>Same In Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN_NODE__SAME_IN_RULE = eINSTANCE.getPatternNode_SameInRule();

		/**
		 * The meta object literal for the '<em><b>Same In Prop</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN_NODE__SAME_IN_PROP = eINSTANCE.getPatternNode_SameInProp();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternEdgeImpl <em>Pattern Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternEdgeImpl
		 * @see org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacPackageImpl#getPatternEdge()
		 * @generated
		 */
		EClass PATTERN_EDGE = eINSTANCE.getPatternEdge();

		/**
		 * The meta object literal for the '<em><b>Same In Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN_EDGE__SAME_IN_RULE = eINSTANCE.getPatternEdge_SameInRule();

		/**
		 * The meta object literal for the '<em><b>Same In Prop</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN_EDGE__SAME_IN_PROP = eINSTANCE.getPatternEdge_SameInProp();

	}

} //NacPackage
