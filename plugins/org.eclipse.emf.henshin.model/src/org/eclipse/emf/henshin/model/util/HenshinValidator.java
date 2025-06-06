/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.henshin.HenshinModelPlugin;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.MultiUnit;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.True;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.emf.henshin.model.UnaryUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.Xor;

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the Henshin model. <!--
 * end-user-doc -->
 * @see org.eclipse.emf.henshin.model.HenshinPackage
 * @generated
 */
public class HenshinValidator extends EObjectValidator {

	/**
	 * The cached model package
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final HenshinValidator INSTANCE = new HenshinValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.emf.henshin.model";

	/**
	 * A constant with a fixed name that can be used as the base value for
	 * additional hand written constants. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * JavaScript engine wrapper for validating expressions.
	 */
	private static final ScriptEngineWrapper SCRIPT_ENGINE = new ScriptEngineWrapper(new String[0]);

	private static final String CONTAINMENT_CYCLES_KEY = new String("CONTAINMENT_CYCLES");

	public static final String PREF_ENABLE_EXTENDED_CONSISTENCY_CHECK = "Global.enableExtendedConsistencyCheck";

	public static final String PREF_SUPPRESS_PARAMETERKIND_DEPRECATED_WARNINGS = "Global.suppressParameterKindDeprecatedWarnings";

	private static final Set<String> JAVA_KEYWORDS = new HashSet<String>(Arrays.asList(new String[] { "abstract",
			"continue", "for", "new", "switch", "assert", "default", "goto", "package", "synchronized", "boolean", "do",
			"if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import",
			"public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short",
			"try", "char", "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile",
			"const", "float", "native", "super", "while" }));

	private static final Set<String> JAVASCRIPT_KEYWORDS = new HashSet<String>(Arrays.asList(new String[] { "abstract",
			"arguments", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "debugger",
			"default", "delete", "do", "double", "else", "enum", "eval", "export", "extends", "false", "final",
			"finally", "float", "for", "function", "goto", "if", "implements", "import", "in", "instanceof", "int",
			"interface", "let", "long", "native", "new", "null", "package", "private", "protected", "public", "return",
			"short", "static", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try",
			"typeof", "var", "void", "volatile", "while", "with", "yield" }));

	private Set<Unit> unitsWithIllegallyNamedParameters = new HashSet<Unit>();

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public HenshinValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return HenshinPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		switch (classifierID) {
			case HenshinPackage.MODEL_ELEMENT:
				return validateModelElement((ModelElement)value, diagnostics, context);
			case HenshinPackage.ANNOTATION:
				return validateAnnotation((Annotation)value, diagnostics, context);
			case HenshinPackage.NAMED_ELEMENT:
				return validateNamedElement((NamedElement)value, diagnostics, context);
			case HenshinPackage.GRAPH_ELEMENT:
				return validateGraphElement((GraphElement)value, diagnostics, context);
			case HenshinPackage.MODULE:
				return validateModule((Module)value, diagnostics, context);
			case HenshinPackage.UNIT:
				return validateUnit((Unit)value, diagnostics, context);
			case HenshinPackage.RULE:
				return validateRule((Rule)value, diagnostics, context);
			case HenshinPackage.PARAMETER:
				return validateParameter((Parameter)value, diagnostics, context);
			case HenshinPackage.PARAMETER_MAPPING:
				return validateParameterMapping((ParameterMapping)value, diagnostics, context);
			case HenshinPackage.GRAPH:
				return validateGraph((Graph)value, diagnostics, context);
			case HenshinPackage.NODE:
				return validateNode((Node)value, diagnostics, context);
			case HenshinPackage.EDGE:
				return validateEdge((Edge)value, diagnostics, context);
			case HenshinPackage.ATTRIBUTE:
				return validateAttribute((Attribute)value, diagnostics, context);
			case HenshinPackage.ATTRIBUTE_CONDITION:
				return validateAttributeCondition((AttributeCondition)value, diagnostics, context);
			case HenshinPackage.MAPPING:
				return validateMapping((Mapping)value, diagnostics, context);
			case HenshinPackage.UNARY_UNIT:
				return validateUnaryUnit((UnaryUnit)value, diagnostics, context);
			case HenshinPackage.MULTI_UNIT:
				return validateMultiUnit((MultiUnit)value, diagnostics, context);
			case HenshinPackage.INDEPENDENT_UNIT:
				return validateIndependentUnit((IndependentUnit)value, diagnostics, context);
			case HenshinPackage.SEQUENTIAL_UNIT:
				return validateSequentialUnit((SequentialUnit)value, diagnostics, context);
			case HenshinPackage.CONDITIONAL_UNIT:
				return validateConditionalUnit((ConditionalUnit)value, diagnostics, context);
			case HenshinPackage.PRIORITY_UNIT:
				return validatePriorityUnit((PriorityUnit)value, diagnostics, context);
			case HenshinPackage.ITERATED_UNIT:
				return validateIteratedUnit((IteratedUnit)value, diagnostics, context);
			case HenshinPackage.LOOP_UNIT:
				return validateLoopUnit((LoopUnit)value, diagnostics, context);
			case HenshinPackage.FORMULA:
				return validateFormula((Formula)value, diagnostics, context);
			case HenshinPackage.NESTED_CONDITION:
				return validateNestedCondition((NestedCondition)value, diagnostics, context);
			case HenshinPackage.UNARY_FORMULA:
				return validateUnaryFormula((UnaryFormula)value, diagnostics, context);
			case HenshinPackage.BINARY_FORMULA:
				return validateBinaryFormula((BinaryFormula)value, diagnostics, context);
			case HenshinPackage.AND:
				return validateAnd((And)value, diagnostics, context);
			case HenshinPackage.OR:
				return validateOr((Or)value, diagnostics, context);
			case HenshinPackage.XOR:
				return validateXor((Xor)value, diagnostics, context);
			case HenshinPackage.NOT:
				return validateNot((Not)value, diagnostics, context);
			case HenshinPackage.TRUE:
				return validateTrue((True)value, diagnostics, context);
			case HenshinPackage.PARAMETER_KIND:
				return validateParameterKind((ParameterKind)value, diagnostics, context);
			case HenshinPackage.ACTION:
				return validateAction((Action)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateModelElement(ModelElement modelElement, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(modelElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAnnotation(Annotation annotation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(annotation, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement(NamedElement namedElement, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(namedElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRule(Rule rule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(rule, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_nameNotEmpty(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterNamesUnique(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterMappingsPointToDirectSubUnit(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_mappingsFromLeft2Right(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_createdNodesNotAbstract(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_createdEdgesNotDerived(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_deletedEdgesNotDerived(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_uniqueNodeNames(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_varParametersOccurOnLeftSide(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_multiRuleParametersSameKind(rule, diagnostics, context);
		return result;
	}

	/**
	 * Validates the mappingsFromLeft2Right constraint of '<em>Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateRule_mappingsFromLeft2Right(Rule rule, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = true;
		for (Mapping m : rule.getMappings()) {
			if (m.getOrigin() != null && m.getImage() != null
					&& (!m.getOrigin().getGraph().isLhs() || !m.getImage().getGraph().isRhs())) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, m, Rule.class, "mappingsFromLeft2Right", context));
				result = false;
			}
		}
		return result;
	}

	/**
	 * Validates the createdNodesNotAbstract constraint of '<em>Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateRule_createdNodesNotAbstract(Rule rule, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = true;
		for (Node node : rule.getRhs().getNodes()) {
			if (node.getType() != null && node.getType().isAbstract()) {
				if (rule.getMappings().getOrigin(node) == null) {
					diagnostics.add(
							createDiagnostic(Diagnostic.ERROR, node, Rule.class, "createdNodesNotAbstract", context));
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * Validates the createdEdgesNotDerived constraint of '<em>Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateRule_createdEdgesNotDerived(Rule rule, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = true;
		for (Edge edge : rule.getRhs().getEdges()) {
			if (edge.getType() != null && edge.getType().isDerived()) {
				if (rule.getMappings().getOrigin(edge) == null) {
					diagnostics.add(
							createDiagnostic(Diagnostic.ERROR, edge, Rule.class, "createdEdgesNotDerived", context));
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * Validates the deletedEdgesNotDerived constraint of '<em>Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateRule_deletedEdgesNotDerived(Rule rule, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = true;
		for (Edge edge : rule.getLhs().getEdges()) {
			if (edge.getType() != null && edge.getType().isDerived()) {
				if (rule.getMappings().getImage(edge, rule.getRhs()) == null) {
					diagnostics.add(
							createDiagnostic(Diagnostic.ERROR, edge, Rule.class, "deletedEdgesNotDerived", context));
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * Validates the uniqueNodeNames constraint of '<em>Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateRule_uniqueNodeNames(Rule rule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = true;
		EList<Node> nodes = rule.getActionNodes(null);

		if (nodes != null) {
			int size = nodes.size();
			for (int i = 0; i < size; i++) {
				Node n1 = nodes.get(i);
				if (n1.getName() != null && n1.getName().trim().length() != 0) {
					for (int j = i + 1; j < size; j++) {
						Node n2 = nodes.get(j);
						if (n2.getName() != null && n1.getName().trim().equals(n2.getName().trim())
								&& n1.getGraph().equals(n2.getGraph())) {
							diagnostics.add(
									createDiagnostic(Diagnostic.ERROR, n1, Rule.class, "uniqueNodeNames", context));
							diagnostics.add(
									createDiagnostic(Diagnostic.ERROR, n2, Rule.class, "uniqueNodeNames", context));
							result = false;
						}
					}
				}
			}
		}
		return result;
	}

	private List<Parameter> getVarParameters(Unit unit) {
		EList<Parameter> parameterList = unit.getParameters();
		List<Parameter> varParameterList = new ArrayList<Parameter>();

		for (Parameter param : parameterList) {
			if (param.getKind() == ParameterKind.VAR) {
				varParameterList.add(param);
			}
		}

		return varParameterList;
	}

	private boolean parameterContainedInRule(Rule rule, String paramName) {
		EList<Edge> edges = rule.getLhs().getEdges();
		EList<Node> nodes = rule.getLhs().getNodes();
		EList<NestedCondition> ncs = rule.getLhs().getNestedConditions();

		return parameterContainedInEdges(edges, paramName) || parameterContainedInNodes(nodes, paramName)
				|| parameterContainedInNestedConditions(ncs, paramName);
	}

	private boolean parameterContainedInEdges(EList<Edge> edges, String paramName) {
		boolean containsParam = false;

		for (Edge edge : edges) {
			if (edge.getIndex() != null && edge.getIndex().equals(paramName)) {
				containsParam = true;
				break;
			}
		}

		return containsParam;
	}

	private boolean parameterContainedInNodes(EList<Node> nodes, String paramName) {
		boolean containsParam = false;

		for (Node node : nodes) {
			if (node.getName() != null && node.getName().equals(paramName)) {
				containsParam = true;
				break;
			}
			EList<Attribute> attributes = node.getAttributes();
			for (Attribute attribute : attributes) {
				if (attribute.getValue().equals(paramName)) {
					containsParam = true;
					break;
				}
			}
			if (containsParam) {
				break;
			}
		}

		return containsParam;
	}

	private boolean parameterContainedInNestedConditions(EList<NestedCondition> ncs, String paramName) {
		boolean containsParam = false;

		for (NestedCondition nc : ncs) {
			Graph conclusion = nc.getConclusion();
			EList<Edge> edges = conclusion.getEdges();
			EList<Node> nodes = conclusion.getNodes();

			containsParam = parameterContainedInEdges(edges, paramName) || parameterContainedInNodes(nodes, paramName)
					|| parameterContainedInNestedConditions(conclusion.getNestedConditions(), paramName);

			if (containsParam) {
				break;
			}
		}
		return containsParam;
	}

	/**
	 * Validates the varParametersOccurOnLeftSide constraint of '<em>Rule</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateRule_varParametersOccurOnLeftSide(Rule rule, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		// Multirules are considered together with their kernel rule
		if (rule.isMultiRule()) {
			return true;
		}

		boolean result = true;
		List<Parameter> varParameterList = getVarParameters(rule);

		for (Parameter param : varParameterList) {
			String paramName = param.getName();
			boolean lhsContainsParam = false;

			// Check if the parameter is contained in the LHS of the rule itself
			if (parameterContainedInRule(rule, paramName)) {
				lhsContainsParam = true;
				break;
			}

			// Check if multirules contain the parameter
			for (Rule multiRule : rule.getAllMultiRules()) {
				if (parameterContainedInRule(multiRule, paramName)) {
					lhsContainsParam = true;
					break;
				}
			}

			if (!lhsContainsParam) {
				diagnostics.add(
						createDiagnostic(Diagnostic.ERROR, param, Rule.class, "varParametersOccurOnLeftSide", context));
				result = false;
			}
		}

		return result;
	}

	/**
	 * Validates the multiRuleParametersSameKind constraint of '<em>Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateRule_multiRuleParametersSameKind(Rule rule, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = true;

		for (Rule multiRule : rule.getMultiRules()) {
			EList<Parameter> multiRuleParams = multiRule.getParameters();

			for (Parameter param : multiRuleParams) {
				Parameter kernelParam = rule.getParameter(param.getName());
				if (kernelParam != null && param.getKind() != kernelParam.getKind()) {
					diagnostics
							.add(createDiagnostic(Diagnostic.ERROR, param, Rule.class, "sameParameterKinds", context));
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * Validates the modeling of opposite '<em>Edge</em>'s. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateEdge_oppositeEdgeConsidered(Edge edge, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!isExtendedConsistencyCheck()) {
			return true;
		}
		if (edge.getType() != null) {
			EReference eOpposite = edge.getType().getEOpposite();
			if (eOpposite != null) {
				EList<Edge> allOutgoingEdgesOfTargetNode = edge.getTarget().getOutgoing();
				for (Edge outgoingEdgeOfTargetNode : allOutgoingEdgesOfTargetNode) {
					if (outgoingEdgeOfTargetNode.getTarget() == edge.getSource()) {
						if (outgoingEdgeOfTargetNode.getType() == eOpposite)
							return true;
					}
				}
				diagnostics
						.add(createDiagnostic(Diagnostic.WARNING, edge, Edge.class, "oppositeEdgeConsidered", context));
				return false;
			}
		}
		return true;
	}

	/**
	 * Validates to have no parallel '<em>Edge</em>'s of the same type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateEdge_noParallelEdgesOfSameType(Edge edge, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (edge.getSource() != null) {
			for (Edge outgoindEdgesOfSourceNode : edge.getSource().getOutgoing()) {
				if (outgoindEdgesOfSourceNode != edge) {
					if (outgoindEdgesOfSourceNode.getTarget() == edge.getTarget()
							&& outgoindEdgesOfSourceNode.getType() == edge.getType())
						diagnostics.add(createDiagnostic(Diagnostic.WARNING, edge, Edge.class,
								"noParallelEdgesOfSameType", context));
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Validates the deletion of containment '<em>Edge</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateEdge_containmentEdgeDeletion(Edge edge, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!isExtendedConsistencyCheck()) {
			return true;
		}
		Rule rule = edge.getGraph().getRule();
		MappingList mappings = rule.getMappings();
		if (rule.getLhs().getEdges().contains(edge)) {
			if (edge.getType() != null && edge.getType().isContainment()) {
				if (edge.getActionEdge().getAction().getType() == Action.Type.DELETE) {
					Node targetNodeInRhs = mappings.getImage(edge.getTarget(), rule.getRhs());
					boolean targetNodeDeleted = targetNodeInRhs == null;
					// the deletion of the contained node solves the deletion
					// of the containment edge
					boolean newContainmentEdgeforNodeCreated = false;
					// the creation of a new containment edge solves the missing
					// container problem
					if (!targetNodeDeleted) { // required to prevent NPE due to
												// targetNodeInRhs=null
						for (Edge edgeOfRhs : targetNodeInRhs.getIncoming()) {
							if (edgeOfRhs.getType() != null && edgeOfRhs.getType().isContainment()) {
								if (mappings.getOrigin(edgeOfRhs) == null) {
									newContainmentEdgeforNodeCreated = true;
								}
							}
						}
					}
					if ((!targetNodeDeleted && !newContainmentEdgeforNodeCreated)) {
						diagnostics.add(createDiagnostic(Diagnostic.WARNING, edge, Edge.class,
								"containmentEdgeDeletion", context));
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Validates the creation of containment '<em>Edge</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateEdge_containmentEdgeCreation(Edge edge, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!isExtendedConsistencyCheck()) {
			return true;
		}
		Rule rule = edge.getGraph().getRule();
		MappingList mappings = rule.getMappings();
		if (rule.getRhs().getEdges().contains(edge)) { // only edges of the RHS
														// are relevant
			if (edge.getType() != null && edge.getType().isContainment()) {
				if (mappings.getOrigin(edge) == null) {
					Node targetNodeInLhs = mappings.getOrigin(edge.getTarget());
					boolean targetNodeCreated = targetNodeInLhs == null;
					// creation of the target node solves the created
					// containment edge
					boolean originalContainmentEdgeDeleted = false;
					// deletion of the old containment edge solves the creation
					// of a new // containment edge
					if (targetNodeInLhs != null) {
						for (Edge incomingEdgeOfTargetNodeInLhs : targetNodeInLhs.getIncoming()) {
							if (incomingEdgeOfTargetNodeInLhs.getType() != null
									&& incomingEdgeOfTargetNodeInLhs.getType().isContainment()) {
								if (mappings.getImage(incomingEdgeOfTargetNodeInLhs, rule.getRhs()) == null) {
									originalContainmentEdgeDeleted = true;
								}
							}
						}
					}
					if (!targetNodeCreated && !originalContainmentEdgeDeleted) {
						diagnostics.add(createDiagnostic(Diagnostic.WARNING, edge, Edge.class,
								"containmentEdgeCreation", context));
						return false;
					}
				}
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributeCondition(AttributeCondition attributeCondition, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(attributeCondition, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validateAttributeCondition_conditionTextNotEmpty(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validateAttributeCondition_conditionValidJavaScript(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validateAttributeCondition_conditionAllParametersAreDeclared(attributeCondition, diagnostics, context);
		return result;
	}

	/**
	 * Validates the conditionTextNotEmpty constraint of ' <em>Attribute
	 * Condition</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateAttributeCondition_conditionTextNotEmpty(AttributeCondition attributeCondition,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (attributeCondition.getConditionText() == null
				|| attributeCondition.getConditionText().trim().length() == 0) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, attributeCondition, AttributeCondition.class,
					"conditionTextNotEmpty", context));
			return false;
		}
		return true;
	}

	/**
	 * Validates the conditionValidJavaScript constraint of ' <em>Attribute
	 * Condition</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateAttributeCondition_conditionValidJavaScript(AttributeCondition attributeCondition,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		try {
			validateExpression(attributeCondition.getConditionText(), attributeCondition.getRule());
		} catch (ScriptException e) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, attributeCondition, AttributeCondition.class,
					"conditionValidJavaScript", context, e));
		}
		return true;
	}

	/**
	 * Validates the conditionAllParametersAreDeclared constraint of '
	 * <em>Attribute Condition</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated NOT
	 */
	public boolean validateAttributeCondition_conditionAllParametersAreDeclared(AttributeCondition attributeCondition,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (attributeCondition.getConditionText() != null && !attributeCondition.getConditionText().isEmpty()) {
			List<String> undeclared = findUndeclaredNames(attributeCondition.getConditionText(),
					attributeCondition.getRule());

			if (!undeclared.isEmpty()) {
				for (Node n : attributeCondition.getRule().getLhs().getNodes()) {
					for (Attribute  a : n.getAttributes()) {
						if (a.getType() != null && a.getType().getEType() instanceof EEnum) {
							for (EEnumLiteral lit : ((EEnum) a.getType().getEType()).getELiterals()) {
								undeclared.remove(lit.getName());
							}
						}
					}
				}
			}
			if (!undeclared.isEmpty()) {
				if (isJavaScriptExpression(attributeCondition.getConditionText()))
					diagnostics.add(createDiagnostic(Diagnostic.WARNING, attributeCondition, AttributeCondition.class,
							"conditionAllParametersAreDeclaredWarning", context,
							new RuntimeException(undeclared.get(0))));
				else
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, attributeCondition, AttributeCondition.class,
							"conditionAllParametersAreDeclared", context, new RuntimeException(undeclared.get(0))));
				return false;
			}
		}
		return true;
	}

	/*
	 * Validate a JavaScript expression. Throws a ScriptException on validation
	 * errors.
	 */
	private void validateExpression(String expression, Unit unit) throws ScriptException {
		// If the unit has invalid parameter names (like keywords), this is the primary
		// error; directly return to avoid additional feedback that may confuse the user
		if (unitsWithIllegallyNamedParameters.contains(unit))
			return;

		if (expression == null || unit == null) {
			return;
		}
		expression = expression.trim();
		if (expression.length() == 0) {
			return;
		}
		if (SCRIPT_ENGINE.getEngine() == null) {
			return;
		}
		StringBuilder function = new StringBuilder();
		function.append("function _validate_expr(");
		int paramCount = unit.getParameters().size();
		for (int i = 0; i < paramCount; i++) {
			Parameter param = unit.getParameters().get(i);
			if (param.getName() == null || param.getName().trim().length() == 0) {
				return;
			}
			if (!JAVASCRIPT_KEYWORDS.contains(param.getName()) && !JAVA_KEYWORDS.contains(param.getName())) {
				function.append(param.getName().trim());
				if (i < paramCount - 2) {
					function.append(", ");
				}
			}

		}
		function.append(") {\n");
		function.append("return\n" + expression + ";\n");
		function.append("}\n");
		// System.out.println(function);
		synchronized (SCRIPT_ENGINE) {
			try {
				SCRIPT_ENGINE.eval(function.toString(),
						(unit instanceof Rule) ? ((Rule) unit).getAllJavaImports() : new ArrayList<String>());
			} catch (ScriptException e) {
				String msg = (e.getMessage() != null)
						? e.getMessage().replaceFirst(
								Pattern.quote("<eval>:" + e.getLineNumber() + ":" + e.getColumnNumber()),
								"position " + (e.getLineNumber() - 2) + ":" + e.getColumnNumber() + ":")
						: "unknown error";
				throw new ScriptException(msg, e.getFileName(), 1, e.getColumnNumber());
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateParameter(Parameter parameter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(parameter, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateParameter_nameNotEmpty(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateParameter_nameNotTypeName(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateParameter_nameNotKindAlias(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateParameter_unknownKindDeprecated(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateParameter_nameNotKeyword(parameter, diagnostics, context);
		return result;
	}

	/**
	 * Validates the nameNotEmpty constraint of '<em>Parameter</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateParameter_nameNotEmpty(Parameter parameter, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (parameter.getName() == null || parameter.getName().trim().length() == 0) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, parameter, Parameter.class, "nameNotEmpty", context));
			return false;
		}
		return true;
	}

	/**
	 * Validates the nameNotTypeName constraint of '<em>Parameter</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateParameter_nameNotTypeName(Parameter parameter, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (parameter.getName() == null) {
			return true;
		}
		Unit unit = parameter.getUnit();
		if (unit == null) {
			return true;
		}
		Module module = unit.getModule();
		if (module == null) {
			return true;
		}
		for (EPackage epackage : module.getImports()) {
			if (validateParameter_nameNotTypeName(parameter, diagnostics, context, epackage)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Validates the nameNotKindAlias constraint of '<em>Parameter</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateParameter_nameNotKindAlias(Parameter parameter, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (parameter != null && ParameterKind.getByString(parameter.getName()) != null) {
			diagnostics
					.add(createDiagnostic(Diagnostic.ERROR, parameter, Parameter.class, "nameNotKindAlias", context));
			return false;
		}
		return true;
	}

	/**
	 * Validates the unknownKindDeprecated constraint of '<em>Parameter</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateParameter_unknownKindDeprecated(Parameter parameter, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (isSuppressParameterKindDeprecatedWarnings())
			return true;

		EObject container = parameter.eContainer();
		if (parameter != null && parameter.getKind() == ParameterKind.UNKNOWN
				&& !(container instanceof Rule && ((Rule) container).isMultiRule())) {
			diagnostics.add(
					createDiagnostic(Diagnostic.WARNING, parameter, Parameter.class, "unknownKindDeprecated", context));
		}
		return true;
	}

	/*
	 * Helper method for validateParameter_nameNotTypeName.
	 */
	private boolean validateParameter_nameNotTypeName(Parameter parameter, DiagnosticChain diagnostics,
			Map<Object, Object> context, EPackage epackage) {
		for (EClassifier classifier : epackage.getEClassifiers()) {
			if (parameter.getName().equals(classifier.getName())) {
				diagnostics.add(
						createDiagnostic(Diagnostic.ERROR, parameter, Parameter.class, "nameNotTypeName", context));
				return false;
			}
		}
		for (EPackage sub : epackage.getESubpackages()) {
			if (!validateParameter_nameNotTypeName(parameter, diagnostics, context, sub)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Validates the nameNotKeyword constraint of '<em>Parameter</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateParameter_nameNotKeyword(Parameter parameter, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (parameter.getName() != null) {
			if (JAVA_KEYWORDS.contains(parameter.getName()) || JAVASCRIPT_KEYWORDS.contains(parameter.getName())) {
				diagnostics
						.add(createDiagnostic(Diagnostic.ERROR, parameter, Parameter.class, "nameNotKeyword", context));
				if (parameter.getUnit() != null) {
					unitsWithIllegallyNamedParameters.add(parameter.getUnit());
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGraph(Graph graph, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(graph, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGraphElement(GraphElement graphElement, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(graphElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateModule(Module module, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(module, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnit(Unit unit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(unit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(unit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_nameNotEmpty(unit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterNamesUnique(unit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterMappingsPointToDirectSubUnit(unit, diagnostics, context);
		return result;
	}

	/**
	 * Validates the nameNotEmpty constraint of '<em>Unit</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateUnit_nameNotEmpty(Unit unit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!(unit instanceof Rule) || (((Rule) unit).getKernelRule() == null)) {
			if (unit.getName() == null || unit.getName().trim().length() == 0) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, unit, Unit.class, "nameNotEmpty", context));
				return false;
			}
		}
		return true;
	}

	/**
	 * Validates the parameterNamesUnique constraint of '<em>Unit</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateUnit_parameterNamesUnique(Unit unit, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = true;
		for (Parameter p1 : unit.getParameters()) {
			if (p1.getName() != null && p1.getName().trim().length() != 0) {
				for (Parameter p2 : unit.getParameters()) {
					if (p1 == p2) {
						break;
					}
					if (p2.getName() != null && p1.getName().trim().equals(p2.getName().trim())) {
						diagnostics.add(
								createDiagnostic(Diagnostic.ERROR, p2, Unit.class, "parameterNamesUnique", context));
						result = false;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Validates the parameterMappingsPointToDirectSubUnit constraint of '
	 * <em>Unit</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateUnit_parameterMappingsPointToDirectSubUnit(Unit unit, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		for (ParameterMapping pm : unit.getParameterMappings()) {
			if (unit.getParameters().contains(pm.getSource())) {
				if (pm.getTarget() != null && !unit.getSubUnits(false).contains(pm.getTarget().getUnit())) {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, unit, Unit.class,
							"parameterMappingsPointToDirectSubUnit", context));
					return false;
				}
			}
			if (unit.getParameters().contains(pm.getTarget())) {
				if (pm.getSource() != null && !unit.getSubUnits(false).contains(pm.getSource().getUnit())) {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, unit, Unit.class,
							"parameterMappingsPointToDirectSubUnit", context));
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapping(Mapping mapping, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(mapping, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNode(Node node, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(node, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(node, diagnostics, context);
		if (result || diagnostics != null) result &= validateNode_uniqueAttributeTypes(node, diagnostics, context);
		if (result || diagnostics != null) result &= validateNode_atMostOneContainer(node, diagnostics, context);
		if (result || diagnostics != null) result &= validateNode_NodeDeletionDanglingEdge(node, diagnostics, context);
		if (result || diagnostics != null) result &= validateNode_NodeCreationWithoutContainment(node, diagnostics, context);
		return result;
	}

	private boolean isExtendedConsistencyCheck() {
		// access the global preferences
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(HenshinModelPlugin.PLUGIN_ID);
		return preferences.getBoolean(PREF_ENABLE_EXTENDED_CONSISTENCY_CHECK, false);
	}

	private boolean isSuppressParameterKindDeprecatedWarnings() {
		// access the global preferences
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(HenshinModelPlugin.PLUGIN_ID);
		return preferences.getBoolean(PREF_SUPPRESS_PARAMETERKIND_DEPRECATED_WARNINGS, false);
	}

	/**
	 * Validates the uniqueAttributeTypes constraint of '<em>Node</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateNode_uniqueAttributeTypes(Node node, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = true;
		for (Attribute a1 : node.getAttributes()) {
			if (a1.getType() != null) {
				for (Attribute a2 : node.getAttributes()) {
					if (a1 == a2)
						break;
					if (a1.getType() == a2.getType()) {
						diagnostics.add(
								createDiagnostic(Diagnostic.ERROR, a2, Node.class, "uniqueAttributeTypes", context));
						result = false;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Validates the atMostOneContainer constraint of '<em>Node</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateNode_atMostOneContainer(Node node, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		Set<Node> containers = new HashSet<Node>();
		for (Edge incoming : node.getIncoming()) {
			EReference type = incoming.getType();
			if (type != null && type.isContainment()) {
				containers.add(incoming.getSource());
			}
		}
		for (Edge outgoing : node.getOutgoing()) {
			EReference type = outgoing.getType();
			if (type != null && type.isContainer() && type.getEOpposite() != null) {
				containers.add(outgoing.getTarget());
			}
		}
		if (containers.size() > 1) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, node.getActionNode(), Node.class, "atMostOneContainer",
					context));
		}
		return false;
	}

	/**
	 * Validates the consistent deletion of '<em>Node</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateNode_NodeDeletionDanglingEdge(Node node, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!isExtendedConsistencyCheck()) {
			return true;
		}
		Rule rule = node.getGraph().getRule();
		boolean nodeIsDeleted = rule.getLhs().getNodes().contains(node)
				&& rule.getMappings().getImage(node, rule.getRhs()) == null;
		if (rule.isCheckDangling()) {
			if (nodeIsDeleted) {
				for (Edge edge : node.getIncoming()) {
					if (edge.getType().isContainment()) {
						if (rule.getMappings().getImage(edge, rule.getRhs()) == null) {
							// checks the edge to be deleted
							return true;
						} else { // if the containment edge of the deleted node
									// is not deleted this leads to a dangling
									// edge error.
							diagnostics.add(createDiagnostic(Diagnostic.WARNING, node.getActionNode(), Node.class,
									"NodeDeletionDanglingEdge", context));
						}
					}
				}
				// if there is no associated containment edge which is deleted,
				// this seems to be an inconsistency, since each node has to be
				// contained somewhere and by not deleting this edge it might
				// lead to an dangling edge problem
				diagnostics.add(createDiagnostic(Diagnostic.WARNING, node.getActionNode(), Node.class,
						"NodeDeletionDanglingEdge", context));
			}
		}
		return false;
	}

	/**
	 * Validates the creation of '<em>Node</em>'s with their containment '
	 * <em>Edge</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateNode_NodeCreationWithoutContainment(Node node, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!isExtendedConsistencyCheck()) {
			return true;
		}
		Rule rule = node.getGraph().getRule();
		MappingList mappings = rule.getMappings();
		if (rule.getRhs().getNodes().contains(node)) {// only nodes in the RHS
														// have to be checked
			if (mappings.getOrigin(node) == null) { // check if node is created
				for (Edge edge : node.getIncoming()) {
					if (edge.getType().isContainment()) { // incoming
															// containment edge
															// is expected
						if (mappings.getOrigin(edge) == null) { // (containment-)edge
																// should be
																// created
							return true;
						}
					}
				}
				diagnostics.add(createDiagnostic(Diagnostic.WARNING, node.getActionNode(), Node.class,
						"NodeCreationWithoutContainment", context));
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttribute(Attribute attribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(attribute, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateAttribute_valueValidJavaScript(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateAttribute_valueAllParametersAreDeclared(attribute, diagnostics, context);
		return result;
	}

	/**
	 * Validates the valueValidJavaScript constraint of '<em>Attribute</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateAttribute_valueValidJavaScript(Attribute attribute, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		try {
			if (attribute.getNode() != null && attribute.getNode().getGraph() != null) {
				validateExpression(attribute.getValue(), attribute.getNode().getGraph().getRule());
			}
		} catch (ScriptException e) {
			diagnostics.add(
					createDiagnostic(Diagnostic.ERROR, attribute, Attribute.class, "valueValidJavaScript", context, e));
		}
		return true;
	}

	/**
	 * Validates the valueAllParametersAreDeclared constraint of '
	 * <em>Attribute</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateAttribute_valueAllParametersAreDeclared(Attribute attribute, DiagnosticChain diagnostics,
			Map<Object, Object> context) {

		if (attribute.getNode() != null && attribute.getNode().getGraph() != null && attribute.getValue() != null
				&& !attribute.getValue().isEmpty()) {

			List<String> undeclared = findUndeclaredNames(attribute.getValue(),
					attribute.getNode().getGraph().getRule());
			if (!undeclared.isEmpty()) {
				if (attribute.getType() != null && attribute.getType().getEType() instanceof EEnum) {
					for (EEnumLiteral lit : ((EEnum) attribute.getType().getEType()).getELiterals()) {
						undeclared.remove(lit.getName());
					}
				}
			}
			if (!undeclared.isEmpty()) {
				if (isJavaScriptExpression(attribute.getValue()))
					diagnostics.add(createDiagnostic(Diagnostic.WARNING, attribute, Attribute.class,
							"valueAllParametersAreDeclaredWarning", context, new RuntimeException(undeclared.get(0))));
				else
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, attribute, Attribute.class,
							"valueAllParametersAreDeclared", context, new RuntimeException(undeclared.get(0))));
				return false;
			}
		}
		return true;
	}

	private boolean isJavaScriptExpression(String value) {
		// For our purpose, it is sufficient to check if the input
		// value is alphanumeric. In this case, we can assume it
		// is a simple value, i.e., not a JS expression.
		return alphanumeric.matcher(value).find();
	}

	private static Pattern alphanumeric = Pattern.compile("[^a-zA-Z0-9]");

	private List<String> findUndeclaredNames(String value, Unit unit) {
		List<String> undeclared = new ArrayList<String>();

		Set<String> declared = new HashSet<String>();
		declared.addAll(getParameterNames(unit));
		declared.addAll(JAVA_KEYWORDS);
		declared.addAll(JAVASCRIPT_KEYWORDS);

		// Find all words (that is, groups of one character followed
		// by n characters or numbers).
		String regex = "\\b[a-zA-Z]+[a-zA-Z0-9]*\\b";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(value);
		String previous = new String();

		while (matcher.find()) {
			// Check if we're in a declaration or instantiation
			String candidate = matcher.group();
			if (previous.equals("var") || previous.equals("function") || previous.equals("new")) {
				previous = candidate;
				declared.add(candidate);
				continue;
			}
			previous = candidate;

			// Check if candidate has been declared earlier
			if (declared.contains(candidate))
				continue;

			// If the candidate has a leading dot, it is probably
			// a method or field name.
			if (matcher.start() > 0)
				if (value.charAt(matcher.start() - 1) == '.')
					continue;

			// Otherwise, if the candidate has a trailing dot, it
			// is probably a class name.
			if (matcher.end() < value.length() - 1) {
				if (value.charAt(matcher.end()) == '.')
					continue;
			}

			// If an uneven number of quotes comes before the
			// candidate, it is probably part of a String.
			String line = value.substring(0, matcher.start());
			int count = line.length() - line.replace("\"", "").length();
			if (count % 2 == 1)
				continue;

			undeclared.add(candidate);
		}

		return undeclared;
	}

	private Set<String> getParameterNames(Unit unit) {
		Set<String> result = new HashSet<String>();
		for (Parameter p : unit.getParameters()) {
			result.add(p.getName());
		}
		if (unit instanceof Rule) {
			Rule r = (Rule) unit;
			if (r.getKernelRule() != r && r.getKernelRule() != null)
				result.addAll(getParameterNames(r.getKernelRule()));
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEdge(Edge edge, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(edge, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validateEdge_equalParentGraphs(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validateEdge_indexValidJavaScript(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validateEdge_indexAllParametersAreDeclared(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validateEdge_noContainmentCycles(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validateEdge_EOppositeContainments(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validateEdge_oppositeEdgeConsidered(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validateEdge_noParallelEdgesOfSameType(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validateEdge_containmentEdgeDeletion(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validateEdge_containmentEdgeCreation(edge, diagnostics, context);
		return result;
	}

	/**
	 * Validates the equalParentGraphs constraint of '<em>Edge</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateEdge_equalParentGraphs(Edge edge, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (edge.getSource() != null && edge.getTarget() != null) {
			if (edge.getSource().getGraph() != edge.getGraph() || edge.getTarget().getGraph() != edge.getGraph()) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, edge, Edge.class, "equalParentGraphs", context));
				return false;
			}
		}
		return true;
	}

	/**
	 * Validates the indexValidJavaScript constraint of '<em>Edge</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateEdge_indexValidJavaScript(Edge edge, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		try {
			validateExpression(edge.getIndex(), edge.getGraph().getRule());
		} catch (ScriptException e) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, edge, Edge.class, "indexValidJavaScript", context, e));
		}
		return true;
	}

	/**
	 * Validates the indexAllParametersAreDeclared constraint of '<em>Edge</em> '.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateEdge_indexAllParametersAreDeclared(Edge edge, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (edge.getGraph() != null && edge.getIndex() != null && !edge.getIndex().isEmpty()) {
			List<String> undeclared = findUndeclaredNames(edge.getIndex(), edge.getGraph().getRule());
			if (!undeclared.isEmpty()) {
				if (isJavaScriptExpression(edge.getIndex()))
					diagnostics.add(createDiagnostic(Diagnostic.WARNING, edge, Edge.class,
							"indexAllParametersAreDeclaredWarning", context, new RuntimeException(undeclared.get(0))));
				else
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, edge, Edge.class,
							"indexAllParametersAreDeclared", context, new RuntimeException(undeclared.get(0))));
				return false;
			}
		}
		return true;
	}

	/**
	 * Validates the noContainmentCycles constraint of '<em>Edge</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public boolean validateEdge_noContainmentCycles(Edge edge, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = true;
		// Try to retrieve previously created graph-to-cycles map. If not
		// available, create new one.
		Map<Graph, List<List<Edge>>> cycleMap = new HashMap<Graph, List<List<Edge>>>();
		if (!context.containsKey(CONTAINMENT_CYCLES_KEY))
			context.put(CONTAINMENT_CYCLES_KEY, cycleMap);
		else
			cycleMap = (Map<Graph, List<List<Edge>>>) context.get(CONTAINMENT_CYCLES_KEY);

		// Try to retrieve previously created cycle map for the edge's host
		// graph.
		// If not available, create new one.
		List<List<Edge>> cycles = new ArrayList<List<Edge>>();
		if (cycleMap.containsKey(edge.getGraph())) {
			cycles = cycleMap.get(edge.getGraph());
		} else {
			ContainmentCycleFinder cycleFinder = new ContainmentCycleFinder();
			cycles = cycleFinder.findContainmentCycles(edge.getGraph());
			cycleMap.put(edge.getGraph(), cycles);
		}

		// If edge is part of cycle, highlight it.
		for (List<Edge> cycle : cycles) {
			if (cycle.contains(edge) && result) {
				result = false;
				diagnostics.add(createDiagnostic(Diagnostic.WARNING, edge, Edge.class, "noContainmentCycles", context));
			}
		}
		return result;
	}

	/**
	 * Validates the EOpposite constraint of '<em>Edge</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateEdge_EOppositeContainments(Edge edge, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (edge.getType() != null && edge.getType().getEOpposite() != null
				&& edge.getType().getEOpposite().isContainment()) {
			for (Edge potentialCorrespondingContainment : edge.getTarget().getOutgoing()) {
				if (potentialCorrespondingContainment.getType() == edge.getType().getEOpposite())
					return true;
			}
			diagnostics.add(createDiagnostic(Diagnostic.WARNING, edge, Edge.class, "EOppositeContainments", context));
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnaryUnit(UnaryUnit unaryUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(unaryUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(unaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(unaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(unaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(unaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(unaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(unaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(unaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(unaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_nameNotEmpty(unaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterNamesUnique(unaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterMappingsPointToDirectSubUnit(unaryUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMultiUnit(MultiUnit multiUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(multiUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(multiUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(multiUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(multiUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(multiUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(multiUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(multiUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(multiUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(multiUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_nameNotEmpty(multiUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterNamesUnique(multiUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterMappingsPointToDirectSubUnit(multiUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIndependentUnit(IndependentUnit independentUnit, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(independentUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_nameNotEmpty(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterNamesUnique(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterMappingsPointToDirectSubUnit(independentUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSequentialUnit(SequentialUnit sequentialUnit, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(sequentialUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_nameNotEmpty(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterNamesUnique(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterMappingsPointToDirectSubUnit(sequentialUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConditionalUnit(ConditionalUnit conditionalUnit, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(conditionalUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_nameNotEmpty(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterNamesUnique(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterMappingsPointToDirectSubUnit(conditionalUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePriorityUnit(PriorityUnit priorityUnit, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(priorityUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_nameNotEmpty(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterNamesUnique(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterMappingsPointToDirectSubUnit(priorityUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIteratedUnit(IteratedUnit iteratedUnit, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(iteratedUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_nameNotEmpty(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterNamesUnique(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterMappingsPointToDirectSubUnit(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateIteratedUnit_iterationsNotEmpty(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateIteratedUnit_iterationsValidJavaScript(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateIteratedUnit_iterationsAllParametersAreDeclared(iteratedUnit, diagnostics, context);
		return result;
	}

	/**
	 * Validates the iterationsNotEmpty constraint of '<em>Iterated Unit</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateIteratedUnit_iterationsNotEmpty(IteratedUnit iteratedUnit, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (iteratedUnit.getIterations() == null || iteratedUnit.getIterations().trim().length() == 0) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, iteratedUnit, IteratedUnit.class, "iterationsNotEmpty",
					context));
			return false;
		}
		return true;
	}

	/**
	 * Validates the iterationsValidJavaScript constraint of ' <em>Iterated
	 * Unit</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateIteratedUnit_iterationsValidJavaScript(IteratedUnit iteratedUnit,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		try {
			validateExpression(iteratedUnit.getIterations(), iteratedUnit);
		} catch (ScriptException e) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, iteratedUnit, IteratedUnit.class,
					"iterationsValidJavaScript", context, e));
		}
		return true;
	}

	/**
	 * Validates the iterationsAllParametersAreDeclared constraint of ' <em>Iterated
	 * Unit</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateIteratedUnit_iterationsAllParametersAreDeclared(IteratedUnit iteratedUnit,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (iteratedUnit.getIterations() != null && !iteratedUnit.getIterations().isEmpty()) {
			List<String> undeclared = findUndeclaredNames(iteratedUnit.getIterations(), iteratedUnit);
			if (!undeclared.isEmpty()) {
				if (isJavaScriptExpression(iteratedUnit.getIterations()))
					diagnostics.add(createDiagnostic(Diagnostic.WARNING, iteratedUnit, IteratedUnit.class,
							"iterationsAllParametersAreDeclaredWarning", context,
							new RuntimeException(undeclared.get(0))));
				else
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, iteratedUnit, IteratedUnit.class,
							"iterationsAllParametersAreDeclared", context, new RuntimeException(undeclared.get(0))));
				return false;
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLoopUnit(LoopUnit loopUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(loopUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_nameNotEmpty(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterNamesUnique(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_parameterMappingsPointToDirectSubUnit(loopUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNestedCondition(NestedCondition nestedCondition, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(nestedCondition, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(nestedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(nestedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(nestedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(nestedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(nestedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(nestedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(nestedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(nestedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validateNestedCondition_mappingOriginContainedInParentCondition(nestedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validateNestedCondition_mappingImageContainedInCurrent(nestedCondition, diagnostics, context);
		return result;
	}

	/**
	 * Validates the mappingOriginContainedInParentCondition constraint of '
	 * <em>Nested Condition</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateNestedCondition_mappingOriginContainedInParentCondition(NestedCondition nestedCondition,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		Graph graph = nestedCondition.getHost();
		if (graph != null && graph.eContainer() instanceof NestedCondition) {
			for (Mapping mapping : nestedCondition.getMappings()) {
				if (mapping.getOrigin() != null && mapping.getOrigin().getGraph() != graph) {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, mapping, NestedCondition.class,
							"mappingOriginContainedInParentCondition", context));
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Validates the mappingImageContainedInCurrent constraint of ' <em>Nested
	 * Condition</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateNestedCondition_mappingImageContainedInCurrent(NestedCondition nestedCondition,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		for (Mapping mapping : nestedCondition.getMappings()) {
			if (!nestedCondition.getConclusion().getNodes().contains(mapping.getImage())) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, mapping, NestedCondition.class,
						"mappingImageContainedInCurrent", context));
				return false;
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula(Formula formula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(formula, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnaryFormula(UnaryFormula unaryFormula, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(unaryFormula, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBinaryFormula(BinaryFormula binaryFormula, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(binaryFormula, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAnd(And and, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(and, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOr(Or or, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(or, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNot(Not not, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(not, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTrue(True true_, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(true_, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateParameterKind(ParameterKind parameterKind, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAction(Action action, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateXor(Xor xor, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(xor, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateParameterMapping(ParameterMapping parameterMapping, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(parameterMapping, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(parameterMapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(parameterMapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(parameterMapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(parameterMapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(parameterMapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(parameterMapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(parameterMapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(parameterMapping, diagnostics, context);
		if (result || diagnostics != null) result &= validateParameterMapping_inParameterMappingIsCausal(parameterMapping, diagnostics, context);
		if (result || diagnostics != null) result &= validateParameterMapping_outParameterMappingIsCausal(parameterMapping, diagnostics, context);
		if (result || diagnostics != null) result &= validateParameterMapping_inoutParameterMappingIsCausal(parameterMapping, diagnostics, context);
		if (result || diagnostics != null) result &= validateParameterMapping_varParameterMappingIsCausal(parameterMapping, diagnostics, context);
		return result;
	}

	/**
	 * Validates the inParameterMappingIsCausal constraint of ' <em>Parameter
	 * Mapping</em>'. <!-- begin-user-doc --> If the source of a parameter mapping
	 * is an in parameter contained in a unit, the target parameter has to be of the
	 * kinds in, inout or unknown. <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateParameterMapping_inParameterMappingIsCausal(ParameterMapping parameterMapping,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		final Parameter source = parameterMapping.getSource();
		final Parameter target = parameterMapping.getTarget();
		boolean result = true;

		if (source != null && target != null && source.getKind() == ParameterKind.IN
				&& eObjectIsUnit(source.eContainer())) {
			ParameterKind targetKind = target.getKind();
			if (targetKind != ParameterKind.UNKNOWN && targetKind != ParameterKind.IN
					&& targetKind != ParameterKind.INOUT && targetKind != ParameterKind.VAR) {
				result = false;
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, parameterMapping, ParameterMapping.class,
						"inParameterMappingIsCausal", context));
			}
		}

		return result;
	}

	/**
	 * Validates the outParameterMappingIsCausal constraint of ' <em>Parameter
	 * Mapping</em>'. <!-- begin-user-doc --> If the source of a parameter mapping
	 * is an out parameter contained in a unit, tha target parameter has to be of
	 * the kinds out, inout, or unknown. <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateParameterMapping_outParameterMappingIsCausal(ParameterMapping parameterMapping,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		final Parameter source = parameterMapping.getSource();
		final Parameter target = parameterMapping.getTarget();
		boolean result = true;

		if (source != null && target != null && source.getKind() == ParameterKind.OUT
				&& eObjectIsUnit(source.eContainer())) {
			ParameterKind targetKind = target.getKind();
			if (targetKind != ParameterKind.UNKNOWN && targetKind != ParameterKind.OUT
					&& targetKind != ParameterKind.INOUT && targetKind != ParameterKind.VAR) {
				result = false;
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, parameterMapping, ParameterMapping.class,
						"outParameterMappingIsCausal", context));
			}
		}

		return result;
	}

	/**
	 * Validates the inoutParameterMappingIsCausal constraint of '<em>Parameter Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unused")
	public boolean validateParameterMapping_inoutParameterMappingIsCausal(ParameterMapping parameterMapping, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "inoutParameterMappingIsCausal", getObjectLabel(parameterMapping, context) },
						 new Object[] { parameterMapping },
						 context));
			}
			return false;
		}
		return true;
	}

	private boolean mappingChainIsCausal(Parameter firstParameter, Parameter secondParameter) {
		ParameterKind firstParameterKind = firstParameter.getKind();
		ParameterKind secondParameterKind = secondParameter.getKind();

		return (firstParameterKind == ParameterKind.OUT || firstParameterKind == ParameterKind.INOUT
				|| firstParameterKind == ParameterKind.UNKNOWN)
				&& (secondParameterKind == ParameterKind.IN || secondParameterKind == ParameterKind.INOUT
						|| secondParameterKind == ParameterKind.UNKNOWN);
	}

	/**
	 * Validates the varParameterMappingIsCausal constraint of ' <em>Parameter
	 * Mapping</em>'. <!-- begin-user-doc --> If the source of a parameter mapping
	 * is a var parameter contained in a unit, and the target parameter is of the
	 * kind in, the mapping is valid if: <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateParameterMapping_varParameterMappingIsCausal(ParameterMapping parameterMapping,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		final Parameter source = parameterMapping.getSource();
		final Parameter target = parameterMapping.getTarget();
		boolean result = true;

		if (source != null && target != null && source.getKind() == ParameterKind.VAR
				&& eObjectIsUnit(source.eContainer())) {
			ParameterKind targetKind = target.getKind();

			if (targetKind == ParameterKind.VAR) {
				result = false;
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, parameterMapping, ParameterMapping.class,
						"varParameterMappingIsCausal", context));
			} else if (targetKind == ParameterKind.IN || targetKind == ParameterKind.OUT
					|| targetKind == ParameterKind.UNKNOWN) {
				final Unit unit = (Unit) source.eContainer();
				final EList<ParameterMapping> mappings = unit.getParameterMappings();
				final Unit targetUnit = target.getUnit();
				final EList<Unit> subUnits = unit.getSubUnits(false);
				final int targetUnitIndex = subUnits.indexOf(targetUnit);
				boolean causalityChainFound = false;

				for (ParameterMapping mapping : mappings) {
					Parameter mappingSource = mapping.getSource();
					Parameter mappingTarget = mapping.getTarget();
					Unit mappingSourceUnit = mappingSource.getUnit();
					boolean mappingIsCausal = false;

					if (mappingTarget.equals(source)) {
						if (subUnits.indexOf(mappingSourceUnit) < targetUnitIndex) {
							mappingIsCausal = mappingChainIsCausal(mappingSource, target);
						} else {
							mappingIsCausal = mappingChainIsCausal(target, mappingSource);
						}

						if (mappingIsCausal) {
							causalityChainFound = true;
							break;
						}
					}
				}

				if (!causalityChainFound) {
					result = false;
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, parameterMapping, ParameterMapping.class,
							"varParameterMappingIsCausal", context));
				}
			}
		}
		return result;
	}

	private boolean eObjectIsUnit(EObject object) {
		return object != null && object instanceof Unit && !(object instanceof Rule);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return HenshinModelPlugin.INSTANCE;
	}

	/*
	 * Private helper for creating diagnostics.
	 */
	private Diagnostic createDiagnostic(int severity, EObject object, Class<?> targetType, String constraint,
			Map<Object, Object> context) {
		return createDiagnostic(severity, object, targetType, constraint, context, null);
	}

	/*
	 * Private helper for creating diagnostics.
	 */
	private Diagnostic createDiagnostic(int severity, EObject object, Class<?> targetType, String constraint,
			Map<Object, Object> context, Exception exception) {
		String typeName = targetType.getSimpleName();
		String objectLabel = (object instanceof NamedElement) ? ((NamedElement) object).getName() + ""
				: getObjectLabel(object, context);
		return createDiagnostic(severity, DIAGNOSTIC_SOURCE, 0, "_UI_GenericConstraint_diagnostic",
				(exception != null) ? new Object[] { objectLabel, exception.getMessage() }
						: new Object[] { objectLabel },
				new Object[] { object }, context, "_Constraint_Msg_" + typeName + "_" + constraint);
	}

	/*
	 * Private helper for creating diagnostics.
	 */
	protected BasicDiagnostic createDiagnostic(int severity, String source, int code, String messageKey,
			Object[] messageSubstitutions, Object[] data, Map<Object, Object> context, String customMessage) {
		String message;
		if ((customMessage != null) && (customMessage.length() > 0)) {
			message = (customMessage.startsWith("_") ? getString(customMessage, messageSubstitutions) : customMessage);
		} else {
			message = getString(messageKey, messageSubstitutions);
		}
		return new BasicDiagnostic(severity, source, code, message, data);
	}

}
