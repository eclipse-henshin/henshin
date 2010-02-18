package org.eclipse.emf.henshin.statespace.explorer.edit;

import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.commands.ExploreStatesCommand;
import org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

/**
 * State diagram edit part.
 * @author Christian Krause
 */
public class StateEditPart extends AbstractGraphicalEditPart implements NodeEditPart, Adapter {
	
	// Size of state figures.
	public final static Dimension SIZE = new Dimension(-1,22);
	
	// Default color to be used.
	public final static Color COLOR_DEFAULT = new Color(null, 220,220,220);

	// Color to be used for initial states.
	public final static Color COLOR_INITIAL = new Color(null, 40,220,40);

	// Color to be used for terminal states.
	public final static Color COLOR_TERMINAL = new Color(null, 250,60,60);
	
	// Color to be used for open states.
	public final static Color COLOR_OPEN = new Color(null, 100,100,250);

	// Connection anchor:
	private ConnectionAnchor anchor;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getState().eAdapters().add(this);
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateComponentEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		return new StateFigure();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			getState().eAdapters().remove(this);
			super.deactivate();
		}
	}
	
	/**
	 * Get the state that this edit part belongs to.
	 * @return State.
	 */
	public State getState() {
		return (State) getModel();
	}
	
	/**
	 * Get the used state space manager.
	 * @return State space manager.
	 */
	public StateSpaceManager getStateSpaceManager() {
		return ((StateSpaceEditPart) getParent()).getStateSpaceManager();
	}
	
	/*
	 * Get the connection anchor.
	 */
	protected ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			anchor = new EllipseAnchor(getFigure());
		}
		return anchor;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections()
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected List getModelSourceConnections() {
		return getState().getOutgoing();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections()
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected List getModelTargetConnections() {
		return getState().getIncoming();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
	 */
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		refreshLocation();
		refreshLabel();
		refreshColor();
	}
	
	/*
	 * Update the state's location.
	 */
	private void refreshLocation() {
		int[] loc = getState().getLocation();
		Rectangle bounds = new Rectangle(loc[0], loc[1], SIZE.width, SIZE.height);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
	}
	
	/*
	 * Update the name label.
	 */
	private void refreshLabel() {
		
		// Update label text:
		int index = getState().getIndex();
		String name = " " + index + " ";
		((StateFigure) getFigure()).getLabel().setText(name);
		
		// Update tool tip:
		if (getState().isInitial()) {
			String tooltip = getState().getModel().getURI().deresolve(getState().eResource().getURI()).toString();
			getFigure().setToolTip(new Label(tooltip));
		}
		
	}
	
	/*
	 * Refresh the color.
	 */
	private void refreshColor() {
		State state = getState();
		if (state.isInitial()) {
			getFigure().setBackgroundColor(COLOR_INITIAL);	
		}
		else if (state.isTerminal()) {
			getFigure().setBackgroundColor(COLOR_TERMINAL);	
		}
		else if (state.isOpen()) {
			getFigure().setBackgroundColor(COLOR_OPEN);
		}
		else {
			getFigure().setBackgroundColor(COLOR_DEFAULT);			
		}		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef.Request)
	 */
	@Override
	public void performRequest(Request request) {
		if (request.getType()==RequestConstants.REQ_OPEN) {
			// Explore the current state:
			ExploreStatesCommand command = new ExploreStatesCommand(getStateSpaceManager(), getState());
			command.setGenerateLocations(true);
			CommandStack stack = getViewer().getEditDomain().getCommandStack();
			stack.execute(command);
		} else {
			super.performRequest(request);
		}
	}
	
	/**
	 * State figure.
	 * @author Christian Krause
	 */
	static class StateFigure extends Ellipse {
		
		private Label label;
		
		StateFigure() {
			setAntialias(SWT.ON);
			setOpaque(true);
			setLayoutManager(new StackLayout());
			add(label = new Label());
		}
		
		public Label getLabel() {
			return label;
		}
		
	}
	
	
	/* -------------------- *
	 * --- Notification --- *
	 * -------------------- */
	
	private Notifier target;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.Adapter#getTarget()
	 */
	public Notifier getTarget() {
		return target;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.Adapter#isAdapterForType(java.lang.Object)
	 */
	public boolean isAdapterForType(Object type) {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void notifyChanged(final Notification event) {
		
		switch (event.getFeatureID(State.class)) {
		
		case StateSpacePackageImpl.STATE__DATA: 
			refreshColor();
			refreshLocation();
			break;
		
		case StateSpacePackageImpl.STATE__INDEX: 
			refreshLabel(); 
			break;
			
		case StateSpacePackageImpl.STATE__OUTGOING: 
			refreshSourceConnections(); 
			break;

		case StateSpacePackageImpl.STATE__INCOMING: 
			refreshTargetConnections(); 
			break;
			
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.Adapter#setTarget(org.eclipse.emf.common.notify.Notifier)
	 */
	public void setTarget(Notifier target) {
		this.target = target;
	}
	
}