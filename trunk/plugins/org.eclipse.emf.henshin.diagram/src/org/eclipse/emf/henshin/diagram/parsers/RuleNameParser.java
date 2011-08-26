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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.diagram.edit.helpers.RootObjectEditHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.TransformationSystemEditHelper;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Parser for rule names and root objects.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class RuleNameParser extends UnitNameParser {
	
	/**
	 * Default constructor.
	 * 
	 * @param rule
	 *            Rule.
	 */
	public RuleNameParser(View view) {
		super(view);
		if (!RootObjectEditHelper.isRuleView(view)) {
			throw new IllegalArgumentException();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.henshin.diagram.parsers.UnitNameParser#isUnitEmpty(org
	 * .eclipse.emf.henshin.model.TransformationUnit)
	 */
	@Override
	protected boolean isUnitEmpty(TransformationUnit unit) {
		if (unit instanceof Rule) {
			Rule rule = (Rule) unit;
			return rule.getLhs().getNodes().isEmpty() && rule.getRhs().getNodes().isEmpty()
					&& rule.getParameters().isEmpty();
		} else {
			return super.isUnitEmpty(unit);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString
	 * (org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getPrintString(IAdaptable element, int flags) {
		
		// Compute the root object:
		String root = "";
		Node rootObject = RootObjectEditHelper.getRootObject(unitView);
		if (rootObject != null) {
			root = " @" + rootObject.getType().getName();
		}
		
		// Compile the title:
		return (super.getPrintString(element, flags) + root);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.henshin.diagram.parsers.UnitNameParser#doParsing(java
	 * .lang.String)
	 */
	@Override
	protected CommandResult doParsing(String value) throws ExecutionException {
		
		// We need the rule:
		Rule rule = (Rule) unitView.getElement();
		
		// Parse the input:
		String name, rootType;
		
		// Separate the root type:
		int at = value.indexOf('@');
		if (at < 0) {
			name = value;
			rootType = null;
		} else {
			name = value.substring(0, at).trim();
			rootType = value.substring(at + 1).trim();
			if (rootType.isEmpty()) {
				rootType = null;
			}
		}
		
		// Update the name and the parameters:
		super.doParsing(name);
		
		// Update the root object:
		Node oldRoot = RootObjectEditHelper.getRootObject(unitView);
		
		// Do we need to set a new root object?
		if (rootType != null && (oldRoot == null || !rootType.equals(oldRoot.getType().getName()))) {
			
			// First find the proper class and initialize the new root:
			EClass rootClass = null;
			EClassifier[] eclassifiers = TransformationSystemEditHelper.findEClassifierByName(
					rule.getTransformationSystem(), rootType);
			for (EClassifier ec : eclassifiers) {
				if (ec instanceof EClass) {
					rootClass = (EClass) rootClass;
					break;
				}// if
			}// for
			
			// We change only if the new root type was found:
			if (rootClass != null) {
				RootObjectEditHelper.setRootObjectType(unitView, rootClass);
			}
		}
		
		// Do we have to erase the current root object?
		if (rootType == null && oldRoot != null) {
			RootObjectEditHelper.setRootObject(unitView, null);
		}
		
		// Now we can update the rule name, but not directly:
		// AmalgamationEditHelper.renameKernelRule(rule,name);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
	@Override
	protected void doSetName(TransformationUnit unit, String name) {
		// We do it ourself later.
	}
	
}
