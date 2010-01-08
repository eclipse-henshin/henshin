package org.eclipse.emf.henshin.diagram.part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.diagram.edit.commands.NodeCreateCommand;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.presentation.HenshinIcons;
import org.eclipse.gef.Request;
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
	
	// EPackage icon:
	private static ImageDescriptor EPACKAGE_ICON = ImageDescriptor.createFromImage(HenshinIcons.EPACKAGE_ICON);
	
	// EClass icon:
	private static ImageDescriptor ECLASS_ICON = ImageDescriptor.createFromImage(HenshinIcons.ECLASS_ICON);
	
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
			PaletteDrawer drawer = new PaletteDrawer(epackage.getName(), EPACKAGE_ICON);
			
			// Add entries for all classes:
			List<EClassifier> eclassifiers = new ArrayList<EClassifier>(epackage.getEClassifiers());
			Collections.sort(eclassifiers, eclassComparator);
			for (EClassifier eclassifier : eclassifiers){
				if (eclassifier instanceof EClass) {
					drawer.add(new EClassNodeToolEntry((EClass) eclassifier));
				}
			}
			
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
	
	
	/*
	 * A comparator for EClasses.
	 */
	private Comparator<EClassifier> eclassComparator = new Comparator<EClassifier>() {
		public int compare(EClassifier c1, EClassifier c2) {
			String n1 = c1.getName()!=null ? c1.getName() : "";
			String n2 = c2.getName()!=null ? c2.getName() : "";
			return n1.compareTo(n2);
		}
	};
	
	/*
	 * Creation palette tool entry for nodes.
	 */
	static class EClassNodeToolEntry extends ToolEntry {
				
		// EClass:
		private EClass eclass;

		public EClassNodeToolEntry(EClass eclass) {
			super(eclass.getName(), "Create a " + eclass.getName() + " node", ECLASS_ICON, null);
			this.eclass = eclass;
		}
		
		@Override
		public Tool createTool() {
			Tool tool = new EClassNodeTool(eclass);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}
	
	/*
	 * Creation tool for nodes.
	 */
	static class EClassNodeTool extends UnspecifiedTypeCreationTool {

		// Element types: Henshin node
		private static final List<IElementType> types;
		static {
			types = new ArrayList<IElementType>(1);
			types.add(HenshinElementTypes.Node_3001);
		}
		
		// Request parameters.
		private Map<Object,Object> parameters = new HashMap<Object,Object>();
		
		public EClassNodeTool(EClass eclass) {
			super(types);
			parameters.put(NodeCreateCommand.TYPE_PARAMETER_KEY, eclass);
		}
		
		@Override
		protected Request createTargetRequest() {
			Request request = super.createTargetRequest();
			request.setExtendedData(parameters);
			return request;
		}

	}
	
}
