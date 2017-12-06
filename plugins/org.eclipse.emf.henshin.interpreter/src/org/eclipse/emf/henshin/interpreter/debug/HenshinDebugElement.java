package org.eclipse.emf.henshin.interpreter.debug;

import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.emf.henshin.HenshinModelPlugin;

public abstract class HenshinDebugElement extends DebugElement{

	public HenshinDebugElement(IDebugTarget target) {
		super(target);
	}

	@Override
	public String getModelIdentifier() {
		return HenshinModelPlugin.PLUGIN_ID;
	}

}
