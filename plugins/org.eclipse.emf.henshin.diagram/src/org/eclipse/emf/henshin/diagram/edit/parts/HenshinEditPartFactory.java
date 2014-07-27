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
	public static CellEditorLocator getTextCellEditorLocator(
			ITextAwareEditPart source) {
		if (source.getFigure() instanceof WrappingLabel)
			return new TextCellEditorLocator((WrappingLabel) source.getFigure());
		else {
			return new LabelCellEditorLocator((Label) source.getFigure());
		}
	}

	/**
	 * @generated
	 */
	static private class TextCellEditorLocator implements CellEditorLocator {

		/**
		 * @generated
		 */
		private WrappingLabel wrapLabel;

		/**
		 * @generated
		 */
		public TextCellEditorLocator(WrappingLabel wrapLabel) {
			this.wrapLabel = wrapLabel;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getWrapLabel() {
			return wrapLabel;
		}

		/**
		 * @generated NOT
		 */
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			// Make sure the text and its font are not disposed already.
			if (!text.isDisposed() && !text.getFont().isDisposed()) {
				relocateGen(celleditor);
			}
		}

		/**
		 * @generated
		 */
		public void relocateGen(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getWrapLabel().getTextBounds().getCopy();
			getWrapLabel().translateToAbsolute(rect);
			if (!text.getFont().isDisposed()) {
				if (getWrapLabel().isTextWrapOn()
						&& getWrapLabel().getText().length() > 0) {
					rect.setSize(new Dimension(text.computeSize(rect.width,
							SWT.DEFAULT)));
				} else {
					int avr = FigureUtilities.getFontMetrics(text.getFont())
							.getAverageCharWidth();
					rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT,
							SWT.DEFAULT)).expand(avr * 2, 0));
				}
			}
			if (!rect.equals(new Rectangle(text.getBounds()))) {
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}
	}

	/**
	 * @generated
	 */
	private static class LabelCellEditorLocator implements CellEditorLocator {

		/**
		 * @generated
		 */
		private Label label;

		/**
		 * @generated
		 */
		public LabelCellEditorLocator(Label label) {
			this.label = label;
		}

		/**
		 * @generated
		 */
		public Label getLabel() {
			return label;
		}

		/**
		 * @generated
		 */
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getLabel().getTextBounds().getCopy();
			getLabel().translateToAbsolute(rect);
			if (!text.getFont().isDisposed()) {
				int avr = FigureUtilities.getFontMetrics(text.getFont())
						.getAverageCharWidth();
				rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT,
						SWT.DEFAULT)).expand(avr * 2, 0));
			}
			if (!rect.equals(new Rectangle(text.getBounds()))) {
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}
	}
}
