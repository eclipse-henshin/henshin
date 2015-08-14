/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;

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
 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphFactory
 * @model kind="package"
 * @generated
 */
public interface SamtypegraphPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "samtypegraph";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2015/Henshin/sam/typegraph";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "samtypegraph";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SamtypegraphPackage eINSTANCE = org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem <em>Attributed Elem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getAttributedElem()
	 * @generated
	 */
	int ATTRIBUTED_ELEM = 9;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTED_ELEM__ATTRIBUTES = 0;

	/**
	 * The number of structural features of the '<em>Attributed Elem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTED_ELEM_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl <em>Node Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getNodeType()
	 * @generated
	 */
	int NODE_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TYPE__ATTRIBUTES = ATTRIBUTED_ELEM__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TYPE__ANNOTATIONS = ATTRIBUTED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TYPE__OUTGOING = ATTRIBUTED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TYPE__INCOMING = ATTRIBUTED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TYPE__NAME = ATTRIBUTED_ELEM_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TYPE__TYPE_GRAPH = ATTRIBUTED_ELEM_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Super Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TYPE__SUPER_TYPE = ATTRIBUTED_ELEM_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Sub Type</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TYPE__SUB_TYPE = ATTRIBUTED_ELEM_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Node Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TYPE_FEATURE_COUNT = ATTRIBUTED_ELEM_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl <em>Edge Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getEdgeType()
	 * @generated
	 */
	int EDGE_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_TYPE__ATTRIBUTES = ATTRIBUTED_ELEM__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_TYPE__ANNOTATIONS = ATTRIBUTED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_TYPE__SOURCE = ATTRIBUTED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_TYPE__TARGET = ATTRIBUTED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_TYPE__NAME = ATTRIBUTED_ELEM_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_TYPE__TYPE_GRAPH = ATTRIBUTED_ELEM_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_TYPE__DIRECTION = ATTRIBUTED_ELEM_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Source Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_TYPE__SOURCE_CARDINALITY = ATTRIBUTED_ELEM_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Target Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_TYPE__TARGET_CARDINALITY = ATTRIBUTED_ELEM_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Edge Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_TYPE_FEATURE_COUNT = ATTRIBUTED_ELEM_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeImpl <em>Attribute Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getAttributeType()
	 * @generated
	 */
	int ATTRIBUTE_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__NAME = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sort</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__SORT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributed Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Attribute Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.ContinuousAttrImpl <em>Continuous Attr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.ContinuousAttrImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getContinuousAttr()
	 * @generated
	 */
	int CONTINUOUS_ATTR = 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_ATTR__ANNOTATIONS = ATTRIBUTE_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_ATTR__NAME = ATTRIBUTE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Sort</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_ATTR__SORT = ATTRIBUTE_TYPE__SORT;

	/**
	 * The feature id for the '<em><b>Attributed Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_ATTR__ATTRIBUTED_ELEMENT = ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Derivation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_ATTR__DERIVATION = ATTRIBUTE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_ATTR__LOWER_BOUND = ATTRIBUTE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_ATTR__UPPER_BOUND = ATTRIBUTE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Continuous Attr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_ATTR_FEATURE_COUNT = ATTRIBUTE_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.InheritanceRelationImpl <em>Inheritance Relation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.InheritanceRelationImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getInheritanceRelation()
	 * @generated
	 */
	int INHERITANCE_RELATION = 4;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INHERITANCE_RELATION__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Sub Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INHERITANCE_RELATION__SUB_TYPE = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Super Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INHERITANCE_RELATION__SUPER_TYPE = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Inheritance Relation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INHERITANCE_RELATION_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.TypeGraphImpl <em>Type Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.TypeGraphImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getTypeGraph()
	 * @generated
	 */
	int TYPE_GRAPH = 5;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_GRAPH__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Node Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_GRAPH__NODE_TYPES = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Edge Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_GRAPH__EDGE_TYPES = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_GRAPH__NAME = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_GRAPH__CONDITIONS = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Type Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_GRAPH_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition <em>Type Graph Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getTypeGraphCondition()
	 * @generated
	 */
	int TYPE_GRAPH_CONDITION = 6;

	/**
	 * The number of structural features of the '<em>Type Graph Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_GRAPH_CONDITION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeConditionImpl <em>Attribute Type Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeConditionImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getAttributeTypeCondition()
	 * @generated
	 */
	int ATTRIBUTE_TYPE_CONDITION = 7;

	/**
	 * The feature id for the '<em><b>Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE_CONDITION__OPERATION = TYPE_GRAPH_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Attribute Type Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE_CONDITION_FEATURE_COUNT = TYPE_GRAPH_CONDITION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.CardinalityImpl <em>Cardinality</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.CardinalityImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getCardinality()
	 * @generated
	 */
	int CARDINALITY = 8;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARDINALITY__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARDINALITY__LOWER_BOUND = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARDINALITY__UPPER_BOUND = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Cardinality</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARDINALITY_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.DataTypeImpl <em>Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.DataTypeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 10;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__ATTRIBUTES = NODE_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__ANNOTATIONS = NODE_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__OUTGOING = NODE_TYPE__OUTGOING;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__INCOMING = NODE_TYPE__INCOMING;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__NAME = NODE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Type Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__TYPE_GRAPH = NODE_TYPE__TYPE_GRAPH;

	/**
	 * The feature id for the '<em><b>Super Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__SUPER_TYPE = NODE_TYPE__SUPER_TYPE;

	/**
	 * The feature id for the '<em><b>Sub Type</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__SUB_TYPE = NODE_TYPE__SUB_TYPE;

	/**
	 * The feature id for the '<em><b>Sort</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__SORT = NODE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_FEATURE_COUNT = NODE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection <em>Edge Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getEdgeDirection()
	 * @generated
	 */
	int EDGE_DIRECTION = 11;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType <em>Node Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Type</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType
	 * @generated
	 */
	EClass getNodeType();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getOutgoing()
	 * @see #getNodeType()
	 * @generated
	 */
	EReference getNodeType_Outgoing();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getIncoming()
	 * @see #getNodeType()
	 * @generated
	 */
	EReference getNodeType_Incoming();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getName()
	 * @see #getNodeType()
	 * @generated
	 */
	EAttribute getNodeType_Name();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getTypeGraph <em>Type Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Type Graph</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getTypeGraph()
	 * @see #getNodeType()
	 * @generated
	 */
	EReference getNodeType_TypeGraph();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getSuperType <em>Super Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Super Type</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getSuperType()
	 * @see #getNodeType()
	 * @generated
	 */
	EReference getNodeType_SuperType();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getSubType <em>Sub Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Type</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getSubType()
	 * @see #getNodeType()
	 * @generated
	 */
	EReference getNodeType_SubType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType <em>Edge Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edge Type</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType
	 * @generated
	 */
	EClass getEdgeType();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getSource()
	 * @see #getEdgeType()
	 * @generated
	 */
	EReference getEdgeType_Source();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTarget()
	 * @see #getEdgeType()
	 * @generated
	 */
	EReference getEdgeType_Target();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getName()
	 * @see #getEdgeType()
	 * @generated
	 */
	EAttribute getEdgeType_Name();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTypeGraph <em>Type Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Type Graph</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTypeGraph()
	 * @see #getEdgeType()
	 * @generated
	 */
	EReference getEdgeType_TypeGraph();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getDirection()
	 * @see #getEdgeType()
	 * @generated
	 */
	EAttribute getEdgeType_Direction();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getSourceCardinality <em>Source Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source Cardinality</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getSourceCardinality()
	 * @see #getEdgeType()
	 * @generated
	 */
	EReference getEdgeType_SourceCardinality();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTargetCardinality <em>Target Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target Cardinality</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTargetCardinality()
	 * @see #getEdgeType()
	 * @generated
	 */
	EReference getEdgeType_TargetCardinality();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Type</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType
	 * @generated
	 */
	EClass getAttributeType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getName()
	 * @see #getAttributeType()
	 * @generated
	 */
	EAttribute getAttributeType_Name();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getSort <em>Sort</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sort</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getSort()
	 * @see #getAttributeType()
	 * @generated
	 */
	EReference getAttributeType_Sort();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getAttributedElement <em>Attributed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Attributed Element</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getAttributedElement()
	 * @see #getAttributeType()
	 * @generated
	 */
	EReference getAttributeType_AttributedElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr <em>Continuous Attr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Continuous Attr</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr
	 * @generated
	 */
	EClass getContinuousAttr();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getDerivation <em>Derivation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Derivation</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getDerivation()
	 * @see #getContinuousAttr()
	 * @generated
	 */
	EAttribute getContinuousAttr_Derivation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getLowerBound <em>Lower Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Bound</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getLowerBound()
	 * @see #getContinuousAttr()
	 * @generated
	 */
	EAttribute getContinuousAttr_LowerBound();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getUpperBound <em>Upper Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Bound</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getUpperBound()
	 * @see #getContinuousAttr()
	 * @generated
	 */
	EAttribute getContinuousAttr_UpperBound();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation <em>Inheritance Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inheritance Relation</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation
	 * @generated
	 */
	EClass getInheritanceRelation();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSubType <em>Sub Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Sub Type</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSubType()
	 * @see #getInheritanceRelation()
	 * @generated
	 */
	EReference getInheritanceRelation_SubType();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSuperType <em>Super Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Super Type</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSuperType()
	 * @see #getInheritanceRelation()
	 * @generated
	 */
	EReference getInheritanceRelation_SuperType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph <em>Type Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Graph</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph
	 * @generated
	 */
	EClass getTypeGraph();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getNodeTypes <em>Node Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Node Types</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getNodeTypes()
	 * @see #getTypeGraph()
	 * @generated
	 */
	EReference getTypeGraph_NodeTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getEdgeTypes <em>Edge Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edge Types</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getEdgeTypes()
	 * @see #getTypeGraph()
	 * @generated
	 */
	EReference getTypeGraph_EdgeTypes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getName()
	 * @see #getTypeGraph()
	 * @generated
	 */
	EAttribute getTypeGraph_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conditions</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getConditions()
	 * @see #getTypeGraph()
	 * @generated
	 */
	EReference getTypeGraph_Conditions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition <em>Type Graph Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Graph Condition</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition
	 * @generated
	 */
	EClass getTypeGraphCondition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeTypeCondition <em>Attribute Type Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Type Condition</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeTypeCondition
	 * @generated
	 */
	EClass getAttributeTypeCondition();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeTypeCondition#getOperation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Operation</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeTypeCondition#getOperation()
	 * @see #getAttributeTypeCondition()
	 * @generated
	 */
	EReference getAttributeTypeCondition_Operation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.Cardinality <em>Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cardinality</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.Cardinality
	 * @generated
	 */
	EClass getCardinality();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.Cardinality#getLowerBound <em>Lower Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Bound</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.Cardinality#getLowerBound()
	 * @see #getCardinality()
	 * @generated
	 */
	EAttribute getCardinality_LowerBound();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.Cardinality#getUpperBound <em>Upper Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Bound</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.Cardinality#getUpperBound()
	 * @see #getCardinality()
	 * @generated
	 */
	EAttribute getCardinality_UpperBound();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem <em>Attributed Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attributed Elem</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem
	 * @generated
	 */
	EClass getAttributedElem();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem#getAttributes()
	 * @see #getAttributedElem()
	 * @generated
	 */
	EReference getAttributedElem_Attributes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Type</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.DataType
	 * @generated
	 */
	EClass getDataType();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.DataType#getSort <em>Sort</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sort</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.DataType#getSort()
	 * @see #getDataType()
	 * @generated
	 */
	EReference getDataType_Sort();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection <em>Edge Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Edge Direction</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection
	 * @generated
	 */
	EEnum getEdgeDirection();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SamtypegraphFactory getSamtypegraphFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl <em>Node Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getNodeType()
		 * @generated
		 */
		EClass NODE_TYPE = eINSTANCE.getNodeType();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TYPE__OUTGOING = eINSTANCE.getNodeType_Outgoing();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TYPE__INCOMING = eINSTANCE.getNodeType_Incoming();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_TYPE__NAME = eINSTANCE.getNodeType_Name();

		/**
		 * The meta object literal for the '<em><b>Type Graph</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TYPE__TYPE_GRAPH = eINSTANCE.getNodeType_TypeGraph();

		/**
		 * The meta object literal for the '<em><b>Super Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TYPE__SUPER_TYPE = eINSTANCE.getNodeType_SuperType();

		/**
		 * The meta object literal for the '<em><b>Sub Type</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TYPE__SUB_TYPE = eINSTANCE.getNodeType_SubType();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl <em>Edge Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getEdgeType()
		 * @generated
		 */
		EClass EDGE_TYPE = eINSTANCE.getEdgeType();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_TYPE__SOURCE = eINSTANCE.getEdgeType_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_TYPE__TARGET = eINSTANCE.getEdgeType_Target();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDGE_TYPE__NAME = eINSTANCE.getEdgeType_Name();

		/**
		 * The meta object literal for the '<em><b>Type Graph</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_TYPE__TYPE_GRAPH = eINSTANCE.getEdgeType_TypeGraph();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDGE_TYPE__DIRECTION = eINSTANCE.getEdgeType_Direction();

		/**
		 * The meta object literal for the '<em><b>Source Cardinality</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_TYPE__SOURCE_CARDINALITY = eINSTANCE.getEdgeType_SourceCardinality();

		/**
		 * The meta object literal for the '<em><b>Target Cardinality</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_TYPE__TARGET_CARDINALITY = eINSTANCE.getEdgeType_TargetCardinality();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeImpl <em>Attribute Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getAttributeType()
		 * @generated
		 */
		EClass ATTRIBUTE_TYPE = eINSTANCE.getAttributeType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_TYPE__NAME = eINSTANCE.getAttributeType_Name();

		/**
		 * The meta object literal for the '<em><b>Sort</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_TYPE__SORT = eINSTANCE.getAttributeType_Sort();

		/**
		 * The meta object literal for the '<em><b>Attributed Element</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT = eINSTANCE.getAttributeType_AttributedElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.ContinuousAttrImpl <em>Continuous Attr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.ContinuousAttrImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getContinuousAttr()
		 * @generated
		 */
		EClass CONTINUOUS_ATTR = eINSTANCE.getContinuousAttr();

		/**
		 * The meta object literal for the '<em><b>Derivation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTINUOUS_ATTR__DERIVATION = eINSTANCE.getContinuousAttr_Derivation();

		/**
		 * The meta object literal for the '<em><b>Lower Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTINUOUS_ATTR__LOWER_BOUND = eINSTANCE.getContinuousAttr_LowerBound();

		/**
		 * The meta object literal for the '<em><b>Upper Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTINUOUS_ATTR__UPPER_BOUND = eINSTANCE.getContinuousAttr_UpperBound();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.InheritanceRelationImpl <em>Inheritance Relation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.InheritanceRelationImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getInheritanceRelation()
		 * @generated
		 */
		EClass INHERITANCE_RELATION = eINSTANCE.getInheritanceRelation();

		/**
		 * The meta object literal for the '<em><b>Sub Type</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INHERITANCE_RELATION__SUB_TYPE = eINSTANCE.getInheritanceRelation_SubType();

		/**
		 * The meta object literal for the '<em><b>Super Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INHERITANCE_RELATION__SUPER_TYPE = eINSTANCE.getInheritanceRelation_SuperType();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.TypeGraphImpl <em>Type Graph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.TypeGraphImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getTypeGraph()
		 * @generated
		 */
		EClass TYPE_GRAPH = eINSTANCE.getTypeGraph();

		/**
		 * The meta object literal for the '<em><b>Node Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_GRAPH__NODE_TYPES = eINSTANCE.getTypeGraph_NodeTypes();

		/**
		 * The meta object literal for the '<em><b>Edge Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_GRAPH__EDGE_TYPES = eINSTANCE.getTypeGraph_EdgeTypes();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_GRAPH__NAME = eINSTANCE.getTypeGraph_Name();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_GRAPH__CONDITIONS = eINSTANCE.getTypeGraph_Conditions();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition <em>Type Graph Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getTypeGraphCondition()
		 * @generated
		 */
		EClass TYPE_GRAPH_CONDITION = eINSTANCE.getTypeGraphCondition();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeConditionImpl <em>Attribute Type Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeConditionImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getAttributeTypeCondition()
		 * @generated
		 */
		EClass ATTRIBUTE_TYPE_CONDITION = eINSTANCE.getAttributeTypeCondition();

		/**
		 * The meta object literal for the '<em><b>Operation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_TYPE_CONDITION__OPERATION = eINSTANCE.getAttributeTypeCondition_Operation();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.CardinalityImpl <em>Cardinality</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.CardinalityImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getCardinality()
		 * @generated
		 */
		EClass CARDINALITY = eINSTANCE.getCardinality();

		/**
		 * The meta object literal for the '<em><b>Lower Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARDINALITY__LOWER_BOUND = eINSTANCE.getCardinality_LowerBound();

		/**
		 * The meta object literal for the '<em><b>Upper Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARDINALITY__UPPER_BOUND = eINSTANCE.getCardinality_UpperBound();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem <em>Attributed Elem</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getAttributedElem()
		 * @generated
		 */
		EClass ATTRIBUTED_ELEM = eINSTANCE.getAttributedElem();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTED_ELEM__ATTRIBUTES = eINSTANCE.getAttributedElem_Attributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.DataTypeImpl <em>Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.DataTypeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getDataType()
		 * @generated
		 */
		EClass DATA_TYPE = eINSTANCE.getDataType();

		/**
		 * The meta object literal for the '<em><b>Sort</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_TYPE__SORT = eINSTANCE.getDataType_Sort();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection <em>Edge Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection
		 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl#getEdgeDirection()
		 * @generated
		 */
		EEnum EDGE_DIRECTION = eINSTANCE.getEdgeDirection();

	}

} //SamtypegraphPackage
