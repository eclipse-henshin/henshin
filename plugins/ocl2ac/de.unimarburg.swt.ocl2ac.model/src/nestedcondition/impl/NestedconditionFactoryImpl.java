/**
 */
package nestedcondition.impl;

import nestedcondition.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NestedconditionFactoryImpl extends EFactoryImpl implements NestedconditionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NestedconditionFactory init() {
		try {
			NestedconditionFactory theNestedconditionFactory = (NestedconditionFactory)EPackage.Registry.INSTANCE.getEFactory(NestedconditionPackage.eNS_URI);
			if (theNestedconditionFactory != null) {
				return theNestedconditionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NestedconditionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedconditionFactoryImpl() {
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
			case NestedconditionPackage.NESTED_CONSTRAINT: return createNestedConstraint();
			case NestedconditionPackage.VARIABLE: return createVariable();
			case NestedconditionPackage.QUANTIFIED_CONDITION: return createQuantifiedCondition();
			case NestedconditionPackage.TRUE: return createTrue();
			case NestedconditionPackage.FORMULA: return createFormula();
			case NestedconditionPackage.MORPHISM: return createMorphism();
			case NestedconditionPackage.NODE_MAPPING: return createNodeMapping();
			case NestedconditionPackage.EDGE_MAPPING: return createEdgeMapping();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedConstraint createNestedConstraint() {
		NestedConstraintImpl nestedConstraint = new NestedConstraintImpl();
		return nestedConstraint;
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
	public QuantifiedCondition createQuantifiedCondition() {
		QuantifiedConditionImpl quantifiedCondition = new QuantifiedConditionImpl();
		return quantifiedCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public True createTrue() {
		TrueImpl true_ = new TrueImpl();
		return true_;
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
	public Morphism createMorphism() {
		MorphismImpl morphism = new MorphismImpl();
		return morphism;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeMapping createNodeMapping() {
		NodeMappingImpl nodeMapping = new NodeMappingImpl();
		return nodeMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EdgeMapping createEdgeMapping() {
		EdgeMappingImpl edgeMapping = new EdgeMappingImpl();
		return edgeMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedconditionPackage getNestedconditionPackage() {
		return (NestedconditionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NestedconditionPackage getPackage() {
		return NestedconditionPackage.eINSTANCE;
	}

} //NestedconditionFactoryImpl
