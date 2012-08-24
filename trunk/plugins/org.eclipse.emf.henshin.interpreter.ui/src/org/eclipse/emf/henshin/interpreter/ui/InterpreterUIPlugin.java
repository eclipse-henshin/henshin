/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;

/**
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class InterpreterUIPlugin extends EMFPlugin {
	
	public static final String ID = "org.eclipse.emf.henshin.interpreter.ui";
	
	public static final InterpreterUIPlugin INSTANCE = new InterpreterUIPlugin();
	
	private static Activator plugin;
	
	public InterpreterUIPlugin() {
		super(new ResourceLocator[] {});
	}
	
	public static Activator getPlugin() {
		return plugin;
	}
	
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}
	
	public static class Activator extends EclipseUIPlugin {
		public Activator() {
			super();
			plugin = this;
		}
	}
	
	/**
	 * Non-breaking property access.
	 * @param key
	 * @return
	 */
	public static String LL(String key) {
		try {
			return INSTANCE.getString(key);
		} catch (Exception e) {
			System.out.println("missing property: " + key);
			return key + "_NOLL";
		}
	}
	
}
