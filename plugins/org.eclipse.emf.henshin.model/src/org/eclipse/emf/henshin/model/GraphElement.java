/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph Element</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getGraphElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface GraphElement extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * Returns the graph this graph element is contained in.
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 * @generated
	 */
	Graph getGraph();

} // GraphElement
