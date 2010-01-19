/**
 * <copyright>
 * </copyright>
 *
 * $Id: HenshinFactoryImpl.java,v 1.1 2009/10/28 10:38:14 enrico Exp $
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.henshin.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HenshinFactoryImpl extends EFactoryImpl implements HenshinFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HenshinFactory init() {
		try {
			HenshinFactory theHenshinFactory = (HenshinFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/emf/2010/Henshin"); 
			if (theHenshinFactory != null) {
				return theHenshinFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new HenshinFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HenshinFactoryImpl() {
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
			case HenshinPackage.NAMED_ELEMENT: return createNamedElement();
			case HenshinPackage.TRANSFORMATION_SYSTEM: return createTransformationSystem();
			case HenshinPackage.RULE: return createRule();
			case HenshinPackage.ATTRIBUTE_CONDITION: return createAttributeCondition();
			case HenshinPackage.VARIABLE: return createVariable();
			case HenshinPackage.PORT_OBJECT: return createPortObject();
			case HenshinPackage.PORT_PARAMETER: return createPortParameter();
			case HenshinPackage.GRAPH: return createGraph();
			case HenshinPackage.MAPPING: return createMapping();
			case HenshinPackage.NODE: return createNode();
			case HenshinPackage.ATTRIBUTE: return createAttribute();
			case HenshinPackage.EDGE: return createEdge();
			case HenshinPackage.TRANSFORMATION: return createTransformation();
			case HenshinPackage.INDEPENDENT_UNIT: return createIndependentUnit();
			case HenshinPackage.SEQUENTIAL_UNIT: return createSequentialUnit();
			case HenshinPackage.CONDITIONAL_UNIT: return createConditionalUnit();
			case HenshinPackage.PRIORITY_UNIT: return createPriorityUnit();
			case HenshinPackage.SINGLE_UNIT: return createSingleUnit();
			case HenshinPackage.AMALGAMATED_UNIT: return createAmalgamatedUnit();
			case HenshinPackage.COUNTED_UNIT: return createCountedUnit();
			case HenshinPackage.NESTED_CONDITION: return createNestedCondition();
			case HenshinPackage.FORMULA: return createFormula();
			case HenshinPackage.UNARY_FORMULA: return createUnaryFormula();
			case HenshinPackage.BINARY_FORMULA: return createBinaryFormula();
			case HenshinPackage.AND: return createAnd();
			case HenshinPackage.OR: return createOr();
			case HenshinPackage.NOT: return createNot();
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
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case HenshinPackage.PORT_KIND:
				return createPortKindFromString(eDataType, initialValue);
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
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case HenshinPackage.PORT_KIND:
				return convertPortKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedElement createNamedElement() {
		NamedElementImpl namedElement = new NamedElementImpl();
		return namedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationSystem createTransformationSystem() {
		TransformationSystemImpl transformationSystem = new TransformationSystemImpl();
		return transformationSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule createRule() {
		RuleImpl rule = new RuleImpl();
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeCondition createAttributeCondition() {
		AttributeConditionImpl attributeCondition = new AttributeConditionImpl();
		return attributeCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable createVariable() {
		VariableImpl variable = new VariableImpl();
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PortObject createPortObject() {
		PortObjectImpl portObject = new PortObjectImpl();
		return portObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PortParameter createPortParameter() {
		PortParameterImpl portParameter = new PortParameterImpl();
		return portParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph createGraph() {
		GraphImpl graph = new GraphImpl();
		return graph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Mapping createMapping() {
		MappingImpl mapping = new MappingImpl();
		return mapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node createNode() {
		NodeImpl node = new NodeImpl();
		return node;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.HenshinFactory#createNode(org.eclipse.emf.henshin.model.Graph, org.eclipse.emf.ecore.EClass)
	 */
	public Node createNode(Graph graph, EClass type) {
		Node node = createNode();
		node.setType(type);
		graph.getNodes().add(node);
		return node;
	}// createNode

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge createEdge() {
		EdgeImpl edge = new EdgeImpl();
		return edge;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.HenshinFactory#createEdge(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.henshin.model.Node, org.eclipse.emf.ecore.EReference)
	 */
	public Edge createEdge(Node source, Node target, EReference type) {
		Edge edge = createEdge();
		edge.setSource(source);
		edge.setTarget(target);
		edge.setType(type);
		edge.setGraph(source.getGraph());
		return edge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transformation createTransformation() {
		TransformationImpl transformation = new TransformationImpl();
		return transformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndependentUnit createIndependentUnit() {
		IndependentUnitImpl independentUnit = new IndependentUnitImpl();
		return independentUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SequentialUnit createSequentialUnit() {
		SequentialUnitImpl sequentialUnit = new SequentialUnitImpl();
		return sequentialUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionalUnit createConditionalUnit() {
		ConditionalUnitImpl conditionalUnit = new ConditionalUnitImpl();
		return conditionalUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PriorityUnit createPriorityUnit() {
		PriorityUnitImpl priorityUnit = new PriorityUnitImpl();
		return priorityUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SingleUnit createSingleUnit() {
		SingleUnitImpl singleUnit = new SingleUnitImpl();
		return singleUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AmalgamatedUnit createAmalgamatedUnit() {
		AmalgamatedUnitImpl amalgamatedUnit = new AmalgamatedUnitImpl();
		return amalgamatedUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CountedUnit createCountedUnit() {
		CountedUnitImpl countedUnit = new CountedUnitImpl();
		return countedUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedCondition createNestedCondition() {
		NestedConditionImpl nestedCondition = new NestedConditionImpl();
		return nestedCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Formula createFormula() {
		FormulaImpl formula = new FormulaImpl();
		return formula;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryFormula createUnaryFormula() {
		UnaryFormulaImpl unaryFormula = new UnaryFormulaImpl();
		return unaryFormula;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryFormula createBinaryFormula() {
		BinaryFormulaImpl binaryFormula = new BinaryFormulaImpl();
		return binaryFormula;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public And createAnd() {
		AndImpl and = new AndImpl();
		return and;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Or createOr() {
		OrImpl or = new OrImpl();
		return or;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Not createNot() {
		NotImpl not = new NotImpl();
		return not;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PortKind createPortKindFromString(EDataType eDataType, String initialValue) {
		PortKind result = PortKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPortKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HenshinPackage getHenshinPackage() {
		return (HenshinPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static HenshinPackage getPackage() {
		return HenshinPackage.eINSTANCE;
	}

} //HenshinFactoryImpl
