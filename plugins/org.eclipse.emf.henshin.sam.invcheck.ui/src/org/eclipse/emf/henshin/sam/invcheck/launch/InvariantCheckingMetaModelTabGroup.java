package org.eclipse.emf.henshin.sam.invcheck.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.CommonTab;

public class InvariantCheckingMetaModelTabGroup extends
		AbstractLaunchConfigurationTabGroup {

	//@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
				new InvariantCheckingMetaModelMainTab(), new InvariantCheckingMetaModelOptionsTab(),  new CommonTab() };
		setTabs(tabs);
	}

}
