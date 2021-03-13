/**
 */
package laxcondition;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link laxcondition.Condition#getTypeGraph <em>Type Graph</em>}</li>
 *   <li>{@link laxcondition.Condition#getName <em>Name</em>}</li>
 *   <li>{@link laxcondition.Condition#getLaxCondition <em>Lax Condition</em>}</li>
 * </ul>
 *
 * @see laxcondition.LaxconditionPackage#getCondition()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='TypeGraphConsistency'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot TypeGraphConsistency='graph_0::Graph.allInstances() -> forAll(g|g.typegraph = self.typeGraph)'"
 * @generated
 */
public interface Condition extends EObject {
	/**
	 * Returns the value of the '<em><b>Type Graph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Graph</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Graph</em>' reference.
	 * @see #setTypeGraph(EPackage)
	 * @see laxcondition.LaxconditionPackage#getCondition_TypeGraph()
	 * @model required="true"
	 * @generated
	 */
	EPackage getTypeGraph();

	/**
	 * Sets the value of the '{@link laxcondition.Condition#getTypeGraph <em>Type Graph</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Graph</em>' reference.
	 * @see #getTypeGraph()
	 * @generated
	 */
	void setTypeGraph(EPackage value);

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
	 * @see laxcondition.LaxconditionPackage#getCondition_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link laxcondition.Condition#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Lax Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lax Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lax Condition</em>' containment reference.
	 * @see #setLaxCondition(LaxCondition)
	 * @see laxcondition.LaxconditionPackage#getCondition_LaxCondition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	LaxCondition getLaxCondition();

	/**
	 * Sets the value of the '{@link laxcondition.Condition#getLaxCondition <em>Lax Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lax Condition</em>' containment reference.
	 * @see #getLaxCondition()
	 * @generated
	 */
	void setLaxCondition(LaxCondition value);

} // Condition
