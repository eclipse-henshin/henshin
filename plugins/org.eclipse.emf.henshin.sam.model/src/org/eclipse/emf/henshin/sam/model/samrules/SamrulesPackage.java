/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samrules;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;

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
 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory
 * @model kind="package"
 * @generated
 */
public interface SamrulesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "samrules";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2015/Henshin/sam/rules";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "samrules";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SamrulesPackage eINSTANCE = org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.GraphRuleImpl <em>Graph Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.GraphRuleImpl
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getGraphRule()
	 * @generated
	 */
	int GRAPH_RULE = 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_RULE__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_RULE__LEFT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_RULE__RIGHT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attribute Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_RULE__ATTRIBUTE_CONDITION = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_RULE__PRIORITY = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Urgent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_RULE__URGENT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_RULE__NAME = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Graph Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_RULE_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.PreservedNodeImpl <em>Preserved Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.PreservedNodeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getPreservedNode()
	 * @generated
	 */
	int PRESERVED_NODE = 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_NODE__ATTRIBUTES = SamgraphPackage.NODE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_NODE__ANNOTATIONS = SamgraphPackage.NODE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_NODE__INSTANCE_OF = SamgraphPackage.NODE__INSTANCE_OF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_NODE__NAME = SamgraphPackage.NODE__NAME;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_NODE__OUTGOING = SamgraphPackage.NODE__OUTGOING;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_NODE__INCOMING = SamgraphPackage.NODE__INCOMING;

	/**
	 * The feature id for the '<em><b>Ref In Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_NODE__REF_IN_RULE = SamgraphPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Preserved Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_NODE_FEATURE_COUNT = SamgraphPackage.NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.PreservedEdgeImpl <em>Preserved Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.PreservedEdgeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getPreservedEdge()
	 * @generated
	 */
	int PRESERVED_EDGE = 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_EDGE__ATTRIBUTES = SamgraphPackage.EDGE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_EDGE__ANNOTATIONS = SamgraphPackage.EDGE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_EDGE__SOURCE = SamgraphPackage.EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_EDGE__TARGET = SamgraphPackage.EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_EDGE__INSTANCE_OF = SamgraphPackage.EDGE__INSTANCE_OF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_EDGE__NAME = SamgraphPackage.EDGE__NAME;

	/**
	 * The feature id for the '<em><b>Ref In Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_EDGE__REF_IN_RULE = SamgraphPackage.EDGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Preserved Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRESERVED_EDGE_FEATURE_COUNT = SamgraphPackage.EDGE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.RuleGraphImpl <em>Rule Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.RuleGraphImpl
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getRuleGraph()
	 * @generated
	 */
	int RULE_GRAPH = 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_GRAPH__ANNOTATIONS = SamgraphPackage.GRAPH__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_GRAPH__NODES = SamgraphPackage.GRAPH__NODES;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_GRAPH__EDGES = SamgraphPackage.GRAPH__EDGES;

	/**
	 * The feature id for the '<em><b>Typed Over</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_GRAPH__TYPED_OVER = SamgraphPackage.GRAPH__TYPED_OVER;

	/**
	 * The feature id for the '<em><b>Attribute Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_GRAPH__ATTRIBUTE_CONDITION = SamgraphPackage.GRAPH__ATTRIBUTE_CONDITION;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_GRAPH__CONDITION = SamgraphPackage.GRAPH_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Rule Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_GRAPH_FEATURE_COUNT = SamgraphPackage.GRAPH_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.GTSImpl <em>GTS</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.GTSImpl
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getGTS()
	 * @generated
	 */
	int GTS = 4;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GTS__RULES = 0;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GTS__TYPES = 1;

	/**
	 * The number of structural features of the '<em>GTS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GTS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.CreatedNodeImpl <em>Created Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.CreatedNodeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getCreatedNode()
	 * @generated
	 */
	int CREATED_NODE = 5;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_NODE__ATTRIBUTES = SamgraphPackage.NODE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_NODE__ANNOTATIONS = SamgraphPackage.NODE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_NODE__INSTANCE_OF = SamgraphPackage.NODE__INSTANCE_OF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_NODE__NAME = SamgraphPackage.NODE__NAME;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_NODE__OUTGOING = SamgraphPackage.NODE__OUTGOING;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_NODE__INCOMING = SamgraphPackage.NODE__INCOMING;

	/**
	 * The number of structural features of the '<em>Created Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_NODE_FEATURE_COUNT = SamgraphPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.DeletedNodeImpl <em>Deleted Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.DeletedNodeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getDeletedNode()
	 * @generated
	 */
	int DELETED_NODE = 6;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_NODE__ATTRIBUTES = SamgraphPackage.NODE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_NODE__ANNOTATIONS = SamgraphPackage.NODE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_NODE__INSTANCE_OF = SamgraphPackage.NODE__INSTANCE_OF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_NODE__NAME = SamgraphPackage.NODE__NAME;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_NODE__OUTGOING = SamgraphPackage.NODE__OUTGOING;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_NODE__INCOMING = SamgraphPackage.NODE__INCOMING;

	/**
	 * The number of structural features of the '<em>Deleted Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_NODE_FEATURE_COUNT = SamgraphPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.CreatedEdgeImpl <em>Created Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.CreatedEdgeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getCreatedEdge()
	 * @generated
	 */
	int CREATED_EDGE = 7;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_EDGE__ATTRIBUTES = SamgraphPackage.EDGE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_EDGE__ANNOTATIONS = SamgraphPackage.EDGE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_EDGE__SOURCE = SamgraphPackage.EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_EDGE__TARGET = SamgraphPackage.EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_EDGE__INSTANCE_OF = SamgraphPackage.EDGE__INSTANCE_OF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_EDGE__NAME = SamgraphPackage.EDGE__NAME;

	/**
	 * The number of structural features of the '<em>Created Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATED_EDGE_FEATURE_COUNT = SamgraphPackage.EDGE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.DeletedEdgeImpl <em>Deleted Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.DeletedEdgeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getDeletedEdge()
	 * @generated
	 */
	int DELETED_EDGE = 8;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_EDGE__ATTRIBUTES = SamgraphPackage.EDGE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_EDGE__ANNOTATIONS = SamgraphPackage.EDGE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_EDGE__SOURCE = SamgraphPackage.EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_EDGE__TARGET = SamgraphPackage.EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_EDGE__INSTANCE_OF = SamgraphPackage.EDGE__INSTANCE_OF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_EDGE__NAME = SamgraphPackage.EDGE__NAME;

	/**
	 * The number of structural features of the '<em>Deleted Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETED_EDGE_FEATURE_COUNT = SamgraphPackage.EDGE_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule <em>Graph Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graph Rule</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.GraphRule
	 * @generated
	 */
	EClass getGraphRule();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getLeft()
	 * @see #getGraphRule()
	 * @generated
	 */
	EReference getGraphRule_Left();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getRight()
	 * @see #getGraphRule()
	 * @generated
	 */
	EReference getGraphRule_Right();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getAttributeCondition <em>Attribute Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Condition</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getAttributeCondition()
	 * @see #getGraphRule()
	 * @generated
	 */
	EReference getGraphRule_AttributeCondition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getPriority()
	 * @see #getGraphRule()
	 * @generated
	 */
	EAttribute getGraphRule_Priority();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#isUrgent <em>Urgent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Urgent</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.GraphRule#isUrgent()
	 * @see #getGraphRule()
	 * @generated
	 */
	EAttribute getGraphRule_Urgent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getName()
	 * @see #getGraphRule()
	 * @generated
	 */
	EAttribute getGraphRule_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samrules.PreservedNode <em>Preserved Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Preserved Node</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.PreservedNode
	 * @generated
	 */
	EClass getPreservedNode();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samrules.PreservedNode#getRefInRule <em>Ref In Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ref In Rule</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.PreservedNode#getRefInRule()
	 * @see #getPreservedNode()
	 * @generated
	 */
	EReference getPreservedNode_RefInRule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samrules.PreservedEdge <em>Preserved Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Preserved Edge</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.PreservedEdge
	 * @generated
	 */
	EClass getPreservedEdge();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samrules.PreservedEdge#getRefInRule <em>Ref In Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ref In Rule</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.PreservedEdge#getRefInRule()
	 * @see #getPreservedEdge()
	 * @generated
	 */
	EReference getPreservedEdge_RefInRule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samrules.RuleGraph <em>Rule Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Graph</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.RuleGraph
	 * @generated
	 */
	EClass getRuleGraph();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samrules.RuleGraph#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.RuleGraph#getCondition()
	 * @see #getRuleGraph()
	 * @generated
	 */
	EReference getRuleGraph_Condition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samrules.GTS <em>GTS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GTS</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.GTS
	 * @generated
	 */
	EClass getGTS();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samrules.GTS#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.GTS#getRules()
	 * @see #getGTS()
	 * @generated
	 */
	EReference getGTS_Rules();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samrules.GTS#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.GTS#getTypes()
	 * @see #getGTS()
	 * @generated
	 */
	EReference getGTS_Types();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samrules.CreatedNode <em>Created Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Created Node</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.CreatedNode
	 * @generated
	 */
	EClass getCreatedNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samrules.DeletedNode <em>Deleted Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deleted Node</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.DeletedNode
	 * @generated
	 */
	EClass getDeletedNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samrules.CreatedEdge <em>Created Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Created Edge</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.CreatedEdge
	 * @generated
	 */
	EClass getCreatedEdge();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samrules.DeletedEdge <em>Deleted Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deleted Edge</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.DeletedEdge
	 * @generated
	 */
	EClass getDeletedEdge();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SamrulesFactory getSamrulesFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.GraphRuleImpl <em>Graph Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.GraphRuleImpl
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getGraphRule()
		 * @generated
		 */
		EClass GRAPH_RULE = eINSTANCE.getGraphRule();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH_RULE__LEFT = eINSTANCE.getGraphRule_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH_RULE__RIGHT = eINSTANCE.getGraphRule_Right();

		/**
		 * The meta object literal for the '<em><b>Attribute Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH_RULE__ATTRIBUTE_CONDITION = eINSTANCE.getGraphRule_AttributeCondition();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPH_RULE__PRIORITY = eINSTANCE.getGraphRule_Priority();

		/**
		 * The meta object literal for the '<em><b>Urgent</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPH_RULE__URGENT = eINSTANCE.getGraphRule_Urgent();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPH_RULE__NAME = eINSTANCE.getGraphRule_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.PreservedNodeImpl <em>Preserved Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.PreservedNodeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getPreservedNode()
		 * @generated
		 */
		EClass PRESERVED_NODE = eINSTANCE.getPreservedNode();

		/**
		 * The meta object literal for the '<em><b>Ref In Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRESERVED_NODE__REF_IN_RULE = eINSTANCE.getPreservedNode_RefInRule();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.PreservedEdgeImpl <em>Preserved Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.PreservedEdgeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getPreservedEdge()
		 * @generated
		 */
		EClass PRESERVED_EDGE = eINSTANCE.getPreservedEdge();

		/**
		 * The meta object literal for the '<em><b>Ref In Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRESERVED_EDGE__REF_IN_RULE = eINSTANCE.getPreservedEdge_RefInRule();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.RuleGraphImpl <em>Rule Graph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.RuleGraphImpl
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getRuleGraph()
		 * @generated
		 */
		EClass RULE_GRAPH = eINSTANCE.getRuleGraph();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_GRAPH__CONDITION = eINSTANCE.getRuleGraph_Condition();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.GTSImpl <em>GTS</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.GTSImpl
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getGTS()
		 * @generated
		 */
		EClass GTS = eINSTANCE.getGTS();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GTS__RULES = eINSTANCE.getGTS_Rules();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GTS__TYPES = eINSTANCE.getGTS_Types();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.CreatedNodeImpl <em>Created Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.CreatedNodeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getCreatedNode()
		 * @generated
		 */
		EClass CREATED_NODE = eINSTANCE.getCreatedNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.DeletedNodeImpl <em>Deleted Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.DeletedNodeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getDeletedNode()
		 * @generated
		 */
		EClass DELETED_NODE = eINSTANCE.getDeletedNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.CreatedEdgeImpl <em>Created Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.CreatedEdgeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getCreatedEdge()
		 * @generated
		 */
		EClass CREATED_EDGE = eINSTANCE.getCreatedEdge();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samrules.impl.DeletedEdgeImpl <em>Deleted Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.DeletedEdgeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl#getDeletedEdge()
		 * @generated
		 */
		EClass DELETED_EDGE = eINSTANCE.getDeletedEdge();

	}

} //SamrulesPackage
