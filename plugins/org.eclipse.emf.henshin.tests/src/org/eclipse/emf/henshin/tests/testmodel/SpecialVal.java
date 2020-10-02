/**
 */
package org.eclipse.emf.henshin.tests.testmodel;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Special Val</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.tests.testmodel.SpecialVal#getDerivedvl <em>Derivedvl</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.tests.testmodel.SpecialVal#getUnsetablevl <em>Unsetablevl</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.tests.testmodel.SpecialVal#getUnchangeablevl <em>Unchangeablevl</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.tests.testmodel.SpecialVal#getUnsetableunchangeablevl <em>Unsetableunchangeablevl</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getSpecialVal()
 * @model kind="class"
 * @generated
 */
public class SpecialVal extends Val {
	/**
	 * The default value of the '{@link #getDerivedvl() <em>Derivedvl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDerivedvl()
	 * @generated
	 * @ordered
	 */
	protected static final int DERIVEDVL_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDerivedvl() <em>Derivedvl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDerivedvl()
	 * @generated
	 * @ordered
	 */
	protected int derivedvl = DERIVEDVL_EDEFAULT;

	/**
	 * The default value of the '{@link #getUnsetablevl() <em>Unsetablevl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsetablevl()
	 * @generated
	 * @ordered
	 */
	protected static final int UNSETABLEVL_EDEFAULT = 42;

	/**
	 * The cached value of the '{@link #getUnsetablevl() <em>Unsetablevl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsetablevl()
	 * @generated
	 * @ordered
	 */
	protected int unsetablevl = UNSETABLEVL_EDEFAULT;

	/**
	 * This is true if the Unsetablevl attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean unsetablevlESet;

	/**
	 * The default value of the '{@link #getUnchangeablevl() <em>Unchangeablevl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnchangeablevl()
	 * @generated
	 * @ordered
	 */
	protected static final int UNCHANGEABLEVL_EDEFAULT = 42;

	/**
	 * The cached value of the '{@link #getUnchangeablevl() <em>Unchangeablevl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnchangeablevl()
	 * @generated
	 * @ordered
	 */
	protected int unchangeablevl = UNCHANGEABLEVL_EDEFAULT;

	/**
	 * The default value of the '{@link #getUnsetableunchangeablevl() <em>Unsetableunchangeablevl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsetableunchangeablevl()
	 * @generated
	 * @ordered
	 */
	protected static final int UNSETABLEUNCHANGEABLEVL_EDEFAULT = 42;

	/**
	 * The cached value of the '{@link #getUnsetableunchangeablevl() <em>Unsetableunchangeablevl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsetableunchangeablevl()
	 * @generated
	 * @ordered
	 */
	protected int unsetableunchangeablevl = UNSETABLEUNCHANGEABLEVL_EDEFAULT;

	/**
	 * This is true if the Unsetableunchangeablevl attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean unsetableunchangeablevlESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SpecialVal() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestmodelPackage.Literals.SPECIAL_VAL;
	}

	/**
	 * Returns the value of the '<em><b>Derivedvl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Derivedvl</em>' attribute.
	 * @see #setDerivedvl(int)
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getSpecialVal_Derivedvl()
	 * @model transient="true" derived="true"
	 * @generated
	 */
	public int getDerivedvl() {
		return derivedvl;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.tests.testmodel.SpecialVal#getDerivedvl <em>Derivedvl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param newDerivedvl the new value of the '<em>Derivedvl</em>' attribute.
	 * @see #getDerivedvl()
	 * @generated
	 */
	public void setDerivedvl(int newDerivedvl) {
		int oldDerivedvl = derivedvl;
		derivedvl = newDerivedvl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.SPECIAL_VAL__DERIVEDVL, oldDerivedvl, derivedvl));
	}

	/**
	 * Returns the value of the '<em><b>Unchangeablevl</b></em>' attribute.
	 * The default value is <code>"42"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unchangeablevl</em>' attribute.
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getSpecialVal_Unchangeablevl()
	 * @model default="42" changeable="false"
	 * @generated
	 */
	public int getUnchangeablevl() {
		return unchangeablevl;
	}

	/**
	 * Returns the value of the '<em><b>Unsetableunchangeablevl</b></em>' attribute.
	 * The default value is <code>"42"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unsetableunchangeablevl</em>' attribute.
	 * @see #isSetUnsetableunchangeablevl()
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getSpecialVal_Unsetableunchangeablevl()
	 * @model default="42" unsettable="true" changeable="false"
	 * @generated
	 */
	public int getUnsetableunchangeablevl() {
		return unsetableunchangeablevl;
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.henshin.tests.testmodel.SpecialVal#getUnsetableunchangeablevl <em>Unsetableunchangeablevl</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unsetableunchangeablevl</em>' attribute is set.
	 * @see #getUnsetableunchangeablevl()
	 * @generated
	 */
	public boolean isSetUnsetableunchangeablevl() {
		return unsetableunchangeablevlESet;
	}

	/**
	 * Returns the value of the '<em><b>Unsetablevl</b></em>' attribute.
	 * The default value is <code>"42"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unsetablevl</em>' attribute.
	 * @see #isSetUnsetablevl()
	 * @see #unsetUnsetablevl()
	 * @see #setUnsetablevl(int)
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getSpecialVal_Unsetablevl()
	 * @model default="42" unsettable="true"
	 * @generated
	 */
	public int getUnsetablevl() {
		return unsetablevl;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.tests.testmodel.SpecialVal#getUnsetablevl <em>Unsetablevl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param newUnsetablevl the new value of the '<em>Unsetablevl</em>' attribute.
	 * @see #isSetUnsetablevl()
	 * @see #unsetUnsetablevl()
	 * @see #getUnsetablevl()
	 * @generated
	 */
	public void setUnsetablevl(int newUnsetablevl) {
		int oldUnsetablevl = unsetablevl;
		unsetablevl = newUnsetablevl;
		boolean oldUnsetablevlESet = unsetablevlESet;
		unsetablevlESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.SPECIAL_VAL__UNSETABLEVL, oldUnsetablevl, unsetablevl, !oldUnsetablevlESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.henshin.tests.testmodel.SpecialVal#getUnsetablevl <em>Unsetablevl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUnsetablevl()
	 * @see #getUnsetablevl()
	 * @see #setUnsetablevl(int)
	 * @generated
	 */
	public void unsetUnsetablevl() {
		int oldUnsetablevl = unsetablevl;
		boolean oldUnsetablevlESet = unsetablevlESet;
		unsetablevl = UNSETABLEVL_EDEFAULT;
		unsetablevlESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TestmodelPackage.SPECIAL_VAL__UNSETABLEVL, oldUnsetablevl, UNSETABLEVL_EDEFAULT, oldUnsetablevlESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.henshin.tests.testmodel.SpecialVal#getUnsetablevl <em>Unsetablevl</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unsetablevl</em>' attribute is set.
	 * @see #unsetUnsetablevl()
	 * @see #getUnsetablevl()
	 * @see #setUnsetablevl(int)
	 * @generated
	 */
	public boolean isSetUnsetablevl() {
		return unsetablevlESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TestmodelPackage.SPECIAL_VAL__DERIVEDVL:
				return getDerivedvl();
			case TestmodelPackage.SPECIAL_VAL__UNSETABLEVL:
				return getUnsetablevl();
			case TestmodelPackage.SPECIAL_VAL__UNCHANGEABLEVL:
				return getUnchangeablevl();
			case TestmodelPackage.SPECIAL_VAL__UNSETABLEUNCHANGEABLEVL:
				return getUnsetableunchangeablevl();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TestmodelPackage.SPECIAL_VAL__DERIVEDVL:
				setDerivedvl((Integer)newValue);
				return;
			case TestmodelPackage.SPECIAL_VAL__UNSETABLEVL:
				setUnsetablevl((Integer)newValue);
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
			case TestmodelPackage.SPECIAL_VAL__DERIVEDVL:
				setDerivedvl(DERIVEDVL_EDEFAULT);
				return;
			case TestmodelPackage.SPECIAL_VAL__UNSETABLEVL:
				unsetUnsetablevl();
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
			case TestmodelPackage.SPECIAL_VAL__DERIVEDVL:
				return derivedvl != DERIVEDVL_EDEFAULT;
			case TestmodelPackage.SPECIAL_VAL__UNSETABLEVL:
				return isSetUnsetablevl();
			case TestmodelPackage.SPECIAL_VAL__UNCHANGEABLEVL:
				return unchangeablevl != UNCHANGEABLEVL_EDEFAULT;
			case TestmodelPackage.SPECIAL_VAL__UNSETABLEUNCHANGEABLEVL:
				return isSetUnsetableunchangeablevl();
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (derivedvl: ");
		result.append(derivedvl);
		result.append(", unsetablevl: ");
		if (unsetablevlESet) result.append(unsetablevl); else result.append("<unset>");
		result.append(", unchangeablevl: ");
		result.append(unchangeablevl);
		result.append(", unsetableunchangeablevl: ");
		if (unsetableunchangeablevlESet) result.append(unsetableunchangeablevl); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} // SpecialVal
