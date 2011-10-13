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
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.henshin.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HenshinFactoryImpl extends EFactoryImpl implements HenshinFactory {
	
	/**
	 * The default name of LHS graphs.
	 */
	public static final String DEFAULT_RULE_LHS_NAME = "LHS";

	/**
	 * The default name of RHS graphs.
	 */
	public static final String DEFAULT_RULE_RHS_NAME = "RHS";
	
	
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HenshinFactory init() {
		try {
			HenshinFactory theHenshinFactory = (HenshinFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/emf/2010/Henshin"); 
			if (theHenshinFactory != null) {
				return theHenshinFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new HenshinFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HenshinFactoryImpl() {
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
			case HenshinPackage.TRANSFORMATION_SYSTEM: return createTransformationSystem();
			case HenshinPackage.RULE: return createRule();
			case HenshinPackage.ATTRIBUTE_CONDITION: return createAttributeCondition();
			case HenshinPackage.PARAMETER: return createParameter();
			case HenshinPackage.GRAPH: return createGraph();
			case HenshinPackage.MAPPING: return createMapping();
			case HenshinPackage.NODE: return createNode();
			case HenshinPackage.ATTRIBUTE: return createAttribute();
			case HenshinPackage.EDGE: return createEdge();
			case HenshinPackage.INDEPENDENT_UNIT: return createIndependentUnit();
			case HenshinPackage.SEQUENTIAL_UNIT: return createSequentialUnit();
			case HenshinPackage.CONDITIONAL_UNIT: return createConditionalUnit();
			case HenshinPackage.PRIORITY_UNIT: return createPriorityUnit();
			case HenshinPackage.AMALGAMATION_UNIT: return createAmalgamationUnit();
			case HenshinPackage.COUNTED_UNIT: return createCountedUnit();
			case HenshinPackage.NESTED_CONDITION: return createNestedCondition();
			case HenshinPackage.AND: return createAnd();
			case HenshinPackage.OR: return createOr();
			case HenshinPackage.XOR: return createXor();
			case HenshinPackage.NOT: return createNot();
			case HenshinPackage.PARAMETER_MAPPING: return createParameterMapping();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationSystem createTransformationSystem() {
		TransformationSystemImpl transformationSystem = new TransformationSystemImpl();
		return transformationSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule createRule() {
		RuleImpl rule = new RuleImpl();
		
		Graph lhs = createGraph();
		lhs.setName(DEFAULT_RULE_LHS_NAME);
		rule.setLhs(lhs);
		Graph rhs = createGraph();
		rhs.setName(DEFAULT_RULE_RHS_NAME);
		rule.setRhs(rhs);
		
		return rule;
	}// createRule

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeCondition createAttributeCondition() {
		AttributeConditionImpl attributeCondition = new AttributeConditionImpl();
		return attributeCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * Create a parameter.
	 */
	public Parameter createParameter(String name) {
		ParameterImpl parameter = new ParameterImpl();
		parameter.setName(name);
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph createGraph() {
		GraphImpl graph = new GraphImpl();
		return graph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Mapping createMapping() {
		MappingImpl mapping = new MappingImpl();
		return mapping;
	}

	/**
	 * Creates a mapping for a given node origin and image.
	 * @param origin Origin node.
	 * @param image Image node.
	 * @return The created mapping.
	 */
	public Mapping createMapping(Node origin, Node image) {
		Mapping mapping = createMapping();
		mapping.setOrigin(origin);
		mapping.setImage(image);
		return mapping;
	}// createMapping

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node createNode() {
		NodeImpl node = new NodeImpl();
		return node;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.HenshinFactory#createNode(org.eclipse.emf.henshin.model.Graph, org.eclipse.emf.ecore.EClass)
	 */
	public Node createNode(Graph graph, EClass type) {
		Node node = createNode();
		node.setType(type);
		graph.getNodes().add(node);
		return node;
	}// createNode

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.HenshinFactory#createAttribute(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.ecore.EAttribute, java.lang.String)
	 */
	public Attribute createAttribute(Node node, EAttribute type, String value) {
		Attribute attribute = createAttribute();
		attribute.setNode(node);
		attribute.setType(type);
		attribute.setValue(value);
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge createEdge() {
		EdgeImpl edge = new EdgeImpl();
		return edge;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.HenshinFactory#createEdge(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.henshin.model.Node, org.eclipse.emf.ecore.EReference)
	 */
	public Edge createEdge(Node source, Node target, EReference type) {
		Edge edge = createEdge();
		edge.setSource(source);
		edge.setTarget(target);
		edge.setType(type);
		edge.setGraph(source.getGraph());
		return edge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndependentUnit createIndependentUnit() {
		IndependentUnitImpl independentUnit = new IndependentUnitImpl();
		return independentUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SequentialUnit createSequentialUnit() {
		SequentialUnitImpl sequentialUnit = new SequentialUnitImpl();
		return sequentialUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionalUnit createConditionalUnit() {
		ConditionalUnitImpl conditionalUnit = new ConditionalUnitImpl();
		return conditionalUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PriorityUnit createPriorityUnit() {
		PriorityUnitImpl priorityUnit = new PriorityUnitImpl();
		return priorityUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AmalgamationUnit createAmalgamationUnit() {
		AmalgamationUnitImpl amalgamationUnit = new AmalgamationUnitImpl();
		return amalgamationUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CountedUnit createCountedUnit() {
		CountedUnitImpl countedUnit = new CountedUnitImpl();
		return countedUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedCondition createNestedCondition() {
		NestedConditionImpl nestedCondition = new NestedConditionImpl();
		return nestedCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public And createAnd() {
		AndImpl and = new AndImpl();
		return and;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Or createOr() {
		OrImpl or = new OrImpl();
		return or;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Not createNot() {
		NotImpl not = new NotImpl();
		return not;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Xor createXor() {
		XorImpl xor = new XorImpl();
		return xor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterMapping createParameterMapping() {
		ParameterMappingImpl parameterMapping = new ParameterMappingImpl();
		return parameterMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HenshinPackage getHenshinPackage() {
		return (HenshinPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static HenshinPackage getPackage() {
		return HenshinPackage.eINSTANCE;
	}

} //HenshinFactoryImpl
