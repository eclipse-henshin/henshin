package org.eclipse.emf.henshin.diagram.edit.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.henshin.diagram.actions.Action;
import org.eclipse.emf.henshin.diagram.actions.EdgeActionUtil;
import org.eclipse.emf.henshin.diagram.edit.policies.EdgeItemSemanticEditPolicy;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinGraphUtil;
import org.eclipse.emf.henshin.model.util.RuleGraphsListener;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

/**
 * @generated
 */
public class EdgeEditPart extends ConnectionNodeEditPart implements
		ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 4001;

	/**
	 * @generated NOT
	 */
	private RuleGraphsListener ruleListener;

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
	public void activate() {
		super.activate();
		if (ruleListener == null) {
			Edge edge = (Edge) (getNotationView().getElement());
			Rule rule = HenshinGraphUtil.getRule(edge.getGraph());
			ruleListener = new RuleGraphsListener(rule, new AdapterImpl() {
				public void notifyChanged(Notification event) {
					// Make sure the edge still exists.
					if (getNotationView().getElement()!=null) {
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
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new EdgeItemSemanticEditPolicy());
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
		return new PolylineConnectionEx();
	}

	/**
	 * @generated
	 */
	public PolylineConnectionEx getPrimaryShape() {
		return (PolylineConnectionEx) getFigure();
	}
	
	/**
	 * @generated NOT
	 */
	@Override
	public void refreshForegroundColor() {
		Edge edge = (Edge) getNotationView().getElement();
		Action action = EdgeActionUtil.getEdgeAction(edge);
		Color color = (action != null) ? action.getType().getColor() 
				: ColorConstants.gray;
		setForegroundColor(color);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void refreshVisuals() {
		super.refreshVisuals();
	}

}
