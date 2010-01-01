/**
 * <copyright>
 * </copyright>
 *
 * $Id: HenshinPackage.java,v 1.1 2009/10/28 10:38:05 enrico Exp $
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.emf.henshin.model.HenshinFactory
 * @model kind="package"
 * @generated
 */
public interface HenshinPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "henshin";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.eclipse.emf.henshin";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "henshin";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HenshinPackage eINSTANCE = org.eclipse.emf.henshin.model.impl.HenshinPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.NamedElementImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.DescribedElementImpl <em>Described Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.DescribedElementImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getDescribedElement()
	 * @generated
	 */
	int DESCRIBED_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIBED_ELEMENT__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Described Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIBED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.TransformationSystemImpl <em>Transformation System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.TransformationSystemImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getTransformationSystem()
	 * @generated
	 */
	int TRANSFORMATION_SYSTEM = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__DESCRIPTION = DESCRIBED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__NAME = DESCRIBED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__RULES = DESCRIBED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Transformations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__TRANSFORMATIONS = DESCRIBED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__IMPORTS = DESCRIBED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__INSTANCES = DESCRIBED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Transformation System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM_FEATURE_COUNT = DESCRIBED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.RuleImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getRule()
	 * @generated
	 */
	int RULE = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__DESCRIPTION = DESCRIBED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__NAME = DESCRIBED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__LHS = DESCRIBED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__RHS = DESCRIBED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attribute Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__ATTRIBUTE_CONDITIONS = DESCRIBED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__MAPPINGS = DESCRIBED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Transformation System</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__TRANSFORMATION_SYSTEM = DESCRIBED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__VARIABLES = DESCRIBED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = DESCRIBED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.AttributeConditionImpl <em>Attribute Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.AttributeConditionImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getAttributeCondition()
	 * @generated
	 */
	int ATTRIBUTE_CONDITION = 4;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONDITION__DESCRIPTION = DESCRIBED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONDITION__NAME = DESCRIBED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONDITION__RULE = DESCRIBED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Condition Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONDITION__CONDITION_TEXT = DESCRIBED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Attribute Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONDITION_FEATURE_COUNT = DESCRIBED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.VariableImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 5;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__DESCRIPTION = DESCRIBED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = DESCRIBED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__RULE = DESCRIBED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = DESCRIBED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.PortImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 6;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__DESCRIPTION = DESCRIBED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = DESCRIBED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__DIRECTION = DESCRIBED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__UNIT = DESCRIBED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = DESCRIBED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.PortObjectImpl <em>Port Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.PortObjectImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getPortObject()
	 * @generated
	 */
	int PORT_OBJECT = 7;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_OBJECT__DESCRIPTION = PORT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_OBJECT__NAME = PORT__NAME;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_OBJECT__DIRECTION = PORT__DIRECTION;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_OBJECT__UNIT = PORT__UNIT;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_OBJECT__NODE = PORT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Port Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_OBJECT_FEATURE_COUNT = PORT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.PortParameterImpl <em>Port Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.PortParameterImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getPortParameter()
	 * @generated
	 */
	int PORT_PARAMETER = 8;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PARAMETER__DESCRIPTION = PORT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PARAMETER__NAME = PORT__NAME;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PARAMETER__DIRECTION = PORT__DIRECTION;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PARAMETER__UNIT = PORT__UNIT;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PARAMETER__VARIABLE = PORT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Port Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PARAMETER_FEATURE_COUNT = PORT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.GraphImpl <em>Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.GraphImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getGraph()
	 * @generated
	 */
	int GRAPH = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH__NODES = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH__EDGES = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Formula</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH__FORMULA = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.MappingImpl <em>Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.MappingImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getMapping()
	 * @generated
	 */
	int MAPPING = 10;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__ORIGIN = 0;

	/**
	 * The feature id for the '<em><b>Image</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__IMAGE = 1;

	/**
	 * The number of structural features of the '<em>Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.NodeImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__TYPE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__ATTRIBUTES = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__GRAPH = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__INCOMING = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__OUTGOING = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.AttributeImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 12;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Node</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NODE = 2;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.EdgeImpl <em>Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.EdgeImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getEdge()
	 * @generated
	 */
	int EDGE = 13;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__TYPE = 2;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__GRAPH = 3;

	/**
	 * The number of structural features of the '<em>Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.TransformationImpl <em>Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.TransformationImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getTransformation()
	 * @generated
	 */
	int TRANSFORMATION = 14;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION__DESCRIPTION = DESCRIBED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION__NAME = DESCRIBED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Main Unit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION__MAIN_UNIT = DESCRIBED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Transformation System</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION__TRANSFORMATION_SYSTEM = DESCRIBED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_FEATURE_COUNT = DESCRIBED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.TransformationUnitImpl <em>Transformation Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.TransformationUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getTransformationUnit()
	 * @generated
	 */
	int TRANSFORMATION_UNIT = 15;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_UNIT__ACTIVATED = 0;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_UNIT__PORTS = 1;

	/**
	 * The number of structural features of the '<em>Transformation Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_UNIT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.IndependentUnitImpl <em>Independent Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.IndependentUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getIndependentUnit()
	 * @generated
	 */
	int INDEPENDENT_UNIT = 16;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEPENDENT_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEPENDENT_UNIT__PORTS = TRANSFORMATION_UNIT__PORTS;

	/**
	 * The feature id for the '<em><b>Sub Units</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEPENDENT_UNIT__SUB_UNITS = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Independent Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEPENDENT_UNIT_FEATURE_COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.SequentialUnitImpl <em>Sequential Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.SequentialUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getSequentialUnit()
	 * @generated
	 */
	int SEQUENTIAL_UNIT = 17;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT__PORTS = TRANSFORMATION_UNIT__PORTS;

	/**
	 * The feature id for the '<em><b>Sub Units</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT__SUB_UNITS = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sequential Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT_FEATURE_COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl <em>Conditional Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getConditionalUnit()
	 * @generated
	 */
	int CONDITIONAL_UNIT = 18;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__PORTS = TRANSFORMATION_UNIT__PORTS;

	/**
	 * The feature id for the '<em><b>If</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__IF = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Then</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__THEN = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__ELSE = TRANSFORMATION_UNIT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Conditional Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT_FEATURE_COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.PriorityUnitImpl <em>Priority Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.PriorityUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getPriorityUnit()
	 * @generated
	 */
	int PRIORITY_UNIT = 19;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_UNIT__PORTS = TRANSFORMATION_UNIT__PORTS;

	/**
	 * The feature id for the '<em><b>Sub Units</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_UNIT__SUB_UNITS = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Priority Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_UNIT_FEATURE_COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.SingleUnitImpl <em>Single Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.SingleUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getSingleUnit()
	 * @generated
	 */
	int SINGLE_UNIT = 20;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_UNIT__PORTS = TRANSFORMATION_UNIT__PORTS;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_UNIT__RULE = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Single Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_UNIT_FEATURE_COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.AmalgamatedUnitImpl <em>Amalgamated Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.AmalgamatedUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getAmalgamatedUnit()
	 * @generated
	 */
	int AMALGAMATED_UNIT = 21;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AMALGAMATED_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AMALGAMATED_UNIT__PORTS = TRANSFORMATION_UNIT__PORTS;

	/**
	 * The feature id for the '<em><b>Kernel Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AMALGAMATED_UNIT__KERNEL_RULE = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Multi Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AMALGAMATED_UNIT__MULTI_RULES = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lhs Mappings</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AMALGAMATED_UNIT__LHS_MAPPINGS = TRANSFORMATION_UNIT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Rhs Mappings</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AMALGAMATED_UNIT__RHS_MAPPINGS = TRANSFORMATION_UNIT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Amalgamated Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AMALGAMATED_UNIT_FEATURE_COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.CountedUnitImpl <em>Counted Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.CountedUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getCountedUnit()
	 * @generated
	 */
	int COUNTED_UNIT = 22;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTED_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTED_UNIT__PORTS = TRANSFORMATION_UNIT__PORTS;

	/**
	 * The feature id for the '<em><b>Sub Unit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTED_UNIT__SUB_UNIT = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTED_UNIT__COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Counted Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTED_UNIT_FEATURE_COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.FormulaImpl <em>Formula</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.FormulaImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getFormula()
	 * @generated
	 */
	int FORMULA = 24;

	/**
	 * The number of structural features of the '<em>Formula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.NestedConditionImpl <em>Nested Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.NestedConditionImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getNestedCondition()
	 * @generated
	 */
	int NESTED_CONDITION = 23;

	/**
	 * The feature id for the '<em><b>Negated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONDITION__NEGATED = FORMULA_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Conclusion</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONDITION__CONCLUSION = FORMULA_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONDITION__MAPPINGS = FORMULA_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Nested Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONDITION_FEATURE_COUNT = FORMULA_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.UnaryFormulaImpl <em>Unary Formula</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.UnaryFormulaImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getUnaryFormula()
	 * @generated
	 */
	int UNARY_FORMULA = 25;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_FORMULA__CHILD = FORMULA_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Unary Formula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_FORMULA_FEATURE_COUNT = FORMULA_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.BinaryFormulaImpl <em>Binary Formula</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.BinaryFormulaImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getBinaryFormula()
	 * @generated
	 */
	int BINARY_FORMULA = 26;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FORMULA__LEFT = FORMULA_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FORMULA__RIGHT = FORMULA_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Binary Formula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FORMULA_FEATURE_COUNT = FORMULA_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.AndImpl <em>And</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.AndImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getAnd()
	 * @generated
	 */
	int AND = 27;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND__LEFT = BINARY_FORMULA__LEFT;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND__RIGHT = BINARY_FORMULA__RIGHT;

	/**
	 * The number of structural features of the '<em>And</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_FEATURE_COUNT = BINARY_FORMULA_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.OrImpl <em>Or</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.OrImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getOr()
	 * @generated
	 */
	int OR = 28;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR__LEFT = BINARY_FORMULA__LEFT;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR__RIGHT = BINARY_FORMULA__RIGHT;

	/**
	 * The number of structural features of the '<em>Or</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_FEATURE_COUNT = BINARY_FORMULA_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.NotImpl <em>Not</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.NotImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getNot()
	 * @generated
	 */
	int NOT = 29;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT__CHILD = UNARY_FORMULA__CHILD;

	/**
	 * The number of structural features of the '<em>Not</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_FEATURE_COUNT = UNARY_FORMULA_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.PortKind <em>Port Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.PortKind
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getPortKind()
	 * @generated
	 */
	int PORT_KIND = 30;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see org.eclipse.emf.henshin.model.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.model.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.DescribedElement <em>Described Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Described Element</em>'.
	 * @see org.eclipse.emf.henshin.model.DescribedElement
	 * @generated
	 */
	EClass getDescribedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.DescribedElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.emf.henshin.model.DescribedElement#getDescription()
	 * @see #getDescribedElement()
	 * @generated
	 */
	EAttribute getDescribedElement_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.TransformationSystem <em>Transformation System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transformation System</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationSystem
	 * @generated
	 */
	EClass getTransformationSystem();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.TransformationSystem#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationSystem#getRules()
	 * @see #getTransformationSystem()
	 * @generated
	 */
	EReference getTransformationSystem_Rules();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.TransformationSystem#getTransformations <em>Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transformations</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationSystem#getTransformations()
	 * @see #getTransformationSystem()
	 * @generated
	 */
	EReference getTransformationSystem_Transformations();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.model.TransformationSystem#getImports <em>Imports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Imports</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationSystem#getImports()
	 * @see #getTransformationSystem()
	 * @generated
	 */
	EReference getTransformationSystem_Imports();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.TransformationSystem#getInstances <em>Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Instances</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationSystem#getInstances()
	 * @see #getTransformationSystem()
	 * @generated
	 */
	EReference getTransformationSystem_Instances();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see org.eclipse.emf.henshin.model.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.Rule#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.eclipse.emf.henshin.model.Rule#getLhs()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Lhs();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.Rule#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.eclipse.emf.henshin.model.Rule#getRhs()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Rhs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.Rule#getAttributeConditions <em>Attribute Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Conditions</em>'.
	 * @see org.eclipse.emf.henshin.model.Rule#getAttributeConditions()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_AttributeConditions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.Rule#getMappings <em>Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see org.eclipse.emf.henshin.model.Rule#getMappings()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Mappings();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.model.Rule#getTransformationSystem <em>Transformation System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Transformation System</em>'.
	 * @see org.eclipse.emf.henshin.model.Rule#getTransformationSystem()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_TransformationSystem();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.Rule#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see org.eclipse.emf.henshin.model.Rule#getVariables()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Variables();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.AttributeCondition <em>Attribute Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Condition</em>'.
	 * @see org.eclipse.emf.henshin.model.AttributeCondition
	 * @generated
	 */
	EClass getAttributeCondition();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.model.AttributeCondition#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule</em>'.
	 * @see org.eclipse.emf.henshin.model.AttributeCondition#getRule()
	 * @see #getAttributeCondition()
	 * @generated
	 */
	EReference getAttributeCondition_Rule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.AttributeCondition#getConditionText <em>Condition Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condition Text</em>'.
	 * @see org.eclipse.emf.henshin.model.AttributeCondition#getConditionText()
	 * @see #getAttributeCondition()
	 * @generated
	 */
	EAttribute getAttributeCondition_ConditionText();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see org.eclipse.emf.henshin.model.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.model.Variable#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule</em>'.
	 * @see org.eclipse.emf.henshin.model.Variable#getRule()
	 * @see #getVariable()
	 * @generated
	 */
	EReference getVariable_Rule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see org.eclipse.emf.henshin.model.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.Port#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see org.eclipse.emf.henshin.model.Port#getDirection()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Direction();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.model.Port#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.Port#getUnit()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Unit();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.PortObject <em>Port Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port Object</em>'.
	 * @see org.eclipse.emf.henshin.model.PortObject
	 * @generated
	 */
	EClass getPortObject();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.PortObject#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.eclipse.emf.henshin.model.PortObject#getNode()
	 * @see #getPortObject()
	 * @generated
	 */
	EReference getPortObject_Node();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.PortParameter <em>Port Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port Parameter</em>'.
	 * @see org.eclipse.emf.henshin.model.PortParameter
	 * @generated
	 */
	EClass getPortParameter();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.PortParameter#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see org.eclipse.emf.henshin.model.PortParameter#getVariable()
	 * @see #getPortParameter()
	 * @generated
	 */
	EReference getPortParameter_Variable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Graph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graph</em>'.
	 * @see org.eclipse.emf.henshin.model.Graph
	 * @generated
	 */
	EClass getGraph();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.Graph#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see org.eclipse.emf.henshin.model.Graph#getNodes()
	 * @see #getGraph()
	 * @generated
	 */
	EReference getGraph_Nodes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.Graph#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edges</em>'.
	 * @see org.eclipse.emf.henshin.model.Graph#getEdges()
	 * @see #getGraph()
	 * @generated
	 */
	EReference getGraph_Edges();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.Graph#getFormula <em>Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Formula</em>'.
	 * @see org.eclipse.emf.henshin.model.Graph#getFormula()
	 * @see #getGraph()
	 * @generated
	 */
	EReference getGraph_Formula();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Mapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping</em>'.
	 * @see org.eclipse.emf.henshin.model.Mapping
	 * @generated
	 */
	EClass getMapping();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.Mapping#getOrigin <em>Origin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Origin</em>'.
	 * @see org.eclipse.emf.henshin.model.Mapping#getOrigin()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_Origin();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.Mapping#getImage <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Image</em>'.
	 * @see org.eclipse.emf.henshin.model.Mapping#getImage()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_Image();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see org.eclipse.emf.henshin.model.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.Node#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.emf.henshin.model.Node#getType()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.Node#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.eclipse.emf.henshin.model.Node#getAttributes()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_Attributes();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.model.Node#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Graph</em>'.
	 * @see org.eclipse.emf.henshin.model.Node#getGraph()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_Graph();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.model.Node#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see org.eclipse.emf.henshin.model.Node#getIncoming()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_Incoming();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.model.Node#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see org.eclipse.emf.henshin.model.Node#getOutgoing()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_Outgoing();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see org.eclipse.emf.henshin.model.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.Attribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.emf.henshin.model.Attribute#getType()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.Attribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emf.henshin.model.Attribute#getValue()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Value();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.model.Attribute#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Node</em>'.
	 * @see org.eclipse.emf.henshin.model.Attribute#getNode()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_Node();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Edge <em>Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edge</em>'.
	 * @see org.eclipse.emf.henshin.model.Edge
	 * @generated
	 */
	EClass getEdge();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.Edge#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.emf.henshin.model.Edge#getSource()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Source();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.Edge#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.emf.henshin.model.Edge#getTarget()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Target();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.Edge#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.emf.henshin.model.Edge#getType()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Type();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.model.Edge#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Graph</em>'.
	 * @see org.eclipse.emf.henshin.model.Edge#getGraph()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Graph();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Transformation <em>Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transformation</em>'.
	 * @see org.eclipse.emf.henshin.model.Transformation
	 * @generated
	 */
	EClass getTransformation();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.Transformation#getMainUnit <em>Main Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Main Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.Transformation#getMainUnit()
	 * @see #getTransformation()
	 * @generated
	 */
	EReference getTransformation_MainUnit();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.model.Transformation#getTransformationSystem <em>Transformation System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Transformation System</em>'.
	 * @see org.eclipse.emf.henshin.model.Transformation#getTransformationSystem()
	 * @see #getTransformation()
	 * @generated
	 */
	EReference getTransformation_TransformationSystem();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.TransformationUnit <em>Transformation Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transformation Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationUnit
	 * @generated
	 */
	EClass getTransformationUnit();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.TransformationUnit#isActivated <em>Activated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Activated</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationUnit#isActivated()
	 * @see #getTransformationUnit()
	 * @generated
	 */
	EAttribute getTransformationUnit_Activated();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.TransformationUnit#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationUnit#getPorts()
	 * @see #getTransformationUnit()
	 * @generated
	 */
	EReference getTransformationUnit_Ports();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.IndependentUnit <em>Independent Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Independent Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.IndependentUnit
	 * @generated
	 */
	EClass getIndependentUnit();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.IndependentUnit#getSubUnits <em>Sub Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Units</em>'.
	 * @see org.eclipse.emf.henshin.model.IndependentUnit#getSubUnits()
	 * @see #getIndependentUnit()
	 * @generated
	 */
	EReference getIndependentUnit_SubUnits();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.SequentialUnit <em>Sequential Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sequential Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.SequentialUnit
	 * @generated
	 */
	EClass getSequentialUnit();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.SequentialUnit#getSubUnits <em>Sub Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Units</em>'.
	 * @see org.eclipse.emf.henshin.model.SequentialUnit#getSubUnits()
	 * @see #getSequentialUnit()
	 * @generated
	 */
	EReference getSequentialUnit_SubUnits();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.ConditionalUnit <em>Conditional Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.ConditionalUnit
	 * @generated
	 */
	EClass getConditionalUnit();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getIf <em>If</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>If</em>'.
	 * @see org.eclipse.emf.henshin.model.ConditionalUnit#getIf()
	 * @see #getConditionalUnit()
	 * @generated
	 */
	EReference getConditionalUnit_If();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getThen <em>Then</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Then</em>'.
	 * @see org.eclipse.emf.henshin.model.ConditionalUnit#getThen()
	 * @see #getConditionalUnit()
	 * @generated
	 */
	EReference getConditionalUnit_Then();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getElse <em>Else</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Else</em>'.
	 * @see org.eclipse.emf.henshin.model.ConditionalUnit#getElse()
	 * @see #getConditionalUnit()
	 * @generated
	 */
	EReference getConditionalUnit_Else();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.PriorityUnit <em>Priority Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Priority Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.PriorityUnit
	 * @generated
	 */
	EClass getPriorityUnit();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.PriorityUnit#getSubUnits <em>Sub Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Units</em>'.
	 * @see org.eclipse.emf.henshin.model.PriorityUnit#getSubUnits()
	 * @see #getPriorityUnit()
	 * @generated
	 */
	EReference getPriorityUnit_SubUnits();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.SingleUnit <em>Single Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Single Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.SingleUnit
	 * @generated
	 */
	EClass getSingleUnit();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.SingleUnit#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule</em>'.
	 * @see org.eclipse.emf.henshin.model.SingleUnit#getRule()
	 * @see #getSingleUnit()
	 * @generated
	 */
	EReference getSingleUnit_Rule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.AmalgamatedUnit <em>Amalgamated Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Amalgamated Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.AmalgamatedUnit
	 * @generated
	 */
	EClass getAmalgamatedUnit();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.AmalgamatedUnit#getKernelRule <em>Kernel Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Kernel Rule</em>'.
	 * @see org.eclipse.emf.henshin.model.AmalgamatedUnit#getKernelRule()
	 * @see #getAmalgamatedUnit()
	 * @generated
	 */
	EReference getAmalgamatedUnit_KernelRule();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.model.AmalgamatedUnit#getMultiRules <em>Multi Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Multi Rules</em>'.
	 * @see org.eclipse.emf.henshin.model.AmalgamatedUnit#getMultiRules()
	 * @see #getAmalgamatedUnit()
	 * @generated
	 */
	EReference getAmalgamatedUnit_MultiRules();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.model.AmalgamatedUnit#getLhsMappings <em>Lhs Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Lhs Mappings</em>'.
	 * @see org.eclipse.emf.henshin.model.AmalgamatedUnit#getLhsMappings()
	 * @see #getAmalgamatedUnit()
	 * @generated
	 */
	EReference getAmalgamatedUnit_LhsMappings();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.model.AmalgamatedUnit#getRhsMappings <em>Rhs Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rhs Mappings</em>'.
	 * @see org.eclipse.emf.henshin.model.AmalgamatedUnit#getRhsMappings()
	 * @see #getAmalgamatedUnit()
	 * @generated
	 */
	EReference getAmalgamatedUnit_RhsMappings();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.CountedUnit <em>Counted Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Counted Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.CountedUnit
	 * @generated
	 */
	EClass getCountedUnit();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.CountedUnit#getSubUnit <em>Sub Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sub Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.CountedUnit#getSubUnit()
	 * @see #getCountedUnit()
	 * @generated
	 */
	EReference getCountedUnit_SubUnit();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.CountedUnit#getCount <em>Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Count</em>'.
	 * @see org.eclipse.emf.henshin.model.CountedUnit#getCount()
	 * @see #getCountedUnit()
	 * @generated
	 */
	EAttribute getCountedUnit_Count();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.NestedCondition <em>Nested Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Nested Condition</em>'.
	 * @see org.eclipse.emf.henshin.model.NestedCondition
	 * @generated
	 */
	EClass getNestedCondition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.NestedCondition#isNegated <em>Negated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Negated</em>'.
	 * @see org.eclipse.emf.henshin.model.NestedCondition#isNegated()
	 * @see #getNestedCondition()
	 * @generated
	 */
	EAttribute getNestedCondition_Negated();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.NestedCondition#getConclusion <em>Conclusion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Conclusion</em>'.
	 * @see org.eclipse.emf.henshin.model.NestedCondition#getConclusion()
	 * @see #getNestedCondition()
	 * @generated
	 */
	EReference getNestedCondition_Conclusion();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.NestedCondition#getMappings <em>Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see org.eclipse.emf.henshin.model.NestedCondition#getMappings()
	 * @see #getNestedCondition()
	 * @generated
	 */
	EReference getNestedCondition_Mappings();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Formula <em>Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Formula</em>'.
	 * @see org.eclipse.emf.henshin.model.Formula
	 * @generated
	 */
	EClass getFormula();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.UnaryFormula <em>Unary Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Formula</em>'.
	 * @see org.eclipse.emf.henshin.model.UnaryFormula
	 * @generated
	 */
	EClass getUnaryFormula();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.UnaryFormula#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see org.eclipse.emf.henshin.model.UnaryFormula#getChild()
	 * @see #getUnaryFormula()
	 * @generated
	 */
	EReference getUnaryFormula_Child();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.BinaryFormula <em>Binary Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Formula</em>'.
	 * @see org.eclipse.emf.henshin.model.BinaryFormula
	 * @generated
	 */
	EClass getBinaryFormula();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.BinaryFormula#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see org.eclipse.emf.henshin.model.BinaryFormula#getLeft()
	 * @see #getBinaryFormula()
	 * @generated
	 */
	EReference getBinaryFormula_Left();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.model.BinaryFormula#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see org.eclipse.emf.henshin.model.BinaryFormula#getRight()
	 * @see #getBinaryFormula()
	 * @generated
	 */
	EReference getBinaryFormula_Right();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.And <em>And</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>And</em>'.
	 * @see org.eclipse.emf.henshin.model.And
	 * @generated
	 */
	EClass getAnd();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Or <em>Or</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Or</em>'.
	 * @see org.eclipse.emf.henshin.model.Or
	 * @generated
	 */
	EClass getOr();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Not <em>Not</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Not</em>'.
	 * @see org.eclipse.emf.henshin.model.Not
	 * @generated
	 */
	EClass getNot();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.henshin.model.PortKind <em>Port Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Port Kind</em>'.
	 * @see org.eclipse.emf.henshin.model.PortKind
	 * @generated
	 */
	EEnum getPortKind();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	HenshinFactory getHenshinFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.NamedElementImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.DescribedElementImpl <em>Described Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.DescribedElementImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getDescribedElement()
		 * @generated
		 */
		EClass DESCRIBED_ELEMENT = eINSTANCE.getDescribedElement();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESCRIBED_ELEMENT__DESCRIPTION = eINSTANCE.getDescribedElement_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.TransformationSystemImpl <em>Transformation System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.TransformationSystemImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getTransformationSystem()
		 * @generated
		 */
		EClass TRANSFORMATION_SYSTEM = eINSTANCE.getTransformationSystem();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION_SYSTEM__RULES = eINSTANCE.getTransformationSystem_Rules();

		/**
		 * The meta object literal for the '<em><b>Transformations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION_SYSTEM__TRANSFORMATIONS = eINSTANCE.getTransformationSystem_Transformations();

		/**
		 * The meta object literal for the '<em><b>Imports</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION_SYSTEM__IMPORTS = eINSTANCE.getTransformationSystem_Imports();

		/**
		 * The meta object literal for the '<em><b>Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION_SYSTEM__INSTANCES = eINSTANCE.getTransformationSystem_Instances();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.RuleImpl <em>Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.RuleImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__LHS = eINSTANCE.getRule_Lhs();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__RHS = eINSTANCE.getRule_Rhs();

		/**
		 * The meta object literal for the '<em><b>Attribute Conditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__ATTRIBUTE_CONDITIONS = eINSTANCE.getRule_AttributeConditions();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__MAPPINGS = eINSTANCE.getRule_Mappings();

		/**
		 * The meta object literal for the '<em><b>Transformation System</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__TRANSFORMATION_SYSTEM = eINSTANCE.getRule_TransformationSystem();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__VARIABLES = eINSTANCE.getRule_Variables();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.AttributeConditionImpl <em>Attribute Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.AttributeConditionImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getAttributeCondition()
		 * @generated
		 */
		EClass ATTRIBUTE_CONDITION = eINSTANCE.getAttributeCondition();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_CONDITION__RULE = eINSTANCE.getAttributeCondition_Rule();

		/**
		 * The meta object literal for the '<em><b>Condition Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_CONDITION__CONDITION_TEXT = eINSTANCE.getAttributeCondition_ConditionText();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.VariableImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE__RULE = eINSTANCE.getVariable_Rule();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.PortImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__DIRECTION = eINSTANCE.getPort_Direction();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__UNIT = eINSTANCE.getPort_Unit();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.PortObjectImpl <em>Port Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.PortObjectImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getPortObject()
		 * @generated
		 */
		EClass PORT_OBJECT = eINSTANCE.getPortObject();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_OBJECT__NODE = eINSTANCE.getPortObject_Node();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.PortParameterImpl <em>Port Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.PortParameterImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getPortParameter()
		 * @generated
		 */
		EClass PORT_PARAMETER = eINSTANCE.getPortParameter();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_PARAMETER__VARIABLE = eINSTANCE.getPortParameter_Variable();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.GraphImpl <em>Graph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.GraphImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getGraph()
		 * @generated
		 */
		EClass GRAPH = eINSTANCE.getGraph();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH__NODES = eINSTANCE.getGraph_Nodes();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH__EDGES = eINSTANCE.getGraph_Edges();

		/**
		 * The meta object literal for the '<em><b>Formula</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH__FORMULA = eINSTANCE.getGraph_Formula();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.MappingImpl <em>Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.MappingImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getMapping()
		 * @generated
		 */
		EClass MAPPING = eINSTANCE.getMapping();

		/**
		 * The meta object literal for the '<em><b>Origin</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING__ORIGIN = eINSTANCE.getMapping_Origin();

		/**
		 * The meta object literal for the '<em><b>Image</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING__IMAGE = eINSTANCE.getMapping_Image();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.NodeImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__TYPE = eINSTANCE.getNode_Type();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__ATTRIBUTES = eINSTANCE.getNode_Attributes();

		/**
		 * The meta object literal for the '<em><b>Graph</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__GRAPH = eINSTANCE.getNode_Graph();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__INCOMING = eINSTANCE.getNode_Incoming();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__OUTGOING = eINSTANCE.getNode_Outgoing();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.AttributeImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__TYPE = eINSTANCE.getAttribute_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__VALUE = eINSTANCE.getAttribute_Value();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__NODE = eINSTANCE.getAttribute_Node();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.EdgeImpl <em>Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.EdgeImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getEdge()
		 * @generated
		 */
		EClass EDGE = eINSTANCE.getEdge();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__SOURCE = eINSTANCE.getEdge_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__TARGET = eINSTANCE.getEdge_Target();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__TYPE = eINSTANCE.getEdge_Type();

		/**
		 * The meta object literal for the '<em><b>Graph</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__GRAPH = eINSTANCE.getEdge_Graph();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.TransformationImpl <em>Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.TransformationImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getTransformation()
		 * @generated
		 */
		EClass TRANSFORMATION = eINSTANCE.getTransformation();

		/**
		 * The meta object literal for the '<em><b>Main Unit</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION__MAIN_UNIT = eINSTANCE.getTransformation_MainUnit();

		/**
		 * The meta object literal for the '<em><b>Transformation System</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION__TRANSFORMATION_SYSTEM = eINSTANCE.getTransformation_TransformationSystem();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.TransformationUnitImpl <em>Transformation Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.TransformationUnitImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getTransformationUnit()
		 * @generated
		 */
		EClass TRANSFORMATION_UNIT = eINSTANCE.getTransformationUnit();

		/**
		 * The meta object literal for the '<em><b>Activated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSFORMATION_UNIT__ACTIVATED = eINSTANCE.getTransformationUnit_Activated();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION_UNIT__PORTS = eINSTANCE.getTransformationUnit_Ports();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.IndependentUnitImpl <em>Independent Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.IndependentUnitImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getIndependentUnit()
		 * @generated
		 */
		EClass INDEPENDENT_UNIT = eINSTANCE.getIndependentUnit();

		/**
		 * The meta object literal for the '<em><b>Sub Units</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INDEPENDENT_UNIT__SUB_UNITS = eINSTANCE.getIndependentUnit_SubUnits();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.SequentialUnitImpl <em>Sequential Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.SequentialUnitImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getSequentialUnit()
		 * @generated
		 */
		EClass SEQUENTIAL_UNIT = eINSTANCE.getSequentialUnit();

		/**
		 * The meta object literal for the '<em><b>Sub Units</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEQUENTIAL_UNIT__SUB_UNITS = eINSTANCE.getSequentialUnit_SubUnits();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl <em>Conditional Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getConditionalUnit()
		 * @generated
		 */
		EClass CONDITIONAL_UNIT = eINSTANCE.getConditionalUnit();

		/**
		 * The meta object literal for the '<em><b>If</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_UNIT__IF = eINSTANCE.getConditionalUnit_If();

		/**
		 * The meta object literal for the '<em><b>Then</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_UNIT__THEN = eINSTANCE.getConditionalUnit_Then();

		/**
		 * The meta object literal for the '<em><b>Else</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_UNIT__ELSE = eINSTANCE.getConditionalUnit_Else();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.PriorityUnitImpl <em>Priority Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.PriorityUnitImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getPriorityUnit()
		 * @generated
		 */
		EClass PRIORITY_UNIT = eINSTANCE.getPriorityUnit();

		/**
		 * The meta object literal for the '<em><b>Sub Units</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIORITY_UNIT__SUB_UNITS = eINSTANCE.getPriorityUnit_SubUnits();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.SingleUnitImpl <em>Single Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.SingleUnitImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getSingleUnit()
		 * @generated
		 */
		EClass SINGLE_UNIT = eINSTANCE.getSingleUnit();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SINGLE_UNIT__RULE = eINSTANCE.getSingleUnit_Rule();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.AmalgamatedUnitImpl <em>Amalgamated Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.AmalgamatedUnitImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getAmalgamatedUnit()
		 * @generated
		 */
		EClass AMALGAMATED_UNIT = eINSTANCE.getAmalgamatedUnit();

		/**
		 * The meta object literal for the '<em><b>Kernel Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AMALGAMATED_UNIT__KERNEL_RULE = eINSTANCE.getAmalgamatedUnit_KernelRule();

		/**
		 * The meta object literal for the '<em><b>Multi Rules</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AMALGAMATED_UNIT__MULTI_RULES = eINSTANCE.getAmalgamatedUnit_MultiRules();

		/**
		 * The meta object literal for the '<em><b>Lhs Mappings</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AMALGAMATED_UNIT__LHS_MAPPINGS = eINSTANCE.getAmalgamatedUnit_LhsMappings();

		/**
		 * The meta object literal for the '<em><b>Rhs Mappings</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AMALGAMATED_UNIT__RHS_MAPPINGS = eINSTANCE.getAmalgamatedUnit_RhsMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.CountedUnitImpl <em>Counted Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.CountedUnitImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getCountedUnit()
		 * @generated
		 */
		EClass COUNTED_UNIT = eINSTANCE.getCountedUnit();

		/**
		 * The meta object literal for the '<em><b>Sub Unit</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUNTED_UNIT__SUB_UNIT = eINSTANCE.getCountedUnit_SubUnit();

		/**
		 * The meta object literal for the '<em><b>Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COUNTED_UNIT__COUNT = eINSTANCE.getCountedUnit_Count();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.NestedConditionImpl <em>Nested Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.NestedConditionImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getNestedCondition()
		 * @generated
		 */
		EClass NESTED_CONDITION = eINSTANCE.getNestedCondition();

		/**
		 * The meta object literal for the '<em><b>Negated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NESTED_CONDITION__NEGATED = eINSTANCE.getNestedCondition_Negated();

		/**
		 * The meta object literal for the '<em><b>Conclusion</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NESTED_CONDITION__CONCLUSION = eINSTANCE.getNestedCondition_Conclusion();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NESTED_CONDITION__MAPPINGS = eINSTANCE.getNestedCondition_Mappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.FormulaImpl <em>Formula</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.FormulaImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getFormula()
		 * @generated
		 */
		EClass FORMULA = eINSTANCE.getFormula();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.UnaryFormulaImpl <em>Unary Formula</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.UnaryFormulaImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getUnaryFormula()
		 * @generated
		 */
		EClass UNARY_FORMULA = eINSTANCE.getUnaryFormula();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNARY_FORMULA__CHILD = eINSTANCE.getUnaryFormula_Child();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.BinaryFormulaImpl <em>Binary Formula</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.BinaryFormulaImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getBinaryFormula()
		 * @generated
		 */
		EClass BINARY_FORMULA = eINSTANCE.getBinaryFormula();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_FORMULA__LEFT = eINSTANCE.getBinaryFormula_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_FORMULA__RIGHT = eINSTANCE.getBinaryFormula_Right();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.AndImpl <em>And</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.AndImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getAnd()
		 * @generated
		 */
		EClass AND = eINSTANCE.getAnd();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.OrImpl <em>Or</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.OrImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getOr()
		 * @generated
		 */
		EClass OR = eINSTANCE.getOr();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.NotImpl <em>Not</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.NotImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getNot()
		 * @generated
		 */
		EClass NOT = eINSTANCE.getNot();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.PortKind <em>Port Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.PortKind
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getPortKind()
		 * @generated
		 */
		EEnum PORT_KIND = eINSTANCE.getPortKind();

	}

} //HenshinPackage
