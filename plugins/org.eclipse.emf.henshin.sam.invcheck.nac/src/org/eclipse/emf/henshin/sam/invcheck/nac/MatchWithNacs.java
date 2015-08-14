/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.invcheck.nac;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Match With Nacs</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.invcheck.nac.MatchWithNacs#getNacMatching <em>Nac Matching</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage#getMatchWithNacs()
 * @model
 * @generated
 */
public interface MatchWithNacs extends Match {
	/**
	 * Returns the value of the '<em><b>Nac Matching</b></em>' map.
	 * The key is of type {@link T},
	 * and the value is of type {@link S},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nac Matching</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nac Matching</em>' map.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage#getMatchWithNacs_NacMatching()
	 * @model mapType="samgraphuniverse.samtrace.MatchEntry<T, S>"
	 * @generated
	 */
	EMap<NegativeApplicationCondition, NegativeApplicationCondition> getNacMatching();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='this.getNodeMatching().clear();\nthis.getEdgeMatching().clear();\nthis.getNacMatching().clear();'"
	 * @generated
	 */
	void clear();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='MatchWithNacs result = NacFactory.eINSTANCE.createMatchWithNacs();\nresult.getNodeMatching().putAll(this.getNodeMatching());\nresult.getEdgeMatching().putAll(this.getEdgeMatching());\nresult.getNacMatching().putAll(this.getNacMatching());\nreturn result;'"
	 * @generated
	 */
	MatchWithNacs copy();

} // MatchWithNacs
