/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Transformation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getTransformation <em>Transformation</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getTransformationClass <em>Transformation Class</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenPackages <em>Gen Packages</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenUnits <em>Gen Units</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenHenshin <em>Gen Henshin</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getEngine <em>Engine</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenTransformation()
 * @model
 * @generated
 */
public interface GenTransformation extends EObject {
	/**
	 * Returns the value of the '<em><b>Transformation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformation</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformation</em>' reference.
	 * @see #setTransformation(TransformationSystem)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenTransformation_Transformation()
	 * @model
	 * @generated
	 */
	TransformationSystem getTransformation();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getTransformation <em>Transformation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transformation</em>' reference.
	 * @see #getTransformation()
	 * @generated
	 */
	void setTransformation(TransformationSystem value);

	/**
	 * Returns the value of the '<em><b>Transformation Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformation Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformation Class</em>' attribute.
	 * @see #setTransformationClass(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenTransformation_TransformationClass()
	 * @model
	 * @generated
	 */
	String getTransformationClass();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getTransformationClass <em>Transformation Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transformation Class</em>' attribute.
	 * @see #getTransformationClass()
	 * @generated
	 */
	void setTransformationClass(String value);

	/**
	 * Returns the value of the '<em><b>Gen Packages</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.codegen.ecore.genmodel.GenPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen Packages</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Packages</em>' reference list.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenTransformation_GenPackages()
	 * @model
	 * @generated
	 */
	EList<GenPackage> getGenPackages();

	/**
	 * Returns the value of the '<em><b>Gen Units</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.codegen.model.GenUnit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen Units</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Units</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenTransformation_GenUnits()
	 * @model containment="true"
	 * @generated
	 */
	EList<GenUnit> getGenUnits();

	/**
	 * Returns the value of the '<em><b>Gen Henshin</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getGenTransformations <em>Gen Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen Henshin</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Henshin</em>' container reference.
	 * @see #setGenHenshin(GenHenshin)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenTransformation_GenHenshin()
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshin#getGenTransformations
	 * @model opposite="genTransformations" transient="false"
	 * @generated
	 */
	GenHenshin getGenHenshin();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenHenshin <em>Gen Henshin</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gen Henshin</em>' container reference.
	 * @see #getGenHenshin()
	 * @generated
	 */
	void setGenHenshin(GenHenshin value);

	/**
	 * Returns the value of the '<em><b>Engine</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.henshin.codegen.model.TransformationEngine}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Engine</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Engine</em>' attribute.
	 * @see org.eclipse.emf.henshin.codegen.model.TransformationEngine
	 * @see #setEngine(TransformationEngine)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenTransformation_Engine()
	 * @model
	 * @generated
	 */
	TransformationEngine getEngine();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getEngine <em>Engine</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Engine</em>' attribute.
	 * @see org.eclipse.emf.henshin.codegen.model.TransformationEngine
	 * @see #getEngine()
	 * @generated
	 */
	void setEngine(TransformationEngine value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	GenPackage getGenPackage(EPackage ePackage);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getTransformationClassFormatted();

} // GenTransformation
