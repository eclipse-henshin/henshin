package org.eclipse.emf.henshin.variability.configuration.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityConstants;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityTransactionHelper;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

/**
 * This class enables moving a selected element to the base rule.
 * 
 * @author Stefan Schulz
 *
 */
public class MoveElementToBaseRuleAction implements IActionDelegate {

	public final static String ID = "org.eclipse.emf.henshin.variability.ui.MoveElementToBaseRuleActionID";
	private ArrayList<ModelElement> selectedModelElementList = new ArrayList<ModelElement>();
	
	
	@Override
	public void run(IAction action) {
		if(selectedModelElementList != null && !selectedModelElementList.isEmpty()) {
			for(ModelElement modelElement : selectedModelElementList) {
				VariabilityTransactionHelper.setAnnotationValue(modelElement, VariabilityConstants.PRESENCE_CONDITION, null);
			}
		}
		
	}
		
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		selectedModelElementList.clear();
		if (selection instanceof IStructuredSelection) {
			Iterator<?> it = ((IStructuredSelection)selection).iterator();
			
			while(it.hasNext()) {
				Object o = it.next();
				if (o instanceof AbstractGraphicalEditPart) {
					AbstractGraphicalEditPart editPart = (AbstractGraphicalEditPart) o;
					if(editPart.getModel() instanceof View) {
						View view = (View) editPart.getModel();
						if(view.getElement() instanceof ModelElement) {
							selectedModelElementList.add((ModelElement) view.getElement());
						}
					}
				}
			}
		}
	}

}
