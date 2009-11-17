package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Light-weight state model.
 * @generated
 */
public interface State extends EObject {

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.State#getName <em>Name</em>}' attribute.
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.statespace.Transition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.statespace.Transition#getTarget <em>Target</em>}'.
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<Transition> getIncoming();

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.statespace.Transition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.statespace.Transition#getSource <em>Source</em>}'.
	 * @return the value of the '<em>Outgoing</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getSource
	 * @model opposite="source" containment="true"
	 * @generated
	 */
	EList<Transition> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(long)
	 * @model
	 * @generated
	 */
	int[] getLocation();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.State#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated NOT
	 */
	void setLocation(int... location);

	/**
	 * Returns the value of the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model</em>' attribute.
	 * @see #setModel(Resource)
	 * @model transient="true"
	 * @generated
	 */
	Resource getModel();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.State#getModel <em>Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model</em>' attribute.
	 * @see #getModel()
	 * @generated
	 */
	void setModel(Resource value);

	/**
	 * <!-- begin-user-doc -->
	 * Get the X-coordinate of this state.
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	int getX();

	/**
	 * <!-- begin-user-doc -->
	 * Get the Y-coordinate of this state.
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	int getY();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isInitial();
		
}