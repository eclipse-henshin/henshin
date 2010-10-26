package org.eclipse.emf.henshin.editor.commands.node;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class RemoveOriginNodeCommand extends AbstractCommand {

	protected Rule rule;
	
	protected Node origNode;	
	
	protected Node imageNode;
	
	protected Mapping mapping;

	@Override
	public void execute() {
		rule.getLhs().removeNode(origNode);
		rule.getMappings().remove(mapping);		
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
		rule = imageNode.getGraph().getContainerRule();
		for(Mapping mapping:rule.getMappings()){
			if(mapping.getImage() == this.imageNode){
				this.mapping = mapping;
				this.imageNode = mapping.getImage();
				return true;
			}
		}
		return false;
	}

	public void setNode(Node node) {
		this.imageNode = node;
	}
}