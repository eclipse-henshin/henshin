/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition.impl;

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
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantor;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Quantification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.QuantificationImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.QuantificationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.QuantificationImpl#getQuantor <em>Quantor</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.QuantificationImpl#getNesting <em>Nesting</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.QuantificationImpl#getContext <em>Context</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.QuantificationImpl#getAttributeCondition <em>Attribute Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class QuantificationImpl extends EObjectImpl implements Quantification {
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
	 * The default value of the '{@link #getQuantor() <em>Quantor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuantor()
	 * @generated
	 * @ordered
	 */
	protected static final Quantor QUANTOR_EDEFAULT = Quantor.EXISTS;

	/**
	 * The cached value of the '{@link #getQuantor() <em>Quantor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuantor()
	 * @generated
	 * @ordered
	 */
	protected Quantor quantor = QUANTOR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNesting() <em>Nesting</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNesting()
	 * @generated
	 * @ordered
	 */
	protected GraphCondition nesting;

	/**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected Graph context;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QuantificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamgraphconditionPackage.Literals.QUANTIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamgraphconditionPackage.QUANTIFICATION__ANNOTATIONS);
		}
		return annotations;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphconditionPackage.QUANTIFICATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Quantor getQuantor() {
		return quantor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQuantor(Quantor newQuantor) {
		Quantor oldQuantor = quantor;
		quantor = newQuantor == null ? QUANTOR_EDEFAULT : newQuantor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphconditionPackage.QUANTIFICATION__QUANTOR, oldQuantor, quantor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GraphCondition getNesting() {
		return nesting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNesting(GraphCondition newNesting, NotificationChain msgs) {
		GraphCondition oldNesting = nesting;
		nesting = newNesting;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamgraphconditionPackage.QUANTIFICATION__NESTING, oldNesting, newNesting);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNesting(GraphCondition newNesting) {
		if (newNesting != nesting) {
			NotificationChain msgs = null;
			if (nesting != null)
				msgs = ((InternalEObject)nesting).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamgraphconditionPackage.QUANTIFICATION__NESTING, null, msgs);
			if (newNesting != null)
				msgs = ((InternalEObject)newNesting).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SamgraphconditionPackage.QUANTIFICATION__NESTING, null, msgs);
			msgs = basicSetNesting(newNesting, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphconditionPackage.QUANTIFICATION__NESTING, newNesting, newNesting));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getContext() {
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContext(Graph newContext, NotificationChain msgs) {
		Graph oldContext = context;
		context = newContext;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamgraphconditionPackage.QUANTIFICATION__CONTEXT, oldContext, newContext);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContext(Graph newContext) {
		if (newContext != context) {
			NotificationChain msgs = null;
			if (context != null)
				msgs = ((InternalEObject)context).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamgraphconditionPackage.QUANTIFICATION__CONTEXT, null, msgs);
			if (newContext != null)
				msgs = ((InternalEObject)newContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SamgraphconditionPackage.QUANTIFICATION__CONTEXT, null, msgs);
			msgs = basicSetContext(newContext, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphconditionPackage.QUANTIFICATION__CONTEXT, newContext, newContext));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamgraphconditionPackage.QUANTIFICATION__ATTRIBUTE_CONDITION, oldAttributeCondition, newAttributeCondition);
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
				msgs = ((InternalEObject)attributeCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamgraphconditionPackage.QUANTIFICATION__ATTRIBUTE_CONDITION, null, msgs);
			if (newAttributeCondition != null)
				msgs = ((InternalEObject)newAttributeCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SamgraphconditionPackage.QUANTIFICATION__ATTRIBUTE_CONDITION, null, msgs);
			msgs = basicSetAttributeCondition(newAttributeCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphconditionPackage.QUANTIFICATION__ATTRIBUTE_CONDITION, newAttributeCondition, newAttributeCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamgraphconditionPackage.QUANTIFICATION__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamgraphconditionPackage.QUANTIFICATION__NESTING:
				return basicSetNesting(null, msgs);
			case SamgraphconditionPackage.QUANTIFICATION__CONTEXT:
				return basicSetContext(null, msgs);
			case SamgraphconditionPackage.QUANTIFICATION__ATTRIBUTE_CONDITION:
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
			case SamgraphconditionPackage.QUANTIFICATION__ANNOTATIONS:
				return getAnnotations();
			case SamgraphconditionPackage.QUANTIFICATION__NAME:
				return getName();
			case SamgraphconditionPackage.QUANTIFICATION__QUANTOR:
				return getQuantor();
			case SamgraphconditionPackage.QUANTIFICATION__NESTING:
				return getNesting();
			case SamgraphconditionPackage.QUANTIFICATION__CONTEXT:
				return getContext();
			case SamgraphconditionPackage.QUANTIFICATION__ATTRIBUTE_CONDITION:
				return getAttributeCondition();
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
			case SamgraphconditionPackage.QUANTIFICATION__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamgraphconditionPackage.QUANTIFICATION__NAME:
				setName((String)newValue);
				return;
			case SamgraphconditionPackage.QUANTIFICATION__QUANTOR:
				setQuantor((Quantor)newValue);
				return;
			case SamgraphconditionPackage.QUANTIFICATION__NESTING:
				setNesting((GraphCondition)newValue);
				return;
			case SamgraphconditionPackage.QUANTIFICATION__CONTEXT:
				setContext((Graph)newValue);
				return;
			case SamgraphconditionPackage.QUANTIFICATION__ATTRIBUTE_CONDITION:
				setAttributeCondition((OperationUse)newValue);
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
			case SamgraphconditionPackage.QUANTIFICATION__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamgraphconditionPackage.QUANTIFICATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SamgraphconditionPackage.QUANTIFICATION__QUANTOR:
				setQuantor(QUANTOR_EDEFAULT);
				return;
			case SamgraphconditionPackage.QUANTIFICATION__NESTING:
				setNesting((GraphCondition)null);
				return;
			case SamgraphconditionPackage.QUANTIFICATION__CONTEXT:
				setContext((Graph)null);
				return;
			case SamgraphconditionPackage.QUANTIFICATION__ATTRIBUTE_CONDITION:
				setAttributeCondition((OperationUse)null);
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
			case SamgraphconditionPackage.QUANTIFICATION__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamgraphconditionPackage.QUANTIFICATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SamgraphconditionPackage.QUANTIFICATION__QUANTOR:
				return quantor != QUANTOR_EDEFAULT;
			case SamgraphconditionPackage.QUANTIFICATION__NESTING:
				return nesting != null;
			case SamgraphconditionPackage.QUANTIFICATION__CONTEXT:
				return context != null;
			case SamgraphconditionPackage.QUANTIFICATION__ATTRIBUTE_CONDITION:
				return attributeCondition != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == AnnotatedElem.class) {
			switch (derivedFeatureID) {
				case SamgraphconditionPackage.QUANTIFICATION__ANNOTATIONS: return SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == AnnotatedElem.class) {
			switch (baseFeatureID) {
				case SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS: return SamgraphconditionPackage.QUANTIFICATION__ANNOTATIONS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", quantor: ");
		result.append(quantor);
		result.append(')');
		return result.toString();
	}

} //QuantificationImpl
