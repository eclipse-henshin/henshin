package org.eclipse.emf.henshin.adapters.xtext.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.shared.SharedStateModule;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.google.inject.util.Modules;

import org.eclipse.emf.henshin.adapters.xtext.HenshinRuntimeModule;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.emf.henshin.adapters.xtext.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private Injector injector;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	public Injector getInjector() {
		return injector;
	}

	private void initializeInjector() {
		injector = Guice.createInjector(
			Modules.override(Modules.override(new HenshinRuntimeModule()).with(new HenshinUiModule(plugin))).with(
				new SharedStateModule()));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		initializeInjector();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		injector = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
