package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * Light-weight state space model.
 * @generated
 */
public interface StateSpace extends EObject {

	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.statespace.State}.
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @model containment="true"
	 * @generated
	 */
	EList<State> getStates();
	
}
