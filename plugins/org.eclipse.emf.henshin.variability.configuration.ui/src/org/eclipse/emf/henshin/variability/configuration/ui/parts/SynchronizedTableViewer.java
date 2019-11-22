package org.eclipse.emf.henshin.variability.configuration.ui.parts;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Stefan Schulz
 *
 */
public class SynchronizedTableViewer extends TableViewer {

	private final ITableViewerSynchronizedPart synchronizedPart;
	
	public SynchronizedTableViewer(Composite parent, int style, ITableViewerSynchronizedPart synchronizedPart) {
		super(parent, style);
		this.synchronizedPart = synchronizedPart;
	}
	
	@Override
	public void update(Object element, String[] properties) {
		super.update(element, properties);
		if(synchronizedPart != null) {
			synchronizedPart.tableViewerUpdated();
		}
	}
}
