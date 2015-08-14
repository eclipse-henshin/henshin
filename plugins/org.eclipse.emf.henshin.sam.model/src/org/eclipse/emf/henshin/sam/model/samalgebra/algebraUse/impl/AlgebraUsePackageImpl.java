/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.SamalgebraPackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUseFactory;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AttributeUse;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationParameter;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse;
import org.eclipse.emf.henshin.sam.model.samalgebra.impl.SamalgebraPackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.TermAlgebraUsePackageImpl;
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
public class AlgebraUsePackageImpl extends EPackageImpl implements AlgebraUsePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeUseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationUseEClass = null;

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
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AlgebraUsePackageImpl() {
		super(eNS_URI, AlgebraUseFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link AlgebraUsePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AlgebraUsePackage init() {
		if (isInited) return (AlgebraUsePackage)EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI);

		// Obtain or create and register package
		AlgebraUsePackageImpl theAlgebraUsePackage = (AlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AlgebraUsePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		SamtypegraphPackageImpl theSamtypegraphPackage = (SamtypegraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI) instanceof SamtypegraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI) : SamtypegraphPackage.eINSTANCE);
		SamalgebraPackageImpl theSamalgebraPackage = (SamalgebraPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) instanceof SamalgebraPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) : SamalgebraPackage.eINSTANCE);
		SignaturePackageImpl theSignaturePackage = (SignaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) instanceof SignaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) : SignaturePackage.eINSTANCE);
		TermAlgebraUsePackageImpl theTermAlgebraUsePackage = (TermAlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI) instanceof TermAlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI) : TermAlgebraUsePackage.eINSTANCE);
		SamgraphPackageImpl theSamgraphPackage = (SamgraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) instanceof SamgraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) : SamgraphPackage.eINSTANCE);
		SamrulesPackageImpl theSamrulesPackage = (SamrulesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI) instanceof SamrulesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI) : SamrulesPackage.eINSTANCE);
		SamgraphconditionPackageImpl theSamgraphconditionPackage = (SamgraphconditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI) instanceof SamgraphconditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI) : SamgraphconditionPackage.eINSTANCE);
		SamtracePackageImpl theSamtracePackage = (SamtracePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI) instanceof SamtracePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI) : SamtracePackage.eINSTANCE);
		SamannotationPackageImpl theSamannotationPackage = (SamannotationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) instanceof SamannotationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) : SamannotationPackage.eINSTANCE);

		// Create package meta-data objects
		theAlgebraUsePackage.createPackageContents();
		theSamtypegraphPackage.createPackageContents();
		theSamalgebraPackage.createPackageContents();
		theSignaturePackage.createPackageContents();
		theTermAlgebraUsePackage.createPackageContents();
		theSamgraphPackage.createPackageContents();
		theSamrulesPackage.createPackageContents();
		theSamgraphconditionPackage.createPackageContents();
		theSamtracePackage.createPackageContents();
		theSamannotationPackage.createPackageContents();

		// Initialize created meta-data
		theAlgebraUsePackage.initializePackageContents();
		theSamtypegraphPackage.initializePackageContents();
		theSamalgebraPackage.initializePackageContents();
		theSignaturePackage.initializePackageContents();
		theTermAlgebraUsePackage.initializePackageContents();
		theSamgraphPackage.initializePackageContents();
		theSamrulesPackage.initializePackageContents();
		theSamgraphconditionPackage.initializePackageContents();
		theSamtracePackage.initializePackageContents();
		theSamannotationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAlgebraUsePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AlgebraUsePackage.eNS_URI, theAlgebraUsePackage);
		return theAlgebraUsePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationParameter() {
		return operationParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationParameter_References() {
		return (EReference)operationParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataElement() {
		return dataElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataElement_Sort() {
		return (EReference)dataElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataElement_Value() {
		return (EAttribute)dataElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeUse() {
		return attributeUseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeUse_TheAttribute() {
		return (EReference)attributeUseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationUse() {
		return operationUseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationUse_TheOperation() {
		return (EReference)operationUseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationUse_Operands() {
		return (EReference)operationUseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlgebraUseFactory getAlgebraUseFactory() {
		return (AlgebraUseFactory)getEFactoryInstance();
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
		operationParameterEClass = createEClass(OPERATION_PARAMETER);
		createEReference(operationParameterEClass, OPERATION_PARAMETER__REFERENCES);

		dataElementEClass = createEClass(DATA_ELEMENT);
		createEReference(dataElementEClass, DATA_ELEMENT__SORT);
		createEAttribute(dataElementEClass, DATA_ELEMENT__VALUE);

		attributeUseEClass = createEClass(ATTRIBUTE_USE);
		createEReference(attributeUseEClass, ATTRIBUTE_USE__THE_ATTRIBUTE);

		operationUseEClass = createEClass(OPERATION_USE);
		createEReference(operationUseEClass, OPERATION_USE__THE_OPERATION);
		createEReference(operationUseEClass, OPERATION_USE__OPERANDS);
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
		SamannotationPackage theSamannotationPackage = (SamannotationPackage)EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI);
		SignaturePackage theSignaturePackage = (SignaturePackage)EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI);
		SamgraphPackage theSamgraphPackage = (SamgraphPackage)EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI);
		SamalgebraPackage theSamalgebraPackage = (SamalgebraPackage)EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		operationParameterEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		dataElementEClass.getESuperTypes().add(this.getOperationParameter());
		attributeUseEClass.getESuperTypes().add(this.getOperationParameter());
		operationUseEClass.getESuperTypes().add(this.getOperationParameter());

		// Initialize classes and features; add operations and parameters
		initEClass(operationParameterEClass, OperationParameter.class, "OperationParameter", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOperationParameter_References(), theSignaturePackage.getOperandSymbol(), null, "references", null, 0, 1, OperationParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dataElementEClass, DataElement.class, "DataElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDataElement_Sort(), theSignaturePackage.getSort(), null, "sort", null, 0, 1, DataElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataElement_Value(), ecorePackage.getEString(), "value", null, 0, 1, DataElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeUseEClass, AttributeUse.class, "AttributeUse", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeUse_TheAttribute(), theSamgraphPackage.getAttribute(), null, "theAttribute", null, 0, 1, AttributeUse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(operationUseEClass, OperationUse.class, "OperationUse", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOperationUse_TheOperation(), theSamalgebraPackage.getOperation(), null, "theOperation", null, 1, 1, OperationUse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperationUse_Operands(), this.getOperationParameter(), null, "operands", null, 0, -1, OperationUse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //AlgebraUsePackageImpl
