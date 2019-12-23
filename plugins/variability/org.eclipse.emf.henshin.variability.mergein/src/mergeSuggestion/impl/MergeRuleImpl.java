/**
 */
package mergeSuggestion.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Rule;

import mergeSuggestion.MergeNAC;
import mergeSuggestion.MergePAC;
import mergeSuggestion.MergeRule;
import mergeSuggestion.MergeRuleElement;
import mergeSuggestion.MergeSuggestionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Merge Rule</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link mergeSuggestion.impl.MergeRuleImpl#getMasterRule <em>Master Rule</em>}</li>
 *   <li>{@link mergeSuggestion.impl.MergeRuleImpl#getRules <em>Rules</em>}</li>
 *   <li>{@link mergeSuggestion.impl.MergeRuleImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link mergeSuggestion.impl.MergeRuleImpl#getName <em>Name</em>}</li>
 *   <li>{@link mergeSuggestion.impl.MergeRuleImpl#getMergeNacs <em>Merge Nacs</em>}</li>
 *   <li>{@link mergeSuggestion.impl.MergeRuleImpl#getMergePacs <em>Merge Pacs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MergeRuleImpl extends MinimalEObjectImpl.Container implements
		MergeRule {
	/**
	 * The cached value of the '{@link #getMasterRule() <em>Master Rule</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMasterRule()
	 * @generated
	 * @ordered
	 */
	protected Rule masterRule;

	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> rules;

	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<MergeRuleElement> elements;

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
	 * The cached value of the '{@link #getMergeNacs() <em>Merge Nacs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMergeNacs()
	 * @generated
	 * @ordered
	 */
	protected EList<MergeNAC> mergeNacs;

	/**
	 * The cached value of the '{@link #getMergePacs() <em>Merge Pacs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMergePacs()
	 * @generated
	 * @ordered
	 */
	protected EList<MergePAC> mergePacs;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected MergeRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MergeSuggestionPackage.Literals.MERGE_RULE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getMasterRule() {
		if (masterRule != null && masterRule.eIsProxy()) {
			InternalEObject oldMasterRule = (InternalEObject)masterRule;
			masterRule = (Rule)eResolveProxy(oldMasterRule);
			if (masterRule != oldMasterRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MergeSuggestionPackage.MERGE_RULE__MASTER_RULE, oldMasterRule, masterRule));
			}
		}
		return masterRule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Rule basicGetMasterRule() {
		return masterRule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setMasterRule(Rule newMasterRule) {
		Rule oldMasterRule = masterRule;
		masterRule = newMasterRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MergeSuggestionPackage.MERGE_RULE__MASTER_RULE, oldMasterRule, masterRule));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rule> getRules() {
		if (rules == null) {
			rules = new EObjectResolvingEList<Rule>(Rule.class, this, MergeSuggestionPackage.MERGE_RULE__RULES);
		}
		return rules;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MergeRuleElement> getElements() {
		if (elements == null) {
			elements = new EObjectContainmentEList<MergeRuleElement>(MergeRuleElement.class, this, MergeSuggestionPackage.MERGE_RULE__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getName() {
		StringBuilder sb = new StringBuilder();
		if (!getRules().isEmpty()) {
			Iterator<Rule> it = getRules().iterator();
			while (it.hasNext()) {
				Rule next = it.next();
				sb.append(next.getName());
				if (it.hasNext())
					sb.append(", ");
			}
		}
		
		return sb.toString();
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
			eNotify(new ENotificationImpl(this, Notification.SET, MergeSuggestionPackage.MERGE_RULE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MergeNAC> getMergeNacs() {
		if (mergeNacs == null) {
			mergeNacs = new EObjectContainmentEList<MergeNAC>(MergeNAC.class, this, MergeSuggestionPackage.MERGE_RULE__MERGE_NACS);
		}
		return mergeNacs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MergePAC> getMergePacs() {
		if (mergePacs == null) {
			mergePacs = new EObjectContainmentEList<MergePAC>(MergePAC.class, this, MergeSuggestionPackage.MERGE_RULE__MERGE_PACS);
		}
		return mergePacs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public MergeRuleElement findMergeRuleElement(GraphElement graphElement) {
		for (MergeRuleElement mergeRuleElement : getElements()) {
			if (mergeRuleElement.getReferenceElements().contains(graphElement))
				return mergeRuleElement;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void addMergeRuleElement(MergeRuleElement mergeRuleElement) {
		EList<GraphElement> refElemens = mergeRuleElement
				.getReferenceElements();
		for (MergeRuleElement el : getElements()) {
			for (GraphElement ge : el.getReferenceElements()) {
				if (refElemens.contains(ge)) {
					if (el.getReferenceElements().containsAll(refElemens))
						return;
					else
						throw new RuntimeException(
								"Error changing MergeRule: Tried to add a different MergeRuleElement for the same graph element!");
				}
			}
		}
		getElements().add(mergeRuleElement);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void addMergeNAC(MergeNAC mergeNac) {
		EList<Graph> refGraphs = mergeNac.getReferenceNACs();
		for (MergeNAC currentNAC : getMergeNacs()) {
			for (Graph g : currentNAC.getReferenceNACs()) {
				if (refGraphs.contains(g)) {
					if (currentNAC.getReferenceNACs().containsAll(refGraphs))
						return;
				throw new RuntimeException(
								"Error changing MergeRule: Tried to add a different MergeNAC for the same graph!");
				}
			}
		}
		getMergeNacs().add(mergeNac);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void addMergePAC(MergePAC mergePac) {
		EList<Graph> refGraphs = mergePac.getReferencePACs();
		for (MergePAC currentPAC : getMergePacs()) {
			for (Graph g : currentPAC.getReferencePACs()) {
				if (refGraphs.contains(g)) {
					if (currentPAC.getReferencePACs().containsAll(refGraphs))
							return;
				throw new RuntimeException(
								"Error changing MergeRule: Tried to add a different MergePAC for the same graph!");
				}
			}
		}
		getMergePacs().add(mergePac);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public MergeNAC findMergeNAC(Graph graph) {
		for (MergeNAC nac : getMergeNacs()) {
			if (nac.getReferenceNACs().contains(graph))
				return nac;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public MergePAC findMergePAC(Graph graph) {
		for (MergePAC pac : getMergePacs()) {
			if (pac.getReferencePACs().contains(graph))
				return pac;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MergeSuggestionPackage.MERGE_RULE__ELEMENTS:
				return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
			case MergeSuggestionPackage.MERGE_RULE__MERGE_NACS:
				return ((InternalEList<?>)getMergeNacs()).basicRemove(otherEnd, msgs);
			case MergeSuggestionPackage.MERGE_RULE__MERGE_PACS:
				return ((InternalEList<?>)getMergePacs()).basicRemove(otherEnd, msgs);
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
			case MergeSuggestionPackage.MERGE_RULE__MASTER_RULE:
				if (resolve) return getMasterRule();
				return basicGetMasterRule();
			case MergeSuggestionPackage.MERGE_RULE__RULES:
				return getRules();
			case MergeSuggestionPackage.MERGE_RULE__ELEMENTS:
				return getElements();
			case MergeSuggestionPackage.MERGE_RULE__NAME:
				return getName();
			case MergeSuggestionPackage.MERGE_RULE__MERGE_NACS:
				return getMergeNacs();
			case MergeSuggestionPackage.MERGE_RULE__MERGE_PACS:
				return getMergePacs();
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
			case MergeSuggestionPackage.MERGE_RULE__MASTER_RULE:
				setMasterRule((Rule)newValue);
				return;
			case MergeSuggestionPackage.MERGE_RULE__RULES:
				getRules().clear();
				getRules().addAll((Collection<? extends Rule>)newValue);
				return;
			case MergeSuggestionPackage.MERGE_RULE__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends MergeRuleElement>)newValue);
				return;
			case MergeSuggestionPackage.MERGE_RULE__NAME:
				setName((String)newValue);
				return;
			case MergeSuggestionPackage.MERGE_RULE__MERGE_NACS:
				getMergeNacs().clear();
				getMergeNacs().addAll((Collection<? extends MergeNAC>)newValue);
				return;
			case MergeSuggestionPackage.MERGE_RULE__MERGE_PACS:
				getMergePacs().clear();
				getMergePacs().addAll((Collection<? extends MergePAC>)newValue);
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
			case MergeSuggestionPackage.MERGE_RULE__MASTER_RULE:
				setMasterRule((Rule)null);
				return;
			case MergeSuggestionPackage.MERGE_RULE__RULES:
				getRules().clear();
				return;
			case MergeSuggestionPackage.MERGE_RULE__ELEMENTS:
				getElements().clear();
				return;
			case MergeSuggestionPackage.MERGE_RULE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MergeSuggestionPackage.MERGE_RULE__MERGE_NACS:
				getMergeNacs().clear();
				return;
			case MergeSuggestionPackage.MERGE_RULE__MERGE_PACS:
				getMergePacs().clear();
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
			case MergeSuggestionPackage.MERGE_RULE__MASTER_RULE:
				return masterRule != null;
			case MergeSuggestionPackage.MERGE_RULE__RULES:
				return rules != null && !rules.isEmpty();
			case MergeSuggestionPackage.MERGE_RULE__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case MergeSuggestionPackage.MERGE_RULE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MergeSuggestionPackage.MERGE_RULE__MERGE_NACS:
				return mergeNacs != null && !mergeNacs.isEmpty();
			case MergeSuggestionPackage.MERGE_RULE__MERGE_PACS:
				return mergePacs != null && !mergePacs.isEmpty();
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
			case MergeSuggestionPackage.MERGE_RULE___FIND_MERGE_RULE_ELEMENT__GRAPHELEMENT:
				return findMergeRuleElement((GraphElement)arguments.get(0));
			case MergeSuggestionPackage.MERGE_RULE___ADD_MERGE_RULE_ELEMENT__MERGERULEELEMENT:
				addMergeRuleElement((MergeRuleElement)arguments.get(0));
				return null;
			case MergeSuggestionPackage.MERGE_RULE___ADD_MERGE_NAC__MERGENAC:
				addMergeNAC((MergeNAC)arguments.get(0));
				return null;
			case MergeSuggestionPackage.MERGE_RULE___ADD_MERGE_PAC__MERGEPAC:
				addMergePAC((MergePAC)arguments.get(0));
				return null;
			case MergeSuggestionPackage.MERGE_RULE___FIND_MERGE_NAC__GRAPH:
				return findMergeNAC((Graph)arguments.get(0));
			case MergeSuggestionPackage.MERGE_RULE___FIND_MERGE_PAC__GRAPH:
				return findMergePAC((Graph)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("MergeRule comprising ");
		sb.append(rules.size());
		sb.append(" rules [");
		for (Iterator<Rule> it = getRules().iterator(); it.hasNext();) {
			Rule rule = it.next();
			sb.append(rule.getName());
			if (it.hasNext())
				sb.append(", ");
		}
		sb.append("] and ");
		sb.append(elements.size());
		sb.append(" elements:\n");
		for (MergeRuleElement e : elements) {
			sb.append(" * ");
			if (!e.getReferenceElements().isEmpty())
				sb.append(e.getReferenceElements().iterator().next().toString());
			else
				sb.append("null");
			sb.append(" (");
			sb.append(e.getReferenceElements().size());
			sb.append(" corresponding elements)\n");

		}
		return sb.toString();
	}
} // MergeRuleImpl
