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
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getPluginID <em>Plugin ID</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getBaseDirectory <em>Base Directory</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getSourceDirectory <em>Source Directory</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getCopyrightText <em>Copyright Text</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getInterfacePackage <em>Interface Package</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getInterfacePattern <em>Interface Pattern</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getImplementationPackage <em>Implementation Package</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getImplementationPattern <em>Implementation Pattern</em>}</li>
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
	 * Returns the value of the '<em><b>Plugin ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugin ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plugin ID</em>' attribute.
	 * @see #setPluginID(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin_PluginID()
	 * @model
	 * @generated
	 */
	String getPluginID();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getPluginID <em>Plugin ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plugin ID</em>' attribute.
	 * @see #getPluginID()
	 * @generated
	 */
	void setPluginID(String value);

	/**
	 * Returns the value of the '<em><b>Base Directory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Directory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Directory</em>' attribute.
	 * @see #setBaseDirectory(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin_BaseDirectory()
	 * @model
	 * @generated
	 */
	String getBaseDirectory();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getBaseDirectory <em>Base Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Directory</em>' attribute.
	 * @see #getBaseDirectory()
	 * @generated
	 */
	void setBaseDirectory(String value);

	/**
	 * Returns the value of the '<em><b>Source Directory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Directory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Directory</em>' attribute.
	 * @see #setSourceDirectory(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin_SourceDirectory()
	 * @model
	 * @generated
	 */
	String getSourceDirectory();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getSourceDirectory <em>Source Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Directory</em>' attribute.
	 * @see #getSourceDirectory()
	 * @generated
	 */
	void setSourceDirectory(String value);

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
	 * Returns the value of the '<em><b>Interface Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interface Pattern</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interface Pattern</em>' attribute.
	 * @see #setInterfacePattern(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin_InterfacePattern()
	 * @model
	 * @generated
	 */
	String getInterfacePattern();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getInterfacePattern <em>Interface Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interface Pattern</em>' attribute.
	 * @see #getInterfacePattern()
	 * @generated
	 */
	void setInterfacePattern(String value);

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
	 * Returns the value of the '<em><b>Implementation Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementation Pattern</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementation Pattern</em>' attribute.
	 * @see #setImplementationPattern(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenHenshin_ImplementationPattern()
	 * @model
	 * @generated
	 */
	String getImplementationPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getImplementationPattern <em>Implementation Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementation Pattern</em>' attribute.
	 * @see #getImplementationPattern()
	 * @generated
	 */
	void setImplementationPattern(String value);

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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getCopyrightComment();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String applyInterfacePattern(String baseName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String applyImplementationPattern(String baseName);

} // GenHenshin
