package org.eclipse.emf.henshin.variability.configuration.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.configuration.ui.helpers.VariabilityModelHelper;
import org.eclipse.emf.henshin.variability.configuration.ui.providers.ConfigurationProvider;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityConstants;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityTransactionHelper;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import configuration.Configuration;

/**
 * This class allows moving a selected element to a configuration; i.e. changing its presence condition according to the current configuration.
 * 
 * @author Stefan Schulz
 *
 */
public class MoveElementToConfigurationAction implements IActionDelegate {

	public final static String ID = "org.eclipse.emf.henshin.variability.ui.MoveElementToConfigurationActionID";
	private ArrayList<GraphElement> selectedGraphElementList = new ArrayList<GraphElement>();
	
	@Override
	public void run(IAction action) {
		if(selectedGraphElementList != null && !selectedGraphElementList.isEmpty()) {
			for(GraphElement graphElement : selectedGraphElementList) {
				if (graphElement instanceof ModelElement) {
					Rule rule = graphElement.getGraph().getRule();
					Configuration configuration = ConfigurationProvider.getInstance().getConfiguration(rule);
					String presenceCondition = VariabilityModelHelper.getPresenceCondition(configuration);
					VariabilityTransactionHelper.setAnnotationValue((ModelElement) graphElement, VariabilityConstants.PRESENCE_CONDITION, presenceCondition);
				}
			}
		}
		
	}
		
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		selectedGraphElementList.clear();
		if (selection instanceof IStructuredSelection) {
			Iterator<?> it = ((IStructuredSelection)selection).iterator();
			
			while(it.hasNext()) {
				Object o = it.next();
				if (o instanceof AbstractGraphicalEditPart) {
					AbstractGraphicalEditPart editPart = (AbstractGraphicalEditPart) o;
					if(editPart.getModel() instanceof View) {
						View view = (View) editPart.getModel();
						if(view.getElement() instanceof GraphElement) {
							selectedGraphElementList.add((GraphElement) view.getElement());
						}
					}
				}
			}
		}
	}

}
