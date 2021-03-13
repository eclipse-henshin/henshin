/**
 */
package laxcondition.util;

import java.util.Map;

import laxcondition.*;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see laxcondition.LaxconditionPackage
 * @generated
 */
public class LaxconditionValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final LaxconditionValidator INSTANCE = new LaxconditionValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "laxcondition";

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
	public LaxconditionValidator() {
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
	  return LaxconditionPackage.eINSTANCE;
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
			case LaxconditionPackage.CONDITION:
				return validateCondition((Condition)value, diagnostics, context);
			case LaxconditionPackage.LAX_CONDITION:
				return validateLaxCondition((LaxCondition)value, diagnostics, context);
			case LaxconditionPackage.QUANTIFIED_LAX_CONDITION:
				return validateQuantifiedLaxCondition((QuantifiedLaxCondition)value, diagnostics, context);
			case LaxconditionPackage.TRUE:
				return validateTrue((True)value, diagnostics, context);
			case LaxconditionPackage.FORMULA:
				return validateFormula((Formula)value, diagnostics, context);
			case LaxconditionPackage.VARIABLE:
				return validateVariable((Variable)value, diagnostics, context);
			case LaxconditionPackage.QUANTIFIER:
				return validateQuantifier((Quantifier)value, diagnostics, context);
			case LaxconditionPackage.OPERATOR:
				return validateOperator((Operator)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCondition(Condition condition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(condition, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(condition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(condition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(condition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(condition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(condition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(condition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(condition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(condition, diagnostics, context);
		if (result || diagnostics != null) result &= validateCondition_TypeGraphConsistency(condition, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the TypeGraphConsistency constraint of '<em>Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String CONDITION__TYPE_GRAPH_CONSISTENCY__EEXPRESSION = "graph_0::Graph.allInstances() -> forAll(g|g.typegraph = self.typeGraph)";

	/**
	 * Validates the TypeGraphConsistency constraint of '<em>Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCondition_TypeGraphConsistency(Condition condition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(LaxconditionPackage.Literals.CONDITION,
				 condition,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "TypeGraphConsistency",
				 CONDITION__TYPE_GRAPH_CONSISTENCY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLaxCondition(LaxCondition laxCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(laxCondition, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateQuantifiedLaxCondition(QuantifiedLaxCondition quantifiedLaxCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(quantifiedLaxCondition, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(quantifiedLaxCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(quantifiedLaxCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(quantifiedLaxCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(quantifiedLaxCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(quantifiedLaxCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(quantifiedLaxCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(quantifiedLaxCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(quantifiedLaxCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validateQuantifiedLaxCondition_HostGraphConsistency(quantifiedLaxCondition, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the HostGraphConsistency constraint of '<em>Quantified Lax Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String QUANTIFIED_LAX_CONDITION__HOST_GRAPH_CONSISTENCY__EEXPRESSION = "self.graph.edges -> forAll(e|self.graph.nodes -> includes(e.source) and self.graph.nodes -> includes(e.target))";

	/**
	 * Validates the HostGraphConsistency constraint of '<em>Quantified Lax Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateQuantifiedLaxCondition_HostGraphConsistency(QuantifiedLaxCondition quantifiedLaxCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(LaxconditionPackage.Literals.QUANTIFIED_LAX_CONDITION,
				 quantifiedLaxCondition,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "HostGraphConsistency",
				 QUANTIFIED_LAX_CONDITION__HOST_GRAPH_CONSISTENCY__EEXPRESSION,
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
		if (result || diagnostics != null) result &= validateFormula_OneArgumentForNOT(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validateFormula_AtLeastTwoArgumentForANDOR(formula, diagnostics, context);
		if (result || diagnostics != null) result &= validateFormula_TwoArgumentForIMPLEQUALXOR(formula, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the OneArgumentForNOT constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String FORMULA__ONE_ARGUMENT_FOR_NOT__EEXPRESSION = "(self.op = Operator::NOT) implies (self.arguments -> size() = 1)";

	/**
	 * Validates the OneArgumentForNOT constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula_OneArgumentForNOT(Formula formula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(LaxconditionPackage.Literals.FORMULA,
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
	protected static final String FORMULA__AT_LEAST_TWO_ARGUMENT_FOR_ANDOR__EEXPRESSION = "((self.op = Operator::AND) or (self.op = Operator::OR)) implies (self.arguments -> size() > 1)";

	/**
	 * Validates the AtLeastTwoArgumentForANDOR constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula_AtLeastTwoArgumentForANDOR(Formula formula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(LaxconditionPackage.Literals.FORMULA,
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
	protected static final String FORMULA__TWO_ARGUMENT_FOR_IMPLEQUALXOR__EEXPRESSION = "((self.op = Operator::IMPLIES) or (self.op = Operator::EQUIVALENT) or (self.op = Operator::XOR)) implies (self.arguments -> size() = 2)";

	/**
	 * Validates the TwoArgumentForIMPLEQUALXOR constraint of '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula_TwoArgumentForIMPLEQUALXOR(Formula formula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(LaxconditionPackage.Literals.FORMULA,
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
	public boolean validateVariable(Variable variable, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(variable, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateQuantifier(Quantifier quantifier, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOperator(Operator operator, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
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

} //LaxconditionValidator
