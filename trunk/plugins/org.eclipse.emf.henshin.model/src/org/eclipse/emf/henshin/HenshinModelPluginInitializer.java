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
package org.eclipse.emf.henshin;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;

/**
 * Henshin model plug-in initializer.
 * 
 * @author Christian Krause
 */
class HenshinModelPluginInitializer {
	
	/**
	 * Load the importers and exporters registered via the platform. If the 
	 * platform is not present, loading this class will throw an exception.
	 */
	public static void loadAdapters() throws Throwable {
		
		// Adapters extension point:
		IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(HenshinModelPlugin.PLUGIN_ID + ".adapters");
		
		// Load the importers and exporters:
		for (IConfigurationElement element : point.getConfigurationElements()) {
			if ("exporter".equals(element.getName())) {
				try {
					HenshinModelExporter exporter = (HenshinModelExporter) element.createExecutableExtension("class");
					HenshinModelPlugin.INSTANCE.addExporter(exporter);
				} catch (Throwable t) {
					HenshinModelPlugin.INSTANCE.log(IStatus.ERROR, "Loading exporter failed", t);
				}
			}
			if ("importer".equals(element.getName())) {
				try {
					HenshinModelImporter importer = (HenshinModelImporter) element.createExecutableExtension("class");
					HenshinModelPlugin.INSTANCE.addImporter(importer);
				} catch (Throwable t) {
					HenshinModelPlugin.INSTANCE.log(IStatus.ERROR, "Loading importer failed", t);
				}
			}
		}
		
	}
	
}
