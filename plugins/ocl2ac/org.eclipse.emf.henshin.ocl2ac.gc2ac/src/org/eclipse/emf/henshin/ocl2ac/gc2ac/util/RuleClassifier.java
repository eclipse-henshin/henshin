/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.gc2ac.util;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * 
 * This class helps of identify the rules which aim for identifying the optimization cases. 
 *
 */
public class RuleClassifier {

	private Rule rule;
	public EList<Node> createActionNodes;
	public EList<Edge> createActionEdges;
	EList<Attribute> createActionAttributes;
	EList<Object> createActionElements = new BasicEList<Object>();

	public EList<Node> deleteActionNodes;
	EList<Edge> deleteActionEdges;
	EList<Attribute> deleteActionAttributes;
	EList<Object> deleteActionElements = new BasicEList<Object>();

	EList<Node> forbidActionNodes;
	EList<Edge> forbidActionEdges;
	EList<Attribute> forbidActionAttributes;
	EList<Object> forbidActionElements = new BasicEList<Object>();

	EList<Node> requireActionNodes;
	EList<Edge> requireActionEdges;
	EList<Attribute> requireActionAttributes;
	EList<Object> requireActionElements = new BasicEList<Object>();

	public EList<Node> preserveActionNodes;
	EList<Edge> preserveActionEdges;
	EList<Attribute> preserveActionAttributes;
	EList<Object> preserveActionElements = new BasicEList<Object>();

	public RuleClassifier(Rule rule) {
		this.rule = rule;
		createActionNodes = getActionNodes(Action.Type.CREATE);
		createActionEdges = getActionEdges(Action.Type.CREATE);
		createActionAttributes = getCreateActionAttributes();
		createActionElements.addAll(createActionNodes);
		createActionElements.addAll(createActionEdges);
		createActionElements.addAll(createActionAttributes);

		deleteActionNodes = getActionNodes(Action.Type.DELETE);
		deleteActionEdges = getActionEdges(Action.Type.DELETE);
		deleteActionAttributes = getDeleteActionAttributes();
		deleteActionElements.addAll(deleteActionNodes);
		deleteActionElements.addAll(deleteActionEdges);
		deleteActionElements.addAll(deleteActionAttributes);

		requireActionNodes = getActionNodes(Action.Type.REQUIRE);
		requireActionEdges = getActionEdges(Action.Type.REQUIRE);
		requireActionAttributes = getRequireActionAttributes();
		requireActionElements.addAll(requireActionNodes);
		requireActionElements.addAll(requireActionEdges);
		requireActionElements.addAll(requireActionAttributes);

		forbidActionNodes = getActionNodes(Action.Type.FORBID);
		forbidActionEdges = getActionEdges(Action.Type.FORBID);
		forbidActionAttributes = getForbidActionAttributes();
		forbidActionElements.addAll(forbidActionNodes);
		forbidActionElements.addAll(forbidActionEdges);
		forbidActionElements.addAll(forbidActionAttributes);

		preserveActionNodes = getActionNodes(Action.Type.PRESERVE);
		preserveActionEdges = getActionEdges(Action.Type.PRESERVE);
		preserveActionAttributes = getPreserveActionAttributes();
		preserveActionElements.addAll(preserveActionNodes);
		preserveActionElements.addAll(preserveActionEdges);
		preserveActionElements.addAll(preserveActionAttributes);
	}

	public int getRuleSize() {
		return createActionElements.size() + deleteActionElements.size() + requireActionElements.size()
				+ forbidActionElements.size() + preserveActionElements.size();
	}

	/**
	 * 
	 * @return
	 */
	public boolean doesRuleCreateOnly() {
		if (createActionElements != null) {
			if (deleteActionElements.size() == 0 && forbidActionElements.size() == 0
					&& requireActionElements.size() == 0)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean doesRuleDeleteOnly() {
		if (deleteActionElements != null) {
			if (createActionElements.size() == 0 && forbidActionElements.size() == 0
					&& requireActionElements.size() == 0)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param action
	 * @return
	 */
	private EList<Edge> getActionEdges(Action.Type action) {
		return rule.getActionEdges(new Action(action));
	}

	/**
	 * 
	 * @param action
	 * @return
	 */
	private EList<Node> getActionNodes(Action.Type action) {
		return rule.getActionNodes(new Action(action));
	}

	/**
	 * 
	 * @return
	 */
	private EList<Attribute> getCreateActionAttributes() {
		EList<Attribute> createActionAttributes = new BasicEList<Attribute>();
		for (Node node : rule.getRhs().getNodes()) {
			for (Attribute attr : node.getAttributes()) {
				if (attr.getAction() != null) {
					if (attr.getAction().getType() == Action.Type.CREATE)
						createActionAttributes.add(attr);
				}
			}
		}
		return createActionAttributes;
	}

	/**
	 * 
	 * @return
	 */
	private EList<Attribute> getDeleteActionAttributes() {
		EList<Attribute> deleteActionAttributes = new BasicEList<Attribute>();
		for (Node node : rule.getLhs().getNodes()) {
			for (Attribute attr : node.getAttributes()) {
				if (attr.getAction().getType() == Action.Type.DELETE)
					deleteActionAttributes.add(attr);
			}
		}
		return deleteActionAttributes;
	}

	/**
	 * 
	 * @return
	 */
	private EList<Attribute> getForbidActionAttributes() {
		EList<Attribute> forbidActionAttributes = new BasicEList<Attribute>();

		for (Node node : rule.getLhs().getNodes()) {
			for (Attribute attr : node.getAttributes()) {
				if (attr.getAction().getType() == Action.Type.FORBID)
					forbidActionAttributes.add(attr);
			}
		}
		return forbidActionAttributes;
	}

	/**
	 * 
	 * @return
	 */
	private EList<Attribute> getRequireActionAttributes() {
		EList<Attribute> requireActionAttributes = new BasicEList<Attribute>();

		for (Node node : rule.getLhs().getNodes()) {
			for (Attribute attr : node.getAttributes()) {
				if (attr.getAction().getType() == Action.Type.REQUIRE)
					requireActionAttributes.add(attr);
			}
		}
		return requireActionAttributes;
	}

	/**
	 * 
	 * @return
	 */
	private EList<Attribute> getPreserveActionAttributes() {
		EList<Attribute> preserveActionAttributes = new BasicEList<Attribute>();

		for (Node node : rule.getLhs().getNodes()) {
			for (Attribute attr : node.getAttributes()) {
				if (attr.getAction().getType() == Action.Type.PRESERVE)
					preserveActionAttributes.add(attr);
			}
		}
		return preserveActionAttributes;
	}

}
