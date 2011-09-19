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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.CopyCommand.Helper;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * Creates a copy of the given {@link AmalgamationUnit} in the
 * {@link TransformationSystem}, adds it to
 * {@link AmalgamationUnit#getMultiRules()} and creates the corresponding
 * mappings.
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 */
public class CopyKernelToMultiRulesAndCreateMappingCommand extends CompoundCommand {
	
	protected EditingDomain domain;
	protected AmalgamationUnit amalgamationUnit;
	protected Rule kernelRule;
	protected Rule newMultiRule;
	protected Collection<Mapping> rhsMappings = new ArrayList<Mapping>();
	protected Collection<Mapping> lhsMappings = new ArrayList<Mapping>();
	
	public CopyKernelToMultiRulesAndCreateMappingCommand(EditingDomain domain,
			AmalgamationUnit aUnit) {
		this.domain = domain;
		this.amalgamationUnit = aUnit;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#prepare()
	 */
	@Override
	protected boolean prepare() {
		kernelRule = amalgamationUnit.getKernelRule();
		return kernelRule != null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		// create copy of the kernelRule
		Command copyCmd = new CopyCommand(domain, kernelRule, new Helper());
		this.appendAndExecute(copyCmd);
		newMultiRule = (Rule) copyCmd.getResult().iterator().next();
		newMultiRule.setName("Copy of " + newMultiRule.getName());
		
		// create mappings for RHS
		Iterator<Node> rightKernelNodes = kernelRule.getRhs().getNodes().iterator();
		Iterator<Node> rightMultiNodes = newMultiRule.getRhs().getNodes().iterator();
		while (rightKernelNodes.hasNext() && rightMultiNodes.hasNext()) {
			rhsMappings.add(HenshinFactory.eINSTANCE.createMapping(rightKernelNodes.next(),
					rightMultiNodes.next()));
		}
		
		// create mappings for LHS
		Iterator<Node> leftKernelNodes = kernelRule.getLhs().getNodes().iterator();
		Iterator<Node> leftMultiNodes = newMultiRule.getLhs().getNodes().iterator();
		while (leftKernelNodes.hasNext() && leftMultiNodes.hasNext()) {
			lhsMappings.add(HenshinFactory.eINSTANCE.createMapping(leftKernelNodes.next(),
					leftMultiNodes.next()));
		}
		
		redo();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		kernelRule.getTransformationSystem().getRules().remove(newMultiRule);
		amalgamationUnit.getMultiRules().remove(newMultiRule);
		amalgamationUnit.getRhsMappings().removeAll(rhsMappings);
		amalgamationUnit.getLhsMappings().removeAll(lhsMappings);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
		kernelRule.getTransformationSystem().getRules().add(newMultiRule);
		amalgamationUnit.getMultiRules().add(newMultiRule);
		amalgamationUnit.getRhsMappings().addAll(rhsMappings);
		amalgamationUnit.getLhsMappings().addAll(lhsMappings);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#getAffectedObjects()
	 */
	@Override
	public Collection<?> getAffectedObjects() {
		return Collections.singleton(newMultiRule);
	}
	
}
