package org.eclipse.emf.henshin.statespace;


/**
 * Light-weight transition model.
 * @generated
 */
public interface Transition extends Storage {

	/**
	 * Returns the value of the '<em><b>Source</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.statespace.State#getOutgoing <em>Outgoing</em>}'.
	 * @return the value of the '<em>Source</em>' container reference.
	 * @see #setSource(State)
	 * @see org.eclipse.emf.henshin.statespace.State#getOutgoing
	 * @model opposite="outgoing" transient="false"
	 * @generated
	 */
	State getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.Transition#getSource <em>Source</em>}' container reference.
	 * @param value the new value of the '<em>Source</em>' container reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(State value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.statespace.State#getIncoming <em>Incoming</em>}'.
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(State)
	 * @see org.eclipse.emf.henshin.statespace.State#getIncoming
	 * @model opposite="incoming"
	 * @generated
	 */
	State getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.Transition#getTarget <em>Target</em>}' reference.
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(State value);

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' attribute.
	 * @return the value of the '<em>Rule</em>' attribute.
	 * @see #setRule(String)
	 * @model
	 * @generated
	 */
	String getRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.Transition#getRule <em>Rule</em>}' attribute.
	 * @param value the new value of the '<em>Rule</em>' attribute.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(String value);

	/**
	 * Returns the value of the '<em><b>Match</b></em>' attribute.
	 * @return the value of the '<em>Match</em>' attribute.
	 * @see #setMatch(int)
	 * @model
	 * @generated
	 */
	int getMatch();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.Transition#getMatch <em>Match</em>}' attribute.
	 * @param value the new value of the '<em>Match</em>' attribute.
	 * @see #getMatch()
	 * @generated
	 */
	void setMatch(int value);
		
}