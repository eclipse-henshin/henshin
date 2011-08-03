/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
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
import org.eclipse.emf.henshin.codegen.model.TransformationEngine;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Henshin</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getPluginID <em>Plugin ID</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getBaseDirectory <em>Base Directory</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getSourceDirectory <em>Source Directory</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getCopyrightText <em>Copyright Text</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getInterfacePackage <em>Interface Package</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getInterfacePattern <em>Interface Pattern</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getImplementationPackage <em>Implementation Package</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getImplementationPattern <em>Implementation Pattern</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#isSupressInterfaces <em>Supress Interfaces</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getGenTransformations <em>Gen Transformations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl#getGenModels <em>Gen Models</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenHenshinImpl extends EObjectImpl implements GenHenshin {
	/**
	 * The default value of the '{@link #getPluginID() <em>Plugin ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginID()
	 * @generated
	 * @ordered
	 */
	protected static final String PLUGIN_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPluginID() <em>Plugin ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginID()
	 * @generated
	 * @ordered
	 */
	protected String pluginID = PLUGIN_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getBaseDirectory() <em>Base Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseDirectory()
	 * @generated
	 * @ordered
	 */
	protected static final String BASE_DIRECTORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBaseDirectory() <em>Base Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseDirectory()
	 * @generated
	 * @ordered
	 */
	protected String baseDirectory = BASE_DIRECTORY_EDEFAULT;

	/**
	 * The default value of the '{@link #getSourceDirectory() <em>Source Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceDirectory()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_DIRECTORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourceDirectory() <em>Source Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceDirectory()
	 * @generated
	 * @ordered
	 */
	protected String sourceDirectory = SOURCE_DIRECTORY_EDEFAULT;

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
	 * The default value of the '{@link #getInterfacePattern() <em>Interface Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterfacePattern()
	 * @generated
	 * @ordered
	 */
	protected static final String INTERFACE_PATTERN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInterfacePattern() <em>Interface Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterfacePattern()
	 * @generated
	 * @ordered
	 */
	protected String interfacePattern = INTERFACE_PATTERN_EDEFAULT;

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
	 * The default value of the '{@link #getImplementationPattern() <em>Implementation Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationPattern()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_PATTERN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationPattern() <em>Implementation Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationPattern()
	 * @generated
	 * @ordered
	 */
	protected String implementationPattern = IMPLEMENTATION_PATTERN_EDEFAULT;

	/**
	 * The default value of the '{@link #isSupressInterfaces() <em>Supress Interfaces</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSupressInterfaces()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUPRESS_INTERFACES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSupressInterfaces() <em>Supress Interfaces</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSupressInterfaces()
	 * @generated
	 * @ordered
	 */
	protected boolean supressInterfaces = SUPRESS_INTERFACES_EDEFAULT;

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
	public String getPluginID() {
		return pluginID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPluginID(String newPluginID) {
		String oldPluginID = pluginID;
		pluginID = newPluginID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_HENSHIN__PLUGIN_ID, oldPluginID, pluginID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBaseDirectory() {
		return baseDirectory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseDirectory(String newBaseDirectory) {
		String oldBaseDirectory = baseDirectory;
		baseDirectory = newBaseDirectory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_HENSHIN__BASE_DIRECTORY, oldBaseDirectory, baseDirectory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSourceDirectory() {
		return sourceDirectory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceDirectory(String newSourceDirectory) {
		String oldSourceDirectory = sourceDirectory;
		sourceDirectory = newSourceDirectory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_HENSHIN__SOURCE_DIRECTORY, oldSourceDirectory, sourceDirectory));
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
	public String getInterfacePattern() {
		return interfacePattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInterfacePattern(String newInterfacePattern) {
		String oldInterfacePattern = interfacePattern;
		interfacePattern = newInterfacePattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_HENSHIN__INTERFACE_PATTERN, oldInterfacePattern, interfacePattern));
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
	public String getImplementationPattern() {
		return implementationPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementationPattern(String newImplementationPattern) {
		String oldImplementationPattern = implementationPattern;
		implementationPattern = newImplementationPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PATTERN, oldImplementationPattern, implementationPattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSupressInterfaces() {
		return supressInterfaces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSupressInterfaces(boolean newSupressInterfaces) {
		boolean oldSupressInterfaces = supressInterfaces;
		supressInterfaces = newSupressInterfaces;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_HENSHIN__SUPRESS_INTERFACES, oldSupressInterfaces, supressInterfaces));
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
	 * @generated NOT
	 */
	public String getCopyrightComment() {
		if (copyrightText==null || copyrightText.trim().length()==0) {
			return "";
		}
		String comment = "/*******************************************************************\n";
		String[] lines = copyrightText.split("\n");
		for (String line : lines) {
			comment = comment + " * " + line + "\n";
		}
		comment = comment + " *******************************************************************/\n";
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String applyInterfacePattern(String baseName) {
		String pattern = interfacePattern!=null ? interfacePattern : "";
		return pattern.replaceAll("\\*", baseName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String applyImplementationPattern(String baseName) {
		String pattern = implementationPattern!=null ? implementationPattern : "";
		return pattern.replaceAll("\\*", baseName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<String> getRequiredPlugins() {
		List<String> plugins = new ArrayList<String>();
		plugins.add("org.eclipse.emf.ecore");
		for (GenTransformation genTrafo : getGenTransformations()) {
			if (genTrafo.getEngine()==TransformationEngine.INTERPRETER) {
				plugins.add("org.eclipse.emf.henshin.common");
				plugins.add("org.eclipse.emf.henshin.interpreter");
				plugins.add("org.eclipse.emf.henshin.model");
				break;
			}
		}
		for (GenModel genModel : getGenModels()) {
			plugins.add(genModel.getModelPluginID());
		}
		return new BasicEList<String>(plugins);
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
			case GenHenshinPackage.GEN_HENSHIN__PLUGIN_ID:
				return getPluginID();
			case GenHenshinPackage.GEN_HENSHIN__BASE_DIRECTORY:
				return getBaseDirectory();
			case GenHenshinPackage.GEN_HENSHIN__SOURCE_DIRECTORY:
				return getSourceDirectory();
			case GenHenshinPackage.GEN_HENSHIN__COPYRIGHT_TEXT:
				return getCopyrightText();
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PACKAGE:
				return getInterfacePackage();
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PATTERN:
				return getInterfacePattern();
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PACKAGE:
				return getImplementationPackage();
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PATTERN:
				return getImplementationPattern();
			case GenHenshinPackage.GEN_HENSHIN__SUPRESS_INTERFACES:
				return isSupressInterfaces();
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
			case GenHenshinPackage.GEN_HENSHIN__PLUGIN_ID:
				setPluginID((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__BASE_DIRECTORY:
				setBaseDirectory((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__SOURCE_DIRECTORY:
				setSourceDirectory((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__COPYRIGHT_TEXT:
				setCopyrightText((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PACKAGE:
				setInterfacePackage((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PATTERN:
				setInterfacePattern((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PACKAGE:
				setImplementationPackage((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PATTERN:
				setImplementationPattern((String)newValue);
				return;
			case GenHenshinPackage.GEN_HENSHIN__SUPRESS_INTERFACES:
				setSupressInterfaces((Boolean)newValue);
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
			case GenHenshinPackage.GEN_HENSHIN__PLUGIN_ID:
				setPluginID(PLUGIN_ID_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__BASE_DIRECTORY:
				setBaseDirectory(BASE_DIRECTORY_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__SOURCE_DIRECTORY:
				setSourceDirectory(SOURCE_DIRECTORY_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__COPYRIGHT_TEXT:
				setCopyrightText(COPYRIGHT_TEXT_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PACKAGE:
				setInterfacePackage(INTERFACE_PACKAGE_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PATTERN:
				setInterfacePattern(INTERFACE_PATTERN_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PACKAGE:
				setImplementationPackage(IMPLEMENTATION_PACKAGE_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PATTERN:
				setImplementationPattern(IMPLEMENTATION_PATTERN_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_HENSHIN__SUPRESS_INTERFACES:
				setSupressInterfaces(SUPRESS_INTERFACES_EDEFAULT);
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
			case GenHenshinPackage.GEN_HENSHIN__PLUGIN_ID:
				return PLUGIN_ID_EDEFAULT == null ? pluginID != null : !PLUGIN_ID_EDEFAULT.equals(pluginID);
			case GenHenshinPackage.GEN_HENSHIN__BASE_DIRECTORY:
				return BASE_DIRECTORY_EDEFAULT == null ? baseDirectory != null : !BASE_DIRECTORY_EDEFAULT.equals(baseDirectory);
			case GenHenshinPackage.GEN_HENSHIN__SOURCE_DIRECTORY:
				return SOURCE_DIRECTORY_EDEFAULT == null ? sourceDirectory != null : !SOURCE_DIRECTORY_EDEFAULT.equals(sourceDirectory);
			case GenHenshinPackage.GEN_HENSHIN__COPYRIGHT_TEXT:
				return COPYRIGHT_TEXT_EDEFAULT == null ? copyrightText != null : !COPYRIGHT_TEXT_EDEFAULT.equals(copyrightText);
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PACKAGE:
				return INTERFACE_PACKAGE_EDEFAULT == null ? interfacePackage != null : !INTERFACE_PACKAGE_EDEFAULT.equals(interfacePackage);
			case GenHenshinPackage.GEN_HENSHIN__INTERFACE_PATTERN:
				return INTERFACE_PATTERN_EDEFAULT == null ? interfacePattern != null : !INTERFACE_PATTERN_EDEFAULT.equals(interfacePattern);
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PACKAGE:
				return IMPLEMENTATION_PACKAGE_EDEFAULT == null ? implementationPackage != null : !IMPLEMENTATION_PACKAGE_EDEFAULT.equals(implementationPackage);
			case GenHenshinPackage.GEN_HENSHIN__IMPLEMENTATION_PATTERN:
				return IMPLEMENTATION_PATTERN_EDEFAULT == null ? implementationPattern != null : !IMPLEMENTATION_PATTERN_EDEFAULT.equals(implementationPattern);
			case GenHenshinPackage.GEN_HENSHIN__SUPRESS_INTERFACES:
				return supressInterfaces != SUPRESS_INTERFACES_EDEFAULT;
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
		result.append(" (pluginID: ");
		result.append(pluginID);
		result.append(", baseDirectory: ");
		result.append(baseDirectory);
		result.append(", sourceDirectory: ");
		result.append(sourceDirectory);
		result.append(", copyrightText: ");
		result.append(copyrightText);
		result.append(", interfacePackage: ");
		result.append(interfacePackage);
		result.append(", interfacePattern: ");
		result.append(interfacePattern);
		result.append(", implementationPackage: ");
		result.append(implementationPackage);
		result.append(", implementationPattern: ");
		result.append(implementationPattern);
		result.append(", supressInterfaces: ");
		result.append(supressInterfaces);
		result.append(')');
		return result.toString();
	}

} //GenHenshinImpl
