/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinFactory
 * @model kind="package"
 * @generated
 */
public interface GenHenshinPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "codegen";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2011/Henshin/codegen";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "codegen";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GenHenshinPackage eINSTANCE = org.eclipse.emf.henshin.codegen.model.impl.GenHenshinPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl <em>Gen Henshin</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl
	 * @see org.eclipse.emf.henshin.codegen.model.impl.GenHenshinPackageImpl#getGenHenshin()
	 * @generated
	 */
	int GEN_HENSHIN = 0;

	/**
	 * The feature id for the '<em><b>Copyright Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_HENSHIN__COPYRIGHT_TEXT = 0;

	/**
	 * The feature id for the '<em><b>Directory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_HENSHIN__DIRECTORY = 1;

	/**
	 * The feature id for the '<em><b>Interface Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_HENSHIN__INTERFACE_PACKAGE = 2;

	/**
	 * The feature id for the '<em><b>Implementation Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_HENSHIN__IMPLEMENTATION_PACKAGE = 3;

	/**
	 * The feature id for the '<em><b>Gen Transformations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_HENSHIN__GEN_TRANSFORMATIONS = 4;

	/**
	 * The feature id for the '<em><b>Gen Models</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_HENSHIN__GEN_MODELS = 5;

	/**
	 * The number of structural features of the '<em>Gen Henshin</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_HENSHIN_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl <em>Gen Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl
	 * @see org.eclipse.emf.henshin.codegen.model.impl.GenHenshinPackageImpl#getGenTransformation()
	 * @generated
	 */
	int GEN_TRANSFORMATION = 1;

	/**
	 * The feature id for the '<em><b>Transformation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_TRANSFORMATION__TRANSFORMATION = 0;

	/**
	 * The feature id for the '<em><b>Transformation Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_TRANSFORMATION__TRANSFORMATION_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Gen Packages</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_TRANSFORMATION__GEN_PACKAGES = 2;

	/**
	 * The feature id for the '<em><b>Gen Units</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_TRANSFORMATION__GEN_UNITS = 3;

	/**
	 * The feature id for the '<em><b>Gen Henshin</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_TRANSFORMATION__GEN_HENSHIN = 4;

	/**
	 * The number of structural features of the '<em>Gen Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_TRANSFORMATION_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.codegen.model.impl.GenUnitImpl <em>Gen Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.codegen.model.impl.GenUnitImpl
	 * @see org.eclipse.emf.henshin.codegen.model.impl.GenHenshinPackageImpl#getGenUnit()
	 * @generated
	 */
	int GEN_UNIT = 2;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_UNIT__UNIT = 0;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_UNIT__METHOD = 1;

	/**
	 * The feature id for the '<em><b>Public</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_UNIT__PUBLIC = 2;

	/**
	 * The number of structural features of the '<em>Gen Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_UNIT_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.codegen.model.impl.GenRuleImpl <em>Gen Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.codegen.model.impl.GenRuleImpl
	 * @see org.eclipse.emf.henshin.codegen.model.impl.GenHenshinPackageImpl#getGenRule()
	 * @generated
	 */
	int GEN_RULE = 3;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_RULE__UNIT = GEN_UNIT__UNIT;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_RULE__METHOD = GEN_UNIT__METHOD;

	/**
	 * The feature id for the '<em><b>Public</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_RULE__PUBLIC = GEN_UNIT__PUBLIC;

	/**
	 * The feature id for the '<em><b>Check Dangling</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_RULE__CHECK_DANGLING = GEN_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Check Inverse Dangling</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_RULE__CHECK_INVERSE_DANGLING = GEN_UNIT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Gen Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_RULE_FEATURE_COUNT = GEN_UNIT_FEATURE_COUNT + 2;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin <em>Gen Henshin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Gen Henshin</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshin
	 * @generated
	 */
	EClass getGenHenshin();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getCopyrightText <em>Copyright Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Copyright Text</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshin#getCopyrightText()
	 * @see #getGenHenshin()
	 * @generated
	 */
	EAttribute getGenHenshin_CopyrightText();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getDirectory <em>Directory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Directory</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshin#getDirectory()
	 * @see #getGenHenshin()
	 * @generated
	 */
	EAttribute getGenHenshin_Directory();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getInterfacePackage <em>Interface Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Interface Package</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshin#getInterfacePackage()
	 * @see #getGenHenshin()
	 * @generated
	 */
	EAttribute getGenHenshin_InterfacePackage();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getImplementationPackage <em>Implementation Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation Package</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshin#getImplementationPackage()
	 * @see #getGenHenshin()
	 * @generated
	 */
	EAttribute getGenHenshin_ImplementationPackage();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getGenTransformations <em>Gen Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Gen Transformations</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshin#getGenTransformations()
	 * @see #getGenHenshin()
	 * @generated
	 */
	EReference getGenHenshin_GenTransformations();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.codegen.model.GenHenshin#getGenModels <em>Gen Models</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Gen Models</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshin#getGenModels()
	 * @see #getGenHenshin()
	 * @generated
	 */
	EReference getGenHenshin_GenModels();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation <em>Gen Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Gen Transformation</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenTransformation
	 * @generated
	 */
	EClass getGenTransformation();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getTransformation <em>Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Transformation</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenTransformation#getTransformation()
	 * @see #getGenTransformation()
	 * @generated
	 */
	EReference getGenTransformation_Transformation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getTransformationClass <em>Transformation Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transformation Class</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenTransformation#getTransformationClass()
	 * @see #getGenTransformation()
	 * @generated
	 */
	EAttribute getGenTransformation_TransformationClass();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenPackages <em>Gen Packages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Gen Packages</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenPackages()
	 * @see #getGenTransformation()
	 * @generated
	 */
	EReference getGenTransformation_GenPackages();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenUnits <em>Gen Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Gen Units</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenUnits()
	 * @see #getGenTransformation()
	 * @generated
	 */
	EReference getGenTransformation_GenUnits();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenHenshin <em>Gen Henshin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Gen Henshin</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenTransformation#getGenHenshin()
	 * @see #getGenTransformation()
	 * @generated
	 */
	EReference getGenTransformation_GenHenshin();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.codegen.model.GenUnit <em>Gen Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Gen Unit</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenUnit
	 * @generated
	 */
	EClass getGenUnit();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Unit</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenUnit#getUnit()
	 * @see #getGenUnit()
	 * @generated
	 */
	EReference getGenUnit_Unit();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Method</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenUnit#getMethod()
	 * @see #getGenUnit()
	 * @generated
	 */
	EAttribute getGenUnit_Method();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.codegen.model.GenUnit#isPublic <em>Public</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Public</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenUnit#isPublic()
	 * @see #getGenUnit()
	 * @generated
	 */
	EAttribute getGenUnit_Public();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.codegen.model.GenRule <em>Gen Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Gen Rule</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenRule
	 * @generated
	 */
	EClass getGenRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.codegen.model.GenRule#isCheckDangling <em>Check Dangling</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Check Dangling</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenRule#isCheckDangling()
	 * @see #getGenRule()
	 * @generated
	 */
	EAttribute getGenRule_CheckDangling();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.codegen.model.GenRule#isCheckInverseDangling <em>Check Inverse Dangling</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Check Inverse Dangling</em>'.
	 * @see org.eclipse.emf.henshin.codegen.model.GenRule#isCheckInverseDangling()
	 * @see #getGenRule()
	 * @generated
	 */
	EAttribute getGenRule_CheckInverseDangling();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GenHenshinFactory getGenHenshinFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl <em>Gen Henshin</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.codegen.model.impl.GenHenshinImpl
		 * @see org.eclipse.emf.henshin.codegen.model.impl.GenHenshinPackageImpl#getGenHenshin()
		 * @generated
		 */
		EClass GEN_HENSHIN = eINSTANCE.getGenHenshin();

		/**
		 * The meta object literal for the '<em><b>Copyright Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GEN_HENSHIN__COPYRIGHT_TEXT = eINSTANCE.getGenHenshin_CopyrightText();

		/**
		 * The meta object literal for the '<em><b>Directory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GEN_HENSHIN__DIRECTORY = eINSTANCE.getGenHenshin_Directory();

		/**
		 * The meta object literal for the '<em><b>Interface Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GEN_HENSHIN__INTERFACE_PACKAGE = eINSTANCE.getGenHenshin_InterfacePackage();

		/**
		 * The meta object literal for the '<em><b>Implementation Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GEN_HENSHIN__IMPLEMENTATION_PACKAGE = eINSTANCE.getGenHenshin_ImplementationPackage();

		/**
		 * The meta object literal for the '<em><b>Gen Transformations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GEN_HENSHIN__GEN_TRANSFORMATIONS = eINSTANCE.getGenHenshin_GenTransformations();

		/**
		 * The meta object literal for the '<em><b>Gen Models</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GEN_HENSHIN__GEN_MODELS = eINSTANCE.getGenHenshin_GenModels();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl <em>Gen Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl
		 * @see org.eclipse.emf.henshin.codegen.model.impl.GenHenshinPackageImpl#getGenTransformation()
		 * @generated
		 */
		EClass GEN_TRANSFORMATION = eINSTANCE.getGenTransformation();

		/**
		 * The meta object literal for the '<em><b>Transformation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GEN_TRANSFORMATION__TRANSFORMATION = eINSTANCE.getGenTransformation_Transformation();

		/**
		 * The meta object literal for the '<em><b>Transformation Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GEN_TRANSFORMATION__TRANSFORMATION_CLASS = eINSTANCE.getGenTransformation_TransformationClass();

		/**
		 * The meta object literal for the '<em><b>Gen Packages</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GEN_TRANSFORMATION__GEN_PACKAGES = eINSTANCE.getGenTransformation_GenPackages();

		/**
		 * The meta object literal for the '<em><b>Gen Units</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GEN_TRANSFORMATION__GEN_UNITS = eINSTANCE.getGenTransformation_GenUnits();

		/**
		 * The meta object literal for the '<em><b>Gen Henshin</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GEN_TRANSFORMATION__GEN_HENSHIN = eINSTANCE.getGenTransformation_GenHenshin();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.codegen.model.impl.GenUnitImpl <em>Gen Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.codegen.model.impl.GenUnitImpl
		 * @see org.eclipse.emf.henshin.codegen.model.impl.GenHenshinPackageImpl#getGenUnit()
		 * @generated
		 */
		EClass GEN_UNIT = eINSTANCE.getGenUnit();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GEN_UNIT__UNIT = eINSTANCE.getGenUnit_Unit();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GEN_UNIT__METHOD = eINSTANCE.getGenUnit_Method();

		/**
		 * The meta object literal for the '<em><b>Public</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GEN_UNIT__PUBLIC = eINSTANCE.getGenUnit_Public();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.codegen.model.impl.GenRuleImpl <em>Gen Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.codegen.model.impl.GenRuleImpl
		 * @see org.eclipse.emf.henshin.codegen.model.impl.GenHenshinPackageImpl#getGenRule()
		 * @generated
		 */
		EClass GEN_RULE = eINSTANCE.getGenRule();

		/**
		 * The meta object literal for the '<em><b>Check Dangling</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GEN_RULE__CHECK_DANGLING = eINSTANCE.getGenRule_CheckDangling();

		/**
		 * The meta object literal for the '<em><b>Check Inverse Dangling</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GEN_RULE__CHECK_INVERSE_DANGLING = eINSTANCE.getGenRule_CheckInverseDangling();

	}

} //GenHenshinPackage
