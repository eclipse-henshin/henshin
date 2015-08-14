/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Match</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.Match#getNodeMatching <em>Node Matching</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.Match#getEdgeMatching <em>Edge Matching</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.Match#getSize <em>Size</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getMatch()
 * @model
 * @generated
 */
public interface Match extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Node Matching</b></em>' map.
	 * The key is of type {@link T},
	 * and the value is of type {@link S},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node Matching</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Matching</em>' map.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getMatch_NodeMatching()
	 * @model mapType="org.eclipse.emf.henshin.sam.model.samtrace.MatchEntry<T, S>"
	 * @generated
	 */
	EMap<Node, Node> getNodeMatching();

	/**
	 * Returns the value of the '<em><b>Edge Matching</b></em>' map.
	 * The key is of type {@link T},
	 * and the value is of type {@link S},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edge Matching</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edge Matching</em>' map.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getMatch_EdgeMatching()
	 * @model mapType="org.eclipse.emf.henshin.sam.model.samtrace.MatchEntry<T, S>"
	 * @generated
	 */
	EMap<Edge, Edge> getEdgeMatching();

	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#getMatch_Size()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getSize();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='this.getEdgeMatching().clear();\nthis.getNodeMatching().clear();'"
	 * @generated
	 */
	void clear();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='Match result = SamtraceFactory.eINSTANCE.createMatch();\nresult.getNodeMatching().putAll(this.getNodeMatching());\nresult.getEdgeMatching().putAll(this.getEdgeMatching());\nreturn result;'"
	 * @generated
	 */
	Match copy();

} // Match
