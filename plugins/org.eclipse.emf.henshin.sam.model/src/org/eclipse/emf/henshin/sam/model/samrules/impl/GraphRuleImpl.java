/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samrules.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Graph Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.impl.GraphRuleImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.impl.GraphRuleImpl#getLeft <em>Left</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.impl.GraphRuleImpl#getRight <em>Right</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.impl.GraphRuleImpl#getAttributeCondition <em>Attribute Condition</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.impl.GraphRuleImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.impl.GraphRuleImpl#isUrgent <em>Urgent</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.impl.GraphRuleImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GraphRuleImpl extends EObjectImpl implements GraphRule {
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
	 * The cached value of the '{@link #getLeft() <em>Left</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeft()
	 * @generated
	 * @ordered
	 */
	protected RuleGraph left;

	/**
	 * The cached value of the '{@link #getRight() <em>Right</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRight()
	 * @generated
	 * @ordered
	 */
	protected RuleGraph right;

	/**
	 * The cached value of the '{@link #getAttributeCondition() <em>Attribute Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeCondition()
	 * @generated
	 * @ordered
	 */
	protected OperationUse attributeCondition;

	/**
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected static final int PRIORITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected int priority = PRIORITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isUrgent() <em>Urgent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUrgent()
	 * @generated
	 * @ordered
	 */
	protected static final boolean URGENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUrgent() <em>Urgent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUrgent()
	 * @generated
	 * @ordered
	 */
	protected boolean urgent = URGENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GraphRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamrulesPackage.Literals.GRAPH_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamrulesPackage.GRAPH_RULE__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleGraph getLeft() {
		return left;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLeft(RuleGraph newLeft, NotificationChain msgs) {
		RuleGraph oldLeft = left;
		left = newLeft;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamrulesPackage.GRAPH_RULE__LEFT, oldLeft, newLeft);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeft(RuleGraph newLeft) {
		if (newLeft != left) {
			NotificationChain msgs = null;
			if (left != null)
				msgs = ((InternalEObject)left).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamrulesPackage.GRAPH_RULE__LEFT, null, msgs);
			if (newLeft != null)
				msgs = ((InternalEObject)newLeft).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SamrulesPackage.GRAPH_RULE__LEFT, null, msgs);
			msgs = basicSetLeft(newLeft, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamrulesPackage.GRAPH_RULE__LEFT, newLeft, newLeft));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleGraph getRight() {
		return right;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRight(RuleGraph newRight, NotificationChain msgs) {
		RuleGraph oldRight = right;
		right = newRight;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamrulesPackage.GRAPH_RULE__RIGHT, oldRight, newRight);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRight(RuleGraph newRight) {
		if (newRight != right) {
			NotificationChain msgs = null;
			if (right != null)
				msgs = ((InternalEObject)right).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamrulesPackage.GRAPH_RULE__RIGHT, null, msgs);
			if (newRight != null)
				msgs = ((InternalEObject)newRight).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SamrulesPackage.GRAPH_RULE__RIGHT, null, msgs);
			msgs = basicSetRight(newRight, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamrulesPackage.GRAPH_RULE__RIGHT, newRight, newRight));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationUse getAttributeCondition() {
		return attributeCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeCondition(OperationUse newAttributeCondition, NotificationChain msgs) {
		OperationUse oldAttributeCondition = attributeCondition;
		attributeCondition = newAttributeCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamrulesPackage.GRAPH_RULE__ATTRIBUTE_CONDITION, oldAttributeCondition, newAttributeCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeCondition(OperationUse newAttributeCondition) {
		if (newAttributeCondition != attributeCondition) {
			NotificationChain msgs = null;
			if (attributeCondition != null)
				msgs = ((InternalEObject)attributeCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamrulesPackage.GRAPH_RULE__ATTRIBUTE_CONDITION, null, msgs);
			if (newAttributeCondition != null)
				msgs = ((InternalEObject)newAttributeCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SamrulesPackage.GRAPH_RULE__ATTRIBUTE_CONDITION, null, msgs);
			msgs = basicSetAttributeCondition(newAttributeCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamrulesPackage.GRAPH_RULE__ATTRIBUTE_CONDITION, newAttributeCondition, newAttributeCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(int newPriority) {
		int oldPriority = priority;
		priority = newPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamrulesPackage.GRAPH_RULE__PRIORITY, oldPriority, priority));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUrgent() {
		return urgent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrgent(boolean newUrgent) {
		boolean oldUrgent = urgent;
		urgent = newUrgent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamrulesPackage.GRAPH_RULE__URGENT, oldUrgent, urgent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamrulesPackage.GRAPH_RULE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamrulesPackage.GRAPH_RULE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamrulesPackage.GRAPH_RULE__LEFT:
				return basicSetLeft(null, msgs);
			case SamrulesPackage.GRAPH_RULE__RIGHT:
				return basicSetRight(null, msgs);
			case SamrulesPackage.GRAPH_RULE__ATTRIBUTE_CONDITION:
				return basicSetAttributeCondition(null, msgs);
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
			case SamrulesPackage.GRAPH_RULE__ANNOTATIONS:
				return getAnnotations();
			case SamrulesPackage.GRAPH_RULE__LEFT:
				return getLeft();
			case SamrulesPackage.GRAPH_RULE__RIGHT:
				return getRight();
			case SamrulesPackage.GRAPH_RULE__ATTRIBUTE_CONDITION:
				return getAttributeCondition();
			case SamrulesPackage.GRAPH_RULE__PRIORITY:
				return getPriority();
			case SamrulesPackage.GRAPH_RULE__URGENT:
				return isUrgent();
			case SamrulesPackage.GRAPH_RULE__NAME:
				return getName();
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
			case SamrulesPackage.GRAPH_RULE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamrulesPackage.GRAPH_RULE__LEFT:
				setLeft((RuleGraph)newValue);
				return;
			case SamrulesPackage.GRAPH_RULE__RIGHT:
				setRight((RuleGraph)newValue);
				return;
			case SamrulesPackage.GRAPH_RULE__ATTRIBUTE_CONDITION:
				setAttributeCondition((OperationUse)newValue);
				return;
			case SamrulesPackage.GRAPH_RULE__PRIORITY:
				setPriority((Integer)newValue);
				return;
			case SamrulesPackage.GRAPH_RULE__URGENT:
				setUrgent((Boolean)newValue);
				return;
			case SamrulesPackage.GRAPH_RULE__NAME:
				setName((String)newValue);
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
			case SamrulesPackage.GRAPH_RULE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamrulesPackage.GRAPH_RULE__LEFT:
				setLeft((RuleGraph)null);
				return;
			case SamrulesPackage.GRAPH_RULE__RIGHT:
				setRight((RuleGraph)null);
				return;
			case SamrulesPackage.GRAPH_RULE__ATTRIBUTE_CONDITION:
				setAttributeCondition((OperationUse)null);
				return;
			case SamrulesPackage.GRAPH_RULE__PRIORITY:
				setPriority(PRIORITY_EDEFAULT);
				return;
			case SamrulesPackage.GRAPH_RULE__URGENT:
				setUrgent(URGENT_EDEFAULT);
				return;
			case SamrulesPackage.GRAPH_RULE__NAME:
				setName(NAME_EDEFAULT);
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
			case SamrulesPackage.GRAPH_RULE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamrulesPackage.GRAPH_RULE__LEFT:
				return left != null;
			case SamrulesPackage.GRAPH_RULE__RIGHT:
				return right != null;
			case SamrulesPackage.GRAPH_RULE__ATTRIBUTE_CONDITION:
				return attributeCondition != null;
			case SamrulesPackage.GRAPH_RULE__PRIORITY:
				return priority != PRIORITY_EDEFAULT;
			case SamrulesPackage.GRAPH_RULE__URGENT:
				return urgent != URGENT_EDEFAULT;
			case SamrulesPackage.GRAPH_RULE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (priority: ");
		result.append(priority);
		result.append(", urgent: ");
		result.append(urgent);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //GraphRuleImpl
