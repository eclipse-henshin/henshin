/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition;

import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A <code>GraphCondition</code> is the abstract super type for graphical conditions (optionally mixed with attribute conditions) that can be attached to a <code>RuleGraph</code> as left or right application condition or a <code>TypeGraph</code> as constraint for all graphs typed over the type graph.
 * A <code>GraphCondition</code> denotes a constraint for the type graph if it is referenced  by the <code>TypeGraph</code> via <code>constraints</code> as a <code>TypeGraphConstraint</code>. 
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getGraphCondition()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface GraphCondition extends TypeGraphCondition, AnnotatedElem {

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getGraphCondition_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);
} // GraphCondition
