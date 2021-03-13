/**
 */
package nestedcondition.impl;

import graph.GraphPackage;

import graph.impl.GraphPackageImpl;

import laxcondition.LaxconditionPackage;

import laxcondition.impl.LaxconditionPackageImpl;

import nestedcondition.EdgeMapping;
import nestedcondition.Formula;
import nestedcondition.Morphism;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.NestedconditionFactory;
import nestedcondition.NestedconditionPackage;
import nestedcondition.NodeMapping;
import nestedcondition.QuantifiedCondition;
import nestedcondition.True;
import nestedcondition.Variable;

import nestedcondition.util.NestedconditionValidator;

import nestedconstraintmodel.NestedconstraintmodelPackage;

import nestedconstraintmodel.impl.NestedconstraintmodelPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NestedconditionPackageImpl extends EPackageImpl implements NestedconditionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nestedConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nestedConditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass quantifiedConditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass trueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass formulaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass morphismEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass edgeMappingEClass = null;

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
	 * @see nestedcondition.NestedconditionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private NestedconditionPackageImpl() {
		super(eNS_URI, NestedconditionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link NestedconditionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static NestedconditionPackage init() {
		if (isInited) return (NestedconditionPackage)EPackage.Registry.INSTANCE.getEPackage(NestedconditionPackage.eNS_URI);

		// Obtain or create and register package
		NestedconditionPackageImpl theNestedconditionPackage = (NestedconditionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof NestedconditionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new NestedconditionPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		NestedconstraintmodelPackageImpl theNestedconstraintmodelPackage = (NestedconstraintmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NestedconstraintmodelPackage.eNS_URI) instanceof NestedconstraintmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NestedconstraintmodelPackage.eNS_URI) : NestedconstraintmodelPackage.eINSTANCE);
		GraphPackageImpl theGraphPackage = (GraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI) instanceof GraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI) : GraphPackage.eINSTANCE);
		LaxconditionPackageImpl theLaxconditionPackage = (LaxconditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(LaxconditionPackage.eNS_URI) instanceof LaxconditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(LaxconditionPackage.eNS_URI) : LaxconditionPackage.eINSTANCE);

		// Create package meta-data objects
		theNestedconditionPackage.createPackageContents();
		theNestedconstraintmodelPackage.createPackageContents();
		theGraphPackage.createPackageContents();
		theLaxconditionPackage.createPackageContents();

		// Initialize created meta-data
		theNestedconditionPackage.initializePackageContents();
		theNestedconstraintmodelPackage.initializePackageContents();
		theGraphPackage.initializePackageContents();
		theLaxconditionPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theNestedconditionPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return NestedconditionValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theNestedconditionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(NestedconditionPackage.eNS_URI, theNestedconditionPackage);
		return theNestedconditionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNestedConstraint() {
		return nestedConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNestedConstraint_Name() {
		return (EAttribute)nestedConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNestedConstraint_TypeGraph() {
		return (EReference)nestedConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNestedConstraint_Condition() {
		return (EReference)nestedConstraintEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNestedConstraint_Domain() {
		return (EReference)nestedConstraintEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNestedCondition() {
		return nestedConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNestedCondition_Variables() {
		return (EReference)nestedConditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNestedCondition_Domain() {
		return (EReference)nestedConditionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariable_Name() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQuantifiedCondition() {
		return quantifiedConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQuantifiedCondition_Quantifier() {
		return (EAttribute)quantifiedConditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQuantifiedCondition_Condition() {
		return (EReference)quantifiedConditionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQuantifiedCondition_Morphism() {
		return (EReference)quantifiedConditionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQuantifiedCondition_Codomain() {
		return (EReference)quantifiedConditionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTrue() {
		return trueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFormula() {
		return formulaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFormula_Operator() {
		return (EAttribute)formulaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFormula_Arguments() {
		return (EReference)formulaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMorphism() {
		return morphismEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMorphism_NodeMappings() {
		return (EReference)morphismEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMorphism_From() {
		return (EReference)morphismEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMorphism_To() {
		return (EReference)morphismEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMorphism_EdgeMappings() {
		return (EReference)morphismEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNodeMapping() {
		return nodeMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeMapping_Origin() {
		return (EReference)nodeMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeMapping_Image() {
		return (EReference)nodeMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEdgeMapping() {
		return edgeMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeMapping_Origin() {
		return (EReference)edgeMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeMapping_Image() {
		return (EReference)edgeMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedconditionFactory getNestedconditionFactory() {
		return (NestedconditionFactory)getEFactoryInstance();
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
		nestedConstraintEClass = createEClass(NESTED_CONSTRAINT);
		createEAttribute(nestedConstraintEClass, NESTED_CONSTRAINT__NAME);
		createEReference(nestedConstraintEClass, NESTED_CONSTRAINT__TYPE_GRAPH);
		createEReference(nestedConstraintEClass, NESTED_CONSTRAINT__CONDITION);
		createEReference(nestedConstraintEClass, NESTED_CONSTRAINT__DOMAIN);

		nestedConditionEClass = createEClass(NESTED_CONDITION);
		createEReference(nestedConditionEClass, NESTED_CONDITION__VARIABLES);
		createEReference(nestedConditionEClass, NESTED_CONDITION__DOMAIN);

		variableEClass = createEClass(VARIABLE);
		createEAttribute(variableEClass, VARIABLE__NAME);

		quantifiedConditionEClass = createEClass(QUANTIFIED_CONDITION);
		createEAttribute(quantifiedConditionEClass, QUANTIFIED_CONDITION__QUANTIFIER);
		createEReference(quantifiedConditionEClass, QUANTIFIED_CONDITION__CONDITION);
		createEReference(quantifiedConditionEClass, QUANTIFIED_CONDITION__MORPHISM);
		createEReference(quantifiedConditionEClass, QUANTIFIED_CONDITION__CODOMAIN);

		trueEClass = createEClass(TRUE);

		formulaEClass = createEClass(FORMULA);
		createEAttribute(formulaEClass, FORMULA__OPERATOR);
		createEReference(formulaEClass, FORMULA__ARGUMENTS);

		morphismEClass = createEClass(MORPHISM);
		createEReference(morphismEClass, MORPHISM__NODE_MAPPINGS);
		createEReference(morphismEClass, MORPHISM__FROM);
		createEReference(morphismEClass, MORPHISM__TO);
		createEReference(morphismEClass, MORPHISM__EDGE_MAPPINGS);

		nodeMappingEClass = createEClass(NODE_MAPPING);
		createEReference(nodeMappingEClass, NODE_MAPPING__ORIGIN);
		createEReference(nodeMappingEClass, NODE_MAPPING__IMAGE);

		edgeMappingEClass = createEClass(EDGE_MAPPING);
		createEReference(edgeMappingEClass, EDGE_MAPPING__ORIGIN);
		createEReference(edgeMappingEClass, EDGE_MAPPING__IMAGE);
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
		GraphPackage theGraphPackage = (GraphPackage)EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI);
		LaxconditionPackage theLaxconditionPackage = (LaxconditionPackage)EPackage.Registry.INSTANCE.getEPackage(LaxconditionPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		quantifiedConditionEClass.getESuperTypes().add(this.getNestedCondition());
		trueEClass.getESuperTypes().add(this.getNestedCondition());
		formulaEClass.getESuperTypes().add(this.getNestedCondition());

		// Initialize classes, features, and operations; add parameters
		initEClass(nestedConstraintEClass, NestedConstraint.class, "NestedConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNestedConstraint_Name(), ecorePackage.getEString(), "name", null, 0, 1, NestedConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNestedConstraint_TypeGraph(), ecorePackage.getEPackage(), null, "typeGraph", null, 1, 1, NestedConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNestedConstraint_Condition(), this.getNestedCondition(), null, "condition", null, 1, 1, NestedConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNestedConstraint_Domain(), theGraphPackage.getGraph(), null, "domain", null, 1, 1, NestedConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nestedConditionEClass, NestedCondition.class, "NestedCondition", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNestedCondition_Variables(), this.getVariable(), null, "variables", null, 0, -1, NestedCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNestedCondition_Domain(), theGraphPackage.getGraph(), null, "domain", null, 1, 1, NestedCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariable_Name(), ecorePackage.getEString(), "name", null, 0, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(quantifiedConditionEClass, QuantifiedCondition.class, "QuantifiedCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQuantifiedCondition_Quantifier(), theLaxconditionPackage.getQuantifier(), "quantifier", null, 0, 1, QuantifiedCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQuantifiedCondition_Condition(), this.getNestedCondition(), null, "condition", null, 1, 1, QuantifiedCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQuantifiedCondition_Morphism(), this.getMorphism(), null, "morphism", null, 1, 1, QuantifiedCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQuantifiedCondition_Codomain(), theGraphPackage.getGraph(), null, "codomain", null, 1, 1, QuantifiedCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trueEClass, True.class, "True", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(formulaEClass, Formula.class, "Formula", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFormula_Operator(), theLaxconditionPackage.getOperator(), "operator", null, 0, 1, Formula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFormula_Arguments(), this.getNestedCondition(), null, "arguments", null, 1, -1, Formula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(morphismEClass, Morphism.class, "Morphism", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMorphism_NodeMappings(), this.getNodeMapping(), null, "nodeMappings", null, 0, -1, Morphism.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMorphism_From(), theGraphPackage.getGraph(), null, "from", null, 1, 1, Morphism.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMorphism_To(), theGraphPackage.getGraph(), null, "to", null, 1, 1, Morphism.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMorphism_EdgeMappings(), this.getEdgeMapping(), null, "edgeMappings", null, 0, -1, Morphism.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeMappingEClass, NodeMapping.class, "NodeMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeMapping_Origin(), theGraphPackage.getNode(), null, "origin", null, 1, 1, NodeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeMapping_Image(), theGraphPackage.getNode(), null, "image", null, 1, 1, NodeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(edgeMappingEClass, EdgeMapping.class, "EdgeMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEdgeMapping_Origin(), theGraphPackage.getEdge(), null, "origin", null, 1, 1, EdgeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeMapping_Image(), theGraphPackage.getEdge(), null, "image", null, 1, 1, EdgeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/OCL/Import
		createImportAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot
		createPivotAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/OCL/Import</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createImportAnnotations() {
		String source = "http://www.eclipse.org/OCL/Import";	
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "ecore", "http://www.eclipse.org/emf/2002/Ecore#/",
			 "graph", "graph.ecore#/",
			 "laxcondition", "laxcondition.ecore#/"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";	
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "invocationDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
			 "settingDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
			 "validationDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot"
		   });	
		addAnnotation
		  (nestedConstraintEClass, 
		   source, 
		   new String[] {
			 "constraints", "ConstraintDomainIsEmpty RootConditionDomainIsConatraintDomain TypeGraphConsistency HostGraphConsistency"
		   });	
		addAnnotation
		  (quantifiedConditionEClass, 
		   source, 
		   new String[] {
			 "constraints", "NestedDomainIsCoDomain MorphismIsFromDomainToCoDomain"
		   });	
		addAnnotation
		  (formulaEClass, 
		   source, 
		   new String[] {
			 "constraints", "ArgumentsDomainConsistency OneArgumentForNOT AtLeastTwoArgumentForANDOR TwoArgumentForIMPLEQUALXOR"
		   });	
		addAnnotation
		  (morphismEClass, 
		   source, 
		   new String[] {
			 "constraints", "NodeMappingIsFromDomainToCoDomain EdgeMappingIsFromDomainToCoDomain EdgeMappingConsistency"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createPivotAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot";	
		addAnnotation
		  (nestedConstraintEClass, 
		   source, 
		   new String[] {
			 "ConstraintDomainIsEmpty", "self.domain.nodes -> isEmpty() and self.domain.edges -> isEmpty()",
			 "RootConditionDomainIsConatraintDomain", "self.condition.domain = self.domain",
			 "TypeGraphConsistency", "graph::Graph.allInstances() -> forAll(g|g.typegraph = self.typeGraph)",
			 "HostGraphConsistency", "graph::Graph.allInstances() -> forAll(g|g.edges -> forAll(e|g.nodes -> includes(e.source) and g.nodes -> includes(e.target)))"
		   });	
		addAnnotation
		  (quantifiedConditionEClass, 
		   source, 
		   new String[] {
			 "NestedDomainIsCoDomain", "self.condition.domain = self.codomain",
			 "MorphismIsFromDomainToCoDomain", "self.morphism.from = self.domain and self.morphism.to = self.codomain"
		   });	
		addAnnotation
		  (formulaEClass, 
		   source, 
		   new String[] {
			 "ArgumentsDomainConsistency", "self.arguments -> forAll(cond|cond.domain = self.domain)",
			 "OneArgumentForNOT", "(self.operator = laxcondition::Operator::NOT) implies (self.arguments -> size() = 1)",
			 "AtLeastTwoArgumentForANDOR", "((self.operator = laxcondition::Operator::AND) or (self.operator = laxcondition::Operator::OR)) implies (self.arguments -> size() > 1)",
			 "TwoArgumentForIMPLEQUALXOR", "((self.operator = laxcondition::Operator::IMPLIES) or (self.operator = laxcondition::Operator::EQUIVALENT) or (self.operator = laxcondition::Operator::XOR)) implies (self.arguments -> size() = 2)"
		   });	
		addAnnotation
		  (morphismEClass, 
		   source, 
		   new String[] {
			 "NodeMappingIsFromDomainToCoDomain", "self.nodeMappings -> forAll(m|self.from.nodes -> includes(m.origin) and self.to.nodes -> includes(m.image))",
			 "EdgeMappingIsFromDomainToCoDomain", "self.edgeMappings -> forAll(m|self.from.edges -> includes(m.origin) and self.to.edges -> includes(m.image))",
			 "EdgeMappingConsistency", "self.edgeMappings -> forAll(em|self.nodeMappings -> exists(nm|nm.origin = em.origin.source and nm.image = em.image.source) and self.nodeMappings -> exists(nm|nm.origin = em.origin.target and nm.image = em.image.target))"
		   });
	}

} //NestedconditionPackageImpl
