/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory
 * @model kind="package"
 * @generated
 */
public interface SamtracePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "samtrace";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2015/Henshin/sam/samtrace";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "samtrace";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SamtracePackage eINSTANCE = org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.TraceImpl <em>Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.TraceImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getTrace()
	 * @generated
	 */
	int TRACE = 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__ENTRIES = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry <em>Trace Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getTraceEntry()
	 * @generated
	 */
	int TRACE_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_ENTRY__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_ENTRY__STATE = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Trace Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_ENTRY_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.ContinuousTraceEntryImpl <em>Continuous Trace Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.ContinuousTraceEntryImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getContinuousTraceEntry()
	 * @generated
	 */
	int CONTINUOUS_TRACE_ENTRY = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_TRACE_ENTRY__ANNOTATIONS = TRACE_ENTRY__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_TRACE_ENTRY__STATE = TRACE_ENTRY__STATE;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_TRACE_ENTRY__DURATION = TRACE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Continuous Trace Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_TRACE_ENTRY_FEATURE_COUNT = TRACE_ENTRY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.DiscreteTraceEntryImpl <em>Discrete Trace Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.DiscreteTraceEntryImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getDiscreteTraceEntry()
	 * @generated
	 */
	int DISCRETE_TRACE_ENTRY = 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISCRETE_TRACE_ENTRY__ANNOTATIONS = TRACE_ENTRY__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISCRETE_TRACE_ENTRY__STATE = TRACE_ENTRY__STATE;

	/**
	 * The feature id for the '<em><b>Current Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISCRETE_TRACE_ENTRY__CURRENT_RULE = TRACE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Current Match</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISCRETE_TRACE_ENTRY__CURRENT_MATCH = TRACE_ENTRY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Discrete Trace Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISCRETE_TRACE_ENTRY_FEATURE_COUNT = TRACE_ENTRY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchImpl <em>Match</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getMatch()
	 * @generated
	 */
	int MATCH = 4;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Node Matching</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH__NODE_MATCHING = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Edge Matching</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH__EDGE_MATCHING = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH__SIZE = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Match</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchEntryImpl <em>Match Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchEntryImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getMatchEntry()
	 * @generated
	 */
	int MATCH_ENTRY = 5;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Match Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.ProxyEdgeImpl <em>Proxy Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.ProxyEdgeImpl
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getProxyEdge()
	 * @generated
	 */
	int PROXY_EDGE = 6;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_EDGE__ATTRIBUTES = SamgraphPackage.EDGE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_EDGE__ANNOTATIONS = SamgraphPackage.EDGE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_EDGE__SOURCE = SamgraphPackage.EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_EDGE__TARGET = SamgraphPackage.EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_EDGE__INSTANCE_OF = SamgraphPackage.EDGE__INSTANCE_OF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_EDGE__NAME = SamgraphPackage.EDGE__NAME;

	/**
	 * The feature id for the '<em><b>Reference Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_EDGE__REFERENCE_EDGE = SamgraphPackage.EDGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Proxy Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_EDGE_FEATURE_COUNT = SamgraphPackage.EDGE_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtrace.Trace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trace</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.Trace
	 * @generated
	 */
	EClass getTrace();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samtrace.Trace#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.Trace#getEntries()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_Entries();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry <em>Trace Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trace Entry</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry
	 * @generated
	 */
	EClass getTraceEntry();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>State</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry#getState()
	 * @see #getTraceEntry()
	 * @generated
	 */
	EReference getTraceEntry_State();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtrace.ContinuousTraceEntry <em>Continuous Trace Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Continuous Trace Entry</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.ContinuousTraceEntry
	 * @generated
	 */
	EClass getContinuousTraceEntry();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtrace.ContinuousTraceEntry#getDuration <em>Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Duration</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.ContinuousTraceEntry#getDuration()
	 * @see #getContinuousTraceEntry()
	 * @generated
	 */
	EAttribute getContinuousTraceEntry_Duration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry <em>Discrete Trace Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Discrete Trace Entry</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry
	 * @generated
	 */
	EClass getDiscreteTraceEntry();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry#getCurrentRule <em>Current Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Current Rule</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry#getCurrentRule()
	 * @see #getDiscreteTraceEntry()
	 * @generated
	 */
	EReference getDiscreteTraceEntry_CurrentRule();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry#getCurrentMatch <em>Current Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Current Match</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry#getCurrentMatch()
	 * @see #getDiscreteTraceEntry()
	 * @generated
	 */
	EReference getDiscreteTraceEntry_CurrentMatch();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtrace.Match <em>Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Match</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.Match
	 * @generated
	 */
	EClass getMatch();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.henshin.sam.model.samtrace.Match#getNodeMatching <em>Node Matching</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Node Matching</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.Match#getNodeMatching()
	 * @see #getMatch()
	 * @generated
	 */
	EReference getMatch_NodeMatching();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.henshin.sam.model.samtrace.Match#getEdgeMatching <em>Edge Matching</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Edge Matching</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.Match#getEdgeMatching()
	 * @see #getMatch()
	 * @generated
	 */
	EReference getMatch_EdgeMatching();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samtrace.Match#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.Match#getSize()
	 * @see #getMatch()
	 * @generated
	 */
	EAttribute getMatch_Size();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Match Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Match Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyKind="reference" keyType="T" keyRequired="true"
	 *        valueKind="reference" valueType="S" valueRequired="true"
	 * @generated
	 */
	EClass getMatchEntry();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMatchEntry()
	 * @generated
	 */
	EReference getMatchEntry_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMatchEntry()
	 * @generated
	 */
	EReference getMatchEntry_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samtrace.ProxyEdge <em>Proxy Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proxy Edge</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.ProxyEdge
	 * @generated
	 */
	EClass getProxyEdge();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samtrace.ProxyEdge#getReferenceEdge <em>Reference Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reference Edge</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.ProxyEdge#getReferenceEdge()
	 * @see #getProxyEdge()
	 * @generated
	 */
	EReference getProxyEdge_ReferenceEdge();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SamtraceFactory getSamtraceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.TraceImpl <em>Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.TraceImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getTrace()
		 * @generated
		 */
		EClass TRACE = eINSTANCE.getTrace();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__ENTRIES = eINSTANCE.getTrace_Entries();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry <em>Trace Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getTraceEntry()
		 * @generated
		 */
		EClass TRACE_ENTRY = eINSTANCE.getTraceEntry();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE_ENTRY__STATE = eINSTANCE.getTraceEntry_State();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.ContinuousTraceEntryImpl <em>Continuous Trace Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.ContinuousTraceEntryImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getContinuousTraceEntry()
		 * @generated
		 */
		EClass CONTINUOUS_TRACE_ENTRY = eINSTANCE.getContinuousTraceEntry();

		/**
		 * The meta object literal for the '<em><b>Duration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTINUOUS_TRACE_ENTRY__DURATION = eINSTANCE.getContinuousTraceEntry_Duration();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.DiscreteTraceEntryImpl <em>Discrete Trace Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.DiscreteTraceEntryImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getDiscreteTraceEntry()
		 * @generated
		 */
		EClass DISCRETE_TRACE_ENTRY = eINSTANCE.getDiscreteTraceEntry();

		/**
		 * The meta object literal for the '<em><b>Current Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DISCRETE_TRACE_ENTRY__CURRENT_RULE = eINSTANCE.getDiscreteTraceEntry_CurrentRule();

		/**
		 * The meta object literal for the '<em><b>Current Match</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DISCRETE_TRACE_ENTRY__CURRENT_MATCH = eINSTANCE.getDiscreteTraceEntry_CurrentMatch();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchImpl <em>Match</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getMatch()
		 * @generated
		 */
		EClass MATCH = eINSTANCE.getMatch();

		/**
		 * The meta object literal for the '<em><b>Node Matching</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MATCH__NODE_MATCHING = eINSTANCE.getMatch_NodeMatching();

		/**
		 * The meta object literal for the '<em><b>Edge Matching</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MATCH__EDGE_MATCHING = eINSTANCE.getMatch_EdgeMatching();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATCH__SIZE = eINSTANCE.getMatch_Size();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchEntryImpl <em>Match Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.MatchEntryImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getMatchEntry()
		 * @generated
		 */
		EClass MATCH_ENTRY = eINSTANCE.getMatchEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MATCH_ENTRY__KEY = eINSTANCE.getMatchEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MATCH_ENTRY__VALUE = eINSTANCE.getMatchEntry_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.ProxyEdgeImpl <em>Proxy Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.ProxyEdgeImpl
		 * @see org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl#getProxyEdge()
		 * @generated
		 */
		EClass PROXY_EDGE = eINSTANCE.getProxyEdge();

		/**
		 * The meta object literal for the '<em><b>Reference Edge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROXY_EDGE__REFERENCE_EDGE = eINSTANCE.getProxyEdge_ReferenceEdge();

	}

} //SamtracePackage
