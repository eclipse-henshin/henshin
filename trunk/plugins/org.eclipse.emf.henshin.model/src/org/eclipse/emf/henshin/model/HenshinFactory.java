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
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.model.HenshinPackage
 * @generated
 */
public interface HenshinFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HenshinFactory eINSTANCE = org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Transformation System</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transformation System</em>'.
	 * @generated
	 */
	TransformationSystem createTransformationSystem();

	/**
	 * Returns a new object of class '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * Remark: LHS '<em>Graph</em>' and RHS '<em>Graph</em>' are created as well
	 * and associated with this rule.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule</em>'.
	 * @generated
	 */
	Rule createRule();

	/**
	 * Returns a new object of class '<em>Attribute Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Condition</em>'.
	 * @generated
	 */
	AttributeCondition createAttributeCondition();

	/**
	 * Returns a new object of class '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter</em>'.
	 * @generated
	 */
	Parameter createParameter();

	/**
	 * Returns a new object of class '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter</em>'.
	 * @param name The name of the parameter.
	 * @generated
	 */
	Parameter createParameter(String name);

	/**
	 * Returns a new object of class '<em>Graph</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Graph</em>'.
	 * @generated
	 */
	Graph createGraph();

	/**
	 * Returns a new object of class '<em>Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping</em>'.
	 * @generated
	 */
	Mapping createMapping();

	/**
	 * Creates a mapping for a given node origin and image.
	 * @param origin Origin node.
	 * @param image Image node.
	 * @return The created mapping.
	 */
	Mapping createMapping(Node origin, Node image);	
	
	/**
	 * Returns a new object of class '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node</em>'.
	 * @generated
	 */
	Node createNode();

	/**
	 * Creates a new node and adds it to a graph.
	 * @param graph Graph to be added to.
	 * @param type Type of the node.
	 * @return The created node.
	 */
	Node createNode(Graph graph, EClass type);
	
	/**
	 * Returns a new object of class '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute</em>'.
	 * @generated
	 */
	Attribute createAttribute();

	/**
	 * Create an attribute for a given node, type and value.
	 * @param node Node to which the attribute is added.
	 * @param type Attribute type.
	 * @param value Attribute value.
	 * @return The created attribute.
	 */
	Attribute createAttribute(Node node, EAttribute type, String value);

	/**
	 * Returns a new object of class '<em>Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Edge</em>'.
	 * @generated
	 */
	Edge createEdge();

	/**
	 * Creates a new edge between two nodes. The edge is automatically
	 * added to the graph of the source node if not <code>null</code>.
	 * @param source Source node.
	 * @param target Target node.
	 * @param type Edge type.
	 * @return The created edge.
	 */
	Edge createEdge(Node source, Node target, EReference type);
	
	/**
	 * Returns a new object of class '<em>Independent Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Independent Unit</em>'.
	 * @generated
	 */
	IndependentUnit createIndependentUnit();

	/**
	 * Returns a new object of class '<em>Sequential Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sequential Unit</em>'.
	 * @generated
	 */
	SequentialUnit createSequentialUnit();

	/**
	 * Returns a new object of class '<em>Conditional Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Conditional Unit</em>'.
	 * @generated
	 */
	ConditionalUnit createConditionalUnit();

	/**
	 * Returns a new object of class '<em>Priority Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Priority Unit</em>'.
	 * @generated
	 */
	PriorityUnit createPriorityUnit();

	/**
	 * Returns a new object of class '<em>Amalgamation Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Amalgamation Unit</em>'.
	 * @generated
	 */
	AmalgamationUnit createAmalgamationUnit();

	/**
	 * Returns a new object of class '<em>Counted Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Counted Unit</em>'.
	 * @generated
	 */
	CountedUnit createCountedUnit();

	/**
	 * Returns a new object of class '<em>Nested Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Nested Condition</em>'.
	 * @generated
	 */
	NestedCondition createNestedCondition();

	/**
	 * Returns a new object of class '<em>And</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>And</em>'.
	 * @generated
	 */
	And createAnd();

	/**
	 * Returns a new object of class '<em>Or</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Or</em>'.
	 * @generated
	 */
	Or createOr();

	/**
	 * Returns a new object of class '<em>Not</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Not</em>'.
	 * @generated
	 */
	Not createNot();

	/**
	 * Returns a new object of class '<em>Xor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Xor</em>'.
	 * @generated
	 */
	Xor createXor();

	/**
	 * Returns a new object of class '<em>Parameter Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter Mapping</em>'.
	 * @generated
	 */
	ParameterMapping createParameterMapping();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	HenshinPackage getHenshinPackage();

} //HenshinFactory
