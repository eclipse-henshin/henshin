package org.eclipse.emf.henshin.variability.configuration.ui.helpers;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

/**
 * This concealing strategy lowers the alpha of all elements that have presence conditions contradicting to the current configuration.
 * 
 * @author Stefan Schulz
 *
 */
public class ShapeAlphaConcealingStrategy extends AbstractConcealingStrategy {

	private final int CONCEAL_ALPHA = 25;
	private final int REVEAL_ALPHA = 255;
	
	@Override
	public void doReveal(AbstractGraphicalEditPart abstractEditPart) {
		IFigure figure = getFigure(abstractEditPart);
		if(figure instanceof Shape) {
			doReveal((Shape)figure);
		} else {
			doReveal(figure);
		}
	}
	
	private void doReveal(Shape shape) {
		shape.setAlpha(REVEAL_ALPHA);
		for(Object o : shape.getChildren()) {
			if(o instanceof Shape) {
				doReveal((Shape) o);
			} else if(o instanceof IFigure) {
				doReveal((IFigure) o);
			}
		}
	}
	
	private void doReveal(IFigure figure) {
		FontData fontData = figure.getFont().getFontData()[0];
		Font font = new Font(Display.getCurrent(), new FontData(fontData.getName(), fontData.getHeight(), SWT.NORMAL));
		figure.setFont(font);
	}

	@Override
	public void doConceal(AbstractGraphicalEditPart abstractEditPart) {
		IFigure figure = getFigure(abstractEditPart);
		if(figure instanceof Shape) {
			doConceal((Shape)figure);
		} else if (figure instanceof WrappingLabel){
			doConceal((WrappingLabel)figure);
		}
	}
	
	private void doConceal(Shape shape) {
		shape.setAlpha(CONCEAL_ALPHA);
		for(Object o : shape.getChildren()) {
			if(o instanceof Shape) {
				doConceal((Shape)o);
			} else if (o instanceof WrappingLabel){
				doConceal((WrappingLabel)o);
			}
		}
	}
	
	private void doConceal(WrappingLabel wrappingLabel) {
		FontData fontData = wrappingLabel.getFont().getFontData()[0];
		Font font = new Font(Display.getCurrent(), new FontData(fontData.getName(), fontData.getHeight(), SWT.ITALIC));
		wrappingLabel.setFont(font);
	}
}
