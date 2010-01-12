/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sierpinski.model.Sierpinski;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see sierpinski.model.Sierpinski.SierpinskiPackage
 * @generated
 */
public interface SierpinskiFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SierpinskiFactory eINSTANCE = sierpinski.model.Sierpinski.impl.SierpinskiFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Vertex</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Vertex</em>'.
	 * @generated
	 */
	Vertex createVertex();

	/**
	 * Returns a new object of class '<em>Vertex Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Vertex Container</em>'.
	 * @generated
	 */
	VertexContainer createVertexContainer();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SierpinskiPackage getSierpinskiPackage();

} //SierpinskiFactory
