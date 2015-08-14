/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.Trace#getEntries <em>Entries</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getTrace()
 * @model
 * @generated
 */
public interface Trace extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getTrace_Entries()
	 * @model containment="true"
	 * @generated
	 */
	EList<TraceEntry> getEntries();

} // Trace
