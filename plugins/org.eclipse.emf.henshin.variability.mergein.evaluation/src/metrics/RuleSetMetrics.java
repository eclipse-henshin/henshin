/**
 */
package metrics;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Set Metrics</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link metrics.RuleSetMetrics#getRuleMetrics <em>Rule Metrics</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getNumberOfRules <em>Number Of Rules</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getRuleSet <em>Rule Set</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getTotalNumberOfNodes <em>Total Number Of Nodes</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getTotalNumberOfEdges <em>Total Number Of Edges</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getTotalNumberOfAttributes <em>Total Number Of Attributes</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getTotalNumberOfLhsNodes <em>Total Number Of Lhs Nodes</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getTotalNumberOfLhsEdges <em>Total Number Of Lhs Edges</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getTotalNumberOfLhsAttributes <em>Total Number Of Lhs Attributes</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getTotalNumberOfAnnotatedNodes <em>Total Number Of Annotated Nodes</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getTotalNumberOfAnnotatedEdges <em>Total Number Of Annotated Edges</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getTotalNumberOfAnnotatedAttributes <em>Total Number Of Annotated Attributes</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getTotalNumberOfNACs <em>Total Number Of NA Cs</em>}</li>
 *   <li>{@link metrics.RuleSetMetrics#getTotalNumberOfPACs <em>Total Number Of PA Cs</em>}</li>
 * </ul>
 *
 * @see metrics.MetricsPackage#getRuleSetMetrics()
 * @model
 * @generated
 */
public interface RuleSetMetrics extends EObject {
	/**
	 * Returns the value of the '<em><b>Rule Metrics</b></em>' containment reference list.
	 * The list contents are of type {@link metrics.RuleMetrics}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Metrics</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Metrics</em>' containment reference list.
	 * @see metrics.MetricsPackage#getRuleSetMetrics_RuleMetrics()
	 * @model containment="true"
	 * @generated
	 */
	EList<RuleMetrics> getRuleMetrics();

	/**
	 * Returns the value of the '<em><b>Number Of Rules</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Of Rules</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Rules</em>' attribute.
	 * @see #setNumberOfRules(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_NumberOfRules()
	 * @model
	 * @generated
	 */
	int getNumberOfRules();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getNumberOfRules <em>Number Of Rules</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Rules</em>' attribute.
	 * @see #getNumberOfRules()
	 * @generated
	 */
	void setNumberOfRules(int value);

	/**
	 * Returns the value of the '<em><b>Rule Set</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Rule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Set</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Set</em>' reference list.
	 * @see metrics.MetricsPackage#getRuleSetMetrics_RuleSet()
	 * @model
	 * @generated
	 */
	EList<Rule> getRuleSet();

	/**
	 * Returns the value of the '<em><b>Total Number Of Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of Nodes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Number Of Nodes</em>' attribute.
	 * @see #setTotalNumberOfNodes(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_TotalNumberOfNodes()
	 * @model
	 * @generated
	 */
	int getTotalNumberOfNodes();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getTotalNumberOfNodes <em>Total Number Of Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Number Of Nodes</em>' attribute.
	 * @see #getTotalNumberOfNodes()
	 * @generated
	 */
	void setTotalNumberOfNodes(int value);

	/**
	 * Returns the value of the '<em><b>Total Number Of Edges</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of Edges</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Number Of Edges</em>' attribute.
	 * @see #setTotalNumberOfEdges(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_TotalNumberOfEdges()
	 * @model
	 * @generated
	 */
	int getTotalNumberOfEdges();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getTotalNumberOfEdges <em>Total Number Of Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Number Of Edges</em>' attribute.
	 * @see #getTotalNumberOfEdges()
	 * @generated
	 */
	void setTotalNumberOfEdges(int value);

	/**
	 * Returns the value of the '<em><b>Total Number Of Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of Attributes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Number Of Attributes</em>' attribute.
	 * @see #setTotalNumberOfAttributes(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_TotalNumberOfAttributes()
	 * @model
	 * @generated
	 */
	int getTotalNumberOfAttributes();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getTotalNumberOfAttributes <em>Total Number Of Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Number Of Attributes</em>' attribute.
	 * @see #getTotalNumberOfAttributes()
	 * @generated
	 */
	void setTotalNumberOfAttributes(int value);

	/**
	 * Returns the value of the '<em><b>Total Number Of Lhs Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of Lhs Nodes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Number Of Lhs Nodes</em>' attribute.
	 * @see #setTotalNumberOfLhsNodes(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_TotalNumberOfLhsNodes()
	 * @model
	 * @generated
	 */
	int getTotalNumberOfLhsNodes();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getTotalNumberOfLhsNodes <em>Total Number Of Lhs Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Number Of Lhs Nodes</em>' attribute.
	 * @see #getTotalNumberOfLhsNodes()
	 * @generated
	 */
	void setTotalNumberOfLhsNodes(int value);

	/**
	 * Returns the value of the '<em><b>Total Number Of Lhs Edges</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of Lhs Edges</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Number Of Lhs Edges</em>' attribute.
	 * @see #setTotalNumberOfLhsEdges(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_TotalNumberOfLhsEdges()
	 * @model
	 * @generated
	 */
	int getTotalNumberOfLhsEdges();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getTotalNumberOfLhsEdges <em>Total Number Of Lhs Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Number Of Lhs Edges</em>' attribute.
	 * @see #getTotalNumberOfLhsEdges()
	 * @generated
	 */
	void setTotalNumberOfLhsEdges(int value);

	/**
	 * Returns the value of the '<em><b>Total Number Of Lhs Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of Lhs Attributes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Number Of Lhs Attributes</em>' attribute.
	 * @see #setTotalNumberOfLhsAttributes(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_TotalNumberOfLhsAttributes()
	 * @model
	 * @generated
	 */
	int getTotalNumberOfLhsAttributes();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getTotalNumberOfLhsAttributes <em>Total Number Of Lhs Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Number Of Lhs Attributes</em>' attribute.
	 * @see #getTotalNumberOfLhsAttributes()
	 * @generated
	 */
	void setTotalNumberOfLhsAttributes(int value);

	/**
	 * Returns the value of the '<em><b>Total Number Of Annotated Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of Annotated Nodes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Number Of Annotated Nodes</em>' attribute.
	 * @see #setTotalNumberOfAnnotatedNodes(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_TotalNumberOfAnnotatedNodes()
	 * @model
	 * @generated
	 */
	int getTotalNumberOfAnnotatedNodes();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getTotalNumberOfAnnotatedNodes <em>Total Number Of Annotated Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Number Of Annotated Nodes</em>' attribute.
	 * @see #getTotalNumberOfAnnotatedNodes()
	 * @generated
	 */
	void setTotalNumberOfAnnotatedNodes(int value);

	/**
	 * Returns the value of the '<em><b>Total Number Of Annotated Edges</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of Annotated Edges</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Number Of Annotated Edges</em>' attribute.
	 * @see #setTotalNumberOfAnnotatedEdges(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_TotalNumberOfAnnotatedEdges()
	 * @model
	 * @generated
	 */
	int getTotalNumberOfAnnotatedEdges();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getTotalNumberOfAnnotatedEdges <em>Total Number Of Annotated Edges</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Number Of Annotated Edges</em>' attribute.
	 * @see #getTotalNumberOfAnnotatedEdges()
	 * @generated
	 */
	void setTotalNumberOfAnnotatedEdges(int value);

	/**
	 * Returns the value of the '<em><b>Total Number Of Annotated Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of Annotated Attributes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Number Of Annotated Attributes</em>' attribute.
	 * @see #setTotalNumberOfAnnotatedAttributes(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_TotalNumberOfAnnotatedAttributes()
	 * @model
	 * @generated
	 */
	int getTotalNumberOfAnnotatedAttributes();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getTotalNumberOfAnnotatedAttributes <em>Total Number Of Annotated Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Number Of Annotated Attributes</em>' attribute.
	 * @see #getTotalNumberOfAnnotatedAttributes()
	 * @generated
	 */
	void setTotalNumberOfAnnotatedAttributes(int value);

	/**
	 * Returns the value of the '<em><b>Total Number Of NA Cs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of NA Cs</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Number Of NA Cs</em>' attribute.
	 * @see #setTotalNumberOfNACs(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_TotalNumberOfNACs()
	 * @model
	 * @generated
	 */
	int getTotalNumberOfNACs();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getTotalNumberOfNACs <em>Total Number Of NA Cs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Number Of NA Cs</em>' attribute.
	 * @see #getTotalNumberOfNACs()
	 * @generated
	 */
	void setTotalNumberOfNACs(int value);

	/**
	 * Returns the value of the '<em><b>Total Number Of PA Cs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of PA Cs</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Number Of PA Cs</em>' attribute.
	 * @see #setTotalNumberOfPACs(int)
	 * @see metrics.MetricsPackage#getRuleSetMetrics_TotalNumberOfPACs()
	 * @model
	 * @generated
	 */
	int getTotalNumberOfPACs();

	/**
	 * Sets the value of the '{@link metrics.RuleSetMetrics#getTotalNumberOfPACs <em>Total Number Of PA Cs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Number Of PA Cs</em>' attribute.
	 * @see #getTotalNumberOfPACs()
	 * @generated
	 */
	void setTotalNumberOfPACs(int value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	RuleMetrics findRuleMetrics(Rule rule);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String createPresentationString();

} // RuleSetMetrics
