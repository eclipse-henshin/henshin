/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.editor.commands;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.util.HenshinRuleAnalysisUtil;

/**
 * Creates a {@link Mapping} between two given {@link Node}s.
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 */
public class CreateMappingCommand extends AbstractCommand {
	
	protected Node origin;
	protected Node image;
	protected Mapping mapping;
	protected Collection<?> affectedObjects;
	private boolean lhsRhsMapping = false;
	
	public CreateMappingCommand(Node source, Node target) {
		this.origin = source;
		this.image = target;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
	 */
	@Override
	protected boolean prepare() {
		lhsRhsMapping = false;
		/*
		 * 1) Check at first if any reference is null which shall not be null.
		 * 2) Check if the graphs of origin and image are not the same.
		 */
		if (QuantUtil.anyNull(origin, image, origin.getGraph(), image.getGraph(), origin.getGraph()
				.eContainer(), image.getGraph().eContainer())
				|| QuantUtil.allIdentical(origin.getGraph(), image.getGraph())) return false;
		
		if (isUnmappedLhsRhsPairFromSameRule(origin, image)) {
			lhsRhsMapping = true;
			return true;
		}
		if (isMappableSourceAndNestedTargetNode(origin, image)) return true;
		
		return false;
	}// prepare
	
	@Override
	public void execute() {
		
		mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setOrigin(origin);
		mapping.setImage(image);
		redo();
	}
	
	@Override
	public void redo() {
		if (lhsRhsMapping) {
			origin.getGraph().getContainerRule().getMappings().add(mapping);
		} else {
			NestedCondition nc = (NestedCondition) image.getGraph().eContainer();
			nc.getMappings().add(mapping);
		}
		affectedObjects = Collections.singleton(mapping);
	}
	
	@Override
	public boolean canUndo() {
		return true;
	}
	
	@Override
	public void undo() {
		if (lhsRhsMapping) {
			origin.getGraph().getContainerRule().getMappings().remove(mapping);
			affectedObjects = Collections.singleton(origin.getGraph().getContainerRule());
		} else {
			NestedCondition nc = (NestedCondition) image.getGraph().eContainer();
			nc.getMappings().remove(mapping);
			affectedObjects = Collections.singleton(nc);
		}
	}
	
	@Override
	public Collection<?> getAffectedObjects() {
		return affectedObjects;
	}
	
	/**
	 * Common interface for creating and deleting the containment relation of
	 * mappings in the model
	 * 
	 */
	protected interface ModelConnectionStrategy {
		void makeContained();
		
		/**
		 * @return affectedObjects
		 */
		Collection<?> makeUncontained();
	}
	
	/**
	 * Returns true is source and target are contained in the LHS and RHS of the
	 * same rule.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	protected boolean isUnmappedLhsRhsPairFromSameRule(Node source, Node target) {
		return HenshinRuleAnalysisUtil.isLHS(source.getGraph())
				&& HenshinRuleAnalysisUtil.isRHS(target.getGraph())
				&& (source.getGraph().getContainerRule() == target.getGraph().getContainerRule())
				&& isUnmapped(source, target, source.getGraph().getContainerRule().getMappings());
	}// isLhsRhsPairFromSameRule
	
	/**
	 * Checks if <code>sourceNode</code> or <code>targetNode</code> are involved
	 * in a mapping already, being origin or image respectively, contained in
	 * <code>mappings</code>.
	 * 
	 * @param sourceNode
	 * @param targetNode
	 * @param mappings
	 * @return
	 */
	protected boolean isUnmapped(Node sourceNode, Node targetNode, Collection<Mapping> mappings) {
		
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin() == sourceNode || mapping.getImage() == targetNode)
				return false;
		}// for
		return true;
	}// isUnmapped
	
	/**
	 * @param sourceNode
	 * @param targetNode
	 * @return
	 */
	private boolean isMappableSourceAndNestedTargetNode(Node sourceNode, Node targetNode) {
		if (targetNode.getGraph().eContainer() instanceof NestedCondition) {
			NestedCondition nc = (NestedCondition) targetNode.getGraph().eContainer();
			return isUnmapped(sourceNode, targetNode, nc.getMappings());
		}// if
		return false;
	}// isMappableSourceAndTargetNode
	
}// class
