/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace;

import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Discrete Trace Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry#getCurrentRule <em>Current Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry#getCurrentMatch <em>Current Match</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getDiscreteTraceEntry()
 * @model
 * @generated
 */
public interface DiscreteTraceEntry extends TraceEntry {
	/**
	 * Returns the value of the '<em><b>Current Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Rule</em>' reference.
	 * @see #setCurrentRule(GraphRule)
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getDiscreteTraceEntry_CurrentRule()
	 * @model
	 * @generated
	 */
	GraphRule getCurrentRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry#getCurrentRule <em>Current Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Rule</em>' reference.
	 * @see #getCurrentRule()
	 * @generated
	 */
	void setCurrentRule(GraphRule value);

	/**
	 * Returns the value of the '<em><b>Current Match</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Match</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Match</em>' containment reference.
	 * @see #setCurrentMatch(Match)
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getDiscreteTraceEntry_CurrentMatch()
	 * @model containment="true"
	 * @generated
	 */
	Match getCurrentMatch();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry#getCurrentMatch <em>Current Match</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Match</em>' containment reference.
	 * @see #getCurrentMatch()
	 * @generated
	 */
	void setCurrentMatch(Match value);

} // DiscreteTraceEntry
