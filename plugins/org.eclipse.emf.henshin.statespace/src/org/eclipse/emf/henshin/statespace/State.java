package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Light-weight state model.
 * @generated
 */
public interface State extends Storage {
	
	/**
	 * Get the name of this state.
	 * @return the name of this state.
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Set the name of this state.
	 * @param name The new name.
	 * @generated
	 */
	void setName(String value);

	/**
	 * Get the list of incoming transitions of this state. 
	 * @return list of incoming transitions.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<Transition> getIncoming();

	/**
	 * Get the list of outgoing transitions of this state. 
	 * @return the outgoing transitions.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getSource
	 * @model opposite="source" containment="true"
	 * @generated
	 */
	EList<Transition> getOutgoing();

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
	 * Get the state space that contains this state. 
	 * @return the state space that contains this state.
	 * @see #setStateSpace(StateSpace)
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
	 * Get the location of this state. This always returns 
	 * an integer array of size 3 (X,Y and Z coordinate).
	 * @return the location of this state.
	 * @see #setLocation(int[])
	 * @model dataType="org.eclipse.emf.henshin.statespace.IntegerArray" transient="true" volatile="true"
	 * @generated
	 */
	int[] getLocation();

	/**
	 * Sets the location of this state. The argument must have length 3.
	 * @param the new location of the state.
	 * @see #getLocation()
	 * @generated NOT
	 */
	void setLocation(int... location);

	/**
	 * Check whether this state is open.
	 * @return <code>true</code> if it is open.
	 * @see #setOpen(boolean)
	 * @model default="false"
	 * @generated
	 */
	boolean isOpen();

	/**
	 * Set the open flag.
	 * @param value the new value of the open flag.
	 * @see #isOpen()
	 * @generated
	 */
	void setOpen(boolean value);

	/**
	 * Get the hash code of this state.
	 * @return the hash code.
	 * @see #setHashCode(int)
	 * @model default="0" transient="true" volatile="true"
	 * @generated
	 */
	int getHashCode();

	/**
	 * Set the hash code of this state.
	 * @param hashCode the hash code.
	 * @see #getHashCode()
	 * @generated
	 */
	void setHashCode(int value);

	/**
	 * Check whether this state is an initial one. A state is initial
	 * if {@link #getModel()} returns a resource with a non-<code>null</code> URI.
	 * @model kind="operation"
	 * @generated
	 */
	boolean isInitial();

	/**
	 * Check if this state is terminal. A state is terminal if it is
	 * not open and it has no outgoing transitions.
	 * @model kind="operation"
	 * @generated
	 */
	boolean isTerminal();
		
}