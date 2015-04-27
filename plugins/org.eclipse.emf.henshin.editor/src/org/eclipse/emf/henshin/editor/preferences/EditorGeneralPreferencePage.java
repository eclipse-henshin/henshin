/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.editor.preferences;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.henshin.HenshinModelPlugin;
import org.eclipse.emf.henshin.model.util.HenshinValidator;
import org.eclipse.emf.henshin.presentation.HenshinEditorPlugin;
import org.eclipse.gmf.runtime.common.ui.preferences.AbstractPreferencePage;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.osgi.service.prefs.BackingStoreException;

public class EditorGeneralPreferencePage extends AbstractPreferencePage {

	/**
	 * localized labels
	 */
	private String ENABLE_EXTENDED_CONSISTENCY_CHECK_TEXT = "Enable extended consistency check";
	private String ENABLE_EXTENDED_CONSISTENCY_CHECK_FIELD_NAME = "extendedConsistencyCheck";

	/**
	 * preference page editor controls
	 */
	private BooleanFieldEditor enableExtendedConsistencyCheck = null;

	private Composite globalSettingsComposite;

	
	public EditorGeneralPreferencePage() {
		setPreferenceStore(HenshinEditorPlugin.getPlugin().getPreferenceStore());
	}

	@Override
	protected void addFields(Composite parent) {
		
		
		Group generalGlobalGroup = new Group(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		generalGlobalGroup.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		generalGlobalGroup.setLayoutData(gridData);
		generalGlobalGroup.setText("Warnings and Errors");
		

		globalSettingsComposite = new Composite(generalGlobalGroup, SWT.NONE);	
		
		enableExtendedConsistencyCheck = new BooleanFieldEditor(
				ENABLE_EXTENDED_CONSISTENCY_CHECK_FIELD_NAME,
				ENABLE_EXTENDED_CONSISTENCY_CHECK_TEXT, 
				getGlobalSettingsComposite());
		addField(enableExtendedConsistencyCheck);
		
		//listener on the preference store to forward the consistency-check property changes to the HenshinValidator within the henshin.model plugin
		getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				
				 if (event.getProperty() == ENABLE_EXTENDED_CONSISTENCY_CHECK_FIELD_NAME) {
					 
					 if(event.getNewValue() instanceof Boolean){
						 Boolean newValue = (Boolean) event.getNewValue();
						 
						IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(HenshinModelPlugin.PLUGIN_ID);
						preferences.putBoolean(HenshinValidator.PREF_ENABLE_EXTENDED_CONSISTENCY_CHECK, newValue);	
						 
						try {
							preferences.flush();
						} catch (BackingStoreException e) {
							e.printStackTrace();
						}
					 }
				 }
			}
		  }
		);
	}

	@Override
	protected void initHelp() {
		//do nothing, no context help for modeler yet
	}

	final protected Composite getGlobalSettingsComposite() {
		return globalSettingsComposite;
	}
}
