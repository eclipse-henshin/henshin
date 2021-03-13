/**
 */
package compactconditionmodel;

import laxcondition.Condition;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compact Condition Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compactconditionmodel.CompactConditionModel#getName <em>Name</em>}</li>
 *   <li>{@link compactconditionmodel.CompactConditionModel#getCompactconditions <em>Compactconditions</em>}</li>
 *   <li>{@link compactconditionmodel.CompactConditionModel#getTypeGraph <em>Type Graph</em>}</li>
 * </ul>
 *
 * @see compactconditionmodel.CompactconditionmodelPackage#getCompactConditionModel()
 * @model
 * @generated
 */
public interface CompactConditionModel extends EObject {
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
	 * @see compactconditionmodel.CompactconditionmodelPackage#getCompactConditionModel_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link compactconditionmodel.CompactConditionModel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Compactconditions</b></em>' containment reference list.
	 * The list contents are of type {@link laxcondition.Condition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compactconditions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compactconditions</em>' containment reference list.
	 * @see compactconditionmodel.CompactconditionmodelPackage#getCompactConditionModel_Compactconditions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Condition> getCompactconditions();

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
	 * @see compactconditionmodel.CompactconditionmodelPackage#getCompactConditionModel_TypeGraph()
	 * @model required="true"
	 * @generated
	 */
	EPackage getTypeGraph();

	/**
	 * Sets the value of the '{@link compactconditionmodel.CompactConditionModel#getTypeGraph <em>Type Graph</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Graph</em>' reference.
	 * @see #getTypeGraph()
	 * @generated
	 */
	void setTypeGraph(EPackage value);

} // CompactConditionModel
