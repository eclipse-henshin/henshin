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

import java.util.Collections;

import org.eclipse.emf.henshin.diagram.part.HenshinPaletteTools.LongLivingLinkToolEntry;
import org.eclipse.emf.henshin.diagram.part.HenshinPaletteTools.LongLivingNodeToolEntry;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.part.DefaultLinkToolEntry;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.part.DefaultNodeToolEntry;

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
		PaletteGroup paletteContainer = new PaletteGroup(Messages.Henshin1Group_title);
		paletteContainer.setId("createHenshin1Group"); //$NON-NLS-1$

		// Rules:
		paletteContainer.add(createRule1CreationTool());
		paletteContainer.add(createNodeCreationTool());
		paletteContainer.add(createEdge2CreationTool());
		paletteContainer.add(createAttribute3CreationTool());
		paletteContainer.add(createCondition4CreationTool());

		paletteContainer.add(new PaletteSeparator());

		// Transformation units:
		paletteContainer.add(createUnit6CreationTool());
		paletteContainer.add(createInvocation7CreationTool());

		return paletteContainer;
	}

	/**
	 * @generated NOT
	 */
	private ToolEntry createNodeCreationTool() {
		// Override the default image:
		LongLivingNodeToolEntry entry = new LongLivingNodeToolEntry(Messages.NodeCreationTool_title,
				Messages.NodeCreationTool_desc, Collections.singletonList(HenshinElementTypes.Node_3001));
		entry.setId("createNodeCreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinDiagramEditorPlugin.getBundledImageDescriptor("icons/obj16/Node.png"));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createRule1CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Rule1CreationTool_title,
				Messages.Rule1CreationTool_desc, Collections.singletonList(HenshinElementTypes.Rule_2001));
		entry.setId("createRule1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinElementTypes.getImageDescriptor(HenshinElementTypes.Rule_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated NOT
	 */
	private ToolEntry createEdge2CreationTool() {
		LongLivingLinkToolEntry entry = new LongLivingLinkToolEntry(Messages.Edge2CreationTool_title,
				Messages.Edge2CreationTool_desc, Collections.singletonList(HenshinElementTypes.Edge_4001));
		entry.setId("createEdge2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinElementTypes.getImageDescriptor(HenshinElementTypes.Edge_4001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated NOT
	 */
	private ToolEntry createAttribute3CreationTool() {
		LongLivingNodeToolEntry entry = new LongLivingNodeToolEntry(Messages.Attribute3CreationTool_title,
				Messages.Attribute3CreationTool_desc, Collections.singletonList(HenshinElementTypes.Attribute_3002));
		entry.setId("createAttribute3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinElementTypes.getImageDescriptor(HenshinElementTypes.Attribute_3002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createCondition4CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Condition4CreationTool_title,
				Messages.Condition4CreationTool_desc,
				Collections.singletonList(HenshinElementTypes.AttributeCondition_3005));
		entry.setId("createCondition4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinElementTypes.getImageDescriptor(HenshinElementTypes.AttributeCondition_3005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createUnit6CreationToolGen() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Unit6CreationTool_title,
				Messages.Unit6CreationTool_desc, Collections.singletonList(HenshinElementTypes.Unit_2002));
		entry.setId("createUnit6CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinElementTypes.getImageDescriptor(HenshinElementTypes.Unit_2002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated NOT
	 */
	private ToolEntry createUnit6CreationTool() {
		// Override the default image:
		DefaultNodeToolEntry entry = (DefaultNodeToolEntry) createUnit6CreationToolGen();
		entry.setSmallIcon(HenshinDiagramEditorPlugin.getBundledImageDescriptor("icons/obj16/Unit.png"));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated NOT
	 */
	private ToolEntry createInvocation7CreationTool() {
		LongLivingNodeToolEntry entry = new LongLivingNodeToolEntry(Messages.Invocation7CreationTool_title,
				Messages.Invocation7CreationTool_desc, Collections.singletonList(HenshinElementTypes.Unit_3003));
		entry.setId("createInvocation6CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(HenshinDiagramEditorPlugin.getBundledImageDescriptor("icons/obj16/Invocation.png"));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

}
