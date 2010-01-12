/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sierpinski.model.Sierpinski.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import sierpinski.model.Sierpinski.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SierpinskiFactoryImpl extends EFactoryImpl implements SierpinskiFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SierpinskiFactory init() {
		try {
			SierpinskiFactory theSierpinskiFactory = (SierpinskiFactory)EPackage.Registry.INSTANCE.getEFactory("http://Sierpinski"); 
			if (theSierpinskiFactory != null) {
				return theSierpinskiFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SierpinskiFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SierpinskiFactoryImpl() {
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
			case SierpinskiPackage.VERTEX: return createVertex();
			case SierpinskiPackage.VERTEX_CONTAINER: return createVertexContainer();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vertex createVertex() {
		VertexImpl vertex = new VertexImpl();
		return vertex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VertexContainer createVertexContainer() {
		VertexContainerImpl vertexContainer = new VertexContainerImpl();
		return vertexContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SierpinskiPackage getSierpinskiPackage() {
		return (SierpinskiPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SierpinskiPackage getPackage() {
		return SierpinskiPackage.eINSTANCE;
	}

} //SierpinskiFactoryImpl
