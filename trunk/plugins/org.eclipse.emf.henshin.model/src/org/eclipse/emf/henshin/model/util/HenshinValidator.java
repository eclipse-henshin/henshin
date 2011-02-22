/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.emf.henshin.HenshinModelPlugin;

import org.eclipse.emf.henshin.model.*;

import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;

import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.model.HenshinPackage
 * @generated
 */
public class HenshinValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final HenshinValidator INSTANCE = new HenshinValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.emf.henshin.model";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * The parsed OCL expression for the definition of the '<em>ValidName</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint namedElement_ValidNameInvOCL;
	/**
	 * The parsed OCL expression for the definition of the '<em>lhsAndRhsNotNull</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint rule_lhsAndRhsNotNullInvOCL;

	/**
	 * The parsed OCL expression for the definition of the '<em>EqualParentGraphs</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint edge_EqualParentGraphsInvOCL;
	/**
	 * The parsed OCL expression for the definition of the '<em>kernelLhsNodesMapped</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint amalgamationUnit_kernelLhsNodesMappedInvOCL;

	/**
	 * The parsed OCL expression for the definition of the '<em>kernelRhsNodesMapped</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint amalgamationUnit_kernelRhsNodesMappedInvOCL;

	/**
	 * The parsed OCL expression for the definition of the '<em>kernelLhsEdgesMapped</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint amalgamationUnit_kernelLhsEdgesMappedInvOCL;

	/**
	 * The parsed OCL expression for the definition of the '<em>kernelRhsEdgesMapped</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint amalgamationUnit_kernelRhsEdgesMappedInvOCL;

	private static final String OCL_ANNOTATION_SOURCE = "http://www.eclipse.org/emf/2010/Henshin/OCL";
	
	private static final OCL OCL_ENV = OCL.newInstance();
	
	/**
	 * Maps to translate OCL severity additions ("info", "warning" and "error")
	 * to corresponding enumeration values of Diagnostic, i.e. Diagnostic.INFO,
	 * Diagnostic.WARNING and Diagnostic.ERROR.
	 */
	private static final Map<String, String> HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP = new HashMap<String, String>();
	static {
		HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.put("info", Integer.toString(Diagnostic.INFO));
		HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.put("warning", Integer.toString(Diagnostic.WARNING));
		HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.put("error", Integer.toString(Diagnostic.ERROR));
	}

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HenshinValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return HenshinPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case HenshinPackage.NAMED_ELEMENT:
				return validateNamedElement((NamedElement)value, diagnostics, context);
			case HenshinPackage.DESCRIBED_ELEMENT:
				return validateDescribedElement((DescribedElement)value, diagnostics, context);
			case HenshinPackage.TRANSFORMATION_SYSTEM:
				return validateTransformationSystem((TransformationSystem)value, diagnostics, context);
			case HenshinPackage.RULE:
				return validateRule((Rule)value, diagnostics, context);
			case HenshinPackage.ATTRIBUTE_CONDITION:
				return validateAttributeCondition((AttributeCondition)value, diagnostics, context);
			case HenshinPackage.PARAMETER:
				return validateParameter((Parameter)value, diagnostics, context);
			case HenshinPackage.GRAPH:
				return validateGraph((Graph)value, diagnostics, context);
			case HenshinPackage.GRAPH_ELEMENT:
				return validateGraphElement((GraphElement)value, diagnostics, context);
			case HenshinPackage.MAPPING:
				return validateMapping((Mapping)value, diagnostics, context);
			case HenshinPackage.NODE:
				return validateNode((Node)value, diagnostics, context);
			case HenshinPackage.ATTRIBUTE:
				return validateAttribute((Attribute)value, diagnostics, context);
			case HenshinPackage.EDGE:
				return validateEdge((Edge)value, diagnostics, context);
			case HenshinPackage.TRANSFORMATION_UNIT:
				return validateTransformationUnit((TransformationUnit)value, diagnostics, context);
			case HenshinPackage.INDEPENDENT_UNIT:
				return validateIndependentUnit((IndependentUnit)value, diagnostics, context);
			case HenshinPackage.SEQUENTIAL_UNIT:
				return validateSequentialUnit((SequentialUnit)value, diagnostics, context);
			case HenshinPackage.CONDITIONAL_UNIT:
				return validateConditionalUnit((ConditionalUnit)value, diagnostics, context);
			case HenshinPackage.PRIORITY_UNIT:
				return validatePriorityUnit((PriorityUnit)value, diagnostics, context);
			case HenshinPackage.AMALGAMATION_UNIT:
				return validateAmalgamationUnit((AmalgamationUnit)value, diagnostics, context);
			case HenshinPackage.COUNTED_UNIT:
				return validateCountedUnit((CountedUnit)value, diagnostics, context);
			case HenshinPackage.NESTED_CONDITION:
				return validateNestedCondition((NestedCondition)value, diagnostics, context);
			case HenshinPackage.FORMULA:
				return validateFormula((Formula)value, diagnostics, context);
			case HenshinPackage.UNARY_FORMULA:
				return validateUnaryFormula((UnaryFormula)value, diagnostics, context);
			case HenshinPackage.BINARY_FORMULA:
				return validateBinaryFormula((BinaryFormula)value, diagnostics, context);
			case HenshinPackage.AND:
				return validateAnd((And)value, diagnostics, context);
			case HenshinPackage.OR:
				return validateOr((Or)value, diagnostics, context);
			case HenshinPackage.NOT:
				return validateNot((Not)value, diagnostics, context);
			case HenshinPackage.PARAMETER_MAPPING:
				return validateParameterMapping((ParameterMapping)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement(NamedElement namedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(namedElement, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ValidName constraint of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement_ValidName(NamedElement namedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
        if (namedElement_ValidNameInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.NAMED_ELEMENT);
			
			EAnnotation ocl = HenshinPackage.Literals.NAMED_ELEMENT.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("ValidName");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				namedElement_ValidNameInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			namedElement_ValidNameInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("ValidName.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("ValidName.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(namedElement_ValidNameInvOCL);
		
		if (!query.check(namedElement)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = namedElement_ValidNameInvOCL
						.getEAnnotation(OCL_ANNOTATION_SOURCE);
				int severity = henshinAnnotation.getDetails().containsKey("Severity") ? Integer
						.parseInt(henshinAnnotation.getDetails().get("Severity"))
						: Diagnostic.ERROR; //default severity is Diagnostic.ERROR

				String addMsg = henshinAnnotation.getDetails().containsKey("Msg") ? henshinAnnotation
						.getDetails().get("Msg") : null;			
			
				diagnostics.add
					(createDiagnostic
						(severity,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ValidName", getObjectLabel(namedElement, context) },
						 new Object[] { namedElement },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDescribedElement(DescribedElement describedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(describedElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransformationSystem(TransformationSystem transformationSystem, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(transformationSystem, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRule(Rule rule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_lhsAndRhsNotNull(rule, diagnostics, context);
		return result;
	}

	/**
	 * Validates the lhsAndRhsNotNull constraint of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRule_lhsAndRhsNotNull(Rule rule, DiagnosticChain diagnostics, Map<Object, Object> context) {
        if (rule_lhsAndRhsNotNullInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.RULE);
			
			EAnnotation ocl = HenshinPackage.Literals.RULE.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("lhsAndRhsNotNull");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				rule_lhsAndRhsNotNullInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			rule_lhsAndRhsNotNullInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("lhsAndRhsNotNull.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("lhsAndRhsNotNull.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(rule_lhsAndRhsNotNullInvOCL);
		
		if (!query.check(rule)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = rule_lhsAndRhsNotNullInvOCL
						.getEAnnotation(OCL_ANNOTATION_SOURCE);
				int severity = henshinAnnotation.getDetails().containsKey("Severity") ? Integer
						.parseInt(henshinAnnotation.getDetails().get("Severity"))
						: Diagnostic.ERROR; //default severity is Diagnostic.ERROR

				String addMsg = henshinAnnotation.getDetails().containsKey("Msg") ? henshinAnnotation
						.getDetails().get("Msg") : null;			
			
				diagnostics.add
					(createDiagnostic
						(severity,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "lhsAndRhsNotNull", getObjectLabel(rule, context) },
						 new Object[] { rule },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributeCondition(AttributeCondition attributeCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(attributeCondition, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateParameter(Parameter parameter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(parameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(parameter, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGraph(Graph graph, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(graph, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGraphElement(GraphElement graphElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(graphElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapping(Mapping mapping, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(mapping, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNode(Node node, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(node, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(node, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttribute(Attribute attribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(attribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEdge(Edge edge, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validateEdge_EqualParentGraphs(edge, diagnostics, context);
		return result;
	}

	/**
	 * Validates the EqualParentGraphs constraint of '<em>Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEdge_EqualParentGraphs(Edge edge, DiagnosticChain diagnostics, Map<Object, Object> context) {
        if (edge_EqualParentGraphsInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.EDGE);
			
			EAnnotation ocl = HenshinPackage.Literals.EDGE.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("EqualParentGraphs");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				edge_EqualParentGraphsInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			edge_EqualParentGraphsInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("EqualParentGraphs.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("EqualParentGraphs.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(edge_EqualParentGraphsInvOCL);
		
		if (!query.check(edge)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = edge_EqualParentGraphsInvOCL
						.getEAnnotation(OCL_ANNOTATION_SOURCE);
				int severity = henshinAnnotation.getDetails().containsKey("Severity") ? Integer
						.parseInt(henshinAnnotation.getDetails().get("Severity"))
						: Diagnostic.ERROR; //default severity is Diagnostic.ERROR

				String addMsg = henshinAnnotation.getDetails().containsKey("Msg") ? henshinAnnotation
						.getDetails().get("Msg") : null;			
			
				diagnostics.add
					(createDiagnostic
						(severity,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "EqualParentGraphs", getObjectLabel(edge, context) },
						 new Object[] { edge },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransformationUnit(TransformationUnit transformationUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(transformationUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIndependentUnit(IndependentUnit independentUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(independentUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSequentialUnit(SequentialUnit sequentialUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(sequentialUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConditionalUnit(ConditionalUnit conditionalUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(conditionalUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePriorityUnit(PriorityUnit priorityUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(priorityUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAmalgamationUnit(AmalgamationUnit amalgamationUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateAmalgamationUnit_kernelLhsNodesMapped(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateAmalgamationUnit_kernelRhsNodesMapped(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateAmalgamationUnit_kernelLhsEdgesMapped(amalgamationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateAmalgamationUnit_kernelRhsEdgesMapped(amalgamationUnit, diagnostics, context);
		return result;
	}

	/**
	 * Validates the kernelLhsNodesMapped constraint of '<em>Amalgamation Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAmalgamationUnit_kernelLhsNodesMapped(AmalgamationUnit amalgamationUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
        if (amalgamationUnit_kernelLhsNodesMappedInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.AMALGAMATION_UNIT);
			
			EAnnotation ocl = HenshinPackage.Literals.AMALGAMATION_UNIT.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("kernelLhsNodesMapped");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				amalgamationUnit_kernelLhsNodesMappedInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			amalgamationUnit_kernelLhsNodesMappedInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("kernelLhsNodesMapped.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("kernelLhsNodesMapped.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(amalgamationUnit_kernelLhsNodesMappedInvOCL);
		
		if (!query.check(amalgamationUnit)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = amalgamationUnit_kernelLhsNodesMappedInvOCL
						.getEAnnotation(OCL_ANNOTATION_SOURCE);
				int severity = henshinAnnotation.getDetails().containsKey("Severity") ? Integer
						.parseInt(henshinAnnotation.getDetails().get("Severity"))
						: Diagnostic.ERROR; //default severity is Diagnostic.ERROR

				String addMsg = henshinAnnotation.getDetails().containsKey("Msg") ? henshinAnnotation
						.getDetails().get("Msg") : null;			
			
				diagnostics.add
					(createDiagnostic
						(severity,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "kernelLhsNodesMapped", getObjectLabel(amalgamationUnit, context) },
						 new Object[] { amalgamationUnit },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the kernelRhsNodesMapped constraint of '<em>Amalgamation Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAmalgamationUnit_kernelRhsNodesMapped(AmalgamationUnit amalgamationUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
        if (amalgamationUnit_kernelRhsNodesMappedInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.AMALGAMATION_UNIT);
			
			EAnnotation ocl = HenshinPackage.Literals.AMALGAMATION_UNIT.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("kernelRhsNodesMapped");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				amalgamationUnit_kernelRhsNodesMappedInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			amalgamationUnit_kernelRhsNodesMappedInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("kernelRhsNodesMapped.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("kernelRhsNodesMapped.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(amalgamationUnit_kernelRhsNodesMappedInvOCL);
		
		if (!query.check(amalgamationUnit)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = amalgamationUnit_kernelRhsNodesMappedInvOCL
						.getEAnnotation(OCL_ANNOTATION_SOURCE);
				int severity = henshinAnnotation.getDetails().containsKey("Severity") ? Integer
						.parseInt(henshinAnnotation.getDetails().get("Severity"))
						: Diagnostic.ERROR; //default severity is Diagnostic.ERROR

				String addMsg = henshinAnnotation.getDetails().containsKey("Msg") ? henshinAnnotation
						.getDetails().get("Msg") : null;			
			
				diagnostics.add
					(createDiagnostic
						(severity,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "kernelRhsNodesMapped", getObjectLabel(amalgamationUnit, context) },
						 new Object[] { amalgamationUnit },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the kernelLhsEdgesMapped constraint of '<em>Amalgamation Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAmalgamationUnit_kernelLhsEdgesMapped(AmalgamationUnit amalgamationUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
        if (amalgamationUnit_kernelLhsEdgesMappedInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.AMALGAMATION_UNIT);
			
			EAnnotation ocl = HenshinPackage.Literals.AMALGAMATION_UNIT.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("kernelLhsEdgesMapped");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				amalgamationUnit_kernelLhsEdgesMappedInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			amalgamationUnit_kernelLhsEdgesMappedInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("kernelLhsEdgesMapped.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("kernelLhsEdgesMapped.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(amalgamationUnit_kernelLhsEdgesMappedInvOCL);
		
		if (!query.check(amalgamationUnit)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = amalgamationUnit_kernelLhsEdgesMappedInvOCL
						.getEAnnotation(OCL_ANNOTATION_SOURCE);
				int severity = henshinAnnotation.getDetails().containsKey("Severity") ? Integer
						.parseInt(henshinAnnotation.getDetails().get("Severity"))
						: Diagnostic.ERROR; //default severity is Diagnostic.ERROR

				String addMsg = henshinAnnotation.getDetails().containsKey("Msg") ? henshinAnnotation
						.getDetails().get("Msg") : null;			
			
				diagnostics.add
					(createDiagnostic
						(severity,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "kernelLhsEdgesMapped", getObjectLabel(amalgamationUnit, context) },
						 new Object[] { amalgamationUnit },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the kernelRhsEdgesMapped constraint of '<em>Amalgamation Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAmalgamationUnit_kernelRhsEdgesMapped(AmalgamationUnit amalgamationUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
        if (amalgamationUnit_kernelRhsEdgesMappedInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.AMALGAMATION_UNIT);
			
			EAnnotation ocl = HenshinPackage.Literals.AMALGAMATION_UNIT.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("kernelRhsEdgesMapped");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				amalgamationUnit_kernelRhsEdgesMappedInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			amalgamationUnit_kernelRhsEdgesMappedInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("kernelRhsEdgesMapped.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("kernelRhsEdgesMapped.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(amalgamationUnit_kernelRhsEdgesMappedInvOCL);
		
		if (!query.check(amalgamationUnit)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = amalgamationUnit_kernelRhsEdgesMappedInvOCL
						.getEAnnotation(OCL_ANNOTATION_SOURCE);
				int severity = henshinAnnotation.getDetails().containsKey("Severity") ? Integer
						.parseInt(henshinAnnotation.getDetails().get("Severity"))
						: Diagnostic.ERROR; //default severity is Diagnostic.ERROR

				String addMsg = henshinAnnotation.getDetails().containsKey("Msg") ? henshinAnnotation
						.getDetails().get("Msg") : null;			
			
				diagnostics.add
					(createDiagnostic
						(severity,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "kernelRhsEdgesMapped", getObjectLabel(amalgamationUnit, context) },
						 new Object[] { amalgamationUnit },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCountedUnit(CountedUnit countedUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(countedUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNestedCondition(NestedCondition nestedCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(nestedCondition, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula(Formula formula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(formula, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnaryFormula(UnaryFormula unaryFormula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(unaryFormula, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBinaryFormula(BinaryFormula binaryFormula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(binaryFormula, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAnd(And and, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(and, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOr(Or or, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(or, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNot(Not not, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(not, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateParameterMapping(ParameterMapping parameterMapping, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(parameterMapping, diagnostics, context);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return HenshinModelPlugin.INSTANCE;
	}


	/**
	 * Extended version of
	 * {@link #createDiagnostic(int, String, int, String, Object[], Object[], Map)}
	 * which essentially does the same except that is provides the possibility
	 * to append additional information to the diagnostic text. This can be done
	 * differently:<br>
	 * 1) if additionalMessage starts with an "_", it is considered of being a
	 * key pointing to a string in the plugin.properties,<br>
	 * 2) otherwise the contained string is passed as is
	 * 
	 * @param severity
	 * @param source
	 * @param code
	 * @param messageKey
	 * @param messageSubstitutions
	 * @param data
	 * @param context
	 * @param additionalMessage
	 * @return
	 * 
	 * @author sjurack
	 *
	 * @generated
	 */
	protected BasicDiagnostic createDiagnostic(int severity, String source, int code,
			String messageKey, Object[] messageSubstitutions, Object[] data,
			Map<Object, Object> context, String additionalMessage) {

		String henshinMessage = "";

		if ((additionalMessage != null) && (additionalMessage.length() > 0))
			henshinMessage = " -- " + (additionalMessage.startsWith("_") ? getString(additionalMessage,
					messageSubstitutions) : additionalMessage);

		String message = getString(messageKey, messageSubstitutions);
		return new BasicDiagnostic(severity, source, code, message + henshinMessage, data);
	}// createDiagnostic


} //HenshinValidator
