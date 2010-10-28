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
package org.eclipse.emf.henshin.commands.dnd;

import java.util.Collection;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.DragAndDropFeedback;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * 
 * 
 * @author Stefan Jurack (sjurack)
 * 
 */
public class KernelRuleDragAndDropCommand extends AbstractCommand implements DragAndDropFeedback {
	
	protected EditingDomain domain;
	protected AmalgamationUnit amalgUnit;
	protected EReference reference;
	protected Command dropCommand;
	protected Collection<?> collection;
	
	/**
	 * @param domain
	 * @param owner
	 * @param location
	 * @param operations
	 * @param operation
	 * @param collection
	 */
	public KernelRuleDragAndDropCommand(EditingDomain domain, AmalgamationUnit amalgUnit,
			Collection<?> collection) {
		this.domain = domain;
		this.amalgUnit = amalgUnit;
		this.collection = collection;
		this.dropCommand = UnexecutableCommand.INSTANCE;
		this.reference = HenshinPackage.eINSTANCE.getAmalgamationUnit_KernelRule();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
	 */
	@Override
	protected boolean prepare() {
		
		dropCommand = UnexecutableCommand.INSTANCE;
		
		if ((domain != null) && (amalgUnit != null) && (collection != null)
				&& (collection.size() == 1)) {
			
			Object o = collection.toArray()[0];
			
			dropCommand = SetCommand.create(domain, amalgUnit, reference, o);
		}// if
		
		return dropCommand.canExecute();
	}// prepare
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	@Override
	public void execute() {
		dropCommand.execute();
	}// execute
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
	 */
	@Override
	public void undo() {
		dropCommand.undo();
	}// undo
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	@Override
	public void redo() {
		dropCommand.redo();
	}// redo
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#getResult()
	 */
	@Override
	public Collection<?> getResult() {
		return dropCommand != null ? dropCommand.getResult() : super.getResult();
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.command.DragAndDropFeedback#validate(java.lang.Object
	 * , float, int, int, java.util.Collection)
	 */
	@Override
	public boolean validate(Object owner, float location, int operations, int operation,
			Collection<?> collection) {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.DragAndDropFeedback#getFeedback()
	 */
	@Override
	public int getFeedback() {
		// Only return the feedback for an executable command.
		//
		return isExecutable ? FEEDBACK_SELECT : FEEDBACK_NONE;
	}// getFeedback
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.DragAndDropFeedback#getOperation()
	 */
	@Override
	public int getOperation() {
		// Only return the operation for an executable command.
		//
		return isExecutable ? DROP_LINK : DROP_NONE;
	}// getOperation
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#getAffectedObjects()
	 */
	@Override
	public Collection<?> getAffectedObjects() {
		return dropCommand != null ? dropCommand.getAffectedObjects() : super.getAffectedObjects();
	}// getAffectedObjects
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#dispose()
	 */
	@Override
	public void dispose() {
		if (dropCommand != null) {
			dropCommand.dispose();
		}// if
	}// dispose
	
}// class
