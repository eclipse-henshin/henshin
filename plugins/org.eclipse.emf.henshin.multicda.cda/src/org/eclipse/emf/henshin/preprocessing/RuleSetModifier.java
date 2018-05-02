package org.eclipse.emf.henshin.preprocessing;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class RuleSetModifier {

	public void removeMultiRules(String sourcePath, String targetPath){
		
		File dir = new File(sourcePath);
		List<String> pathsToHenshinFiles = DirectoryUtil.inspectDirectoryForHenshinFiles(dir);
		
		for (String pathToHenshinFiles : pathsToHenshinFiles) {
			HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
			Module module = henshinResourceSet.getModule(pathToHenshinFiles);
			boolean unitContained = false;
			for (Unit unit : module.getUnits()){
				if (unit instanceof Rule){
					Rule rule = (Rule) unit;
					if(rule.getMultiRules().size() > 0){
						List<Rule> multiRules = new LinkedList<Rule>();
						multiRules.addAll(rule.getMultiRules());
						rule.getMultiRules().removeAll(multiRules);
					}
				}
			}
		// Ergebnis speichern
			String resultPath = pathToHenshinFiles.replace(sourcePath, targetPath);
			// save result in FileSystem!
			HenshinResourceSet resultResourceSet = new HenshinResourceSet();
			resultResourceSet.saveEObject(module, resultPath);
		}
	}

	public void removeUnits(String sourcePath, String targetPath){
		
		File dir = new File(sourcePath);
		List<String> pathsToHenshinFiles = DirectoryUtil.inspectDirectoryForHenshinFiles(dir);
		
		for (String pathToHenshinFiles : pathsToHenshinFiles) {
			HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
			Module module = henshinResourceSet.getModule(pathToHenshinFiles);
			boolean unitContained = false;
			Set<Unit> units = new HashSet<Unit>();
			for (Unit unit : module.getUnits()){
				if (!(unit instanceof Rule)){
					units.add(unit);
				}
			}
			module.getUnits().removeAll(units);
//			if(unitContained){
//				numberOfFilesWithUnits++;
//			}
			
		// Ergebnis speichern
			String resultPath = pathToHenshinFiles.replace(sourcePath, targetPath);
			// save result in FileSystem!
			HenshinResourceSet resultResourceSet = new HenshinResourceSet();
			resultResourceSet.saveEObject(module, resultPath);
		}
	}

	public void removeApplicationConditions(String sourcePath, String targetPath) {
		
		File dir = new File(sourcePath);
		List<String> pathsToHenshinFiles = DirectoryUtil.inspectDirectoryForHenshinFiles(dir);
		
		for (String pathToHenshinFiles : pathsToHenshinFiles) {
			HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
			Module module = henshinResourceSet.getModule(pathToHenshinFiles);
			boolean unitContained = false;
			Set<Unit> units = new HashSet<Unit>();
			for (Unit unit : module.getUnits()){
				if (unit instanceof Rule){
					Rule rule = (Rule) unit;
//					// check regarding AC:
					Formula formula = rule.getLhs().getFormula();
					if(formula != null)
						rule.getLhs().setFormula(null);
				}
			}
			module.getUnits().removeAll(units);
		// Ergebnis speichern
			String resultPath = pathToHenshinFiles.replace(sourcePath, targetPath);
			HenshinResourceSet resultResourceSet = new HenshinResourceSet();
			resultResourceSet.saveEObject(module, resultPath);
		}
	}
	
	public void transformDeleteToPreserve(String sourcePath, String targetPath, boolean adaptRuleName) {
		
		
		File dir = new File(sourcePath);
		List<String> pathsToHenshinFiles = DirectoryUtil.inspectDirectoryForHenshinFiles(dir);
		
		for (String pathToHenshinFiles : pathsToHenshinFiles) {
			HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
			Module module = henshinResourceSet.getModule(pathToHenshinFiles);
//			List<Unit> units = new LinkedL
			Set<Unit> units = new HashSet<Unit>();
			units.addAll(module.getUnits());
			transformDeleteToPreserve(units, adaptRuleName);
		// Ergebnis speichern
			String resultPath = pathToHenshinFiles.replace(sourcePath, targetPath);
			HenshinResourceSet resultResourceSet = new HenshinResourceSet();
			resultResourceSet.saveEObject(module, resultPath);
		}
	}

	private void transformDeleteToPreserve(Set<Unit> units, boolean adaptRuleName) {
		for (Unit unit : units){
			if (unit instanceof Rule){
				Rule rule = (Rule) unit;
				transformDeleteToPreserve(rule, adaptRuleName);
			}
		}
	}

	public static void transformDeleteToPreserve(Rule rule, boolean adaptRuleName) {
		HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;
		// identify DELETE nodes
		Action deleteAction = new Action(Action.Type.DELETE);
		EList<Node> deletionNodes = rule.getActionNodes(deleteAction);
		// create new Node in RHS
		for(Node deletionNode : deletionNodes){
			Node newNodeInRHS = henshinFactory.createNode(rule.getRhs(), deletionNode.getType(), deletionNode.getName());
			// createMapping
			rule.getMappings().add(deletionNode, newNodeInRHS);
		}
		
		// identify Delete Edges
		EList<Edge> deletionEdges = rule.getActionEdges(deleteAction);
		for(Edge edge : deletionEdges){
			MappingList mappingList = rule.getMappings();
			Node sourceNodeInRHS = mappingList.getImage(edge.getSource(), rule.getRhs());
			Node targetNodeInRHS = mappingList.getImage(edge.getTarget(), rule.getRhs());
			// create new Edge in RHS!
			henshinFactory.createEdge(sourceNodeInRHS, targetNodeInRHS, edge.getType());
		}
		if(adaptRuleName)
			rule.setName(rule.getName()+"_preserve");
	}
	
	public static List<Rule> getDeleteToPreserveCopyOfRules(List<Rule> rules, boolean adaptRuleName){
		List<Rule> copyOfRules = new LinkedList<Rule>();
		for(Rule rule : rules){
			Copier copier = new Copier();
			Rule copiedRule = (Rule) copier.copy(rule);
			// soll die kopierte Regel auch Teil des Module sein? für den nachfolgenden Export vermutlich nicht verkehrt, aber wirklich gewollt? 
			copier.copyReferences();
			copyOfRules.add(copiedRule);
			transformDeleteToPreserve(copiedRule, true);
		}
		return copyOfRules;
	}

}
