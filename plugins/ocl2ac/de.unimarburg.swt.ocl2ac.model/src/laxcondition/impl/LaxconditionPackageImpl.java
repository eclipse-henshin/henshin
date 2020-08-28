/**
 */
package laxcondition.impl;

import graph.GraphPackage;

import graph.impl.GraphPackageImpl;

import laxcondition.Condition;
import laxcondition.Formula;
import laxcondition.LaxCondition;
import laxcondition.LaxconditionFactory;
import laxcondition.LaxconditionPackage;
import laxcondition.Operator;
import laxcondition.QuantifiedLaxCondition;
import laxcondition.Quantifier;
import laxcondition.True;
import laxcondition.Variable;

import laxcondition.util.LaxconditionValidator;

import nestedcondition.NestedconditionPackage;

import nestedcondition.impl.NestedconditionPackageImpl;

import nestedconstraintmodel.NestedconstraintmodelPackage;

import nestedconstraintmodel.impl.NestedconstraintmodelPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
public class LaxconditionPackageImpl extends EPackageImpl implements LaxconditionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass laxConditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass quantifiedLaxConditionEClass = null;

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
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum quantifierEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum operatorEEnum = null;

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
	 * @see laxcondition.LaxconditionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LaxconditionPackageImpl() {
		super(eNS_URI, LaxconditionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link LaxconditionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LaxconditionPackage init() {
		if (isInited) return (LaxconditionPackage)EPackage.Registry.INSTANCE.getEPackage(LaxconditionPackage.eNS_URI);

		// Obtain or create and register package
		LaxconditionPackageImpl theLaxconditionPackage = (LaxconditionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof LaxconditionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new LaxconditionPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		NestedconstraintmodelPackageImpl theNestedconstraintmodelPackage = (NestedconstraintmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NestedconstraintmodelPackage.eNS_URI) instanceof NestedconstraintmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NestedconstraintmodelPackage.eNS_URI) : NestedconstraintmodelPackage.eINSTANCE);
		NestedconditionPackageImpl theNestedconditionPackage = (NestedconditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NestedconditionPackage.eNS_URI) instanceof NestedconditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NestedconditionPackage.eNS_URI) : NestedconditionPackage.eINSTANCE);
		GraphPackageImpl theGraphPackage = (GraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI) instanceof GraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI) : GraphPackage.eINSTANCE);

		// Create package meta-data objects
		theLaxconditionPackage.createPackageContents();
		theNestedconstraintmodelPackage.createPackageContents();
		theNestedconditionPackage.createPackageContents();
		theGraphPackage.createPackageContents();

		// Initialize created meta-data
		theLaxconditionPackage.initializePackageContents();
		theNestedconstraintmodelPackage.initializePackageContents();
		theNestedconditionPackage.initializePackageContents();
		theGraphPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theLaxconditionPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return LaxconditionValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theLaxconditionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LaxconditionPackage.eNS_URI, theLaxconditionPackage);
		return theLaxconditionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCondition() {
		return conditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCondition_TypeGraph() {
		return (EReference)conditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCondition_Name() {
		return (EAttribute)conditionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCondition_LaxCondition() {
		return (EReference)conditionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLaxCondition() {
		return laxConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLaxCondition_Formula() {
		return (EReference)laxConditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQuantifiedLaxCondition() {
		return quantifiedLaxConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQuantifiedLaxCondition_Graph() {
		return (EReference)quantifiedLaxConditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQuantifiedLaxCondition_Condition() {
		return (EReference)quantifiedLaxConditionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQuantifiedLaxCondition_Quantifier() {
		return (EAttribute)quantifiedLaxConditionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQuantifiedLaxCondition_Variables() {
		return (EReference)quantifiedLaxConditionEClass.getEStructuralFeatures().get(3);
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
	public EAttribute getFormula_Op() {
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
	public EEnum getQuantifier() {
		return quantifierEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOperator() {
		return operatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LaxconditionFactory getLaxconditionFactory() {
		return (LaxconditionFactory)getEFactoryInstance();
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
		conditionEClass = createEClass(CONDITION);
		createEReference(conditionEClass, CONDITION__TYPE_GRAPH);
		createEAttribute(conditionEClass, CONDITION__NAME);
		createEReference(conditionEClass, CONDITION__LAX_CONDITION);

		laxConditionEClass = createEClass(LAX_CONDITION);
		createEReference(laxConditionEClass, LAX_CONDITION__FORMULA);

		quantifiedLaxConditionEClass = createEClass(QUANTIFIED_LAX_CONDITION);
		createEReference(quantifiedLaxConditionEClass, QUANTIFIED_LAX_CONDITION__GRAPH);
		createEReference(quantifiedLaxConditionEClass, QUANTIFIED_LAX_CONDITION__CONDITION);
		createEAttribute(quantifiedLaxConditionEClass, QUANTIFIED_LAX_CONDITION__QUANTIFIER);
		createEReference(quantifiedLaxConditionEClass, QUANTIFIED_LAX_CONDITION__VARIABLES);

		trueEClass = createEClass(TRUE);

		formulaEClass = createEClass(FORMULA);
		createEAttribute(formulaEClass, FORMULA__OP);
		createEReference(formulaEClass, FORMULA__ARGUMENTS);

		variableEClass = createEClass(VARIABLE);
		createEAttribute(variableEClass, VARIABLE__NAME);

		// Create enums
		quantifierEEnum = createEEnum(QUANTIFIER);
		operatorEEnum = createEEnum(OPERATOR);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		quantifiedLaxConditionEClass.getESuperTypes().add(this.getLaxCondition());
		trueEClass.getESuperTypes().add(this.getLaxCondition());
		formulaEClass.getESuperTypes().add(this.getLaxCondition());

		// Initialize classes, features, and operations; add parameters
		initEClass(conditionEClass, Condition.class, "Condition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCondition_TypeGraph(), ecorePackage.getEPackage(), null, "typeGraph", null, 1, 1, Condition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCondition_Name(), ecorePackage.getEString(), "name", null, 0, 1, Condition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCondition_LaxCondition(), this.getLaxCondition(), null, "laxCondition", null, 1, 1, Condition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(laxConditionEClass, LaxCondition.class, "LaxCondition", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLaxCondition_Formula(), this.getFormula(), this.getFormula_Arguments(), "formula", null, 0, 1, LaxCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(quantifiedLaxConditionEClass, QuantifiedLaxCondition.class, "QuantifiedLaxCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getQuantifiedLaxCondition_Graph(), theGraphPackage.getGraph(), null, "graph", null, 1, 1, QuantifiedLaxCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQuantifiedLaxCondition_Condition(), this.getLaxCondition(), null, "condition", null, 1, 1, QuantifiedLaxCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQuantifiedLaxCondition_Quantifier(), this.getQuantifier(), "quantifier", "EXISTS", 1, 1, QuantifiedLaxCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQuantifiedLaxCondition_Variables(), this.getVariable(), null, "variables", null, 0, -1, QuantifiedLaxCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trueEClass, True.class, "True", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(formulaEClass, Formula.class, "Formula", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFormula_Op(), this.getOperator(), "op", "NOT", 1, 1, Formula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFormula_Arguments(), this.getLaxCondition(), this.getLaxCondition_Formula(), "arguments", null, 1, -1, Formula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariable_Name(), ecorePackage.getEString(), "name", null, 0, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(quantifierEEnum, Quantifier.class, "Quantifier");
		addEEnumLiteral(quantifierEEnum, Quantifier.EXISTS);
		addEEnumLiteral(quantifierEEnum, Quantifier.FORALL);

		initEEnum(operatorEEnum, Operator.class, "Operator");
		addEEnumLiteral(operatorEEnum, Operator.NOT);
		addEEnumLiteral(operatorEEnum, Operator.AND);
		addEEnumLiteral(operatorEEnum, Operator.OR);
		addEEnumLiteral(operatorEEnum, Operator.IMPLIES);
		addEEnumLiteral(operatorEEnum, Operator.EQUIVALENT);
		addEEnumLiteral(operatorEEnum, Operator.XOR);

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
			 "ecore", "http://www.eclipse.org/emf/2002/Ecore",
			 "graph_0", "graph.ecore#/"
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
		  (conditionEClass, 
		   source, 
		   new String[] {
			 "constraints", "TypeGraphConsistency"
		   });	
		addAnnotation
		  (quantifiedLaxConditionEClass, 
		   source, 
		   new String[] {
			 "constraints", "HostGraphConsistency"
		   });	
		addAnnotation
		  (formulaEClass, 
		   source, 
		   new String[] {
			 "constraints", "OneArgumentForNOT AtLeastTwoArgumentForANDOR TwoArgumentForIMPLEQUALXOR"
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
		  (conditionEClass, 
		   source, 
		   new String[] {
			 "TypeGraphConsistency", "graph_0::Graph.allInstances() -> forAll(g|g.typegraph = self.typeGraph)"
		   });	
		addAnnotation
		  (quantifiedLaxConditionEClass, 
		   source, 
		   new String[] {
			 "HostGraphConsistency", "self.graph.edges -> forAll(e|self.graph.nodes -> includes(e.source) and self.graph.nodes -> includes(e.target))"
		   });	
		addAnnotation
		  (formulaEClass, 
		   source, 
		   new String[] {
			 "OneArgumentForNOT", "(self.op = Operator::NOT) implies (self.arguments -> size() = 1)",
			 "AtLeastTwoArgumentForANDOR", "((self.op = Operator::AND) or (self.op = Operator::OR)) implies (self.arguments -> size() > 1)",
			 "TwoArgumentForIMPLEQUALXOR", "((self.op = Operator::IMPLIES) or (self.op = Operator::EQUIVALENT) or (self.op = Operator::XOR)) implies (self.arguments -> size() = 2)"
		   });
	}

} //LaxconditionPackageImpl
