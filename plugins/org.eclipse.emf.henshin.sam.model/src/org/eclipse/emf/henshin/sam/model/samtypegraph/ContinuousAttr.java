/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Continuous Attr</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getDerivation <em>Derivation</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getUpperBound <em>Upper Bound</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getContinuousAttr()
 * @model
 * @generated
 */
public interface ContinuousAttr extends AttributeType {
	/**
	 * Returns the value of the '<em><b>Derivation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Derivation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Derivation</em>' attribute.
	 * @see #setDerivation(float)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getContinuousAttr_Derivation()
	 * @model
	 * @generated
	 */
	float getDerivation();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getDerivation <em>Derivation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Derivation</em>' attribute.
	 * @see #getDerivation()
	 * @generated
	 */
	void setDerivation(float value);

	/**
	 * Returns the value of the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Bound</em>' attribute.
	 * @see #isSetLowerBound()
	 * @see #unsetLowerBound()
	 * @see #setLowerBound(double)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getContinuousAttr_LowerBound()
	 * @model unsettable="true"
	 * @generated
	 */
	double getLowerBound();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getLowerBound <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Bound</em>' attribute.
	 * @see #isSetLowerBound()
	 * @see #unsetLowerBound()
	 * @see #getLowerBound()
	 * @generated
	 */
	void setLowerBound(double value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getLowerBound <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLowerBound()
	 * @see #getLowerBound()
	 * @see #setLowerBound(double)
	 * @generated
	 */
	void unsetLowerBound();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getLowerBound <em>Lower Bound</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Lower Bound</em>' attribute is set.
	 * @see #unsetLowerBound()
	 * @see #getLowerBound()
	 * @see #setLowerBound(double)
	 * @generated
	 */
	boolean isSetLowerBound();

	/**
	 * Returns the value of the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Bound</em>' attribute.
	 * @see #isSetUpperBound()
	 * @see #unsetUpperBound()
	 * @see #setUpperBound(double)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getContinuousAttr_UpperBound()
	 * @model unsettable="true"
	 * @generated
	 */
	double getUpperBound();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getUpperBound <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Bound</em>' attribute.
	 * @see #isSetUpperBound()
	 * @see #unsetUpperBound()
	 * @see #getUpperBound()
	 * @generated
	 */
	void setUpperBound(double value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getUpperBound <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUpperBound()
	 * @see #getUpperBound()
	 * @see #setUpperBound(double)
	 * @generated
	 */
	void unsetUpperBound();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr#getUpperBound <em>Upper Bound</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Upper Bound</em>' attribute is set.
	 * @see #unsetUpperBound()
	 * @see #getUpperBound()
	 * @see #setUpperBound(double)
	 * @generated
	 */
	boolean isSetUpperBound();

} // ContinuousAttr
