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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeEditPart.NodeFigure;
import org.eclipse.emf.henshin.diagram.edit.policies.NodeCompartmentCanonicalEditPolicy;
import org.eclipse.emf.henshin.diagram.edit.policies.NodeCompartmentItemSemanticEditPolicy;
import org.eclipse.emf.henshin.diagram.part.Messages;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.OneLineBorder;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class NodeCompartmentEditPart extends ListCompartmentEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 7002;

	/**
	 * @generated
	 */
	public NodeCompartmentEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	public String getCompartmentName() {
		return Messages.NodeCompartmentEditPart_title;
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected boolean hasModelChildrenChanged(Notification evt) {
		return true;
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected boolean modeAutomatic() {
		return true;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void refresh() {
		super.refresh();
		for (Object child : getChildren()) {
			// Always refresh the attribute edit parts:
			if (child instanceof AttributeEditPart) {
				((AttributeEditPart) child).refresh();
			}
		}
	}

	/*
	 * Helper method for determining whether a shadow should be drawn.
	 */
	private boolean shouldDrawShadow() {
		return ((NodeEditPart) getParent()).shouldDrawShadow();
	}

	/**
	 * @generated NOT
	 */
	public IFigure createFigure() {
		ResizableCompartmentFigure figure = (ResizableCompartmentFigure) createFigureGen();
		figure.setBorder(new OneLineBorder(getMapMode().DPtoLP(1),
				PositionConstants.TOP) {
			@Override
			public void paint(IFigure figure, Graphics graphics, Insets insets) {
				tempRect.setBounds(getPaintRectangle(figure, insets));
				int one = MapModeUtil.getMapMode(figure).DPtoLP(1);
				int widthInDP = getWidth() / one;
				int halfWidthInLP = MapModeUtil.getMapMode(figure).DPtoLP(
						widthInDP / 2);
				graphics.setLineWidth(getWidth());
				graphics.setLineStyle(getStyle());
				if (getColor() != null) {
					graphics.setForegroundColor(getColor());
				}
				tempRect.y += halfWidthInLP;
				tempRect.height -= getWidth();
				if (shouldDrawShadow()) { // check if there should be a shadow
					tempRect.width = tempRect.width - NodeFigure.SHADOW_WIDTH
							- 1;
				}
				graphics.drawLine(tempRect.getTopLeft(), tempRect.getTopRight());
			}
		});
		return figure;
	}

	/**
	 * @generated
	 */
	public IFigure createFigureGen() {
		ResizableCompartmentFigure result = (ResizableCompartmentFigure) super
				.createFigure();
		result.setTitleVisibility(false);
		return result;
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new NodeCompartmentItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CREATION_ROLE,
				new CreationEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
				new DragDropEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new NodeCompartmentCanonicalEditPolicy());
	}

	/**
	 * @generated
	 */
	protected void setRatio(Double ratio) {
		// nothing to do -- parent layout does not accept Double constraints as ratio
		// super.setRatio(ratio); 
	}

}
