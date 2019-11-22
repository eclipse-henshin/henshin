/**
 */
package metrics.impl;

import metrics.MetricsPackage;
import metrics.RuleMetrics;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Metrics</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getNumberOfNodes <em>Number Of Nodes</em>}</li>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getNumberOfEdges <em>Number Of Edges</em>}</li>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getNumberOfAttributes <em>Number Of Attributes</em>}</li>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getNumberOfLhsNodes <em>Number Of Lhs Nodes</em>}</li>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getNumberOfLhsEdges <em>Number Of Lhs Edges</em>}</li>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getNumberOfLhsAttributes <em>Number Of Lhs Attributes</em>}</li>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getNumberOfAnnotatedNodes <em>Number Of Annotated Nodes</em>}</li>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getNumberOfAnnotatedEdges <em>Number Of Annotated Edges</em>}</li>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getNumberOfAnnotatedAttributes <em>Number Of Annotated Attributes</em>}</li>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getNumberOfNACs <em>Number Of NA Cs</em>}</li>
 *   <li>{@link metrics.impl.RuleMetricsImpl#getNumberOfPACs <em>Number Of PA Cs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuleMetricsImpl extends MinimalEObjectImpl.Container implements RuleMetrics {
	/**
	 * The default value of the '{@link #getNumberOfNodes() <em>Number Of Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfNodes()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_NODES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfNodes() <em>Number Of Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfNodes()
	 * @generated
	 * @ordered
	 */
	protected int numberOfNodes = NUMBER_OF_NODES_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfEdges() <em>Number Of Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfEdges()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_EDGES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfEdges() <em>Number Of Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfEdges()
	 * @generated
	 * @ordered
	 */
	protected int numberOfEdges = NUMBER_OF_EDGES_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfAttributes() <em>Number Of Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_ATTRIBUTES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfAttributes() <em>Number Of Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfAttributes()
	 * @generated
	 * @ordered
	 */
	protected int numberOfAttributes = NUMBER_OF_ATTRIBUTES_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfLhsNodes() <em>Number Of Lhs Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfLhsNodes()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_LHS_NODES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfLhsNodes() <em>Number Of Lhs Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfLhsNodes()
	 * @generated
	 * @ordered
	 */
	protected int numberOfLhsNodes = NUMBER_OF_LHS_NODES_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfLhsEdges() <em>Number Of Lhs Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfLhsEdges()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_LHS_EDGES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfLhsEdges() <em>Number Of Lhs Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfLhsEdges()
	 * @generated
	 * @ordered
	 */
	protected int numberOfLhsEdges = NUMBER_OF_LHS_EDGES_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfLhsAttributes() <em>Number Of Lhs Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfLhsAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_LHS_ATTRIBUTES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfLhsAttributes() <em>Number Of Lhs Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfLhsAttributes()
	 * @generated
	 * @ordered
	 */
	protected int numberOfLhsAttributes = NUMBER_OF_LHS_ATTRIBUTES_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfAnnotatedNodes() <em>Number Of Annotated Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfAnnotatedNodes()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_ANNOTATED_NODES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfAnnotatedNodes() <em>Number Of Annotated Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfAnnotatedNodes()
	 * @generated
	 * @ordered
	 */
	protected int numberOfAnnotatedNodes = NUMBER_OF_ANNOTATED_NODES_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfAnnotatedEdges() <em>Number Of Annotated Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfAnnotatedEdges()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_ANNOTATED_EDGES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfAnnotatedEdges() <em>Number Of Annotated Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfAnnotatedEdges()
	 * @generated
	 * @ordered
	 */
	protected int numberOfAnnotatedEdges = NUMBER_OF_ANNOTATED_EDGES_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfAnnotatedAttributes() <em>Number Of Annotated Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfAnnotatedAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_ANNOTATED_ATTRIBUTES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfAnnotatedAttributes() <em>Number Of Annotated Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfAnnotatedAttributes()
	 * @generated
	 * @ordered
	 */
	protected int numberOfAnnotatedAttributes = NUMBER_OF_ANNOTATED_ATTRIBUTES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected Rule rule;

	/**
	 * The default value of the '{@link #getNumberOfNACs() <em>Number Of NA Cs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfNACs()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_NA_CS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfNACs() <em>Number Of NA Cs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfNACs()
	 * @generated
	 * @ordered
	 */
	protected int numberOfNACs = NUMBER_OF_NA_CS_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfPACs() <em>Number Of PA Cs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfPACs()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_PA_CS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfPACs() <em>Number Of PA Cs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfPACs()
	 * @generated
	 * @ordered
	 */
	protected int numberOfPACs = NUMBER_OF_PA_CS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleMetricsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetricsPackage.Literals.RULE_METRICS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfNodes(int newNumberOfNodes) {
		int oldNumberOfNodes = numberOfNodes;
		numberOfNodes = newNumberOfNodes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__NUMBER_OF_NODES, oldNumberOfNodes, numberOfNodes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfEdges() {
		return numberOfEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfEdges(int newNumberOfEdges) {
		int oldNumberOfEdges = numberOfEdges;
		numberOfEdges = newNumberOfEdges;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__NUMBER_OF_EDGES, oldNumberOfEdges, numberOfEdges));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfAttributes() {
		return numberOfAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfAttributes(int newNumberOfAttributes) {
		int oldNumberOfAttributes = numberOfAttributes;
		numberOfAttributes = newNumberOfAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__NUMBER_OF_ATTRIBUTES, oldNumberOfAttributes, numberOfAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfLhsNodes() {
		return numberOfLhsNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfLhsNodes(int newNumberOfLhsNodes) {
		int oldNumberOfLhsNodes = numberOfLhsNodes;
		numberOfLhsNodes = newNumberOfLhsNodes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_NODES, oldNumberOfLhsNodes, numberOfLhsNodes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfLhsEdges() {
		return numberOfLhsEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfLhsEdges(int newNumberOfLhsEdges) {
		int oldNumberOfLhsEdges = numberOfLhsEdges;
		numberOfLhsEdges = newNumberOfLhsEdges;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_EDGES, oldNumberOfLhsEdges, numberOfLhsEdges));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfLhsAttributes() {
		return numberOfLhsAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfLhsAttributes(int newNumberOfLhsAttributes) {
		int oldNumberOfLhsAttributes = numberOfLhsAttributes;
		numberOfLhsAttributes = newNumberOfLhsAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_ATTRIBUTES, oldNumberOfLhsAttributes, numberOfLhsAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRule() {
		if (rule != null && rule.eIsProxy()) {
			InternalEObject oldRule = (InternalEObject)rule;
			rule = (Rule)eResolveProxy(oldRule);
			if (rule != oldRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetricsPackage.RULE_METRICS__RULE, oldRule, rule));
			}
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule basicGetRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule(Rule newRule) {
		Rule oldRule = rule;
		rule = newRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__RULE, oldRule, rule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfNACs() {
		return numberOfNACs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfNACs(int newNumberOfNACs) {
		int oldNumberOfNACs = numberOfNACs;
		numberOfNACs = newNumberOfNACs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__NUMBER_OF_NA_CS, oldNumberOfNACs, numberOfNACs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfPACs() {
		return numberOfPACs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfPACs(int newNumberOfPACs) {
		int oldNumberOfPACs = numberOfPACs;
		numberOfPACs = newNumberOfPACs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__NUMBER_OF_PA_CS, oldNumberOfPACs, numberOfPACs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfAnnotatedNodes() {
		return numberOfAnnotatedNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfAnnotatedNodes(int newNumberOfAnnotatedNodes) {
		int oldNumberOfAnnotatedNodes = numberOfAnnotatedNodes;
		numberOfAnnotatedNodes = newNumberOfAnnotatedNodes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_NODES, oldNumberOfAnnotatedNodes, numberOfAnnotatedNodes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfAnnotatedEdges() {
		return numberOfAnnotatedEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfAnnotatedEdges(int newNumberOfAnnotatedEdges) {
		int oldNumberOfAnnotatedEdges = numberOfAnnotatedEdges;
		numberOfAnnotatedEdges = newNumberOfAnnotatedEdges;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_EDGES, oldNumberOfAnnotatedEdges, numberOfAnnotatedEdges));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfAnnotatedAttributes() {
		return numberOfAnnotatedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfAnnotatedAttributes(int newNumberOfAnnotatedAttributes) {
		int oldNumberOfAnnotatedAttributes = numberOfAnnotatedAttributes;
		numberOfAnnotatedAttributes = newNumberOfAnnotatedAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_ATTRIBUTES, oldNumberOfAnnotatedAttributes, numberOfAnnotatedAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetricsPackage.RULE_METRICS__NUMBER_OF_NODES:
				return getNumberOfNodes();
			case MetricsPackage.RULE_METRICS__NUMBER_OF_EDGES:
				return getNumberOfEdges();
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ATTRIBUTES:
				return getNumberOfAttributes();
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_NODES:
				return getNumberOfLhsNodes();
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_EDGES:
				return getNumberOfLhsEdges();
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_ATTRIBUTES:
				return getNumberOfLhsAttributes();
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_NODES:
				return getNumberOfAnnotatedNodes();
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_EDGES:
				return getNumberOfAnnotatedEdges();
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_ATTRIBUTES:
				return getNumberOfAnnotatedAttributes();
			case MetricsPackage.RULE_METRICS__RULE:
				if (resolve) return getRule();
				return basicGetRule();
			case MetricsPackage.RULE_METRICS__NUMBER_OF_NA_CS:
				return getNumberOfNACs();
			case MetricsPackage.RULE_METRICS__NUMBER_OF_PA_CS:
				return getNumberOfPACs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetricsPackage.RULE_METRICS__NUMBER_OF_NODES:
				setNumberOfNodes((Integer)newValue);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_EDGES:
				setNumberOfEdges((Integer)newValue);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ATTRIBUTES:
				setNumberOfAttributes((Integer)newValue);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_NODES:
				setNumberOfLhsNodes((Integer)newValue);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_EDGES:
				setNumberOfLhsEdges((Integer)newValue);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_ATTRIBUTES:
				setNumberOfLhsAttributes((Integer)newValue);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_NODES:
				setNumberOfAnnotatedNodes((Integer)newValue);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_EDGES:
				setNumberOfAnnotatedEdges((Integer)newValue);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_ATTRIBUTES:
				setNumberOfAnnotatedAttributes((Integer)newValue);
				return;
			case MetricsPackage.RULE_METRICS__RULE:
				setRule((Rule)newValue);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_NA_CS:
				setNumberOfNACs((Integer)newValue);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_PA_CS:
				setNumberOfPACs((Integer)newValue);
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
			case MetricsPackage.RULE_METRICS__NUMBER_OF_NODES:
				setNumberOfNodes(NUMBER_OF_NODES_EDEFAULT);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_EDGES:
				setNumberOfEdges(NUMBER_OF_EDGES_EDEFAULT);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ATTRIBUTES:
				setNumberOfAttributes(NUMBER_OF_ATTRIBUTES_EDEFAULT);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_NODES:
				setNumberOfLhsNodes(NUMBER_OF_LHS_NODES_EDEFAULT);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_EDGES:
				setNumberOfLhsEdges(NUMBER_OF_LHS_EDGES_EDEFAULT);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_ATTRIBUTES:
				setNumberOfLhsAttributes(NUMBER_OF_LHS_ATTRIBUTES_EDEFAULT);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_NODES:
				setNumberOfAnnotatedNodes(NUMBER_OF_ANNOTATED_NODES_EDEFAULT);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_EDGES:
				setNumberOfAnnotatedEdges(NUMBER_OF_ANNOTATED_EDGES_EDEFAULT);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_ATTRIBUTES:
				setNumberOfAnnotatedAttributes(NUMBER_OF_ANNOTATED_ATTRIBUTES_EDEFAULT);
				return;
			case MetricsPackage.RULE_METRICS__RULE:
				setRule((Rule)null);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_NA_CS:
				setNumberOfNACs(NUMBER_OF_NA_CS_EDEFAULT);
				return;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_PA_CS:
				setNumberOfPACs(NUMBER_OF_PA_CS_EDEFAULT);
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
			case MetricsPackage.RULE_METRICS__NUMBER_OF_NODES:
				return numberOfNodes != NUMBER_OF_NODES_EDEFAULT;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_EDGES:
				return numberOfEdges != NUMBER_OF_EDGES_EDEFAULT;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ATTRIBUTES:
				return numberOfAttributes != NUMBER_OF_ATTRIBUTES_EDEFAULT;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_NODES:
				return numberOfLhsNodes != NUMBER_OF_LHS_NODES_EDEFAULT;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_EDGES:
				return numberOfLhsEdges != NUMBER_OF_LHS_EDGES_EDEFAULT;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_LHS_ATTRIBUTES:
				return numberOfLhsAttributes != NUMBER_OF_LHS_ATTRIBUTES_EDEFAULT;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_NODES:
				return numberOfAnnotatedNodes != NUMBER_OF_ANNOTATED_NODES_EDEFAULT;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_EDGES:
				return numberOfAnnotatedEdges != NUMBER_OF_ANNOTATED_EDGES_EDEFAULT;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_ANNOTATED_ATTRIBUTES:
				return numberOfAnnotatedAttributes != NUMBER_OF_ANNOTATED_ATTRIBUTES_EDEFAULT;
			case MetricsPackage.RULE_METRICS__RULE:
				return rule != null;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_NA_CS:
				return numberOfNACs != NUMBER_OF_NA_CS_EDEFAULT;
			case MetricsPackage.RULE_METRICS__NUMBER_OF_PA_CS:
				return numberOfPACs != NUMBER_OF_PA_CS_EDEFAULT;
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
		result.append(" (numberOfNodes: ");
		result.append(numberOfNodes);
		result.append(", numberOfEdges: ");
		result.append(numberOfEdges);
		result.append(", numberOfAttributes: ");
		result.append(numberOfAttributes);
		result.append(", numberOfLhsNodes: ");
		result.append(numberOfLhsNodes);
		result.append(", numberOfLhsEdges: ");
		result.append(numberOfLhsEdges);
		result.append(", numberOfLhsAttributes: ");
		result.append(numberOfLhsAttributes);
		result.append(", numberOfAnnotatedNodes: ");
		result.append(numberOfAnnotatedNodes);
		result.append(", numberOfAnnotatedEdges: ");
		result.append(numberOfAnnotatedEdges);
		result.append(", numberOfAnnotatedAttributes: ");
		result.append(numberOfAnnotatedAttributes);
		result.append(", numberOfNACs: ");
		result.append(numberOfNACs);
		result.append(", numberOfPACs: ");
		result.append(numberOfPACs);
		result.append(')');
		return result.toString();
	}

} //RuleMetricsImpl
