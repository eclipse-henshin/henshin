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
package org.eclipse.emf.henshin.diagram.parsers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Parser for unit names including its parameters.
 * @generated NOT
 * @author Christian Krause
 */
public class UnitNameParser extends AbstractParser {
	
	// View of the unit to be edited:
	protected View unitView;
	
	/**
	 * Default constructor.
	 */
	public UnitNameParser(View view) {
		super(new EAttribute[] { HenshinPackage.eINSTANCE.getNamedElement_Name() });
		this.unitView = view;
	}
	
	/*
	 * Helper method to check whether a unit is empty.
	 */
	protected boolean isUnitEmpty(TransformationUnit unit) {
		return unit.getSubUnits(false).isEmpty() && unit.getParameters().isEmpty();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getPrintString(IAdaptable element, int flags) {
		TransformationUnit unit = (TransformationUnit) unitView.getElement();
		
		// Compute the display name:
		String name = unit.getName();
		if (name==null) {
			name = "";
		}
		if (name.trim().length()==0 && !isUnitEmpty(unit)) {
			name = "unnamed";
		}
		
		// Compute the parameters:
		String params = "";
		int paramCount = unit.getParameters().size();
		if (paramCount>0) {
			for (int i=0; i<paramCount; i++) {
				params = params + unit.getParameters().get(i).getName();
				if (i<paramCount-1) {
					params = params + ",";
				}
			}
			params = "(" + params + ")";
		}
		
		// Compile the title:
		return (name + params);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getEditString(IAdaptable element, int flags) {
		return getPrintString(element, flags);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand(org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
	 */
	public ICommand getParseCommand(IAdaptable element, final String value, int flags) {
		
		// Resolve the rule view:
		final View view = (View) element.getAdapter(View.class);
		
		// Get the editing domain:
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(view.getElement());
		if (domain == null) {
			return UnexecutableCommand.INSTANCE;
		}
		
		// Create parse command:
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "Parse Unit Name", null) {
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return doParsing(value);
			}
		};
		return command;
		
	}
	
	/*
	 * Parse a rule name and the optional root node.
	 */
	protected CommandResult doParsing(String value) throws ExecutionException {
		
		// We need the unit:
		TransformationUnit unit = (TransformationUnit) unitView.getElement();
		
		// Parse the input:
		String name = value;
		String[] params = new String[0];

		// Separate the parameters:
		int open_bracket = name.indexOf('(');
		int close_bracket = name.indexOf(')');
		if (open_bracket>=0) {
			if (close_bracket<open_bracket) {
				return CommandResult.newErrorCommandResult("Error parsing rule parameters");
			}
			params = name.substring(open_bracket+1, close_bracket).split(",");
			name = name.substring(0, open_bracket).trim();
		}
		
		// Update the parameters:
		unit.getParameters().add(HenshinFactory.eINSTANCE.createParameter());	// dummy change to enforce notification
		while (unit.getParameters().size()>params.length) {
			unit.getParameters().remove(unit.getParameters().size()-1);
		}
		while (unit.getParameters().size()<params.length) {
			unit.getParameters().add(HenshinFactory.eINSTANCE.createParameter());
		}
		for (int i=0; i<params.length; i++) {
			String p = params[i];
			if (p==null || p.trim().length()==0) {
				p = "p"+i;
			}
			unit.getParameters().get(i).setName(p.trim());
		}
		
		// Now set the name:
		doSetName(unit, name);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
	protected void doSetName(TransformationUnit unit, String name) {
		unit.setName(name);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.parsers.AbstractParser#isAffectingFeature(java.lang.Object)
	 */
	@Override
	protected boolean isAffectingFeature(Object feature) {
		if (feature==HenshinPackage.eINSTANCE.getNamedElement_Name()) return true;
		if (feature==HenshinPackage.eINSTANCE.getTransformationUnit_Parameters()) return true;
		if (feature==EcorePackage.eINSTANCE.getEModelElement_EAnnotations()) return true;
		if (feature==EcorePackage.eINSTANCE.getEAnnotation_References()) return true;
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString(org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 */
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

}
