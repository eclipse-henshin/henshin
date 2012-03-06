package org.eclipse.emf.henshin.migration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceFactory;
import org.eclipse.emf.henshin.testframework.Tools;


/**
 * 
 * @author Felix Rieger
 *
 */

public class Transformation {

	private Map<EObject, EObject> newElements = new HashMap<EObject, EObject>();		// map from old element to new element
	private ArrayList<EObject> createdElements = new ArrayList<EObject>();	// list of newly created elements
	
	private ArrayList<NestedCondition> nacList = new ArrayList<NestedCondition>();	// list of NACs
	private ArrayList<String> errorList = new ArrayList<String>();	// list of errors for future gui
	
	private Map<Rule, Rule> amalgamationUnitRuleCopies = new HashMap<Rule, Rule>();  // Map from (Rules used in AmalgamationUnits) to (Copies of Rules)
	private ArrayList<RuleReplacementHelper> ruleReplacements = new ArrayList<RuleReplacementHelper>();	// List containing Rules and their indices in TransformationUnits
	
	private class AmalgamationUnitHelper {
		/**
		 * Data structure for storing information related to amalgamationUnits
		 */
		EObject kernelRule;
		ArrayList<EObject> multiRules = new ArrayList<EObject>();
		ArrayList<EObject> lhsMappings = new ArrayList<EObject>();
		ArrayList<EObject> rhsMappings = new ArrayList<EObject>();
		String amalgamationUnitName = "";
		
		@Override
		public String toString() {
			String mrString = "";
			for (EObject eo : multiRules) {
				mrString += eo + "\t";
			}
			return "kernel: " + kernelRule + "  ;;; multi: " + mrString;
		}
	}
	
	private class CountedUnitHelper {
		/**
		 * Data structure for storing information related to countedUnits
		 */
		EObject countedUnit;	// counted unit (old model)
		EObject subUnit;		// sub unit	(old model)
		int count;				// count
		public CountedUnitHelper(EObject countedUnit, EObject subUnit, int count) {
			this.countedUnit = countedUnit;
			this.subUnit = subUnit;
			this.count = count;
		}
	}
	
	private class RuleReplacementHelper {
		/**
		 * Data structure for storing information related to Rules
		 */
		EObject rule;
		EObject tu;
		
		ArrayList<Integer> ruleInTuIndices = new ArrayList<Integer>();
		
		public RuleReplacementHelper(EObject rule, EObject tu, int ruleInTuIndex) {
			this.rule = rule;
			this.tu = tu;
			ruleInTuIndices.add(ruleInTuIndex);
		}
		
		@Override
		public String toString() {
			return "@idx=" + getIndicesString() + "    " + rule + "      in " + tu;
		}
		
		private String getIndicesString() {
			String s = "";
			for (Integer i : ruleInTuIndices) {
				s = s + i + " ";
			}
			return s;
		}
	}
	
	private ArrayList<AmalgamationUnitHelper> amuList = new ArrayList<AmalgamationUnitHelper>();	// List of amalgamation units
	private ArrayList<CountedUnitHelper> cuList = new ArrayList<CountedUnitHelper>();	// List of counted units
	
	private TransformationSystem newRoot;	// root object of the new Transformation System
			
	
	public void migrate(java.net.URI henshinFileUri, boolean optimizeNcs, IProgressMonitor pm) throws ClassNotFoundException, IOException, FileNotFoundException {
		migrate(henshinFileUri, null, optimizeNcs, pm);
	}
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws FileNotFoundException
	 */
	public void migrate(java.net.URI fileUri, java.net.URI henshinDiagramUri, boolean optimizeNcs, IProgressMonitor pm) throws ClassNotFoundException, IOException, FileNotFoundException {		
        ResourceSet resourceSet = new ResourceSetImpl();
        
		pm.beginTask("migrating to new model", 100);
		URI eFileUri = org.eclipse.emf.common.util.URI.createURI(fileUri.toString());
		URI diagramUri = henshinDiagramUri!=null?org.eclipse.emf.common.util.URI.createURI(henshinDiagramUri.toString()):null;
		
		System.out.println("diagram: " + diagramUri);
		
		URI backupUri = eFileUri.appendFileExtension("bak");
		URI newUri = eFileUri;

		HenshinPackageImpl.init();
		HenshinPackage.eINSTANCE.getClass();/*		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new HenshinResourceFactory());//new EcoreResourceFactoryImpl());		Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap().put("org.eclipse.emf.ecore", new HenshinResourceFactory());
		Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap().put("org.eclipse.emf.henshin", new HenshinResourceFactory());
*/
		URI henshinUri = URI.createPlatformPluginURI("org.eclipse.emf.henshin.migration/model/henshin-080.ecore", false);
		
		// copied directly from ModelHelper
		//ModelHelper.registerEPackageByEcoreFile(CommonPlugin.resolve(henshinUri), resourceSet);
			
		EPackage result = null;
		Resource packageResource = resourceSet.createResource(CommonPlugin.resolve(henshinUri));
		if (packageResource != null) {
			try {
				packageResource.load(null);
			} catch (IOException e) {
				e.printStackTrace();
				result = null;
			}
			if ((packageResource.getContents() != null)
					&& (packageResource.getContents().size() > 0)) {
				EObject tmpR = packageResource.getContents().get(0);
				if (tmpR != null && tmpR instanceof EPackage) {
					result = (EPackage) tmpR;
					resourceSet.getPackageRegistry().put(result.getNsURI(), result);
				}
			}
		}
		if (result != null) {
			EPackage.Registry.INSTANCE.put(result.getNsURI(), result);
		}
				
		// ---
		
		
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("henshin", new HenshinResourceFactory());
		

		Resource oldHenshinResource = resourceSet.getResource(URI.createFileURI(eFileUri.toFileString()), true);

		EObject graphRoot = oldHenshinResource.getContents().get(0);

		// load diagram
		if (diagramUri != null && diagramUri.isFile()) {
			resourceSet.getResource(diagramUri, true);
		}
			
		
		// --- Actual conversion starts here ---

		pm.subTask("Analyzing transformation system");
		addRootObjectToMap(graphRoot);			// fill map by creating new objects
		pm.worked(20);
		collectRuleReferences();				// collect references to rules so there will be no ambiguity when AmalgamationUnits are replaced by their kernel rules
		pm.worked(10);
		System.out.println("\n\n\n");
		for (RuleReplacementHelper rrh : ruleReplacements) {
			System.out.println(rrh);
		}
		
		pm.subTask("updating references");
		updateAmalgamationUnitReferences();		// update references to AmalgamationUnits to point to the kernel rule
		pm.worked(10);
		processCountedUnits();					// replace counted units not already replaced by loop units by sequential units
		pm.worked(10);
		
		System.out.println("\n ****** \n");
		newRoot = (TransformationSystem) newElements.get(graphRoot);
		
		Resource newHenshinResource = new HenshinResourceFactory().createResource(null);
		newHenshinResource.getContents().add(newRoot);
		resourceSet.getResources().add(newHenshinResource);
		
		updateReferences();						// update references between objects
		pm.worked(20);
		buildAmalgamationUnits();				// create copies of the rules used in AmalgamationUnits and modify existing kernel rules to contain the multi rules
		pm.worked(10);
		wrapNACs();								// wrap NACs
		pm.worked(2);
		if (optimizeNcs) {
			cleanUpNotFormulas();				// clean up not formulas (consolidate multiple negations)
			pm.worked(2);
		}
		moveRulesToRulesReference();			// move Rules (i.e. former amalgamation units) from "TransformationUnits" to "Rules" reference
		pm.worked(2);
		
		for (TransformationUnit tu : newRoot.getTransformationUnits()) {
			System.out.println("updating amalgamation unit references for " + tu.getName());
			updateAmalgamationUnitReferences(tu);
		}
		pm.worked(10);
		
		// migration of the henshin file is done now
		// now, migrate the diagram
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		newElements.put(graphRoot, newRoot);	// put the old and the new transformationSystem in the map

		System.out.println("\n\n####################");
		System.out.println(newRoot);
		Tools.printCollection(newRoot.getRules());
		
		pm.subTask("creating backup");
		
		// rename old file:
		File oldHenshinFile = new File(fileUri);
		File oldHenshinFileBackup = new File(backupUri.toFileString());
		if (!oldHenshinFile.renameTo(oldHenshinFileBackup)) { // renaming failed
			throw new FileNotFoundException();
		} 
		pm.subTask("write new file");
		
		// write new file		
		newHenshinResource.setURI(newUri);
		newHenshinResource.save(null);
		
		try {
			boolean diagramTransformationSuccess = DiagramTransformation.transformDiagram(newElements, amalgamationUnitRuleCopies, resourceSet, diagramUri);
			System.out.println("Diagram transformation " + (diagramTransformationSuccess?"successful":"failed"));
		} catch (Exception e) {
			System.err.println("Problems during diagram migration:");
			e.printStackTrace();
		}
		
		
		
		
		System.out.println("naclist:");
		Tools.printCollection(nacList);
		
		if (nacList.size() != 0) {
			addError("Some NACs couldn't be converted.");
		}
		
		
		for (Entry<EObject, EObject> entry : newElements.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}
		
		
		System.out.println("\n\n\n");
		for (AmalgamationUnitHelper auh : amuList) {
			System.out.println(auh);
		}
		
		System.out.println("\n\n");
		for (RuleReplacementHelper rrh : ruleReplacements) {
			System.out.println(rrh);
		}
		
	}
		
	
	

	
	/**
	 * Save references to Rules in transformationUnits so that later, 
	 * Kernel Rules of AmalgamationUnits used outside an AmalgamationUnit can be correctly
	 * converted and will not be replaced by the Rule formerly known as AmalgamationUnit 
	 */
	private void collectRuleReferences() {
		for (Entry<EObject, EObject> e : newElements.entrySet()) {
			if ((e.getValue() instanceof TransformationUnit) && !(e.getValue() instanceof Rule)) {	// only transformationUnits are interesting
				System.out.println("NOW working on " + e.getKey() + " -> " + e.getValue()); 
				if (e.getValue() instanceof LoopUnit) {		
					Object subUnit = e.getKey().eGet(getEReferenceByName("subUnit", e.getKey().eClass()));
					if (newElements.get(subUnit) instanceof Rule) { // if the subunit is a Rule, add it to the list
						addRuleReplacement(newElements.get(subUnit), e.getValue(), 0);
					}
				} else if (e.getValue() instanceof ConditionalUnit) {
					Object subIf = e.getKey().eGet(getEReferenceByName("if", e.getKey().eClass()));
					Object subThen = e.getKey().eGet(getEReferenceByName("then", e.getKey().eClass()));
					Object subElse = e.getKey().eGet(getEReferenceByName("else", e.getKey().eClass()));
					if (newElements.get(subIf) instanceof Rule) { // if the subunit is a Rule, add it to the list
						addRuleReplacement(newElements.get(subIf), e.getValue(), 0);
					}
					
					if (newElements.get(subThen) instanceof Rule) { // if the subunit is a Rule, add it to the list
						addRuleReplacement(newElements.get(subThen), e.getValue(), 0);
					}
					
					if (newElements.get(subElse) instanceof Rule) { // if the subunit is a Rule, add it to the list
						addRuleReplacement(newElements.get(subElse), e.getValue(), 0);
					}
				} else if ("CountedUnit".equals(e.getKey().eClass().getName())) {
					// special case for counted units
					Object subUnit = e.getKey().eGet(getEReferenceByName("subUnit", e.getKey().eClass()));
					if (newElements.get(subUnit) instanceof Rule) { // if the subunit is a Rule, add it to the list
						addRuleReplacement(newElements.get(subUnit), e.getValue(), 0);
					}
				} else { // any other transformation unit
					EList<TransformationUnit> subUnits = (EList<TransformationUnit>) e.getKey().eGet(getEReferenceByName("subUnits", e.getKey().eClass()));
					for (int i = 0; i < subUnits.size(); i++) {
						if (newElements.get(subUnits.get(i)) instanceof Rule) {
							addRuleReplacement(newElements.get(subUnits.get(i)), e.getValue(), i);
						}
					}
				}
			}
		}
	}
	
	/**
	 * When an amalgamationUnit is converted, it becomes a Rule, but will still be contained in the TransformationSystem's
	 * TransformationUnits. For improved model consistency, move these Rules to the "Rules" reference.
	 */
	private void moveRulesToRulesReference() {
		ArrayList<TransformationUnit> ruleList = new ArrayList<TransformationUnit>();
		for (TransformationUnit tu : newRoot.getTransformationUnits()) {
			if (tu instanceof Rule) {
				ruleList.add(tu);
			}
		}
		
		for (TransformationUnit tu : ruleList) {
			newRoot.getTransformationUnits().remove(tu);
			newRoot.getRules().add((Rule) tu);
		}
		
	}
	
	/**
	 * Update references to Amalgamation Units
	 * @param tu
	 */
	private void updateAmalgamationUnitReferences(TransformationUnit tu) {
		updateAmalgamationUnitReferences(new ArrayList<TransformationUnit>(), tu);
	}
	
	/**
	 * Update references to Amalgamation Units recursively.
	 * @param stack	Contains the TransformationUnits which were already visited
	 * @param tu	TransformationUnit to update
	 */
	private void updateAmalgamationUnitReferences(ArrayList<TransformationUnit> stack, TransformationUnit tu) {
		if (stack.contains(tu)) { // unit already is on stack, so we have a cycle
			return;
		}
		stack.add(tu);	// push the current transformation unit on the stack
		if (tu instanceof LoopUnit) {
			TransformationUnit su = ((LoopUnit) tu).getSubUnit();
			
			if (su instanceof TransformationUnit && !(su instanceof Rule)) { // check for nested transformation units
				updateAmalgamationUnitReferences(stack, su);	// recursively check nested transformation units
			} else if (amalgamationUnitRuleCopies.get(su) != null) { // is the rule known to have been an amalgamation unit?
				if (searchForRuleReplacement(tu, (Rule) su) == null) { // search for the rule+unit combination in the list of rule replacements. If not found, the Rule was an amalgamationUnit, so replace it
					((LoopUnit) tu).setSubUnit(amalgamationUnitRuleCopies.get(su));
				}
			}
		} else if (tu instanceof Rule) {
			// Nothing to do here
		} else if (tu instanceof ConditionalUnit) {
			// get the sub-units
			ConditionalUnit cu = (ConditionalUnit) tu;
			TransformationUnit suIf = cu.getIf();
			TransformationUnit suThen = cu.getThen();
			TransformationUnit suElse = cu.getElse();
			
			if (suIf instanceof TransformationUnit && !(suIf instanceof Rule)) {
				updateAmalgamationUnitReferences (stack, suIf);
			} else if ((amalgamationUnitRuleCopies.get(suIf) != null) && (searchForRuleReplacement(tu, (Rule) suIf) == null)) {
				cu.setIf(amalgamationUnitRuleCopies.get(suIf));
			}
			
			if (suThen instanceof TransformationUnit && !(suThen instanceof Rule)) {
				updateAmalgamationUnitReferences (stack, suThen);
			} else if ((amalgamationUnitRuleCopies.get(suThen) != null) && (searchForRuleReplacement(tu, (Rule) suIf) == null)) {
				cu.setThen(amalgamationUnitRuleCopies.get(suThen));
			}
			
			if (suElse instanceof TransformationUnit && !(suElse instanceof Rule)) {
				updateAmalgamationUnitReferences (stack, suElse);
			} else if ((amalgamationUnitRuleCopies.get(suElse) != null) && (searchForRuleReplacement(tu, (Rule) suIf) == null)) {
				cu.setElse(amalgamationUnitRuleCopies.get(suElse));
			}
			
		} else {
			// get the sub-units.
			// this list needs to be modified, so the getSubUnits(boolean deep) method can not be used here
			EList<TransformationUnit> subUnits = (EList<TransformationUnit>) tu.eGet(getEReferenceByName("subUnits", tu.eClass()));
			for (int i = 0; i < subUnits.size(); i++) {
				TransformationUnit su = subUnits.get(i);
				System.out.println("I=" + i + " SU=" + su);
				if (su instanceof TransformationUnit && !(su instanceof Rule)) { // check for nested TransformationUnits
					updateAmalgamationUnitReferences(stack, su);
				} else if (amalgamationUnitRuleCopies.get(su) != null) {
					RuleReplacementHelper rrh = searchForRuleReplacement(tu, (Rule) su);
					System.out.println("idx::" + i + " Rule replacement ::: " + rrh);
					if ((rrh == null) || (!(rrh.ruleInTuIndices.contains(i)))) { // replace
						System.out.println("replacing!");
						subUnits.set(i, amalgamationUnitRuleCopies.get(su));
					} 
				}
			}
		}
	}
	
	/**
	 * Build new Rules in place of the old AmalgamationUnits
	 * Note: Kernel and Multi Rules will be copied, although this will not be neccessary most of the time.
	 */
	private void buildAmalgamationUnits() {
		// amalgamationUnitRuleCopies:    unmodifiedOriginalRule -> copiedRuleInAmalgamatioUnit 
		
		for (AmalgamationUnitHelper amu : amuList) {
			// Copy the kernel rule
			// New name will be AMALGAMATIONUNITNAME_KRL_KERNELRULENAME
			Rule krlRuleTmp = (Rule) newElements.get(amu.kernelRule);
			Rule krlRule = (Rule) EcoreUtil.copy(krlRuleTmp);		// <- this is the new AmalgamationUnit
			krlRule.getMultiRules().clear();
			amalgamationUnitRuleCopies.put(krlRuleTmp, krlRule);
			krlRule.setName(amu.amalgamationUnitName + "_krl_" + krlRule.getName());
			System.out.println("\n\n\n\nKERNEL RULE: " + krlRule);
			newRoot.getRules().add(krlRule);
			
			for (EObject mr : amu.multiRules) {
				// Copy the multi rules
				// New name will be AMALGAMATIONUNITNAME_mul_MULTIRULENAME
				Rule multiRuleTmp = (Rule) newElements.get(mr);
				Rule multiRule = (Rule) EcoreUtil.copy(multiRuleTmp);
				amalgamationUnitRuleCopies.put(multiRuleTmp, multiRule);
				multiRule.setName(amu.amalgamationUnitName + "_mul_" + multiRule.getName());
				System.out.println("multi rule: " + multiRule);
				System.out.println("######################## " + krlRule.getMultiRules().remove(multiRuleTmp));
				newRoot.getRules().add(multiRuleTmp);
				krlRule.getMultiRules().add(multiRule);
			}
			
			
			System.out.println("lhs mappings:");
			// Convert the mappings
			/* The idea:
			 * originalKernelRule                                       kernelRuleCopy
			 *       |- LHS                                                |- LHS (copy)
			 *       |  '- Node   <----.                                   |  '- Node (copy)        <--.
			 *       '- RHS             |                                  |- RHS (copy)               |
			 *          '- ...          |                                  '- multiRule (copy)         | mapping[1]
			 *                       mapping[1]     ==transform.==>              |- LHS (copy)         |
			 *                          |                                        |  '- Node (copy)   --'
			 * 	originalMultiRule       |                                        |- RHS
			 *       |- LHS             /                                        '- mappings  
			 *       |  '- Node     ---'                                             '- mapping[1]
			 *       '- RHS
			 *          '- ...  
			 *          
			 *  lhsMappings
			 *       '- mapping[1]
			 */
			
			for (ArrayList<EObject> al : new ArrayList[]{amu.lhsMappings, amu.rhsMappings}) { 
				System.out.println("***!");
				for (EObject mp : al) {
					Mapping newMp = (Mapping) newElements.get(mp);
					System.out.println("\tnew mapping:" + newMp + " : " + newMp.getOrigin().getGraph().getContainerRule().getName() + " -> " + newMp.getImage().getGraph().getContainerRule().getName());
					
					// get the origin node and the rule containing the origin node
					Node originNodeTmp = newMp.getOrigin();
					Rule originNodeContainerRuleTmp = originNodeTmp.getGraph().getContainerRule();
					
					// get the copied rule containing the new origin node
					Rule originNodeContainerRule = amalgamationUnitRuleCopies.get(originNodeContainerRuleTmp);
					System.out.println("\tor:" + originNodeContainerRule);
	
					// find out whether the node was in the LHS or RHS and obtain the new Node
					Node newOriginNode;
					if (originNodeContainerRuleTmp.getLhs().equals(originNodeTmp.getGraph())) { // Node in LHS
						int nodeIndex = originNodeContainerRuleTmp.getLhs().getNodes().indexOf(originNodeTmp);
						newOriginNode = originNodeContainerRule.getLhs().getNodes().get(nodeIndex);
						System.out.println("node in LHS @ index " + nodeIndex);
					} else { // Node in RHS
						int nodeIndex = originNodeContainerRuleTmp.getRhs().getNodes().indexOf(originNodeTmp);
						newOriginNode = originNodeContainerRule.getRhs().getNodes().get(nodeIndex);
						System.out.println("node in RHS @ index " + nodeIndex);					
					}
					System.out.println(originNodeTmp + " ==> " + newOriginNode);
					
					newMp.setOrigin(newOriginNode);
					
					Node imageNodeTmp = newMp.getImage();
					Rule imageNodeContainerRuleTmp = imageNodeTmp.getGraph().getContainerRule();
					
					Rule imageNodeContainerRule = amalgamationUnitRuleCopies.get(imageNodeContainerRuleTmp);
					
					Node newImageNode;
					if (imageNodeContainerRuleTmp.getLhs().equals(imageNodeTmp.getGraph())) { // Node in LHS
						int nodeIndex = imageNodeContainerRuleTmp.getLhs().getNodes().indexOf(imageNodeTmp);
						newImageNode = imageNodeContainerRule.getLhs().getNodes().get(nodeIndex);
						System.out.println("node in LHS @ index " + nodeIndex);
					} else {	// Node in RHS
						int nodeIndex = imageNodeContainerRuleTmp.getRhs().getNodes().indexOf(imageNodeTmp);
						newImageNode = imageNodeContainerRule.getRhs().getNodes().get(nodeIndex);
						System.out.println("node in RHS @ index " + nodeIndex);
					}
					
					
					System.out.println("\tim:" + imageNodeContainerRule);
	
					newMp.setImage(newImageNode);
					imageNodeContainerRule.getMultiMappings().add(newMp);
					System.out.println("mapping container:" + newMp.eContainer());
				}
			}
		}
	}


	/**
	 * Update references of the new objects. All references of 2011 objects should point to other 2011 objects, so
	 * traverse the newElements map and update all references between objects.
	 */
	private void updateReferences() {
		for (Entry<EObject, EObject> e: newElements.entrySet()) {
			System.out.println("\nREFERENCES FOR " + e.getKey() + "\t\t\t (new object: " + e.getValue() + ")");

			for (EReference er : ((EObject) e.getKey()).eClass().getEAllReferences()) {	// XXX: debug print, remove
				System.out.println("\t" + er);
			}
			for (EReference er : ((EObject) e.getValue()).eClass().getEAllReferences()) {
				System.out.println("#\t" + er);
				EReference oldErefType = getEReferenceByName(er.getName(), ((EObject) e.getKey()).eClass());
				if (er.isChangeable()) {
					if (oldErefType != null) {
						if (((EObject) e.getKey()).eGet(oldErefType) instanceof EList) {
							EList<EObject> oldReferencedObjects = (EList<EObject>) ((EObject) e.getKey()).eGet(oldErefType);
							System.out.println("lst:: " + oldReferencedObjects);
							if (oldReferencedObjects != null) { // reference is a list
								EList<EObject> newReferencedObjects = ((EList<EObject>) ((EObject) e.getValue()).eGet(er));
								
								if (er.getName().equals("imports")) { // special case for imports
									System.out.println("special case for imports");
									((EObject) e.getValue()).eSet(er, oldReferencedObjects);
								} else if (er.getName().equals("parameterMappings")) { // special case for parameter mappings
									if (e.getKey().eClass().getName().equals("AmalgamationUnit")) {
										// do nothing
									} else {
										convertObjectList(oldReferencedObjects, newReferencedObjects);
									}
								} else if (er.getName().equals("parameters")) { // special case for parameters
									if (e.getKey().eClass().getName().equals("AmalgamationUnit")) {
										// do nothing; use the kernel rule's parameters
									} else {
										convertObjectList(oldReferencedObjects, newReferencedObjects);
									}
								} else {
									convertObjectList(oldReferencedObjects, newReferencedObjects);	// set reference
									System.out.println("NEW:: " + newReferencedObjects);
									System.out.println("new:: " + ((EObject) e.getValue()).eGet(er) + " // " + newReferencedObjects);
									System.out.println("non-null");
								}
							}
						} else {	// reference is not a list, but (maybe?) a single object
							if (er.getName().equals("type")) { // special case for types
								System.out.println("special case for types");
								EObject objectType = (EObject) ((EObject) e.getKey()).eGet(oldErefType);
								((EObject) e.getValue()).eSet(er, objectType);
								
							} else if (((EObject) e.getKey()).eGet(oldErefType) instanceof EObject) {
								
								
								
								if (e.getValue() instanceof LoopUnit) {
									System.err.println("*********");
									System.out.println("**********" + e.getValue());
									System.out.println("**********" + e.getKey());
									System.out.flush();
								}
								
								EObject oldReferencedObject = (EObject) ((EObject) e.getKey()).eGet(oldErefType);
								System.out.println("obj:: " + oldReferencedObject);
								if (oldReferencedObject != null) {
									EObject newReferencedObject = newElements.get(oldReferencedObject);
									((EObject) e.getValue()).eSet(er, newReferencedObject);
									System.out.println("~>" + newReferencedObject);
									System.out.println("non-null");
								}
							} else {
								System.out.println("no list, no single object ... ??? ->" + ((EObject) e.getKey()).eGet(oldErefType));
							}
						}
					} else {
						System.out.println(er + " has no old reference equivalent");
					}
				} else {
					System.out.println("reference not changeable");
				}
			}
		}
	}
	
	/**
	 * Convert a List of old (2010) Objects to a List of new (2011) Objects. The order will be preserved as best as possible.
	 * @param oldList	List of '2010' objects
	 * @param newList	New List of '2011' objects
	 */
	private void convertObjectList(EList<EObject> oldList, EList<EObject> newList) {
		System.out.print("{");
	
		for (int i = 0; i < oldList.size(); i++) {
			System.out.print(newElements.get(oldList.get(i)) + " ?->");						
			System.out.print(newList.add(newElements.get(oldList.get(i))) + ", ");
		}
		
		// sort the new list so the new items are in the same place as the old items
		for (int i = oldList.size() - 1; i > 0; i--) {
			newList.move(i, (EObject) newElements.get(oldList.get(i)));
		}
		
		System.out.println("} => " + newList);
		
	}
	
	/**
	 * Update references to amalgamation units to point to their kernel rules
	 */
	private void updateAmalgamationUnitReferences() {
		for (Entry<EObject, EObject> e : newElements.entrySet()) {
			if (newElements.containsKey(e.getValue())) {
				EObject ruleReplacingAmalgamationUnit = newElements.get(e.getValue());
				newElements.put(e.getKey(), ruleReplacingAmalgamationUnit);
			}
		}
	}
	
	
	/**
	 * When replacing counted units by sequential units, create the sequential units with COUNT times the countedUnit's subUnit
	 */
	private void processCountedUnits() {
		for (CountedUnitHelper cuh : cuList) {
			SequentialUnit su = (SequentialUnit) newElements.get(cuh.countedUnit);
			
			EObject subUnit = newElements.get(cuh.subUnit);
			
			for (int i = 0; i < cuh.count; i++) {
				su.getSubUnits().add((TransformationUnit) subUnit);
			}
		}
	}
	
	
	/**
	 * Fill the old Element->new Element map (newElements) recursively.
	 * @param rootObject
	 */
	private void fillMap(EObject rootObject) {
		EPackage henshinNew = HenshinPackageImpl.eINSTANCE;
		
		for (EObject eo : rootObject.eContents()) {		
			boolean detectedNC = false;
			
			String type = eo.eClass().getName();
			System.out.println(type);
		
			if ("AmalgamationUnit".equals(type)) {
				// Handle AmalgamationUnit
				EClass ec = (EClass) henshinNew.getEClassifier(type); 

		
				EObject amalgamationUnitKernelRule = (EObject) eo.eGet(getEReferenceByName("kernelRule", eo.eClass()));
				
				AmalgamationUnitHelper amu = new AmalgamationUnitHelper();
				amu.kernelRule = amalgamationUnitKernelRule;
				amu.amalgamationUnitName = (String) eo.eGet(getEAttributeForName("name", eo.eClass()));
				
				EList<EObject> amalgamationUnitMultiRules = (EList<EObject>) eo.eGet(getEReferenceByName("multiRules", eo.eClass()));
				for (Iterator iter = amalgamationUnitMultiRules.iterator(); iter.hasNext(); ) {
					EObject multiRule = (EObject) iter.next();
					amu.multiRules.add(multiRule);
				}
				
				EList<EObject> amalgamationUnitLhsMappings = (EList<EObject>) eo.eGet(getEReferenceByName("lhsMappings", eo.eClass()));
				for (Iterator iter = amalgamationUnitLhsMappings.iterator(); iter.hasNext(); ) {
					EObject lhsMapping = (EObject) iter.next();
					amu.lhsMappings.add(lhsMapping);
					
					Mapping newLhsMapping = HenshinFactory.eINSTANCE.createMapping();
					newElements.put(lhsMapping, newLhsMapping);
				}
				
				EList<EObject> amalgamationUnitRhsMappings = (EList<EObject>) eo.eGet(getEReferenceByName("rhsMappings", eo.eClass()));
				for (Iterator iter = amalgamationUnitRhsMappings.iterator(); iter.hasNext(); ) {
					EObject rhsMapping = (EObject) iter.next();
					amu.rhsMappings.add(rhsMapping);
					
					Mapping newRhsMapping = HenshinFactory.eINSTANCE.createMapping();
					newElements.put(rhsMapping, newRhsMapping);
				}
				
				amuList.add(amu);
				newElements.put(eo, amalgamationUnitKernelRule);
				
				continue;
			} else if ("NestedCondition".equals(type)) {
				// Handle NestedCondition
				detectedNC = true;
			} else if ("CountedUnit".equals(type)) {
				// handle counted unit: gets replaced by (a LoopUnit (containing a sequential unit (containing 'count' times the subunit)))

				
				// migrate parameters and parameterMappings
				EList<EObject> countedUnitParameters = (EList<EObject>) eo.eGet(getEReferenceByName("parameters", eo.eClass()));
				for (Iterator iter = countedUnitParameters.iterator(); iter.hasNext(); ) {
					EObject parameter = (EObject) iter.next();
					Parameter newParameter = HenshinFactory.eINSTANCE.createParameter();
					newElements.put(parameter, newParameter);
				}
				
				EList<EObject> countedUnitParameterMappings = (EList<EObject>) eo.eGet(getEReferenceByName("parameterMappings", eo.eClass()));
				for (Iterator iter = countedUnitParameterMappings.iterator(); iter.hasNext(); ) {
					EObject parameterMapping = (EObject) iter.next();
					ParameterMapping newParameterMapping = HenshinFactory.eINSTANCE.createParameterMapping();
					newElements.put(parameterMapping, newParameterMapping);
				}
				
				
				EAttribute attr = getEAttributeForName("count", eo.eClass());
				int count = (Integer) eo.eGet(attr);
				
				EObject cuReplacement;
				
				if (count < 1) { // counted unit will just be replaced by a LoopUnit
					LoopUnit countedUnitReplacement = HenshinFactoryImpl.eINSTANCE.createLoopUnit();
					countedUnitReplacement.setName((String) eo.eGet(getEAttributeForName("name", eo.eClass())));
					countedUnitReplacement.setDescription((String) eo.eGet(getEAttributeForName("description", eo.eClass())));
					if (count != 0) {
						countedUnitReplacement.setActivated((Boolean) eo.eGet(getEAttributeForName("activated", eo.eClass())));
					} else if (count == 0) {
						// count was 0, so we don't know what to do.
						// In order to preserve structure and not confuse the user, create a LoopUnit, but deactivate it
						countedUnitReplacement.setActivated(false);
					}
					cuReplacement = countedUnitReplacement;
				} else {
					// counted unit will be replaced by a sequential unit
					CountedUnitHelper cuHelper = new CountedUnitHelper(eo, (EObject) eo.eGet(getEReferenceByName("subUnit", eo.eClass())), (Integer) eo.eGet(getEAttributeForName("count", eo.eClass())));
					SequentialUnit countedUnitReplacementSeqUnit = HenshinFactory.eINSTANCE.createSequentialUnit();
					countedUnitReplacementSeqUnit.setName((String) eo.eGet(getEAttributeForName("name", eo.eClass())));
					countedUnitReplacementSeqUnit.setDescription((String) eo.eGet(getEAttributeForName("description", eo.eClass())));
					countedUnitReplacementSeqUnit.setActivated((Boolean) eo.eGet(getEAttributeForName("activated", eo.eClass())));
					cuList.add(cuHelper);
					cuReplacement = countedUnitReplacementSeqUnit;
				}
				
				newElements.put(eo, cuReplacement);
				continue;
			}
			
			

			
			EClass ec = (EClass) henshinNew.getEClassifier(type); 
			
			EObject newObject = HenshinFactoryImpl.eINSTANCE.create(ec);

			for (Iterator<EAttribute> iter = eo.eClass().getEAllAttributes().iterator(); iter.hasNext(); ) {
				EAttribute attr = (EAttribute) iter.next();
				Object val = eo.eGet(attr);
				
				EAttribute newAttr = getEAttributeForName(attr.getName(), ec);
				
				// handler for NACs, put them in a List (nacList) 
				if ((detectedNC) && (attr.getName().equals("negated"))) {
					if (((Boolean) val) == true) {
						nacList.add((NestedCondition) newObject); 
					}
					continue;
				}
				
				// copy attributes
				
				if (newAttr != null) {
					newObject.eSet(newAttr, val);
				} else {
					System.err.println("deleted attribute: " + attr.getName());
				}
				
				System.out.println("OLD-> " + attr.getName() + " : " + val);
			}
			
			
			// TODO: remove when done, this just prints the new values
			for (Iterator<EAttribute> iter = newObject.eClass().getEAllAttributes().iterator(); iter.hasNext(); ) {
				EAttribute attr = (EAttribute) iter.next();
				Object val = newObject.eGet(attr);
				
				System.out.println("NEW-> " + attr.getName() + " : " + val);
			}
			
			System.out.println(newObject);
			newElements.put(eo, newObject);
			System.out.println(" --- ");
			
			fillMap(eo);			
		}

	}
	
	/**
	 * Add the root object to the newElements map
	 * @param eo
	 */
	private void addRootObjectToMap(EObject eo) {
		EPackage henshinNew = HenshinPackageImpl.eINSTANCE;
		
			
		String type = eo.eClass().getName();
		System.out.println(type);
	
		if ("AmalgamationUnit".equals(type)) {
			return;
		}
		
	
		EClass ec = (EClass) henshinNew.getEClassifier(type); 
		
		EObject newObject = HenshinFactoryImpl.eINSTANCE.create(ec);

		for (Iterator<EAttribute> iter = eo.eClass().getEAllAttributes().iterator(); iter.hasNext(); ) {
			EAttribute attr = (EAttribute) iter.next();
			Object val = eo.eGet(attr);
			
			EAttribute newAttr = getEAttributeForName(attr.getName(), ec);
			if (newAttr != null) {
				newObject.eSet(newAttr, val);
			} else {
				System.err.println("deleted attribute: " + attr.getName());
			}
			
			System.out.println("OLD-> " + attr.getName() + " : " + val);
		}
		
		
		for (Iterator<EAttribute> iter = newObject.eClass().getEAllAttributes().iterator(); iter.hasNext(); ) {
			EAttribute attr = (EAttribute) iter.next();
			Object val = newObject.eGet(attr);
			
			System.out.println("NEW-> " + attr.getName() + " : " + val);
		}
		
		System.out.println(newObject);
		newElements.put(eo, newObject);
		System.out.println(" --- ");
		
		fillMap(eo);
	}
	

	/**
	 * Get the eClasse's EAttribute with the specified name 
	 * @param name
	 * @param ec
	 * @return
	 */
	private EAttribute getEAttributeForName(String name, EClass ec) {
		for (Iterator<EAttribute> iter = ec.getEAllAttributes().iterator(); iter.hasNext(); ) {
			EAttribute attr = (EAttribute) iter.next();
			if (attr.getName().equals(name)) {
				return attr;
			}
		}
		return null;
	}
	
	/**
	 * Get the eClasse's EReference with the specified name
	 * @param name
	 * @param ec
	 * @return
	 */
	private EReference getEReferenceByName(String name, EClass ec) {
		for (Iterator<EReference> iter = ec.getEAllReferences().iterator(); iter.hasNext(); ) {
			EReference eref = (EReference) iter.next();
			System.out.println("\t" + eref.getName());
			if (eref.getName().equals(name)) {
				return eref;
			}
		}
		return null;
	}
	
	
	/**
	 * Wrap NACs in NOT
	 */
	private void wrapNACs() {
		System.out.println("\n\n --- wrap nacs ---");
		for (Entry<EObject, EObject> entry : newElements.entrySet()) {
			if (entry.getValue() instanceof Graph) {
				Formula nacCandidate = ((Graph) entry.getValue()).getFormula();
				if (nacCandidate instanceof NestedCondition) {
					// check if nac, then wrap in not
					if (nacList.contains(nacCandidate)) {	 // candidate is a NAC
						NestedCondition nac = (NestedCondition) nacCandidate;
						nacList.remove(nac);			// remove the nac from the nac list. 
														// If there are any problems, the list will not
														// be empty when the program is finished.
						Not nacNotWrapper = HenshinFactory.eINSTANCE.createNot(); 
						nacNotWrapper.setChild(nac);
						((Graph) entry.getValue()).setFormula(nacNotWrapper);
						
						createdElements.add(nacNotWrapper);
					} else {
						// candidate is a PAC, so do nothing
					}
				} else if (nacCandidate != null) {
					// otherwise, nac may be more deeply nested in formula; 
					// traverse formula to find out if nac is contained there,
					// and if so, wrap it
					System.out.println("traverse formula!" + nacCandidate);
					traverseFormula(nacCandidate);
				}
			}
		}
	}

	
	/**
	 * Traverse formulas recursively for searching for NACs.
	 * @param f
	 */
	private void traverseFormula(Formula f) {
		System.out.println("... traversing formula " + f);
		if (f instanceof Not) {
			 if (((Not) f).getChild() instanceof NestedCondition) {
				 if (nacList.contains(((NestedCondition) ((Not) f).getChild()))) {
					 ((Not) f).setChild(wrapNac((NestedCondition) ((Not) f).getChild())); 
				 }
			 } else {
				 // it's a formula!
				 traverseFormula(((Not) f).getChild());
			 }
		} else if (f instanceof BinaryFormula) {
			BinaryFormula binF = (BinaryFormula) f;
			
			System.out.println(".. binary formula: <<" + binF.getLeft() + " ; " + binF.getRight() + ">>");
			
			if (binF.getLeft() instanceof NestedCondition) {
				if (nacList.contains((NestedCondition) binF.getLeft())) {
					binF.setLeft(wrapNac((NestedCondition) binF.getLeft()));
				}
			} else {
				// it's a formula!
				traverseFormula(binF.getLeft());
			}
			
			if (binF.getRight() instanceof NestedCondition) {
				if (nacList.contains((NestedCondition) binF.getRight())) {
					binF.setRight(wrapNac((NestedCondition) binF.getRight()));
				}
			} else {
				// it's a formula!
				traverseFormula(binF.getRight());
			}
			
		}
	}
	
	/**
	 * Wrap a NAC and return Not<>--NC
	 */
	private Not wrapNac(NestedCondition nac) {
		nacList.remove(nac);
		Not nacNotWrapper = HenshinFactory.eINSTANCE.createNot();
		nacNotWrapper.setChild(nac);
		createdElements.add(nacNotWrapper);
		return nacNotWrapper;
	}
	
	
	/**
	 * Clean up double negations
	 */
	private void cleanUpNotFormulas() {
		ArrayList<EObject> newObjectList = new ArrayList<EObject>();
		newObjectList.addAll(newElements.values());
		newObjectList.addAll(createdElements);
		
		for (EObject val : newObjectList) {
			if (val instanceof Not) {
				Not tempN = (Not) val;
				System.out.println("tempn container::::::: " + tempN.eContainer());
				if (tempN.getChild() instanceof Not) {
					if (tempN.eContainer() instanceof Graph) {
						// Graph<>---> Not<>---> Not<>---> Formula    ==>
						// Graph<>-----------------------> Formula
						Graph ncContainer = (Graph) tempN.eContainer();
						ncContainer.setFormula(((Not) tempN.getChild()).getChild());
					} else if (tempN.eContainer() instanceof BinaryFormula) {
						// BinaryFormula<>---> Not<>---> Not<>---> Formula    
						//              <>---> ?
						//                                                     ===>
						// BinaryFormula<>-----------------------> Formula
						//              <>---> ? 

						BinaryFormula binF = (BinaryFormula) tempN.eContainer();
						if ((binF.getLeft() != null) && (binF.getLeft().equals(tempN))) {
							binF.setLeft(((Not) tempN.getChild()).getChild());
						} else if ((binF.getRight() != null) && (binF.getRight().equals(tempN))) {
							binF.setRight(((Not) tempN.getChild()).getChild());
						}
					}
				}
			}
		}
	}
	
	
	private void addError(String errorText) {
		errorList.add(errorText);
	}

	/**
	 * Search for a Rule Replacement in the Rule Replacement list
	 * @param tu	TransformationUnit to look for
	 * @param r		Rule to look for
	 * @return		the Rule Replacement, if found. Otherwise, null.
	 */
	private RuleReplacementHelper searchForRuleReplacement(TransformationUnit tu, Rule r) {
		for (RuleReplacementHelper rrh : ruleReplacements) {
			if ((r.equals(rrh.rule)) && (tu.equals(rrh.tu))) {
				return rrh;
			}
		}
		return null;
	}
	
	/**
	 * Adds a Rule to the Rule Replacement list.
	 * @param rule	Rule
	 * @param tu	TransformationUnit referencing the Rule
	 * @param index	index of the Rule in the TransformationUnit
	 */
	private void addRuleReplacement(EObject rule, EObject tu, int index) {
		RuleReplacementHelper rrh = searchForRuleReplacement((TransformationUnit) tu, (Rule) rule);
		if (rrh == null) { // no matching rule replacement, add new rule replacement to list
			ruleReplacements.add(new RuleReplacementHelper((Rule) rule, (TransformationUnit) tu, index));
		} else { // rule replacement already exists, so just add another index to the already existing list
			rrh.ruleInTuIndices.add(index);
		}
	}
	
	
}
