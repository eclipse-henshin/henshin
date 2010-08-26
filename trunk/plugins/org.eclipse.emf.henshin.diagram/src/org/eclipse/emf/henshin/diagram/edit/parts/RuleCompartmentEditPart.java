/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.diagram.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.henshin.diagram.edit.helpers.RootObjectEditHelper;
import org.eclipse.emf.henshin.diagram.edit.policies.RuleCompartmentCanonicalEditPolicy;
import org.eclipse.emf.henshin.diagram.edit.policies.RuleCompartmentItemSemanticEditPolicy;
import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorPlugin;
import org.eclipse.emf.henshin.diagram.part.Messages;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class RuleCompartmentEditPart extends ShapeCompartmentEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 7001;

	/**
	 * @generated NOT
	 */
	private RuleGraphsListener ruleListener;

	/**
	 * @generated
	 */
	public RuleCompartmentEditPart(View view) {
		super(view);
	}	

	/**
	 * @generated NOT
	 */
	@Override
	public void addSemanticListeners() {
		super.addSemanticListeners();
		Rule rule = (Rule) getNotationView().getElement();
		ruleListener = new RuleGraphsListener(rule, new AdapterImpl() {
			public void notifyChanged(Notification event) {
				// Really make sure that the edit part is still valid.
				if (isActive()
						&& getNotationView().getElement() instanceof Rule
						&& getParent() != null) {
					updateRootContainment(event);
				}
			}
		});
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
	 * @generated NOT
	 * @param event Notification event.
	 */
	protected void updateRootContainment(Notification event) {
		
		View ruleView = ViewUtil.getContainerView(getNotationView());
		Rule rule = (Rule) ruleView.getElement();
		Node root = RootObjectEditHelper.getRootObject(ruleView);
		
		// If the rule has a hidden root object we need to update the containment links.
		if (root!=null) {
			
			Object oldVal = event.getOldValue();
			Object newVal = event.getNewValue();
			
			try {
				switch (event.getEventType()) {
				case Notification.ADD: 
					if (newVal instanceof Node) {
						RootObjectEditHelper.updateRootContainment(ruleView, (Node) newVal);
					}
					else if (newVal instanceof Edge) {
						Edge edge = ((Edge) newVal);
						if (edge.getSource()!=root && edge.getTarget()!=root) {
							RootObjectEditHelper.updateRootContainment(ruleView, edge.getSource());
							RootObjectEditHelper.updateRootContainment(ruleView, edge.getTarget());
						}
					}
					break;
				case Notification.REMOVE: 
					if (oldVal instanceof Edge) {
						Edge edge = ((Edge) oldVal);
						if (edge.getSource()!=root && edge.getTarget()!=root) {
							RootObjectEditHelper.updateRootContainment(ruleView, edge.getSource());
							RootObjectEditHelper.updateRootContainment(ruleView, edge.getTarget());
						}
					}
				}
			} catch (Throwable t) {
				HenshinDiagramEditorPlugin.getInstance().logError("Error updating root containment for rule " + rule.getName(), t);
			}
		}		
	}

	/**
	 * @generated NOT
	 */
	@Override
	public boolean isSelectable() {
		return false;
	}

	/**
	 * @generated
	 */
	public String getCompartmentName() {
		return Messages.RuleCompartmentEditPart_title;
	}

	/**
	 * @generated
	 */
	public IFigure createFigure() {
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
				new RuleCompartmentItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CREATION_ROLE,
				new CreationEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
				new DragDropEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new RuleCompartmentCanonicalEditPolicy());
	}

	/**
	 * @generated
	 */
	protected void setRatio(Double ratio) {
		if (getFigure().getParent().getLayoutManager() instanceof ConstrainedToolbarLayout) {
			super.setRatio(ratio);
		}
	}

}
