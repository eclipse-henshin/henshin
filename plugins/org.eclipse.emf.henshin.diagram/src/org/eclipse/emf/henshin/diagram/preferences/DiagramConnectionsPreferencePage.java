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

import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorPlugin;
import org.eclipse.gmf.runtime.diagram.ui.preferences.ConnectionsPreferencePage;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @generated
 */
public class DiagramConnectionsPreferencePage extends ConnectionsPreferencePage {

	/**
	 * @generated
	 */
	public DiagramConnectionsPreferencePage() {
		setPreferenceStore(HenshinDiagramEditorPlugin.getInstance().getPreferenceStore());
	}

	@Override
	protected void addFieldEditors(Composite composite) {
		addField(new BooleanFieldEditor(DiagramPreferenceInitializer.CREATE_CONTAINMENT_EDGES,
				"Auto-Create Containment Edges", getFieldEditorParent()));
		super.addFieldEditors(composite);
	}

}
