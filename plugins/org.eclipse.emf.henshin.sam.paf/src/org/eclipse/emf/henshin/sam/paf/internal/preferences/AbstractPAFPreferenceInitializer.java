package org.eclipse.emf.henshin.sam.paf.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.emf.henshin.sam.paf.preferences.PAFPreferenceConstants;
import org.osgi.service.prefs.Preferences;

public class AbstractPAFPreferenceInitializer extends AbstractPreferenceInitializer {

	public AbstractPAFPreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		Preferences pNode = DefaultScope.INSTANCE.getNode("org.eclipse.emf.henshin.sam.paf");
		pNode.putInt(PAFPreferenceConstants.PIPE_SIZE, 10);
		pNode.putInt(PAFPreferenceConstants.ENQUEUE_WAIT, 50);
		pNode.putInt(PAFPreferenceConstants.DEQUEUE_WAIT, 50);
		pNode.putInt(PAFPreferenceConstants.THREADS_COMPLEXITY_LOW, 1);
		pNode.putInt(PAFPreferenceConstants.THREADS_COMPLEXITY_MEDIUM, 1);
		pNode.putInt(PAFPreferenceConstants.THREADS_COMPLEXITY_HIGH, 1);
	}

}
