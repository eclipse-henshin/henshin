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
package org.eclipse.emf.henshin.editor.transformationCommands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.editor.commands.MenuContributor;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.presentation.HenshinEditorPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;

/**
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 */
public abstract class TCMenuContributor extends MenuContributor {
	
	protected TransformationSystem transformationSystem;
	
	protected EmfEngine engine = new EmfEngine();
	
	protected TransformationUnit mainUnit;
	
	protected abstract String getLocalHenshinFile();
	
	protected String getMainUnitName() {
		return "main";
	}
	
	protected void loadTransformation() {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource transResource = resourceSet.getResource(getUriToPluginFile(getLocalHenshinFile()),
				true);
		TransformationSystem transSys = (TransformationSystem) transResource.getContents().get(0);
		mainUnit = transSys.findUnitByName(getMainUnitName());
	}
	
	@Override
	protected final void contributeActions(IMenuManager menuManager, List<?> selection) {
		if (!this.selectionYieldsApplicationContext(selection)) return;
		
		if (this.mainUnit == null) loadTransformation();
		
		contributeActions(menuManager);
	}
	
	abstract void contributeActions(IMenuManager menuManager);
	
	protected abstract boolean selectionYieldsApplicationContext(List<?> selection);
	
	protected IAction createUnitApplicationAction(String label, EmfGraph context) {
		return createDefaultUnitApplicationAction(label, context, new HashMap<String, String>());
	}
	
	protected IAction createDefaultUnitApplicationAction(String label, EmfGraph context,
			Map<String, String> parameters) {
		engine.setEmfGraph(context);
		UnitApplication ua = new UnitApplication(engine, mainUnit);
		for (String parameterName : parameters.keySet())
			ua.setParameterValue(parameterName, parameters.get(parameterName));
		return createAction(label, createUnitApplicationCommand(ua));
	}
	
	protected Command createUnitApplicationCommand(final UnitApplication unitApplication) {
		return new AbstractCommand() {
			
			@Override
			public void redo() {
				unitApplication.redo();
			}
			
			@Override
			public void execute() {
				unitApplication.execute();
			}
			
			@Override
			public boolean canExecute() {
				return true;
			}
			
			@Override
			public boolean canUndo() {
				return true;
			}
			
			@Override
			public void undo() {
				unitApplication.undo();
			}
		};
	}
	
	protected Command createRuleApplicationCommand(final RuleApplication ruleApp) {
		return new AbstractCommand() {
			
			@Override
			public void execute() {
				System.out.println("ruleApp.apply(): " + ruleApp.apply());
			}
			
			@Override
			public void redo() {
				ruleApp.redo();
			}
			
			@Override
			public boolean canExecute() {
				return true;
			}
			
			@Override
			public boolean canUndo() {
				return true;
			}
			
			@Override
			public void undo() {
				ruleApp.undo();
			}
		};
	}
	
	protected Rule loadRule(URI uri, String ruleName) {
		TransformationSystem tSys = loadTransformationSystem(uri);
		return tSys.findRuleByName(ruleName);
	}
	
	protected TransformationUnit loadUnit(URI uri, String unitName) {
		TransformationSystem tSys = loadTransformationSystem(uri);
		return tSys.findUnitByName(unitName);
	}
	
	protected TransformationSystem loadTransformationSystem(URI uri) {
		ResourceSet rs = new ResourceSetImpl();
		Resource res = rs.getResource(uri, true);
		TransformationSystem tSys = (TransformationSystem) res.getContents().get(0);
		return tSys;
	}
	
	protected URI getUriToPluginFile(String fileName) {
		return URI.createURI(HenshinEditorPlugin.INSTANCE.getBaseURL() + fileName);
	}
	
	protected EObject getEContainmentRoot(EObject o) {
		EObject result = o;
		while (result.eContainer() != null)
			result = result.eContainer();
		return result;
	}
	
	protected Iterable<RuleApplication> getMatchApplications(final RuleApplication ruleApp) {
		return new Iterable<RuleApplication>() {
			@Override
			public Iterator<RuleApplication> iterator() {
				
				final Iterator<Match> mIter = ruleApp.findAllMatches().iterator();
				
				return new Iterator<RuleApplication>() {
					@Override
					public boolean hasNext() {
						return mIter.hasNext();
					}
					
					@Override
					public RuleApplication next() {
						RuleApplication nextApp = new RuleApplication(
								ruleApp.getInterpreterEngine(), ruleApp.getRule());
						nextApp.setMatch(mIter.next());
						return nextApp;
					}
					
					@Override
					public void remove() {
						mIter.remove();
					}
				};
			}
		};
	}
	
	protected EmfEngine getEngineForRoots(EObject... roots) {
		EmfGraph graph = new EmfGraph();
		for (EObject root : roots)
			graph.addRoot(root);
		return new EmfEngine(graph);
	}
	
}
