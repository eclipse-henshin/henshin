package org.eclipse.emf.henshin.variability.ui.viewer.util;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import configuration.Configuration;

/**
 * Provides a {@link IStructuredContentProvider} for {@link Feature}s.
 * 
 * @author Stefan Schulz
 *
 */
public class FeatureViewerContentProvider implements IStructuredContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return ((Configuration)inputElement).getFeatures().toArray();
	}

}
