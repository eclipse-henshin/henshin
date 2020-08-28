/**
 */
package morphisms;

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
 *   <li>{@link morphisms.Morphism#getDomain <em>Domain</em>}</li>
 *   <li>{@link morphisms.Morphism#getCodomain <em>Codomain</em>}</li>
 *   <li>{@link morphisms.Morphism#getMappings <em>Mappings</em>}</li>
 * </ul>
 *
 * @see morphisms.MorphismsPackage#getMorphism()
 * @model
 * @generated
 */
public interface Morphism extends EObject {
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
	 * @see morphisms.MorphismsPackage#getMorphism_Domain()
	 * @model
	 * @generated
	 */
	Graph getDomain();

	/**
	 * Sets the value of the '{@link morphisms.Morphism#getDomain <em>Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(Graph value);

	/**
	 * Returns the value of the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Codomain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Codomain</em>' reference.
	 * @see #setCodomain(Graph)
	 * @see morphisms.MorphismsPackage#getMorphism_Codomain()
	 * @model
	 * @generated
	 */
	Graph getCodomain();

	/**
	 * Sets the value of the '{@link morphisms.Morphism#getCodomain <em>Codomain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Codomain</em>' reference.
	 * @see #getCodomain()
	 * @generated
	 */
	void setCodomain(Graph value);

	/**
	 * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link morphisms.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings</em>' containment reference list.
	 * @see morphisms.MorphismsPackage#getMorphism_Mappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getMappings();

} // Morphism
