/**
 */
package nestedcondition.util;

import java.util.Map;

import nestedcondition.*;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see nestedcondition.NestedconditionPackage
 * @generated
 */
public class NestedconditionValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final NestedconditionValidator INSTANCE = new NestedconditionValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "nestedcondition";

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
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedconditionValidator() {
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
	  return NestedconditionPackage.eINSTANCE;
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
			case NestedconditionPackage.NESTED_CONSTRAINT:
				return validateNestedConstraint((NestedConstraint)value, diagnostics, context);
			case NestedconditionPackage.NESTED_CONDITION:
				return validateNestedCondition((NestedCondition)value, diagnostics, context);
			case NestedconditionPackage.VARIABLE:
				return validateVariable((Variable)value, diagnostics, context);
			case NestedconditionPackage.QUANTIFIED_CONDITION:
				return validateQuantifiedCondition((QuantifiedCondition)value, diagnostics, context);
			case NestedconditionPackage.TRUE:
				return validateTrue((True)value, diagnostics, context);
			case NestedconditionPackage.FORMULA:
				return validateFormula((Formula)value, diagnostics, context);
			case NestedconditionPackage.MORPHISM:
				return validateMorphism((Morphism)value, diagnostics, context);
			case NestedconditionPackage.NODE_MAPPING:
				return validateNodeMapping((NodeMapping)value, diagnostics, context);
			case NestedconditionPackage.EDGE_MAPPING:
				return validateEdgeMapping((EdgeMapping)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNestedConstraint(NestedConstraint nestedConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(nestedConstraint, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(nestedConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(nestedConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(nestedConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(nestedConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(nestedConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(nestedConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(nestedConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(nestedConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validateNestedConstraint_ConstraintDomainIsEmpty(nestedConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validateNestedConstraint_RootConditionDomainIsConatraintDomain(nestedConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validateNestedConstraint_TypeGraphConsistency(nestedConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validateNestedConstraint_HostGraphConsistency(nestedConstraint, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ConstraintDomainIsEmpty constraint of '<em>Nested Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String NESTED_CONSTRAINT__CONSTRAINT_DOMAIN_IS_EMPTY__EEXPRESSION = "self.domain.nodes -> isEmpty() and self.domain.edges -> isEmpty()";

	/**
	 * Validates the ConstraintDomainIsEmpty constraint of '<em>Nested Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNestedConstraint_ConstraintDomainIsEmpty(NestedConstraint nestedConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.NESTED_CONSTRAINT,
				 nestedConstraint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ConstraintDomainIsEmpty",
				 NESTED_CONSTRAINT__CONSTRAINT_DOMAIN_IS_EMPTY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the RootConditionDomainIsConatraintDomain constraint of '<em>Nested Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String NESTED_CONSTRAINT__ROOT_CONDITION_DOMAIN_IS_CONATRAINT_DOMAIN__EEXPRESSION = "self.condition.domain = self.domain";

	/**
	 * Validates the RootConditionDomainIsConatraintDomain constraint of '<em>Nested Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNestedConstraint_RootConditionDomainIsConatraintDomain(NestedConstraint nestedConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.NESTED_CONSTRAINT,
				 nestedConstraint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "RootConditionDomainIsConatraintDomain",
				 NESTED_CONSTRAINT__ROOT_CONDITION_DOMAIN_IS_CONATRAINT_DOMAIN__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the TypeGraphConsistency constraint of '<em>Nested Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String NESTED_CONSTRAINT__TYPE_GRAPH_CONSISTENCY__EEXPRESSION = "graph::Graph.allInstances() -> forAll(g|g.typegraph = self.typeGraph)";

	/**
	 * Validates the TypeGraphConsistency constraint of '<em>Nested Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNestedConstraint_TypeGraphConsistency(NestedConstraint nestedConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.NESTED_CONSTRAINT,
				 nestedConstraint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "TypeGraphConsistency",
				 NESTED_CONSTRAINT__TYPE_GRAPH_CONSISTENCY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the HostGraphConsistency constraint of '<em>Nested Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String NESTED_CONSTRAINT__HOST_GRAPH_CONSISTENCY__EEXPRESSION = "graph::Graph.allInstances() -> forAll(g|g.edges -> forAll(e|g.nodes -> includes(e.source) and g.nodes -> includes(e.target)))";

	/**
	 * Validates the HostGraphConsistency constraint of '<em>Nested Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNestedConstraint_HostGraphConsistency(NestedConstraint nestedConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.NESTED_CONSTRAINT,
				 nestedConstraint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "HostGraphConsistency",
				 NESTED_CONSTRAINT__HOST_GRAPH_CONSISTENCY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
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
	public boolean validateVariable(Variable variable, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(variable, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateQuantifiedCondition(QuantifiedCondition quantifiedCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(quantifiedCondition, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(quantifiedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(quantifiedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(quantifiedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(quantifiedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(quantifiedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(quantifiedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(quantifiedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(quantifiedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validateQuantifiedCondition_NestedDomainIsCoDomain(quantifiedCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validateQuantifiedCondition_MorphismIsFromDomainToCoDomain(quantifiedCondition, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the NestedDomainIsCoDomain constraint of '<em>Quantified Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String QUANTIFIED_CONDITION__NESTED_DOMAIN_IS_CO_DOMAIN__EEXPRESSION = "self.condition.domain = self.codomain";

	/**
	 * Validates the NestedDomainIsCoDomain constraint of '<em>Quantified Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateQuantifiedCondition_NestedDomainIsCoDomain(QuantifiedCondition quantifiedCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.QUANTIFIED_CONDITION,
				 quantifiedCondition,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "NestedDomainIsCoDomain",
				 QUANTIFIED_CONDITION__NESTED_DOMAIN_IS_CO_DOMAIN__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the MorphismIsFromDomainToCoDomain constraint of '<em>Quantified Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String QUANTIFIED_CONDITION__MORPHISM_IS_FROM_DOMAIN_TO_CO_DOMAIN__EEXPRESSION = "self.morphism.from = self.domain and self.morphism.to = self.codomain";

	/**
	 * Validates the MorphismIsFromDomainToCoDomain constraint of '<em>Quantified Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateQuantifiedCondition_MorphismIsFromDomainToCoDomain(QuantifiedCondition quantifiedCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.QUANTIFIED_CONDITION,
				 quantifiedCondition,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "MorphismIsFromDomainToCoDomain",
				 QUANTIFIED_CONDITION__MORPHISM_IS_FROM_DOMAIN_TO_CO_DOMAIN__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTrue(True true_, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(true_, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula(Formula formula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(formula, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validateFormula_ArgumentsDomainConsistency(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validateFormula_OneArgumentForNOT(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validateFormula_AtLeastTwoArgumentForANDOR(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validateFormula_TwoArgumentForIMPLEQUALXOR(formula, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ArgumentsDomainConsistency constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String FORMULA__ARGUMENTS_DOMAIN_CONSISTENCY__EEXPRESSION = "self.arguments -> forAll(cond|cond.domain = self.domain)";

	/**
	 * Validates the ArgumentsDomainConsistency constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula_ArgumentsDomainConsistency(Formula formula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.FORMULA,
				 formula,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ArgumentsDomainConsistency",
				 FORMULA__ARGUMENTS_DOMAIN_CONSISTENCY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the OneArgumentForNOT constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String FORMULA__ONE_ARGUMENT_FOR_NOT__EEXPRESSION = "(self.operator = laxcondition::Operator::NOT) implies (self.arguments -> size() = 1)";

	/**
	 * Validates the OneArgumentForNOT constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula_OneArgumentForNOT(Formula formula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.FORMULA,
				 formula,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "OneArgumentForNOT",
				 FORMULA__ONE_ARGUMENT_FOR_NOT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the AtLeastTwoArgumentForANDOR constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String FORMULA__AT_LEAST_TWO_ARGUMENT_FOR_ANDOR__EEXPRESSION = "((self.operator = laxcondition::Operator::AND) or (self.operator = laxcondition::Operator::OR)) implies (self.arguments -> size() > 1)";

	/**
	 * Validates the AtLeastTwoArgumentForANDOR constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula_AtLeastTwoArgumentForANDOR(Formula formula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.FORMULA,
				 formula,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "AtLeastTwoArgumentForANDOR",
				 FORMULA__AT_LEAST_TWO_ARGUMENT_FOR_ANDOR__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the TwoArgumentForIMPLEQUALXOR constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String FORMULA__TWO_ARGUMENT_FOR_IMPLEQUALXOR__EEXPRESSION = "((self.operator = laxcondition::Operator::IMPLIES) or (self.operator = laxcondition::Operator::EQUIVALENT) or (self.operator = laxcondition::Operator::XOR)) implies (self.arguments -> size() = 2)";

	/**
	 * Validates the TwoArgumentForIMPLEQUALXOR constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula_TwoArgumentForIMPLEQUALXOR(Formula formula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.FORMULA,
				 formula,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "TwoArgumentForIMPLEQUALXOR",
				 FORMULA__TWO_ARGUMENT_FOR_IMPLEQUALXOR__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMorphism(Morphism morphism, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(morphism, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(morphism, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(morphism, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(morphism, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(morphism, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(morphism, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(morphism, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(morphism, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(morphism, diagnostics, context);
		if (result || diagnostics != null) result &= validateMorphism_NodeMappingIsFromDomainToCoDomain(morphism, diagnostics, context);
		if (result || diagnostics != null) result &= validateMorphism_EdgeMappingIsFromDomainToCoDomain(morphism, diagnostics, context);
		if (result || diagnostics != null) result &= validateMorphism_EdgeMappingConsistency(morphism, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the NodeMappingIsFromDomainToCoDomain constraint of '<em>Morphism</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String MORPHISM__NODE_MAPPING_IS_FROM_DOMAIN_TO_CO_DOMAIN__EEXPRESSION = "self.nodeMappings -> forAll(m|self.from.nodes -> includes(m.origin) and self.to.nodes -> includes(m.image))";

	/**
	 * Validates the NodeMappingIsFromDomainToCoDomain constraint of '<em>Morphism</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMorphism_NodeMappingIsFromDomainToCoDomain(Morphism morphism, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.MORPHISM,
				 morphism,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "NodeMappingIsFromDomainToCoDomain",
				 MORPHISM__NODE_MAPPING_IS_FROM_DOMAIN_TO_CO_DOMAIN__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the EdgeMappingIsFromDomainToCoDomain constraint of '<em>Morphism</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String MORPHISM__EDGE_MAPPING_IS_FROM_DOMAIN_TO_CO_DOMAIN__EEXPRESSION = "self.edgeMappings -> forAll(m|self.from.edges -> includes(m.origin) and self.to.edges -> includes(m.image))";

	/**
	 * Validates the EdgeMappingIsFromDomainToCoDomain constraint of '<em>Morphism</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMorphism_EdgeMappingIsFromDomainToCoDomain(Morphism morphism, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.MORPHISM,
				 morphism,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "EdgeMappingIsFromDomainToCoDomain",
				 MORPHISM__EDGE_MAPPING_IS_FROM_DOMAIN_TO_CO_DOMAIN__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the EdgeMappingConsistency constraint of '<em>Morphism</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String MORPHISM__EDGE_MAPPING_CONSISTENCY__EEXPRESSION = "self.edgeMappings -> forAll(em|self.nodeMappings -> exists(nm|nm.origin = em.origin.source and nm.image = em.image.source) and self.nodeMappings -> exists(nm|nm.origin = em.origin.target and nm.image = em.image.target))";

	/**
	 * Validates the EdgeMappingConsistency constraint of '<em>Morphism</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMorphism_EdgeMappingConsistency(Morphism morphism, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(NestedconditionPackage.Literals.MORPHISM,
				 morphism,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "EdgeMappingConsistency",
				 MORPHISM__EDGE_MAPPING_CONSISTENCY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNodeMapping(NodeMapping nodeMapping, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(nodeMapping, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEdgeMapping(EdgeMapping edgeMapping, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(edgeMapping, diagnostics, context);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //NestedconditionValidator
