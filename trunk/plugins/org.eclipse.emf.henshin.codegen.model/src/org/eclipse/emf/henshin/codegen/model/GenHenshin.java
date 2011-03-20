/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Henshin</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getCopyrightText <em>Copyright Text</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getDirectory <em>Directory</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getInterfacePackage <em>Interface Package</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getImplementationPackage <em>Implementation Package</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getGenTransformations <em>Gen Transformations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getGenModels <em>Gen Models</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin()
 * @model
 * @generated
 */
public interface GenHenshin extends EObject {
	/**
	 * Returns the value of the '<em><b>Copyright Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Copyright Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Copyright Text</em>' attribute.
	 * @see #setCopyrightText(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin_CopyrightText()
	 * @model
	 * @generated
	 */
	String getCopyrightText();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getCopyrightText <em>Copyright Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Copyright Text</em>' attribute.
	 * @see #getCopyrightText()
	 * @generated
	 */
	void setCopyrightText(String value);

	/**
	 * Returns the value of the '<em><b>Directory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Directory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Directory</em>' attribute.
	 * @see #setDirectory(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin_Directory()
	 * @model
	 * @generated
	 */
	String getDirectory();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getDirectory <em>Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Directory</em>' attribute.
	 * @see #getDirectory()
	 * @generated
	 */
	void setDirectory(String value);

	/**
	 * Returns the value of the '<em><b>Interface Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interface Package</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interface Package</em>' attribute.
	 * @see #setInterfacePackage(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin_InterfacePackage()
	 * @model
	 * @generated
	 */
	String getInterfacePackage();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getInterfacePackage <em>Interface Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interface Package</em>' attribute.
	 * @see #getInterfacePackage()
	 * @generated
	 */
	void setInterfacePackage(String value);

	/**
	 * Returns the value of the '<em><b>Implementation Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementation Package</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementation Package</em>' attribute.
	 * @see #setImplementationPackage(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin_ImplementationPackage()
	 * @model
	 * @generated
	 */
	String getImplementationPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getImplementationPackage <em>Implementation Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementation Package</em>' attribute.
	 * @see #getImplementationPackage()
	 * @generated
	 */
	void setImplementationPackage(String value);

	/**
	 * Returns the value of the '<em><b>Gen Transformations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.codegen.model.GenTransformation}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenHenshin <em>Gen Henshin</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen Transformations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Transformations</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin_GenTransformations()
	 * @see org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenHenshin
	 * @model opposite="genHenshin" containment="true"
	 * @generated
	 */
	EList<GenTransformation> getGenTransformations();

	/**
	 * Returns the value of the '<em><b>Gen Models</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.codegen.ecore.genmodel.GenModel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen Models</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Models</em>' reference list.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin_GenModels()
	 * @model
	 * @generated
	 */
	EList<GenModel> getGenModels();

} // GenHenshin
