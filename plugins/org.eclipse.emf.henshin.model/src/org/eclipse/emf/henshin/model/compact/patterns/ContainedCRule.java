package org.eclipse.emf.henshin.model.compact.patterns;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.compact.CNode;
import org.eclipse.emf.henshin.model.compact.CRule;

public class ContainedCRule extends CRule {
	
	CNode container;

	protected ContainedCRule(Rule rule, CNode container) {
		super(rule);
		this.container=container;
	}

	@Override
	public CNode createNode(EClass classifier, Action action) {
		CNode cNode = super.createNode(classifier, action);
		for(EReference eRef: container.getNode().getType().getEAllReferences())
		{
			if(eRef.getEReferenceType() == container.getNode().getType() && eRef.isContainment()) {
				container.createEdge(cNode, eRef, cNode.getNode().getAction());
			}
		}
		return cNode;
	}
	
	public static ContainedCRule makeCRuleContained(CRule rule, CNode container) {
		return new ContainedCRule(rule.getUnit(), container);
	}
}
