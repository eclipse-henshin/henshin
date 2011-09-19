/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorPlugin;
import org.eclipse.emf.henshin.diagram.part.HenshinLinkUpdater;
import org.eclipse.emf.henshin.diagram.part.HenshinSymbolUpdater;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class InvocationDeleteCommand extends AbstractTransactionalCommand {

	// Invocation (view) to be deleted:
	private View invocationView;
	
	/**
	 * Default constructor.
	 * @param domain Editing domain.
	 */
	public InvocationDeleteCommand(TransactionalEditingDomain domain, View invocationView) {
		super(domain, "Delete Invocation", null);
		this.invocationView = invocationView;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.commands.operations.AbstractOperation#canExecute()
	 */
	@Override
	public boolean canExecute() {
		if (invocationView==null || getInvocation()==null || getUnit()==null) {
			return false;
		}
		if (!getUnit().getSubUnits(false).contains(getInvocation())) {
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		
		View unitView = getUnitView();
		TransformationUnit unit = getUnit();
		TransformationUnit invocation = getInvocation();
		
		// Check the unit type:
		if (unit instanceof SequentialUnit) {
			((SequentialUnit) unit).getSubUnits().remove(invocation);
		}
		
		// Delete the invocation view and update the unit view:
		ViewUtil.destroy(invocationView);
		PreferencesHint prefHint = HenshinDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
		new HenshinSymbolUpdater(prefHint, true).update(unitView);
		new HenshinLinkUpdater(prefHint, true).update(unitView);
		
		// Done.
		return CommandResult.newOKCommandResult();
	}

	private TransformationUnit getInvocation() {
		return (TransformationUnit) invocationView.getElement();
	}

	private TransformationUnit getUnit() {
		return (TransformationUnit) getUnitView().getElement();
	}

	private View getUnitView() {
		return (View) invocationView.eContainer().eContainer();
	}

}
