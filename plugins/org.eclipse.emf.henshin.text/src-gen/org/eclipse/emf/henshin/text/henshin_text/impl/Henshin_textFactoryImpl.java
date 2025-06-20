/**
 * generated by Xtext 2.16.0
 */
package org.eclipse.emf.henshin.text.henshin_text.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.henshin.text.henshin_text.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Henshin_textFactoryImpl extends EFactoryImpl implements Henshin_textFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static Henshin_textFactory init()
  {
    try
    {
      Henshin_textFactory theHenshin_textFactory = (Henshin_textFactory)EPackage.Registry.INSTANCE.getEFactory(Henshin_textPackage.eNS_URI);
      if (theHenshin_textFactory != null)
      {
        return theHenshin_textFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new Henshin_textFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Henshin_textFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case Henshin_textPackage.MODEL: return createModel();
      case Henshin_textPackage.EPACKAGE_IMPORT: return createEPackageImport();
      case Henshin_textPackage.MODEL_ELEMENT: return createModelElement();
      case Henshin_textPackage.RULE_ELEMENT: return createRuleElement();
      case Henshin_textPackage.JAVA_IMPORT: return createJavaImport();
      case Henshin_textPackage.CHECK_DANGLING: return createCheckDangling();
      case Henshin_textPackage.INJECTIVE_MATCHING: return createInjectiveMatching();
      case Henshin_textPackage.CONDITIONS: return createConditions();
      case Henshin_textPackage.GRAPH: return createGraph();
      case Henshin_textPackage.GRAPH_ELEMENTS: return createGraphElements();
      case Henshin_textPackage.EDGES: return createEdges();
      case Henshin_textPackage.EDGE: return createEdge();
      case Henshin_textPackage.RULE_NODE_TYPES: return createRuleNodeTypes();
      case Henshin_textPackage.NODE: return createNode();
      case Henshin_textPackage.MULTI_RULE_REUSE_NODE: return createMultiRuleReuseNode();
      case Henshin_textPackage.ATTRIBUTE: return createAttribute();
      case Henshin_textPackage.MULTI_RULE: return createMultiRule();
      case Henshin_textPackage.FORMULA: return createFormula();
      case Henshin_textPackage.LOGIC: return createLogic();
      case Henshin_textPackage.CONDITION_GRAPH: return createConditionGraph();
      case Henshin_textPackage.CONDITION_GRAPH_ELEMENTS: return createConditionGraphElements();
      case Henshin_textPackage.CONDITION_EDGES: return createConditionEdges();
      case Henshin_textPackage.CONDITION_EDGE: return createConditionEdge();
      case Henshin_textPackage.CONDITION_NODE_TYPES: return createConditionNodeTypes();
      case Henshin_textPackage.CONDITION_NODE: return createConditionNode();
      case Henshin_textPackage.CONDITION_REUSE_NODE: return createConditionReuseNode();
      case Henshin_textPackage.MATCH: return createMatch();
      case Henshin_textPackage.UNIT_ELEMENT: return createUnitElement();
      case Henshin_textPackage.SEQUENTIAL_PROPERTIES: return createSequentialProperties();
      case Henshin_textPackage.STRICT: return createStrict();
      case Henshin_textPackage.ROLLBACK: return createRollback();
      case Henshin_textPackage.LIST: return createList();
      case Henshin_textPackage.INDEPENDENT_UNIT: return createIndependentUnit();
      case Henshin_textPackage.CONDITIONAL_UNIT: return createConditionalUnit();
      case Henshin_textPackage.PRIORITY_UNIT: return createPriorityUnit();
      case Henshin_textPackage.ITERATED_UNIT: return createIteratedUnit();
      case Henshin_textPackage.LOOP_UNIT: return createLoopUnit();
      case Henshin_textPackage.PARAMETER: return createParameter();
      case Henshin_textPackage.PARAMETER_TYPE: return createParameterType();
      case Henshin_textPackage.EXPRESSION: return createExpression();
      case Henshin_textPackage.RULE: return createRule();
      case Henshin_textPackage.UNIT: return createUnit();
      case Henshin_textPackage.OROR_XOR: return createORorXOR();
      case Henshin_textPackage.AND: return createAND();
      case Henshin_textPackage.NOT: return createNot();
      case Henshin_textPackage.CONDITION_GRAPH_REF: return createConditionGraphRef();
      case Henshin_textPackage.CALL: return createCall();
      case Henshin_textPackage.OR_EXPRESSION: return createOrExpression();
      case Henshin_textPackage.AND_EXPRESSION: return createAndExpression();
      case Henshin_textPackage.EQUALITY_EXPRESSION: return createEqualityExpression();
      case Henshin_textPackage.COMPARISON_EXPRESSION: return createComparisonExpression();
      case Henshin_textPackage.PLUS_EXPRESSION: return createPlusExpression();
      case Henshin_textPackage.MINUS_EXPRESSION: return createMinusExpression();
      case Henshin_textPackage.MUL_OR_DIV_EXPRESSION: return createMulOrDivExpression();
      case Henshin_textPackage.BRACKET_EXPRESSION: return createBracketExpression();
      case Henshin_textPackage.NOT_EXPRESSION: return createNotExpression();
      case Henshin_textPackage.PARAMETER_VALUE: return createParameterValue();
      case Henshin_textPackage.JAVA_CLASS_VALUE: return createJavaClassValue();
      case Henshin_textPackage.JAVA_ATTRIBUTE_VALUE: return createJavaAttributeValue();
      case Henshin_textPackage.STRING_VALUE: return createStringValue();
      case Henshin_textPackage.NUMBER_VALUE: return createNumberValue();
      case Henshin_textPackage.INTEGER_VALUE: return createIntegerValue();
      case Henshin_textPackage.NATURAL_VALUE: return createNaturalValue();
      case Henshin_textPackage.BOOL_VALUE: return createBoolValue();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue)
  {
    switch (eDataType.getClassifierID())
    {
      case Henshin_textPackage.PARAMETER_KIND:
        return createParameterKindFromString(eDataType, initialValue);
      case Henshin_textPackage.TYPE:
        return createTypeFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue)
  {
    switch (eDataType.getClassifierID())
    {
      case Henshin_textPackage.PARAMETER_KIND:
        return convertParameterKindToString(eDataType, instanceValue);
      case Henshin_textPackage.TYPE:
        return convertTypeToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Model createModel()
  {
    ModelImpl model = new ModelImpl();
    return model;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EPackageImport createEPackageImport()
  {
    EPackageImportImpl ePackageImport = new EPackageImportImpl();
    return ePackageImport;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ModelElement createModelElement()
  {
    ModelElementImpl modelElement = new ModelElementImpl();
    return modelElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public RuleElement createRuleElement()
  {
    RuleElementImpl ruleElement = new RuleElementImpl();
    return ruleElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public JavaImport createJavaImport()
  {
    JavaImportImpl javaImport = new JavaImportImpl();
    return javaImport;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CheckDangling createCheckDangling()
  {
    CheckDanglingImpl checkDangling = new CheckDanglingImpl();
    return checkDangling;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public InjectiveMatching createInjectiveMatching()
  {
    InjectiveMatchingImpl injectiveMatching = new InjectiveMatchingImpl();
    return injectiveMatching;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Conditions createConditions()
  {
    ConditionsImpl conditions = new ConditionsImpl();
    return conditions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Graph createGraph()
  {
    GraphImpl graph = new GraphImpl();
    return graph;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public GraphElements createGraphElements()
  {
    GraphElementsImpl graphElements = new GraphElementsImpl();
    return graphElements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Edges createEdges()
  {
    EdgesImpl edges = new EdgesImpl();
    return edges;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Edge createEdge()
  {
    EdgeImpl edge = new EdgeImpl();
    return edge;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public RuleNodeTypes createRuleNodeTypes()
  {
    RuleNodeTypesImpl ruleNodeTypes = new RuleNodeTypesImpl();
    return ruleNodeTypes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Node createNode()
  {
    NodeImpl node = new NodeImpl();
    return node;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MultiRuleReuseNode createMultiRuleReuseNode()
  {
    MultiRuleReuseNodeImpl multiRuleReuseNode = new MultiRuleReuseNodeImpl();
    return multiRuleReuseNode;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Attribute createAttribute()
  {
    AttributeImpl attribute = new AttributeImpl();
    return attribute;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MultiRule createMultiRule()
  {
    MultiRuleImpl multiRule = new MultiRuleImpl();
    return multiRule;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Formula createFormula()
  {
    FormulaImpl formula = new FormulaImpl();
    return formula;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Logic createLogic()
  {
    LogicImpl logic = new LogicImpl();
    return logic;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConditionGraph createConditionGraph()
  {
    ConditionGraphImpl conditionGraph = new ConditionGraphImpl();
    return conditionGraph;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConditionGraphElements createConditionGraphElements()
  {
    ConditionGraphElementsImpl conditionGraphElements = new ConditionGraphElementsImpl();
    return conditionGraphElements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConditionEdges createConditionEdges()
  {
    ConditionEdgesImpl conditionEdges = new ConditionEdgesImpl();
    return conditionEdges;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConditionEdge createConditionEdge()
  {
    ConditionEdgeImpl conditionEdge = new ConditionEdgeImpl();
    return conditionEdge;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConditionNodeTypes createConditionNodeTypes()
  {
    ConditionNodeTypesImpl conditionNodeTypes = new ConditionNodeTypesImpl();
    return conditionNodeTypes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConditionNode createConditionNode()
  {
    ConditionNodeImpl conditionNode = new ConditionNodeImpl();
    return conditionNode;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConditionReuseNode createConditionReuseNode()
  {
    ConditionReuseNodeImpl conditionReuseNode = new ConditionReuseNodeImpl();
    return conditionReuseNode;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Match createMatch()
  {
    MatchImpl match = new MatchImpl();
    return match;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public UnitElement createUnitElement()
  {
    UnitElementImpl unitElement = new UnitElementImpl();
    return unitElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public SequentialProperties createSequentialProperties()
  {
    SequentialPropertiesImpl sequentialProperties = new SequentialPropertiesImpl();
    return sequentialProperties;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Strict createStrict()
  {
    StrictImpl strict = new StrictImpl();
    return strict;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Rollback createRollback()
  {
    RollbackImpl rollback = new RollbackImpl();
    return rollback;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public List createList()
  {
    ListImpl list = new ListImpl();
    return list;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public IndependentUnit createIndependentUnit()
  {
    IndependentUnitImpl independentUnit = new IndependentUnitImpl();
    return independentUnit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConditionalUnit createConditionalUnit()
  {
    ConditionalUnitImpl conditionalUnit = new ConditionalUnitImpl();
    return conditionalUnit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PriorityUnit createPriorityUnit()
  {
    PriorityUnitImpl priorityUnit = new PriorityUnitImpl();
    return priorityUnit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public IteratedUnit createIteratedUnit()
  {
    IteratedUnitImpl iteratedUnit = new IteratedUnitImpl();
    return iteratedUnit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public LoopUnit createLoopUnit()
  {
    LoopUnitImpl loopUnit = new LoopUnitImpl();
    return loopUnit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Parameter createParameter()
  {
    ParameterImpl parameter = new ParameterImpl();
    return parameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ParameterType createParameterType()
  {
    ParameterTypeImpl parameterType = new ParameterTypeImpl();
    return parameterType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Expression createExpression()
  {
    ExpressionImpl expression = new ExpressionImpl();
    return expression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Rule createRule()
  {
    RuleImpl rule = new RuleImpl();
    return rule;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Unit createUnit()
  {
    UnitImpl unit = new UnitImpl();
    return unit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ORorXOR createORorXOR()
  {
    ORorXORImpl oRorXOR = new ORorXORImpl();
    return oRorXOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public AND createAND()
  {
    ANDImpl and = new ANDImpl();
    return and;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Not createNot()
  {
    NotImpl not = new NotImpl();
    return not;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConditionGraphRef createConditionGraphRef()
  {
    ConditionGraphRefImpl conditionGraphRef = new ConditionGraphRefImpl();
    return conditionGraphRef;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Call createCall()
  {
    CallImpl call = new CallImpl();
    return call;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public OrExpression createOrExpression()
  {
    OrExpressionImpl orExpression = new OrExpressionImpl();
    return orExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public AndExpression createAndExpression()
  {
    AndExpressionImpl andExpression = new AndExpressionImpl();
    return andExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EqualityExpression createEqualityExpression()
  {
    EqualityExpressionImpl equalityExpression = new EqualityExpressionImpl();
    return equalityExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ComparisonExpression createComparisonExpression()
  {
    ComparisonExpressionImpl comparisonExpression = new ComparisonExpressionImpl();
    return comparisonExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PlusExpression createPlusExpression()
  {
    PlusExpressionImpl plusExpression = new PlusExpressionImpl();
    return plusExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MinusExpression createMinusExpression()
  {
    MinusExpressionImpl minusExpression = new MinusExpressionImpl();
    return minusExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MulOrDivExpression createMulOrDivExpression()
  {
    MulOrDivExpressionImpl mulOrDivExpression = new MulOrDivExpressionImpl();
    return mulOrDivExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public BracketExpression createBracketExpression()
  {
    BracketExpressionImpl bracketExpression = new BracketExpressionImpl();
    return bracketExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotExpression createNotExpression()
  {
    NotExpressionImpl notExpression = new NotExpressionImpl();
    return notExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ParameterValue createParameterValue()
  {
    ParameterValueImpl parameterValue = new ParameterValueImpl();
    return parameterValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public JavaClassValue createJavaClassValue()
  {
    JavaClassValueImpl javaClassValue = new JavaClassValueImpl();
    return javaClassValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public JavaAttributeValue createJavaAttributeValue()
  {
    JavaAttributeValueImpl javaAttributeValue = new JavaAttributeValueImpl();
    return javaAttributeValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public StringValue createStringValue()
  {
    StringValueImpl stringValue = new StringValueImpl();
    return stringValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NumberValue createNumberValue()
  {
    NumberValueImpl numberValue = new NumberValueImpl();
    return numberValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public IntegerValue createIntegerValue()
  {
    IntegerValueImpl integerValue = new IntegerValueImpl();
    return integerValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NaturalValue createNaturalValue()
  {
    NaturalValueImpl naturalValue = new NaturalValueImpl();
    return naturalValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public BoolValue createBoolValue()
  {
    BoolValueImpl boolValue = new BoolValueImpl();
    return boolValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ParameterKind createParameterKindFromString(EDataType eDataType, String initialValue)
  {
    ParameterKind result = ParameterKind.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertParameterKindToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type createTypeFromString(EDataType eDataType, String initialValue)
  {
    Type result = Type.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertTypeToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Henshin_textPackage getHenshin_textPackage()
  {
    return (Henshin_textPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static Henshin_textPackage getPackage()
  {
    return Henshin_textPackage.eINSTANCE;
  }

} //Henshin_textFactoryImpl
