/*
 * Copyright (c) 2010 HPI Potsdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     HPI Potsdam - initial API and implementation
 */
package org.eclipse.emf.henshin.diagram.edit.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
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
public class LinkEditPart extends ConnectionNodeEditPart implements
		ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 4002;

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
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ViewComponentEditPolicy());
		removeEditPolicy(EditPolicyRoles.SEMANTIC_ROLE);
	}
	
	/*
	 * Update the arrow of the line depending on the
	 * type of transformation unit this link belongs to.
	 */
	private void updateArrow(LinkFigure figure) {
		if (figure!=null) {
			TransformationUnit unit = getTransformationUnit();
			figure.setTargetDecoration(null);	// must be done here because child is cached
			figure.removeAll();
			if (unit instanceof PriorityUnit) {
				figure.add(createArrowDecoration(4, 7), new MiddleLocator(figure));
			} else {
				figure.setTargetDecoration(createArrowDecoration(7, 3));				
			}
		}
	}
	
	/*
	 * Get the transformation unit which this link belong to.
	 */
	private TransformationUnit getTransformationUnit() {
		
		// Get the source node of this link:
		View sourceNode = ((Edge) getNotationView()).getSource();
		if (sourceNode==null) {
			return null;
		}
		
		// Get the compartment where the node is contained in:
		View compartment = (View) sourceNode.eContainer();
		if (compartment==null) {
			return null;
		}
		
		// Get the view of the transformation unit:
		View unitView = (View) compartment.eContainer();
		if (unitView==null) {
			return null;
		}
		
		// Now we can access the unit:
		if (unitView.getElement() instanceof TransformationUnit) {
			return (TransformationUnit) unitView.getElement();
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
		if (RequestConstants.REQ_DELETE.equals(type) ||
			RequestConstants.REQ_RECONNECT_SOURCE.equals(type) ||
			RequestConstants.REQ_RECONNECT_TARGET.equals(type)) {
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
		updateArrow(linkFigure);
		return linkFigure;
	}

	/**
	 * @generated
	 */
	public LinkFigure getPrimaryShape() {
		return (LinkFigure) getFigure();
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

		public MiddleLocator(Connection arg0) {
			super(arg0);
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
}
