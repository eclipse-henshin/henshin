package org.eclipse.emf.henshin.statespace.explorer.edit;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.explorer.commands.TransitionCreateCommand;
import org.eclipse.emf.henshin.statespace.explorer.commands.TransitionReconnectCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

/**
 * State node edit policy.
 * @author Christian Krause
 */
public class StateNodeEditPolicy extends GraphicalNodeEditPolicy {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		TransitionCreateCommand command = (TransitionCreateCommand) request.getStartCommand();
		command.setTarget(getState());
		return command;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		TransitionCreateCommand command = new TransitionCreateCommand(getState());
		request.setStartCommand(command);
		return command;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		Transition transition = ((TransitionDiagramEditPart) request.getConnectionEditPart()).getTransition();
		TransitionReconnectCommand command = new TransitionReconnectCommand(transition);
		command.setNewSource(getState());
		return command;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		Transition transition = (Transition) request.getConnectionEditPart().getModel();
		TransitionReconnectCommand command = new TransitionReconnectCommand(transition);
		command.setNewTarget(getState());
		return command;
	}
	
	/*
	 * Get the host state.
	 */
	private State getState() {
		return ((StateDiagramEditPart) getHost()).getState();
	}
	
}
