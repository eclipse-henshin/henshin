/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.SamalgebraPackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.impl.SamalgebraPackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.AttributeTypeUse;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationTermParameter;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUseFactory;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;
import org.eclipse.emf.henshin.sam.model.samannotation.impl.SamannotationPackageImpl;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samgraph.impl.SamgraphPackageImpl;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage;
import org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TermAlgebraUsePackageImpl extends EPackageImpl implements TermAlgebraUsePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeTypeUseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationTermParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationSymbolUseEClass = null;

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
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TermAlgebraUsePackageImpl() {
		super(eNS_URI, TermAlgebraUseFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TermAlgebraUsePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TermAlgebraUsePackage init() {
		if (isInited) return (TermAlgebraUsePackage)EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI);

		// Obtain or create and register package
		TermAlgebraUsePackageImpl theTermAlgebraUsePackage = (TermAlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TermAlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TermAlgebraUsePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		SamtypegraphPackageImpl theSamtypegraphPackage = (SamtypegraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI) instanceof SamtypegraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI) : SamtypegraphPackage.eINSTANCE);
		SamalgebraPackageImpl theSamalgebraPackage = (SamalgebraPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) instanceof SamalgebraPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) : SamalgebraPackage.eINSTANCE);
		AlgebraUsePackageImpl theAlgebraUsePackage = (AlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI) instanceof AlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI) : AlgebraUsePackage.eINSTANCE);
		SignaturePackageImpl theSignaturePackage = (SignaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) instanceof SignaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) : SignaturePackage.eINSTANCE);
		SamgraphPackageImpl theSamgraphPackage = (SamgraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) instanceof SamgraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) : SamgraphPackage.eINSTANCE);
		SamrulesPackageImpl theSamrulesPackage = (SamrulesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI) instanceof SamrulesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI) : SamrulesPackage.eINSTANCE);
		SamgraphconditionPackageImpl theSamgraphconditionPackage = (SamgraphconditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI) instanceof SamgraphconditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI) : SamgraphconditionPackage.eINSTANCE);
		SamtracePackageImpl theSamtracePackage = (SamtracePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI) instanceof SamtracePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI) : SamtracePackage.eINSTANCE);
		SamannotationPackageImpl theSamannotationPackage = (SamannotationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) instanceof SamannotationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) : SamannotationPackage.eINSTANCE);

		// Create package meta-data objects
		theTermAlgebraUsePackage.createPackageContents();
		theSamtypegraphPackage.createPackageContents();
		theSamalgebraPackage.createPackageContents();
		theAlgebraUsePackage.createPackageContents();
		theSignaturePackage.createPackageContents();
		theSamgraphPackage.createPackageContents();
		theSamrulesPackage.createPackageContents();
		theSamgraphconditionPackage.createPackageContents();
		theSamtracePackage.createPackageContents();
		theSamannotationPackage.createPackageContents();

		// Initialize created meta-data
		theTermAlgebraUsePackage.initializePackageContents();
		theSamtypegraphPackage.initializePackageContents();
		theSamalgebraPackage.initializePackageContents();
		theAlgebraUsePackage.initializePackageContents();
		theSignaturePackage.initializePackageContents();
		theSamgraphPackage.initializePackageContents();
		theSamrulesPackage.initializePackageContents();
		theSamgraphconditionPackage.initializePackageContents();
		theSamtracePackage.initializePackageContents();
		theSamannotationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTermAlgebraUsePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TermAlgebraUsePackage.eNS_URI, theTermAlgebraUsePackage);
		return theTermAlgebraUsePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeTypeUse() {
		return attributeTypeUseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeTypeUse_TheAttributeType() {
		return (EReference)attributeTypeUseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationTermParameter() {
		return operationTermParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationTermParameter_References() {
		return (EReference)operationTermParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationSymbolUse() {
		return operationSymbolUseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationSymbolUse_TheOperationSymbol() {
		return (EReference)operationSymbolUseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationSymbolUse_Operands() {
		return (EReference)operationSymbolUseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TermAlgebraUseFactory getTermAlgebraUseFactory() {
		return (TermAlgebraUseFactory)getEFactoryInstance();
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
		attributeTypeUseEClass = createEClass(ATTRIBUTE_TYPE_USE);
		createEReference(attributeTypeUseEClass, ATTRIBUTE_TYPE_USE__THE_ATTRIBUTE_TYPE);

		operationTermParameterEClass = createEClass(OPERATION_TERM_PARAMETER);
		createEReference(operationTermParameterEClass, OPERATION_TERM_PARAMETER__REFERENCES);

		operationSymbolUseEClass = createEClass(OPERATION_SYMBOL_USE);
		createEReference(operationSymbolUseEClass, OPERATION_SYMBOL_USE__THE_OPERATION_SYMBOL);
		createEReference(operationSymbolUseEClass, OPERATION_SYMBOL_USE__OPERANDS);
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
		SamtypegraphPackage theSamtypegraphPackage = (SamtypegraphPackage)EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI);
		SamannotationPackage theSamannotationPackage = (SamannotationPackage)EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI);
		SignaturePackage theSignaturePackage = (SignaturePackage)EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		attributeTypeUseEClass.getESuperTypes().add(this.getOperationTermParameter());
		operationTermParameterEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		operationSymbolUseEClass.getESuperTypes().add(this.getOperationTermParameter());

		// Initialize classes and features; add operations and parameters
		initEClass(attributeTypeUseEClass, AttributeTypeUse.class, "AttributeTypeUse", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeTypeUse_TheAttributeType(), theSamtypegraphPackage.getAttributeType(), null, "theAttributeType", null, 1, 1, AttributeTypeUse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(operationTermParameterEClass, OperationTermParameter.class, "OperationTermParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOperationTermParameter_References(), theSignaturePackage.getOperandSymbol(), null, "references", null, 0, 1, OperationTermParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(operationSymbolUseEClass, OperationSymbolUse.class, "OperationSymbolUse", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOperationSymbolUse_TheOperationSymbol(), theSignaturePackage.getOperationSymbol(), null, "theOperationSymbol", null, 1, 1, OperationSymbolUse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperationSymbolUse_Operands(), this.getOperationTermParameter(), null, "operands", null, 0, -1, OperationSymbolUse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //TermAlgebraUsePackageImpl
