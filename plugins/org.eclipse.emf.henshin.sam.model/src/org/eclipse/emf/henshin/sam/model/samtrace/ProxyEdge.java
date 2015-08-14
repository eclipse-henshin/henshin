/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace;

import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proxy Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.ProxyEdge#getReferenceEdge <em>Reference Edge</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getProxyEdge()
 * @model
 * @generated
 */
public interface ProxyEdge extends Edge, AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Reference Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference Edge</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference Edge</em>' reference.
	 * @see #setReferenceEdge(Edge)
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getProxyEdge_ReferenceEdge()
	 * @model required="true"
	 * @generated
	 */
	Edge getReferenceEdge();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtrace.ProxyEdge#getReferenceEdge <em>Reference Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference Edge</em>' reference.
	 * @see #getReferenceEdge()
	 * @generated
	 */
	void setReferenceEdge(Edge value);

} // ProxyEdge
