/**
 */
package nestedcondition;

import graph.Graph;

import laxcondition.Quantifier;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Quantified Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link nestedcondition.QuantifiedCondition#getQuantifier <em>Quantifier</em>}</li>
 *   <li>{@link nestedcondition.QuantifiedCondition#getCondition <em>Condition</em>}</li>
 *   <li>{@link nestedcondition.QuantifiedCondition#getMorphism <em>Morphism</em>}</li>
 *   <li>{@link nestedcondition.QuantifiedCondition#getCodomain <em>Codomain</em>}</li>
 * </ul>
 *
 * @see nestedcondition.NestedconditionPackage#getQuantifiedCondition()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='NestedDomainIsCoDomain MorphismIsFromDomainToCoDomain'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot NestedDomainIsCoDomain='self.condition.domain = self.codomain' MorphismIsFromDomainToCoDomain='self.morphism.from = self.domain and self.morphism.to = self.codomain'"
 * @generated
 */
public interface QuantifiedCondition extends NestedCondition {
	/**
	 * Returns the value of the '<em><b>Quantifier</b></em>' attribute.
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
	 * @see nestedcondition.NestedconditionPackage#getQuantifiedCondition_Quantifier()
	 * @model
	 * @generated
	 */
	Quantifier getQuantifier();

	/**
	 * Sets the value of the '{@link nestedcondition.QuantifiedCondition#getQuantifier <em>Quantifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Quantifier</em>' attribute.
	 * @see laxcondition.Quantifier
	 * @see #getQuantifier()
	 * @generated
	 */
	void setQuantifier(Quantifier value);

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(NestedCondition)
	 * @see nestedcondition.NestedconditionPackage#getQuantifiedCondition_Condition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NestedCondition getCondition();

	/**
	 * Sets the value of the '{@link nestedcondition.QuantifiedCondition#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(NestedCondition value);

	/**
	 * Returns the value of the '<em><b>Morphism</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Morphism</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Morphism</em>' containment reference.
	 * @see #setMorphism(Morphism)
	 * @see nestedcondition.NestedconditionPackage#getQuantifiedCondition_Morphism()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Morphism getMorphism();

	/**
	 * Sets the value of the '{@link nestedcondition.QuantifiedCondition#getMorphism <em>Morphism</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Morphism</em>' containment reference.
	 * @see #getMorphism()
	 * @generated
	 */
	void setMorphism(Morphism value);

	/**
	 * Returns the value of the '<em><b>Codomain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Codomain</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Codomain</em>' containment reference.
	 * @see #setCodomain(Graph)
	 * @see nestedcondition.NestedconditionPackage#getQuantifiedCondition_Codomain()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Graph getCodomain();

	/**
	 * Sets the value of the '{@link nestedcondition.QuantifiedCondition#getCodomain <em>Codomain</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Codomain</em>' containment reference.
	 * @see #getCodomain()
	 * @generated
	 */
	void setCodomain(Graph value);

} // QuantifiedCondition
