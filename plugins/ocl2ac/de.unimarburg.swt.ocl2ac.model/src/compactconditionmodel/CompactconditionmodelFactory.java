/**
 */
package compactconditionmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see compactconditionmodel.CompactconditionmodelPackage
 * @generated
 */
public interface CompactconditionmodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CompactconditionmodelFactory eINSTANCE = compactconditionmodel.impl.CompactconditionmodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Compact Condition Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compact Condition Model</em>'.
	 * @generated
	 */
	CompactConditionModel createCompactConditionModel();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CompactconditionmodelPackage getCompactconditionmodelPackage();

} //CompactconditionmodelFactory
