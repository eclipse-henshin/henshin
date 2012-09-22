/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.statespace.impl;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;

/**
 * Model post processor implementation.
 * @author Chrstian Krause
 */
class ModelPostProcessor {
	
	// Script engine:
	private ScriptEngine engine;
	
	// Cached post processing script:
	private String script;
	
	/**
	 * Constructor.
	 * @param statepace State space.
	 */
	public ModelPostProcessor(StateSpace stateSpace) {
		ScriptEngineManager manager = new ScriptEngineManager();
	    engine = manager.getEngineByName("JavaScript");
	    script = stateSpace.getProperties().get("postProcessor");
	    if (script!=null && script.trim().length()==0) {
	    	script = null;
	    }
	    if (script!=null) {
	    	String imports = "importPackage(java.lang);\n" +
	    					 "importPackage(java.util);\n" +
	    					 "importPackage(org.eclipse.emf.ecore);\n" ;
		    script = imports + script;
	    }
	}
	
	/**
	 * Do the post processing for a model.
	 * @param model Model.
	 * @throws StateSpaceException On execution errors.
	 */
	public void process(Model model) throws StateSpaceException {
		if (script!=null) {
			EObject root = model.getResource().getContents().get(0);
			synchronized (engine) {
				engine.put("model", root);
				try {
					engine.eval(script);
				} catch (ScriptException e) {
					throw new StateSpaceException(e);
				}
			}
		}
	}
	
}

