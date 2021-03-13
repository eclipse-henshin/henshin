/**
 */
package nestedcondition;

import graph.Graph;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Nested Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link nestedcondition.NestedConstraint#getName <em>Name</em>}</li>
 *   <li>{@link nestedcondition.NestedConstraint#getTypeGraph <em>Type Graph</em>}</li>
 *   <li>{@link nestedcondition.NestedConstraint#getCondition <em>Condition</em>}</li>
 *   <li>{@link nestedcondition.NestedConstraint#getDomain <em>Domain</em>}</li>
 * </ul>
 *
 * @see nestedcondition.NestedconditionPackage#getNestedConstraint()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ConstraintDomainIsEmpty RootConditionDomainIsConatraintDomain TypeGraphConsistency HostGraphConsistency'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot ConstraintDomainIsEmpty='self.domain.nodes -> isEmpty() and self.domain.edges -> isEmpty()' RootConditionDomainIsConatraintDomain='self.condition.domain = self.domain' TypeGraphConsistency='graph::Graph.allInstances() -> forAll(g|g.typegraph = self.typeGraph)' HostGraphConsistency='graph::Graph.allInstances() -> forAll(g|g.edges -> forAll(e|g.nodes -> includes(e.source) and g.nodes -> includes(e.target)))'"
 * @generated
 */
public interface NestedConstraint extends EObject {
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
	 * @see nestedcondition.NestedconditionPackage#getNestedConstraint_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link nestedcondition.NestedConstraint#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see nestedcondition.NestedconditionPackage#getNestedConstraint_TypeGraph()
	 * @model required="true"
	 * @generated
	 */
	EPackage getTypeGraph();

	/**
	 * Sets the value of the '{@link nestedcondition.NestedConstraint#getTypeGraph <em>Type Graph</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Graph</em>' reference.
	 * @see #getTypeGraph()
	 * @generated
	 */
	void setTypeGraph(EPackage value);

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
	 * @see nestedcondition.NestedconditionPackage#getNestedConstraint_Condition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NestedCondition getCondition();

	/**
	 * Sets the value of the '{@link nestedcondition.NestedConstraint#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(NestedCondition value);

	/**
	 * Returns the value of the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' containment reference.
	 * @see #setDomain(Graph)
	 * @see nestedcondition.NestedconditionPackage#getNestedConstraint_Domain()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Graph getDomain();

	/**
	 * Sets the value of the '{@link nestedcondition.NestedConstraint#getDomain <em>Domain</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' containment reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(Graph value);

} // NestedConstraint
