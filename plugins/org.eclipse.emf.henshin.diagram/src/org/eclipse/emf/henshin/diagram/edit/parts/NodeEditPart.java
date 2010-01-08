package org.eclipse.emf.henshin.diagram.edit.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.henshin.diagram.actions.Action;
import org.eclipse.emf.henshin.diagram.actions.NodeActionUtil;
import org.eclipse.emf.henshin.diagram.edit.policies.NodeGraphicalEditPolicy;
import org.eclipse.emf.henshin.diagram.edit.policies.NodeItemSemanticEditPolicy;
import org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinGraphUtil;
import org.eclipse.emf.henshin.model.util.RuleGraphsListener;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.notation.View;
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
	private RuleGraphsListener ruleListener;

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
	public void activate() {
		super.activate();
		if (ruleListener == null) {
			Node node = (Node) (getNotationView().getElement());
			Rule rule = HenshinGraphUtil.getRule(node.getGraph());
			ruleListener = new RuleGraphsListener(rule, new AdapterImpl() {
				public void notifyChanged(Notification event) {
					// Make sure the node still exists.
					if (getNotationView().getElement() instanceof Node) {
						refreshVisuals();
					}
				}
			});
		}
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void deactivate() {
		if (ruleListener != null) {
			ruleListener.dispose();
			ruleListener = null;
		}
		super.deactivate();
	}

	/**
	 * Refresh the action labels and colors.
	 * @generated NOT
	 */
	@Override
	public void refreshVisuals() {
		super.refreshVisuals();
		IGraphicalEditPart actionLabel = getChildBySemanticHint(String
				.valueOf(NodeActionEditPart.VISUAL_ID));
		if (actionLabel instanceof NodeActionEditPart) {
			actionLabel.refresh();
		}
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void refreshForegroundColor() {
		if (getNotationView().getElement() instanceof Node) {
			Node node = (Node) getNotationView().getElement();
			Action action = NodeActionUtil.getNodeAction(node);
			if (action != null) {
				setForegroundColor(action.getType().getColor());
				return;
			}
		}
		super.refreshForegroundColor();
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		
		// Install a custom graphical node edit policy:
		removeEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE);
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new NodeGraphicalEditPolicy());
		
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new NodeItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		
	}
	
	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {

		FlowLayoutEditPolicy lep = new FlowLayoutEditPolicy() {

			protected Command createAddCommand(EditPart child, EditPart after) {
				return null;
			}

			protected Command createMoveChildCommand(EditPart child,
					EditPart after) {
				return null;
			}

			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		NodeFigure figure = new NodeFigure();
		return primaryShape = figure;
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
		if (childEditPart instanceof NodeNameEditPart) {
			((NodeNameEditPart) childEditPart).setLabel(getPrimaryShape()
					.getNodeNameLabel());
			return true;
		}
		if (childEditPart instanceof NodeActionEditPart) {
			((NodeActionEditPart) childEditPart).setLabel(getPrimaryShape()
					.getNodeActionLabel());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof NodeNameEditPart) {
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
		return getChildBySemanticHint(HenshinVisualIDRegistry
				.getType(NodeNameEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMARelTypesOnSource() {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		types.add(HenshinElementTypes.Edge_4001);
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMARelTypesOnSourceAndTarget(
			IGraphicalEditPart targetEditPart) {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		if (targetEditPart instanceof org.eclipse.emf.henshin.diagram.edit.parts.NodeEditPart) {
			types.add(HenshinElementTypes.Edge_4001);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMATypesForTarget(
			IElementType relationshipType) {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		if (relationshipType == HenshinElementTypes.Edge_4001) {
			types.add(HenshinElementTypes.Node_3001);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMARelTypesOnTarget() {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		types.add(HenshinElementTypes.Edge_4001);
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMATypesForSource(
			IElementType relationshipType) {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		if (relationshipType == HenshinElementTypes.Edge_4001) {
			types.add(HenshinElementTypes.Node_3001);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public class NodeFigure extends RectangleFigure {

		/**
		 * @generated
		 */
		private WrappingLabel fNodeNameLabel;
		/**
		 * @generated
		 */
		private WrappingLabel fNodeActionLabel;

		/**
		 * @generated
		 */
		public NodeFigure() {

			FlowLayout layoutThis = new FlowLayout();
			layoutThis.setStretchMinorAxis(false);
			layoutThis.setMinorAlignment(FlowLayout.ALIGN_LEFTTOP);

			layoutThis.setMajorAlignment(FlowLayout.ALIGN_CENTER);
			layoutThis.setMajorSpacing(5);
			layoutThis.setMinorSpacing(5);
			layoutThis.setHorizontal(true);

			this.setLayoutManager(layoutThis);

			this.setLineWidth(1);
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fNodeActionLabel = new WrappingLabel();
			fNodeActionLabel.setText("none");
			fNodeActionLabel.setForegroundColor(ColorConstants.gray);

			this.add(fNodeActionLabel);

			fNodeNameLabel = new WrappingLabel();
			fNodeNameLabel.setText("Node");

			this.add(fNodeNameLabel);

		}

		/**
		 * @generated
		 */
		private boolean myUseLocalCoordinates = false;

		/**
		 * @generated
		 */
		protected boolean useLocalCoordinates() {
			return myUseLocalCoordinates;
		}

		/**
		 * @generated
		 */
		protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
			myUseLocalCoordinates = useLocalCoordinates;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getNodeNameLabel() {
			return fNodeNameLabel;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getNodeActionLabel() {
			return fNodeActionLabel;
		}

	}

}
