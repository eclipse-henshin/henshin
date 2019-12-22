package org.eclipse.emf.henshin.variability.configuration.ui.helpers;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeEditPart;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * This concealing strategy hides all elements that have presence conditions contradicting to the current configuration.
 * 
 * @author Stefan Schulz
 *
 */
public class FigureVisibilityConcealingStrategy extends AbstractConcealingStrategy {

	@Override
	public void doReveal(AbstractGraphicalEditPart abstractEditPart) {
		IFigure figure = getFigure(abstractEditPart).getParent();
		figure.setVisible(true);
		figure.invalidate();
		if (abstractEditPart instanceof ConnectionEditPart) {
			ConnectionEditPart connection = (ConnectionEditPart) abstractEditPart;
			connection.getSource().refresh();
			connection.getTarget().refresh();
		}
	}

	@Override
	public void doConceal(AbstractGraphicalEditPart abstractEditPart) {
		IFigure figure = getFigure(abstractEditPart);
		if (!(abstractEditPart instanceof EdgeEditPart)) {
			figure = figure.getParent();
		}
		figure.setVisible(false);
		figure.invalidate();
	}
}
