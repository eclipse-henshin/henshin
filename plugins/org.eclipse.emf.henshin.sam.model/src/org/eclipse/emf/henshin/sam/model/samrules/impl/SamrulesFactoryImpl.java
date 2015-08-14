/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samrules.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.henshin.sam.model.samrules.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SamrulesFactoryImpl extends EFactoryImpl implements SamrulesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SamrulesFactory init() {
		try {
			SamrulesFactory theSamrulesFactory = (SamrulesFactory)EPackage.Registry.INSTANCE.getEFactory(SamrulesPackage.eNS_URI);
			if (theSamrulesFactory != null) {
				return theSamrulesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SamrulesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamrulesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SamrulesPackage.GRAPH_RULE: return createGraphRule();
			case SamrulesPackage.PRESERVED_NODE: return createPreservedNode();
			case SamrulesPackage.PRESERVED_EDGE: return createPreservedEdge();
			case SamrulesPackage.RULE_GRAPH: return createRuleGraph();
			case SamrulesPackage.GTS: return createGTS();
			case SamrulesPackage.CREATED_NODE: return createCreatedNode();
			case SamrulesPackage.DELETED_NODE: return createDeletedNode();
			case SamrulesPackage.CREATED_EDGE: return createCreatedEdge();
			case SamrulesPackage.DELETED_EDGE: return createDeletedEdge();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GraphRule createGraphRule() {
		GraphRuleImpl graphRule = new GraphRuleImpl();
		return graphRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreservedNode createPreservedNode() {
		PreservedNodeImpl preservedNode = new PreservedNodeImpl();
		return preservedNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreservedEdge createPreservedEdge() {
		PreservedEdgeImpl preservedEdge = new PreservedEdgeImpl();
		return preservedEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleGraph createRuleGraph() {
		RuleGraphImpl ruleGraph = new RuleGraphImpl();
		return ruleGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTS createGTS() {
		GTSImpl gts = new GTSImpl();
		return gts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreatedNode createCreatedNode() {
		CreatedNodeImpl createdNode = new CreatedNodeImpl();
		return createdNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeletedNode createDeletedNode() {
		DeletedNodeImpl deletedNode = new DeletedNodeImpl();
		return deletedNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreatedEdge createCreatedEdge() {
		CreatedEdgeImpl createdEdge = new CreatedEdgeImpl();
		return createdEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeletedEdge createDeletedEdge() {
		DeletedEdgeImpl deletedEdge = new DeletedEdgeImpl();
		return deletedEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamrulesPackage getSamrulesPackage() {
		return (SamrulesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SamrulesPackage getPackage() {
		return SamrulesPackage.eINSTANCE;
	}

} //SamrulesFactoryImpl
