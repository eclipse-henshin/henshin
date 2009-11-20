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
	 * Set the location attribute of this state.
	 * @param location The new location.
	 * @see #getLocation()
	 * @generated NOT
	 */
	void setLocation(int... location);
	
	/* ---------------------------------------------------------------- *
	 * GENERATED CODE.                                                  *
	 * Do not edit below this line. If you need to edit, move it above  *
	 * this line and change the '@generated'-tag to '@generated NOT'.   *
	 * ---------------------------------------------------------------- */
	
	/**
	 * Get the name of this state..
	 * @return the name of this state.
	 * @see #setName(String)
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the name of this state.
	 * @param name The new name.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Get the list of incoming transitions of this state. The list contents 
	 * are of type {@link org.eclipse.emf.henshin.statespace.Transition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.statespace.Transition#getTarget <em>Target</em>}'.
	 * @return list of incoming transitions.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<Transition> getIncoming();

	/**
	 * Get the list of outgoing transitions of this state. The list contents 
	 * are of type {@link org.eclipse.emf.henshin.statespace.Transition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.statespace.Transition#getSource <em>Source</em>}'.
	 * @return the outgoing transitions.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getSource
	 * @model opposite="source" containment="true"
	 * @generated
	 */
	EList<Transition> getOutgoing();

	/**
	 * Get the location of this state.
	 * @return the location of this state.
	 * @see #setLocation(int[])
	 * @model
	 * @generated
	 */
	int[] getLocation();

	/**
	 * Get the associated model of this state.
	 * @return the associated model.
	 * @see #setModel(Resource)
	 * @model transient="true"
	 * @generated
	 */
	Resource getModel();

	/**
	 * Set the associated model of this state.
	 * @param model the associated model.
	 * @see #getModel()
	 * @generated
	 */
	void setModel(Resource value);

	/**
	 * Get the state space that contains this state. This is bidirectional reference 
	 * and its opposite is '{@link org.eclipse.emf.henshin.statespace.StateSpace#getStates <em>States</em>}'.
	 * @return the state space that contains this state.
	 * @see #setStateSpace(StateSpace)
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getStates
	 * @model opposite="states" transient="false"
	 * @generated
	 */
	StateSpace getStateSpace();

	/**
	 * Set the state space that contains this state.
	 * @param stateSpace the container state space.
	 * @see #getStateSpace()
	 * @generated
	 */
	void setStateSpace(StateSpace value);

	/**
	 * Check whether this state is fully explored already.
	 * @return <code>true</code> if it is fully explored.
	 * @see #setExplored(boolean)
	 * @model volatile="true"
	 * @generated
	 */
	boolean isExplored();

	/**
	 * Set the explored flag of this state.
	 * @param explored the new value of the explored-flag.
	 * @see #isExplored()
	 * @generated
	 */
	void setExplored(boolean value);

	/**
	 * Check whether this state is an initial one. A state is initial
	 * if {@link #getModel()} returns a resource with a non-<code>null</code> URI.
	 * @model kind="operation"
	 * @generated
	 */
	boolean isInitial();
		
}