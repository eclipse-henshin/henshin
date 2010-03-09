/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.validation;

import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.statespace.validation.impl.StateValidatorDelegate;
import org.osgi.framework.BundleContext;

/**
 * State space validation plug-in activator.
 */
public class StateSpaceValidationPlugin extends Plugin {
	
	// The plug-in ID:
	public static final String PLUGIN_ID = "org.eclipse.emf.henshin.validation";

	// Extension point ID for validators:
	public static final String VALIDATORS_EXTENSION_POINT_ID = "org.eclipse.emf.henshin.statespace.validation.validators";
	
	// The shared plug-in instance:
	private static StateSpaceValidationPlugin plugin;
	
	// State validator (delegate).
	private StateValidator stateValidator;
	
	// State space validator (delegate).
	private StateSpaceValidator stateSpaceValidator;
	
	/**
	 * Default constructor.
	 */
	public StateSpaceValidationPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 * @return the shared instance.
	 */
	public static StateSpaceValidationPlugin getInstance() {
		return plugin;
	}

	public StateValidator getStateValidator() {
		if (stateValidator==null) {
			stateValidator = new StateValidatorDelegate();
			loadValidators("stateValidator", ((StateValidatorDelegate) stateValidator).getValidatorTypes()); 
		}
		return stateValidator;
	}

	/**
	 * Get the common state space validator.
	 * @return 
	 */
	public StateSpaceValidator getStateSpaceValidator() {
		return stateSpaceValidator;
	}
	
	/*
	 * Load validators from the extension point.
	 */
	@SuppressWarnings("unchecked")
	private <T> void loadValidators(String element, Map<String,Class<T>> registry) {
		
		// Load the extension point:
		IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(VALIDATORS_EXTENSION_POINT_ID);
		IConfigurationElement[] elements = point.getConfigurationElements();
		
		// Load the validators:
		for (int i=0;i<elements.length; i++) {
			if (element.equals(elements[i].getName())) {
				String name = elements[i].getAttribute("name");
				if (!isIdentifier(name)) {
					logError("Validator with invalid name: '" + name + "' (must be an identifier)",null);
				} else if (registry.containsKey(name)) {
					logError("Duplicate validator extension: '" + name + "' (ignored)",null);
				} else {
					try {
						T instance = (T) elements[i].createExecutableExtension("class");
						registry.put(name, (Class<T>) instance.getClass());
					} catch (Throwable e) {
						logError("Error loading validator '" + name + "'", e);
					}
				}
			}
		}
	}
	
	/*
	 * Check if the parameter is a valid identifier.
	 */
	private boolean isIdentifier(String ident) {
		if (ident==null || ident.length()==0) return false;
		if (!Character.isJavaIdentifierStart(ident.charAt(0))) return false;
		for (int i=1; i<ident.length(); i++) {
			if (!Character.isJavaIdentifierPart(ident.charAt(i))) return false;
		}
		return true;
	}
	
	/**
	 * Log an error.
	 * @param message Error message.
	 * @param t Exception.
	 * @generated NOT
	 */
	public void logError(String message, Throwable t) {
		plugin.getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, 0, message, t));
	}

}
