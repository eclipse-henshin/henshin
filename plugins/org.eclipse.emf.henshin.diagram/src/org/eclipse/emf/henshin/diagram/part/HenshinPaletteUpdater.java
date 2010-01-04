package org.eclipse.emf.henshin.diagram.part;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.editor.actions.EcoreSelectionDialogUtil;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Monitors the imports of a transformation system and 
 * updates the palette of a Henshin diagram editor.
 * @generated NOT
 * @author Christian Krause
 */
public class HenshinPaletteUpdater {
	
	/**
	 * Package icon.
	 */
	private static ImageDescriptor PACKAGE_ICON = ImageDescriptor.createFromImage(EcoreSelectionDialogUtil.PACKAGE_ICON);
	
	// Palette root to be updated.
	private PaletteRoot palette;
	
	// Transformation system to be monitored.
	private TransformationSystem system;
	
	// Palette drawers for the packages.
	private HashMap<EPackage,PaletteDrawer> drawers;
	
	/**
	 * Default constructor.
	 * @param palette Palette root.
	 * @param system Transformation system.
	 */
	public HenshinPaletteUpdater(PaletteRoot palette, TransformationSystem system) {
		this.palette = palette;
		this.system = system;
		this.drawers = new HashMap<EPackage,PaletteDrawer>();
		system.eAdapters().add(listener);
		refresh();
	}
	
	public void refresh() {
		
		// Remove all drawers first:
		for (PaletteDrawer drawer : drawers.values()) {
			palette.remove(drawer);
		}
		drawers.clear();
		
		// Create a new drawer for every package:
		for (EPackage epackage : system.getImports()) {
			PaletteDrawer drawer = new PaletteDrawer(epackage.getName(), PACKAGE_ICON);
			palette.add(drawer);
			drawers.put(epackage, drawer);
		}
		
	}
	
	public void dispose() {
		system.eAdapters().remove(listener);
	}
	
	/*
	 * Transformation system listener.
	 */
	private Adapter listener = new AdapterImpl() {
		public void notifyChanged(Notification event) {
			int featureID = event.getFeatureID(TransformationSystem.class);
			if (featureID==HenshinPackage.TRANSFORMATION_SYSTEM__IMPORTS) {
				refresh();
			}
		}
	};
	
	
	static class EClassNodeToolEntry extends ToolEntry {
		
		// Element types: Henshin node
		private static final List<IElementType> types;
		static {
			types = new ArrayList<IElementType>(1);
			types.add(HenshinElementTypes.Node_3001);
		}
		
		// EClass:
		private EClass eclass;

		public EClassNodeToolEntry(EClass eclass) {
			super(eclass.getName(), "Create a " + eclass.getName() + " node", null, null);
			this.eclass = eclass;
		}
		
		@Override
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeCreationTool(types);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

}
