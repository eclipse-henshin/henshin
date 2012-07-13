/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Rule</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getLhs <em>Lhs</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getRhs <em>Rhs</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getAttributeConditions <em>Attribute Conditions</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getMappings <em>Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#isCheckDangling <em>Check Dangling</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#isInjectiveMatching <em>Injective Matching</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getMultiRules <em>Multi Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.RuleImpl#getMultiMappings <em>Multi Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleImpl extends TransformationUnitImpl implements Rule {
	/**
	 * The cached value of the '{@link #getLhs() <em>Lhs</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLhs()
	 * @generated
	 * @ordered
	 */
	protected Graph lhs;
	
	/**
	 * The cached value of the '{@link #getRhs() <em>Rhs</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRhs()
	 * @generated
	 * @ordered
	 */
	protected Graph rhs;
	
	/**
	 * The cached value of the '{@link #getAttributeConditions()
	 * <em>Attribute Conditions</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAttributeConditions()
	 * @generated
	 * @ordered
	 */
	protected EList<AttributeCondition> attributeConditions;
	
	/**
	 * The cached value of the '{@link #getMappings() <em>Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> mappings;
	
	/**
	 * The default value of the '{@link #isCheckDangling() <em>Check Dangling</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCheckDangling()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CHECK_DANGLING_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isCheckDangling() <em>Check Dangling</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCheckDangling()
	 * @generated
	 * @ordered
	 */
	protected boolean checkDangling = CHECK_DANGLING_EDEFAULT;

	/**
	 * The default value of the '{@link #isInjectiveMatching() <em>Injective Matching</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInjectiveMatching()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INJECTIVE_MATCHING_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isInjectiveMatching() <em>Injective Matching</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInjectiveMatching()
	 * @generated
	 * @ordered
	 */
	protected boolean injectiveMatching = INJECTIVE_MATCHING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMultiRules() <em>Multi Rules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiRules()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> multiRules;

	/**
	 * The cached value of the '{@link #getMultiMappings() <em>Multi Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> multiMappings;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleImpl() {
		super();
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.RULE;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getLhs() {
		return lhs;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLhs(Graph newLhs, NotificationChain msgs) {
		Graph oldLhs = lhs;
		lhs = newLhs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__LHS, oldLhs, newLhs);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhs(Graph newLhs) {
		if (newLhs != lhs) {
			NotificationChain msgs = null;
			if (lhs != null)
				msgs = ((InternalEObject)lhs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.RULE__LHS, null, msgs);
			if (newLhs != null)
				msgs = ((InternalEObject)newLhs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.RULE__LHS, null, msgs);
			msgs = basicSetLhs(newLhs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__LHS, newLhs, newLhs));
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getRhs() {
		return rhs;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRhs(Graph newRhs, NotificationChain msgs) {
		Graph oldRhs = rhs;
		rhs = newRhs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__RHS, oldRhs, newRhs);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setRhs(Graph newRhs) {
		if (newRhs != rhs) {
			NotificationChain msgs = null;
			if (rhs != null)
				msgs = ((InternalEObject)rhs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.RULE__RHS, null, msgs);
			if (newRhs != null)
				msgs = ((InternalEObject)newRhs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.RULE__RHS, null, msgs);
			msgs = basicSetRhs(newRhs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__RHS, newRhs, newRhs));
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeCondition> getAttributeConditions() {
		if (attributeConditions == null) {
			attributeConditions = new EObjectContainmentWithInverseEList<AttributeCondition>(AttributeCondition.class, this, HenshinPackage.RULE__ATTRIBUTE_CONDITIONS, HenshinPackage.ATTRIBUTE_CONDITION__RULE);
		}
		return attributeConditions;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public MappingList getMappings() {
		if (mappings == null) {
			mappings = new MappingListImpl(this, HenshinPackage.RULE__MAPPINGS);
		}
		return (MappingList) mappings;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public TransformationSystem getTransformationSystem() {
		EObject container = eContainer();
		while (container!=null) {
			if (container instanceof TransformationSystem) {
				return (TransformationSystem) container;
			}
			container = container.eContainer();
		}
		return null;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule getKernelRule() {
		EObject container = eContainer();
		if (container instanceof Rule) {
			return (Rule) container;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule getRootKernelRule() {
		Rule kernel = getKernelRule();
		if (kernel==null) {
			return null;
		}
		while (kernel.getKernelRule()!=null) {
			kernel = kernel.getKernelRule();
		}
		return kernel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule getMultiRule(String name) {
		for (Rule multiRule : getMultiRules()) {
			if ((name==null && multiRule.getName()==null) || 
				(name!=null && name.equals(multiRule.getName()))) {
				return multiRule;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void removeNode(Node node, boolean removeMapped) {
		
		// Must be invoked from the root kernel rule:
		if (getRootKernelRule()!=null) {
			getRootKernelRule().removeNode(node, removeMapped);
			return;
		}
		
		// Collect all mappings and nodes to delete:
		Set<Mapping> mappings = new HashSet<Mapping>();
		Set<Node> nodes = new HashSet<Node>();
		nodes.add(node);
		
		boolean changed;
		do {
			changed = false;
			
			// Add all mappings that refer to the nodes:
			TreeIterator<EObject> it = eAllContents();
			while (it.hasNext()) {
				EObject obj = it.next();
				if (obj instanceof Mapping) {
					Mapping m = (Mapping) obj;
					if (!mappings.contains(m)) {
						for (Node n : nodes) {
							if (m.getOrigin()==n || m.getImage()==n) {
								mappings.add(m);
								changed = true;
								break;
							}
						}
					}
				}
			}
			
			// Add all mapped nodes if necessary:
			if (changed && removeMapped) {
				for (Mapping m : mappings) {
					if (m.getOrigin()!=null) {
						nodes.add(m.getOrigin());
					}
					if (m.getImage()!=null) {
						nodes.add(m.getImage());
					}
				}
			}
		} while (changed);
		
		// Now remove the collected mappings and nodes:
		for (Mapping m : mappings) {
			m.setOrigin(null);
			m.setImage(null);
			EcoreUtil.remove(m);
		}
		for (Node n : nodes) {
			n.getGraph().removeNode(n);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<Rule> getAllMultiRules() {
		EList<Rule> allMultiRules = new BasicEList<Rule>();
		allMultiRules.addAll(getMultiRules());
		boolean changed;
		do {
			changed = false;
			for (Rule rule : allMultiRules) {
				changed = changed || allMultiRules.addAll(rule.getMultiRules());
			}
		} while (changed);
		return ECollections.unmodifiableEList(allMultiRules);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void removeEdge(Edge edge, boolean removeMapped) {
		
		// Must be invoked from the root kernel rule:
		if (getRootKernelRule()!=null) {
			getRootKernelRule().removeEdge(edge, removeMapped);
			return;
		}
		
		Set<Edge> edges = new HashSet<Edge>();
		edges.add(edge);
		
		// Collect mapped edges if necessary:
		if (removeMapped) {
			
			// Collect a list of ALL mappings:
			List<Mapping> mappings = new ArrayList<Mapping>();
			TreeIterator<EObject> it = eAllContents();
			while (it.hasNext()) {
				EObject obj = it.next();
				if (obj instanceof Mapping) {
					mappings.add((Mapping) obj);
				}
			}
			
			// Now collect edges to be removed:
			boolean changed;
			do {
				changed = false;
				it = eAllContents();
				while (it.hasNext()) {
					EObject obj = it.next();
					if (obj instanceof Edge) {
						Edge e = (Edge) obj;
						if (e.getType()!=edge.getType() || edges.contains(e)) {
							continue;
						}
						if ((getMapping(edge.getSource(), e.getSource(), mappings)!=null &&
							 getMapping(edge.getTarget(), e.getTarget(), mappings)!=null) ||
							(getMapping(e.getSource(), edge.getSource(), mappings)!=null &&
							 getMapping(e.getTarget(), edge.getTarget(), mappings)!=null)) {
							edges.add(e);
							changed = true;
						}
					}
				}
			} while (changed);
		}
		
		// Now remove the collected edges:
		for (Edge e : edges) {
			e.getGraph().removeEdge(e);
		}
		
	}

	/*
	 * Private helper for finding mappings.
	 */
	private static Mapping getMapping(Node origin, Node image, List<Mapping> mappings) {
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin()==origin && mapping.getImage()==image) {
				return mapping;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCheckDangling() {
		return checkDangling;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckDangling(boolean newCheckDangling) {
		boolean oldCheckDangling = checkDangling;
		checkDangling = newCheckDangling;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__CHECK_DANGLING, oldCheckDangling, checkDangling));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInjectiveMatching() {
		return injectiveMatching;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInjectiveMatching(boolean newInjectiveMatching) {
		boolean oldInjectiveMatching = injectiveMatching;
		injectiveMatching = newInjectiveMatching;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.RULE__INJECTIVE_MATCHING, oldInjectiveMatching, injectiveMatching));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rule> getMultiRules() {
		if (multiRules == null) {
			multiRules = new EObjectContainmentEList<Rule>(Rule.class, this, HenshinPackage.RULE__MULTI_RULES);
		}
		return multiRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public MappingList getMultiMappings() {
		if (multiMappings == null) {
			multiMappings = new MappingListImpl(this, HenshinPackage.RULE__MULTI_MAPPINGS);
		}
		return (MappingList) multiMappings;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Node getNode(String nodename, boolean isLhs) {
		for (Node node : (isLhs) ? lhs.getNodes() : rhs.getNodes()) {
			if (nodename.equals(node.getName())) return node;
		}
		return null;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * Checks whether the rule morphism maps the two specified nodes. NOTE: Will
	 * check only rule mappings, not mappings in application conditions.
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean containsMapping(Node sourceNode, Node targetNode) {
		for (Mapping m : getMappings()) {
			if (m.getOrigin() == sourceNode && m.getImage() == targetNode) return true;
		}
		return false;
	}// containsMapping
	
	/**
	 * <!-- begin-user-doc -->
	 * Check whether the multi-mappings list of this rule contain a mapping
	 * for the given source and target node.
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean containsMultiMapping(Node sourceNode, Node targetNode) {
		for (Mapping m : getMultiMappings()) {
			if (m.getOrigin() == sourceNode && m.getImage() == targetNode) return true;
		}
		return false;
	}// containsMultiMapping

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAttributeConditions()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.RULE__LHS:
				return basicSetLhs(null, msgs);
			case HenshinPackage.RULE__RHS:
				return basicSetRhs(null, msgs);
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				return ((InternalEList<?>)getAttributeConditions()).basicRemove(otherEnd, msgs);
			case HenshinPackage.RULE__MAPPINGS:
				return ((InternalEList<?>)getMappings()).basicRemove(otherEnd, msgs);
			case HenshinPackage.RULE__MULTI_RULES:
				return ((InternalEList<?>)getMultiRules()).basicRemove(otherEnd, msgs);
			case HenshinPackage.RULE__MULTI_MAPPINGS:
				return ((InternalEList<?>)getMultiMappings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.RULE__LHS:
				return getLhs();
			case HenshinPackage.RULE__RHS:
				return getRhs();
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				return getAttributeConditions();
			case HenshinPackage.RULE__MAPPINGS:
				return getMappings();
			case HenshinPackage.RULE__CHECK_DANGLING:
				return isCheckDangling();
			case HenshinPackage.RULE__INJECTIVE_MATCHING:
				return isInjectiveMatching();
			case HenshinPackage.RULE__MULTI_RULES:
				return getMultiRules();
			case HenshinPackage.RULE__MULTI_MAPPINGS:
				return getMultiMappings();
		}
		return super.eGet(featureID, resolve, coreType);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HenshinPackage.RULE__LHS:
				setLhs((Graph)newValue);
				return;
			case HenshinPackage.RULE__RHS:
				setRhs((Graph)newValue);
				return;
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				getAttributeConditions().clear();
				getAttributeConditions().addAll((Collection<? extends AttributeCondition>)newValue);
				return;
			case HenshinPackage.RULE__MAPPINGS:
				getMappings().clear();
				getMappings().addAll((Collection<? extends Mapping>)newValue);
				return;
			case HenshinPackage.RULE__CHECK_DANGLING:
				setCheckDangling((Boolean)newValue);
				return;
			case HenshinPackage.RULE__INJECTIVE_MATCHING:
				setInjectiveMatching((Boolean)newValue);
				return;
			case HenshinPackage.RULE__MULTI_RULES:
				getMultiRules().clear();
				getMultiRules().addAll((Collection<? extends Rule>)newValue);
				return;
			case HenshinPackage.RULE__MULTI_MAPPINGS:
				getMultiMappings().clear();
				getMultiMappings().addAll((Collection<? extends Mapping>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case HenshinPackage.RULE__LHS:
				setLhs((Graph)null);
				return;
			case HenshinPackage.RULE__RHS:
				setRhs((Graph)null);
				return;
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				getAttributeConditions().clear();
				return;
			case HenshinPackage.RULE__MAPPINGS:
				getMappings().clear();
				return;
			case HenshinPackage.RULE__CHECK_DANGLING:
				setCheckDangling(CHECK_DANGLING_EDEFAULT);
				return;
			case HenshinPackage.RULE__INJECTIVE_MATCHING:
				setInjectiveMatching(INJECTIVE_MATCHING_EDEFAULT);
				return;
			case HenshinPackage.RULE__MULTI_RULES:
				getMultiRules().clear();
				return;
			case HenshinPackage.RULE__MULTI_MAPPINGS:
				getMultiMappings().clear();
				return;
		}
		super.eUnset(featureID);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HenshinPackage.RULE__LHS:
				return lhs != null;
			case HenshinPackage.RULE__RHS:
				return rhs != null;
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
				return attributeConditions != null && !attributeConditions.isEmpty();
			case HenshinPackage.RULE__MAPPINGS:
				return mappings != null && !mappings.isEmpty();
			case HenshinPackage.RULE__CHECK_DANGLING:
				return checkDangling != CHECK_DANGLING_EDEFAULT;
			case HenshinPackage.RULE__INJECTIVE_MATCHING:
				return injectiveMatching != INJECTIVE_MATCHING_EDEFAULT;
			case HenshinPackage.RULE__MULTI_RULES:
				return multiRules != null && !multiRules.isEmpty();
			case HenshinPackage.RULE__MULTI_MAPPINGS:
				return multiMappings != null && !multiMappings.isEmpty();
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
		result.append(" (checkDangling: ");
		result.append(checkDangling);
		result.append(", injectiveMatching: ");
		result.append(injectiveMatching);
		result.append(')');
		return result.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public EList<TransformationUnit> getSubUnits() {
		return (EList<TransformationUnit>) ECollections.EMPTY_ELIST;
	}// getSubUnits
	
} // RuleImpl
