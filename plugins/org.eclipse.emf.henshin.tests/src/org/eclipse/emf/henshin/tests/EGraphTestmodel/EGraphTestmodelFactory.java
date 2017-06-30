/**
 */
package org.eclipse.emf.henshin.tests.EGraphTestmodel;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.tests.EGraphTestmodel.EGraphTestmodelPackage
 * @generated
 */
public class EGraphTestmodelFactory extends EFactoryImpl {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EGraphTestmodelFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EGraphTestmodelFactory init() {
		try {
			EGraphTestmodelFactory theEGraphTestmodelFactory = (EGraphTestmodelFactory)EPackage.Registry.INSTANCE.getEFactory(EGraphTestmodelPackage.eNS_URI);
			if (theEGraphTestmodelFactory != null) {
				return theEGraphTestmodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EGraphTestmodelFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGraphTestmodelFactory() {
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
			case EGraphTestmodelPackage.RECURSIVE_NODE: return createRecursiveNode();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RecursiveNode createRecursiveNode() {
		RecursiveNode recursiveNode = new RecursiveNode();
		return recursiveNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGraphTestmodelPackage getEGraphTestmodelPackage() {
		return (EGraphTestmodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EGraphTestmodelPackage getPackage() {
		return EGraphTestmodelPackage.eINSTANCE;
	}

} //EGraphTestmodelFactory
