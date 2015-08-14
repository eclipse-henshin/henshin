/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samrules.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.AttributedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samrules.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage
 * @generated
 */
public class SamrulesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SamrulesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamrulesSwitch() {
		if (modelPackage == null) {
			modelPackage = SamrulesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case SamrulesPackage.GRAPH_RULE: {
				GraphRule graphRule = (GraphRule)theEObject;
				T result = caseGraphRule(graphRule);
				if (result == null) result = caseAnnotatedElem(graphRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamrulesPackage.PRESERVED_NODE: {
				PreservedNode preservedNode = (PreservedNode)theEObject;
				T result = casePreservedNode(preservedNode);
				if (result == null) result = caseNode(preservedNode);
				if (result == null) result = caseAttributedElem(preservedNode);
				if (result == null) result = caseAnnotatedElem(preservedNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamrulesPackage.PRESERVED_EDGE: {
				PreservedEdge preservedEdge = (PreservedEdge)theEObject;
				T result = casePreservedEdge(preservedEdge);
				if (result == null) result = caseEdge(preservedEdge);
				if (result == null) result = caseAttributedElem(preservedEdge);
				if (result == null) result = caseAnnotatedElem(preservedEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamrulesPackage.RULE_GRAPH: {
				RuleGraph ruleGraph = (RuleGraph)theEObject;
				T result = caseRuleGraph(ruleGraph);
				if (result == null) result = caseGraph(ruleGraph);
				if (result == null) result = caseAnnotatedElem(ruleGraph);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamrulesPackage.GTS: {
				GTS gts = (GTS)theEObject;
				T result = caseGTS(gts);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamrulesPackage.CREATED_NODE: {
				CreatedNode createdNode = (CreatedNode)theEObject;
				T result = caseCreatedNode(createdNode);
				if (result == null) result = caseNode(createdNode);
				if (result == null) result = caseAttributedElem(createdNode);
				if (result == null) result = caseAnnotatedElem(createdNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamrulesPackage.DELETED_NODE: {
				DeletedNode deletedNode = (DeletedNode)theEObject;
				T result = caseDeletedNode(deletedNode);
				if (result == null) result = caseNode(deletedNode);
				if (result == null) result = caseAttributedElem(deletedNode);
				if (result == null) result = caseAnnotatedElem(deletedNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamrulesPackage.CREATED_EDGE: {
				CreatedEdge createdEdge = (CreatedEdge)theEObject;
				T result = caseCreatedEdge(createdEdge);
				if (result == null) result = caseEdge(createdEdge);
				if (result == null) result = caseAttributedElem(createdEdge);
				if (result == null) result = caseAnnotatedElem(createdEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamrulesPackage.DELETED_EDGE: {
				DeletedEdge deletedEdge = (DeletedEdge)theEObject;
				T result = caseDeletedEdge(deletedEdge);
				if (result == null) result = caseEdge(deletedEdge);
				if (result == null) result = caseAttributedElem(deletedEdge);
				if (result == null) result = caseAnnotatedElem(deletedEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Graph Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Graph Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGraphRule(GraphRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Preserved Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Preserved Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePreservedNode(PreservedNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Preserved Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Preserved Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePreservedEdge(PreservedEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Graph</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Graph</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleGraph(RuleGraph object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GTS</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GTS</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTS(GTS object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Created Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Created Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCreatedNode(CreatedNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deleted Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deleted Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeletedNode(DeletedNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Created Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Created Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCreatedEdge(CreatedEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deleted Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deleted Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeletedEdge(DeletedEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Annotated Elem</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotated Elem</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnnotatedElem(AnnotatedElem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attributed Elem</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attributed Elem</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributedElem(AttributedElem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNode(Node object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEdge(Edge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Graph</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Graph</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGraph(Graph object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //SamrulesSwitch
