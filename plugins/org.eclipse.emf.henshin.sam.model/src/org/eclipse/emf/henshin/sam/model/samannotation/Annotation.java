/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samannotation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getDetails <em>Details</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getRefInRule <em>Ref In Rule</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage#getAnnotation()
 * @model
 * @generated
 */
public interface Annotation extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see #setSource(String)
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage#getAnnotation_Source()
	 * @model required="true"
	 * @generated
	 */
	String getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getSource <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' attribute.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(String value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EObject)
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage#getAnnotation_Target()
	 * @model
	 * @generated
	 */
	EObject getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(EObject value);

	/**
	 * Returns the value of the '<em><b>Details</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samannotation.AnnotationDetails}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Details</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Details</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage#getAnnotation_Details()
	 * @model containment="true"
	 * @generated
	 */
	EList<AnnotationDetails> getDetails();

	/**
	 * Returns the value of the '<em><b>Ref In Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ref In Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ref In Rule</em>' reference.
	 * @see #setRefInRule(Annotation)
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage#getAnnotation_RefInRule()
	 * @model
	 * @generated
	 */
	Annotation getRefInRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getRefInRule <em>Ref In Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ref In Rule</em>' reference.
	 * @see #getRefInRule()
	 * @generated
	 */
	void setRefInRule(Annotation value);

} // Annotation
