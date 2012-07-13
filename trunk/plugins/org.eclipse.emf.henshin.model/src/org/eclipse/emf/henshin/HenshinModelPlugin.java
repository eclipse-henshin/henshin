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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;

public class HenshinModelPlugin extends EMFPlugin {

	/**
	 * Plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.henshin.model";

	/**
	 * Static plug-in instance.
	 */
	public static final HenshinModelPlugin INSTANCE = new HenshinModelPlugin();
	
	/**
	 * Internal implementation.
	 */
	private static Implementation plugin;	
	
	/**
	 * Importer registry.
	 */
	private Map<String,HenshinModelImporter> importers = new LinkedHashMap<String,HenshinModelImporter>();

	/**
	 * Exporter registry.
	 */
	private Map<String,HenshinModelExporter> exporters = new LinkedHashMap<String,HenshinModelExporter>();

	/**
	 * Flag indicating whether the adapters have been loaded.
	 */
	private boolean adaptersLoaded = false;
	
	/**
	 * Default constructor.
	 */
	public HenshinModelPlugin() {
		super
			(new ResourceLocator [] {
			});
	}	
	
	/**
	 * Constructor.
	 * @param delegateResourceLocators Resource locators.
	 */
	public HenshinModelPlugin(ResourceLocator[] delegateResourceLocators) {
		super(delegateResourceLocators);
	}

	/*
	 * Load the adapters (model plug-in extensions).
	 */
	private void loadAdapters() {
		if (!adaptersLoaded) {
			adaptersLoaded = true;
			try {
				HenshinModelPluginInitializer.loadAdapters();
			} catch (Throwable t) {
				// Not critical. Happens if the platform is not present.
			}
		}
	}
	
	/**
	 * Add an importer to the plug-in registry.
	 * @param importer Importer.
	 */
	public void addImporter(HenshinModelImporter importer) {
		loadAdapters();
		String name = importer.getImporterName();
		if (name==null) {
			throw new NullPointerException("Missing importer name");
		}
		importers.put(name, importer);
	}

	/**
	 * Add an exporter to the plug-in registry.
	 * @param exporter Exporter.
	 */
	public void addExporter(HenshinModelExporter exporter) {
		loadAdapters();
		String name = exporter.getExporterName();
		if (name==null) {
			throw new NullPointerException("Missing exporter name");
		}
		exporters.put(name, exporter);
	}

	/**
	 * Get a registered importer.
	 * @param name Name of the importer.
	 * @return The importer if found.
	 */
	public HenshinModelImporter getImporter(String name) {
		loadAdapters();
		if (name==null) {
			throw new NullPointerException("Missing importer name");
		}
		return importers.get(name);
	}

	/**
	 * Get a registered exporter.
	 * @param name Name of the exporter.
	 * @return The exporter if found.
	 */
	public HenshinModelExporter getExporter(String name) {
		loadAdapters();
		if (name==null) {
			throw new NullPointerException("Missing exporter name");
		}
		return exporters.get(name);
	}

	/**
	 * Get the list of registered importers.
	 * @return List of importers.
	 */
	public List<HenshinModelImporter> getImporters() {
		loadAdapters();
		List<HenshinModelImporter> result = new ArrayList<HenshinModelImporter>(importers.values());
		Collections.sort(result, new Comparator<HenshinModelImporter>() {
			@Override
			public int compare(HenshinModelImporter o1, HenshinModelImporter o2) {
				String n1 = o1.getImporterName();
				if (n1==null) return -1;
				return n1.compareTo(o2.getImporterName());
			}
		});
		return result;
	}

	/**
	 * Get the list of registered exporters.
	 * @return List of exporters.
	 */
	public List<HenshinModelExporter> getExporters() {
		loadAdapters();
		List<HenshinModelExporter> result = new ArrayList<HenshinModelExporter>(exporters.values());
		Collections.sort(result, new Comparator<HenshinModelExporter>() {
			@Override
			public int compare(HenshinModelExporter o1, HenshinModelExporter o2) {
				String n1 = o1.getExporterName();
				if (n1==null) return -1;
				return n1.compareTo(o2.getExporterName());
			}
		});
		return result;
	}

	/**
	 * Log a message.
	 * @param code Status code.
	 * @param message The message.
	 * @param t Exception.
	 */
	public void log(int code, String message, Throwable t) {
		if (plugin!=null && plugin.getLog()!=null) {
			plugin.getLog().log(new Status(code, PLUGIN_ID, 0, message, t));
		} else {
			if (code==IStatus.ERROR) {
				System.err.print("ERROR: " + message);
			}
			else if (code==IStatus.WARNING) {
				System.out.print("WARNING: " + message);
			}
			else if (code==IStatus.INFO) {
				System.out.print("INFO: " + message);
			}
			if (t!=null) t.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.EMFPlugin#getPluginResourceLocator()
	 */
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
