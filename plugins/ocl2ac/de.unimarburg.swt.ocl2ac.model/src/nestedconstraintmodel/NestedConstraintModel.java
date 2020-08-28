/**
 */
package nestedconstraintmodel;

import nestedcondition.NestedConstraint;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Nested Constraint Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link nestedconstraintmodel.NestedConstraintModel#getName <em>Name</em>}</li>
 *   <li>{@link nestedconstraintmodel.NestedConstraintModel#getNestedconstrainmodels <em>Nestedconstrainmodels</em>}</li>
 * </ul>
 *
 * @see nestedconstraintmodel.NestedconstraintmodelPackage#getNestedConstraintModel()
 * @model
 * @generated
 */
public interface NestedConstraintModel extends EObject {
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
	 * @see nestedconstraintmodel.NestedconstraintmodelPackage#getNestedConstraintModel_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link nestedconstraintmodel.NestedConstraintModel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Nestedconstrainmodels</b></em>' containment reference list.
	 * The list contents are of type {@link nestedcondition.NestedConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nestedconstrainmodels</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nestedconstrainmodels</em>' containment reference list.
	 * @see nestedconstraintmodel.NestedconstraintmodelPackage#getNestedConstraintModel_Nestedconstrainmodels()
	 * @model containment="true"
	 * @generated
	 */
	EList<NestedConstraint> getNestedconstrainmodels();

} // NestedConstraintModel
