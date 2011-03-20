/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model.impl;

import java.util.Collection;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.henshin.codegen.model.GenHenshin;
import org.eclipse.emf.henshin.codegen.model.GenHenshinPackage;
import org.eclipse.emf.henshin.codegen.model.GenTransformation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Henshin</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getCopyrightText <em>Copyright Text</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getDirectory <em>Directory</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getInterfacePackage <em>Interface Package</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getImplementationPackage <em>Implementation Package</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getGenTransformations <em>Gen Transformations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getGenModels <em>Gen Models</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenHenshinImpl extends EObjectImpl implements GenHenshin {
	/**
	 * The default value of the '{@link #getCopyrightText() <em>Copyright Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCopyrightText()
	 * @generated
	 * @ordered
	 */
	protected static final String COPYRIGHT_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCopyrightText() <em>Copyright Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCopyrightText()
	 * @generated
	 * @ordered
	 */
	protected String copyrightText = COPYRIGHT_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDirectory() <em>Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirectory()
	 * @generated
	 * @ordered
	 */
	protected static final String DIRECTORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDirectory() <em>Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirectory()
	 * @generated
	 * @ordered
	 */
	protected String directory = DIRECTORY_EDEFAULT;

	/**
	 * The default value of the '{@link #getInterfacePackage() <em>Interface Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterfacePackage()
	 * @generated
	 * @ordered
	 */
	protected static final String INTERFACE_PACKAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInterfacePackage() <em>Interface Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterfacePackage()
	 * @generated
	 * @ordered
	 */
	protected String interfacePackage = INTERFACE_PACKAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getImplementationPackage() <em>Implementation Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationPackage()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_PACKAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationPackage() <em>Implementation Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationPackage()
	 * @generated
	 * @ordered
	 */
	protected String implementationPackage = IMPLEMENTATION_PACKAGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGenTransformations() <em>Gen Transformations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenTransformations()
	 * @generated
	 * @ordered
	 */
	protected EList<GenTransformation> genTransformations;

	/**
	 * The cached value of the '{@link #getGenModels() <em>Gen Models</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenModels()
	 * @generated
	 * @ordered
	 */
	protected EList<GenModel> genModels;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenHenshinImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenHenshinPackage.Literals.GEN_HENSHIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCopyrightText() {
		return copyrightText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCopyrightText(String newCopyrightText) {
		String oldCopyrightText = copyrightText;
		copyrightText = newCopyrightText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_HENSHIN__COPYRIGHT_TEXT, oldCopyrightText, copyrightText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirectory(String newDirectory) {
		String oldDirectory = directory;
		directory = newDirectory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_HENSHIN__DIRECTORY, oldDirectory, directory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInterfacePackage() {
		return interfacePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInterfacePackage(String newInterfacePackage) {
		String oldInterfacePackage = interfacePackage;
		interfacePackage = newInterfacePackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_HENSHIN__INTERFACE_PACKAGE, oldInterfacePackage, interfacePackage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementationPackage() {
		return implementationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementationPackage(String newImplementationPackage) {
		String oldImplementationPackage = implementationPackage;
		implementationPackage = newImplementationPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PACKAGE, oldImplementationPackage, implementationPackage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GenTransformation> getGenTransformations() {
		if (genTransformations == null) {
			genTransformations = new EObjectContainmentWithInverseEList<GenTransformation>(GenTransformation.class, this, GenHenshinPackage.GEN_HENSHIN__GEN_TRANSFORMATIONS, GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN);
		}
		return genTransformations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GenModel> getGenModels() {
		if (genModels == null) {
			genModels = new EObjectResolvingEList<GenModel>(GenModel.class, this, GenHenshinPackage.GEN_HENSHIN__GEN_MODELS);
		}
		return genModels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GenHenshinPackage.GEN_HENSHIN__GEN_TRANSFORMATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getGenTransformations()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GenHenshinPackage.GEN_HENSHIN__GEN_TRANSFORMATIONS:
				return ((InternalEList<?>)getGenTransformations()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GenHenshinPackage.GEN_HENSHIN__COPYRIGHT_TEXT:
				return getCopyrightText();
			case GenHenshinPackage.GEN_HENSHIN__DIRECTORY:
				return getDirectory();
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PACKAGE:
				return getInterfacePackage();
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PACKAGE:
				return getImplementationPackage();
			case GenHenshinPackage.GEN_HENSHIN__GEN_TRANSFORMATIONS:
				return getGenTransformations();
			case GenHenshinPackage.GEN_HENSHIN__GEN_MODELS:
				return getGenModels();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GenHenshinPackage.GEN_HENSHIN__COPYRIGHT_TEXT:
				setCopyrightText((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__DIRECTORY:
				setDirectory((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PACKAGE:
				setInterfacePackage((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PACKAGE:
				setImplementationPackage((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__GEN_TRANSFORMATIONS:
				getGenTransformations().clear();
				getGenTransformations().addAll((Collection<? extends GenTransformation>)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__GEN_MODELS:
				getGenModels().clear();
				getGenModels().addAll((Collection<? extends GenModel>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case GenHenshinPackage.GEN_HENSHIN__COPYRIGHT_TEXT:
				setCopyrightText(COPYRIGHT_TEXT_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__DIRECTORY:
				setDirectory(DIRECTORY_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PACKAGE:
				setInterfacePackage(INTERFACE_PACKAGE_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PACKAGE:
				setImplementationPackage(IMPLEMENTATION_PACKAGE_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__GEN_TRANSFORMATIONS:
				getGenTransformations().clear();
				return;
			case GenHenshinPackage.GEN_HENSHIN__GEN_MODELS:
				getGenModels().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case GenHenshinPackage.GEN_HENSHIN__COPYRIGHT_TEXT:
				return COPYRIGHT_TEXT_EDEFAULT == null ? copyrightText != null : !COPYRIGHT_TEXT_EDEFAULT.equals(copyrightText);
			case GenHenshinPackage.GEN_HENSHIN__DIRECTORY:
				return DIRECTORY_EDEFAULT == null ? directory != null : !DIRECTORY_EDEFAULT.equals(directory);
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PACKAGE:
				return INTERFACE_PACKAGE_EDEFAULT == null ? interfacePackage != null : !INTERFACE_PACKAGE_EDEFAULT.equals(interfacePackage);
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PACKAGE:
				return IMPLEMENTATION_PACKAGE_EDEFAULT == null ? implementationPackage != null : !IMPLEMENTATION_PACKAGE_EDEFAULT.equals(implementationPackage);
			case GenHenshinPackage.GEN_HENSHIN__GEN_TRANSFORMATIONS:
				return genTransformations != null && !genTransformations.isEmpty();
			case GenHenshinPackage.GEN_HENSHIN__GEN_MODELS:
				return genModels != null && !genModels.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (copyrightText: ");
		result.append(copyrightText);
		result.append(", directory: ");
		result.append(directory);
		result.append(", interfacePackage: ");
		result.append(interfacePackage);
		result.append(", implementationPackage: ");
		result.append(implementationPackage);
		result.append(')');
		return result.toString();
	}

} //GenHenshinImpl
