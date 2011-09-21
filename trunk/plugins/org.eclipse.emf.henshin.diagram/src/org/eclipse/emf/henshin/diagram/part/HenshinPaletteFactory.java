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
package org.eclipse.emf.henshin.diagram.part;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

/**
 * @generated
 */
public class HenshinPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createHenshin1Group());
	}

	/**
	 * Creates "Henshin" palette tool group
	 * @generated NOT
	 */
	private PaletteContainer createHenshin1Group() {
		
		// Main palette group:
		PaletteGroup paletteContainer = new PaletteGroup(
				Messages.Henshin1Group_title);
		paletteContainer.setId("createHenshin1Group"); //$NON-NLS-1$
		
		// Rules:
		paletteContainer.add(createRule1CreationTool());
		paletteContainer.add(createNodeCreationTool());
		paletteContainer.add(createEdge2CreationTool());
		paletteContainer.add(createAttribute3CreationTool());
		
		// Transformation units:
		paletteContainer.add(createUnit5CreationTool());
		paletteContainer.add(createInvocation6CreationTool());
		
		return paletteContainer;
	}

	/**
	 * 
	 */
	private ToolEntry createNodeCreationTool() {
		NodeToolEntry entry = new NodeToolEntry(Messages.NodeCreationTool_title,
				Messages.NodeCreationTool_desc,
				Collections.singletonList(HenshinElementTypes.Node_3001));
		entry.setId("createNodeCreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinElementTypes.getImageDescriptor(HenshinElementTypes.Node_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}
	
	/**
	 * @generated
	 */
	private ToolEntry createRule1CreationTool() {
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Rule1CreationTool_title,
				Messages.Rule1CreationTool_desc,
				Collections.singletonList(HenshinElementTypes.Rule_2001));
		entry.setId("createRule1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinElementTypes
				.getImageDescriptor(HenshinElementTypes.Rule_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createEdge2CreationTool() {
		LinkToolEntry entry = new LinkToolEntry(
				Messages.Edge2CreationTool_title,
				Messages.Edge2CreationTool_desc,
				Collections.singletonList(HenshinElementTypes.Edge_4001));
		entry.setId("createEdge2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinElementTypes
				.getImageDescriptor(HenshinElementTypes.Edge_4001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createAttribute3CreationTool() {
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Attribute3CreationTool_title,
				Messages.Attribute3CreationTool_desc,
				Collections.singletonList(HenshinElementTypes.Attribute_3002));
		entry.setId("createAttribute3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinElementTypes
				.getImageDescriptor(HenshinElementTypes.Attribute_3002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createUnit5CreationTool() {
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Unit5CreationTool_title,
				Messages.Unit5CreationTool_desc,
				Collections
						.singletonList(HenshinElementTypes.TransformationUnit_2002));
		entry.setId("createUnit5CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinElementTypes
				.getImageDescriptor(HenshinElementTypes.TransformationUnit_2002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInvocation6CreationTool() {
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Invocation6CreationTool_title,
				Messages.Invocation6CreationTool_desc,
				Collections
						.singletonList(HenshinElementTypes.TransformationUnit_3003));
		entry.setId("createInvocation6CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinElementTypes
				.getImageDescriptor(HenshinElementTypes.TransformationUnit_3003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private static class NodeToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List<IElementType> elementTypes;

		/**
		 * @generated
		 */
		private NodeToolEntry(String title, String description,
				List<IElementType> elementTypes) {
			super(title, description, null, null);
			this.elementTypes = elementTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	/**
	 * @generated
	 */
	private static class LinkToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List<IElementType> relationshipTypes;

		/**
		 * @generated
		 */
		private LinkToolEntry(String title, String description,
				List<IElementType> relationshipTypes) {
			super(title, description, null, null);
			this.relationshipTypes = relationshipTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}
	
}
