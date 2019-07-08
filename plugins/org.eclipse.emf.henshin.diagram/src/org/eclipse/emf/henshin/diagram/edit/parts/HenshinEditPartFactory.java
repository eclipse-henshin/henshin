/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.parts;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.directedit.locator.CellEditorLocatorAccess;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

/**
 * @generated
 */
public class HenshinEditPartFactory implements EditPartFactory {

	/**
	 * @generated
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (HenshinVisualIDRegistry.getVisualID(view)) {

			case ModuleEditPart.VISUAL_ID:
				return new ModuleEditPart(view);

			case RuleEditPart.VISUAL_ID:
				return new RuleEditPart(view);

			case RuleNameEditPart.VISUAL_ID:
				return new RuleNameEditPart(view);

			case UnitEditPart.VISUAL_ID:
				return new UnitEditPart(view);

			case UnitNameEditPart.VISUAL_ID:
				return new UnitNameEditPart(view);

			case NodeEditPart.VISUAL_ID:
				return new NodeEditPart(view);

			case NodeTypeEditPart.VISUAL_ID:
				return new NodeTypeEditPart(view);

			case NodeActionEditPart.VISUAL_ID:
				return new NodeActionEditPart(view);

			case AttributeEditPart.VISUAL_ID:
				return new AttributeEditPart(view);

			case AttributeConditionEditPart.VISUAL_ID:
				return new AttributeConditionEditPart(view);

			case AttributeConditionNameEditPart.VISUAL_ID:
				return new AttributeConditionNameEditPart(view);

			case AttributeConditionBodyEditPart.VISUAL_ID:
				return new AttributeConditionBodyEditPart(view);

			case SymbolEditPart.VISUAL_ID:
				return new SymbolEditPart(view);

			case InvocationEditPart.VISUAL_ID:
				return new InvocationEditPart(view);

			case InvocationNameEditPart.VISUAL_ID:
				return new InvocationNameEditPart(view);

			case RuleCompartmentEditPart.VISUAL_ID:
				return new RuleCompartmentEditPart(view);

			case NodeCompartmentEditPart.VISUAL_ID:
				return new NodeCompartmentEditPart(view);

			case UnitCompartmentEditPart.VISUAL_ID:
				return new UnitCompartmentEditPart(view);

			case EdgeEditPart.VISUAL_ID:
				return new EdgeEditPart(view);

			case EdgeTypeEditPart.VISUAL_ID:
				return new EdgeTypeEditPart(view);

			case EdgeActionEditPart.VISUAL_ID:
				return new EdgeActionEditPart(view);

			case LinkEditPart.VISUAL_ID:
				return new LinkEditPart(view);

			}
		}
		return createUnrecognizedEditPart(context, model);
	}

	/**
	 * @generated
	 */
	private EditPart createUnrecognizedEditPart(EditPart context, Object model) {
		// Handle creation of unrecognized child node EditParts here
		return null;
	}

	/**
	 * @generated
	 */
	public static CellEditorLocator getTextCellEditorLocator(ITextAwareEditPart source) {
		return CellEditorLocatorAccess.INSTANCE.getTextCellEditorLocator(source);
	}
}
