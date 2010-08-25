/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Philipps-University Marburg
 *******************************************************************************/
package org.eclipse.emf.henshin.examples.metamodelevolution;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceFactory;

/**
 * This meta-model evolution example is a proof-of-concept showing how such a
 * engineering process can be implemented with Henshin. This case study follows
 * the <i>manual specification</i> approach i.e. we encode meta-model and
 * instance model changes manually since currently there does not exist a
 * meta-model evolution framework based on Henshin. Nevertheless, this is going
 * to give a practical idea how (semi-) automatic meta-model evolution can be
 * realized with Henshin leading to an \emph{operator-based co-evolution}
 * approach.
 * <p>
 * Henshin is able to handle any Ecore-based model that is why we can create
 * transformation rules for both, meta-models and instance models. Meta-models
 * may occur in form of an Eclipse plugin with generated model classes or
 * standalone as <i>.ecore</i> file. The latter is more flexible and since
 * Henshin supports Dynamic EMF, we use such Ecore files in our approach. The
 * control flow is currently implemented in this Java class which loads related
 * models and transformation rules and which triggers the transformation
 * performed by the Henshin interpreter.
 * <p>
 * Our case study is dealing with the evolution of a Petri net meta-model. It
 * contains nodes <tt>Place</tt> and <tt>Transition</tt> with direct
 * bidirectional references between them. The evolution intends to replace such
 * direct bidirectional references by a reference class. This might by useful
 * e.g. to introduce additional attributes according to this relation. However,
 * meta-model modifications often require an adaption of instance models. Please
 * note, that the prepare meta-model rules are quite general and not restricted
 * on our Petri net example.<br>
 * 
 * The meta-model evolution is performed in three separate steps:
 * <ol>
 * <li>Creation of new elements in the meta-model.
 * <li>Creation of instances of the new element in the instance model by
 * deleting the corresponding old references the same time.
 * <li>Deletion of the substituted references in the meta-model.
 * </ol>
 * <p>
 * <strong>Remark:</strong> Make sure, that the model folder (see {@link #BASE})
 * contains fresh unmodified files. In case of doubt, please copy fresh file
 * from the backup folder into the model folder.<br>
 * <strong>Remark:</strong> Feel free to give us feedback about this example by
 * sending an email to <a
 * href="mailto:henshin-dev@eclipse.org">henshin-dev@eclipse.org</a> or visit
 * the <a href="http://www.eclipse.org/projects/project_summary.php?projectid=modeling.emft.henshin"
 * >Henshin project website</a>.
 * 
 * @author Stefan Jurack (sjurack)
 * 
 */
public class Evolution1 {

	private static final String BASE = "src/org/eclipse/emf/henshin/examples/metamodelevolution/model/";
	/**
	 * Meta-model, instance model and rule files.
	 */
	private static final String MODEL_PETRI_META = BASE + "petri.ecore";
	private static final String MODEL_PETRI_INSTANCE = BASE + "Net1.xmi";
	private static final String HENSHIN_PETRI_META = BASE + "petriM.henshin";
	private static final String HENSHIN_PETRI_INSTANCE = BASE
			+ "petriI.henshin";
	/**
	 * Meta-model and instance model need to be in the same resource set, in
	 * order to use the same (!!, not only equal) types.
	 */
	ResourceSet resourceSet = new ResourceSetImpl();

	/**
	 * Implements the control flow for the whol meta-model evolution.
	 */
	private void start() {

		initializeResourceFactories();

		/*
		 * Load the petri net meta-model which Henshin rules and instance models
		 * base on.
		 */
		EPackage petri = loadPetriEcoreModel();

		/**
		 * STEP 1: Create new elements in the meta-model.
		 */
		UnitApplication mm_unit1App = evolveMetaModel_ReplaceRefWithRefclass(
				petri, "Place", "Transition", "ArcPT");

		/*
		 * If the rule/unit was successfully applied, we can fetch the matched
		 * classes in the meta-model. They are needed to perform a migration of
		 * the instance model.
		 */
		EClass srcType = (EClass) mm_unit1App.getParameterValue("objSource");
		EClass trgType = (EClass) mm_unit1App.getParameterValue("objTarget");
		EClass refclassType = (EClass) mm_unit1App.getParameterValue("objRefclass");
		EReference refType = (EReference) mm_unit1App.getParameterValue("objRef");
		EReference refSrcTrg = (EReference) mm_unit1App
				.getParameterValue("objRefSrcTrg");
		EReference refTrgSrc = (EReference) mm_unit1App
				.getParameterValue("objRefTrgSrc");

		/**
		 * STEP 2: Migrate instance model i.e. create instance of new classes
		 * and references and delete unneeded old references
		 */
		migrateInstanceModel_ReplaceRefWithRefclass(petri, srcType, trgType,
				refType, refclassType, refSrcTrg, refTrgSrc);

		/**
		 * STEP 3: Remove old (and now unused) reference types from meta-model.
		 */
		evolveMetaModel_DeleteOldReference(petri, refType);

	}// start

	/**
	 * In this method the Henshin rule is loaded and equipped with informations
	 * to perform a replacement of a direct reference with a reference class. In
	 * fact, the direct reference is not delete, but the new reference class
	 * created and associated.
	 * 
	 * @param petri
	 *            Meta-model root object.
	 * @return the unit application evolving the meta-model, if successfully
	 *         applied. Otherwise <code>null</code> is returned.
	 */
	private UnitApplication evolveMetaModel_ReplaceRefWithRefclass(
			EPackage petri, String srcNodeName, String trgNodeName,
			String refclassName) {

		// initialize henshin package and load Henshin transformation system
		TransformationSystem tsM = loadPetriTrafoSystemM();

		// instantiate Henshin interpreter objects
		EmfGraph graphM = new EmfGraph();

		/*
		 * If the left-hand side of a rule contains EDataType instances (e.g.
		 * :EDataType(name="EString")) in order to match Ecore datatypes, the
		 * EmfGraph instance has to be additionally equipped with an initialized
		 * ecore model (see below). This is useful e.g. if an class shall be
		 * equipped with an additional attribute of a certain Ecore type.
		 */
		// EObject ecoreRoot = EcorePackage.eINSTANCE;
		// graphM.addRoot(ecoreRoot);
		graphM.addRoot(petri);
		EmfEngine engineM = new EmfEngine(graphM);

		// select rule
		Rule mm_rule1 = tsM.findRuleByName("MM_CreateRefClass");

		/*
		 * UnitApplication encapsulates a transformation basing on an engine and
		 * a transformation unit. It allows furthermore to set parameters which are either
		 * values (see below) or objects.
		 */
		UnitApplication mm_unit1App = new UnitApplication(engineM, mm_rule1);
		mm_unit1App.setParameterValue("srcName", srcNodeName);
		mm_unit1App.setParameterValue("trgName", trgNodeName);
		mm_unit1App.setParameterValue("refclassName", refclassName);

		// perform the transformation
		boolean resultM = mm_unit1App.execute();

		if (resultM) {
			System.out.println("\"MM_CreateRefClass\" applied.");
		} else {
			System.out.println("\"MM_CreateRefClass\" not applied.");
			return null;
		}// if

		// persist the changes of the meta-model
		try {
			petri.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}// try catch

		return mm_unit1App;
	}// evolveMetaModel_ReplaceRefWithRefclass

	/**
	 * This method migrates our instance model in correspondence to the creation
	 * of a reference class in the meta-model. It depends on matched types of
	 * the meta-model transformation.<br>
	 * After performing all changes to the instance model, it is saved.
	 * 
	 * @param petri
	 *            Meta-Model root object.
	 * @param srcType
	 *            Type (EClass) being the source of the reference to be
	 *            replaced.
	 * @param trgType
	 *            Type (EClass) being the target of the reference to be
	 *            replaced.
	 * @param refType
	 *            Type (EReference) of the reference to be deleted and replaced.
	 *            Its source is of type <code>srcType</code> and is target is of
	 *            type <code>trgType</code>.
	 * @param refclassType
	 *            Type (EClass) being the newly introduced reference class.
	 *            Instances of this type are going to represent old references
	 *            i.e. a new instance of this class is part of the replacement.
	 * @param refSrcTrg
	 *            Type (EReference) of the reference running from
	 *            <code>srcType</code> to <code>refclassType</code>. A new
	 *            instance of this reference is part of the replacement.
	 * @param refTrgSrc
	 *            Type (EReference) of the reference running from
	 *            <code>trgType</code> to <code>refclassType</code>. A new
	 *            instance of this reference is part of the replacement
	 * @return the unit application instance migrating the instance model. If
	 *         successfully applied it contains further informations as port
	 *         mappings and so on.
	 */
	@SuppressWarnings("unused")
	private UnitApplication migrateInstanceModel_ReplaceRefWithRefclass(
			EPackage petri, EClass srcType, EClass trgType, EReference refType,
			EClass refclassType, EReference refSrcTrg, EReference refTrgSrc) {
		/*
		 * With these classes (or types) at hand we could automatically generate
		 * a rule for a co-evolution of instance models. As such a framework is
		 * not available yet, we create that rule manually.
		 */
		HenshinFactory hFac = HenshinFactoryImpl.eINSTANCE;
		TransformationSystem tsI = hFac.createTransformationSystem();
		tsI.getImports().add(petri);

		Rule i_rule1 = hFac.createRule();
		tsI.getRules().add(i_rule1);
		i_rule1.setActivated(true);
		i_rule1.setName("Migrate Instance Model");
		Graph lhs = i_rule1.getLhs(); // left-hand side of the rule
		Graph rhs = i_rule1.getRhs(); // right-hand side of the rule

		// Create all nodes and edges in the LHS.
		Node lhs_n_sC = hFac.createNode(lhs, srcType);
		Node lhs_n_tC = hFac.createNode(lhs, trgType);
		Edge lhs_e = hFac.createEdge(lhs_n_sC, lhs_n_tC, refType);
		// Create all nodes and edges in the RHS.
		Node rhs_n_sC = hFac.createNode(rhs, srcType);
		Node rhs_n_tC = hFac.createNode(rhs, trgType);
		Node rhs_n_rC = hFac.createNode(rhs, refclassType);
		Edge rhs_e_st = hFac.createEdge(rhs_n_sC, rhs_n_rC, refSrcTrg);
		Edge rhs_e_ts = hFac.createEdge(rhs_n_tC, rhs_n_rC, refTrgSrc);
		// Create mappings to specify creation, deletion and preserve
		Mapping m1 = hFac.createMapping(lhs_n_sC, rhs_n_sC);
		i_rule1.getMappings().add(m1);
		Mapping m2 = hFac.createMapping(lhs_n_tC, rhs_n_tC);
		i_rule1.getMappings().add(m2);
		/*
		 * Create now an independent unit to allow continuous application of
		 * that rule. Alternatively, we could perform a single application of
		 * that rule in a <code>while</code> block until transformation returns
		 * <code>false</code>.
		 */
		IndependentUnit i_unit = hFac.createIndependentUnit();
		tsI.getTransformationUnits().add(i_unit);
		i_unit.setActivated(true);
		i_unit.setName("Migration I-Unit");
		i_unit.getSubUnits().add(i_rule1);

		// Remark: Only for debugging purposes! You may comment this out.
		savePetriInstanceTrafoSystem(tsI);

		// Load the instance model to be changed.
		EObject net = loadPetriInstanceModel();

		// Instantiate Henshin interpreter objects
		EmfGraph graphI = new EmfGraph();
		graphI.addRoot(net);
		EmfEngine engineI = new EmfEngine(graphI);

		UnitApplication i_unit1App = new UnitApplication(engineI, i_unit);

		boolean resultI = i_unit1App.execute();

		if (resultI) {
			System.out.println("Co-Evolution of instance model successful.");
		} else {
			System.out
					.println("Co-Evolution of instance model not successful.");
			return null;
		}// if

		// persist the changes of the instance model
		try {
			net.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}// try catch

		return i_unit1App;
	}// migrateInstanceModel_ReplaceRefWithRefclass

	/**
	 * Deletes the given reference from the petri ecore model and persists the
	 * model.
	 * 
	 * @param petri
	 *            Meta-Model root object.
	 * @param refType
	 *            Type (EReference) of the reference to be deleted. Its opposite
	 *            is deduced and deleted as well.
	 * @return
	 */
	public UnitApplication evolveMetaModel_DeleteOldReference(EPackage petri,
			EReference refType) {

		// load Henshin transformation system
		TransformationSystem tsM = loadPetriTrafoSystemM();

		// instantiate Henshin interpreter objects
		EmfGraph graphM = new EmfGraph();
		graphM.addRoot(petri);
		EmfEngine engineM = new EmfEngine(graphM);

		// select rule
		Rule mm_rule2 = tsM.findRuleByName("MM_DeleteOldRefs");

		/*
		 * UnitApplication encapsulates a transformation basing on an engine and
		 * a transformation unit. It allows furthermore to set input port
		 * parameter values or objects (see below). Setting input port objects
		 * provide a partial match to the rule into the graph.
		 */
		UnitApplication mm_unit2App = new UnitApplication(engineM, mm_rule2);
		mm_unit2App.setParameterValue("objDelRef", refType);

		// perform the transformation
		boolean resultM = mm_unit2App.execute();

		if (resultM) {
			System.out.println("\"MM_DeleteOldRefs\" applied.");
		} else {
			System.out.println("\"MM_DeleteOldRefs\" not applied.");
			return null;
		}// if

		// persist the changes of the meta-model
		try {
			petri.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}// try catch

		return mm_unit2App;
	}// evolveMetaModel_DeleteOldReference

	/**
	 * For debugging purposes only: Saves the transformation system to a file
	 * (see {@link #HENSHIN_PETRI_INSTANCE}) to see how it looks like.
	 * 
	 * @param tsI
	 */
	private void savePetriInstanceTrafoSystem(TransformationSystem tsI) {
		// 
		URI iURI = URI.createFileURI(new File(HENSHIN_PETRI_INSTANCE)
				.getAbsolutePath());
		Resource res = resourceSet.createResource(iURI, "henshin");
		res.getContents().add(tsI);
		try {
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}// try catch
	}// savePetriInstanceTrafoSystem

	/**
	 * Loads the meta-model related Henshin from {@link #MODEL_PETRI_META} and
	 * return the root object.
	 * 
	 * @return a transformation system for our petri net ecore model.
	 */
	private TransformationSystem loadPetriTrafoSystemM() {
		HenshinPackageImpl.init();
		TransformationSystem tsM = (TransformationSystem) ModelHelper
				.loadFile(HENSHIN_PETRI_META);
		return tsM;
	}// loadPetriTrafoSystemM

	/**
	 * Loads the meta-model {@link #MODEL_PETRI_META} and return the root
	 * object.
	 * 
	 * @return the meta-model root object.
	 */
	private EPackage loadPetriEcoreModel() {
		URI modelUri = URI.createFileURI(new File(MODEL_PETRI_META)
				.getAbsolutePath());
		Resource resourceModel = resourceSet.getResource(modelUri, true);
		return (EPackage) resourceModel.getContents().get(0);
	}// loadPetriEcoreModel

	/**
	 * Loads the instance model with path {@link #MODEL_PETRI_INSTANCE} typed
	 * over the petri meta-model ({@link #MODEL_PETRI_META}). If the instance
	 * model is already loaded, it is unload and re-loaded again.
	 * 
	 * @return
	 */
	private EObject loadPetriInstanceModel() {

		/*
		 * Note the usage of ".getAbsolutePath()" below! This is necessary for
		 * EMF to resolve relative paths in instance models. In our case, the
		 * typing i.e. the meta-model petri.ecore is given relative to this
		 * instance models location.
		 */
		URI instanceUri = URI.createFileURI(new File(MODEL_PETRI_INSTANCE)
				.getAbsolutePath());
		Resource resourceInstance = resourceSet.getResource(instanceUri, true);

		/*
		 * The instance model is only aware of meta-model changes if it is
		 * reloaded afterwards. Accordingly, if already loaded, the instance
		 * model is unloaded and re-loaded again. Note, unloading only replaces
		 * each object with its proxy. The resource remains in the resource set
		 * and therefore can be easily reloaded.
		 */
		if (resourceInstance.isLoaded()) {
			resourceInstance.unload();
			try {
				resourceInstance.load(null);
			} catch (IOException e) {
				e.printStackTrace();
			}// try catch
		}// if

		return resourceInstance.getContents().get(0);
	}// loadPetriInstanceModel

	/**
	 * Assigns to each file extensions of the Ecore meta-model (.ecore),
	 * instance models (.xmi) and Henshin rules (.henshin) an appropriate
	 * resource factory. This is necessary in order to load such files.
	 */
	private void initializeResourceFactories() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"xmi", new XMIResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"henshin", new HenshinResourceFactory());
	}// initializeResourceFactories

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Evolution1 test = new Evolution1();
		test.start();

	}

}
