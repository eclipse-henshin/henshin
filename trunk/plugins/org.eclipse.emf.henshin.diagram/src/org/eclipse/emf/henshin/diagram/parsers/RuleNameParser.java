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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.diagram.edit.helpers.AmalgamationEditHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RootObjectEditHelper;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
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
 * Parser for rule names and root objects.
 * @generated NOT
 * @author Christian Krause
 */
public class RuleNameParser extends AbstractParser {
	
	// View of the rule to be edited:
	private View ruleView;
	
	/**
	 * Default constructor.
	 * @param rule Rule.
	 */
	public RuleNameParser(View view) {
		super(new EAttribute[] { HenshinPackage.eINSTANCE.getNamedElement_Name() });
		if (!RootObjectEditHelper.isRuleView(view)) {
			throw new IllegalArgumentException();
		}
		this.ruleView = view;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getPrintString(IAdaptable element, int flags) {
		Rule rule = (Rule) ruleView.getElement();
		
		// Compute the display name:
		String name = rule.getName();
		if (name==null) {
			name = "";
		}
		if ((name.trim().length()==0) && 
			(!rule.getLhs().getNodes().isEmpty() || 
			 !rule.getRhs().getNodes().isEmpty() ||
			 !rule.getParameters().isEmpty())) {
			name = "unnamed";
		}
		
		// Compute the parameters:
		String params = "";
		int paramCount = rule.getParameters().size();
		if (paramCount>0) {
			for (int i=0; i<paramCount; i++) {
				params = params + rule.getParameters().get(i).getName();
				if (i<paramCount-1) {
					params = params + ",";
				}
			}
			params = "(" + params + ")";
		}
		
		// Compute the root object:
		String root = "";
		Node rootObject = RootObjectEditHelper.getRootObject(ruleView);
		if (rootObject!=null) {
			root = " @" + rootObject.getType().getName();
		}
		
		// Compile the title:
		return (name + params + root);
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
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "Parse Rule Name", null) {
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return doParsing(value);
			}
		};
		return command;
		
	}
	
	/*
	 * Parse a rule name and the optional root node.
	 */
	private CommandResult doParsing(String value) throws ExecutionException {
		
		// We need the rule:
		Rule rule = (Rule) ruleView.getElement();
		
		// Parse the input:
		String name, rootType;
		String[] params = new String[0];

		// Separate the root type:
		int at = value.indexOf('@');
		if (at<0) {
			name = value;
			rootType = null;
		} else {
			name = value.substring(0,at).trim();
			rootType = value.substring(at+1).trim();
			if (rootType.length()==0) rootType = null;
		}
		
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
		rule.getParameters().add(HenshinFactory.eINSTANCE.createParameter());	// dummy change to enforce notification
		while (rule.getParameters().size()>params.length) {
			rule.getParameters().remove(rule.getParameters().size()-1);
		}
		while (rule.getParameters().size()<params.length) {
			rule.getParameters().add(HenshinFactory.eINSTANCE.createParameter());
		}
		for (int i=0; i<params.length; i++) {
			String p = params[i];
			if (p==null || p.trim().length()==0) {
				p = "p"+i;
			}
			rule.getParameters().get(i).setName(p.trim());
		}
		
		// Update the root object:
		Node oldRoot = RootObjectEditHelper.getRootObject(ruleView);
		
		// Do we need to set a new root object?
		if (rootType!=null && (oldRoot==null || !rootType.equals(oldRoot.getType().getName()))) {
			
			// First find the proper class and initialize the new root:
			EClass rootClass = null;
			for (EPackage epackage : rule.getTransformationSystem().getImports()) {
				EClassifier classifier = epackage.getEClassifier(rootType);
				if (classifier instanceof EClass) {
					rootClass = (EClass) classifier;
					break;
				}
			}
			
			// We change only if the new root type was found:
			if (rootClass!=null) {
				RootObjectEditHelper.setRootObjectType(ruleView, rootClass);
			}
		}
		
		// Do we have to erase the current root object?
		if (rootType==null && oldRoot!=null) {
			RootObjectEditHelper.setRootObject(ruleView, null);
		}
		
		// Now we can update the rule name, but not directly:
		AmalgamationEditHelper.renameKernelRule(rule,name);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
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
