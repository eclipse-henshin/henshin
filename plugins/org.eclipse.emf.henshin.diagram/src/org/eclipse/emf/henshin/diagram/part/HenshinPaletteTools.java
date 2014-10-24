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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.presentation.HenshinIcons;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.events.KeyEvent;

/**
 * Additional palette tools for the graphical Henshin editor.
 * @author Christian Krause
 */
public class HenshinPaletteTools {

	// EClass icon:
	public static ImageDescriptor ECLASS_ICON = ImageDescriptor.createFromImage(HenshinIcons.ECLASS);
	
	// EPackage icon:
	public static ImageDescriptor EPACKAGE_ICON = ImageDescriptor.createFromImage(HenshinIcons.EPACKAGE);
	
	/**
	 * A long-living creation tool.
	 */
	public static class LongLivingCreationTool extends UnspecifiedTypeCreationTool {

		public LongLivingCreationTool(List<?> elementTypes) {
			super(elementTypes);
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool#handleFinished()
		 */
		@Override
		protected void handleFinished() {
			reactivate();  // keep the currently active tool
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.gef.tools.CreationTool#handleButtonDown(int)
		 */
		@Override
		protected boolean handleButtonDown(int button) {
			if (getCommand()==null) {
				super.handleFinished();
			}
			return super.handleButtonDown(button);
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool#handleKeyUp(org.eclipse.swt.events.KeyEvent)
		 */
		@Override
		protected boolean handleKeyUp(KeyEvent e) {
			return false;
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool#selectAddedObject(org.eclipse.gef.EditPartViewer, java.util.Collection)
		 */
		@SuppressWarnings("rawtypes")
		@Override
		protected void selectAddedObject(EditPartViewer viewer, Collection objects) {
			// Workaround: Unhandled event loop exception: An EditPart has to be selectable (isSelectable() == true) in order to get selected.
			try {
				super.selectAddedObject(viewer, objects);
			} catch (Throwable t) {
				// don't complain
			}
		}

	}

	/**
	 * A long-living connection tool.
	 */
	public static class LongLivingConnectionTool extends UnspecifiedTypeConnectionTool {

		public LongLivingConnectionTool(List<?> elementTypes) {
			super(elementTypes);
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool#handleFinished()
		 */
		@Override
		protected void handleFinished() {
			reactivate();  // keep the currently active tool
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.gef.tools.CreationTool#handleButtonDown(int)
		 */
		@Override
		protected boolean handleButtonDown(int button) {
			if (getCommand()==null) {
				super.handleFinished();
			}
			return super.handleButtonDown(button);
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool#handleKeyUp(org.eclipse.swt.events.KeyEvent)
		 */
		@Override
		protected boolean handleKeyUp(KeyEvent e) {
			return false;
		}

	}

	public static class LongLivingNodeToolEntry extends ToolEntry {

		private final List<IElementType> elementTypes;

		public LongLivingNodeToolEntry(String title, String description, List<IElementType> elementTypes) {
			super(title, description, null, null);
			this.elementTypes = elementTypes;
		}

		public Tool createTool() {
			Tool tool = new LongLivingCreationTool(elementTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}
	
	public static class LongLivingLinkToolEntry extends ToolEntry {

		private final List<IElementType> relationshipTypes;

		public LongLivingLinkToolEntry(String title, String description, List<IElementType> relationshipTypes) {
			super(title, description, null, null);
			this.relationshipTypes = relationshipTypes;
		}

		public Tool createTool() {
			Tool tool = new LongLivingConnectionTool(relationshipTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	/**
	 * Creation tool for nodes.
	 */
	public static class EClassNodeTool extends LongLivingCreationTool {
		
		/**
		 * Key for the node type parameter in creation requests.
		 */
		public static final String TYPE_PARAMETER_KEY = "eclass_node_type";
	
		// Request parameters.
		private Map<Object,Object> parameters = new HashMap<Object,Object>();
		
		public EClassNodeTool(EClass eclass, List<IElementType> types) {
			super(types);
			parameters.put(TYPE_PARAMETER_KEY, eclass);
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool#createTargetRequest()
		 */
		@Override
		protected Request createTargetRequest() {
			Request request = super.createTargetRequest();
			request.setExtendedData(parameters);
			return request;
		}
		
	}

	/**
	 * Creation palette tool entry for nodes.
	 */
	public static class EClassNodeToolEntry extends ToolEntry {
				
		// EClass:
		private EClass eclass;
		
		// Element types: Henshin node
		private static final List<IElementType> HENSHIN_NODE_TYPES;
		static {
			HENSHIN_NODE_TYPES = new ArrayList<IElementType>(1);
			HENSHIN_NODE_TYPES.add(HenshinElementTypes.Node_3001);
		}
	
		public EClassNodeToolEntry(EClass eclass) {
			super(eclass.getName(), "Create a " + eclass.getName() + " node", ECLASS_ICON, null);
			this.eclass = eclass;
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.gef.palette.ToolEntry#createTool()
		 */
		@Override
		public Tool createTool() {
			Tool tool = new EClassNodeTool(eclass, HENSHIN_NODE_TYPES);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

}
