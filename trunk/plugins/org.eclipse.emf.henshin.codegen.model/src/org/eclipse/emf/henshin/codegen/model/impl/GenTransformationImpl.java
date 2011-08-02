/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model.impl;

import java.util.Collection;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.codegen.model.GenHenshin;
import org.eclipse.emf.henshin.codegen.model.GenHenshinPackage;
import org.eclipse.emf.henshin.codegen.model.GenTransformation;
import org.eclipse.emf.henshin.codegen.model.GenUnit;
import org.eclipse.emf.henshin.codegen.model.TransformationEngine;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Transformation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl#getTransformation <em>Transformation</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl#getTransformationClass <em>Transformation Class</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl#getGenPackages <em>Gen Packages</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl#getGenUnits <em>Gen Units</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl#getGenHenshin <em>Gen Henshin</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl#getEngine <em>Engine</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenTransformationImpl extends EObjectImpl implements GenTransformation {
	/**
	 * The cached value of the '{@link #getTransformation() <em>Transformation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformation()
	 * @generated
	 * @ordered
	 */
	protected TransformationSystem transformation;

	/**
	 * The default value of the '{@link #getTransformationClass() <em>Transformation Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformationClass()
	 * @generated
	 * @ordered
	 */
	protected static final String TRANSFORMATION_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTransformationClass() <em>Transformation Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformationClass()
	 * @generated
	 * @ordered
	 */
	protected String transformationClass = TRANSFORMATION_CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGenPackages() <em>Gen Packages</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenPackages()
	 * @generated
	 * @ordered
	 */
	protected EList<GenPackage> genPackages;

	/**
	 * The cached value of the '{@link #getGenUnits() <em>Gen Units</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenUnits()
	 * @generated
	 * @ordered
	 */
	protected EList<GenUnit> genUnits;

	/**
	 * The default value of the '{@link #getEngine() <em>Engine</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEngine()
	 * @generated
	 * @ordered
	 */
	protected static final TransformationEngine ENGINE_EDEFAULT = TransformationEngine.INTERPRETER;

	/**
	 * The cached value of the '{@link #getEngine() <em>Engine</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEngine()
	 * @generated
	 * @ordered
	 */
	protected TransformationEngine engine = ENGINE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenTransformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenHenshinPackage.Literals.GEN_TRANSFORMATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationSystem getTransformation() {
		if (transformation != null && transformation.eIsProxy()) {
			InternalEObject oldTransformation = (InternalEObject)transformation;
			transformation = (TransformationSystem)eResolveProxy(oldTransformation);
			if (transformation != oldTransformation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION, oldTransformation, transformation));
			}
		}
		return transformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationSystem basicGetTransformation() {
		return transformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransformation(TransformationSystem newTransformation) {
		TransformationSystem oldTransformation = transformation;
		transformation = newTransformation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION, oldTransformation, transformation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTransformationClass() {
		return transformationClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransformationClass(String newTransformationClass) {
		String oldTransformationClass = transformationClass;
		transformationClass = newTransformationClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION_CLASS, oldTransformationClass, transformationClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GenPackage> getGenPackages() {
		if (genPackages == null) {
			genPackages = new EObjectResolvingEList<GenPackage>(GenPackage.class, this, GenHenshinPackage.GEN_TRANSFORMATION__GEN_PACKAGES);
		}
		return genPackages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GenUnit> getGenUnits() {
		if (genUnits == null) {
			genUnits = new EObjectContainmentWithInverseEList<GenUnit>(GenUnit.class, this, GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS, GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION);
		}
		return genUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenHenshin getGenHenshin() {
		if (eContainerFeatureID() != GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN) return null;
		return (GenHenshin)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGenHenshin(GenHenshin newGenHenshin, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newGenHenshin, GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenHenshin(GenHenshin newGenHenshin) {
		if (newGenHenshin != eInternalContainer() || (eContainerFeatureID() != GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN && newGenHenshin != null)) {
			if (EcoreUtil.isAncestor(this, newGenHenshin))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newGenHenshin != null)
				msgs = ((InternalEObject)newGenHenshin).eInverseAdd(this, GenHenshinPackage.GEN_HENSHIN__GEN_TRANSFORMATIONS, GenHenshin.class, msgs);
			msgs = basicSetGenHenshin(newGenHenshin, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN, newGenHenshin, newGenHenshin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationEngine getEngine() {
		return engine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEngine(TransformationEngine newEngine) {
		TransformationEngine oldEngine = engine;
		engine = newEngine == null ? ENGINE_EDEFAULT : newEngine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_TRANSFORMATION__ENGINE, oldEngine, engine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public GenPackage getGenPackage(EPackage ePackage) {
		for (GenPackage genPackage : getGenPackages()) {
			if (genPackage.getEcorePackage()==ePackage) {
				return genPackage;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public GenClass getGenClass(EClass eClass) {
		GenPackage genPackage = getGenPackage(eClass.getEPackage());
		if (genPackage!=null) {
			for (GenClass genClass : genPackage.getGenClasses()) {
				if (genClass.getEcoreClass()==eClass) {
					return genClass;
				}
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getTransformationClassFormatted() {
		String result = transformationClass;
		if (result==null || result.trim().length()==0) {
			result = "Transformation";
		}
		result = result.replaceAll("\\s", "");
		return result;
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
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getGenUnits()).basicAdd(otherEnd, msgs);
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetGenHenshin((GenHenshin)otherEnd, msgs);
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
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS:
				return ((InternalEList<?>)getGenUnits()).basicRemove(otherEnd, msgs);
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				return basicSetGenHenshin(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				return eInternalContainer().eInverseRemove(this, GenHenshinPackage.GEN_HENSHIN__GEN_TRANSFORMATIONS, GenHenshin.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION:
				if (resolve) return getTransformation();
				return basicGetTransformation();
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION_CLASS:
				return getTransformationClass();
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_PACKAGES:
				return getGenPackages();
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS:
				return getGenUnits();
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				return getGenHenshin();
			case GenHenshinPackage.GEN_TRANSFORMATION__ENGINE:
				return getEngine();
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
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION:
				setTransformation((TransformationSystem)newValue);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION_CLASS:
				setTransformationClass((String)newValue);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_PACKAGES:
				getGenPackages().clear();
				getGenPackages().addAll((Collection<? extends GenPackage>)newValue);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS:
				getGenUnits().clear();
				getGenUnits().addAll((Collection<? extends GenUnit>)newValue);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				setGenHenshin((GenHenshin)newValue);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__ENGINE:
				setEngine((TransformationEngine)newValue);
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
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION:
				setTransformation((TransformationSystem)null);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION_CLASS:
				setTransformationClass(TRANSFORMATION_CLASS_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_PACKAGES:
				getGenPackages().clear();
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS:
				getGenUnits().clear();
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				setGenHenshin((GenHenshin)null);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__ENGINE:
				setEngine(ENGINE_EDEFAULT);
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
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION:
				return transformation != null;
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION_CLASS:
				return TRANSFORMATION_CLASS_EDEFAULT == null ? transformationClass != null : !TRANSFORMATION_CLASS_EDEFAULT.equals(transformationClass);
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_PACKAGES:
				return genPackages != null && !genPackages.isEmpty();
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS:
				return genUnits != null && !genUnits.isEmpty();
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				return getGenHenshin() != null;
			case GenHenshinPackage.GEN_TRANSFORMATION__ENGINE:
				return engine != ENGINE_EDEFAULT;
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
		result.append(" (transformationClass: ");
		result.append(transformationClass);
		result.append(", engine: ");
		result.append(engine);
		result.append(')');
		return result.toString();
	}

} //GenTransformationImpl
