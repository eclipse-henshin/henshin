/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
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
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc
 * -->
 * 
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
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;
	
	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;
	
	/**
	 * The parsed OCL expression for the definition of the '<em>ValidName</em>' invariant constraint.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint namedElement_ValidNameInvOCL;
	/**
	 * The parsed OCL expression for the definition of the '<em>uniqueUnitNames</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint transformationSystem_uniqueUnitNamesInvOCL;
	
	/**
	 * The parsed OCL expression for the definition of the '<em>lhsAndRhsNotNull</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint rule_lhsAndRhsNotNullInvOCL;
	
	/**
	 * The parsed OCL expression for the definition of the '
	 * <em>mappingsFromLeft2Right</em>' invariant constraint. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static Constraint rule_mappingsFromLeft2RightInvOCL;
	
	/**
	 * The parsed OCL expression for the definition of the '<em>nameRequired</em>' invariant constraint.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	private static Constraint parameter_nameRequiredInvOCL;
	
	/**
	 * The parsed OCL expression for the definition of the '<em>uniqueNodeNames</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint graph_uniqueNodeNamesInvOCL;
	
	/**
	 * The parsed OCL expression for the definition of the '
	 * <em>ruleMapping_TypeEquality</em>' invariant constraint. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static Constraint mapping_ruleMapping_TypeEqualityInvOCL;
	
	/**
	 * The parsed OCL expression for the definition of the '<em>uniqueAttributeTypes</em>' invariant constraint.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint node_uniqueAttributeTypesInvOCL;
	
	/**
	 * The parsed OCL expression for the definition of the '<em>equalParentGraphs</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint edge_equalParentGraphsInvOCL;
	
	/**
	 * The parsed OCL expression for the definition of the '<em>uniqueParameterNames</em>' invariant constraint.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint transformationUnit_uniqueParameterNamesInvOCL;
	
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
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		switch (classifierID) {
			case HenshinPackage.NAMED_ELEMENT:
				return validateNamedElement((NamedElement)value, diagnostics, context);
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
			case HenshinPackage.ITERATED_UNIT:
				return validateIteratedUnit((IteratedUnit)value, diagnostics, context);
			case HenshinPackage.LOOP_UNIT:
				return validateLoopUnit((LoopUnit)value, diagnostics, context);
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
			case HenshinPackage.XOR:
				return validateXor((Xor)value, diagnostics, context);
			case HenshinPackage.NOT:
				return validateNot((Not)value, diagnostics, context);
			case HenshinPackage.PARAMETER_MAPPING:
				return validateParameterMapping((ParameterMapping)value, diagnostics, context);
			default:
				return true;
		}
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement(NamedElement namedElement, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
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
	 * Validates the ValidName constraint of '<em>Named Element</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean validateNamedElement_ValidName(NamedElement namedElement,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransformationSystem(TransformationSystem transformationSystem,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
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
		if (result || diagnostics != null) result &= validateTransformationSystem_uniqueUnitNames(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationSystem_noCyclicUnits(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationSystem_parameterNamesNotTypeName(transformationSystem, diagnostics, context);
		return result;
	}
	
	/**
	 * Validates the uniqueUnitNames constraint of '<em>Transformation System</em>'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public boolean validateTransformationSystem_uniqueUnitNames(
			TransformationSystem transformationSystem, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
        if (transformationSystem_uniqueUnitNamesInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.TRANSFORMATION_SYSTEM);
			
			EAnnotation ocl = HenshinPackage.Literals.TRANSFORMATION_SYSTEM.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("uniqueUnitNames");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				transformationSystem_uniqueUnitNamesInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			transformationSystem_uniqueUnitNamesInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("uniqueUnitNames.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("uniqueUnitNames.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(transformationSystem_uniqueUnitNamesInvOCL);
		
		if (!query.check(transformationSystem)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = transformationSystem_uniqueUnitNamesInvOCL
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
						 new Object[] { "uniqueUnitNames", getObjectLabel(transformationSystem, context) },
						 new Object[] { transformationSystem },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Validates the noCyclicUnits constraint of '<em>Transformation System</em>
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @author Gregor Bonifer
	 * @author Stefan Jurack (sjurack)
	 * @generated NOT
	 */
	public boolean validateTransformationSystem_noCyclicUnits(
			TransformationSystem transformationSystem, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		final Stack<TransformationUnit> path = new Stack<TransformationUnit>();
		final List<TransformationUnit> cyclic = new ArrayList<TransformationUnit>();
		boolean result = true;
		
		for (TransformationUnit unit : transformationSystem.getTransformationUnits()) {
			path.clear();
			TransformationUnit cyc = isCyclic(path, unit);
			if (cyc != null && !cyclic.contains(cyc)) {
				cyclic.add(cyc);
				diagnostics.add(createDiagnostic(
						Diagnostic.WARNING,
						DIAGNOSTIC_SOURCE,
						0,
						"_UI_GenericConstraint_diagnostic",
						new Object[] { "transformationSystem_noCyclicUnits",
								getObjectLabel(transformationSystem, context),
								getObjectLabel(cyc, context) },
						new Object[] { transformationSystem }, context,
						"_EcoreConstraint_Msg_TransformationSystem_noCyclicUnits"));
				result = false;
			}
		}
		
		return result;
	}
	
	/**
	 * Validates the parameterNamesNotTypeName constraint of '
	 * <em>Transformation System</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated NOT
	 * @author Gregor Bonifer
	 * @author Stefan Jurack (sjurack)
	 */
	public boolean validateTransformationSystem_parameterNamesNotTypeName(
			TransformationSystem transformationSystem, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		
		for (TransformationUnit tUnit : transformationSystem.getTransformationUnits()) {
			for (Parameter param : tUnit.getParameters()) {
				if (containsImportedEClassifier(transformationSystem, param.getName())) {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, DIAGNOSTIC_SOURCE, 0,
							"_UI_GenericConstraint_diagnostic", new Object[] {
									"transformationSystem_parameterNamesNotTypeName",
									getObjectLabel(transformationSystem, context) },
							new Object[] { transformationSystem }, context,
							"_EcoreConstraint_Msg_TransformationSystem_parameterNamesNotTypeName"));
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param tSys
	 * @param name
	 * @return
	 */
	protected boolean containsImportedEClassifier(TransformationSystem tSys, String name) {
		for (EPackage pack : tSys.getImports()) {
			if (pack.getEClassifier(name) != null) return true;
		}
		return false;
	}
	
	/**
	 * Recursive Helper for validateTransformationSystem_noCyclicUnits. '. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @author Gregor Bonifer
	 * @author Stefan Jurack (sjurack)
	 * @generated NOT
	 */
	protected TransformationUnit isCyclic(Stack<TransformationUnit> path, TransformationUnit unit) {
		if (path.contains(unit)) return unit;
		path.push(unit);
		for (TransformationUnit subUnit : unit.getSubUnits(false)) {
			if (subUnit != null) {
				TransformationUnit tu = isCyclic(path, subUnit);
				if (tu != null) return tu;
			}
		}
		path.pop();
		return null;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		if (result || diagnostics != null) result &= validateTransformationUnit_uniqueParameterNames(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationUnit_parameterMappingsPointToDirectSubUnit(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_lhsAndRhsNotNull(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_mappingsFromLeft2Right(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_createdNodesNotAbstract(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_createdEdgesNotDerived(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateRule_deletedEdgesNotDerived(rule, diagnostics, context);
		return result;
	}
	
	/**
	 * Validates the lhsAndRhsNotNull constraint of '<em>Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean validateRule_lhsAndRhsNotNull(Rule rule, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
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
	 * Validates the mappingsFromLeft2Right constraint of '<em>Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean validateRule_mappingsFromLeft2Right(Rule rule, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
        if (rule_mappingsFromLeft2RightInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.RULE);
			
			EAnnotation ocl = HenshinPackage.Literals.RULE.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("mappingsFromLeft2Right");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				rule_mappingsFromLeft2RightInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			rule_mappingsFromLeft2RightInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("mappingsFromLeft2Right.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("mappingsFromLeft2Right.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(rule_mappingsFromLeft2RightInvOCL);
		
		if (!query.check(rule)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = rule_mappingsFromLeft2RightInvOCL
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
						 new Object[] { "mappingsFromLeft2Right", getObjectLabel(rule, context) },
						 new Object[] { rule },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Validates the createdNodesNotAbstract constraint of '<em>Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 * @author Gregor Bonifer
	 * @author Stefan Jurack (sjurack)
	 */
	public boolean validateRule_createdNodesNotAbstract(Rule rule, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		nodeLoop: for (Node node : rule.getRhs().getNodes()) {
			if (node.getType() != null && node.getType().isAbstract()) {
				for (Mapping mapping : rule.getMappings()) {
					// node is not <<create>>, if there is a mapping onto it.
					//
					if (mapping.getImage() == node) continue nodeLoop;
				}
				// no mapping onto current node found. So node is <<create>>
				//
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, DIAGNOSTIC_SOURCE, 0,
						"_UI_GenericConstraint_diagnostic", new Object[] {
								"rule_createdNodesNotAbstract", getObjectLabel(node, context) },
						new Object[] { node }, context,
						"_EcoreConstraint_Msg_Rule_createdNodesNotAbstract"));
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Validates the createdEdgesNotDerived constraint of '<em>Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 * @author Gregor Bonifer
	 * @author Stefan Jurack (sjurack)
	 */
	public boolean validateRule_createdEdgesNotDerived(Rule rule, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		edgeLoop: for (Edge edge : rule.getRhs().getEdges()) {
			if (edge.getType() != null && edge.getType().isDerived()) {
				// check whether the edge is <<create>>
				//
				Node sourceOrigin = null, targetOrigin = null;
				boolean originsFound = false;
				mappingLoop: for (Mapping mapping : rule.getMappings()) {
					
					if (mapping.getImage() != null && mapping.getImage() == edge.getSource()) {
						sourceOrigin = mapping.getOrigin();
						// cannot perform check for current edge if involved
						// mappings incomplete.
						//
						if (sourceOrigin == null) continue edgeLoop;
					}
					
					if (mapping.getImage() != null && mapping.getImage() == edge.getTarget()) {
						targetOrigin = mapping.getOrigin();
						// cannot perform check for current edge if involved
						// mappings incomplete.
						//
						if (targetOrigin == null) continue edgeLoop;
					}
					
					// involved origins found.
					if (sourceOrigin != null && targetOrigin != null) {
						originsFound = true;
						break mappingLoop;
					}
					
				}
				if (!originsFound) continue edgeLoop;
				
				for (Edge oEdge : sourceOrigin.getOutgoing()) {
					// if lhs edge of same type is found between origins, the
					// edge is not <<create>>
					if (oEdge.getTarget() == targetOrigin && oEdge.getType() == edge.getType()) {
						continue edgeLoop;
					}
				}
				// no lhs edge of same type is found between origin. So the edge
				// is <<create>>.
				//
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, DIAGNOSTIC_SOURCE, 0,
						"_UI_GenericConstraint_diagnostic", new Object[] {
								"rule_createdNodesNotAbstract", getObjectLabel(edge, context) },
						new Object[] { edge }, context,
						"_EcoreConstraint_Msg_Rule_createdEdgesNotDerived"));
				return false;
				
			}
		}
		return true;
	}
	
	/**
	 * Validates the deletedEdgesNotDerived constraint of '<em>Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 * @author Gregor Bonifer
	 * @author Stefan Jurack (sjurack)
	 */
	public boolean validateRule_deletedEdgesNotDerived(Rule rule, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		edgeLoop: for (Edge edge : rule.getLhs().getEdges()) {
			if (edge.getType() != null && edge.getType().isDerived()) {
				// check whether the edge is <<delete>>
				//
				Node sourceImage = null, targetImage = null;
				boolean imagesFound = false;
				mappingLoop: for (Mapping mapping : rule.getMappings()) {
					
					if (mapping.getOrigin() != null && mapping.getOrigin() == edge.getSource()) {
						sourceImage = mapping.getImage();
						// cannot perform check for current edge if involved
						// mappings incomplete.
						//
						if (sourceImage == null) continue edgeLoop;
					}
					
					if (mapping.getOrigin() != null && mapping.getOrigin() == edge.getTarget()) {
						targetImage = mapping.getImage();
						// cannot perform check for current edge if involved
						// mappings incomplete.
						//
						if (targetImage == null) continue edgeLoop;
					}
					
					// involved images found.
					if (sourceImage != null && targetImage != null) {
						imagesFound = true;
						break mappingLoop;
					}
					
				}
				if (!imagesFound) continue edgeLoop;
				
				for (Edge iEdge : sourceImage.getOutgoing()) {
					// if rhs edge of same type is found between origins, the
					// edge is not <<delete>>
					if (iEdge.getTarget() == targetImage && iEdge.getType() == edge.getType()) {
						continue edgeLoop;
					}
				}
				// no rhs edge of same type is found between images. So the edge
				// is <<delete>>.
				//
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, DIAGNOSTIC_SOURCE, 0,
						"_UI_GenericConstraint_diagnostic", new Object[] {
								"rule_createdNodesNotAbstract", getObjectLabel(edge, context) },
						new Object[] { edge }, context,
						"_EcoreConstraint_Msg_Rule_deletedEdgesNotDerived"));
				return false;
				
			}
		}
		return true;
		
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributeCondition(AttributeCondition attributeCondition,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateParameter(Parameter parameter, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
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
		if (result || diagnostics != null) result &= validateParameter_nameRequired(parameter, diagnostics, context);
		return result;
	}
	
	/**
	 * Validates the nameRequired constraint of '<em>Parameter</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean validateParameter_nameRequired(Parameter parameter, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
        if (parameter_nameRequiredInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.PARAMETER);
			
			EAnnotation ocl = HenshinPackage.Literals.PARAMETER.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("nameRequired");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				parameter_nameRequiredInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			parameter_nameRequiredInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("nameRequired.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("nameRequired.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(parameter_nameRequiredInvOCL);
		
		if (!query.check(parameter)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = parameter_nameRequiredInvOCL
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
						 new Object[] { "nameRequired", getObjectLabel(parameter, context) },
						 new Object[] { parameter },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGraph(Graph graph, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
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
		if (result || diagnostics != null) result &= validateGraph_uniqueNodeNames(graph, diagnostics, context);
		return result;
	}
	
	/**
	 * Validates the uniqueNodeNames constraint of '<em>Graph</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean validateGraph_uniqueNodeNames(Graph graph, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
        if (graph_uniqueNodeNamesInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.GRAPH);
			
			EAnnotation ocl = HenshinPackage.Literals.GRAPH.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("uniqueNodeNames");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				graph_uniqueNodeNamesInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			graph_uniqueNodeNamesInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("uniqueNodeNames.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("uniqueNodeNames.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(graph_uniqueNodeNamesInvOCL);
		
		if (!query.check(graph)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = graph_uniqueNodeNamesInvOCL
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
						 new Object[] { "uniqueNodeNames", getObjectLabel(graph, context) },
						 new Object[] { graph },
						 context, addMsg));
			}
			return false;
		}
		return true;
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapping(Mapping mapping, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(mapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(mapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(mapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(mapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(mapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(mapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(mapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(mapping, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(mapping, diagnostics, context);
		if (result || diagnostics != null) result &= validateMapping_ruleMapping_TypeEquality(mapping, diagnostics, context);
		return result;
	}
	
	/**
	 * Validates the ruleMapping_TypeEquality constraint of '<em>Mapping</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapping_ruleMapping_TypeEquality(Mapping mapping,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
        if (mapping_ruleMapping_TypeEqualityInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.MAPPING);
			
			EAnnotation ocl = HenshinPackage.Literals.MAPPING.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("ruleMapping_TypeEquality");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				mapping_ruleMapping_TypeEqualityInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			mapping_ruleMapping_TypeEqualityInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("ruleMapping_TypeEquality.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("ruleMapping_TypeEquality.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(mapping_ruleMapping_TypeEqualityInvOCL);
		
		if (!query.check(mapping)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = mapping_ruleMapping_TypeEqualityInvOCL
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
						 new Object[] { "ruleMapping_TypeEquality", getObjectLabel(mapping, context) },
						 new Object[] { mapping },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		if (result || diagnostics != null) result &= validateNode_uniqueAttributeTypes(node, diagnostics, context);
		return result;
	}
	
	/**
	 * Validates the uniqueAttributeTypes constraint of '<em>Node</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean validateNode_uniqueAttributeTypes(Node node, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
        if (node_uniqueAttributeTypesInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.NODE);
			
			EAnnotation ocl = HenshinPackage.Literals.NODE.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("uniqueAttributeTypes");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				node_uniqueAttributeTypesInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			node_uniqueAttributeTypesInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("uniqueAttributeTypes.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("uniqueAttributeTypes.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(node_uniqueAttributeTypesInvOCL);
		
		if (!query.check(node)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = node_uniqueAttributeTypesInvOCL
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
						 new Object[] { "uniqueAttributeTypes", getObjectLabel(node, context) },
						 new Object[] { node },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttribute(Attribute attribute, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(attribute, diagnostics, context);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		if (result || diagnostics != null) result &= validateEdge_equalParentGraphs(edge, diagnostics, context);
		return result;
	}
	
	/**
	 * Validates the equalParentGraphs constraint of '<em>Edge</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean validateEdge_equalParentGraphs(Edge edge, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
        if (edge_equalParentGraphsInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.EDGE);
			
			EAnnotation ocl = HenshinPackage.Literals.EDGE.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("equalParentGraphs");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				edge_equalParentGraphsInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			edge_equalParentGraphsInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("equalParentGraphs.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("equalParentGraphs.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(edge_equalParentGraphsInvOCL);
		
		if (!query.check(edge)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = edge_equalParentGraphsInvOCL
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
						 new Object[] { "equalParentGraphs", getObjectLabel(edge, context) },
						 new Object[] { edge },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransformationUnit(TransformationUnit transformationUnit,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
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
		if (result || diagnostics != null) result &= validateTransformationUnit_uniqueParameterNames(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationUnit_parameterMappingsPointToDirectSubUnit(transformationUnit, diagnostics, context);
		return result;
	}
	
	/**
	 * Validates the uniqueParameterNames constraint of '
	 * <em>Transformation Unit</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public boolean validateTransformationUnit_uniqueParameterNames(
			TransformationUnit transformationUnit, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
        if (transformationUnit_uniqueParameterNamesInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.TRANSFORMATION_UNIT);
			
			EAnnotation ocl = HenshinPackage.Literals.TRANSFORMATION_UNIT.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("uniqueParameterNames");
			EAnnotation henshinOclAnnotation = EcoreFactoryImpl.eINSTANCE.createEAnnotation();
			henshinOclAnnotation.setSource(OCL_ANNOTATION_SOURCE);
			
			try {
				transformationUnit_uniqueParameterNamesInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
			
			transformationUnit_uniqueParameterNamesInvOCL.getEAnnotations().add(henshinOclAnnotation);
			
			String msg = ocl.getDetails().get("uniqueParameterNames.Msg");
			if (msg != null && msg.length() > 0) {
				henshinOclAnnotation.getDetails().put("Msg", msg);
			}// if
			
			String sev = ocl.getDetails().get("uniqueParameterNames.Severity");
			if (sev != null && sev.length() > 0) {
				sev = sev.toLowerCase();
				if (HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.containsKey(sev))
					henshinOclAnnotation.getDetails().put("Severity",
							HENSHIN_SEVERITY_2_DIAGNOSTIC_MAP.get(sev));
			}// if
			
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(transformationUnit_uniqueParameterNamesInvOCL);
		
		if (!query.check(transformationUnit)) {
			if (diagnostics != null) {
			
				EAnnotation henshinAnnotation = transformationUnit_uniqueParameterNamesInvOCL
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
						 new Object[] { "uniqueParameterNames", getObjectLabel(transformationUnit, context) },
						 new Object[] { transformationUnit },
						 context, addMsg));
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Validates the parameterMappingsPointToDirectSubUnit constraint of '
	 * <em>Transformation Unit</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated NOT
	 * @author Gregor Bonifer
	 * @author Stefan Jurack (sjurack)
	 * 
	 */
	public boolean validateTransformationUnit_parameterMappingsPointToDirectSubUnit(
			TransformationUnit transformationUnit, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		
		for (ParameterMapping pm : transformationUnit.getParameterMappings()) {
			if (transformationUnit.getParameters().contains(pm.getSource())) {
				if (pm.getTarget() != null
						&& !transformationUnit.getSubUnits(false)
								.contains(pm.getTarget().getUnit())) {
					diagnostics
							.add(createDiagnostic(
									Diagnostic.ERROR,
									DIAGNOSTIC_SOURCE,
									0,
									"_UI_GenericConstraint_diagnostic",
									new Object[] {
											"transformationUnit_parameterMappingsPointToDirectSubUnit",
											getObjectLabel(transformationUnit, context) },
									new Object[] { transformationUnit }, context,
									"_EcoreConstraint_Msg_TransformationUnit_parameterMappingsPointToDirectSubUnit"));
					return false;
				}
			}
			
			if (transformationUnit.getParameters().contains(pm.getTarget())) {
				if (pm.getSource() != null
						&& !transformationUnit.getSubUnits(false)
								.contains(pm.getSource().getUnit())) {
					diagnostics
							.add(createDiagnostic(
									Diagnostic.ERROR,
									DIAGNOSTIC_SOURCE,
									0,
									"_UI_GenericConstraint_diagnostic",
									new Object[] {
											"transformationUnit_parameterMappingsPointToDirectSubUnit",
											getObjectLabel(transformationUnit, context) },
									new Object[] { transformationUnit }, context,
									"_EcoreConstraint_Msg_TransformationUnit_parameterMappingsPointToDirectSubUnit"));
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
	public boolean validateIndependentUnit(IndependentUnit independentUnit,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
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
		if (result || diagnostics != null) result &= validateTransformationUnit_uniqueParameterNames(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationUnit_parameterMappingsPointToDirectSubUnit(independentUnit, diagnostics, context);
		return result;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSequentialUnit(SequentialUnit sequentialUnit,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
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
		if (result || diagnostics != null) result &= validateTransformationUnit_uniqueParameterNames(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationUnit_parameterMappingsPointToDirectSubUnit(sequentialUnit, diagnostics, context);
		return result;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConditionalUnit(ConditionalUnit conditionalUnit,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
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
		if (result || diagnostics != null) result &= validateTransformationUnit_uniqueParameterNames(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationUnit_parameterMappingsPointToDirectSubUnit(conditionalUnit, diagnostics, context);
		return result;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePriorityUnit(PriorityUnit priorityUnit, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
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
		if (result || diagnostics != null) result &= validateTransformationUnit_uniqueParameterNames(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationUnit_parameterMappingsPointToDirectSubUnit(priorityUnit, diagnostics, context);
		return result;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIteratedUnit(IteratedUnit iteratedUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationUnit_uniqueParameterNames(iteratedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationUnit_parameterMappingsPointToDirectSubUnit(iteratedUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLoopUnit(LoopUnit loopUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationUnit_uniqueParameterNames(loopUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransformationUnit_parameterMappingsPointToDirectSubUnit(loopUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNestedCondition(NestedCondition nestedCondition,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_NoCircularContainment(nestedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMultiplicityConforms(nestedCondition, diagnostics, context);
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
	public boolean validateNestedCondition_mappingOriginContainedInParentCondition(
			NestedCondition nestedCondition, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		
		Graph graph = findContainingGraph(nestedCondition);
		if (graph != null && graph.eContainer() instanceof NestedCondition) {
			for (Mapping mapping : nestedCondition.getMappings()) {
				if (mapping.getOrigin() != null && mapping.getOrigin().getGraph() != graph) {
					diagnostics
							.add(createDiagnostic(
									Diagnostic.ERROR,
									DIAGNOSTIC_SOURCE,
									0,
									"_UI_GenericConstraint_diagnostic",
									new Object[] {
											"nestedCondition_mappingOriginContainedInParentCondition",
											getObjectLabel(nestedCondition, context) },
									new Object[] { nestedCondition }, context,
									"_EcoreConstraint_Msg_NestedCondition_mappingOriginContainedInParentCondition"));
					return false;
				}
				
			}
		}
		return true;
	}
	
	/**
	 * Helper for
	 * {@link HenshinValidator#validateNestedCondition_mappingOriginContainedInParentCondition(NestedCondition, DiagnosticChain, Map)}
	 * Returns the nearest graph containing the given {@link NestedCondition}.
	 * 
	 * @param nestedCondition
	 * @return
	 * @generated NOT
	 * @author Gregor Bonifer
	 * @author Stefan Jurack (sjurack)
	 */
	protected Graph findContainingGraph(NestedCondition nestedCondition) {
		EObject container = nestedCondition.eContainer();
		while (container != null) {
			if (container instanceof Graph) return (Graph) container;
			container = container.eContainer();
		}
		return null;
	}
	
	/**
	 * Validates the mappingImageContainedInCurrent constraint of '
	 * <em>Nested Condition</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateNestedCondition_mappingImageContainedInCurrent(
			NestedCondition nestedCondition, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		for (Mapping mapping : nestedCondition.getMappings()) {
			if (!nestedCondition.getConclusion().getNodes().contains(mapping.getImage())) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, DIAGNOSTIC_SOURCE, 0,
						"_UI_GenericConstraint_diagnostic",
						new Object[] { "nestedCondition_mappingImageContainedInCurrent",
								getObjectLabel(nestedCondition, context) }, new Object[] {
								nestedCondition, mapping }, context,
						"_EcoreConstraint_Msg_NestedCondition_mappingImageContainedInCurrent"));
				return false;
			}
		}
		return true;
		
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula(Formula formula, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
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
	public boolean validateXor(Xor xor, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(xor, diagnostics, context);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateParameterMapping(ParameterMapping parameterMapping,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(parameterMapping, diagnostics, context);
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
	
} // HenshinValidator
