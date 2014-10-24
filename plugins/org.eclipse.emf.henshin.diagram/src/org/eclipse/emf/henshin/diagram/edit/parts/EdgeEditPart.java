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

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.diagram.edit.helpers.ColorModeHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RuleEditHelper.RuleListener;
import org.eclipse.emf.henshin.diagram.edit.policies.EdgeItemSemanticEditPolicy;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.provider.util.HenshinColorMode;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class EdgeEditPart extends ConnectionNodeEditPart implements ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 4001;

	/**
	 * @generated NOT
	 */
	private RuleListener ruleListener;

	/**
	 * @generated
	 */
	public EdgeEditPart(View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void addSemanticListeners() {
		super.addSemanticListeners();
		View view = getNotationView();
		if (view == null) {
			return;
		}
		Edge edge = (Edge) (getNotationView().getElement());
		if (edge == null) {
			return;
		}
		ruleListener = new RuleListener(edge) {
			@Override
			public void notifyChanged(Notification event) {
				super.notifyChanged(event);
				if (event.getEventType() == Notification.REMOVING_ADAPTER) {
					return;
				}
				// Really make sure that the edit part is still valid.
				if (isActive() && getNotationView().getElement() instanceof Edge && getParent() != null) {
					refreshVisuals();
				}
			}
		};
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void removeSemanticListeners() {
		super.removeSemanticListeners();
		if (ruleListener != null) {
			ruleListener.dispose();
			ruleListener = null;
		}
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new EdgeItemSemanticEditPolicy());
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof EdgeTypeEditPart) {
			((EdgeTypeEditPart) childEditPart).setLabel(getPrimaryShape().getEdgeTypeLabel());
			return true;
		}
		if (childEditPart instanceof EdgeActionEditPart) {
			((EdgeActionEditPart) childEditPart).setLabel(getPrimaryShape().getEdgeActionLabel());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, index);
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof EdgeTypeEditPart) {
			return true;
		}
		if (childEditPart instanceof EdgeActionEditPart) {
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected Connection createConnectionFigure() {
		return new EdgeFigure();
	}

	/**
	 * @generated
	 */
	public EdgeFigure getPrimaryShape() {
		return (EdgeFigure) getFigure();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void refreshForegroundColor() {
		HenshinColorMode.Color color = ColorModeHelper.getActionColor(getNotationView(), true);
		if (color != null) {
			setForegroundColor(ColorModeHelper.getSWTColor(color));
		} else {
			super.refreshForegroundColor();
		}
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void refreshVisuals() {

		// Make sure the model is there:
		Object elem = getNotationView().getElement();
		if (!(elem instanceof Edge)) {
			return;
		}

		// Make the normal refresh:
		super.refreshVisuals();

		// Set the decorations:
		Edge edge = (Edge) elem;
		EReference type = edge.getType();
		if (type != null) {

			// Source decoration:
			if (type.isContainment()) {
				getPrimaryShape().setSourceDecoration(createDiamondDecoration());
			} else if (type.getEOpposite() != null) {
				//PolylineDecoration sourceArrow = (PolylineDecoration) createArrowDecoration();
				//sourceArrow.setAlpha(96);
				//getPrimaryShape().setSourceDecoration(sourceArrow);
				getPrimaryShape().setSourceDecoration(null);
			} else {
				getPrimaryShape().setSourceDecoration(null);
			}

			// Target decoration:
			getPrimaryShape().setTargetDecoration(createArrowDecoration());

		} else {
			getPrimaryShape().setSourceDecoration(null);
			getPrimaryShape().setTargetDecoration(null);
		}
	}

	/**
	 * Create a new diamond decoration, used for containment edges.
	 * @generated NOT
	 */
	private RotatableDecoration createDiamondDecoration() {
		PolygonDecoration df = new PolygonDecoration();
		PointList pl = new PointList();
		pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(1));
		pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
		pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(-1));
		pl.addPoint(getMapMode().DPtoLP(-2), getMapMode().DPtoLP(0));
		pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(1));
		df.setTemplate(pl);
		df.setScale(getMapMode().DPtoLP(7), getMapMode().DPtoLP(3));
		return df;
	}

	/**
	 * Create a new arrow decoration.
	 * @generated NOT
	 */
	private RotatableDecoration createArrowDecoration() {
		PolylineDecoration df = new PolylineDecoration();
		df.setLineWidth(1);
		return df;
	}

	/**
	 * @generated
	 */
	public class EdgeFigure extends PolylineConnectionEx {

		/**
		 * @generated
		 */
		private WrappingLabel fEdgeTypeLabel;

		/**
		 * @generated
		 */
		private WrappingLabel fEdgeActionLabel;

		/**
		 * @generated
		 */
		public EdgeFigure() {

			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fEdgeTypeLabel = new WrappingLabel();

			fEdgeTypeLabel.setText("unknown");

			this.add(fEdgeTypeLabel);

			fEdgeActionLabel = new WrappingLabel();

			fEdgeActionLabel.setText("unknown");

			this.add(fEdgeActionLabel);

		}

		/**
		 * @generated
		 */
		public WrappingLabel getEdgeTypeLabel() {
			return fEdgeTypeLabel;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getEdgeActionLabel() {
			return fEdgeActionLabel;
		}

	}

}
