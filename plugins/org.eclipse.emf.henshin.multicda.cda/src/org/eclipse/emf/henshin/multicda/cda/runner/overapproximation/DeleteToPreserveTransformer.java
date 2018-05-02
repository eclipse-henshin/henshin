package org.eclipse.emf.henshin.multicda.cda.runner.overapproximation;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.multicda.cda.runner.Runner;
import org.junit.Assert;

public class DeleteToPreserveTransformer {

	private static HenshinFactory henshinFactory = HenshinFactoryImpl.eINSTANCE;

	private static String path = "testData\\featureModelingWithoutUpperLimitsOnReferences\\fmedit_noAmalgamation_noNACs_noAttrChange_additionalPreserveProgrammatic\\preserve_rules\\";

	public static void main(String[] args) {

		final File f = new File(Runner.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();
		String projectPath = filePath.replaceAll("bin", "");
		System.out.println(projectPath);
		String fullSubDirectoryPath = projectPath + path;

		File dir = new File(fullSubDirectoryPath);

		List<File> henshinFiles = inspectDirectoryForHenshinFiles(dir);

		System.out.println("number of henshin files: " + henshinFiles.size());

		List<String> failedRules = new LinkedList<String>();

		int amountOfModifiedFiles = 0;

		for (File henshinFile : henshinFiles) {

			File containingFolder = henshinFile.getParentFile();
			String absolutePath = containingFolder.getAbsolutePath();

			String henshinFileName = henshinFile.getName();
			HenshinResourceSet resourceSet = new HenshinResourceSet(absolutePath);
			Module module = resourceSet.getModule(henshinFileName, false);

			EList<Unit> units = module.getUnits();
			Rule theRule = null;
			boolean multipleRules = false;
			for (Unit unit : units) {
				if (unit instanceof Rule) {
					multipleRules = (theRule != null) ? true : false;
					theRule = (Rule) unit;
				}
			}
			if (multipleRules)
				System.err.println("amount of rules: " + units.size());

			if (theRule != null) {

				System.out.println("processing rule: " + theRule.getName());

				int numberOfLhsNodesBefore = theRule.getRhs().getNodes().size();
				System.out.println("numberOfRhsNodesBefore: " + numberOfLhsNodesBefore);

				Action deleteAction = new Action(Action.Type.DELETE);

				EList<Node> deletionNodes = theRule.getActionNodes(deleteAction);
				System.err.println("deletionNodes before: " + deletionNodes.size());
				int amountOfMappingsBefore = theRule.getMappings().size();
				System.err.println("mappingsBefore: " + amountOfMappingsBefore);
				for (Node deletionNode : deletionNodes) {
					Node newRhsNode = henshinFactory.createNode(theRule.getRhs(), deletionNode.getType(),
							deletionNode.getName()); //: ggf. Null von getName() abfangen?
					Mapping createMapping = henshinFactory.createMapping(deletionNode, newRhsNode);
					theRule.getMappings().add(createMapping); //: prüfen ob das notwendig ist

				}

				Assert.assertTrue(true);

				EList<Node> deletionNodesAfter = theRule.getActionNodes(deleteAction);
				System.err.println("deletionNodes after: " + deletionNodesAfter.size());
				MappingList mappingsAfter = theRule.getMappings();
				System.err.println("mappingsAfter: " + mappingsAfter.size() + " ; +"
						+ (mappingsAfter.size() - amountOfMappingsBefore));

				EList<Edge> deletionEdges = theRule.getActionEdges(deleteAction);
				for (Edge deletionEdge : deletionEdges) {
					MappingList allMappings = theRule.getAllMappings();
					Node sourceInLhs = deletionEdge.getSource();//.getActionNode();
					Node sourceInRhs = allMappings.getImage(sourceInLhs, theRule.getRhs());
					if (sourceInLhs == null || sourceInRhs == null)
						System.out.println("ERROR");
					Node targetInLhs = deletionEdge.getTarget();//.getActionNode();
					Node targetInRhs = allMappings.getImage(targetInLhs, theRule.getRhs());
					if (targetInLhs == null || targetInRhs == null)
						System.out.println("ERROR");
					henshinFactory.createEdge(sourceInRhs, targetInRhs, deletionEdge.getType());
				}

				int numberOfLhsNodesAfter = theRule.getRhs().getNodes().size();
				System.out.println("numberOfRhsNodesAfter: " + numberOfLhsNodesAfter);

				int numberOfDeleteActionNodes = theRule.getActionNodes(deleteAction).size();
				System.err.println("numberOfDeleteActionNodes: " + numberOfDeleteActionNodes);
				int numberOfDeleteActionEdges = theRule.getActionEdges(deleteAction).size();
				System.err.println("numberOfDeleteActionEdges: " + numberOfDeleteActionEdges);
			}

			amountOfModifiedFiles++;
			System.out.println("amountOfModifiedFiles: " + amountOfModifiedFiles);

			if (!failedRules.contains(theRule.getName())) {
				resourceSet.saveEObject(module, henshinFile.getPath());
			}
		}

		System.err.println("failed rules:");
		for (String failedRuleName : failedRules) {
			System.err.println(failedRuleName);
		}
	}


	private static List<File> inspectDirectoryForHenshinFiles(File dir) {
		List<File> henshinFiles = new LinkedList<File>();
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				String fileName = child.getName();
				if (fileName.endsWith(".henshin")) {
					henshinFiles.add(child);
				} else if (!child.getName().contains(".")) {
					File subDir = child;
					henshinFiles.addAll(inspectDirectoryForHenshinFiles(subDir));
				}
			}
		}
		return henshinFiles;
	}
}
