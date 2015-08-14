/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.SamalgebraPackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.impl.SamalgebraPackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.TermAlgebraUsePackageImpl;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;
import org.eclipse.emf.henshin.sam.model.samannotation.impl.SamannotationPackageImpl;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samgraph.impl.SamgraphPackageImpl;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage;
import org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl;
import org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeTypeCondition;
import org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem;
import org.eclipse.emf.henshin.sam.model.samtypegraph.Cardinality;
import org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr;
import org.eclipse.emf.henshin.sam.model.samtypegraph.DataType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SamtypegraphPackageImpl extends EPackageImpl implements SamtypegraphPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass edgeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass continuousAttrEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inheritanceRelationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeGraphEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeGraphConditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeTypeConditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cardinalityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributedElemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum edgeDirectionEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SamtypegraphPackageImpl() {
		super(eNS_URI, SamtypegraphFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link SamtypegraphPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SamtypegraphPackage init() {
		if (isInited) return (SamtypegraphPackage)EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI);

		// Obtain or create and register package
		SamtypegraphPackageImpl theSamtypegraphPackage = (SamtypegraphPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SamtypegraphPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SamtypegraphPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		SamalgebraPackageImpl theSamalgebraPackage = (SamalgebraPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) instanceof SamalgebraPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) : SamalgebraPackage.eINSTANCE);
		AlgebraUsePackageImpl theAlgebraUsePackage = (AlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI) instanceof AlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI) : AlgebraUsePackage.eINSTANCE);
		SignaturePackageImpl theSignaturePackage = (SignaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) instanceof SignaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) : SignaturePackage.eINSTANCE);
		TermAlgebraUsePackageImpl theTermAlgebraUsePackage = (TermAlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI) instanceof TermAlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI) : TermAlgebraUsePackage.eINSTANCE);
		SamgraphPackageImpl theSamgraphPackage = (SamgraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) instanceof SamgraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) : SamgraphPackage.eINSTANCE);
		SamrulesPackageImpl theSamrulesPackage = (SamrulesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI) instanceof SamrulesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI) : SamrulesPackage.eINSTANCE);
		SamgraphconditionPackageImpl theSamgraphconditionPackage = (SamgraphconditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI) instanceof SamgraphconditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI) : SamgraphconditionPackage.eINSTANCE);
		SamtracePackageImpl theSamtracePackage = (SamtracePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI) instanceof SamtracePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI) : SamtracePackage.eINSTANCE);
		SamannotationPackageImpl theSamannotationPackage = (SamannotationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) instanceof SamannotationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) : SamannotationPackage.eINSTANCE);

		// Create package meta-data objects
		theSamtypegraphPackage.createPackageContents();
		theSamalgebraPackage.createPackageContents();
		theAlgebraUsePackage.createPackageContents();
		theSignaturePackage.createPackageContents();
		theTermAlgebraUsePackage.createPackageContents();
		theSamgraphPackage.createPackageContents();
		theSamrulesPackage.createPackageContents();
		theSamgraphconditionPackage.createPackageContents();
		theSamtracePackage.createPackageContents();
		theSamannotationPackage.createPackageContents();

		// Initialize created meta-data
		theSamtypegraphPackage.initializePackageContents();
		theSamalgebraPackage.initializePackageContents();
		theAlgebraUsePackage.initializePackageContents();
		theSignaturePackage.initializePackageContents();
		theTermAlgebraUsePackage.initializePackageContents();
		theSamgraphPackage.initializePackageContents();
		theSamrulesPackage.initializePackageContents();
		theSamgraphconditionPackage.initializePackageContents();
		theSamtracePackage.initializePackageContents();
		theSamannotationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSamtypegraphPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SamtypegraphPackage.eNS_URI, theSamtypegraphPackage);
		return theSamtypegraphPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNodeType() {
		return nodeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeType_Outgoing() {
		return (EReference)nodeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeType_Incoming() {
		return (EReference)nodeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNodeType_Name() {
		return (EAttribute)nodeTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeType_TypeGraph() {
		return (EReference)nodeTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeType_SuperType() {
		return (EReference)nodeTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeType_SubType() {
		return (EReference)nodeTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEdgeType() {
		return edgeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeType_Source() {
		return (EReference)edgeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeType_Target() {
		return (EReference)edgeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEdgeType_Name() {
		return (EAttribute)edgeTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeType_TypeGraph() {
		return (EReference)edgeTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEdgeType_Direction() {
		return (EAttribute)edgeTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeType_SourceCardinality() {
		return (EReference)edgeTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeType_TargetCardinality() {
		return (EReference)edgeTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeType() {
		return attributeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeType_Name() {
		return (EAttribute)attributeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeType_Sort() {
		return (EReference)attributeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeType_AttributedElement() {
		return (EReference)attributeTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContinuousAttr() {
		return continuousAttrEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContinuousAttr_Derivation() {
		return (EAttribute)continuousAttrEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContinuousAttr_LowerBound() {
		return (EAttribute)continuousAttrEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContinuousAttr_UpperBound() {
		return (EAttribute)continuousAttrEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInheritanceRelation() {
		return inheritanceRelationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInheritanceRelation_SubType() {
		return (EReference)inheritanceRelationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInheritanceRelation_SuperType() {
		return (EReference)inheritanceRelationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTypeGraph() {
		return typeGraphEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTypeGraph_NodeTypes() {
		return (EReference)typeGraphEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTypeGraph_EdgeTypes() {
		return (EReference)typeGraphEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTypeGraph_Name() {
		return (EAttribute)typeGraphEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTypeGraph_Conditions() {
		return (EReference)typeGraphEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTypeGraphCondition() {
		return typeGraphConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeTypeCondition() {
		return attributeTypeConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeTypeCondition_Operation() {
		return (EReference)attributeTypeConditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCardinality() {
		return cardinalityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCardinality_LowerBound() {
		return (EAttribute)cardinalityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCardinality_UpperBound() {
		return (EAttribute)cardinalityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributedElem() {
		return attributedElemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributedElem_Attributes() {
		return (EReference)attributedElemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataType() {
		return dataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataType_Sort() {
		return (EReference)dataTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEdgeDirection() {
		return edgeDirectionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamtypegraphFactory getSamtypegraphFactory() {
		return (SamtypegraphFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		nodeTypeEClass = createEClass(NODE_TYPE);
		createEReference(nodeTypeEClass, NODE_TYPE__OUTGOING);
		createEReference(nodeTypeEClass, NODE_TYPE__INCOMING);
		createEAttribute(nodeTypeEClass, NODE_TYPE__NAME);
		createEReference(nodeTypeEClass, NODE_TYPE__TYPE_GRAPH);
		createEReference(nodeTypeEClass, NODE_TYPE__SUPER_TYPE);
		createEReference(nodeTypeEClass, NODE_TYPE__SUB_TYPE);

		edgeTypeEClass = createEClass(EDGE_TYPE);
		createEReference(edgeTypeEClass, EDGE_TYPE__SOURCE);
		createEReference(edgeTypeEClass, EDGE_TYPE__TARGET);
		createEAttribute(edgeTypeEClass, EDGE_TYPE__NAME);
		createEReference(edgeTypeEClass, EDGE_TYPE__TYPE_GRAPH);
		createEAttribute(edgeTypeEClass, EDGE_TYPE__DIRECTION);
		createEReference(edgeTypeEClass, EDGE_TYPE__SOURCE_CARDINALITY);
		createEReference(edgeTypeEClass, EDGE_TYPE__TARGET_CARDINALITY);

		attributeTypeEClass = createEClass(ATTRIBUTE_TYPE);
		createEAttribute(attributeTypeEClass, ATTRIBUTE_TYPE__NAME);
		createEReference(attributeTypeEClass, ATTRIBUTE_TYPE__SORT);
		createEReference(attributeTypeEClass, ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT);

		continuousAttrEClass = createEClass(CONTINUOUS_ATTR);
		createEAttribute(continuousAttrEClass, CONTINUOUS_ATTR__DERIVATION);
		createEAttribute(continuousAttrEClass, CONTINUOUS_ATTR__LOWER_BOUND);
		createEAttribute(continuousAttrEClass, CONTINUOUS_ATTR__UPPER_BOUND);

		inheritanceRelationEClass = createEClass(INHERITANCE_RELATION);
		createEReference(inheritanceRelationEClass, INHERITANCE_RELATION__SUB_TYPE);
		createEReference(inheritanceRelationEClass, INHERITANCE_RELATION__SUPER_TYPE);

		typeGraphEClass = createEClass(TYPE_GRAPH);
		createEReference(typeGraphEClass, TYPE_GRAPH__NODE_TYPES);
		createEReference(typeGraphEClass, TYPE_GRAPH__EDGE_TYPES);
		createEAttribute(typeGraphEClass, TYPE_GRAPH__NAME);
		createEReference(typeGraphEClass, TYPE_GRAPH__CONDITIONS);

		typeGraphConditionEClass = createEClass(TYPE_GRAPH_CONDITION);

		attributeTypeConditionEClass = createEClass(ATTRIBUTE_TYPE_CONDITION);
		createEReference(attributeTypeConditionEClass, ATTRIBUTE_TYPE_CONDITION__OPERATION);

		cardinalityEClass = createEClass(CARDINALITY);
		createEAttribute(cardinalityEClass, CARDINALITY__LOWER_BOUND);
		createEAttribute(cardinalityEClass, CARDINALITY__UPPER_BOUND);

		attributedElemEClass = createEClass(ATTRIBUTED_ELEM);
		createEReference(attributedElemEClass, ATTRIBUTED_ELEM__ATTRIBUTES);

		dataTypeEClass = createEClass(DATA_TYPE);
		createEReference(dataTypeEClass, DATA_TYPE__SORT);

		// Create enums
		edgeDirectionEEnum = createEEnum(EDGE_DIRECTION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		SamannotationPackage theSamannotationPackage = (SamannotationPackage)EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI);
		SignaturePackage theSignaturePackage = (SignaturePackage)EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI);
		TermAlgebraUsePackage theTermAlgebraUsePackage = (TermAlgebraUsePackage)EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		nodeTypeEClass.getESuperTypes().add(this.getAttributedElem());
		nodeTypeEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		edgeTypeEClass.getESuperTypes().add(this.getAttributedElem());
		edgeTypeEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		attributeTypeEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		continuousAttrEClass.getESuperTypes().add(this.getAttributeType());
		inheritanceRelationEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		typeGraphEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		attributeTypeConditionEClass.getESuperTypes().add(this.getTypeGraphCondition());
		cardinalityEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		dataTypeEClass.getESuperTypes().add(this.getNodeType());

		// Initialize classes and features; add operations and parameters
		initEClass(nodeTypeEClass, NodeType.class, "NodeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeType_Outgoing(), this.getEdgeType(), this.getEdgeType_Source(), "outgoing", null, 0, -1, NodeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeType_Incoming(), this.getEdgeType(), this.getEdgeType_Target(), "incoming", null, 0, -1, NodeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeType_Name(), ecorePackage.getEString(), "name", null, 0, 1, NodeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeType_TypeGraph(), this.getTypeGraph(), this.getTypeGraph_NodeTypes(), "typeGraph", null, 1, 1, NodeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeType_SuperType(), this.getInheritanceRelation(), this.getInheritanceRelation_SubType(), "superType", null, 0, 1, NodeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeType_SubType(), this.getInheritanceRelation(), this.getInheritanceRelation_SuperType(), "subType", null, 0, -1, NodeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(edgeTypeEClass, EdgeType.class, "EdgeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEdgeType_Source(), this.getNodeType(), this.getNodeType_Outgoing(), "source", null, 0, 1, EdgeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeType_Target(), this.getNodeType(), this.getNodeType_Incoming(), "target", null, 0, 1, EdgeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeType_Name(), ecorePackage.getEString(), "name", null, 0, 1, EdgeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeType_TypeGraph(), this.getTypeGraph(), this.getTypeGraph_EdgeTypes(), "typeGraph", null, 1, 1, EdgeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeType_Direction(), this.getEdgeDirection(), "direction", "UNDIRECTED", 0, 1, EdgeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeType_SourceCardinality(), this.getCardinality(), null, "sourceCardinality", null, 1, 1, EdgeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeType_TargetCardinality(), this.getCardinality(), null, "targetCardinality", null, 1, 1, EdgeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeTypeEClass, AttributeType.class, "AttributeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeType_Name(), ecorePackage.getEString(), "name", null, 0, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeType_Sort(), theSignaturePackage.getSort(), null, "sort", null, 1, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeType_AttributedElement(), this.getAttributedElem(), this.getAttributedElem_Attributes(), "attributedElement", null, 1, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(attributeTypeEClass, theSignaturePackage.getSort(), "lookUpSort", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDataType(), "dataType", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(attributeTypeEClass, ecorePackage.getEDataType(), "lookUpEDataType", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSignaturePackage.getSort(), "sort", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(attributeTypeEClass, theSignaturePackage.getSort(), "lookUpSort", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(continuousAttrEClass, ContinuousAttr.class, "ContinuousAttr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getContinuousAttr_Derivation(), ecorePackage.getEFloat(), "derivation", null, 0, 1, ContinuousAttr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContinuousAttr_LowerBound(), ecorePackage.getEDouble(), "lowerBound", null, 0, 1, ContinuousAttr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContinuousAttr_UpperBound(), ecorePackage.getEDouble(), "upperBound", null, 0, 1, ContinuousAttr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inheritanceRelationEClass, InheritanceRelation.class, "InheritanceRelation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInheritanceRelation_SubType(), this.getNodeType(), this.getNodeType_SuperType(), "subType", null, 1, 1, InheritanceRelation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInheritanceRelation_SuperType(), this.getNodeType(), this.getNodeType_SubType(), "superType", null, 1, 1, InheritanceRelation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeGraphEClass, TypeGraph.class, "TypeGraph", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypeGraph_NodeTypes(), this.getNodeType(), this.getNodeType_TypeGraph(), "nodeTypes", null, 0, -1, TypeGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTypeGraph_EdgeTypes(), this.getEdgeType(), this.getEdgeType_TypeGraph(), "edgeTypes", null, 0, -1, TypeGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTypeGraph_Name(), ecorePackage.getEString(), "name", null, 0, 1, TypeGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTypeGraph_Conditions(), this.getTypeGraphCondition(), null, "conditions", null, 0, -1, TypeGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeGraphConditionEClass, TypeGraphCondition.class, "TypeGraphCondition", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(attributeTypeConditionEClass, AttributeTypeCondition.class, "AttributeTypeCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeTypeCondition_Operation(), theTermAlgebraUsePackage.getOperationSymbolUse(), null, "operation", null, 1, 1, AttributeTypeCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cardinalityEClass, Cardinality.class, "Cardinality", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCardinality_LowerBound(), ecorePackage.getEInt(), "lowerBound", null, 0, 1, Cardinality.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCardinality_UpperBound(), ecorePackage.getEInt(), "upperBound", null, 0, 1, Cardinality.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributedElemEClass, AttributedElem.class, "AttributedElem", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributedElem_Attributes(), this.getAttributeType(), this.getAttributeType_AttributedElement(), "attributes", null, 0, -1, AttributedElem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dataTypeEClass, DataType.class, "DataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDataType_Sort(), theSignaturePackage.getSort(), null, "sort", null, 0, 1, DataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(dataTypeEClass, theSignaturePackage.getSort(), "lookUpSort", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "dataType", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(edgeDirectionEEnum, EdgeDirection.class, "EdgeDirection");
		addEEnumLiteral(edgeDirectionEEnum, EdgeDirection.DIRECTED);
		addEEnumLiteral(edgeDirectionEEnum, EdgeDirection.UNDIRECTED);

		// Create resource
		createResource(eNS_URI);
	}

} //SamtypegraphPackageImpl
