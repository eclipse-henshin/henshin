/*******************************************************************************
 * Copyright (c) 2004, 2005 Elias Volanakis and others.
?* All rights reserved. This program and the accompanying materials
?* are made available under the terms of the Eclipse Public License v1.0
?* which accompanies this distribution, and is available at
?* http://www.eclipse.org/legal/epl-v10.html
?*
?* Contributors:
?*????Elias Volanakis - initial API and implementation
?*******************************************************************************/
package org.eclipse.emf.henshin.statespace.explorer.edit;

import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * Factory that maps model elements to TreeEditParts.
 * TreeEditParts are used in the outline view of the ShapesEditor.
 * @author Elias Volanakis
 */
public class StateSpaceTreeEditPartFactory implements EditPartFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof State) {
			return new StateTreeEditPart((State) model);
		}
		if (model instanceof StateSpace) {
			return new StateSpaceTreeEditPart((StateSpace) model);
		}
		return null; // will not show an entry for the corresponding model instance
	}

}
