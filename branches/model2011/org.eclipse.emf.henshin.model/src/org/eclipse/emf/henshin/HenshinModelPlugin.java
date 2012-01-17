/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;

public class HenshinModelPlugin extends EMFPlugin {

	public static final HenshinModelPlugin INSTANCE = new HenshinModelPlugin();
	
	private static Implementation plugin;	
	
	public HenshinModelPlugin() {
		super
			(new ResourceLocator [] {
			});
	}	
	
	public HenshinModelPlugin(ResourceLocator[] delegateResourceLocators) {
		super(delegateResourceLocators);
	}

	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	public static Implementation getPlugin() {
		return plugin;
	}
	
	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Implementation extends EclipsePlugin {
		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}
	}	
	
}
