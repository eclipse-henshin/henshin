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
	 * The feature id for the '<em><b>Transition Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__TRANSITION_COUNT = STORAGE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Equality Helper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__EQUALITY_HELPER = STORAGE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Zoom Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__ZOOM_LEVEL = STORAGE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>State Repulsion</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__STATE_REPULSION = STORAGE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Transition Attraction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__TRANSITION_ATTRACTION = STORAGE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Max State Distance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__MAX_STATE_DISTANCE = STORAGE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Hide Labels</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__HIDE_LABELS = STORAGE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE__PROPERTIES = STORAGE_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>State Space</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SPACE_FEATURE_COUNT = STORAGE_FEATURE_COUNT + 12;

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
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__LOCATION = STORAGE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Open</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OPEN = STORAGE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Hash Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__HASH_CODE = STORAGE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Node Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NODE_COUNT = STORAGE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Node IDs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NODE_IDS = STORAGE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__MODEL = STORAGE_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = STORAGE_FEATURE_COUNT + 10;

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
	 * The feature id for the '<em><b>Node IDs Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__NODE_IDS_MAP = 1;

	/**
	 * The feature id for the '<em><b>Node IDs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__NODE_IDS = 2;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FEATURE_COUNT = 3;

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
	 * The feature id for the '<em><b>Parameter IDs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__PARAMETER_IDS = STORAGE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = STORAGE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.statespace.impl.StateEqualityHelperImpl <em>State Equality Helper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.StateEqualityHelperImpl
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getStateEqualityHelper()
	 * @generated
	 */
	int STATE_EQUALITY_HELPER = 4;

	/**
	 * The feature id for the '<em><b>Graph Equality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_EQUALITY_HELPER__GRAPH_EQUALITY = 0;

	/**
	 * The feature id for the '<em><b>Ignore Node IDs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_EQUALITY_HELPER__IGNORE_NODE_IDS = 1;

	/**
	 * The feature id for the '<em><b>Ignore Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES = 2;

	/**
	 * The number of structural features of the '<em>State Equality Helper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_EQUALITY_HELPER_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.statespace.impl.NodeIDImpl <em>Node ID</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.NodeIDImpl
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getNodeID()
	 * @generated
	 */
	int NODE_ID = 6;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ID__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ID__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Node ID</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ID_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '<em>Integer Array</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getIntegerArray()
	 * @generated
	 */
	int INTEGER_ARRAY = 7;


	/**
	 * The meta object id for the '<em>Match</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.interpreter.util.Match
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getMatch()
	 * @generated
	 */
	int MATCH = 8;


	/**
	 * The meta object id for the '<em>Hash Code Map</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.equality.HashCodeMap
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getHashCodeMap()
	 * @generated
	 */
	int HASH_CODE_MAP = 9;

	/**
	 * The meta object id for the '<em>EObject Array</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getEObjectArray()
	 * @generated
	 */
	int EOBJECT_ARRAY = 10;

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
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getZoomLevel <em>Zoom Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zoom Level</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getZoomLevel()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_ZoomLevel();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getStateRepulsion <em>State Repulsion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State Repulsion</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getStateRepulsion()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_StateRepulsion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#getTransitionAttraction <em>Transition Attraction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transition Attraction</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#getTransitionAttraction()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_TransitionAttraction();

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
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateSpace#isHideLabels <em>Hide Labels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hide Labels</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateSpace#isHideLabels()
	 * @see #getStateSpace()
	 * @generated
	 */
	EAttribute getStateSpace_HideLabels();

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
	 * Returns the meta object for the map '{@link org.eclipse.emf.henshin.statespace.Model#getNodeIDsMap <em>Node IDs Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Node IDs Map</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Model#getNodeIDsMap()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_NodeIDsMap();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.Model#getNodeIDs <em>Node IDs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Node IDs</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Model#getNodeIDs()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_NodeIDs();

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
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.State#getNodeCount <em>Node Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Node Count</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getNodeCount()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_NodeCount();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.State#getNodeIDs <em>Node IDs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Node IDs</em>'.
	 * @see org.eclipse.emf.henshin.statespace.State#getNodeIDs()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_NodeIDs();

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
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.Transition#getParameterIDs <em>Parameter IDs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameter IDs</em>'.
	 * @see org.eclipse.emf.henshin.statespace.Transition#getParameterIDs()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_ParameterIDs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.statespace.StateEqualityHelper <em>State Equality Helper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Equality Helper</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateEqualityHelper
	 * @generated
	 */
	EClass getStateEqualityHelper();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateEqualityHelper#isGraphEquality <em>Graph Equality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Graph Equality</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateEqualityHelper#isGraphEquality()
	 * @see #getStateEqualityHelper()
	 * @generated
	 */
	EAttribute getStateEqualityHelper_GraphEquality();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateEqualityHelper#isIgnoreNodeIDs <em>Ignore Node IDs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ignore Node IDs</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateEqualityHelper#isIgnoreNodeIDs()
	 * @see #getStateEqualityHelper()
	 * @generated
	 */
	EAttribute getStateEqualityHelper_IgnoreNodeIDs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.statespace.StateEqualityHelper#isIgnoreAttributes <em>Ignore Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ignore Attributes</em>'.
	 * @see org.eclipse.emf.henshin.statespace.StateEqualityHelper#isIgnoreAttributes()
	 * @see #getStateEqualityHelper()
	 * @generated
	 */
	EAttribute getStateEqualityHelper_IgnoreAttributes();

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
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Node ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node ID</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emf.ecore.EObject"
	 *        valueDataType="org.eclipse.emf.ecore.EIntegerObject"
	 * @generated
	 */
	EClass getNodeID();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getNodeID()
	 * @generated
	 */
	EReference getNodeID_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getNodeID()
	 * @generated
	 */
	EAttribute getNodeID_Value();

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
	 * Returns the meta object for data type '{@link org.eclipse.emf.henshin.statespace.equality.HashCodeMap <em>Hash Code Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Hash Code Map</em>'.
	 * @see org.eclipse.emf.henshin.statespace.equality.HashCodeMap
	 * @model instanceClass="org.eclipse.emf.henshin.statespace.equality.HashCodeMap" serializeable="false"
	 * @generated
	 */
	EDataType getHashCodeMap();

	/**
	 * Returns the meta object for data type '<em>EObject Array</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EObject Array</em>'.
	 * @model instanceClass="org.eclipse.emf.ecore.EObject[]"
	 * @generated
	 */
	EDataType getEObjectArray();

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
		 * The meta object literal for the '<em><b>Zoom Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__ZOOM_LEVEL = eINSTANCE.getStateSpace_ZoomLevel();

		/**
		 * The meta object literal for the '<em><b>State Repulsion</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__STATE_REPULSION = eINSTANCE.getStateSpace_StateRepulsion();

		/**
		 * The meta object literal for the '<em><b>Transition Attraction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__TRANSITION_ATTRACTION = eINSTANCE.getStateSpace_TransitionAttraction();

		/**
		 * The meta object literal for the '<em><b>Max State Distance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__MAX_STATE_DISTANCE = eINSTANCE.getStateSpace_MaxStateDistance();

		/**
		 * The meta object literal for the '<em><b>Hide Labels</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SPACE__HIDE_LABELS = eINSTANCE.getStateSpace_HideLabels();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_SPACE__PROPERTIES = eINSTANCE.getStateSpace_Properties();

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
		 * The meta object literal for the '<em><b>Node IDs Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__NODE_IDS_MAP = eINSTANCE.getModel_NodeIDsMap();

		/**
		 * The meta object literal for the '<em><b>Node IDs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__NODE_IDS = eINSTANCE.getModel_NodeIDs();

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
		 * The meta object literal for the '<em><b>Node Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__NODE_COUNT = eINSTANCE.getState_NodeCount();

		/**
		 * The meta object literal for the '<em><b>Node IDs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__NODE_IDS = eINSTANCE.getState_NodeIDs();

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
		 * The meta object literal for the '<em><b>Parameter IDs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__PARAMETER_IDS = eINSTANCE.getTransition_ParameterIDs();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.statespace.impl.StateEqualityHelperImpl <em>State Equality Helper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.StateEqualityHelperImpl
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getStateEqualityHelper()
		 * @generated
		 */
		EClass STATE_EQUALITY_HELPER = eINSTANCE.getStateEqualityHelper();

		/**
		 * The meta object literal for the '<em><b>Graph Equality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_EQUALITY_HELPER__GRAPH_EQUALITY = eINSTANCE.getStateEqualityHelper_GraphEquality();

		/**
		 * The meta object literal for the '<em><b>Ignore Node IDs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_EQUALITY_HELPER__IGNORE_NODE_IDS = eINSTANCE.getStateEqualityHelper_IgnoreNodeIDs();

		/**
		 * The meta object literal for the '<em><b>Ignore Attributes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES = eINSTANCE.getStateEqualityHelper_IgnoreAttributes();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.statespace.impl.NodeIDImpl <em>Node ID</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.NodeIDImpl
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getNodeID()
		 * @generated
		 */
		EClass NODE_ID = eINSTANCE.getNodeID();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_ID__KEY = eINSTANCE.getNodeID_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_ID__VALUE = eINSTANCE.getNodeID_Value();

		/**
		 * The meta object literal for the '<em>Integer Array</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getIntegerArray()
		 * @generated
		 */
		EDataType INTEGER_ARRAY = eINSTANCE.getIntegerArray();

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
		 * The meta object literal for the '<em>Hash Code Map</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.equality.HashCodeMap
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getHashCodeMap()
		 * @generated
		 */
		EDataType HASH_CODE_MAP = eINSTANCE.getHashCodeMap();

		/**
		 * The meta object literal for the '<em>EObject Array</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl#getEObjectArray()
		 * @generated
		 */
		EDataType EOBJECT_ARRAY = eINSTANCE.getEObjectArray();

	}

} //StateSpacePackage
