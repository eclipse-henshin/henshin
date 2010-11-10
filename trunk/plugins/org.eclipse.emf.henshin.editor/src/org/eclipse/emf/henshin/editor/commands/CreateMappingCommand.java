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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;

/**
 * Creates a {@link Mapping} between two given {@link Node}s.
 * 
 * Uses a strategy/factorymethod pattern to separate cases with different
 * {@link EStructuralFeature}s containing the {@link Mapping}s.
 * 
 * @author Gregor Bonifer
 */
public class CreateMappingCommand extends AbstractCommand {
	
	protected Node origin;
	protected Node image;
	protected Mapping mapping;
	protected ModelConnectionStrategy strategy;
	protected Collection<?> affectedObjects;
	
	protected CreateMappingCommand(Node source, Node target) {
		this.origin = source;
		this.image = target;
	}
	
	protected void setStrategy(ModelConnectionStrategy strategy) {
		this.strategy = strategy;
	}
	
	@Override
	protected boolean prepare() {
		return QuantUtil.noneNull(origin, image);
	}
	
	@Override
	public void execute() {
		mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setOrigin(origin);
		mapping.setImage(image);
		redo();
	}
	
	@Override
	public void redo() {
		strategy.makeContained();
		affectedObjects = Collections.singleton(mapping);
	}
	
	@Override
	public boolean canUndo() {
		return true;
	}
	
	@Override
	public void undo() {
		affectedObjects = strategy.makeUncontained();
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
	
	protected class OriginInRuleConnectionStrategy implements ModelConnectionStrategy {
		@Override
		public void makeContained() {
			origin.getGraph().getContainerRule().getMappings().add(mapping);
		}
		
		@Override
		public Collection<?> makeUncontained() {
			origin.getGraph().getContainerRule().getMappings().remove(mapping);
			return Collections.singleton(origin.getGraph().getContainerRule());
		}
	}
	
	protected class OriginInNestedConditionConnectionStrategy implements ModelConnectionStrategy {
		@Override
		public void makeContained() {
			NestedCondition nc = (NestedCondition) origin.getGraph().eContainer();
			nc.getMappings().add(mapping);
		}
		
		@Override
		public Collection<?> makeUncontained() {
			NestedCondition nc = (NestedCondition) origin.getGraph().eContainer();
			nc.getMappings().remove(mapping);
			return Collections.singleton(nc);
		}
	}
	
	//
	// Factory methods to hide strategy pattern
	//
	
	public static CreateMappingCommand createCreateMappingInRuleCommand(Node source, Node target) {
		CreateMappingCommand cmd = new CreateMappingCommand(source, target);
		cmd.setStrategy(cmd.new OriginInRuleConnectionStrategy());
		return cmd;
	}
	
	public static CreateMappingCommand createCreateMappingInNestedConditionCommand(Node source,
			Node target) {
		CreateMappingCommand cmd = new CreateMappingCommand(source, target);
		cmd.setStrategy(cmd.new OriginInNestedConditionConnectionStrategy());
		return cmd;
	}
	
}
