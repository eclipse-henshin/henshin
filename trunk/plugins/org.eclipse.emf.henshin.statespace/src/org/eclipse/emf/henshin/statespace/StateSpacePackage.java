/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see org.eclipse.emf.henshin.statespace.StateSpaceFactory
 * @model kind="package"
 * @generated
 */
public interface StateSpacePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "statespace";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2010/Henshin/StateSpace";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "statespace";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StateSpacePackage eINSTANCE = org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.statespace.impl.StateSpaceImpl <em>State Space</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpaceImpl
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getStateSpace()
	 * @generated
	 */
	int STATE_SPACE = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.statespace.impl.StorageImpl <em>Storage</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.StorageImpl
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getStorage()
	 * @generated
	 */
	int STORAGE = 5;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE__DATA = 0;

	/**
	 * The number of structural features of the '<em>Storage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__DATA = STORAGE__DATA;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__RULES = STORAGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__STATES = STORAGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Initial States</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__INITIAL_STATES = STORAGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Open States</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__OPEN_STATES = STORAGE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>State Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__STATE_COUNT = STORAGE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Transition Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__TRANSITION_COUNT = STORAGE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Equality Helper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__EQUALITY_HELPER = STORAGE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Layout Zoom Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__LAYOUT_ZOOM_LEVEL = STORAGE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Layout State Repulsion</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__LAYOUT_STATE_REPULSION = STORAGE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Layout Transition Attraction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__LAYOUT_TRANSITION_ATTRACTION = STORAGE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Layout Hide Labels</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__LAYOUT_HIDE_LABELS = STORAGE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Max State Distance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__MAX_STATE_DISTANCE = STORAGE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__PROPERTIES = STORAGE_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Supported Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__SUPPORTED_TYPES = STORAGE_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Supported Type Prefixes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__SUPPORTED_TYPE_PREFIXES = STORAGE_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>All Parameter Keys</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__ALL_PARAMETER_KEYS = STORAGE_FEATURE_COUNT + 15;

	/**
	 * The number of structural features of the '<em>State Space</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE_FEATURE_COUNT = STORAGE_FEATURE_COUNT + 16;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.statespace.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.StateImpl
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getState()
	 * @generated
	 */
	int STATE = 1;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__DATA = STORAGE__DATA;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__INDEX = STORAGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__INCOMING = STORAGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OUTGOING = STORAGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>State Space</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__STATE_SPACE = STORAGE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Hash Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__HASH_CODE = STORAGE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Derived From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__DERIVED_FROM = STORAGE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Open</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OPEN = STORAGE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__LOCATION = STORAGE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Object Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OBJECT_COUNT = STORAGE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Object Keys</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OBJECT_KEYS = STORAGE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__MODEL = STORAGE_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = STORAGE_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.statespace.impl.ModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.ModelImpl
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getModel()
	 * @generated
	 */
	int MODEL = 2;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__RESOURCE = 0;

	/**
	 * The feature id for the '<em><b>Emf Graph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__EMF_GRAPH = 1;

	/**
	 * The feature id for the '<em><b>Object Keys Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__OBJECT_KEYS_MAP = 2;

	/**
	 * The feature id for the '<em><b>Object Keys</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__OBJECT_KEYS = 3;

	/**
	 * The feature id for the '<em><b>Object Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__OBJECT_COUNT = 4;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.statespace.impl.TransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.TransitionImpl
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 3;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__DATA = STORAGE__DATA;

	/**
	 * The feature id for the '<em><b>Source</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SOURCE = STORAGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TARGET = STORAGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__RULE = STORAGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Match</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__MATCH = STORAGE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Parameter Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__PARAMETER_COUNT = STORAGE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Parameter Keys</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__PARAMETER_KEYS = STORAGE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = STORAGE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.statespace.impl.EqualityHelperImpl <em>Equality Helper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.EqualityHelperImpl
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getEqualityHelper()
	 * @generated
	 */
	int EQUALITY_HELPER = 4;

	/**
	 * The feature id for the '<em><b>Use Graph Equality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALITY_HELPER__USE_GRAPH_EQUALITY = 0;

	/**
	 * The feature id for the '<em><b>Use Object Keys</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALITY_HELPER__USE_OBJECT_KEYS = 1;

	/**
	 * The feature id for the '<em><b>Use Object Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALITY_HELPER__USE_OBJECT_ATTRIBUTES = 2;

	/**
	 * The number of structural features of the '<em>Equality Helper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALITY_HELPER_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.statespace.impl.ObjectKeyImpl <em>Object Key</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.ObjectKeyImpl
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getObjectKey()
	 * @generated
	 */
	int OBJECT_KEY = 6;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_KEY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_KEY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Object Key</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_KEY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '<em>Integer Array</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getIntegerArray()
	 * @generated
	 */
	int INTEGER_ARRAY = 7;


	/**
	 * The meta object id for the '<em>String Array</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getStringArray()
	 * @generated
	 */
	int STRING_ARRAY = 8;

	/**
	 * The meta object id for the '<em>EClass Array</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getEClassArray()
	 * @generated
	 */
	int ECLASS_ARRAY = 9;

	/**
	 * The meta object id for the '<em>Match</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.interpreter.util.Match
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getMatch()
	 * @generated
	 */
	int MATCH = 10;


	/**
	 * The meta object id for the '<em>Emf Graph</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.matching.EmfGraph
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getEmfGraph()
	 * @generated
	 */
	int EMF_GRAPH = 11;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.statespace.StateSpace <em>State Space</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Space</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace
	 * @generated
	 */
	EClass getStateSpace();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.statespace.StateSpace#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getStates()
	 * @see #getStateSpace()
	 * @generated
	 */
	EReference getStateSpace_States();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.statespace.StateSpace#getInitialStates <em>Initial States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Initial States</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getInitialStates()
	 * @see #getStateSpace()
	 * @generated
	 */
	EReference getStateSpace_InitialStates();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.statespace.StateSpace#getOpenStates <em>Open States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Open States</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getOpenStates()
	 * @see #getStateSpace()
	 * @generated
	 */
	EReference getStateSpace_OpenStates();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getStateCount <em>State Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State Count</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getStateCount()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_StateCount();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getTransitionCount <em>Transition Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transition Count</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getTransitionCount()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_TransitionCount();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.statespace.StateSpace#getEqualityHelper <em>Equality Helper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Equality Helper</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getEqualityHelper()
	 * @see #getStateSpace()
	 * @generated
	 */
	EReference getStateSpace_EqualityHelper();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getLayoutZoomLevel <em>Layout Zoom Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Layout Zoom Level</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getLayoutZoomLevel()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_LayoutZoomLevel();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getLayoutStateRepulsion <em>Layout State Repulsion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Layout State Repulsion</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getLayoutStateRepulsion()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_LayoutStateRepulsion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getLayoutTransitionAttraction <em>Layout Transition Attraction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Layout Transition Attraction</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getLayoutTransitionAttraction()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_LayoutTransitionAttraction();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#isLayoutHideLabels <em>Layout Hide Labels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Layout Hide Labels</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#isLayoutHideLabels()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_LayoutHideLabels();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getMaxStateDistance <em>Max State Distance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max State Distance</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getMaxStateDistance()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_MaxStateDistance();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.henshin.statespace.StateSpace#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Properties</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getProperties()
	 * @see #getStateSpace()
	 * @generated
	 */
	EReference getStateSpace_Properties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getSupportedTypes <em>Supported Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Supported Types</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getSupportedTypes()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_SupportedTypes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getSupportedTypePrefixes <em>Supported Type Prefixes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Supported Type Prefixes</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getSupportedTypePrefixes()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_SupportedTypePrefixes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getAllParameterKeys <em>All Parameter Keys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>All Parameter Keys</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getAllParameterKeys()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_AllParameterKeys();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.statespace.StateSpace#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rules</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getRules()
	 * @see #getStateSpace()
	 * @generated
	 */
	EReference getStateSpace_Rules();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.statespace.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.State#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getIndex()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Index();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.State#getDerivedFrom <em>Derived From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Derived From</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getDerivedFrom()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_DerivedFrom();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.statespace.State#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getIncoming()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Incoming();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.statespace.State#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outgoing</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getOutgoing()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Outgoing();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.statespace.State#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Model</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getModel()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Model();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.statespace.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Model
	 * @generated
	 */
	EClass getModel();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.Model#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Model#getResource()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_Resource();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.Model#getEmfGraph <em>Emf Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Emf Graph</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Model#getEmfGraph()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_EmfGraph();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.henshin.statespace.Model#getObjectKeysMap <em>Object Keys Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Object Keys Map</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Model#getObjectKeysMap()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_ObjectKeysMap();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.Model#getObjectKeys <em>Object Keys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Keys</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Model#getObjectKeys()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_ObjectKeys();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.Model#getObjectCount <em>Object Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Count</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Model#getObjectCount()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_ObjectCount();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.statespace.State#getStateSpace <em>State Space</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>State Space</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getStateSpace()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_StateSpace();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.State#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getLocation()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Location();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.State#isOpen <em>Open</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Open</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#isOpen()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Open();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.State#getHashCode <em>Hash Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hash Code</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getHashCode()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_HashCode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.State#getObjectCount <em>Object Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Count</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getObjectCount()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_ObjectCount();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.State#getObjectKeys <em>Object Keys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Keys</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getObjectKeys()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_ObjectKeys();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.statespace.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.henshin.statespace.Transition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Source</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getSource()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Source();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.statespace.Transition#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getTarget()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Target();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.statespace.Transition#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getRule()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Rule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.Transition#getMatch <em>Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Match</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getMatch()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Match();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.Transition#getParameterCount <em>Parameter Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameter Count</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getParameterCount()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_ParameterCount();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.Transition#getParameterKeys <em>Parameter Keys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameter Keys</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getParameterKeys()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_ParameterKeys();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.statespace.EqualityHelper <em>Equality Helper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Equality Helper</em>'.
	 * @see org.eclipse.emf.henshin.statespace.EqualityHelper
	 * @generated
	 */
	EClass getEqualityHelper();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.EqualityHelper#isUseGraphEquality <em>Use Graph Equality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Graph Equality</em>'.
	 * @see org.eclipse.emf.henshin.statespace.EqualityHelper#isUseGraphEquality()
	 * @see #getEqualityHelper()
	 * @generated
	 */
	EAttribute getEqualityHelper_UseGraphEquality();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.EqualityHelper#isUseObjectKeys <em>Use Object Keys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Object Keys</em>'.
	 * @see org.eclipse.emf.henshin.statespace.EqualityHelper#isUseObjectKeys()
	 * @see #getEqualityHelper()
	 * @generated
	 */
	EAttribute getEqualityHelper_UseObjectKeys();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.EqualityHelper#isUseObjectAttributes <em>Use Object Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Object Attributes</em>'.
	 * @see org.eclipse.emf.henshin.statespace.EqualityHelper#isUseObjectAttributes()
	 * @see #getEqualityHelper()
	 * @generated
	 */
	EAttribute getEqualityHelper_UseObjectAttributes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.statespace.Storage <em>Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Storage</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Storage
	 * @generated
	 */
	EClass getStorage();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.Storage#getData <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Storage#getData()
	 * @see #getStorage()
	 * @generated
	 */
	EAttribute getStorage_Data();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Object Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Key</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emf.ecore.EObject"
	 *        valueDataType="org.eclipse.emf.ecore.EIntegerObject"
	 * @generated
	 */
	EClass getObjectKey();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getObjectKey()
	 * @generated
	 */
	EReference getObjectKey_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getObjectKey()
	 * @generated
	 */
	EAttribute getObjectKey_Value();

	/**
	 * Returns the meta object for data type '<em>Integer Array</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Integer Array</em>'.
	 * @model instanceClass="int[]"
	 * @generated
	 */
	EDataType getIntegerArray();

	/**
	 * Returns the meta object for data type '<em>String Array</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>String Array</em>'.
	 * @model instanceClass="java.lang.String[]"
	 * @generated
	 */
	EDataType getStringArray();

	/**
	 * Returns the meta object for data type '<em>EClass Array</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EClass Array</em>'.
	 * @model instanceClass="org.eclipse.emf.ecore.EClass[]"
	 * @generated
	 */
	EDataType getEClassArray();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.henshin.interpreter.util.Match <em>Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Match</em>'.
	 * @see org.eclipse.emf.henshin.interpreter.util.Match
	 * @model instanceClass="org.eclipse.emf.henshin.interpreter.util.Match"
	 * @generated
	 */
	EDataType getMatch();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.henshin.matching.EmfGraph <em>Emf Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Emf Graph</em>'.
	 * @see org.eclipse.emf.henshin.matching.EmfGraph
	 * @model instanceClass="org.eclipse.emf.henshin.matching.EmfGraph"
	 * @generated
	 */
	EDataType getEmfGraph();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	StateSpaceFactory getStateSpaceFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.statespace.impl.StateSpaceImpl <em>State Space</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpaceImpl
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getStateSpace()
		 * @generated
		 */
		EClass STATE_SPACE = eINSTANCE.getStateSpace();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_SPACE__STATES = eINSTANCE.getStateSpace_States();

		/**
		 * The meta object literal for the '<em><b>Initial States</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_SPACE__INITIAL_STATES = eINSTANCE.getStateSpace_InitialStates();

		/**
		 * The meta object literal for the '<em><b>Open States</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_SPACE__OPEN_STATES = eINSTANCE.getStateSpace_OpenStates();

		/**
		 * The meta object literal for the '<em><b>State Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__STATE_COUNT = eINSTANCE.getStateSpace_StateCount();

		/**
		 * The meta object literal for the '<em><b>Transition Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__TRANSITION_COUNT = eINSTANCE.getStateSpace_TransitionCount();

		/**
		 * The meta object literal for the '<em><b>Equality Helper</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_SPACE__EQUALITY_HELPER = eINSTANCE.getStateSpace_EqualityHelper();

		/**
		 * The meta object literal for the '<em><b>Layout Zoom Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__LAYOUT_ZOOM_LEVEL = eINSTANCE.getStateSpace_LayoutZoomLevel();

		/**
		 * The meta object literal for the '<em><b>Layout State Repulsion</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__LAYOUT_STATE_REPULSION = eINSTANCE.getStateSpace_LayoutStateRepulsion();

		/**
		 * The meta object literal for the '<em><b>Layout Transition Attraction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__LAYOUT_TRANSITION_ATTRACTION = eINSTANCE.getStateSpace_LayoutTransitionAttraction();

		/**
		 * The meta object literal for the '<em><b>Layout Hide Labels</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__LAYOUT_HIDE_LABELS = eINSTANCE.getStateSpace_LayoutHideLabels();

		/**
		 * The meta object literal for the '<em><b>Max State Distance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__MAX_STATE_DISTANCE = eINSTANCE.getStateSpace_MaxStateDistance();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_SPACE__PROPERTIES = eINSTANCE.getStateSpace_Properties();

		/**
		 * The meta object literal for the '<em><b>Supported Types</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__SUPPORTED_TYPES = eINSTANCE.getStateSpace_SupportedTypes();

		/**
		 * The meta object literal for the '<em><b>Supported Type Prefixes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__SUPPORTED_TYPE_PREFIXES = eINSTANCE.getStateSpace_SupportedTypePrefixes();

		/**
		 * The meta object literal for the '<em><b>All Parameter Keys</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__ALL_PARAMETER_KEYS = eINSTANCE.getStateSpace_AllParameterKeys();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_SPACE__RULES = eINSTANCE.getStateSpace_Rules();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.statespace.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.StateImpl
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__INDEX = eINSTANCE.getState_Index();

		/**
		 * The meta object literal for the '<em><b>Derived From</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__DERIVED_FROM = eINSTANCE.getState_DerivedFrom();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__INCOMING = eINSTANCE.getState_Incoming();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__OUTGOING = eINSTANCE.getState_Outgoing();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__MODEL = eINSTANCE.getState_Model();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.statespace.impl.ModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.ModelImpl
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getModel()
		 * @generated
		 */
		EClass MODEL = eINSTANCE.getModel();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__RESOURCE = eINSTANCE.getModel_Resource();

		/**
		 * The meta object literal for the '<em><b>Emf Graph</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__EMF_GRAPH = eINSTANCE.getModel_EmfGraph();

		/**
		 * The meta object literal for the '<em><b>Object Keys Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__OBJECT_KEYS_MAP = eINSTANCE.getModel_ObjectKeysMap();

		/**
		 * The meta object literal for the '<em><b>Object Keys</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__OBJECT_KEYS = eINSTANCE.getModel_ObjectKeys();

		/**
		 * The meta object literal for the '<em><b>Object Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__OBJECT_COUNT = eINSTANCE.getModel_ObjectCount();

		/**
		 * The meta object literal for the '<em><b>State Space</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__STATE_SPACE = eINSTANCE.getState_StateSpace();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__LOCATION = eINSTANCE.getState_Location();

		/**
		 * The meta object literal for the '<em><b>Open</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__OPEN = eINSTANCE.getState_Open();

		/**
		 * The meta object literal for the '<em><b>Hash Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__HASH_CODE = eINSTANCE.getState_HashCode();

		/**
		 * The meta object literal for the '<em><b>Object Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__OBJECT_COUNT = eINSTANCE.getState_ObjectCount();

		/**
		 * The meta object literal for the '<em><b>Object Keys</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__OBJECT_KEYS = eINSTANCE.getState_ObjectKeys();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.statespace.impl.TransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.TransitionImpl
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__SOURCE = eINSTANCE.getTransition_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__TARGET = eINSTANCE.getTransition_Target();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__RULE = eINSTANCE.getTransition_Rule();

		/**
		 * The meta object literal for the '<em><b>Match</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__MATCH = eINSTANCE.getTransition_Match();

		/**
		 * The meta object literal for the '<em><b>Parameter Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__PARAMETER_COUNT = eINSTANCE.getTransition_ParameterCount();

		/**
		 * The meta object literal for the '<em><b>Parameter Keys</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__PARAMETER_KEYS = eINSTANCE.getTransition_ParameterKeys();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.statespace.impl.EqualityHelperImpl <em>Equality Helper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.EqualityHelperImpl
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getEqualityHelper()
		 * @generated
		 */
		EClass EQUALITY_HELPER = eINSTANCE.getEqualityHelper();

		/**
		 * The meta object literal for the '<em><b>Use Graph Equality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EQUALITY_HELPER__USE_GRAPH_EQUALITY = eINSTANCE.getEqualityHelper_UseGraphEquality();

		/**
		 * The meta object literal for the '<em><b>Use Object Keys</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EQUALITY_HELPER__USE_OBJECT_KEYS = eINSTANCE.getEqualityHelper_UseObjectKeys();

		/**
		 * The meta object literal for the '<em><b>Use Object Attributes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EQUALITY_HELPER__USE_OBJECT_ATTRIBUTES = eINSTANCE.getEqualityHelper_UseObjectAttributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.statespace.impl.StorageImpl <em>Storage</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.StorageImpl
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getStorage()
		 * @generated
		 */
		EClass STORAGE = eINSTANCE.getStorage();

		/**
		 * The meta object literal for the '<em><b>Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STORAGE__DATA = eINSTANCE.getStorage_Data();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.statespace.impl.ObjectKeyImpl <em>Object Key</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.ObjectKeyImpl
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getObjectKey()
		 * @generated
		 */
		EClass OBJECT_KEY = eINSTANCE.getObjectKey();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_KEY__KEY = eINSTANCE.getObjectKey_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBJECT_KEY__VALUE = eINSTANCE.getObjectKey_Value();

		/**
		 * The meta object literal for the '<em>Integer Array</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getIntegerArray()
		 * @generated
		 */
		EDataType INTEGER_ARRAY = eINSTANCE.getIntegerArray();

		/**
		 * The meta object literal for the '<em>String Array</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getStringArray()
		 * @generated
		 */
		EDataType STRING_ARRAY = eINSTANCE.getStringArray();

		/**
		 * The meta object literal for the '<em>EClass Array</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getEClassArray()
		 * @generated
		 */
		EDataType ECLASS_ARRAY = eINSTANCE.getEClassArray();

		/**
		 * The meta object literal for the '<em>Match</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.interpreter.util.Match
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getMatch()
		 * @generated
		 */
		EDataType MATCH = eINSTANCE.getMatch();

		/**
		 * The meta object literal for the '<em>Emf Graph</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.matching.EmfGraph
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getEmfGraph()
		 * @generated
		 */
		EDataType EMF_GRAPH = eINSTANCE.getEmfGraph();

	}

} //StateSpacePackage
