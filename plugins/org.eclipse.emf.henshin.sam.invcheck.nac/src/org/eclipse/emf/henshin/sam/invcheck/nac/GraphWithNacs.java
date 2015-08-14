/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.invcheck.nac;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph With Nacs</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs#getNacs <em>Nacs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage#getGraphWithNacs()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface GraphWithNacs extends Graph {
	/**
	 * Returns the value of the '<em><b>Nacs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nacs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nacs</em>' reference list.
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage#getGraphWithNacs_Nacs()
	 * @model
	 * @generated
	 */
	EList<NegativeApplicationCondition> getNacs();

} // GraphWithNacs
