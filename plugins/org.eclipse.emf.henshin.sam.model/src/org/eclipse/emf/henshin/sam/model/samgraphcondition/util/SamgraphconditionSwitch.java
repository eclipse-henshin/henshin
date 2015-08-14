/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.AttributedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.*;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition;

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
 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage
 * @generated
 */
public class SamgraphconditionSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SamgraphconditionPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamgraphconditionSwitch() {
		if (modelPackage == null) {
			modelPackage = SamgraphconditionPackage.eINSTANCE;
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
			case SamgraphconditionPackage.PROXY_NODE: {
				ProxyNode proxyNode = (ProxyNode)theEObject;
				T result = caseProxyNode(proxyNode);
				if (result == null) result = caseNode(proxyNode);
				if (result == null) result = caseAttributedElem(proxyNode);
				if (result == null) result = caseAnnotatedElem(proxyNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamgraphconditionPackage.GRAPH_CONDITION: {
				GraphCondition graphCondition = (GraphCondition)theEObject;
				T result = caseGraphCondition(graphCondition);
				if (result == null) result = caseTypeGraphCondition(graphCondition);
				if (result == null) result = caseAnnotatedElem(graphCondition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING: {
				LogicalGCCoupling logicalGCCoupling = (LogicalGCCoupling)theEObject;
				T result = caseLogicalGCCoupling(logicalGCCoupling);
				if (result == null) result = caseGraphCondition(logicalGCCoupling);
				if (result == null) result = caseTypeGraphCondition(logicalGCCoupling);
				if (result == null) result = caseAnnotatedElem(logicalGCCoupling);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamgraphconditionPackage.QUANTIFICATION: {
				Quantification quantification = (Quantification)theEObject;
				T result = caseQuantification(quantification);
				if (result == null) result = caseGraphCondition(quantification);
				if (result == null) result = caseTypeGraphCondition(quantification);
				if (result == null) result = caseAnnotatedElem(quantification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamgraphconditionPackage.TERMINATION_CONDITION: {
				TerminationCondition terminationCondition = (TerminationCondition)theEObject;
				T result = caseTerminationCondition(terminationCondition);
				if (result == null) result = caseGraphCondition(terminationCondition);
				if (result == null) result = caseTypeGraphCondition(terminationCondition);
				if (result == null) result = caseAnnotatedElem(terminationCondition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SamgraphconditionPackage.NEGATED_CONDITION: {
				NegatedCondition negatedCondition = (NegatedCondition)theEObject;
				T result = caseNegatedCondition(negatedCondition);
				if (result == null) result = caseGraphCondition(negatedCondition);
				if (result == null) result = caseTypeGraphCondition(negatedCondition);
				if (result == null) result = caseAnnotatedElem(negatedCondition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proxy Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proxy Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProxyNode(ProxyNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Graph Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Graph Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGraphCondition(GraphCondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Logical GC Coupling</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Logical GC Coupling</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLogicalGCCoupling(LogicalGCCoupling object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Quantification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Quantification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQuantification(Quantification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Termination Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Termination Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTerminationCondition(TerminationCondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Negated Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Negated Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNegatedCondition(NegatedCondition object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Type Graph Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Graph Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeGraphCondition(TypeGraphCondition object) {
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

} //SamgraphconditionSwitch
