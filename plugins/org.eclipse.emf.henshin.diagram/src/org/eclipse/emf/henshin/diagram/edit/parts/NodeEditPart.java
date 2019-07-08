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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.diagram.edit.helpers.ColorModeHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RuleEditHelper.RuleListener;
import org.eclipse.emf.henshin.diagram.edit.policies.HenshinTextSelectionEditPolicy;
import org.eclipse.emf.henshin.diagram.edit.policies.NodeGraphicalEditPolicy;
import org.eclipse.emf.henshin.diagram.edit.policies.NodeItemSemanticEditPolicy;
import org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.provider.util.HenshinColorMode;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.edit.policies.reparent.CreationEditPolicyWithCustomReparent;
import org.eclipse.swt.graphics.Color;

/**
 * @generated
 */
public class NodeEditPart extends ShapeNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3001;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated NOT
	 */
	private RuleListener ruleListener;

	/**
	 * @generated
	 */
	public NodeEditPart(View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void addSemanticListeners() {
		super.addSemanticListeners();
		Node node = (Node) (getNotationView().getElement());
		ruleListener = new RuleListener(node) {
			@Override
			public void notifyChanged(Notification event) {
				super.notifyChanged(event);
				if (event.getEventType() == Notification.REMOVING_ADAPTER) {
					return;
				}
				// Really make sure that the edit part is still valid.
				if (isActive() && getNotationView().getElement() instanceof Node && getParent() != null) {
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
	 * Refresh the action labels and colors.
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void refreshVisuals() {
		super.refreshVisuals();

		// Also refresh the the action label:
		IGraphicalEditPart actionLabel = getChildBySemanticHint(String.valueOf(NodeActionEditPart.VISUAL_ID));
		if (actionLabel instanceof NodeActionEditPart) {
			actionLabel.refresh();
		}

		// And all source and target connections:
		List<?> cons = new ArrayList<Object>();
		cons.addAll(getSourceConnections());
		cons.addAll(getTargetConnections());
		for (Object con : cons) {
			if (con instanceof EdgeEditPart) {
				((EdgeEditPart) con).refresh();
			}
		}
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
	public void refreshBackgroundColor() {
		HenshinColorMode.Color color = ColorModeHelper.getActionColor(getNotationView(), false);
		if (color != null) {
			setBackgroundColor(ColorModeHelper.getSWTColor(color));
		} else {
			super.refreshBackgroundColor();
		}
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void createDefaultEditPolicies() {
		createDefaultEditPoliciesGen();

		// Install a custom graphical node edit policy:
		removeEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE);
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new NodeGraphicalEditPolicy());

		// Remove connection handles:
		removeEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE);

	}

	/**
	 * Helper method for determining whether a shadow should be drawn.
	 * @return Whether to draw a shadow or not.
	 */
	public boolean shouldDrawShadow() {
		Action action = getNodeAction();
		return (action != null) && (action.isMulti());
	}

	/**
	 * Get the action associated to this node.
	 * @return Action.
	 */
	public Action getNodeAction() {
		Node node = getNode();
		if (node != null) {
			return node.getAction();
		} else {
			return null;
		}
	}

	/**
	 * Get the node for this edit part (model element).
	 * @return The node.
	 */
	public Node getNode() {
		return (Node) getNotationView().getElement();
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPoliciesGen() {
		installEditPolicy(EditPolicyRoles.CREATION_ROLE,
				new CreationEditPolicyWithCustomReparent(HenshinVisualIDRegistry.TYPED_INSTANCE));
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new NodeItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {

		ConstrainedToolbarLayoutEditPolicy lep = new ConstrainedToolbarLayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				if (child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE) == null) {
					if (child instanceof ITextAwareEditPart) {
						return new HenshinTextSelectionEditPolicy();
					}
				}
				return super.createChildEditPolicy(child);
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		return primaryShape = new NodeFigure();
	}

	/**
	 * @generated
	 */
	public NodeFigure getPrimaryShape() {
		return (NodeFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof NodeTypeEditPart) {
			((NodeTypeEditPart) childEditPart).setLabel(getPrimaryShape().getNodeTypeLabel());
			return true;
		}
		if (childEditPart instanceof NodeActionEditPart) {
			((NodeActionEditPart) childEditPart).setLabel(getPrimaryShape().getNodeActionLabel());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof NodeTypeEditPart) {
			return true;
		}
		if (childEditPart instanceof NodeActionEditPart) {
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
		super.addChildVisual(childEditPart, -1);
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
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {
		return getContentPane();
	}

	/**
	 * @generated
	 */
	protected org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(40, 40);
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
	protected org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure createNodeFigure() {
		org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure figure = createNodePlate();
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
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(5);
			nodeShape.setLayoutManager(layout);
		}
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

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(HenshinVisualIDRegistry.getType(NodeTypeEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public EditPart getTargetEditPart(Request request) {
		if (request instanceof CreateViewAndElementRequest) {
			CreateElementRequestAdapter adapter = ((CreateViewAndElementRequest) request).getViewAndElementDescriptor()
					.getCreateElementRequestAdapter();
			IElementType type = (IElementType) adapter.getAdapter(IElementType.class);
			if (type == HenshinElementTypes.Attribute_3002) {
				return getChildBySemanticHint(HenshinVisualIDRegistry.getType(NodeCompartmentEditPart.VISUAL_ID));
			}
		}
		return super.getTargetEditPart(request);
	}

	/**
	 * @generated
	 */
	public class NodeFigure extends RectangleFigure {

		/**
		 * @generated
		 */
		private WrappingLabel fNodeActionLabel;

		/**
		 * @generated
		 */
		private WrappingLabel fNodeTypeLabel;

		/**
		 * @generated
		 */
		public NodeFigure() {

			ToolbarLayout layoutThis = new ToolbarLayout();
			layoutThis.setStretchMinorAxis(true);
			layoutThis.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);

			layoutThis.setSpacing(0);
			layoutThis.setVertical(true);

			this.setLayoutManager(layoutThis);

			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fNodeActionLabel = new WrappingLabel();

			fNodeActionLabel.setText("none");

			fNodeActionLabel.setBorder(new MarginBorder(getMapMode().DPtoLP(0), getMapMode().DPtoLP(2),
					getMapMode().DPtoLP(0), getMapMode().DPtoLP(2)));

			this.add(fNodeActionLabel);

			fNodeTypeLabel = new WrappingLabel();

			fNodeTypeLabel.setText("Node");

			fNodeTypeLabel.setBorder(new MarginBorder(getMapMode().DPtoLP(0), getMapMode().DPtoLP(2),
					getMapMode().DPtoLP(0), getMapMode().DPtoLP(2)));

			this.add(fNodeTypeLabel);

		}

		/**
		 * @generated
		 */
		public WrappingLabel getNodeActionLabel() {
			return fNodeActionLabel;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getNodeTypeLabel() {
			return fNodeTypeLabel;
		}

		// Shadow width.
		public static final int SHADOW_WIDTH = 5;

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
		 */
		@Override
		public Dimension getPreferredSize(int wHint, int hHint) {
			Dimension pref = super.getPreferredSize(wHint, hHint);
			if (shouldDrawShadow()) {
				pref.width += SHADOW_WIDTH;
				pref.height += SHADOW_WIDTH;
			}
			return pref;
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.draw2d.RectangleFigure#fillShape(org.eclipse.draw2d.Graphics)
		 */
		@Override
		protected void fillShape(Graphics graphics) {
			if (shouldDrawShadow()) {
				Rectangle r = Rectangle.SINGLETON.setBounds(getBounds());
				graphics.fillRectangle(r.x, r.y, r.width - SHADOW_WIDTH, r.height - SHADOW_WIDTH);
				graphics.fillRectangle(r.x + SHADOW_WIDTH, r.y + SHADOW_WIDTH, r.width, r.height);
			} else {
				super.fillShape(graphics);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.draw2d.RectangleFigure#outlineShape(org.eclipse.draw2d.Graphics)
		 */
		@Override
		protected void outlineShape(Graphics graphics) {
			if (shouldDrawShadow()) {
				float lineInset = Math.max(1.0f, getLineWidthFloat()) / 2.0f;
				int inset1 = (int) Math.floor(lineInset);
				int inset2 = (int) Math.ceil(lineInset);
				Rectangle r = Rectangle.SINGLETON.setBounds(getBounds());
				r.x += inset1;
				r.y += inset1;
				r.width -= inset1 + inset2;
				r.height -= inset1 + inset2;
				graphics.drawRectangle(r.x, r.y, r.width - SHADOW_WIDTH, r.height - SHADOW_WIDTH);

				// Now draw the shadow:
				graphics.setForegroundColor(getShadowColor());
				graphics.drawLine(r.x + SHADOW_WIDTH, r.y + r.height - SHADOW_WIDTH, r.x + SHADOW_WIDTH,
						r.y + r.height);
				graphics.drawLine(r.x + r.width - SHADOW_WIDTH, r.y + SHADOW_WIDTH, r.x + r.width, r.y + SHADOW_WIDTH);
				graphics.drawLine(r.x + SHADOW_WIDTH, r.y + r.height, r.x + r.width, r.y + r.height);
				graphics.drawLine(r.x + r.width, r.y + SHADOW_WIDTH, r.x + r.width, r.y + r.height);
				graphics.setForegroundColor(getForegroundColor());
			} else {
				super.outlineShape(graphics);
			}
		}

		/**
		 * Get the foreground color for the shadow.
		 * @return shadow color.
		 */
		public Color getShadowColor() {
			Color c = getForegroundColor();
			return new Color(c.getDevice(), Math.min(255, c.getRed() + ((255 - c.getRed()) / 2)),
					Math.min(255, c.getGreen() + ((255 - c.getGreen()) / 2)),
					Math.min(255, c.getBlue() + ((255 - c.getBlue()) / 2)));
		}

	}

}
