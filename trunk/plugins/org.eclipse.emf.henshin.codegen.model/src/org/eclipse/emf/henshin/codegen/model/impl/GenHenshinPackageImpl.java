/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model.impl;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.henshin.codegen.model.GenHenshin;
import org.eclipse.emf.henshin.codegen.model.GenHenshinFactory;
import org.eclipse.emf.henshin.codegen.model.GenHenshinPackage;
import org.eclipse.emf.henshin.codegen.model.GenRule;
import org.eclipse.emf.henshin.codegen.model.GenTransformation;
import org.eclipse.emf.henshin.codegen.model.GenUnit;

import org.eclipse.emf.henshin.codegen.model.TransformationEngine;
import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GenHenshinPackageImpl extends EPackageImpl implements GenHenshinPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genHenshinEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genTransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum transformationEngineEEnum = null;

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
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private GenHenshinPackageImpl() {
		super(eNS_URI, GenHenshinFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link GenHenshinPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static GenHenshinPackage init() {
		if (isInited) return (GenHenshinPackage)EPackage.Registry.INSTANCE.getEPackage(GenHenshinPackage.eNS_URI);

		// Obtain or create and register package
		GenHenshinPackageImpl theGenHenshinPackage = (GenHenshinPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof GenHenshinPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new GenHenshinPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		GenModelPackage.eINSTANCE.eClass();
		HenshinPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theGenHenshinPackage.createPackageContents();

		// Initialize created meta-data
		theGenHenshinPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGenHenshinPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(GenHenshinPackage.eNS_URI, theGenHenshinPackage);
		return theGenHenshinPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenHenshin() {
		return genHenshinEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenHenshin_PluginID() {
		return (EAttribute)genHenshinEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenHenshin_BaseDirectory() {
		return (EAttribute)genHenshinEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenHenshin_SourceDirectory() {
		return (EAttribute)genHenshinEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenHenshin_CopyrightText() {
		return (EAttribute)genHenshinEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenHenshin_InterfacePackage() {
		return (EAttribute)genHenshinEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenHenshin_InterfacePattern() {
		return (EAttribute)genHenshinEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenHenshin_ImplementationPackage() {
		return (EAttribute)genHenshinEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenHenshin_ImplementationPattern() {
		return (EAttribute)genHenshinEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenHenshin_SupressInterfaces() {
		return (EAttribute)genHenshinEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenHenshin_GenTransformations() {
		return (EReference)genHenshinEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenHenshin_GenModels() {
		return (EReference)genHenshinEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenTransformation() {
		return genTransformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenTransformation_Transformation() {
		return (EReference)genTransformationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenTransformation_TransformationClass() {
		return (EAttribute)genTransformationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenTransformation_GenPackages() {
		return (EReference)genTransformationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenTransformation_GenUnits() {
		return (EReference)genTransformationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenTransformation_GenHenshin() {
		return (EReference)genTransformationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenTransformation_Engine() {
		return (EAttribute)genTransformationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenUnit() {
		return genUnitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenUnit_Unit() {
		return (EReference)genUnitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenUnit_Method() {
		return (EAttribute)genUnitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenUnit_Public() {
		return (EAttribute)genUnitEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenUnit_GenTransformation() {
		return (EReference)genUnitEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenRule() {
		return genRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenRule_CheckDangling() {
		return (EAttribute)genRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenRule_CheckInverseDangling() {
		return (EAttribute)genRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTransformationEngine() {
		return transformationEngineEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenHenshinFactory getGenHenshinFactory() {
		return (GenHenshinFactory)getEFactoryInstance();
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
		genHenshinEClass = createEClass(GEN_HENSHIN);
		createEAttribute(genHenshinEClass, GEN_HENSHIN__PLUGIN_ID);
		createEAttribute(genHenshinEClass, GEN_HENSHIN__BASE_DIRECTORY);
		createEAttribute(genHenshinEClass, GEN_HENSHIN__SOURCE_DIRECTORY);
		createEAttribute(genHenshinEClass, GEN_HENSHIN__COPYRIGHT_TEXT);
		createEAttribute(genHenshinEClass, GEN_HENSHIN__INTERFACE_PACKAGE);
		createEAttribute(genHenshinEClass, GEN_HENSHIN__INTERFACE_PATTERN);
		createEAttribute(genHenshinEClass, GEN_HENSHIN__IMPLEMENTATION_PACKAGE);
		createEAttribute(genHenshinEClass, GEN_HENSHIN__IMPLEMENTATION_PATTERN);
		createEAttribute(genHenshinEClass, GEN_HENSHIN__SUPRESS_INTERFACES);
		createEReference(genHenshinEClass, GEN_HENSHIN__GEN_TRANSFORMATIONS);
		createEReference(genHenshinEClass, GEN_HENSHIN__GEN_MODELS);

		genTransformationEClass = createEClass(GEN_TRANSFORMATION);
		createEReference(genTransformationEClass, GEN_TRANSFORMATION__TRANSFORMATION);
		createEAttribute(genTransformationEClass, GEN_TRANSFORMATION__TRANSFORMATION_CLASS);
		createEReference(genTransformationEClass, GEN_TRANSFORMATION__GEN_PACKAGES);
		createEReference(genTransformationEClass, GEN_TRANSFORMATION__GEN_UNITS);
		createEReference(genTransformationEClass, GEN_TRANSFORMATION__GEN_HENSHIN);
		createEAttribute(genTransformationEClass, GEN_TRANSFORMATION__ENGINE);

		genUnitEClass = createEClass(GEN_UNIT);
		createEReference(genUnitEClass, GEN_UNIT__UNIT);
		createEAttribute(genUnitEClass, GEN_UNIT__METHOD);
		createEAttribute(genUnitEClass, GEN_UNIT__PUBLIC);
		createEReference(genUnitEClass, GEN_UNIT__GEN_TRANSFORMATION);

		genRuleEClass = createEClass(GEN_RULE);
		createEAttribute(genRuleEClass, GEN_RULE__CHECK_DANGLING);
		createEAttribute(genRuleEClass, GEN_RULE__CHECK_INVERSE_DANGLING);

		// Create enums
		transformationEngineEEnum = createEEnum(TRANSFORMATION_ENGINE);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		GenModelPackage theGenModelPackage = (GenModelPackage)EPackage.Registry.INSTANCE.getEPackage(GenModelPackage.eNS_URI);
		HenshinPackage theHenshinPackage = (HenshinPackage)EPackage.Registry.INSTANCE.getEPackage(HenshinPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		genRuleEClass.getESuperTypes().add(this.getGenUnit());

		// Initialize classes and features; add operations and parameters
		initEClass(genHenshinEClass, GenHenshin.class, "GenHenshin", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGenHenshin_PluginID(), theEcorePackage.getEString(), "pluginID", null, 0, 1, GenHenshin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenHenshin_BaseDirectory(), ecorePackage.getEString(), "baseDirectory", null, 0, 1, GenHenshin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenHenshin_SourceDirectory(), ecorePackage.getEString(), "sourceDirectory", null, 0, 1, GenHenshin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenHenshin_CopyrightText(), ecorePackage.getEString(), "copyrightText", null, 0, 1, GenHenshin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenHenshin_InterfacePackage(), ecorePackage.getEString(), "interfacePackage", null, 0, 1, GenHenshin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenHenshin_InterfacePattern(), ecorePackage.getEString(), "interfacePattern", null, 0, 1, GenHenshin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenHenshin_ImplementationPackage(), ecorePackage.getEString(), "implementationPackage", null, 0, 1, GenHenshin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenHenshin_ImplementationPattern(), ecorePackage.getEString(), "implementationPattern", null, 0, 1, GenHenshin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenHenshin_SupressInterfaces(), theEcorePackage.getEBoolean(), "supressInterfaces", null, 0, 1, GenHenshin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGenHenshin_GenTransformations(), this.getGenTransformation(), this.getGenTransformation_GenHenshin(), "genTransformations", null, 0, -1, GenHenshin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGenHenshin_GenModels(), theGenModelPackage.getGenModel(), null, "genModels", null, 0, -1, GenHenshin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(genHenshinEClass, theEcorePackage.getEString(), "getCopyrightComment", 0, 1, IS_UNIQUE, IS_ORDERED);

		EOperation op = addEOperation(genHenshinEClass, theEcorePackage.getEString(), "applyInterfacePattern", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "baseName", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(genHenshinEClass, theEcorePackage.getEString(), "applyImplementationPattern", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "baseName", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(genTransformationEClass, GenTransformation.class, "GenTransformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGenTransformation_Transformation(), theHenshinPackage.getTransformationSystem(), null, "transformation", null, 0, 1, GenTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenTransformation_TransformationClass(), ecorePackage.getEString(), "transformationClass", null, 0, 1, GenTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGenTransformation_GenPackages(), theGenModelPackage.getGenPackage(), null, "genPackages", null, 0, -1, GenTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGenTransformation_GenUnits(), this.getGenUnit(), this.getGenUnit_GenTransformation(), "genUnits", null, 0, -1, GenTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGenTransformation_GenHenshin(), this.getGenHenshin(), this.getGenHenshin_GenTransformations(), "genHenshin", null, 0, 1, GenTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenTransformation_Engine(), this.getTransformationEngine(), "engine", null, 0, 1, GenTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(genTransformationEClass, theGenModelPackage.getGenPackage(), "getGenPackage", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEPackage(), "ePackage", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(genTransformationEClass, theGenModelPackage.getGenClass(), "getGenClass", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEClass(), "eClass", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(genTransformationEClass, theEcorePackage.getEString(), "getTransformationClassFormatted", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(genUnitEClass, GenUnit.class, "GenUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGenUnit_Unit(), theHenshinPackage.getTransformationUnit(), null, "unit", null, 0, 1, GenUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenUnit_Method(), ecorePackage.getEString(), "method", null, 0, 1, GenUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenUnit_Public(), ecorePackage.getEBoolean(), "public", "true", 0, 1, GenUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGenUnit_GenTransformation(), this.getGenTransformation(), this.getGenTransformation_GenUnits(), "genTransformation", null, 0, 1, GenUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(genUnitEClass, theEcorePackage.getEString(), "getMethodFormatted", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(genUnitEClass, theEcorePackage.getEString(), "getParametersFormatted", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(genRuleEClass, GenRule.class, "GenRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGenRule_CheckDangling(), theEcorePackage.getEBoolean(), "checkDangling", null, 0, 1, GenRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenRule_CheckInverseDangling(), theEcorePackage.getEBoolean(), "checkInverseDangling", null, 0, 1, GenRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(genRuleEClass, theHenshinPackage.getRule(), "getRule", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(transformationEngineEEnum, TransformationEngine.class, "TransformationEngine");
		addEEnumLiteral(transformationEngineEEnum, TransformationEngine.INTERPRETER);
		addEEnumLiteral(transformationEngineEEnum, TransformationEngine.ADHOC);

		// Create resource
		createResource(eNS_URI);
	}

} //GenHenshinPackageImpl
