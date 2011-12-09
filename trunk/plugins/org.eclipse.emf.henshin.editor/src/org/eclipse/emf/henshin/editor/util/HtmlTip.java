package org.eclipse.emf.henshin.editor.util;

import org.eclipse.jface.viewers.TreeViewer;

public class HtmlTip extends HtmlTipSupport {
	
	public HtmlTip(TreeViewer viewer) {
		super(viewer);
	}
	
	public static void enableFor(TreeViewer viewer) {
		if (!(viewer.getLabelProvider() instanceof IToolTipProvider))
			return;
		new HtmlTip(viewer);
	}
	
	
	
}
