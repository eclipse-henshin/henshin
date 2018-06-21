package org.eclipse.emf.henshin.rulegen.ui.handlers;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.rulegen.RuleGenerationFacade;
import org.eclipse.emf.henshin.rulegen.ui.dialog.RadioButtonSelectionDialog;
import org.eclipse.emf.henshin.rulegen.ui.util.EMFHandlerUtil;
import org.eclipse.emf.henshin.rulegen.ui.util.UIUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Constructs a Henshin rule from a pair of models, i.e. an example which declaratively demonstrates the transformation
 * in terms of an original model A and a changed model B.<br/>
 * 
 * Basically, the corresponding elements in A and B will be treated as elements to be preserved by the rule, elements
 * contained in A only will be treated as elements to be deleted, and elements contained in B only will be treated as
 * elements to be created.<br/>
 * 
 * Corresponding elements in A and B are currently determined by calculating a matching using EMFCompare. This might be
 * a variation point in the future, the basic infrastructure is designed for exchangeability of the matcher used to
 * determine the corresponding elements in A and B.
 * 
 * 
 * @author Manuel Ohrndorf, Timo Kehrer
 */
public class CreateRuleByExampleHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		Resource resourceA = null;
		Resource resourceB = null;
		String nameA = "";
		String nameB = "";

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			if (structuredSelection.size() == 2) {
				for (@SuppressWarnings("rawtypes")
				Iterator iterator = structuredSelection.iterator(); iterator.hasNext();) {
					Object oA = iterator.next();
					Object oB = iterator.next();

					if ((oA instanceof IResource) && (oB instanceof IResource)) {

						nameA = ((IResource) oA).getName();
						nameB = ((IResource) oB).getName();

						ResourceSet rssA = new ResourceSetImpl();

						URI uriA = EMFHandlerUtil.getURI((IResource) oA);
						try {
							resourceA = rssA.getResource(uriA, true);
						} catch (Exception e) {
						}

						ResourceSet rssB = new ResourceSetImpl();
						URI uriB = EMFHandlerUtil.getURI((IResource) oB);
						try {
							resourceB = rssB.getResource(uriB, true);
						} catch (Exception e) {
						}
					}
				}
			}
		}

		// TODO: Get all the UI Strings used below from plugin.properties
		if ((resourceA != null) && (resourceB != null)) {

			Shell parent = PlatformUI.getWorkbench().getDisplay().getActiveShell();
			List<Resource> lhs_rhs_order = openSelectDialog(parent, resourceA, nameA, resourceB, nameB);

			if (lhs_rhs_order != null) {

				Resource original = lhs_rhs_order.get(0);
				Resource changed = lhs_rhs_order.get(1);

				if (original.getContents().get(0).eClass().getEPackage() == changed.getContents().get(0).eClass()
						.getEPackage()) {

					Module module = RuleGenerationFacade.createRuleByExample(original, changed);

					if (module != null) {
						URI eoURI = original.getURI().trimSegments(1).appendSegment(module.getName())
								.appendFileExtension("henshin");
						Resource eoRes = new ResourceSetImpl().createResource(eoURI);
						eoRes.getContents().add(module);

						try {
							eoRes.save(Collections.emptyMap());
						} catch (IOException e) {
							e.printStackTrace();
						}

						return null;
					} else {
						UIUtil.showError("Could not generate a transformation rule from this example.");
						return null;
					}
				} else {
					UIUtil.showError("Please select two models of the same type.");
					return null;
				}

			} else {
				return null;
			}
		} else {
			UIUtil.showError("Please select two models.");
			return null;
		}
	}
	
	/**
	 * 
	 * @param parent - parent composite of the new selection dialog
	 * @param resourceA - Eclipse Resource
	 * @param nameA - File name of Eclipse resource resourceA
	 * @param resourceB - Eclipse Resource
	 * @param nameB - File name of Eclipse resource resourceB
	 * @return - list with first element = LHS, second element = RHS of the generated rule
	 */

	private List<Resource> openSelectDialog(Shell parent, Resource resourceA, String nameA, Resource resourceB,
			String nameB) {
		List<Resource> result = new ArrayList<Resource>();

		String title = "Henshin Rule Generation";
		String message = "Please select the model that represents the left-hand side of the rule.";

		List<Entry<String, String>> buttonList = new ArrayList<Entry<String, String>>();
		buttonList.add(new AbstractMap.SimpleEntry<String, String>("buttonA", nameA));
		buttonList.add(new AbstractMap.SimpleEntry<String, String>("buttonB", nameB));

		RadioButtonSelectionDialog dialog = new RadioButtonSelectionDialog(parent);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setButtonList(buttonList);
		int returnCode = dialog.open();

		if (returnCode == Window.OK) {
			Object[] selection = dialog.getResult();
			if (selection != null) {
				String selectedButton = (String) selection[0];

				if (selectedButton.equals("buttonA")) {
					result.add(resourceA);
					result.add(resourceB);
					return result;
				} else if (selectedButton.equals("buttonB")) {
					result.add(resourceB);
					result.add(resourceA);
					return result;
				}
			}
		}

		return null;
	}

}
