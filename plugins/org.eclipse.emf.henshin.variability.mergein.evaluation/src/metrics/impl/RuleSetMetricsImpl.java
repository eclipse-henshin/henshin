/**
 */
package metrics.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import metrics.MetricsPackage;
import metrics.RuleMetrics;
import metrics.RuleSetMetrics;

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
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Set Metrics</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getRuleMetrics <em>Rule Metrics</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getNumberOfRules <em>Number Of Rules</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getRuleSet <em>Rule Set</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getTotalNumberOfNodes <em>Total Number Of Nodes</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getTotalNumberOfEdges <em>Total Number Of Edges</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getTotalNumberOfAttributes <em>Total Number Of Attributes</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getTotalNumberOfLhsNodes <em>Total Number Of Lhs Nodes</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getTotalNumberOfLhsEdges <em>Total Number Of Lhs Edges</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getTotalNumberOfLhsAttributes <em>Total Number Of Lhs Attributes</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getTotalNumberOfAnnotatedNodes <em>Total Number Of Annotated Nodes</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getTotalNumberOfAnnotatedEdges <em>Total Number Of Annotated Edges</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getTotalNumberOfAnnotatedAttributes <em>Total Number Of Annotated Attributes</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getTotalNumberOfNACs <em>Total Number Of NA Cs</em>}</li>
 *   <li>{@link metrics.impl.RuleSetMetricsImpl#getTotalNumberOfPACs <em>Total Number Of PA Cs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuleSetMetricsImpl extends MinimalEObjectImpl.Container implements RuleSetMetrics {
	/**
	 * The cached value of the '{@link #getRuleMetrics() <em>Rule Metrics</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleMetrics()
	 * @generated
	 * @ordered
	 */
	protected EList<RuleMetrics> ruleMetrics;

	/**
	 * The default value of the '{@link #getNumberOfRules() <em>Number Of Rules</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfRules()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_RULES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfRules() <em>Number Of Rules</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfRules()
	 * @generated
	 * @ordered
	 */
	protected int numberOfRules = NUMBER_OF_RULES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRuleSet() <em>Rule Set</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleSet()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> ruleSet;

	/**
	 * The default value of the '{@link #getTotalNumberOfNodes() <em>Total Number Of Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfNodes()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_NODES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalNumberOfNodes() <em>Total Number Of Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfNodes()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfNodes = TOTAL_NUMBER_OF_NODES_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalNumberOfEdges() <em>Total Number Of Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfEdges()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_EDGES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalNumberOfEdges() <em>Total Number Of Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfEdges()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfEdges = TOTAL_NUMBER_OF_EDGES_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalNumberOfAttributes() <em>Total Number Of Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_ATTRIBUTES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalNumberOfAttributes() <em>Total Number Of Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfAttributes()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfAttributes = TOTAL_NUMBER_OF_ATTRIBUTES_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalNumberOfLhsNodes() <em>Total Number Of Lhs Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfLhsNodes()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_LHS_NODES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalNumberOfLhsNodes() <em>Total Number Of Lhs Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfLhsNodes()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfLhsNodes = TOTAL_NUMBER_OF_LHS_NODES_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalNumberOfLhsEdges() <em>Total Number Of Lhs Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfLhsEdges()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_LHS_EDGES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalNumberOfLhsEdges() <em>Total Number Of Lhs Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfLhsEdges()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfLhsEdges = TOTAL_NUMBER_OF_LHS_EDGES_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalNumberOfLhsAttributes() <em>Total Number Of Lhs Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfLhsAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_LHS_ATTRIBUTES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalNumberOfLhsAttributes() <em>Total Number Of Lhs Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfLhsAttributes()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfLhsAttributes = TOTAL_NUMBER_OF_LHS_ATTRIBUTES_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalNumberOfAnnotatedNodes() <em>Total Number Of Annotated Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfAnnotatedNodes()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_ANNOTATED_NODES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalNumberOfAnnotatedNodes() <em>Total Number Of Annotated Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfAnnotatedNodes()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfAnnotatedNodes = TOTAL_NUMBER_OF_ANNOTATED_NODES_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalNumberOfAnnotatedEdges() <em>Total Number Of Annotated Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfAnnotatedEdges()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_ANNOTATED_EDGES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalNumberOfAnnotatedEdges() <em>Total Number Of Annotated Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfAnnotatedEdges()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfAnnotatedEdges = TOTAL_NUMBER_OF_ANNOTATED_EDGES_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalNumberOfAnnotatedAttributes() <em>Total Number Of Annotated Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfAnnotatedAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalNumberOfAnnotatedAttributes() <em>Total Number Of Annotated Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfAnnotatedAttributes()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfAnnotatedAttributes = TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalNumberOfNACs() <em>Total Number Of NA Cs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfNACs()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_NA_CS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalNumberOfNACs() <em>Total Number Of NA Cs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfNACs()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfNACs = TOTAL_NUMBER_OF_NA_CS_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalNumberOfPACs() <em>Total Number Of PA Cs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfPACs()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_PA_CS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalNumberOfPACs() <em>Total Number Of PA Cs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalNumberOfPACs()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfPACs = TOTAL_NUMBER_OF_PA_CS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleSetMetricsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetricsPackage.Literals.RULE_SET_METRICS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RuleMetrics> getRuleMetrics() {
		if (ruleMetrics == null) {
			ruleMetrics = new EObjectContainmentEList<RuleMetrics>(RuleMetrics.class, this, MetricsPackage.RULE_SET_METRICS__RULE_METRICS);
		}
		return ruleMetrics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfRules() {
		return numberOfRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfRules(int newNumberOfRules) {
		int oldNumberOfRules = numberOfRules;
		numberOfRules = newNumberOfRules;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__NUMBER_OF_RULES, oldNumberOfRules, numberOfRules));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rule> getRuleSet() {
		if (ruleSet == null) {
			ruleSet = new EObjectResolvingEList<Rule>(Rule.class, this, MetricsPackage.RULE_SET_METRICS__RULE_SET);
		}
		return ruleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotalNumberOfNodes() {
		return totalNumberOfNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalNumberOfNodes(int newTotalNumberOfNodes) {
		int oldTotalNumberOfNodes = totalNumberOfNodes;
		totalNumberOfNodes = newTotalNumberOfNodes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_NODES, oldTotalNumberOfNodes, totalNumberOfNodes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotalNumberOfEdges() {
		return totalNumberOfEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalNumberOfEdges(int newTotalNumberOfEdges) {
		int oldTotalNumberOfEdges = totalNumberOfEdges;
		totalNumberOfEdges = newTotalNumberOfEdges;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_EDGES, oldTotalNumberOfEdges, totalNumberOfEdges));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotalNumberOfAttributes() {
		return totalNumberOfAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalNumberOfAttributes(int newTotalNumberOfAttributes) {
		int oldTotalNumberOfAttributes = totalNumberOfAttributes;
		totalNumberOfAttributes = newTotalNumberOfAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ATTRIBUTES, oldTotalNumberOfAttributes, totalNumberOfAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotalNumberOfLhsNodes() {
		return totalNumberOfLhsNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalNumberOfLhsNodes(int newTotalNumberOfLhsNodes) {
		int oldTotalNumberOfLhsNodes = totalNumberOfLhsNodes;
		totalNumberOfLhsNodes = newTotalNumberOfLhsNodes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_NODES, oldTotalNumberOfLhsNodes, totalNumberOfLhsNodes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotalNumberOfLhsEdges() {
		return totalNumberOfLhsEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalNumberOfLhsEdges(int newTotalNumberOfLhsEdges) {
		int oldTotalNumberOfLhsEdges = totalNumberOfLhsEdges;
		totalNumberOfLhsEdges = newTotalNumberOfLhsEdges;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_EDGES, oldTotalNumberOfLhsEdges, totalNumberOfLhsEdges));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotalNumberOfLhsAttributes() {
		return totalNumberOfLhsAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalNumberOfLhsAttributes(int newTotalNumberOfLhsAttributes) {
		int oldTotalNumberOfLhsAttributes = totalNumberOfLhsAttributes;
		totalNumberOfLhsAttributes = newTotalNumberOfLhsAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_ATTRIBUTES, oldTotalNumberOfLhsAttributes, totalNumberOfLhsAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotalNumberOfAnnotatedNodes() {
		return totalNumberOfAnnotatedNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalNumberOfAnnotatedNodes(int newTotalNumberOfAnnotatedNodes) {
		int oldTotalNumberOfAnnotatedNodes = totalNumberOfAnnotatedNodes;
		totalNumberOfAnnotatedNodes = newTotalNumberOfAnnotatedNodes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_NODES, oldTotalNumberOfAnnotatedNodes, totalNumberOfAnnotatedNodes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotalNumberOfAnnotatedEdges() {
		return totalNumberOfAnnotatedEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalNumberOfAnnotatedEdges(int newTotalNumberOfAnnotatedEdges) {
		int oldTotalNumberOfAnnotatedEdges = totalNumberOfAnnotatedEdges;
		totalNumberOfAnnotatedEdges = newTotalNumberOfAnnotatedEdges;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_EDGES, oldTotalNumberOfAnnotatedEdges, totalNumberOfAnnotatedEdges));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotalNumberOfAnnotatedAttributes() {
		return totalNumberOfAnnotatedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalNumberOfAnnotatedAttributes(int newTotalNumberOfAnnotatedAttributes) {
		int oldTotalNumberOfAnnotatedAttributes = totalNumberOfAnnotatedAttributes;
		totalNumberOfAnnotatedAttributes = newTotalNumberOfAnnotatedAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES, oldTotalNumberOfAnnotatedAttributes, totalNumberOfAnnotatedAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotalNumberOfNACs() {
		return totalNumberOfNACs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalNumberOfNACs(int newTotalNumberOfNACs) {
		int oldTotalNumberOfNACs = totalNumberOfNACs;
		totalNumberOfNACs = newTotalNumberOfNACs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_NA_CS, oldTotalNumberOfNACs, totalNumberOfNACs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotalNumberOfPACs() {
		return totalNumberOfPACs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalNumberOfPACs(int newTotalNumberOfPACs) {
		int oldTotalNumberOfPACs = totalNumberOfPACs;
		totalNumberOfPACs = newTotalNumberOfPACs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_PA_CS, oldTotalNumberOfPACs, totalNumberOfPACs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public RuleMetrics findRuleMetrics(Rule rule) {
		for (RuleMetrics m : getRuleMetrics()) {
			if (m.getRule() == rule)
				return m;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String createPresentationString() {

		StringBuffer result = new StringBuffer();
		result.append("#Rules:\t");
		result.append(numberOfRules);
		result.append("\t#El./Rule:\t");
		result.append(((double)totalNumberOfNodes+totalNumberOfEdges+totalNumberOfAttributes)/(double)numberOfRules);
		result.append("\t#LHS El./Rule:\t");
		result.append(((double)totalNumberOfLhsNodes+totalNumberOfLhsEdges+totalNumberOfLhsAttributes)/(double)numberOfRules);
		result.append("\t#Elements:\t");
		result.append(totalNumberOfNodes);
		result.append("\t");
		result.append(totalNumberOfEdges);
		result.append("\t");
		result.append(totalNumberOfAttributes);
		result.append("\t#NACs:\t");
		result.append(totalNumberOfNACs);
		result.append("\t#PACs:\t");
		result.append(totalNumberOfPACs);
		result.append("\t#LHS Elements:\t");
		result.append(totalNumberOfLhsNodes);
		result.append("\t");
		result.append(totalNumberOfLhsEdges);
		result.append("\t");
		result.append(totalNumberOfLhsAttributes);
		result.append('\t');
		result.append("\t#Annotated Elements:\t");
		result.append(totalNumberOfAnnotatedNodes);
		result.append("\t");
		result.append(totalNumberOfAnnotatedEdges);
		result.append("\t");
		result.append(totalNumberOfAnnotatedAttributes);
		result.append('\t');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetricsPackage.RULE_SET_METRICS__RULE_METRICS:
				return ((InternalEList<?>)getRuleMetrics()).basicRemove(otherEnd, msgs);
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
			case MetricsPackage.RULE_SET_METRICS__RULE_METRICS:
				return getRuleMetrics();
			case MetricsPackage.RULE_SET_METRICS__NUMBER_OF_RULES:
				return getNumberOfRules();
			case MetricsPackage.RULE_SET_METRICS__RULE_SET:
				return getRuleSet();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_NODES:
				return getTotalNumberOfNodes();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_EDGES:
				return getTotalNumberOfEdges();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ATTRIBUTES:
				return getTotalNumberOfAttributes();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_NODES:
				return getTotalNumberOfLhsNodes();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_EDGES:
				return getTotalNumberOfLhsEdges();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_ATTRIBUTES:
				return getTotalNumberOfLhsAttributes();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_NODES:
				return getTotalNumberOfAnnotatedNodes();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_EDGES:
				return getTotalNumberOfAnnotatedEdges();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES:
				return getTotalNumberOfAnnotatedAttributes();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_NA_CS:
				return getTotalNumberOfNACs();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_PA_CS:
				return getTotalNumberOfPACs();
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
			case MetricsPackage.RULE_SET_METRICS__RULE_METRICS:
				getRuleMetrics().clear();
				getRuleMetrics().addAll((Collection<? extends RuleMetrics>)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__NUMBER_OF_RULES:
				setNumberOfRules((Integer)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__RULE_SET:
				getRuleSet().clear();
				getRuleSet().addAll((Collection<? extends Rule>)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_NODES:
				setTotalNumberOfNodes((Integer)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_EDGES:
				setTotalNumberOfEdges((Integer)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ATTRIBUTES:
				setTotalNumberOfAttributes((Integer)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_NODES:
				setTotalNumberOfLhsNodes((Integer)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_EDGES:
				setTotalNumberOfLhsEdges((Integer)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_ATTRIBUTES:
				setTotalNumberOfLhsAttributes((Integer)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_NODES:
				setTotalNumberOfAnnotatedNodes((Integer)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_EDGES:
				setTotalNumberOfAnnotatedEdges((Integer)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES:
				setTotalNumberOfAnnotatedAttributes((Integer)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_NA_CS:
				setTotalNumberOfNACs((Integer)newValue);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_PA_CS:
				setTotalNumberOfPACs((Integer)newValue);
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
			case MetricsPackage.RULE_SET_METRICS__RULE_METRICS:
				getRuleMetrics().clear();
				return;
			case MetricsPackage.RULE_SET_METRICS__NUMBER_OF_RULES:
				setNumberOfRules(NUMBER_OF_RULES_EDEFAULT);
				return;
			case MetricsPackage.RULE_SET_METRICS__RULE_SET:
				getRuleSet().clear();
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_NODES:
				setTotalNumberOfNodes(TOTAL_NUMBER_OF_NODES_EDEFAULT);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_EDGES:
				setTotalNumberOfEdges(TOTAL_NUMBER_OF_EDGES_EDEFAULT);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ATTRIBUTES:
				setTotalNumberOfAttributes(TOTAL_NUMBER_OF_ATTRIBUTES_EDEFAULT);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_NODES:
				setTotalNumberOfLhsNodes(TOTAL_NUMBER_OF_LHS_NODES_EDEFAULT);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_EDGES:
				setTotalNumberOfLhsEdges(TOTAL_NUMBER_OF_LHS_EDGES_EDEFAULT);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_ATTRIBUTES:
				setTotalNumberOfLhsAttributes(TOTAL_NUMBER_OF_LHS_ATTRIBUTES_EDEFAULT);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_NODES:
				setTotalNumberOfAnnotatedNodes(TOTAL_NUMBER_OF_ANNOTATED_NODES_EDEFAULT);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_EDGES:
				setTotalNumberOfAnnotatedEdges(TOTAL_NUMBER_OF_ANNOTATED_EDGES_EDEFAULT);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES:
				setTotalNumberOfAnnotatedAttributes(TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES_EDEFAULT);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_NA_CS:
				setTotalNumberOfNACs(TOTAL_NUMBER_OF_NA_CS_EDEFAULT);
				return;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_PA_CS:
				setTotalNumberOfPACs(TOTAL_NUMBER_OF_PA_CS_EDEFAULT);
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
			case MetricsPackage.RULE_SET_METRICS__RULE_METRICS:
				return ruleMetrics != null && !ruleMetrics.isEmpty();
			case MetricsPackage.RULE_SET_METRICS__NUMBER_OF_RULES:
				return numberOfRules != NUMBER_OF_RULES_EDEFAULT;
			case MetricsPackage.RULE_SET_METRICS__RULE_SET:
				return ruleSet != null && !ruleSet.isEmpty();
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_NODES:
				return totalNumberOfNodes != TOTAL_NUMBER_OF_NODES_EDEFAULT;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_EDGES:
				return totalNumberOfEdges != TOTAL_NUMBER_OF_EDGES_EDEFAULT;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ATTRIBUTES:
				return totalNumberOfAttributes != TOTAL_NUMBER_OF_ATTRIBUTES_EDEFAULT;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_NODES:
				return totalNumberOfLhsNodes != TOTAL_NUMBER_OF_LHS_NODES_EDEFAULT;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_EDGES:
				return totalNumberOfLhsEdges != TOTAL_NUMBER_OF_LHS_EDGES_EDEFAULT;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_ATTRIBUTES:
				return totalNumberOfLhsAttributes != TOTAL_NUMBER_OF_LHS_ATTRIBUTES_EDEFAULT;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_NODES:
				return totalNumberOfAnnotatedNodes != TOTAL_NUMBER_OF_ANNOTATED_NODES_EDEFAULT;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_EDGES:
				return totalNumberOfAnnotatedEdges != TOTAL_NUMBER_OF_ANNOTATED_EDGES_EDEFAULT;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES:
				return totalNumberOfAnnotatedAttributes != TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES_EDEFAULT;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_NA_CS:
				return totalNumberOfNACs != TOTAL_NUMBER_OF_NA_CS_EDEFAULT;
			case MetricsPackage.RULE_SET_METRICS__TOTAL_NUMBER_OF_PA_CS:
				return totalNumberOfPACs != TOTAL_NUMBER_OF_PA_CS_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case MetricsPackage.RULE_SET_METRICS___FIND_RULE_METRICS__RULE:
				return findRuleMetrics((Rule)arguments.get(0));
			case MetricsPackage.RULE_SET_METRICS___CREATE_PRESENTATION_STRING:
				return createPresentationString();
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (numberOfRules: ");
		result.append(numberOfRules);
		result.append(", totalNumberOfNodes: ");
		result.append(totalNumberOfNodes);
		result.append(", totalNumberOfEdges: ");
		result.append(totalNumberOfEdges);
		result.append(", totalNumberOfAttributes: ");
		result.append(totalNumberOfAttributes);
		result.append(", totalNumberOfLhsNodes: ");
		result.append(totalNumberOfLhsNodes);
		result.append(", totalNumberOfLhsEdges: ");
		result.append(totalNumberOfLhsEdges);
		result.append(", totalNumberOfLhsAttributes: ");
		result.append(totalNumberOfLhsAttributes);
		result.append(", totalNumberOfAnnotatedNodes: ");
		result.append(totalNumberOfAnnotatedNodes);
		result.append(", totalNumberOfAnnotatedEdges: ");
		result.append(totalNumberOfAnnotatedEdges);
		result.append(", totalNumberOfAnnotatedAttributes: ");
		result.append(totalNumberOfAnnotatedAttributes);
		result.append(", totalNumberOfNACs: ");
		result.append(totalNumberOfNACs);
		result.append(", totalNumberOfPACs: ");
		result.append(totalNumberOfPACs);
		result.append(')');
		return result.toString();
	}

} //RuleSetMetricsImpl
