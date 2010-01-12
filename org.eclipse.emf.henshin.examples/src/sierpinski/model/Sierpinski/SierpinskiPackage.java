/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sierpinski.model.Sierpinski;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see sierpinski.model.Sierpinski.SierpinskiFactory
 * @model kind="package"
 * @generated
 */
public interface SierpinskiPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Sierpinski";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://Sierpinski";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "Sierpinski";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SierpinskiPackage eINSTANCE = sierpinski.model.Sierpinski.impl.SierpinskiPackageImpl.init();

	/**
	 * The meta object id for the '{@link sierpinski.model.Sierpinski.impl.VertexImpl <em>Vertex</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sierpinski.model.Sierpinski.impl.VertexImpl
	 * @see sierpinski.model.Sierpinski.impl.SierpinskiPackageImpl#getVertex()
	 * @generated
	 */
	int VERTEX = 0;

	/**
	 * The feature id for the '<em><b>Left</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__LEFT = 0;

	/**
	 * The feature id for the '<em><b>Conn</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__CONN = 1;

	/**
	 * The feature id for the '<em><b>Right</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__RIGHT = 2;

	/**
	 * The number of structural features of the '<em>Vertex</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link sierpinski.model.Sierpinski.impl.VertexContainerImpl <em>Vertex Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sierpinski.model.Sierpinski.impl.VertexContainerImpl
	 * @see sierpinski.model.Sierpinski.impl.SierpinskiPackageImpl#getVertexContainer()
	 * @generated
	 */
	int VERTEX_CONTAINER = 1;

	/**
	 * The feature id for the '<em><b>Vertices</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX_CONTAINER__VERTICES = 0;

	/**
	 * The number of structural features of the '<em>Vertex Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX_CONTAINER_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link sierpinski.model.Sierpinski.Vertex <em>Vertex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vertex</em>'.
	 * @see sierpinski.model.Sierpinski.Vertex
	 * @generated
	 */
	EClass getVertex();

	/**
	 * Returns the meta object for the reference '{@link sierpinski.model.Sierpinski.Vertex#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Left</em>'.
	 * @see sierpinski.model.Sierpinski.Vertex#getLeft()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Left();

	/**
	 * Returns the meta object for the reference '{@link sierpinski.model.Sierpinski.Vertex#getConn <em>Conn</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Conn</em>'.
	 * @see sierpinski.model.Sierpinski.Vertex#getConn()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Conn();

	/**
	 * Returns the meta object for the reference '{@link sierpinski.model.Sierpinski.Vertex#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Right</em>'.
	 * @see sierpinski.model.Sierpinski.Vertex#getRight()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Right();

	/**
	 * Returns the meta object for class '{@link sierpinski.model.Sierpinski.VertexContainer <em>Vertex Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vertex Container</em>'.
	 * @see sierpinski.model.Sierpinski.VertexContainer
	 * @generated
	 */
	EClass getVertexContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link sierpinski.model.Sierpinski.VertexContainer#getVertices <em>Vertices</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vertices</em>'.
	 * @see sierpinski.model.Sierpinski.VertexContainer#getVertices()
	 * @see #getVertexContainer()
	 * @generated
	 */
	EReference getVertexContainer_Vertices();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SierpinskiFactory getSierpinskiFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link sierpinski.model.Sierpinski.impl.VertexImpl <em>Vertex</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sierpinski.model.Sierpinski.impl.VertexImpl
		 * @see sierpinski.model.Sierpinski.impl.SierpinskiPackageImpl#getVertex()
		 * @generated
		 */
		EClass VERTEX = eINSTANCE.getVertex();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX__LEFT = eINSTANCE.getVertex_Left();

		/**
		 * The meta object literal for the '<em><b>Conn</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX__CONN = eINSTANCE.getVertex_Conn();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX__RIGHT = eINSTANCE.getVertex_Right();

		/**
		 * The meta object literal for the '{@link sierpinski.model.Sierpinski.impl.VertexContainerImpl <em>Vertex Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sierpinski.model.Sierpinski.impl.VertexContainerImpl
		 * @see sierpinski.model.Sierpinski.impl.SierpinskiPackageImpl#getVertexContainer()
		 * @generated
		 */
		EClass VERTEX_CONTAINER = eINSTANCE.getVertexContainer();

		/**
		 * The meta object literal for the '<em><b>Vertices</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX_CONTAINER__VERTICES = eINSTANCE.getVertexContainer_Vertices();

	}

} //SierpinskiPackage
