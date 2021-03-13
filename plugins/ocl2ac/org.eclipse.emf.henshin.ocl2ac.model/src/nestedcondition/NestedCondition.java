/**
 */
package nestedcondition;

import graph.Graph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Nested Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link nestedcondition.NestedCondition#getVariables <em>Variables</em>}</li>
 *   <li>{@link nestedcondition.NestedCondition#getDomain <em>Domain</em>}</li>
 * </ul>
 *
 * @see nestedcondition.NestedconditionPackage#getNestedCondition()
 * @model abstract="true"
 * @generated
 */
public interface NestedCondition extends EObject {
	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link nestedcondition.Variable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see nestedcondition.NestedconditionPackage#getNestedCondition_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getVariables();

	/**
	 * Returns the value of the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' reference.
	 * @see #setDomain(Graph)
	 * @see nestedcondition.NestedconditionPackage#getNestedCondition_Domain()
	 * @model required="true"
	 * @generated
	 */
	Graph getDomain();

	/**
	 * Sets the value of the '{@link nestedcondition.NestedCondition#getDomain <em>Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(Graph value);

} // NestedCondition
