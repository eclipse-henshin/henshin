/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samannotation;

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
 * @see org.eclipse.emf.henshin.sam.model.samannotation.SamannotationFactory
 * @model kind="package"
 * @generated
 */
public interface SamannotationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "samannotation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2015/Henshin/sam/samannotation";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "samannotation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SamannotationPackage eINSTANCE = org.eclipse.emf.henshin.sam.model.samannotation.impl.SamannotationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationImpl
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.impl.SamannotationPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Details</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__DETAILS = 2;

	/**
	 * The feature id for the '<em><b>Ref In Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__REF_IN_RULE = 3;

	/**
	 * The number of structural features of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationDetailsImpl <em>Annotation Details</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationDetailsImpl
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.impl.SamannotationPackageImpl#getAnnotationDetails()
	 * @generated
	 */
	int ANNOTATION_DETAILS = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_DETAILS__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_DETAILS__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Annotation Details</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_DETAILS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem <em>Annotated Elem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.impl.SamannotationPackageImpl#getAnnotatedElem()
	 * @generated
	 */
	int ANNOTATED_ELEM = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_ELEM__ANNOTATIONS = 0;

	/**
	 * The number of structural features of the '<em>Annotated Elem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_ELEM_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.Annotation
	 * @generated
	 */
	EClass getAnnotation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getSource()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Source();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getTarget()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_Target();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getDetails <em>Details</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Details</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getDetails()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_Details();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getRefInRule <em>Ref In Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ref In Rule</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.Annotation#getRefInRule()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_RefInRule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samannotation.AnnotationDetails <em>Annotation Details</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation Details</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.AnnotationDetails
	 * @generated
	 */
	EClass getAnnotationDetails();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samannotation.AnnotationDetails#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.AnnotationDetails#getKey()
	 * @see #getAnnotationDetails()
	 * @generated
	 */
	EAttribute getAnnotationDetails_Key();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samannotation.AnnotationDetails#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.AnnotationDetails#getValue()
	 * @see #getAnnotationDetails()
	 * @generated
	 */
	EAttribute getAnnotationDetails_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem <em>Annotated Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotated Elem</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem
	 * @generated
	 */
	EClass getAnnotatedElem();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem#getAnnotations()
	 * @see #getAnnotatedElem()
	 * @generated
	 */
	EReference getAnnotatedElem_Annotations();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SamannotationFactory getSamannotationFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationImpl
		 * @see org.eclipse.emf.henshin.sam.model.samannotation.impl.SamannotationPackageImpl#getAnnotation()
		 * @generated
		 */
		EClass ANNOTATION = eINSTANCE.getAnnotation();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__SOURCE = eINSTANCE.getAnnotation_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATION__TARGET = eINSTANCE.getAnnotation_Target();

		/**
		 * The meta object literal for the '<em><b>Details</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATION__DETAILS = eINSTANCE.getAnnotation_Details();

		/**
		 * The meta object literal for the '<em><b>Ref In Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATION__REF_IN_RULE = eINSTANCE.getAnnotation_RefInRule();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationDetailsImpl <em>Annotation Details</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationDetailsImpl
		 * @see org.eclipse.emf.henshin.sam.model.samannotation.impl.SamannotationPackageImpl#getAnnotationDetails()
		 * @generated
		 */
		EClass ANNOTATION_DETAILS = eINSTANCE.getAnnotationDetails();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION_DETAILS__KEY = eINSTANCE.getAnnotationDetails_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION_DETAILS__VALUE = eINSTANCE.getAnnotationDetails_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem <em>Annotated Elem</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem
		 * @see org.eclipse.emf.henshin.sam.model.samannotation.impl.SamannotationPackageImpl#getAnnotatedElem()
		 * @generated
		 */
		EClass ANNOTATED_ELEM = eINSTANCE.getAnnotatedElem();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATED_ELEM__ANNOTATIONS = eINSTANCE.getAnnotatedElem_Annotations();

	}

} //SamannotationPackage
