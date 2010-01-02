package org.eclipse.emf.henshin.diagram.navigator;

import org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * @generated
 */
public class HenshinNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 7003;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof HenshinNavigatorItem) {
			HenshinNavigatorItem item = (HenshinNavigatorItem) element;
			return HenshinVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
