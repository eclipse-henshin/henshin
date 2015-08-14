/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samrules;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GTS</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.GTS#getRules <em>Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.GTS#getTypes <em>Types</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getGTS()
 * @model
 * @generated
 */
public interface GTS extends EObject {
	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getGTS_Rules()
	 * @model containment="true"
	 * @generated
	 */
	EList<GraphRule> getRules();

	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getGTS_Types()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeGraph> getTypes();

} // GTS
