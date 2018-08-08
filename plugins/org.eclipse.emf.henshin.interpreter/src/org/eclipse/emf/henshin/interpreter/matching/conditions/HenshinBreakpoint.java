package org.eclipse.emf.henshin.interpreter.matching.conditions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.emf.henshin.HenshinModelPlugin;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition.DebugLevel;

public abstract class HenshinBreakpoint extends Breakpoint {
		
	@Override
	public String getModelIdentifier() {
		return HenshinModelPlugin.PLUGIN_ID;
	}
	
	protected int getIntAttribute(String name, int defaultValue) {
		try {
			return ensureMarker().getAttribute(name, defaultValue);
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return defaultValue;
	}
	
	protected boolean getBooleanAttribute(String name) {
		return Boolean.parseBoolean(getAttribute(name, ""));
	}
	
	protected String getAttribute(String name, String defaultValue) {
		try {
			return ensureMarker().getAttribute(name, defaultValue);
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return defaultValue;
	}

	public DebugLevel getDebugLevel() {
		try {
			return DebugLevel.valueOf(getAttribute("DebugLevel", ""));
		} catch (Exception e) {
		}
		return DebugLevel.NONE;
	}

	public void setDebugLevel(DebugLevel debugLevel) {
		try {
			setAttribute("DebugLevel", debugLevel.toString());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getMessage() {
		return getAttribute(IMarker.MESSAGE, null);
	}
	
	public void setMessage(String message) {
		try {
			setAttribute(IMarker.MESSAGE, message);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
