package org.eclipse.emf.henshin.variability.ui.viewer.util;

import org.eclipse.emf.henshin.variability.configuration.ui.helpers.VariabilityModelHelper;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;import org.eclipse.swt.SWT;

import configuration.Configuration;
import configuration.Feature;
import configuration.FeatureBinding;

/**
 * Provides editing support for {@link Feature} bindings.
 * 
 * @author Stefan Schulz
 *
 */
public class FeatureViewerBindingEditingSupport extends EditingSupport {

	private final TableViewer viewer;
	private String[] nonContradictingOptions = FeatureBinding.getNames();
	
	public FeatureViewerBindingEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}
	
	@Override
	protected CellEditor getCellEditor(Object element) {
		Configuration configuration = (Configuration) viewer.getInput();
		Feature feature = (Feature) element;
		nonContradictingOptions = VariabilityModelHelper.getNonContradictingBindingOptions(configuration, feature);
		return new ComboBoxCellEditor(viewer.getTable(), nonContradictingOptions, SWT.READ_ONLY);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		Feature feature = ((Feature)element);
		String bindingname = feature.getBinding().getName();
		
		int index = -1;
		for(int i = nonContradictingOptions.length - 1; i >= 0; i--) {
			if(nonContradictingOptions[i].equals(bindingname)) {
				index = i;
			}
		}
		return index;
	}

	@Override
	protected void setValue(Object element, Object value) {
		Feature vp = (Feature)element;
		FeatureBinding oldBinding = vp.getBinding();
		String bindingString = nonContradictingOptions[(int)value];
		FeatureBinding newBinding = FeatureBinding.getByName(bindingString);
		
		if(oldBinding != newBinding) {
			vp.setBinding(newBinding);
			getViewer().update(element, new String[]{"binding"});
		}
	}

}
