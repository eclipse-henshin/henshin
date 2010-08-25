/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.examples.mediator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

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
 * @see org.eclipse.emf.henshin.examples.mediator.MediatorFactory
 * @model kind="package"
 * @generated
 */
public class MediatorPackage extends EPackageImpl {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "mediator";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "http://www.eclipse.org/emf/2010/Henshin/Examples/Mediator";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "mediator";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final MediatorPackage eINSTANCE = org.eclipse.emf.henshin.examples.mediator.MediatorPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.mediator.Channel <em>Channel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.mediator.Channel
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getChannel()
	 * @generated
	 */
	public static final int CHANNEL = 1;

	/**
	 * The number of structural features of the '<em>Channel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CHANNEL_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.mediator.Worker <em>Worker</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.mediator.Worker
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getWorker()
	 * @generated
	 */
	public static final int WORKER = 0;

	/**
	 * The feature id for the '<em><b>Start</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int WORKER__START = CHANNEL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Stop</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int WORKER__STOP = CHANNEL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int WORKER__ACTIVE = CHANNEL_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Worker</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int WORKER_FEATURE_COUNT = CHANNEL_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.mediator.Node <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.mediator.Node
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getNode()
	 * @generated
	 */
	public static final int NODE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NODE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NODE__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NODE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.mediator.Network <em>Network</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.mediator.Network
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getNetwork()
	 * @generated
	 */
	public static final int NETWORK = 3;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NETWORK__NODES = 0;

	/**
	 * The feature id for the '<em><b>Channels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NETWORK__CHANNELS = 1;

	/**
	 * The number of structural features of the '<em>Network</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NETWORK_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.mediator.FIFO <em>FIFO</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.mediator.FIFO
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getFIFO()
	 * @generated
	 */
	public static final int FIFO = 4;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FIFO__SOURCE = CHANNEL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sink</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FIFO__SINK = CHANNEL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Full</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FIFO__FULL = CHANNEL_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>FIFO</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FIFO_FEATURE_COUNT = CHANNEL_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.mediator.SyncDrain <em>Sync Drain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.mediator.SyncDrain
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getSyncDrain()
	 * @generated
	 */
	public static final int SYNC_DRAIN = 5;

	/**
	 * The feature id for the '<em><b>Source1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SYNC_DRAIN__SOURCE1 = CHANNEL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SYNC_DRAIN__SOURCE2 = CHANNEL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Sync Drain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SYNC_DRAIN_FEATURE_COUNT = CHANNEL_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.mediator.Sync <em>Sync</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.mediator.Sync
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getSync()
	 * @generated
	 */
	public static final int SYNC = 6;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SYNC__SOURCE = CHANNEL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sink</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SYNC__SINK = CHANNEL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Sync</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SYNC_FEATURE_COUNT = CHANNEL_FEATURE_COUNT + 2;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass workerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass channelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass networkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fifoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass syncDrainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass syncEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MediatorPackage() {
		super(eNS_URI, MediatorFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link MediatorPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MediatorPackage init() {
		if (isInited) return (MediatorPackage)EPackage.Registry.INSTANCE.getEPackage(MediatorPackage.eNS_URI);

		// Obtain or create and register package
		MediatorPackage theMediatorPackage = (MediatorPackage)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MediatorPackage ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MediatorPackage());

		isInited = true;

		// Create package meta-data objects
		theMediatorPackage.createPackageContents();

		// Initialize created meta-data
		theMediatorPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMediatorPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MediatorPackage.eNS_URI, theMediatorPackage);
		return theMediatorPackage;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.mediator.Worker <em>Worker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Worker</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Worker
	 * @generated
	 */
	public EClass getWorker() {
		return workerEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.examples.mediator.Worker#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Worker#getStart()
	 * @see #getWorker()
	 * @generated
	 */
	public EReference getWorker_Start() {
		return (EReference)workerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.examples.mediator.Worker#getStop <em>Stop</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Stop</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Worker#getStop()
	 * @see #getWorker()
	 * @generated
	 */
	public EReference getWorker_Stop() {
		return (EReference)workerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.examples.mediator.Worker#isActive <em>Active</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Active</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Worker#isActive()
	 * @see #getWorker()
	 * @generated
	 */
	public EAttribute getWorker_Active() {
		return (EAttribute)workerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.mediator.Channel <em>Channel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Channel</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Channel
	 * @generated
	 */
	public EClass getChannel() {
		return channelEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.mediator.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Node
	 * @generated
	 */
	public EClass getNode() {
		return nodeEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.examples.mediator.Node#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Node#getName()
	 * @see #getNode()
	 * @generated
	 */
	public EAttribute getNode_Name() {
		return (EAttribute)nodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.examples.mediator.Node#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Node#getType()
	 * @see #getNode()
	 * @generated
	 */
	public EAttribute getNode_Type() {
		return (EAttribute)nodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.mediator.Network <em>Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Network</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Network
	 * @generated
	 */
	public EClass getNetwork() {
		return networkEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.examples.mediator.Network#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Network#getNodes()
	 * @see #getNetwork()
	 * @generated
	 */
	public EReference getNetwork_Nodes() {
		return (EReference)networkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.examples.mediator.Network#getChannels <em>Channels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Channels</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Network#getChannels()
	 * @see #getNetwork()
	 * @generated
	 */
	public EReference getNetwork_Channels() {
		return (EReference)networkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.mediator.FIFO <em>FIFO</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>FIFO</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.FIFO
	 * @generated
	 */
	public EClass getFIFO() {
		return fifoEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.examples.mediator.FIFO#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.FIFO#getSource()
	 * @see #getFIFO()
	 * @generated
	 */
	public EReference getFIFO_Source() {
		return (EReference)fifoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.examples.mediator.FIFO#getSink <em>Sink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sink</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.FIFO#getSink()
	 * @see #getFIFO()
	 * @generated
	 */
	public EReference getFIFO_Sink() {
		return (EReference)fifoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.examples.mediator.FIFO#isFull <em>Full</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Full</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.FIFO#isFull()
	 * @see #getFIFO()
	 * @generated
	 */
	public EAttribute getFIFO_Full() {
		return (EAttribute)fifoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.mediator.SyncDrain <em>Sync Drain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sync Drain</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.SyncDrain
	 * @generated
	 */
	public EClass getSyncDrain() {
		return syncDrainEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.examples.mediator.SyncDrain#getSource1 <em>Source1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source1</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.SyncDrain#getSource1()
	 * @see #getSyncDrain()
	 * @generated
	 */
	public EReference getSyncDrain_Source1() {
		return (EReference)syncDrainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.examples.mediator.SyncDrain#getSource2 <em>Source2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source2</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.SyncDrain#getSource2()
	 * @see #getSyncDrain()
	 * @generated
	 */
	public EReference getSyncDrain_Source2() {
		return (EReference)syncDrainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.mediator.Sync <em>Sync</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sync</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Sync
	 * @generated
	 */
	public EClass getSync() {
		return syncEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.examples.mediator.Sync#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Sync#getSource()
	 * @see #getSync()
	 * @generated
	 */
	public EReference getSync_Source() {
		return (EReference)syncEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.examples.mediator.Sync#getSink <em>Sink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sink</em>'.
	 * @see org.eclipse.emf.henshin.examples.mediator.Sync#getSink()
	 * @see #getSync()
	 * @generated
	 */
	public EReference getSync_Sink() {
		return (EReference)syncEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public MediatorFactory getMediatorFactory() {
		return (MediatorFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		workerEClass = createEClass(WORKER);
		createEReference(workerEClass, WORKER__START);
		createEReference(workerEClass, WORKER__STOP);
		createEAttribute(workerEClass, WORKER__ACTIVE);

		channelEClass = createEClass(CHANNEL);

		nodeEClass = createEClass(NODE);
		createEAttribute(nodeEClass, NODE__NAME);
		createEAttribute(nodeEClass, NODE__TYPE);

		networkEClass = createEClass(NETWORK);
		createEReference(networkEClass, NETWORK__NODES);
		createEReference(networkEClass, NETWORK__CHANNELS);

		fifoEClass = createEClass(FIFO);
		createEReference(fifoEClass, FIFO__SOURCE);
		createEReference(fifoEClass, FIFO__SINK);
		createEAttribute(fifoEClass, FIFO__FULL);

		syncDrainEClass = createEClass(SYNC_DRAIN);
		createEReference(syncDrainEClass, SYNC_DRAIN__SOURCE1);
		createEReference(syncDrainEClass, SYNC_DRAIN__SOURCE2);

		syncEClass = createEClass(SYNC);
		createEReference(syncEClass, SYNC__SOURCE);
		createEReference(syncEClass, SYNC__SINK);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		workerEClass.getESuperTypes().add(this.getChannel());
		fifoEClass.getESuperTypes().add(this.getChannel());
		syncDrainEClass.getESuperTypes().add(this.getChannel());
		syncEClass.getESuperTypes().add(this.getChannel());

		// Initialize classes and features; add operations and parameters
		initEClass(workerEClass, Worker.class, "Worker", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWorker_Start(), this.getNode(), null, "start", null, 0, 1, Worker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWorker_Stop(), this.getNode(), null, "stop", null, 0, 1, Worker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWorker_Active(), ecorePackage.getEBoolean(), "active", null, 0, 1, Worker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(channelEClass, Channel.class, "Channel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nodeEClass, Node.class, "Node", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNode_Name(), ecorePackage.getEString(), "name", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNode_Type(), ecorePackage.getEString(), "type", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(networkEClass, Network.class, "Network", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNetwork_Nodes(), this.getNode(), null, "nodes", null, 0, -1, Network.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNetwork_Channels(), this.getChannel(), null, "channels", null, 0, -1, Network.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fifoEClass, org.eclipse.emf.henshin.examples.mediator.FIFO.class, "FIFO", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFIFO_Source(), this.getNode(), null, "source", null, 0, 1, org.eclipse.emf.henshin.examples.mediator.FIFO.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFIFO_Sink(), this.getNode(), null, "sink", null, 0, 1, org.eclipse.emf.henshin.examples.mediator.FIFO.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFIFO_Full(), ecorePackage.getEBoolean(), "full", null, 0, 1, org.eclipse.emf.henshin.examples.mediator.FIFO.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(syncDrainEClass, SyncDrain.class, "SyncDrain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSyncDrain_Source1(), this.getNode(), null, "source1", null, 0, 1, SyncDrain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSyncDrain_Source2(), this.getNode(), null, "source2", null, 0, 1, SyncDrain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(syncEClass, Sync.class, "Sync", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSync_Source(), this.getNode(), null, "source", null, 0, 1, Sync.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSync_Sink(), this.getNode(), null, "sink", null, 0, 1, Sync.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

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
	public interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.mediator.Worker <em>Worker</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.mediator.Worker
		 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getWorker()
		 * @generated
		 */
		public static final EClass WORKER = eINSTANCE.getWorker();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference WORKER__START = eINSTANCE.getWorker_Start();

		/**
		 * The meta object literal for the '<em><b>Stop</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference WORKER__STOP = eINSTANCE.getWorker_Stop();

		/**
		 * The meta object literal for the '<em><b>Active</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute WORKER__ACTIVE = eINSTANCE.getWorker_Active();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.mediator.Channel <em>Channel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.mediator.Channel
		 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getChannel()
		 * @generated
		 */
		public static final EClass CHANNEL = eINSTANCE.getChannel();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.mediator.Node <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.mediator.Node
		 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getNode()
		 * @generated
		 */
		public static final EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute NODE__NAME = eINSTANCE.getNode_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute NODE__TYPE = eINSTANCE.getNode_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.mediator.Network <em>Network</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.mediator.Network
		 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getNetwork()
		 * @generated
		 */
		public static final EClass NETWORK = eINSTANCE.getNetwork();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference NETWORK__NODES = eINSTANCE.getNetwork_Nodes();

		/**
		 * The meta object literal for the '<em><b>Channels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference NETWORK__CHANNELS = eINSTANCE.getNetwork_Channels();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.mediator.FIFO <em>FIFO</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.mediator.FIFO
		 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getFIFO()
		 * @generated
		 */
		public static final EClass FIFO = eINSTANCE.getFIFO();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference FIFO__SOURCE = eINSTANCE.getFIFO_Source();

		/**
		 * The meta object literal for the '<em><b>Sink</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference FIFO__SINK = eINSTANCE.getFIFO_Sink();

		/**
		 * The meta object literal for the '<em><b>Full</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute FIFO__FULL = eINSTANCE.getFIFO_Full();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.mediator.SyncDrain <em>Sync Drain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.mediator.SyncDrain
		 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getSyncDrain()
		 * @generated
		 */
		public static final EClass SYNC_DRAIN = eINSTANCE.getSyncDrain();

		/**
		 * The meta object literal for the '<em><b>Source1</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SYNC_DRAIN__SOURCE1 = eINSTANCE.getSyncDrain_Source1();

		/**
		 * The meta object literal for the '<em><b>Source2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SYNC_DRAIN__SOURCE2 = eINSTANCE.getSyncDrain_Source2();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.mediator.Sync <em>Sync</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.mediator.Sync
		 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getSync()
		 * @generated
		 */
		public static final EClass SYNC = eINSTANCE.getSync();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SYNC__SOURCE = eINSTANCE.getSync_Source();

		/**
		 * The meta object literal for the '<em><b>Sink</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SYNC__SINK = eINSTANCE.getSync_Sink();

	}

} //MediatorPackage
