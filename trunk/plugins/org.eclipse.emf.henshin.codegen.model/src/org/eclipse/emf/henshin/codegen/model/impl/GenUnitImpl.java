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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.codegen.model.GenHenshinPackage;
import org.eclipse.emf.henshin.codegen.model.GenParameter;
import org.eclipse.emf.henshin.codegen.model.GenTransformation;
import org.eclipse.emf.henshin.codegen.model.GenUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenUnitImpl#getUnit <em>Unit</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenUnitImpl#getMethod <em>Method</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenUnitImpl#isPublic <em>Public</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenUnitImpl#getGenTransformation <em>Gen Transformation</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenUnitImpl#getInputGenParameters <em>Input Gen Parameters</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenUnitImpl#getOutputGenParameters <em>Output Gen Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenUnitImpl extends EObjectImpl implements GenUnit {
	/**
	 * The cached value of the '{@link #getUnit() <em>Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnit()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit unit;

	/**
	 * The default value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected String method = METHOD_EDEFAULT;

	/**
	 * The default value of the '{@link #isPublic() <em>Public</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPublic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PUBLIC_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isPublic() <em>Public</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPublic()
	 * @generated
	 * @ordered
	 */
	protected boolean public_ = PUBLIC_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInputGenParameters() <em>Input Gen Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputGenParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<GenParameter> inputGenParameters;

	/**
	 * The cached value of the '{@link #getOutputGenParameters() <em>Output Gen Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputGenParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<GenParameter> outputGenParameters;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenHenshinPackage.Literals.GEN_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getUnit() {
		if (unit != null && unit.eIsProxy()) {
			InternalEObject oldUnit = (InternalEObject)unit;
			unit = (TransformationUnit)eResolveProxy(oldUnit);
			if (unit != oldUnit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GenHenshinPackage.GEN_UNIT__UNIT, oldUnit, unit));
			}
		}
		return unit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit basicGetUnit() {
		return unit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnit(TransformationUnit newUnit) {
		TransformationUnit oldUnit = unit;
		unit = newUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_UNIT__UNIT, oldUnit, unit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethod(String newMethod) {
		String oldMethod = method;
		method = newMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_UNIT__METHOD, oldMethod, method));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPublic() {
		return public_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublic(boolean newPublic) {
		boolean oldPublic = public_;
		public_ = newPublic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_UNIT__PUBLIC, oldPublic, public_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenTransformation getGenTransformation() {
		if (eContainerFeatureID() != GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION) return null;
		return (GenTransformation)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGenTransformation(GenTransformation newGenTransformation, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newGenTransformation, GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenTransformation(GenTransformation newGenTransformation) {
		if (newGenTransformation != eInternalContainer() || (eContainerFeatureID() != GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION && newGenTransformation != null)) {
			if (EcoreUtil.isAncestor(this, newGenTransformation))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newGenTransformation != null)
				msgs = ((InternalEObject)newGenTransformation).eInverseAdd(this, GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS, GenTransformation.class, msgs);
			msgs = basicSetGenTransformation(newGenTransformation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION, newGenTransformation, newGenTransformation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GenParameter> getInputGenParameters() {
		if (inputGenParameters == null) {
			inputGenParameters = new EObjectContainmentEList<GenParameter>(GenParameter.class, this, GenHenshinPackage.GEN_UNIT__INPUT_GEN_PARAMETERS);
		}
		return inputGenParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GenParameter> getOutputGenParameters() {
		if (outputGenParameters == null) {
			outputGenParameters = new EObjectContainmentEList<GenParameter>(GenParameter.class, this, GenHenshinPackage.GEN_UNIT__OUTPUT_GEN_PARAMETERS);
		}
		return outputGenParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getMethodFormatted() {
		List<String> previousMethods = new ArrayList<String>();
		if (getGenTransformation()!=null) {
			for (GenUnit previous : getGenTransformation().getGenUnits()) {
				if (previous==this) break;
				previousMethods.add(previous.getMethodFormatted());
			}
		}
		String result = method;
		if (result==null || result.trim().length()==0) {
			result = "unit";
		}
		if (previousMethods.contains(result)) {
			int i = 2;
			while (previousMethods.contains(result + String.valueOf(i))) {
				i++;
			}
			result = result + String.valueOf(i);
		}		
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getResultTypeName() {
		if (getOutputGenParameters().isEmpty()) {
			return Boolean.TYPE.getName();
		} else {
			return capitalize(getMethodFormatted()) + "Result";
		}
	}
	
	/*
	 * Capitalize a string.
	 */
	private static String capitalize(String string) {
		return string = string.substring(0, 1).toUpperCase() + string.substring(1);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getResultTypeInterface(String indent) {
		if (getOutputGenParameters().isEmpty()) {
			return "";
		} else {
			String result = indent + "interface " + getResultTypeName() + " {\n";
			result = result + indent + "\t" + "getResult();\n";
			for (GenParameter outParam : getOutputGenParameters()) {
				result = result + indent + "\t" + "get" + capitalize(outParam.getNameFormatted()) + "();\n";
			}
			result = result + "\n" + indent + "}";
			return result;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getResultTypeImplementation(String indent, boolean result, EMap<String, String> output) {
		if (getOutputGenParameters().isEmpty()) {
			return String.valueOf(result);
		} else {
			return "new " + getResultTypeName() + "() {}";
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getInputGenParametersFormatted() {
		String paramsString = "";
		int count = getInputGenParameters().size();
		for (int i=0; i<count; i++) {
			GenParameter param = getInputGenParameters().get(i);
			paramsString = paramsString + param.getType() + " " + param.getNameFormatted();
			if (i<count-1) paramsString = paramsString + ", ";
		}
		return paramsString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetGenTransformation((GenTransformation)otherEnd, msgs);
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
			case GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION:
				return basicSetGenTransformation(null, msgs);
			case GenHenshinPackage.GEN_UNIT__INPUT_GEN_PARAMETERS:
				return ((InternalEList<?>)getInputGenParameters()).basicRemove(otherEnd, msgs);
			case GenHenshinPackage.GEN_UNIT__OUTPUT_GEN_PARAMETERS:
				return ((InternalEList<?>)getOutputGenParameters()).basicRemove(otherEnd, msgs);
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
			case GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION:
				return eInternalContainer().eInverseRemove(this, GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS, GenTransformation.class, msgs);
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
			case GenHenshinPackage.GEN_UNIT__UNIT:
				if (resolve) return getUnit();
				return basicGetUnit();
			case GenHenshinPackage.GEN_UNIT__METHOD:
				return getMethod();
			case GenHenshinPackage.GEN_UNIT__PUBLIC:
				return isPublic();
			case GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION:
				return getGenTransformation();
			case GenHenshinPackage.GEN_UNIT__INPUT_GEN_PARAMETERS:
				return getInputGenParameters();
			case GenHenshinPackage.GEN_UNIT__OUTPUT_GEN_PARAMETERS:
				return getOutputGenParameters();
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
			case GenHenshinPackage.GEN_UNIT__UNIT:
				setUnit((TransformationUnit)newValue);
				return;
			case GenHenshinPackage.GEN_UNIT__METHOD:
				setMethod((String)newValue);
				return;
			case GenHenshinPackage.GEN_UNIT__PUBLIC:
				setPublic((Boolean)newValue);
				return;
			case GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION:
				setGenTransformation((GenTransformation)newValue);
				return;
			case GenHenshinPackage.GEN_UNIT__INPUT_GEN_PARAMETERS:
				getInputGenParameters().clear();
				getInputGenParameters().addAll((Collection<? extends GenParameter>)newValue);
				return;
			case GenHenshinPackage.GEN_UNIT__OUTPUT_GEN_PARAMETERS:
				getOutputGenParameters().clear();
				getOutputGenParameters().addAll((Collection<? extends GenParameter>)newValue);
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
			case GenHenshinPackage.GEN_UNIT__UNIT:
				setUnit((TransformationUnit)null);
				return;
			case GenHenshinPackage.GEN_UNIT__METHOD:
				setMethod(METHOD_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_UNIT__PUBLIC:
				setPublic(PUBLIC_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION:
				setGenTransformation((GenTransformation)null);
				return;
			case GenHenshinPackage.GEN_UNIT__INPUT_GEN_PARAMETERS:
				getInputGenParameters().clear();
				return;
			case GenHenshinPackage.GEN_UNIT__OUTPUT_GEN_PARAMETERS:
				getOutputGenParameters().clear();
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
			case GenHenshinPackage.GEN_UNIT__UNIT:
				return unit != null;
			case GenHenshinPackage.GEN_UNIT__METHOD:
				return METHOD_EDEFAULT == null ? method != null : !METHOD_EDEFAULT.equals(method);
			case GenHenshinPackage.GEN_UNIT__PUBLIC:
				return public_ != PUBLIC_EDEFAULT;
			case GenHenshinPackage.GEN_UNIT__GEN_TRANSFORMATION:
				return getGenTransformation() != null;
			case GenHenshinPackage.GEN_UNIT__INPUT_GEN_PARAMETERS:
				return inputGenParameters != null && !inputGenParameters.isEmpty();
			case GenHenshinPackage.GEN_UNIT__OUTPUT_GEN_PARAMETERS:
				return outputGenParameters != null && !outputGenParameters.isEmpty();
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
		result.append(" (method: ");
		result.append(method);
		result.append(", public: ");
		result.append(public_);
		result.append(')');
		return result.toString();
	}

} //GenUnitImpl
