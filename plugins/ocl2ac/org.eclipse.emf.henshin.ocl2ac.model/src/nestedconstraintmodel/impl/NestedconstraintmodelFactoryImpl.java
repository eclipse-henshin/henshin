/**
 */
package nestedconstraintmodel.impl;

import nestedconstraintmodel.*;

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
public class NestedconstraintmodelFactoryImpl extends EFactoryImpl implements NestedconstraintmodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NestedconstraintmodelFactory init() {
		try {
			NestedconstraintmodelFactory theNestedconstraintmodelFactory = (NestedconstraintmodelFactory)EPackage.Registry.INSTANCE.getEFactory(NestedconstraintmodelPackage.eNS_URI);
			if (theNestedconstraintmodelFactory != null) {
				return theNestedconstraintmodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NestedconstraintmodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedconstraintmodelFactoryImpl() {
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
			case NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL: return createNestedConstraintModel();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedConstraintModel createNestedConstraintModel() {
		NestedConstraintModelImpl nestedConstraintModel = new NestedConstraintModelImpl();
		return nestedConstraintModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedconstraintmodelPackage getNestedconstraintmodelPackage() {
		return (NestedconstraintmodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NestedconstraintmodelPackage getPackage() {
		return NestedconstraintmodelPackage.eINSTANCE;
	}

} //NestedconstraintmodelFactoryImpl
