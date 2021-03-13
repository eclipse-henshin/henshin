/**
 */
package morphisms;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pair</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link morphisms.Pair#getA <em>A</em>}</li>
 *   <li>{@link morphisms.Pair#getB <em>B</em>}</li>
 * </ul>
 *
 * @see morphisms.MorphismsPackage#getPair()
 * @model
 * @generated
 */
public interface Pair extends EObject {
	/**
	 * Returns the value of the '<em><b>A</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>A</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>A</em>' containment reference.
	 * @see #setA(Morphism)
	 * @see morphisms.MorphismsPackage#getPair_A()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Morphism getA();

	/**
	 * Sets the value of the '{@link morphisms.Pair#getA <em>A</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>A</em>' containment reference.
	 * @see #getA()
	 * @generated
	 */
	void setA(Morphism value);

	/**
	 * Returns the value of the '<em><b>B</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>B</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>B</em>' containment reference.
	 * @see #setB(Morphism)
	 * @see morphisms.MorphismsPackage#getPair_B()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Morphism getB();

	/**
	 * Sets the value of the '{@link morphisms.Pair#getB <em>B</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>B</em>' containment reference.
	 * @see #getB()
	 * @generated
	 */
	void setB(Morphism value);

} // Pair
