/**
 */
package laxcondition;

import graph.Graph;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Quantified Lax Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link laxcondition.QuantifiedLaxCondition#getGraph <em>Graph</em>}</li>
 *   <li>{@link laxcondition.QuantifiedLaxCondition#getCondition <em>Condition</em>}</li>
 *   <li>{@link laxcondition.QuantifiedLaxCondition#getQuantifier <em>Quantifier</em>}</li>
 *   <li>{@link laxcondition.QuantifiedLaxCondition#getVariables <em>Variables</em>}</li>
 * </ul>
 *
 * @see laxcondition.LaxconditionPackage#getQuantifiedLaxCondition()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='HostGraphConsistency'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot HostGraphConsistency='self.graph.edges -> forAll(e|self.graph.nodes -> includes(e.source) and self.graph.nodes -> includes(e.target))'"
 * @generated
 */
public interface QuantifiedLaxCondition extends LaxCondition {
	/**
	 * Returns the value of the '<em><b>Graph</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Graph</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graph</em>' containment reference.
	 * @see #setGraph(Graph)
	 * @see laxcondition.LaxconditionPackage#getQuantifiedLaxCondition_Graph()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Graph getGraph();

	/**
	 * Sets the value of the '{@link laxcondition.QuantifiedLaxCondition#getGraph <em>Graph</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graph</em>' containment reference.
	 * @see #getGraph()
	 * @generated
	 */
	void setGraph(Graph value);

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(LaxCondition)
	 * @see laxcondition.LaxconditionPackage#getQuantifiedLaxCondition_Condition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	LaxCondition getCondition();

	/**
	 * Sets the value of the '{@link laxcondition.QuantifiedLaxCondition#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(LaxCondition value);

	/**
	 * Returns the value of the '<em><b>Quantifier</b></em>' attribute.
	 * The default value is <code>"EXISTS"</code>.
	 * The literals are from the enumeration {@link laxcondition.Quantifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Quantifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Quantifier</em>' attribute.
	 * @see laxcondition.Quantifier
	 * @see #setQuantifier(Quantifier)
	 * @see laxcondition.LaxconditionPackage#getQuantifiedLaxCondition_Quantifier()
	 * @model default="EXISTS" required="true"
	 * @generated
	 */
	Quantifier getQuantifier();

	/**
	 * Sets the value of the '{@link laxcondition.QuantifiedLaxCondition#getQuantifier <em>Quantifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Quantifier</em>' attribute.
	 * @see laxcondition.Quantifier
	 * @see #getQuantifier()
	 * @generated
	 */
	void setQuantifier(Quantifier value);

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link laxcondition.Variable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see laxcondition.LaxconditionPackage#getQuantifiedLaxCondition_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getVariables();

} // QuantifiedLaxCondition
