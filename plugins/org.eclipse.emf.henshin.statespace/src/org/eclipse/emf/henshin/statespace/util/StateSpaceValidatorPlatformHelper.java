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
package org.eclipse.emf.henshin.statespace.util;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.StateSpaceValidator;

/**
 * Helper class for registering platform state space validators.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceValidatorPlatformHelper {
	
	/**
	 * Load the state space validators registered via the platform.
	 * If the platform is not present, loading this class will throw an
	 * exception.
	 * @generated NOT
	 */
	public static void loadValidators() throws Throwable {
		
		// Get the extension point:
		IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(StateSpacePlugin.PLUGIN_ID + ".validators");
		
		// Load the validators:
		for (IConfigurationElement element : point.getConfigurationElements()) {
			if ("validator.".equals(element.getName())) {
				String id = element.getAttribute("id");
				try {
					StateSpaceValidator validator = (StateSpaceValidator) element.createExecutableExtension("class");
					StateSpacePlugin.INSTANCE.getStateSpaceValidators().put(id, validator);
				} catch (Throwable t) {
					StateSpacePlugin.INSTANCE.logError("Error loading state space validator with id " + id, t);
				}
			}
		}
		
	}
	
}
