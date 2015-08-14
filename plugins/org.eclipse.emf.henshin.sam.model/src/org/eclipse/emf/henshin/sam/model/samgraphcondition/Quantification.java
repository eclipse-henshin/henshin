/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition;

import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Quantification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A <code>Quantification</code> is the probably most important <code>GraphCondition</code> to be used. <br/>
 * Either it looks for nodes and edges in the <code>context</code> to exist or it looks for all occurrences of the nodes and edges in the <code>context</code>.
 * For this one occurrence or for all occurences, respectively, the <code>nesting</code> <code>GraphCondition</code> needs to hold.  
 * An <code>attributeCondition</code> may constrain the attribute values of the <code>context</code> (TO BE CHECKED).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getQuantor <em>Quantor</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getNesting <em>Nesting</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getContext <em>Context</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getAttributeCondition <em>Attribute Condition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getQuantification()
 * @model
 * @generated
 */
public interface Quantification extends GraphCondition {
	/**
	 * Returns the value of the '<em><b>Quantor</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Quantor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Quantor</em>' attribute.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantor
	 * @see #setQuantor(Quantor)
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getQuantification_Quantor()
	 * @model
	 * @generated
	 */
	Quantor getQuantor();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getQuantor <em>Quantor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Quantor</em>' attribute.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantor
	 * @see #getQuantor()
	 * @generated
	 */
	void setQuantor(Quantor value);

	/**
	 * Returns the value of the '<em><b>Nesting</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nesting</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nesting</em>' containment reference.
	 * @see #setNesting(GraphCondition)
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getQuantification_Nesting()
	 * @model containment="true" required="true"
	 * @generated
	 */
	GraphCondition getNesting();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getNesting <em>Nesting</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nesting</em>' containment reference.
	 * @see #getNesting()
	 * @generated
	 */
	void setNesting(GraphCondition value);

	/**
	 * Returns the value of the '<em><b>Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' containment reference.
	 * @see #setContext(Graph)
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getQuantification_Context()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Graph getContext();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getContext <em>Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context</em>' containment reference.
	 * @see #getContext()
	 * @generated
	 */
	void setContext(Graph value);

	/**
	 * Returns the value of the '<em><b>Attribute Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Condition</em>' containment reference.
	 * @see #setAttributeCondition(OperationUse)
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getQuantification_AttributeCondition()
	 * @model containment="true"
	 * @generated
	 */
	OperationUse getAttributeCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification#getAttributeCondition <em>Attribute Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Condition</em>' containment reference.
	 * @see #getAttributeCondition()
	 * @generated
	 */
	void setAttributeCondition(OperationUse value);

} // Quantification
