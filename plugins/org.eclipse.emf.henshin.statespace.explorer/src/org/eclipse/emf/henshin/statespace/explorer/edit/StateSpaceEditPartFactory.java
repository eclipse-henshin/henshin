package org.eclipse.emf.henshin.statespace.explorer.edit;

import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * Factory that maps model elements to edit parts.
 * @author Christian Krause
 */
public class StateSpaceEditPartFactory implements EditPartFactory {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object modelElement) {
		// get EditPart for model element
		EditPart part = getPartForElement(modelElement);
		// store model element in EditPart
		part.setModel(modelElement);
		return part;
	}

	/**
	 * Maps an object to an EditPart. 
	 * @throws RuntimeException if no match was found (programming error)
	 */
	private EditPart getPartForElement(Object modelElement) {
		if (modelElement instanceof StateSpace) {
			return new StateSpaceEditPart();
		}
		if (modelElement instanceof State) {
			return new StateEditPart();
		}
		if (modelElement instanceof Transition) {
			return new TransitionEditPart();
		}
		throw new RuntimeException(
				"Can't create part for model element: "
				+ ((modelElement != null) ? modelElement.getClass().getName() : "null"));
	}

}