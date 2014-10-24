/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorPlugin;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @generated
 */
public class DiagramPreferenceInitializer extends AbstractPreferenceInitializer {

	public static final String CREATE_CONTAINMENT_EDGES = "CREATE_CONTAINMENT_EDGES";

	/**
	 * @generated
	 */
	public void initializeDefaultPreferencesGen() {
		IPreferenceStore store = getPreferenceStore();
		DiagramGeneralPreferencePage.initDefaults(store);
		DiagramAppearancePreferencePage.initDefaults(store);
		DiagramConnectionsPreferencePage.initDefaults(store);
		DiagramPrintingPreferencePage.initDefaults(store);
		DiagramRulersAndGridPreferencePage.initDefaults(store);

	}

	/**
	 * @generated NOT
	 */
	@Override
	public void initializeDefaultPreferences() {

		initializeDefaultPreferencesGen();
		IPreferenceStore store = getPreferenceStore();

		store.setDefault(IPreferenceConstants.PREF_RULER_UNITS, RulerProvider.UNIT_CENTIMETERS);
		store.setDefault(IPreferenceConstants.PREF_SHOW_GRID, false);
		store.setDefault(IPreferenceConstants.PREF_SNAP_TO_GRID, false);
		store.setDefault(IPreferenceConstants.PREF_SNAP_TO_GEOMETRY, true);
		store.setDefault(IPreferenceConstants.PREF_GRID_SPACING, 0.25);
		store.setDefault(CREATE_CONTAINMENT_EDGES, false);

	}

	/**
	 * @generated
	 */
	protected IPreferenceStore getPreferenceStore() {
		return HenshinDiagramEditorPlugin.getInstance().getPreferenceStore();
	}
}
