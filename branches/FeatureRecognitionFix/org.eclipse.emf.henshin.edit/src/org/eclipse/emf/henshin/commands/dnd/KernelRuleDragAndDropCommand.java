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
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.DragAndDropFeedback;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;
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
	protected Command dragCommand;
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
		this.dragCommand = UnexecutableCommand.INSTANCE;
		this.dropCommand = UnexecutableCommand.INSTANCE;
		this.reference = HenshinPackage.eINSTANCE.getAmalgamationUnit_KernelRule();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
	 */
	@Override
	protected boolean prepare() {
		
		dragCommand = IdentityCommand.INSTANCE;
		dropCommand = UnexecutableCommand.INSTANCE;
		
		if (checkValidInstanceVariables()) {
			
			/*
			 * create remove commands if the one element in the collection is an
			 * IWrapper since this is considered to be a move of a link.
			 * Otherwise (if o is an EObject) this is considered to be a linking
			 * which requires no remove.
			 */
			Object o = collection.toArray()[0];
			if (o instanceof IWrapperItemProvider) {
				IWrapperItemProvider wrapper = (IWrapperItemProvider) o;
				dragCommand = RemoveCommand.create(domain, wrapper.getOwner(),
						wrapper.getFeature(), wrapper.getValue());
			}// if
			
			dropCommand = SetCommand.create(domain, amalgUnit, reference, o);
		}// if
		
		return dragCommand.canExecute() && dropCommand.canExecute();
	}// prepare
	
	/**
	 * Checks that all variables are set appropriately, e.g. not null and of a
	 * specific type.
	 * 
	 * @return
	 */
	private boolean checkValidInstanceVariables() {
		// check for not being null and appropriate types
		return (domain != null) //
				&& (amalgUnit != null) //
				&& (collection != null) && (collection.size() == 1);
	}// validInstanceVariables
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	@Override
	public void execute() {
		dragCommand.execute();
		dropCommand.execute();
	}// execute
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
	 */
	@Override
	public void undo() {
		dropCommand.undo();
		dragCommand.undo();
	}// undo
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	@Override
	public void redo() {
		execute();
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
		if (dragCommand != null) {
			dragCommand.dispose();
		}// if
		if (dropCommand != null) {
			dropCommand.dispose();
		}// if
	}// dispose
	
}// class
