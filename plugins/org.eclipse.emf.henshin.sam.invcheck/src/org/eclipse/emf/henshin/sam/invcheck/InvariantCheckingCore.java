package org.eclipse.emf.henshin.sam.invcheck;

import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * This singleton complies to the <code>Abstract Factory</code> paradigm.
 */
public class InvariantCheckingCore extends AbstractUIPlugin {

	public static final String SOLVER_STRATEGIES_EXTENSION_POINT = "de.uni_paderborn.invariantchecking.solver";
	
	/**
	 * The string that has to be used in a {@link TypeGraphCondition}'s {@link Annotation} source property.<br />
	 * Assumed Proeprties are only used to drop counter examples but not to produce property / rule pairs.
	 */
	public static final String ASSUMED_GUARANTEE_ANNOTATION_SOURCE = "org.eclipse.emf.henshin.sam.invcheck.AssumedGuarantee";
	
	public static final String ORIGINAL_FORBIDDEN_PATTERN_ANNOTATION_SOURCE = "org.eclipse.emf.henshin.sam.invcheck.OriginalForbiddenPattern";
	
	public static final String PARTIAL_NAC = "org.eclipse.emf.henshin.sam.invcheck.PartialNAC";
	
	public static final String NAC_BOUND_ITEM = "org.eclipse.emf.henshin.sam.invcheck.NACBoundItem";

	
	/**
	 * static initialization block, which sets the plugin's outputStream member. 
	 */
	static {
			/*PrintStream tmpStream = null;
			
			boolean fO = false;
			/*
			final IPreferenceStore pStore = InvariantCheckingCore.getDefault().getPreferenceStore();
			if (pStore != null) {
				fO = pStore.getBoolean(InvariantCheckingPreferenceConstants.FILE_OUTPUT);			
			} else {
				fO = false;
			}
			
			String debugOption = Platform.getDebugOption("de.uni_paderborn.invariantchecking.core/fileOutput");
			if (debugOption != null && debugOption.equals("true")) {
			//if (fO) {
				
				try {
				tmpStream = new PrintStream("verification-"+System.currentTimeMillis()+".log");
				} catch (FileNotFoundException e) {
					System.err.println("unable to create verification.log. Using standard console output instead");
					tmpStream = System.out;
				}
			} else
				tmpStream = System.out;
			outputStream = tmpStream;*/
	}
	
	/**
	 * No comment provided by developer, please add a comment to improve
	 * documentation.
	 */
	private static InvariantCheckingCore instance = null;

	/**
	 * Constructor for class InvariantCheckingCore
	 */
	public InvariantCheckingCore() {
		instance = this;
	}

	/**
	 * No comment provided by developer, please add a comment to improve
	 * documentation.
	 * 
	 * @return No description provided
	 */
	public static InvariantCheckingCore getDefault() {
		return instance;
	}

	private BundleContext coreBundleContext;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		this.coreBundleContext = context;

		//this.initStrategies();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance.coreBundleContext = null;
		/*if (instance.strategies != null) {
			instance.strategies.clear();
		}*/
		instance = null;

		super.stop(context);
	}

	public BundleContext getContext() {
		return this.coreBundleContext;
	}

/*	private Map<String, IConfigurationElement> strategies;

	private void initStrategies() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint solverExPoint = registry
				.getExtensionPoint(SOLVER_STRATEGIES_EXTENSION_POINT);

		IExtension[] extensions = solverExPoint.getExtensions();

		this.strategies = new HashMap<String, IConfigurationElement>(
				extensions.length);

		for (int index = 0; index < extensions.length; index++) {
			IExtension extension = extensions[index];
			IConfigurationElement[] configElems = extension
					.getConfigurationElements();
			for (int j = 0; j < configElems.length; j++) {
				IConfigurationElement configElem = configElems[j];
				StringBuffer id = new StringBuffer(configElem
						.getNamespaceIdentifier());
				id.append(".").append(configElem.getAttribute("id"));
				this.strategies.put(id.toString(), configElem);
			}
		}
	}

	public IConfigurationElement getExtensionForIdentifier(final String ident) {
		return this.strategies != null ? this.strategies.get(ident) : null;
	}

	public String[] getExtensionIdentifiers() {
		return this.strategies != null ? this.strategies.keySet().toArray(
				new String[] {}) : null;
	}*/
}
