/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Match</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchImpl#getNodeMatching <em>Node Matching</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchImpl#getEdgeMatching <em>Edge Matching</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchImpl#getSize <em>Size</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MatchImpl extends EObjectImpl implements Match {
	/**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<Annotation> annotations;

	/**
	 * The cached value of the '{@link #getNodeMatching() <em>Node Matching</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeMatching()
	 * @generated
	 * @ordered
	 */
	protected EMap<Node, Node> nodeMatching;

	/**
	 * The cached value of the '{@link #getEdgeMatching() <em>Edge Matching</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdgeMatching()
	 * @generated
	 * @ordered
	 */
	protected EMap<Edge, Edge> edgeMatching;

	/**
	 * The default value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected static final int SIZE_EDEFAULT = 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MatchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamtracePackage.Literals.MATCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamtracePackage.MATCH__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Node, Node> getNodeMatching() {
		if (nodeMatching == null) {
			nodeMatching = new EcoreEMap<Node,Node>(SamtracePackage.Literals.MATCH_ENTRY, MatchEntryImpl.class, this, SamtracePackage.MATCH__NODE_MATCHING);
		}
		return nodeMatching;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Edge, Edge> getEdgeMatching() {
		if (edgeMatching == null) {
			edgeMatching = new EcoreEMap<Edge,Edge>(SamtracePackage.Literals.MATCH_ENTRY, MatchEntryImpl.class, this, SamtracePackage.MATCH__EDGE_MATCHING);
		}
		return edgeMatching;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public int getSize() {
		return this.getEdgeMatching().size() + this.getNodeMatching().size();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void clear() {
		this.getEdgeMatching().clear();
		this.getNodeMatching().clear();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Match copy() {
		Match result = SamtraceFactory.eINSTANCE.createMatch();
		result.getNodeMatching().putAll(this.getNodeMatching());
		result.getEdgeMatching().putAll(this.getEdgeMatching());
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamtracePackage.MATCH__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamtracePackage.MATCH__NODE_MATCHING:
				return ((InternalEList<?>)getNodeMatching()).basicRemove(otherEnd, msgs);
			case SamtracePackage.MATCH__EDGE_MATCHING:
				return ((InternalEList<?>)getEdgeMatching()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SamtracePackage.MATCH__ANNOTATIONS:
				return getAnnotations();
			case SamtracePackage.MATCH__NODE_MATCHING:
				if (coreType) return getNodeMatching();
				else return getNodeMatching().map();
			case SamtracePackage.MATCH__EDGE_MATCHING:
				if (coreType) return getEdgeMatching();
				else return getEdgeMatching().map();
			case SamtracePackage.MATCH__SIZE:
				return getSize();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SamtracePackage.MATCH__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamtracePackage.MATCH__NODE_MATCHING:
				((EStructuralFeature.Setting)getNodeMatching()).set(newValue);
				return;
			case SamtracePackage.MATCH__EDGE_MATCHING:
				((EStructuralFeature.Setting)getEdgeMatching()).set(newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SamtracePackage.MATCH__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamtracePackage.MATCH__NODE_MATCHING:
				getNodeMatching().clear();
				return;
			case SamtracePackage.MATCH__EDGE_MATCHING:
				getEdgeMatching().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SamtracePackage.MATCH__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamtracePackage.MATCH__NODE_MATCHING:
				return nodeMatching != null && !nodeMatching.isEmpty();
			case SamtracePackage.MATCH__EDGE_MATCHING:
				return edgeMatching != null && !edgeMatching.isEmpty();
			case SamtracePackage.MATCH__SIZE:
				return getSize() != SIZE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //MatchImpl
