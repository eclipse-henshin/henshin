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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.diagram.edit.helpers.ColorModeHelper;
import org.eclipse.emf.henshin.provider.util.HenshinColorMode;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.internal.text.revisions.Colors;
import org.eclipse.swt.graphics.Color;

/**
 * @generated
 */
public class SymbolEditPart extends ShapeNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3004;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public SymbolEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPoliciesGen() {
		super.createDefaultEditPolicies();
		removeEditPolicy(EditPolicyRoles.SEMANTIC_ROLE);
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void createDefaultEditPolicies() {
		createDefaultEditPoliciesGen();
		removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}

			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/*
	 * Create the node shape for this symbol.
	 * The shape depends on the symbol type.
	 */
	protected IFigure createNodeShape() {
		SymbolType symbol = SymbolType.get(getNotationView());
		if (symbol == SymbolType.UNIT_BEGIN) {
			primaryShape = new SymbolCircleFigure(true);
		} else if (symbol == SymbolType.UNIT_END) {
			primaryShape = new SymbolCircleFigure(false);
		} else if (symbol == SymbolType.INDEPENDENT_CHOICE) {
			primaryShape = new IndependentChoiceSymbolFigure();
		} else {
			primaryShape = new InvalidSymbolFigure();
		}
		return primaryShape;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {

		// We forbid deletion of symbols, unless it is corrupt:
		Object type = request.getType();
		if (RequestConstants.REQ_DELETE.equals(type) && SymbolType.get(getNotationView()) != null) {
			return UnexecutableCommand.INSTANCE;
		}

		// We also forbid to reconnect links:
		if (RequestConstants.REQ_RECONNECT_SOURCE.equals(type) || RequestConstants.REQ_RECONNECT_TARGET.equals(type)) {
			return UnexecutableCommand.INSTANCE;
		}

		// Everything else is ok:
		return super.getCommand(request);
	}

	/**
	 * Get the primary shape of this edit part.
	 * @generated
	 */
	public SymbolCircleFigure getPrimaryShape() {
		return (SymbolCircleFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(20, 20);
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	protected void setForegroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setForegroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setBackgroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setBackgroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineWidth(int width) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineWidth(width);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineType(int style) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineStyle(style);
		}
	}

	@Override
	public void refreshForegroundColor() {
		HenshinColorMode.Color color = ColorModeHelper.getColor(getNotationView(), HenshinColorMode.FG_UNIT);
		if (color != null) {
			setForegroundColor(ColorModeHelper.getSWTColor(color));
		} else {
			super.refreshForegroundColor();
		}
	}

	@Override
	public void refreshBackgroundColor() {
		HenshinColorMode.Color color = ColorModeHelper.getColor(getNotationView(), HenshinColorMode.BG_UNIT);
		if (color != null) {
			setBackgroundColor(ColorModeHelper.getSWTColor(color));
		} else {
			super.refreshBackgroundColor();
		}
	}

	/**
	 * @generated NOT
	 */
	public class SymbolCircleFigure extends Ellipse {

		private Ellipse inner;

		public SymbolCircleFigure() {
			super();
		}

		public SymbolCircleFigure(boolean begin) {
			super();
			setLayoutManager(new StackLayout());
			if (!begin) {
				final Ellipse main = this;
				inner = new Ellipse() {
					@Override
					public Rectangle getBounds() {
						Rectangle b = main.getBounds();
						return new Rectangle(b.x + b.width / 4, b.y + b.height / 4, b.width / 2, b.height / 2);
					}
				};
				add(inner);
			}
		}

		@Override
		public void setForegroundColor(Color color) {
			super.setForegroundColor(color);
			if (inner != null) {
				inner.setForegroundColor(color);
				inner.setBackgroundColor(color);
			} else {
				setBackgroundColor(color);
			}
		}

		@Override
		public void setBackgroundColor(Color color) {
			//			if (inner == null) {
			super.setBackgroundColor(color);
			//			}
		}
	}

	/*
	 * A figure for invalid symbols.
	 */
	public class InvalidSymbolFigure extends Ellipse {

		public InvalidSymbolFigure() {
			setLayoutManager(new StackLayout());
			add(new Label("  Invalid  "));
		}

		@Override
		public void setBackgroundColor(Color color) {
			super.setBackgroundColor(ColorConstants.red);
		}
	}

	/*
	 * A figure for independent choices.
	 */
	public class IndependentChoiceSymbolFigure extends Figure {

		public IndependentChoiceSymbolFigure() {
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.draw2d.Figure#paint(org.eclipse.draw2d.Graphics)
		 */
		@Override
		public void paint(Graphics graphics) {

			graphics.setForegroundColor(getForegroundColor());
			graphics.setBackgroundColor(getBackgroundColor());
			graphics.setLineWidthFloat(1.0f);

			Rectangle r = getBounds();

			// We want to draw a diamond:
			Point p1 = new Point(r.x, r.y + r.height / 2);
			Point p2 = new Point(r.x + r.width / 2, r.y);
			Point p3 = new Point(r.x + r.width - 1, r.y + r.height / 2);
			Point p4 = new Point(r.x + r.width / 2, r.y + r.height - 1);

			PointList pointList = new PointList();
			pointList.addPoint(p1);
			pointList.addPoint(p2);
			pointList.addPoint(p3);
			pointList.addPoint(p4);

			// Fill the shape
			graphics.fillPolygon(pointList);

			// Draw the outline
			graphics.drawLine(p1, p2);
			graphics.drawLine(p2, p3);
			graphics.drawLine(p3, p4);
			graphics.drawLine(p4, p1);

		}

	}

}
