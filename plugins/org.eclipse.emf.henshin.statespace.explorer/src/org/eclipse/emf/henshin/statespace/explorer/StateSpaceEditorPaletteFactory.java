/*******************************************************************************
 * Copyright (c) 2004, 2008 Elias Volanakis and others.
?* All rights reserved. This program and the accompanying materials
?* are made available under the terms of the Eclipse Public License v1.0
?* which accompanies this distribution, and is available at
?* http://www.eclipse.org/legal/epl-v10.html
?*
?* Contributors:
?*????Elias Volanakis - initial API and implementation
?*******************************************************************************/
package org.eclipse.emf.henshin.statespace.explorer;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteToolbar;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;

/**
 * State space diagram palette factory.
 * @author Christian Krause
 */
public class StateSpaceEditorPaletteFactory {
	
	/** Create the "Shapes" drawer. */
	private static PaletteContainer createShapesDrawer() {
		PaletteDrawer componentsDrawer = new PaletteDrawer("Shapes");

		CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
				"State", 
				"Create a state", 
				State.class,
				new SimpleFactory(State.class), 
				StateSpaceExplorerPlugin.getImageDescriptor("icons/ellipse16.gif"), 
				StateSpaceExplorerPlugin.getImageDescriptor("icons/ellipse24.gif"));
		componentsDrawer.add(component);

		return componentsDrawer;
	}

	/**
	 * Creates the PaletteRoot and adds all palette elements.
	 * Use this factory method to create a new palette for your graphical editor.
	 * @return a new PaletteRoot
	 */
	static PaletteRoot createPalette() {
		PaletteRoot palette = new PaletteRoot();
		palette.add(createToolsGroup(palette));
//		palette.add(createShapesDrawer());
		return palette;
	}

	
	/** Create the "Tools" group. */
	private static PaletteContainer createToolsGroup(PaletteRoot palette) {
		
		PaletteToolbar toolbar = new PaletteToolbar("Tools");

		// Add a selection tool to the group
		ToolEntry tool = new PanningSelectionToolEntry();
		toolbar.add(tool);
		palette.setDefaultEntry(tool);

		// Add a marquee tool to the group
		toolbar.add(new MarqueeToolEntry());
		
		CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
				"State", 
				"Create a state", 
				State.class,
				new SimpleFactory(State.class), 
				StateSpaceExplorerPlugin.getImageDescriptor("icons/ellipse16.gif"), 
				StateSpaceExplorerPlugin.getImageDescriptor("icons/ellipse24.gif"));
		toolbar.add(component);

		// Add (solid-line) connection tool 
		tool = new ConnectionCreationToolEntry(
				"Transition",
				"Create a transition",
				new CreationFactory() {
					public Object getNewObject() { return null; }
					public Object getObjectType() { return null; }
				},
				StateSpaceExplorerPlugin.getImageDescriptor("icons/connection_s16.gif"),
				StateSpaceExplorerPlugin.getImageDescriptor("icons/connection_s24.gif"));
		toolbar.add(tool);

		return toolbar;
	}

}