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
package org.eclipse.emf.henshin.statespace.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.EqualityHelper;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.ParameterUtil;

/**
 * Concrete implementation of the {@link State} interface.
 * @generated
 */
public class StateSpaceImpl extends StorageImpl implements StateSpace {

	/**
	 * Default constructor.
	 * @generated NOT
	 */
	protected StateSpaceImpl() {
		super();
		
		// Create a default equality helper:
		setEqualityHelper(StateSpaceFactory.eINSTANCE.createEqualityHelper());
		
		// Already initialize the set of open states here:
		openStates = new LinkedHashSet<State>();
		
	}

	/**
	 * Get the set of open states in this state space.
	 * @generated NOT
	 */
	public Set<State> getOpenStates() {
		return openStates;
	}
	
	/**
	 * @generated NOT
	 */
	public int getStateCount() {
		return getStates().size();
	}

	/**
	 * Remove a state and detach its transitions from the other states.
	 * The transitions are still connected to the removed node afterwards.
	 * All predecessor states are automatically marked as open.
	 * @generated NOT
	 */
	public boolean removeState(State state) {
		
		// Try to remove the state:
		if (getStates().remove(state)) {
			
			// Detach incoming transitions:
			for (Transition transition : state.getIncoming()) {
				
				// Mark the predecessor state as open!
				State source = transition.getSource();
				if (source!=null) {
					source.setOpen(true);
					if (!getOpenStates().contains(source)) {
						getOpenStates().add(source);
					}
				}
				
				// Detach...
				transition.setSource(null);
				
			}
			
			// Detach outgoing transitions:
			for (Transition transition : state.getOutgoing()) {
				transition.setTarget(null);
			}
			
			// Reset the indizes of all states:
			int index = 0;
			for (State current : getStates()) {
				current.setIndex(index++);
			}
			
			// Done.
			return true;
			
		} else {
			return false;
		}
		
	}

	/**
	 * @generated NOT
	 */
	public void updateSupportedTypes() {
		
		// Get the list of supported rules:
		List<Rule> rules = getRules();
		
		// Get all relevant parameter types:
		Set<EClass> parameterTypes = new LinkedHashSet<EClass>();
		for (Rule rule : rules) {
			try {
				parameterTypes.addAll(ParameterUtil.getParameterTypes(this, rule));
			} catch (StateSpaceException e) {
				throw new RuntimeException(e);
			}
		}		
		
		// Compute the list of supported object types:
		List<EClass> types = new ArrayList<EClass>();
		for (Rule rule : rules) {
			
			// Get the transformation system:
			TransformationSystem system = rule.getTransformationSystem();
			if (system==null) {
				continue;
			}
			
			// Get the imported packages and their classes:
			for (EPackage epackage : system.getImports()) {
				for (EClassifier eclassifier : epackage.getEClassifiers()) {
					if (eclassifier instanceof EClass) {
						EClass eclass = (EClass) eclassifier;
						
						// Check if it is a parameter type:
						boolean isParameterType = false;
						for (EClass paramType : parameterTypes) {
							if (paramType.isSuperTypeOf(eclass) || eclass.isSuperTypeOf(paramType)) {
								isParameterType = true;
								break;
							}
						}
						
						// Abstract classes and interfaces are not supported as object types!
						if (isParameterType && !eclass.isAbstract() && !eclass.isInterface() && !types.contains(eclass)) {
							types.add(eclass);
						}
					}
				}
			}
		}
		
		// Compute the prefixes for these types:
		List<String> prefixes = new ArrayList<String>(types.size());
		for (EClass type : types) {
			char prefix = Character.toLowerCase(type.getName().charAt(0));
			while (prefixes.contains(String.valueOf(prefix))) {
				if (prefix=='z') {
					prefix = 'a';
				} else {
					prefix++;
				}
			}
			prefixes.add(String.valueOf(prefix));
		}
		
		// Now we can update the state space attributes:
		supportedTypes = types.toArray(new EClass[0]);
		supportedTypePrefixes = prefixes.toArray(new String[0]);

	}

	/**
	 * @generated NOT
	 */
	public int[] getAllParameterKeys() {
		
		// Use a set so we do not count duplicates:
		Set<Integer> objectKeys = new LinkedHashSet<Integer>();
		
		// Iterate over all transitions:
		int i, paramCount;
		int[] params;
		for (State state : getStates()) {
			for (Transition transition : state.getOutgoing()) {
				
				// Add all parameters to the set:
				paramCount = transition.getParameterCount();
				params = transition.getParameterKeys();
				for (i=0; i<paramCount; i++) {
					objectKeys.add(params[i]);
				}
				
			}
		}
		
		// Now just put it into an array:
		int[] result = new int[objectKeys.size()];
		i=0;
		for (Integer key : objectKeys) {
			result[i++] = key;
		}
		return result;
		
	}
	
	/**
	 * @generated NOT
	 */
	private int getPercent(int index, int def) {
		int p = getData(index);
		return (p==0) ? def : Math.min(100,Math.max(p,1));
	}

	/**
	 * @generated NOT
	 */
	private void setPercent(int index, int p) {
		p = Math.min(100,Math.max(p,1));
		if (getData(index)!=p) {
			setData(index, p);
		}
	}
	
	/**
	 * @generated NOT
	 */
	public int getLayoutZoomLevel() {
		return getPercent(0, 100);
	}

	/**
	 * @generated NOT
	 */
	public void setLayoutZoomLevel(int zoomLevel) {
		setPercent(0, zoomLevel);
	}

	/**
	 * @generated NOT
	 */
	public int getLayoutStateRepulsion() {
		return getPercent(1, 50);
	}

	/**
	 * @generated NOT
	 */
	public void setLayoutStateRepulsion(int repulsion) {
		setPercent(1, repulsion);
	}

	/**
	 * @generated NOT
	 */
	public int getLayoutTransitionAttraction() {
		return getPercent(2, 50);
	}

	/**
	 * @generated NOT
	 */
	public void setLayoutTransitionAttraction(int attraction) {
		setPercent(2, attraction);
	}

	/**
	 * @generated NOT
	 */
	public int getMaxStateDistance() {
		int maxDistance = getData(3);
		return (maxDistance>0) ? maxDistance : -1;
	}

	/**
	 * @generated NOT
	 */
	public void setMaxStateDistance(int maxStateDistance) {
		setData(3, maxStateDistance);
	}


	/**
	 * @generated NOT
	 */
	public boolean isLayoutHideLabels() {
		return getData(4)!=0;
	}

	/**
	 * @generated NOT
	 */
	public void setLayoutHideLabels(boolean hideLabels) {
		setData(4, hideLabels ? 1 : 0);
	}

	/* ---------------------------------------------------------------- *
	 * GENERATED CODE.                                                  *
	 * Do not edit below this line. If you need to edit, move it above  *
	 * this line and change the '@generated'-tag to '@generated NOT'.   *
	 * ---------------------------------------------------------------- */

	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' reference list.
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> rules;

	
	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> states;

	/**
	 * The cached value of the '{@link #getInitialStates() <em>Initial States</em>}' reference list.
	 * @see #getInitialStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> initialStates;


	/**
	 * Set of open states.
	 * @see #getOpenStates()
	 * @generated NOT
	 */
	protected Set<State> openStates;

	/**
	 * The default value of the '{@link #getStateCount() <em>State Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateCount()
	 * @generated
	 * @ordered
	 */
	protected static final int STATE_COUNT_EDEFAULT = 0;


	/**
	 * The default value of the '{@link #getTransitionCount() <em>Transition Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionCount()
	 * @generated
	 * @ordered
	 */
	protected static final int TRANSITION_COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTransitionCount() <em>Transition Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionCount()
	 * @generated
	 * @ordered
	 */
	protected int transitionCount = TRANSITION_COUNT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEqualityHelper() <em>Equality Helper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEqualityHelper()
	 * @generated
	 * @ordered
	 */
	protected EqualityHelper equalityHelper;

	/**
	 * The default value of the '{@link #getLayoutZoomLevel() <em>Layout Zoom Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayoutZoomLevel()
	 * @generated
	 * @ordered
	 */
	protected static final int LAYOUT_ZOOM_LEVEL_EDEFAULT = 0;


	/**
	 * The default value of the '{@link #getLayoutStateRepulsion() <em>Layout State Repulsion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayoutStateRepulsion()
	 * @generated
	 * @ordered
	 */
	protected static final int LAYOUT_STATE_REPULSION_EDEFAULT = 0;


	/**
	 * The default value of the '{@link #getLayoutTransitionAttraction() <em>Layout Transition Attraction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayoutTransitionAttraction()
	 * @generated
	 * @ordered
	 */
	protected static final int LAYOUT_TRANSITION_ATTRACTION_EDEFAULT = 0;


	/**
	 * The default value of the '{@link #isLayoutHideLabels() <em>Layout Hide Labels</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLayoutHideLabels()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LAYOUT_HIDE_LABELS_EDEFAULT = false;


	/**
	 * The default value of the '{@link #getMaxStateDistance() <em>Max State Distance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxStateDistance()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_STATE_DISTANCE_EDEFAULT = -1;


	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> properties;

	/**
	 * The default value of the '{@link #getSupportedTypes() <em>Supported Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSupportedTypes()
	 * @generated
	 * @ordered
	 */
	protected static final EClass[] SUPPORTED_TYPES_EDEFAULT = null;


	/**
	 * The cached value of the '{@link #getSupportedTypes() <em>Supported Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSupportedTypes()
	 * @generated
	 * @ordered
	 */
	protected EClass[] supportedTypes = SUPPORTED_TYPES_EDEFAULT;

	/**
	 * The default value of the '{@link #getSupportedTypePrefixes() <em>Supported Type Prefixes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSupportedTypePrefixes()
	 * @generated
	 * @ordered
	 */
	protected static final String[] SUPPORTED_TYPE_PREFIXES_EDEFAULT = null;


	/**
	 * The cached value of the '{@link #getSupportedTypePrefixes() <em>Supported Type Prefixes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSupportedTypePrefixes()
	 * @generated
	 * @ordered
	 */
	protected String[] supportedTypePrefixes = SUPPORTED_TYPE_PREFIXES_EDEFAULT;


	/**
	 * The default value of the '{@link #getAllParameterKeys() <em>All Parameter Keys</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllParameterKeys()
	 * @generated
	 * @ordered
	 */
	protected static final int[] ALL_PARAMETER_KEYS_EDEFAULT = null;


	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackage.Literals.STATE_SPACE;
	}
	
	/**
	 * Get the list of states in this state space.
	 * @generated
	 */
	public EList<State> getStates() {
		if (states == null) {
			states = new EObjectContainmentWithInverseEList<State>(State.class, this, StateSpacePackage.STATE_SPACE__STATES, StateSpacePackage.STATE__STATE_SPACE);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getInitialStates() {
		if (initialStates == null) {
			initialStates = new EObjectResolvingEList<State>(State.class, this, StateSpacePackage.STATE_SPACE__INITIAL_STATES);
		}
		return initialStates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rule> getRules() {
		if (rules == null) {
			rules = new EObjectResolvingEList<Rule>(Rule.class, this, StateSpacePackage.STATE_SPACE__RULES);
		}
		return rules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTransitionCount() {
		return transitionCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransitionCount(int newTransitionCount) {
		int oldTransitionCount = transitionCount;
		transitionCount = newTransitionCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_SPACE__TRANSITION_COUNT, oldTransitionCount, transitionCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EqualityHelper getEqualityHelper() {
		return equalityHelper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getProperties() {
		if (properties == null) {
			properties = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, StateSpacePackage.STATE_SPACE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass[] getSupportedTypes() {
		return supportedTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String[] getSupportedTypePrefixes() {
		return supportedTypePrefixes;
	}

	/**
	 * @generated
	 */
	public NotificationChain basicSetEqualityHelper(EqualityHelper newEqualityHelper, NotificationChain msgs) {
		EqualityHelper oldEqualityHelper = equalityHelper;
		equalityHelper = newEqualityHelper;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_SPACE__EQUALITY_HELPER, oldEqualityHelper, newEqualityHelper);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * @generated
	 */
	public void setEqualityHelper(EqualityHelper newEqualityHelper) {
		if (newEqualityHelper != equalityHelper) {
			NotificationChain msgs = null;
			if (equalityHelper != null)
				msgs = ((InternalEObject)equalityHelper).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StateSpacePackage.STATE_SPACE__EQUALITY_HELPER, null, msgs);
			if (newEqualityHelper != null)
				msgs = ((InternalEObject)newEqualityHelper).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StateSpacePackage.STATE_SPACE__EQUALITY_HELPER, null, msgs);
			msgs = basicSetEqualityHelper(newEqualityHelper, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_SPACE__EQUALITY_HELPER, newEqualityHelper, newEqualityHelper));
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__STATES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStates()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
			case StateSpacePackage.STATE_SPACE__EQUALITY_HELPER:
				return basicSetEqualityHelper(null, msgs);
			case StateSpacePackage.STATE_SPACE__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__RULES:
				return getRules();
			case StateSpacePackage.STATE_SPACE__STATES:
				return getStates();
			case StateSpacePackage.STATE_SPACE__INITIAL_STATES:
				return getInitialStates();
			case StateSpacePackage.STATE_SPACE__OPEN_STATES:
				return getOpenStates();
			case StateSpacePackage.STATE_SPACE__STATE_COUNT:
				return getStateCount();
			case StateSpacePackage.STATE_SPACE__TRANSITION_COUNT:
				return getTransitionCount();
			case StateSpacePackage.STATE_SPACE__EQUALITY_HELPER:
				return getEqualityHelper();
			case StateSpacePackage.STATE_SPACE__LAYOUT_ZOOM_LEVEL:
				return getLayoutZoomLevel();
			case StateSpacePackage.STATE_SPACE__LAYOUT_STATE_REPULSION:
				return getLayoutStateRepulsion();
			case StateSpacePackage.STATE_SPACE__LAYOUT_TRANSITION_ATTRACTION:
				return getLayoutTransitionAttraction();
			case StateSpacePackage.STATE_SPACE__LAYOUT_HIDE_LABELS:
				return isLayoutHideLabels();
			case StateSpacePackage.STATE_SPACE__MAX_STATE_DISTANCE:
				return getMaxStateDistance();
			case StateSpacePackage.STATE_SPACE__PROPERTIES:
				if (coreType) return getProperties();
				else return getProperties().map();
			case StateSpacePackage.STATE_SPACE__SUPPORTED_TYPES:
				return getSupportedTypes();
			case StateSpacePackage.STATE_SPACE__SUPPORTED_TYPE_PREFIXES:
				return getSupportedTypePrefixes();
			case StateSpacePackage.STATE_SPACE__ALL_PARAMETER_KEYS:
				return getAllParameterKeys();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__RULES:
				getRules().clear();
				getRules().addAll((Collection<? extends Rule>)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends State>)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__INITIAL_STATES:
				getInitialStates().clear();
				getInitialStates().addAll((Collection<? extends State>)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__OPEN_STATES:
				getOpenStates().clear();
				getOpenStates().addAll((Collection<? extends State>)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__TRANSITION_COUNT:
				setTransitionCount((Integer)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__EQUALITY_HELPER:
				setEqualityHelper((EqualityHelper)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__LAYOUT_ZOOM_LEVEL:
				setLayoutZoomLevel((Integer)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__LAYOUT_STATE_REPULSION:
				setLayoutStateRepulsion((Integer)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__LAYOUT_TRANSITION_ATTRACTION:
				setLayoutTransitionAttraction((Integer)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__LAYOUT_HIDE_LABELS:
				setLayoutHideLabels((Boolean)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__MAX_STATE_DISTANCE:
				setMaxStateDistance((Integer)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__PROPERTIES:
				((EStructuralFeature.Setting)getProperties()).set(newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__RULES:
				getRules().clear();
				return;
			case StateSpacePackage.STATE_SPACE__STATES:
				getStates().clear();
				return;
			case StateSpacePackage.STATE_SPACE__INITIAL_STATES:
				getInitialStates().clear();
				return;
			case StateSpacePackage.STATE_SPACE__OPEN_STATES:
				getOpenStates().clear();
				return;
			case StateSpacePackage.STATE_SPACE__TRANSITION_COUNT:
				setTransitionCount(TRANSITION_COUNT_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__EQUALITY_HELPER:
				setEqualityHelper((EqualityHelper)null);
				return;
			case StateSpacePackage.STATE_SPACE__LAYOUT_ZOOM_LEVEL:
				setLayoutZoomLevel(LAYOUT_ZOOM_LEVEL_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__LAYOUT_STATE_REPULSION:
				setLayoutStateRepulsion(LAYOUT_STATE_REPULSION_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__LAYOUT_TRANSITION_ATTRACTION:
				setLayoutTransitionAttraction(LAYOUT_TRANSITION_ATTRACTION_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__LAYOUT_HIDE_LABELS:
				setLayoutHideLabels(LAYOUT_HIDE_LABELS_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__MAX_STATE_DISTANCE:
				setMaxStateDistance(MAX_STATE_DISTANCE_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__PROPERTIES:
				getProperties().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__RULES:
				return rules != null && !rules.isEmpty();
			case StateSpacePackage.STATE_SPACE__STATES:
				return states != null && !states.isEmpty();
			case StateSpacePackage.STATE_SPACE__INITIAL_STATES:
				return initialStates != null && !initialStates.isEmpty();
			case StateSpacePackage.STATE_SPACE__OPEN_STATES:
				return openStates != null && !openStates.isEmpty();
			case StateSpacePackage.STATE_SPACE__STATE_COUNT:
				return getStateCount() != STATE_COUNT_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__TRANSITION_COUNT:
				return transitionCount != TRANSITION_COUNT_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__EQUALITY_HELPER:
				return equalityHelper != null;
			case StateSpacePackage.STATE_SPACE__LAYOUT_ZOOM_LEVEL:
				return getLayoutZoomLevel() != LAYOUT_ZOOM_LEVEL_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__LAYOUT_STATE_REPULSION:
				return getLayoutStateRepulsion() != LAYOUT_STATE_REPULSION_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__LAYOUT_TRANSITION_ATTRACTION:
				return getLayoutTransitionAttraction() != LAYOUT_TRANSITION_ATTRACTION_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__LAYOUT_HIDE_LABELS:
				return isLayoutHideLabels() != LAYOUT_HIDE_LABELS_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__MAX_STATE_DISTANCE:
				return getMaxStateDistance() != MAX_STATE_DISTANCE_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case StateSpacePackage.STATE_SPACE__SUPPORTED_TYPES:
				return SUPPORTED_TYPES_EDEFAULT == null ? supportedTypes != null : !SUPPORTED_TYPES_EDEFAULT.equals(supportedTypes);
			case StateSpacePackage.STATE_SPACE__SUPPORTED_TYPE_PREFIXES:
				return SUPPORTED_TYPE_PREFIXES_EDEFAULT == null ? supportedTypePrefixes != null : !SUPPORTED_TYPE_PREFIXES_EDEFAULT.equals(supportedTypePrefixes);
			case StateSpacePackage.STATE_SPACE__ALL_PARAMETER_KEYS:
				return ALL_PARAMETER_KEYS_EDEFAULT == null ? getAllParameterKeys() != null : !ALL_PARAMETER_KEYS_EDEFAULT.equals(getAllParameterKeys());
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (transitionCount: ");
		result.append(transitionCount);
		result.append(", supportedTypes: ");
		result.append(supportedTypes);
		result.append(", supportedTypePrefixes: ");
		result.append(supportedTypePrefixes);
		result.append(')');
		return result.toString();
	}

} //StateSpaceImpl
