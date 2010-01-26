/**
 * <copyright>
 * </copyright>
 *
 * $Id: HenshinSwitch.java,v 1.1 2009/10/28 10:38:17 enrico Exp $
 */
package org.eclipse.emf.henshin.model.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.henshin.model.*;

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
 * @see org.eclipse.emf.henshin.model.HenshinPackage
 * @generated
 */
public class HenshinSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static HenshinPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HenshinSwitch() {
		if (modelPackage == null) {
			modelPackage = HenshinPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case HenshinPackage.NAMED_ELEMENT: {
				NamedElement namedElement = (NamedElement)theEObject;
				T result = caseNamedElement(namedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.DESCRIBED_ELEMENT: {
				DescribedElement describedElement = (DescribedElement)theEObject;
				T result = caseDescribedElement(describedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.TRANSFORMATION_SYSTEM: {
				TransformationSystem transformationSystem = (TransformationSystem)theEObject;
				T result = caseTransformationSystem(transformationSystem);
				if (result == null) result = caseDescribedElement(transformationSystem);
				if (result == null) result = caseNamedElement(transformationSystem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.RULE: {
				Rule rule = (Rule)theEObject;
				T result = caseRule(rule);
				if (result == null) result = caseDescribedElement(rule);
				if (result == null) result = caseNamedElement(rule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.ATTRIBUTE_CONDITION: {
				AttributeCondition attributeCondition = (AttributeCondition)theEObject;
				T result = caseAttributeCondition(attributeCondition);
				if (result == null) result = caseDescribedElement(attributeCondition);
				if (result == null) result = caseNamedElement(attributeCondition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.VARIABLE: {
				Variable variable = (Variable)theEObject;
				T result = caseVariable(variable);
				if (result == null) result = caseDescribedElement(variable);
				if (result == null) result = caseNamedElement(variable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.PORT: {
				Port port = (Port)theEObject;
				T result = casePort(port);
				if (result == null) result = caseDescribedElement(port);
				if (result == null) result = caseNamedElement(port);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.PORT_OBJECT: {
				PortObject portObject = (PortObject)theEObject;
				T result = casePortObject(portObject);
				if (result == null) result = casePort(portObject);
				if (result == null) result = caseDescribedElement(portObject);
				if (result == null) result = caseNamedElement(portObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.PORT_PARAMETER: {
				PortParameter portParameter = (PortParameter)theEObject;
				T result = casePortParameter(portParameter);
				if (result == null) result = casePort(portParameter);
				if (result == null) result = caseDescribedElement(portParameter);
				if (result == null) result = caseNamedElement(portParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.GRAPH: {
				Graph graph = (Graph)theEObject;
				T result = caseGraph(graph);
				if (result == null) result = caseNamedElement(graph);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.GRAPH_ELEMENT: {
				GraphElement graphElement = (GraphElement)theEObject;
				T result = caseGraphElement(graphElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.MAPPING: {
				Mapping mapping = (Mapping)theEObject;
				T result = caseMapping(mapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.NODE: {
				Node node = (Node)theEObject;
				T result = caseNode(node);
				if (result == null) result = caseNamedElement(node);
				if (result == null) result = caseGraphElement(node);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.ATTRIBUTE: {
				Attribute attribute = (Attribute)theEObject;
				T result = caseAttribute(attribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.EDGE: {
				Edge edge = (Edge)theEObject;
				T result = caseEdge(edge);
				if (result == null) result = caseGraphElement(edge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.TRANSFORMATION: {
				Transformation transformation = (Transformation)theEObject;
				T result = caseTransformation(transformation);
				if (result == null) result = caseDescribedElement(transformation);
				if (result == null) result = caseNamedElement(transformation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.TRANSFORMATION_UNIT: {
				TransformationUnit transformationUnit = (TransformationUnit)theEObject;
				T result = caseTransformationUnit(transformationUnit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.INDEPENDENT_UNIT: {
				IndependentUnit independentUnit = (IndependentUnit)theEObject;
				T result = caseIndependentUnit(independentUnit);
				if (result == null) result = caseTransformationUnit(independentUnit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.SEQUENTIAL_UNIT: {
				SequentialUnit sequentialUnit = (SequentialUnit)theEObject;
				T result = caseSequentialUnit(sequentialUnit);
				if (result == null) result = caseTransformationUnit(sequentialUnit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.CONDITIONAL_UNIT: {
				ConditionalUnit conditionalUnit = (ConditionalUnit)theEObject;
				T result = caseConditionalUnit(conditionalUnit);
				if (result == null) result = caseTransformationUnit(conditionalUnit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.PRIORITY_UNIT: {
				PriorityUnit priorityUnit = (PriorityUnit)theEObject;
				T result = casePriorityUnit(priorityUnit);
				if (result == null) result = caseTransformationUnit(priorityUnit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.SINGLE_UNIT: {
				SingleUnit singleUnit = (SingleUnit)theEObject;
				T result = caseSingleUnit(singleUnit);
				if (result == null) result = caseTransformationUnit(singleUnit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.AMALGAMATED_UNIT: {
				AmalgamatedUnit amalgamatedUnit = (AmalgamatedUnit)theEObject;
				T result = caseAmalgamatedUnit(amalgamatedUnit);
				if (result == null) result = caseTransformationUnit(amalgamatedUnit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.COUNTED_UNIT: {
				CountedUnit countedUnit = (CountedUnit)theEObject;
				T result = caseCountedUnit(countedUnit);
				if (result == null) result = caseTransformationUnit(countedUnit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.NESTED_CONDITION: {
				NestedCondition nestedCondition = (NestedCondition)theEObject;
				T result = caseNestedCondition(nestedCondition);
				if (result == null) result = caseFormula(nestedCondition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.FORMULA: {
				Formula formula = (Formula)theEObject;
				T result = caseFormula(formula);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.UNARY_FORMULA: {
				UnaryFormula unaryFormula = (UnaryFormula)theEObject;
				T result = caseUnaryFormula(unaryFormula);
				if (result == null) result = caseFormula(unaryFormula);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.BINARY_FORMULA: {
				BinaryFormula binaryFormula = (BinaryFormula)theEObject;
				T result = caseBinaryFormula(binaryFormula);
				if (result == null) result = caseFormula(binaryFormula);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.AND: {
				And and = (And)theEObject;
				T result = caseAnd(and);
				if (result == null) result = caseBinaryFormula(and);
				if (result == null) result = caseFormula(and);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.OR: {
				Or or = (Or)theEObject;
				T result = caseOr(or);
				if (result == null) result = caseBinaryFormula(or);
				if (result == null) result = caseFormula(or);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinPackage.NOT: {
				Not not = (Not)theEObject;
				T result = caseNot(not);
				if (result == null) result = caseUnaryFormula(not);
				if (result == null) result = caseFormula(not);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Described Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Described Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDescribedElement(DescribedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transformation System</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transformation System</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransformationSystem(TransformationSystem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRule(Rule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeCondition(AttributeCondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Port</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Port</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePort(Port object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Port Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Port Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePortObject(PortObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Port Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Port Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePortParameter(PortParameter object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Graph Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Graph Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGraphElement(GraphElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapping(Mapping object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttribute(Attribute object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transformation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransformation(Transformation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transformation Unit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transformation Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransformationUnit(TransformationUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Independent Unit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Independent Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndependentUnit(IndependentUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sequential Unit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sequential Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSequentialUnit(SequentialUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Conditional Unit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conditional Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditionalUnit(ConditionalUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Priority Unit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Priority Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePriorityUnit(PriorityUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Single Unit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Single Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSingleUnit(SingleUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Amalgamated Unit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Amalgamated Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAmalgamatedUnit(AmalgamatedUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Counted Unit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Counted Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCountedUnit(CountedUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Nested Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Nested Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNestedCondition(NestedCondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Formula</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFormula(Formula object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unary Formula</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unary Formula</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnaryFormula(UnaryFormula object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binary Formula</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binary Formula</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBinaryFormula(BinaryFormula object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>And</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>And</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnd(And object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Or</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Or</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOr(Or object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Not</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Not</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNot(Not object) {
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
	public T defaultCase(EObject object) {
		return null;
	}

} //HenshinSwitch
