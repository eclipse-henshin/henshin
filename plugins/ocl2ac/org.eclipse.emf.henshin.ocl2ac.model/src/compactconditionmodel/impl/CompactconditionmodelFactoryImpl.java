/**
 */
package compactconditionmodel.impl;

import compactconditionmodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CompactconditionmodelFactoryImpl extends EFactoryImpl implements CompactconditionmodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CompactconditionmodelFactory init() {
		try {
			CompactconditionmodelFactory theCompactconditionmodelFactory = (CompactconditionmodelFactory)EPackage.Registry.INSTANCE.getEFactory(CompactconditionmodelPackage.eNS_URI);
			if (theCompactconditionmodelFactory != null) {
				return theCompactconditionmodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CompactconditionmodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompactconditionmodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL: return createCompactConditionModel();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompactConditionModel createCompactConditionModel() {
		CompactConditionModelImpl compactConditionModel = new CompactConditionModelImpl();
		return compactConditionModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompactconditionmodelPackage getCompactconditionmodelPackage() {
		return (CompactconditionmodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CompactconditionmodelPackage getPackage() {
		return CompactconditionmodelPackage.eINSTANCE;
	}

} //CompactconditionmodelFactoryImpl
