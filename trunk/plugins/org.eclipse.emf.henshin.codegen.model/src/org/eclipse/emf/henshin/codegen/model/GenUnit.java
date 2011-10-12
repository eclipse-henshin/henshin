/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getUnit <em>Unit</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getMethod <em>Method</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenUnit#isPublic <em>Public</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getGenTransformation <em>Gen Transformation</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getInputGenParameters <em>Input Gen Parameters</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getOutputGenParameters <em>Output Gen Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenUnit()
 * @model
 * @generated
 */
public interface GenUnit extends EObject {
	/**
	 * Returns the value of the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' reference.
	 * @see #setUnit(TransformationUnit)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenUnit_Unit()
	 * @model
	 * @generated
	 */
	TransformationUnit getUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getUnit <em>Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' reference.
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(TransformationUnit value);

	/**
	 * Returns the value of the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method</em>' attribute.
	 * @see #setMethod(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenUnit_Method()
	 * @model
	 * @generated
	 */
	String getMethod();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getMethod <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method</em>' attribute.
	 * @see #getMethod()
	 * @generated
	 */
	void setMethod(String value);

	/**
	 * Returns the value of the '<em><b>Public</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Public</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Public</em>' attribute.
	 * @see #setPublic(boolean)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenUnit_Public()
	 * @model default="true"
	 * @generated
	 */
	boolean isPublic();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenUnit#isPublic <em>Public</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Public</em>' attribute.
	 * @see #isPublic()
	 * @generated
	 */
	void setPublic(boolean value);

	/**
	 * Returns the value of the '<em><b>Gen Transformation</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenUnits <em>Gen Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen Transformation</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Transformation</em>' container reference.
	 * @see #setGenTransformation(GenTransformation)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenUnit_GenTransformation()
	 * @see org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenUnits
	 * @model opposite="genUnits" transient="false"
	 * @generated
	 */
	GenTransformation getGenTransformation();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getGenTransformation <em>Gen Transformation</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gen Transformation</em>' container reference.
	 * @see #getGenTransformation()
	 * @generated
	 */
	void setGenTransformation(GenTransformation value);

	/**
	 * Returns the value of the '<em><b>Input Gen Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.codegen.model.GenParameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Gen Parameters</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenUnit_InputGenParameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<GenParameter> getInputGenParameters();

	/**
	 * Returns the value of the '<em><b>Output Gen Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.codegen.model.GenParameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Gen Parameters</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenUnit_OutputGenParameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<GenParameter> getOutputGenParameters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getMethodFormatted();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getResultTypeName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String getResultTypeInterface(String indent);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model outputMapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	String getResultTypeImplementation(String indent, String result, EMap<String, String> output);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getInputGenParametersFormatted();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<GenParameter> getAllGenParameters();

} // GenUnit
