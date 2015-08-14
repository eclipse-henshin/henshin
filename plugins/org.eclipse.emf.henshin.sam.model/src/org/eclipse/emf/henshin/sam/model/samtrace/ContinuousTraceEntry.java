/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Continuous Trace Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.ContinuousTraceEntry#getDuration <em>Duration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getContinuousTraceEntry()
 * @model
 * @generated
 */
public interface ContinuousTraceEntry extends TraceEntry {
	/**
	 * Returns the value of the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Duration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Duration</em>' attribute.
	 * @see #setDuration(double)
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getContinuousTraceEntry_Duration()
	 * @model required="true"
	 * @generated
	 */
	double getDuration();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtrace.ContinuousTraceEntry#getDuration <em>Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Duration</em>' attribute.
	 * @see #getDuration()
	 * @generated
	 */
	void setDuration(double value);

} // ContinuousTraceEntry
