package org.eclipse.emf.henshin.editor.commands.rule;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class AddMappedNodeCommand extends AbstractCommand {
	
	protected Rule rule;
	
	@Override
	public void execute() {
		
		Node origNode = HenshinFactory.eINSTANCE.createNode();
		origNode.setName("newOrigNode");
		
		Node imgNode = HenshinFactory.eINSTANCE.createNode();
		imgNode.setName("newImgNode");
		
		Mapping mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setOrigin(origNode);
		mapping.setImage(imgNode);
		
		rule.getLhs().getNodes().add(origNode);
		rule.getRhs().getNodes().add(imgNode);
		rule.getMappings().add(mapping);
	}
	
	@Override
	public void redo() {
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
	@Override
	protected boolean prepare() {
		return true;
	}
	
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
}
