package org.eclipse.emf.henshin.interpreter.ui.debug;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.emf.henshin.interpreter.matching.conditions.HenshinBreakpoint;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;

public class DebugModelPresentation extends LabelProvider implements IDebugModelPresentation {

	@Override
	public String getText(Object element) {
		if (element instanceof HenshinBreakpoint) {
			final HenshinBreakpoint breakpoint = (HenshinBreakpoint)element;
			return breakpoint.getMessage();
		}
		return null;
	}

	@Override
	public IEditorInput getEditorInput(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEditorId(IEditorInput input, Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String attribute, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void computeDetail(IValue value, IValueDetailListener listener) {
		// TODO Auto-generated method stub
		
	}
}
