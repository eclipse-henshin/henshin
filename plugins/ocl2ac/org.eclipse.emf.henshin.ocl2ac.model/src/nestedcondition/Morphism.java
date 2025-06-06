/**
 */
package nestedcondition;

import graph.Graph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Morphism</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link nestedcondition.Morphism#getNodeMappings <em>Node Mappings</em>}</li>
 *   <li>{@link nestedcondition.Morphism#getFrom <em>From</em>}</li>
 *   <li>{@link nestedcondition.Morphism#getTo <em>To</em>}</li>
 *   <li>{@link nestedcondition.Morphism#getEdgeMappings <em>Edge Mappings</em>}</li>
 * </ul>
 *
 * @see nestedcondition.NestedconditionPackage#getMorphism()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='NodeMappingIsFromDomainToCoDomain EdgeMappingIsFromDomainToCoDomain EdgeMappingConsistency'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot NodeMappingIsFromDomainToCoDomain='self.nodeMappings -&gt; forAll(m|self.from.nodes -&gt; includes(m.origin) and self.to.nodes -&gt; includes(m.image))' EdgeMappingIsFromDomainToCoDomain='self.edgeMappings -&gt; forAll(m|self.from.edges -&gt; includes(m.origin) and self.to.edges -&gt; includes(m.image))' EdgeMappingConsistency='self.edgeMappings -&gt; forAll(em|self.nodeMappings -&gt; exists(nm|nm.origin = em.origin.source and nm.image = em.image.source) and self.nodeMappings -&gt; exists(nm|nm.origin = em.origin.target and nm.image = em.image.target))'"
 * @generated
 */
public interface Morphism extends EObject {
	/**
	 * Returns the value of the '<em><b>Node Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link nestedcondition.NodeMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Mappings</em>' containment reference list.
	 * @see nestedcondition.NestedconditionPackage#getMorphism_NodeMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<NodeMapping> getNodeMappings();

	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Graph)
	 * @see nestedcondition.NestedconditionPackage#getMorphism_From()
	 * @model required="true"
	 * @generated
	 */
	Graph getFrom();

	/**
	 * Sets the value of the '{@link nestedcondition.Morphism#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(Graph value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Graph)
	 * @see nestedcondition.NestedconditionPackage#getMorphism_To()
	 * @model required="true"
	 * @generated
	 */
	Graph getTo();

	/**
	 * Sets the value of the '{@link nestedcondition.Morphism#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Graph value);

	/**
	 * Returns the value of the '<em><b>Edge Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link nestedcondition.EdgeMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edge Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edge Mappings</em>' containment reference list.
	 * @see nestedcondition.NestedconditionPackage#getMorphism_EdgeMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<EdgeMapping> getEdgeMappings();

} // Morphism
