package org.eclipse.emf.henshin.statespace.explorer.edit;

import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.explorer.commands.StateDeleteCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * State component edit policy.
 * @author Christian Krause
 */
public class StateComponentEditPolicy extends ComponentEditPolicy {

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		
		if (parent instanceof StateSpace && child instanceof State) {
			return new StateDeleteCommand((State) child, (StateSpace) parent);
		}
		
		return super.createDeleteCommand(deleteRequest);
		
	}
	
}