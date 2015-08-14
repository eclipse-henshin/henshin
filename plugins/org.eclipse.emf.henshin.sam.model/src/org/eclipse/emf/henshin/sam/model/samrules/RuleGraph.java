/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samrules;

import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.RuleGraph#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getRuleGraph()
 * @model
 * @generated
 */
public interface RuleGraph extends Graph {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(GraphCondition)
	 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getRuleGraph_Condition()
	 * @model containment="true"
	 * @generated
	 */
	GraphCondition getCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samrules.RuleGraph#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(GraphCondition value);

} // RuleGraph
