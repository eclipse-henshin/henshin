package org.eclipse.emf.henshin.variability.mergein.refactoring.logic;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityFactory;

import mergeSuggestion.MergeRule;
import mergeSuggestion.MergeRuleElement;

public abstract class NewMergerTester {

	private static final String TEST = "-test";
	private static final String TESTCOMPLETE = "-testcomplete";
	private static final String HENSHIN = ".henshin";

	public static void testSortMergeRuleElements(NewMerger merger) {
		System.out.println("===");
		MergeRule mr = merger.getMergeRule();
		for (MergeRuleElement mre : mr.getElements()) {
			String str = mre.getName() + ":";
			for (GraphElement elem : mre.getReferenceElements()) {
				str += " " + elem;
			}
			System.out.println(str);
		}
		System.out.println("===");
	}

	public static void testSetPresenceConditions(NewMerger merger) {
		System.out.println("===");
		MergeRule mr = merger.getMergeRule();
		for (MergeRuleElement mre : mr.getElements()) {
			String str = mre.getName() + ":";
			for (GraphElement elem : mre.getReferenceElements()) {
				str += " " + elem + "(" + VariabilityFactory.INSTANCE.createVariabilityGraphElement(elem).getPresenceCondition() + ")";
			}
			System.out.println(str);
		}
		System.out.println("===");
	}

	public static void testFillNodesMap(NewMerger merger) {
		System.out.println("===");
		HashMap<Node, Node> map = merger.getNodesMap();
		for (GraphElement ge : map.keySet()) {
			System.out.println("Key: " + ge + "; Value: " + map.get(ge));
		}
		System.out.println("=> Number of pairs: " + map.size());
		System.out.println("===");
	}

	public static void testPartitionMergeRuleElements(NewMerger merger) {
		System.out.println("=== LSH elements");
		for (MergeRuleElement mre : merger.getElementsLhs()) {
			String str = mre.getName() + ":";
			for (GraphElement elem : mre.getReferenceElements()) {
				str += " " + elem;
			}
			System.out.println(str);
		}		
		System.out.println("=== RSH elements");
		for (MergeRuleElement mre : merger.getElementsRhs()) {
			String str = mre.getName() + ":";
			for (GraphElement elem : mre.getReferenceElements()) {
				str += " " + elem;
			}
			System.out.println(str);
		}		
		System.out.println("=== Conditions elements");
		for (MergeRuleElement mre : merger.getElementsConditions()) {
			String str = mre.getName() + ":";
			for (GraphElement elem : mre.getReferenceElements()) {
				str += " " + elem;
			}
			System.out.println(str);
		}	
		System.out.println("=== Nested rules elements");
		for (MergeRuleElement mre : merger.getElementsNestedRules()) {
			String str = mre.getName() + ":";
			for (GraphElement elem : mre.getReferenceElements()) {
				str += " " + elem;
			}
			System.out.println(str);
		}		
		System.out.println("===");
	}

	public static void testUnifyParameterNames(NewMerger merger) {
		System.out.println("===");
		Rule masterRule = merger.getMasterRule();
		String str = masterRule.getName() + ":";
		for (Parameter param : masterRule.getParameters()) {
			str += " " + param.getName();
		}
		System.out.println(str);
		for (Rule rule : merger.getFurtherRules()) {
			str = rule.getName() + ":";
			for (Parameter param : rule.getParameters()) {
				str += " " + param.getName();
			}
			System.out.println(str);
		}
		System.out.println("===");
		testSortMergeRuleElements(merger);
	}

	public static void testMergeParametersIntoMasterRule(NewMerger merger) {
		System.out.println("===");
		Rule masterRule = merger.getMasterRule();
		String str = masterRule.getName() + ":";
		for (Parameter param : masterRule.getParameters()) {
			str += " " + param.getName();
		}
		System.out.println(str);
		for (Rule rule : merger.getFurtherRules()) {
			str = rule.getName() + ":";
			for (Parameter param : rule.getParameters()) {
				str += " " + param.getName();
			}
			System.out.println(str);
		}
		System.out.println("===");
	}

	public static void testMergeLhsGraph(NewMerger merger) {
		System.out.println("===");
		Rule masterRule = merger.getMasterRule();
		System.out.println(masterRule.getName() + ":");
		printGraph(masterRule.getLhs());
		for (Rule rule : merger.getFurtherRules()) {
			System.out.println("---");
			System.out.println(rule.getName() + ":");
			printGraph(rule.getLhs());
		}
		System.out.println("===");
	}

	private static void printGraph(Graph graph) {
		for (Node node : graph.getNodes()) {
			System.out.println(node);
			for (Attribute attr : node.getAttributes()) {
				System.out.println(attr);
			}
		}
		for (Edge edge : graph.getEdges()) {
			System.out.println(edge);
		}
	}

	public static void testMergeRhsGraph(NewMerger merger) {
		System.out.println("===");
		Rule masterRule = merger.getMasterRule();
		System.out.println(masterRule.getName() + ":");
		printGraph(masterRule.getRhs());
		for (Rule rule : merger.getFurtherRules()) {
			System.out.println("---");
			System.out.println(rule.getName() + ":");
			printGraph(rule.getRhs());
		}
		System.out.println("===");
	}

	public static void testAddLhsRhsMapping(NewMerger merger) {
		System.out.println("===");
		Rule masterRule = merger.getMasterRule();
		System.out.println(masterRule.getName() + ":");
		printGraph(masterRule.getLhs());
		System.out.println("---");
		printGraph(masterRule.getRhs());
		System.out.println("---");
		printMapping(masterRule.getMappings());
		System.out.println("===");
	}

	private static void printMapping(MappingList mappings) {
		Iterator<Mapping> iter = mappings.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}

	public static void testPrepareRuleSets(NewMerger merger) {
		System.out.println("===");
		EList<MergeRuleElementRulesMapping> mappings = merger.getMergeRuleElementRulesMappings();
		for (MergeRuleElementRulesMapping mapping : mappings) {
			String str = mapping.getMergeRuleElement().getName() + ":";
			for (Rule rule : mapping.getRules()) {
				str += " " + rule.getName();
			}			
			System.out.println(str);
		}
		System.out.println("===");
	}

	public static void testMergeConditionsIntoMasterRule(NewMerger merger) {
		System.out.println("===");
		try {
			String fileName = getHenshinFile(merger.getModule()).getName();
			int index = fileName.lastIndexOf(HENSHIN);
			String rawFileName = fileName.substring(0, index);
			fileName = rawFileName + TEST + HENSHIN;
			String filePath = getHenshinFile(merger.getModule()).getParent();
			fileName = filePath + "\\" + fileName;
			merger.saveModule(fileName);
			System.out.println("saved to " + fileName);
		} catch (MergeInException e) {
			e.printStackTrace();
		}
		System.out.println("===");
	}

	public static void testCompleteMerging(NewMerger merger) {
		System.out.println("===");
		try {
			String fileName = getHenshinFile(merger.getModule()).getName();
			int index = fileName.lastIndexOf(HENSHIN);
			String rawFileName = fileName.substring(0, index);
			fileName = rawFileName + TESTCOMPLETE + HENSHIN;
			String filePath = getHenshinFile(merger.getModule()).getParent();
			fileName = filePath + "\\" + fileName;
			merger.saveModule(fileName);
			System.out.println("saved to " + fileName);
		} catch (MergeInException e) {
			e.printStackTrace();
		}
		System.out.println("===");
	}
	
	private static File getHenshinFile(Module module) throws MergeInException {
		URI uri = module.eResource().getURI();
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IPath path = new Path(uri.toPlatformString(true));
		IFile file = workspaceRoot.getFile(path);
		if (file != null && file.getLocation() != null) {
			File f = file.getLocation().toFile();
			return f;
		}
		throw new MergeInException(MergeInException.NO_MODULE_3);
	}
}
