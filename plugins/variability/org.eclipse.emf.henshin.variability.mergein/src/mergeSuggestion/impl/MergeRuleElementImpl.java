/**
 */
package mergeSuggestion.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import mergeSuggestion.MergeRuleElement;
import mergeSuggestion.MergeSuggestionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Merge Rule Element</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link mergeSuggestion.impl.MergeRuleElementImpl#getReferenceElements <em>Reference Elements</em>}</li>
 *   <li>{@link mergeSuggestion.impl.MergeRuleElementImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MergeRuleElementImpl extends MinimalEObjectImpl.Container
		implements MergeRuleElement {
	/**
	 * The cached value of the '{@link #getReferenceElements() <em>Reference Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenceElements()
	 * @generated
	 * @ordered
	 */
	protected EList<GraphElement> referenceElements;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected MergeRuleElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MergeSuggestionPackage.Literals.MERGE_RULE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GraphElement> getReferenceElements() {
		if (referenceElements == null) {
			referenceElements = new EObjectResolvingEList<GraphElement>(GraphElement.class, this, MergeSuggestionPackage.MERGE_RULE_ELEMENT__REFERENCE_ELEMENTS);
		}
		return referenceElements;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getName() {
		StringBuilder name = new StringBuilder();
		if (!getReferenceElements().isEmpty()) {
			name.append("[");
			name.append(getReferenceElements().size());
			name.append(" ");
			appendGraphKind(name);
			name.append(" ");
			name.append(getReferenceElements().iterator().next().eClass()
					.getName());
			name.append("s] ");
			collectNamesOfReferenceElements(name);
		}
		return name.toString();
	}

	private void collectNamesOfReferenceElements(StringBuilder name) {
		Iterator<GraphElement> it = getReferenceElements().iterator();
		while (it.hasNext()) {
			GraphElement next = it.next();
			if (next instanceof Node) {
				Node node = (Node) next;
				if (node.getName() != null)
					name.append(node.getName());
				name.append(':');
				name.append(node.getType().getName());
			} else if (next instanceof Edge) {
				Edge edge = (Edge) next;
				if (edge.getSource().getName() != null)
					name.append(edge.getSource().getName());
				name.append("->");
				if (edge.getTarget().getName() != null)
					name.append(edge.getTarget().getName());
				name.append(':');
				name.append(((Edge) next).getType().getName());
			} else if (next instanceof Attribute) {
				Attribute attribute = (Attribute) next;
				name.append(attribute.getType().getName());
				name.append('=');
				if (attribute.getValue() != null)
					name.append(attribute.getValue());
			}

			if (it.hasNext())
				name.append(", ");
		}
	}

	private void appendGraphKind(StringBuilder name) {
		if (isNacElement())
			name.append("NAC");
		else if (isPacElement())
			name.append("PAC");
		else if (isLhsElement())
			name.append("LHS");
		else if (isRhsElement())
			name.append("RHS");
		
		if (isMultiRuleElement())
			name.append("*");
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MergeSuggestionPackage.MERGE_RULE_ELEMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isNacElement() {
		if (!getReferenceElements().isEmpty()) {
			GraphElement any = getReferenceElements().iterator().next();
			if (any.getGraph() != null && any.getGraph().isNestedCondition()) {
				NestedCondition nc = (NestedCondition) any.getGraph()
						.eContainer();
				return (nc.isNAC());
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isPacElement() {
		if (!getReferenceElements().isEmpty()) {
			GraphElement any = getReferenceElements().iterator().next();
			if (any.getGraph() != null && any.getGraph().isNestedCondition()) {
				NestedCondition nc = (NestedCondition) any.getGraph()
						.eContainer();
				return (nc.isPAC());
			}
		}
		return false;

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isLhsElement() {
		if (!getReferenceElements().isEmpty()) {
			GraphElement any = getReferenceElements().iterator().next();
			if (any.getGraph() != null)
				return any.getGraph().isLhs();
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isRhsElement() {
		if (!getReferenceElements().isEmpty()) {
			GraphElement any = getReferenceElements().iterator().next();
			if (any.getGraph() != null)
				return any.getGraph().isRhs();
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isMultiRuleElement() {
		if (!getReferenceElements().isEmpty()) {
			GraphElement any = getReferenceElements().iterator().next();
			if (any.getGraph() != null) {
				if (any.getGraph().getRule().eContainer() instanceof Rule) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT__REFERENCE_ELEMENTS:
				return getReferenceElements();
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT__NAME:
				return getName();
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
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT__REFERENCE_ELEMENTS:
				getReferenceElements().clear();
				getReferenceElements().addAll((Collection<? extends GraphElement>)newValue);
				return;
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT__NAME:
				setName((String)newValue);
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
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT__REFERENCE_ELEMENTS:
				getReferenceElements().clear();
				return;
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
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
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT__REFERENCE_ELEMENTS:
				return referenceElements != null && !referenceElements.isEmpty();
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments)
			throws InvocationTargetException {
		switch (operationID) {
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT___IS_NAC_ELEMENT:
				return isNacElement();
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT___IS_PAC_ELEMENT:
				return isPacElement();
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT___IS_LHS_ELEMENT:
				return isLhsElement();
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT___IS_RHS_ELEMENT:
				return isRhsElement();
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT___IS_MULTI_RULE_ELEMENT:
				return isMultiRuleElement();
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // MergeRuleElementImpl
