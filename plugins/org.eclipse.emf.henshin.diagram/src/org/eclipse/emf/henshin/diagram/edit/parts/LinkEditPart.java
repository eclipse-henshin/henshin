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
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.henshin.diagram.edit.helpers.ColorModeHelper;
import org.eclipse.emf.henshin.diagram.part.HenshinLinkUpdater;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.provider.util.HenshinColorMode;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ViewComponentEditPolicy;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class LinkEditPart extends ConnectionNodeEditPart implements ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 4002;

	// Arrow decoration:
	private RotatableDecoration arrow;

	// Optional label:
	private Label label;

	/**
	 * @generated
	 */
	public LinkEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ViewComponentEditPolicy());
		removeEditPolicy(EditPolicyRoles.SEMANTIC_ROLE);
	}

	/*
	 * Update the arrow of the line depending on the
	 * type of transformation unit this link belongs to.
	 */
	private void updateArrow(LinkFigure figure) {
		if (figure != null) {
			Unit unit = getUnit();
			figure.setTargetDecoration(null); // must be done here because child is cached
			if (arrow != null) {
				if (figure.getChildren().contains(arrow)) {
					figure.remove(arrow);
				}
				arrow = null;
			}
			if (unit instanceof PriorityUnit) {
				figure.add(arrow = createArrowDecoration(4, 7), new MiddleLocator(figure));
			} else {
				figure.setTargetDecoration(arrow = createArrowDecoration(7, 3));
			}
		}
	}

	/*
	 * Update the label of the link.
	 */
	private void updateLabel(LinkFigure figure) {
		if (figure != null) {
			if (label != null) {
				figure.remove(label);
				label = null;
			}
			if (HenshinLinkUpdater.isIfLink(getUnit(), getNotationView())) {
				figure.add(label = new Label("if"), new LabelLocator(figure, 0, -10));
			} else if (HenshinLinkUpdater.isThenLink(getUnit(), getNotationView())) {
				figure.add(label = new Label("then"), new LabelLocator(figure, 10, -10));
			} else if (HenshinLinkUpdater.isElseLink(getUnit(), getNotationView())) {
				figure.add(label = new Label("else"), new LabelLocator(figure, 10, 10));
			}
		}
	}

	/*
	 * Get the transformation unit which this link belong to.
	 */
	private Unit getUnit() {

		// Get the source node of this link:
		View sourceNode = ((Edge) getNotationView()).getSource();
		if (sourceNode == null) {
			return null;
		}

		// Get the compartment where the node is contained in:
		View compartment = (View) sourceNode.eContainer();
		if (compartment == null) {
			return null;
		}

		// Get the view of the transformation unit:
		View unitView = (View) compartment.eContainer();
		if (unitView == null) {
			return null;
		}

		// Now we can access the unit:
		if (unitView.getElement() instanceof Unit) {
			return (Unit) unitView.getElement();
		} else {
			return null;
		}
	}

	/*
	 * Create an arrow decoration.
	 */
	private RotatableDecoration createArrowDecoration(int length, int width) {
		PolygonDecoration df = new PolygonDecoration();
		df.setFill(true);
		PointList pl = new PointList();
		pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(1));
		pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
		pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(-1));
		df.setTemplate(pl);
		df.setScale(getMapMode().DPtoLP(length), getMapMode().DPtoLP(width));
		return df;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {

		// We forbid to delete or reconnect links:
		Object type = request.getType();
		if (RequestConstants.REQ_DELETE.equals(type) || RequestConstants.REQ_RECONNECT_SOURCE.equals(type)
				|| RequestConstants.REQ_RECONNECT_TARGET.equals(type)) {
			return UnexecutableCommand.INSTANCE;
		}

		// Everything else should be ok:
		return super.getCommand(request);

	}

	/**
	 * Creates figure for this edit part.
	 * @generated NOT
	 */
	protected Connection createConnectionFigure() {
		LinkFigure linkFigure = new LinkFigure();
		linkFigure.setTargetDecoration(null);
		updateArrow(linkFigure);
		updateLabel(linkFigure);
		return linkFigure;
	}

	/**
	 * @generated
	 */
	public LinkFigure getPrimaryShape() {
		return (LinkFigure) getFigure();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void refreshForegroundColor() {
		HenshinColorMode.Color color = ColorModeHelper.getColor(getNotationView(), HenshinColorMode.FG_UNIT);
		if (color != null) {
			setForegroundColor(ColorModeHelper.getSWTColor(color));
		} else {
			super.refreshForegroundColor();
		}
	}

	/**
	 * @generated
	 */
	public class LinkFigure extends PolylineConnectionEx {

		/**
		 * @generated
		 */
		public LinkFigure() {
			this.setForegroundColor(ColorConstants.black);

			setTargetDecoration(createTargetDecoration());
		}

		/**
		 * @generated
		 */
		private RotatableDecoration createTargetDecoration() {
			PolygonDecoration df = new PolygonDecoration();
			df.setFill(true);
			PointList pl = new PointList();
			pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(1));
			pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
			pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(-1));
			df.setTemplate(pl);
			df.setScale(getMapMode().DPtoLP(7), getMapMode().DPtoLP(3));
			return df;
		}

	}

	/*
	 * A locator which places a decoration in the middle of the line. 
	 */
	private class MiddleLocator extends ConnectionLocator {

		public MiddleLocator(Connection connection) {
			super(connection);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.draw2d.AbstractLocator#relocate(org.eclipse.draw2d.IFigure)
		 */
		@Override
		public void relocate(IFigure target) {
			PointList points = getConnection().getPoints();
			RotatableDecoration box = (RotatableDecoration) target;
			box.setLocation(getLocation(points));
			int midPoint = points.size() / 2;
			box.setReferencePoint(points.getPoint(midPoint - 1));
		}

	}

	/*
	 * Private label locator class.
	 */
	private class LabelLocator extends ConnectionLocator {

		// Distance from the mid point.
		private int x, y;

		public LabelLocator(Connection connection, int x, int y) {
			super(connection);
			this.x = x;
			this.y = y;
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.draw2d.ConnectionLocator#getReferencePoint()
		 */
		@Override
		protected Point getReferencePoint() {
			Connection connection = getConnection();
			Point p = connection.getPoints().getMidpoint();
			connection.translateToAbsolute(p);
			p.x += x;
			p.y += y;
			return p;
		}

	}

}
