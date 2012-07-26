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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
	String eNS_URI = "http://www.eclipse.org/emf/2011/Henshin";

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
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__DESCRIPTION = 1;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.TransformationSystemImpl <em>Transformation System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.TransformationSystemImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getTransformationSystem()
	 * @generated
	 */
	int TRANSFORMATION_SYSTEM = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__RULES = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__IMPORTS = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Transformation Units</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM__INSTANCES = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Transformation System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_SYSTEM_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.RuleImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getRule()
	 * @generated
	 */
	int RULE = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.AttributeConditionImpl <em>Attribute Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.AttributeConditionImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getAttributeCondition()
	 * @generated
	 */
	int ATTRIBUTE_CONDITION = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.GraphImpl <em>Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.GraphImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getGraph()
	 * @generated
	 */
	int GRAPH = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.GraphElement <em>Graph Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.GraphElement
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getGraphElement()
	 * @generated
	 */
	int GRAPH_ELEMENT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.MappingImpl <em>Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.MappingImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getMapping()
	 * @generated
	 */
	int MAPPING = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.NodeImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.AttributeImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.EdgeImpl <em>Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.EdgeImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getEdge()
	 * @generated
	 */
	int EDGE = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.TransformationUnitImpl <em>Transformation Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.TransformationUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getTransformationUnit()
	 * @generated
	 */
	int TRANSFORMATION_UNIT = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_UNIT__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_UNIT__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_UNIT__ACTIVATED = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_UNIT__PARAMETERS = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_UNIT__PARAMETER_MAPPINGS = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Transformation Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_UNIT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__NAME = TRANSFORMATION_UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__DESCRIPTION = TRANSFORMATION_UNIT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__PARAMETERS = TRANSFORMATION_UNIT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__PARAMETER_MAPPINGS = TRANSFORMATION_UNIT__PARAMETER_MAPPINGS;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__LHS = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__RHS = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attribute Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__ATTRIBUTE_CONDITIONS = TRANSFORMATION_UNIT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__MAPPINGS = TRANSFORMATION_UNIT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Check Dangling</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__CHECK_DANGLING = TRANSFORMATION_UNIT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Injective Matching</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__INJECTIVE_MATCHING = TRANSFORMATION_UNIT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Multi Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__MULTI_RULES = TRANSFORMATION_UNIT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Multi Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__MULTI_MAPPINGS = TRANSFORMATION_UNIT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONDITION__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONDITION__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONDITION__RULE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONDITION__CONDITION_TEXT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Attribute Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONDITION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.ParameterImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__UNIT = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__TYPE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

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
	 * The number of structural features of the '<em>Graph Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_ELEMENT_FEATURE_COUNT = 0;

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
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

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
	 * The feature id for the '<em><b>All Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__ALL_EDGES = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;

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
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__SOURCE = GRAPH_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__TARGET = GRAPH_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__TYPE = GRAPH_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__GRAPH = GRAPH_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_FEATURE_COUNT = GRAPH_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.IndependentUnitImpl <em>Independent Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.IndependentUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getIndependentUnit()
	 * @generated
	 */
	int INDEPENDENT_UNIT = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEPENDENT_UNIT__NAME = TRANSFORMATION_UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEPENDENT_UNIT__DESCRIPTION = TRANSFORMATION_UNIT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEPENDENT_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEPENDENT_UNIT__PARAMETERS = TRANSFORMATION_UNIT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEPENDENT_UNIT__PARAMETER_MAPPINGS = TRANSFORMATION_UNIT__PARAMETER_MAPPINGS;

	/**
	 * The feature id for the '<em><b>Sub Units</b></em>' reference list.
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
	int SEQUENTIAL_UNIT = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT__NAME = TRANSFORMATION_UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT__DESCRIPTION = TRANSFORMATION_UNIT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT__PARAMETERS = TRANSFORMATION_UNIT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT__PARAMETER_MAPPINGS = TRANSFORMATION_UNIT__PARAMETER_MAPPINGS;

	/**
	 * The feature id for the '<em><b>Sub Units</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT__SUB_UNITS = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Strict</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT__STRICT = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rollback</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT__ROLLBACK = TRANSFORMATION_UNIT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Sequential Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_UNIT_FEATURE_COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl <em>Conditional Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getConditionalUnit()
	 * @generated
	 */
	int CONDITIONAL_UNIT = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__NAME = TRANSFORMATION_UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__DESCRIPTION = TRANSFORMATION_UNIT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__PARAMETERS = TRANSFORMATION_UNIT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__PARAMETER_MAPPINGS = TRANSFORMATION_UNIT__PARAMETER_MAPPINGS;

	/**
	 * The feature id for the '<em><b>If</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__IF = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Then</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_UNIT__THEN = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else</b></em>' reference.
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
	int PRIORITY_UNIT = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_UNIT__NAME = TRANSFORMATION_UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_UNIT__DESCRIPTION = TRANSFORMATION_UNIT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_UNIT__PARAMETERS = TRANSFORMATION_UNIT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_UNIT__PARAMETER_MAPPINGS = TRANSFORMATION_UNIT__PARAMETER_MAPPINGS;

	/**
	 * The feature id for the '<em><b>Sub Units</b></em>' reference list.
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
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.IteratedUnitImpl <em>Iterated Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.IteratedUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getIteratedUnit()
	 * @generated
	 */
	int ITERATED_UNIT = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATED_UNIT__NAME = TRANSFORMATION_UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATED_UNIT__DESCRIPTION = TRANSFORMATION_UNIT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATED_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATED_UNIT__PARAMETERS = TRANSFORMATION_UNIT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATED_UNIT__PARAMETER_MAPPINGS = TRANSFORMATION_UNIT__PARAMETER_MAPPINGS;

	/**
	 * The feature id for the '<em><b>Sub Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATED_UNIT__SUB_UNIT = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Iterations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATED_UNIT__ITERATIONS = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Iterated Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATED_UNIT_FEATURE_COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.LoopUnitImpl <em>Loop Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.LoopUnitImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getLoopUnit()
	 * @generated
	 */
	int LOOP_UNIT = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_UNIT__NAME = TRANSFORMATION_UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_UNIT__DESCRIPTION = TRANSFORMATION_UNIT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_UNIT__ACTIVATED = TRANSFORMATION_UNIT__ACTIVATED;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_UNIT__PARAMETERS = TRANSFORMATION_UNIT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_UNIT__PARAMETER_MAPPINGS = TRANSFORMATION_UNIT__PARAMETER_MAPPINGS;

	/**
	 * The feature id for the '<em><b>Sub Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_UNIT__SUB_UNIT = TRANSFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Loop Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_UNIT_FEATURE_COUNT = TRANSFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.FormulaImpl <em>Formula</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.FormulaImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getFormula()
	 * @generated
	 */
	int FORMULA = 19;

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
	int NESTED_CONDITION = 18;

	/**
	 * The feature id for the '<em><b>Conclusion</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONDITION__CONCLUSION = FORMULA_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONDITION__MAPPINGS = FORMULA_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Nested Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONDITION_FEATURE_COUNT = FORMULA_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.UnaryFormulaImpl <em>Unary Formula</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.UnaryFormulaImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getUnaryFormula()
	 * @generated
	 */
	int UNARY_FORMULA = 20;

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
	int BINARY_FORMULA = 21;

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
	int AND = 22;

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
	int OR = 23;

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
	int NOT = 25;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.XorImpl <em>Xor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.XorImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getXor()
	 * @generated
	 */
	int XOR = 24;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XOR__LEFT = BINARY_FORMULA__LEFT;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XOR__RIGHT = BINARY_FORMULA__RIGHT;

	/**
	 * The number of structural features of the '<em>Xor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XOR_FEATURE_COUNT = BINARY_FORMULA_FEATURE_COUNT + 0;

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
	 * The meta object id for the '{@link org.eclipse.emf.henshin.model.impl.ParameterMappingImpl <em>Parameter Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.model.impl.ParameterMappingImpl
	 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getParameterMapping()
	 * @generated
	 */
	int PARAMETER_MAPPING = 26;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_MAPPING__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_MAPPING__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Parameter Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_MAPPING_FEATURE_COUNT = 2;

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
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.NamedElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.emf.henshin.model.NamedElement#getDescription()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Description();

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
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.TransformationSystem#getTransformationUnits <em>Transformation Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transformation Units</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationSystem#getTransformationUnits()
	 * @see #getTransformationSystem()
	 * @generated
	 */
	EReference getTransformationSystem_TransformationUnits();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.TransformationSystem#getInstances <em>Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Instances</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationSystem#getInstances()
	 * @see #getTransformationSystem()
	 * @generated
	 */
	@Deprecated
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
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.Rule#isCheckDangling <em>Check Dangling</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Check Dangling</em>'.
	 * @see org.eclipse.emf.henshin.model.Rule#isCheckDangling()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_CheckDangling();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.Rule#isInjectiveMatching <em>Injective Matching</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Injective Matching</em>'.
	 * @see org.eclipse.emf.henshin.model.Rule#isInjectiveMatching()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_InjectiveMatching();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.Rule#getMultiRules <em>Multi Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Multi Rules</em>'.
	 * @see org.eclipse.emf.henshin.model.Rule#getMultiRules()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_MultiRules();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.Rule#getMultiMappings <em>Multi Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Multi Mappings</em>'.
	 * @see org.eclipse.emf.henshin.model.Rule#getMultiMappings()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_MultiMappings();

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
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.eclipse.emf.henshin.model.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.model.Parameter#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.Parameter#getUnit()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_Unit();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.Parameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.emf.henshin.model.Parameter#getType()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_Type();

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
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.GraphElement <em>Graph Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graph Element</em>'.
	 * @see org.eclipse.emf.henshin.model.GraphElement
	 * @generated
	 */
	EClass getGraphElement();

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
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.model.Node#getAllEdges <em>All Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>All Edges</em>'.
	 * @see org.eclipse.emf.henshin.model.Node#getAllEdges()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_AllEdges();

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
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.TransformationUnit#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationUnit#getParameters()
	 * @see #getTransformationUnit()
	 * @generated
	 */
	EReference getTransformationUnit_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.model.TransformationUnit#getParameterMappings <em>Parameter Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Mappings</em>'.
	 * @see org.eclipse.emf.henshin.model.TransformationUnit#getParameterMappings()
	 * @see #getTransformationUnit()
	 * @generated
	 */
	EReference getTransformationUnit_ParameterMappings();

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
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.model.IndependentUnit#getSubUnits <em>Sub Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Units</em>'.
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
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.model.SequentialUnit#getSubUnits <em>Sub Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Units</em>'.
	 * @see org.eclipse.emf.henshin.model.SequentialUnit#getSubUnits()
	 * @see #getSequentialUnit()
	 * @generated
	 */
	EReference getSequentialUnit_SubUnits();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.SequentialUnit#isStrict <em>Strict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strict</em>'.
	 * @see org.eclipse.emf.henshin.model.SequentialUnit#isStrict()
	 * @see #getSequentialUnit()
	 * @generated
	 */
	EAttribute getSequentialUnit_Strict();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.SequentialUnit#isRollback <em>Rollback</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rollback</em>'.
	 * @see org.eclipse.emf.henshin.model.SequentialUnit#isRollback()
	 * @see #getSequentialUnit()
	 * @generated
	 */
	EAttribute getSequentialUnit_Rollback();

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
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getIf <em>If</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>If</em>'.
	 * @see org.eclipse.emf.henshin.model.ConditionalUnit#getIf()
	 * @see #getConditionalUnit()
	 * @generated
	 */
	EReference getConditionalUnit_If();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getThen <em>Then</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Then</em>'.
	 * @see org.eclipse.emf.henshin.model.ConditionalUnit#getThen()
	 * @see #getConditionalUnit()
	 * @generated
	 */
	EReference getConditionalUnit_Then();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getElse <em>Else</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Else</em>'.
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
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.model.PriorityUnit#getSubUnits <em>Sub Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Units</em>'.
	 * @see org.eclipse.emf.henshin.model.PriorityUnit#getSubUnits()
	 * @see #getPriorityUnit()
	 * @generated
	 */
	EReference getPriorityUnit_SubUnits();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.IteratedUnit <em>Iterated Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iterated Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.IteratedUnit
	 * @generated
	 */
	EClass getIteratedUnit();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.IteratedUnit#getSubUnit <em>Sub Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sub Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.IteratedUnit#getSubUnit()
	 * @see #getIteratedUnit()
	 * @generated
	 */
	EReference getIteratedUnit_SubUnit();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.model.IteratedUnit#getIterations <em>Iterations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Iterations</em>'.
	 * @see org.eclipse.emf.henshin.model.IteratedUnit#getIterations()
	 * @see #getIteratedUnit()
	 * @generated
	 */
	EAttribute getIteratedUnit_Iterations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.LoopUnit <em>Loop Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Loop Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.LoopUnit
	 * @generated
	 */
	EClass getLoopUnit();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.LoopUnit#getSubUnit <em>Sub Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sub Unit</em>'.
	 * @see org.eclipse.emf.henshin.model.LoopUnit#getSubUnit()
	 * @see #getLoopUnit()
	 * @generated
	 */
	EReference getLoopUnit_SubUnit();

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
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.Xor <em>Xor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xor</em>'.
	 * @see org.eclipse.emf.henshin.model.Xor
	 * @generated
	 */
	EClass getXor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.model.ParameterMapping <em>Parameter Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Mapping</em>'.
	 * @see org.eclipse.emf.henshin.model.ParameterMapping
	 * @generated
	 */
	EClass getParameterMapping();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.ParameterMapping#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.emf.henshin.model.ParameterMapping#getSource()
	 * @see #getParameterMapping()
	 * @generated
	 */
	EReference getParameterMapping_Source();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.model.ParameterMapping#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.emf.henshin.model.ParameterMapping#getTarget()
	 * @see #getParameterMapping()
	 * @generated
	 */
	EReference getParameterMapping_Target();

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
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__DESCRIPTION = eINSTANCE.getNamedElement_Description();

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
		 * The meta object literal for the '<em><b>Imports</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION_SYSTEM__IMPORTS = eINSTANCE.getTransformationSystem_Imports();

		/**
		 * The meta object literal for the '<em><b>Transformation Units</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS = eINSTANCE.getTransformationSystem_TransformationUnits();

		/**
		 * The meta object literal for the '<em><b>Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		@Deprecated
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
		 * The meta object literal for the '<em><b>Check Dangling</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__CHECK_DANGLING = eINSTANCE.getRule_CheckDangling();

		/**
		 * The meta object literal for the '<em><b>Injective Matching</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__INJECTIVE_MATCHING = eINSTANCE.getRule_InjectiveMatching();

		/**
		 * The meta object literal for the '<em><b>Multi Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__MULTI_RULES = eINSTANCE.getRule_MultiRules();

		/**
		 * The meta object literal for the '<em><b>Multi Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__MULTI_MAPPINGS = eINSTANCE.getRule_MultiMappings();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.ParameterImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__UNIT = eINSTANCE.getParameter_Unit();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__TYPE = eINSTANCE.getParameter_Type();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.GraphElement <em>Graph Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.GraphElement
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getGraphElement()
		 * @generated
		 */
		EClass GRAPH_ELEMENT = eINSTANCE.getGraphElement();

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
		 * The meta object literal for the '<em><b>All Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__ALL_EDGES = eINSTANCE.getNode_AllEdges();

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
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION_UNIT__PARAMETERS = eINSTANCE.getTransformationUnit_Parameters();

		/**
		 * The meta object literal for the '<em><b>Parameter Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION_UNIT__PARAMETER_MAPPINGS = eINSTANCE.getTransformationUnit_ParameterMappings();

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
		 * The meta object literal for the '<em><b>Sub Units</b></em>' reference list feature.
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
		 * The meta object literal for the '<em><b>Sub Units</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEQUENTIAL_UNIT__SUB_UNITS = eINSTANCE.getSequentialUnit_SubUnits();

		/**
		 * The meta object literal for the '<em><b>Strict</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEQUENTIAL_UNIT__STRICT = eINSTANCE.getSequentialUnit_Strict();

		/**
		 * The meta object literal for the '<em><b>Rollback</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEQUENTIAL_UNIT__ROLLBACK = eINSTANCE.getSequentialUnit_Rollback();

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
		 * The meta object literal for the '<em><b>If</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_UNIT__IF = eINSTANCE.getConditionalUnit_If();

		/**
		 * The meta object literal for the '<em><b>Then</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_UNIT__THEN = eINSTANCE.getConditionalUnit_Then();

		/**
		 * The meta object literal for the '<em><b>Else</b></em>' reference feature.
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
		 * The meta object literal for the '<em><b>Sub Units</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIORITY_UNIT__SUB_UNITS = eINSTANCE.getPriorityUnit_SubUnits();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.IteratedUnitImpl <em>Iterated Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.IteratedUnitImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getIteratedUnit()
		 * @generated
		 */
		EClass ITERATED_UNIT = eINSTANCE.getIteratedUnit();

		/**
		 * The meta object literal for the '<em><b>Sub Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATED_UNIT__SUB_UNIT = eINSTANCE.getIteratedUnit_SubUnit();

		/**
		 * The meta object literal for the '<em><b>Iterations</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITERATED_UNIT__ITERATIONS = eINSTANCE.getIteratedUnit_Iterations();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.LoopUnitImpl <em>Loop Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.LoopUnitImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getLoopUnit()
		 * @generated
		 */
		EClass LOOP_UNIT = eINSTANCE.getLoopUnit();

		/**
		 * The meta object literal for the '<em><b>Sub Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOP_UNIT__SUB_UNIT = eINSTANCE.getLoopUnit_SubUnit();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.XorImpl <em>Xor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.XorImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getXor()
		 * @generated
		 */
		EClass XOR = eINSTANCE.getXor();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.model.impl.ParameterMappingImpl <em>Parameter Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.model.impl.ParameterMappingImpl
		 * @see org.eclipse.emf.henshin.model.impl.HenshinPackageImpl#getParameterMapping()
		 * @generated
		 */
		EClass PARAMETER_MAPPING = eINSTANCE.getParameterMapping();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER_MAPPING__SOURCE = eINSTANCE.getParameterMapping_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER_MAPPING__TARGET = eINSTANCE.getParameterMapping_Target();

	}

} //HenshinPackage
