/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.henshin.sam.model.samtrace.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SamtraceFactoryImpl extends EFactoryImpl implements SamtraceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SamtraceFactory init() {
		try {
			SamtraceFactory theSamtraceFactory = (SamtraceFactory)EPackage.Registry.INSTANCE.getEFactory(SamtracePackage.eNS_URI);
			if (theSamtraceFactory != null) {
				return theSamtraceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SamtraceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamtraceFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SamtracePackage.TRACE: return createTrace();
			case SamtracePackage.CONTINUOUS_TRACE_ENTRY: return createContinuousTraceEntry();
			case SamtracePackage.DISCRETE_TRACE_ENTRY: return createDiscreteTraceEntry();
			case SamtracePackage.MATCH: return createMatch();
			case SamtracePackage.MATCH_ENTRY: return (EObject)createMatchEntry();
			case SamtracePackage.PROXY_EDGE: return createProxyEdge();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Trace createTrace() {
		TraceImpl trace = new TraceImpl();
		return trace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContinuousTraceEntry createContinuousTraceEntry() {
		ContinuousTraceEntryImpl continuousTraceEntry = new ContinuousTraceEntryImpl();
		return continuousTraceEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiscreteTraceEntry createDiscreteTraceEntry() {
		DiscreteTraceEntryImpl discreteTraceEntry = new DiscreteTraceEntryImpl();
		return discreteTraceEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Match createMatch() {
		MatchImpl match = new MatchImpl();
		return match;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <T, S> Map.Entry<T, S> createMatchEntry() {
		MatchEntryImpl<T, S> matchEntry = new MatchEntryImpl<T, S>();
		return matchEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProxyEdge createProxyEdge() {
		ProxyEdgeImpl proxyEdge = new ProxyEdgeImpl();
		return proxyEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamtracePackage getSamtracePackage() {
		return (SamtracePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SamtracePackage getPackage() {
		return SamtracePackage.eINSTANCE;
	}

} //SamtraceFactoryImpl
