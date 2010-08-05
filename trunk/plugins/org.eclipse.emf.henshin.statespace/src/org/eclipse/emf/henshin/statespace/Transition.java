/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Rule;


/**
 * Light-weight transition model.
 * @generated
 */
public interface Transition extends EObject {

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
	Rule getRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.Transition#getRule <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Rule value);

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