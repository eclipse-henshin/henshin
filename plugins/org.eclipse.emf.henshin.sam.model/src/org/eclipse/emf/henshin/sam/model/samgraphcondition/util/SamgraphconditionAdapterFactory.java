/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.AttributedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.*;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage
 * @generated
 */
public class SamgraphconditionAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SamgraphconditionPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamgraphconditionAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SamgraphconditionPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SamgraphconditionSwitch<Adapter> modelSwitch =
		new SamgraphconditionSwitch<Adapter>() {
			@Override
			public Adapter caseProxyNode(ProxyNode object) {
				return createProxyNodeAdapter();
			}
			@Override
			public Adapter caseGraphCondition(GraphCondition object) {
				return createGraphConditionAdapter();
			}
			@Override
			public Adapter caseLogicalGCCoupling(LogicalGCCoupling object) {
				return createLogicalGCCouplingAdapter();
			}
			@Override
			public Adapter caseQuantification(Quantification object) {
				return createQuantificationAdapter();
			}
			@Override
			public Adapter caseTerminationCondition(TerminationCondition object) {
				return createTerminationConditionAdapter();
			}
			@Override
			public Adapter caseNegatedCondition(NegatedCondition object) {
				return createNegatedConditionAdapter();
			}
			@Override
			public Adapter caseAttributedElem(AttributedElem object) {
				return createAttributedElemAdapter();
			}
			@Override
			public Adapter caseAnnotatedElem(AnnotatedElem object) {
				return createAnnotatedElemAdapter();
			}
			@Override
			public Adapter caseNode(Node object) {
				return createNodeAdapter();
			}
			@Override
			public Adapter caseTypeGraphCondition(TypeGraphCondition object) {
				return createTypeGraphConditionAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.ProxyNode <em>Proxy Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.ProxyNode
	 * @generated
	 */
	public Adapter createProxyNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition <em>Graph Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition
	 * @generated
	 */
	public Adapter createGraphConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling <em>Logical GC Coupling</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling
	 * @generated
	 */
	public Adapter createLogicalGCCouplingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification <em>Quantification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification
	 * @generated
	 */
	public Adapter createQuantificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.TerminationCondition <em>Termination Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.TerminationCondition
	 * @generated
	 */
	public Adapter createTerminationConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition <em>Negated Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition
	 * @generated
	 */
	public Adapter createNegatedConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.sam.model.samgraph.AttributedElem <em>Attributed Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.AttributedElem
	 * @generated
	 */
	public Adapter createAttributedElemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem <em>Annotated Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem
	 * @generated
	 */
	public Adapter createAnnotatedElemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.sam.model.samgraph.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.Node
	 * @generated
	 */
	public Adapter createNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition <em>Type Graph Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition
	 * @generated
	 */
	public Adapter createTypeGraphConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //SamgraphconditionAdapterFactory
