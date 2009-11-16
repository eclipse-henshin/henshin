package org.eclipse.emf.henshin.statespace.explorer.edit;

import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.properties.PropertyChangeEvent;
import org.eclipse.emf.henshin.statespace.properties.PropertyChangeListener;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

/**
 * State diagram edit part.
 * @author Christian Krause
 */
public class StateDiagramEditPart extends AbstractGraphicalEditPart implements NodeEditPart, PropertyChangeListener {
	
	// Size of state figures.
	public final static Dimension SIZE = new Dimension(-1,22);
	
	// Default color to be used.
	public final static Color COLOR_DEFAULT = new Color(null, 220,220,220);

	// Color to be used for open states.
	public final static Color COLOR_OPEN = new Color(null, 220,40,40);

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
			getState().addPropertyChangeListener(this);
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new StateNodeEditPolicy());
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
			super.deactivate();
			getState().removePropertyChangeListener(this);
		}
	}
	
	/**
	 * Get the state that this edit part belongs to.
	 * @return State.
	 */
	public State getState() {
		return (State) getModel();
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
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
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
	}
	
	/*
	 * Update the state's location.
	 */
	private void refreshLocation() {
		Point location = new Point(getState().getX(), getState().getY());
		Rectangle bounds = new Rectangle(location, SIZE);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
	}
	
	/*
	 * Update the name label.
	 */
	private void refreshLabel() {
		
		// Update label text:
		String name = getState().getName()==null ? "?" : getState().getName();
		((StateFigure) getFigure()).getLabel().setText(" " + name + " ");
		
		// Update color:
		getFigure().setBackgroundColor(COLOR_DEFAULT);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.properties.PropertyChangeListener#propertyChanged(org.eclipse.emf.henshin.statespace.properties.PropertyChangeEvent)
	 */
	@Override
	public void propertyChanged(PropertyChangeEvent event) {
		
		int property = event.getProperty();
		
		if (property==State.LOCATION) {
			refreshLocation();
		}
		if (property==State.NAME) {
			refreshLabel();
		}
		else if (property==State.OUTGOING) {
			refreshSourceConnections();
		}
		else if (property==State.INCOMING) {
			refreshTargetConnections();
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
}