/**
 * <copyright>
 * </copyright>
 *
 * $Id: Graph.java,v 1.1 2009/10/28 10:38:07 enrico Exp $
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Graph#getNodes <em>Nodes</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Graph#getEdges <em>Edges</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Graph#getFormula <em>Formula</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getGraph()
 * @model
 * @generated
 */
public interface Graph extends NamedElement {
        /**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Node}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Node#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Nodes</em>' containment reference list isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getGraph_Nodes()
	 * @see org.eclipse.emf.henshin.model.Node#getGraph
	 * @model opposite="graph" containment="true"
	 * @generated
	 */
        EList<Node> getNodes();

        /**
	 * Returns the value of the '<em><b>Edges</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Edge}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Edge#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Edges</em>' containment reference list isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Edges</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getGraph_Edges()
	 * @see org.eclipse.emf.henshin.model.Edge#getGraph
	 * @model opposite="graph" containment="true"
	 * @generated
	 */
        EList<Edge> getEdges();

        /**
	 * Returns the value of the '<em><b>Formula</b></em>' containment reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Formula</em>' containment reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Formula</em>' containment reference.
	 * @see #setFormula(Formula)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getGraph_Formula()
	 * @model containment="true"
	 * @generated
	 */
        Formula getFormula();

        /**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Graph#getFormula <em>Formula</em>}' containment reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Formula</em>' containment reference.
	 * @see #getFormula()
	 * @generated
	 */
        void setFormula(Formula value);

} // Graph
