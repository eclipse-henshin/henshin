/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace;

import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trace Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry#getState <em>State</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getTraceEntry()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface TraceEntry extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' containment reference.
	 * @see #setState(Graph)
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getTraceEntry_State()
	 * @model containment="true"
	 * @generated
	 */
	Graph getState();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry#getState <em>State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' containment reference.
	 * @see #getState()
	 * @generated
	 */
	void setState(Graph value);

} // TraceEntry
