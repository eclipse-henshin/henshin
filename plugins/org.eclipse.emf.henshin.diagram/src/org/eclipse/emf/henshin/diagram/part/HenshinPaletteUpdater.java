/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.diagram.edit.helpers.EClassComparator;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;

/**
 * Monitors the imports of a transformation system and updates the palette of a
 * Henshin diagram editor.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class HenshinPaletteUpdater {
	
	private static final Comparator<EClassifier> ECLASS_COMPARATOR = new EClassComparator();
	
	// Palette root to be updated.
	private PaletteRoot palette;
	
	// Module to be monitored.
	private Module module;
	
	// Palette drawers for the packages.
	private HashMap<EPackage, PaletteDrawer> drawers;
	
	/**
	 * Default constructor.
	 * 
	 * @param palette Palette root.
	 * @param module The module.
	 */
	public HenshinPaletteUpdater(PaletteRoot palette, Module module) {
		this.palette = palette;
		this.module = module;
		this.drawers = new HashMap<EPackage, PaletteDrawer>();
		module.eAdapters().add(listener);
		refresh();
	}
	
	public void refresh() {
		
		// Remove all drawers first:
		for (PaletteDrawer drawer : drawers.values()) {
			palette.remove(drawer);
		}
		drawers.clear();
		
		// Create a new drawer for every package:
		for (EPackage epackage : module.getImports()) {
			addDrawerForEPackageHelper(epackage, "");
		}
		
	}
	
	/**
	 * @param epackage
	 */
	private void addDrawerForEPackageHelper(EPackage epackage, String prefix) {
		
		String epackageName = prefix + epackage.getName();
		PaletteDrawer drawer = new PaletteDrawer(epackageName, HenshinPaletteTools.EPACKAGE_ICON);
		
		// Add entries for all classes:
		List<EClassifier> eclassifiers = new ArrayList<EClassifier>(epackage.getEClassifiers());
		Collections.sort(eclassifiers, ECLASS_COMPARATOR);
		for (EClassifier eclassifier : eclassifiers) {
			if (eclassifier instanceof EClass) {
				drawer.add(new HenshinPaletteTools.EClassNodeToolEntry((EClass) eclassifier));
			}
		}
		
		palette.add(drawer);
		drawers.put(epackage, drawer);
		
		// include subpackages as well
		for (EPackage ep : epackage.getESubpackages()) {
			addDrawerForEPackageHelper(ep, epackageName + ".");
		}//for
		
	}
	
	public void dispose() {
		module.eAdapters().remove(listener);
	}
	
	/*
	 * Module listener.
	 */
	private Adapter listener = new AdapterImpl() {
		public void notifyChanged(Notification event) {
			int featureID = event.getFeatureID(Module.class);
			if (featureID == HenshinPackage.MODULE__IMPORTS) {
				refresh();
			}
		}
	};
	
}
