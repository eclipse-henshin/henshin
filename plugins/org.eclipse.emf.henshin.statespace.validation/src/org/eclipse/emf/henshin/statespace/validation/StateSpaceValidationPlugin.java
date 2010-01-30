package org.eclipse.emf.henshin.statespace.validation;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * State space validation plug-in activator.
 */
public class StateSpaceValidationPlugin extends Plugin {
	
	// The plug-in ID:
	public static final String PLUGIN_ID = "org.eclipse.emf.henshin.validation";

	// The shared instance:
	private static StateSpaceValidationPlugin plugin;
	
	/**
	 * Default constructor.
	 */
	public StateSpaceValidationPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 * @return the shared instance.
	 */
	public static StateSpaceValidationPlugin getInstance() {
		return plugin;
	}

}
