/**
 * 
 */
package org.eclipse.emf.henshin.examples.oo2rdb;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceFactory;
import org.eclipse.emf.henshin.trace.impl.TracePackageImpl;

/**
 * This abstract class is intended to be overridden and may be used as
 * boilerplate code in order to apply henshin transformations. Note that is does
 * not contain abstract methods though.<br>
 * Feel free to copy'n'paste it and use it to ease the application of your own
 * transformations.
 * 
 * @author Stefan Jurack (sjurack)
 * 
 */
public abstract class ATrafo {

	/**
	 * The shared resource set for loading involved files.
	 */
	protected final ResourceSet resourceSet;

	public ATrafo() {
		resourceSet = new ResourceSetImpl();
	}

	/**
	 * You may want to override this method and add additional functionality.
	 */
	public void start() {
		// Prerequisites
		initializeFactories();
		// ------
	}// start

	/**
	 * Loads the model located at the given path into the current resource set
	 * and returns the root element. Make sure that an appropriate
	 * {@link Factory} is available (see {@link #initializeFactories()}).
	 * 
	 * @param modelFilePath
	 * @return
	 */
	protected EObject loadModel(String modelFilePath) {
		URI modelUri = URI.createFileURI(new File(modelFilePath)
				.getAbsolutePath());
		Resource modelResource = resourceSet.getResource(modelUri, true);
		if (modelResource.isLoaded()) {
			modelResource.unload(); // make sure that we work with a virgin
									// instance of the model
			try {
				modelResource.load(null);
			} catch (IOException e) {
			}// try catch
		}// if
		return modelResource.getContents().get(0);
	}// loadModel

	/**
	 * Saves the content of the given <code>root</code> {@link EObject} to the
	 * specified file path.
	 * 
	 * @param filename
	 * @param root
	 */
	protected void saveModel(String filename, EObject root) {
		URI modelUri = URI.createFileURI(new File(filename).getAbsolutePath());
		Resource res = resourceSet.createResource(modelUri);
		HashMap<String, Object> opts = new HashMap<String, Object>();
		opts.put(XMIResource.OPTION_SCHEMA_LOCATION, true);
		try {
			res.getContents().add(root);
			res.save(opts);
		} catch (IOException e) {
			e.printStackTrace();
		}// try catch
	}// saveModel

	/**
	 * Initializes an number of (possibly) related factories and packages.
	 */
	protected void initializeFactories() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"henshin", new HenshinResourceFactory());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"xmi", new XMIResourceFactoryImpl());

		HenshinPackageImpl.init();
		EcorePackageImpl.init();
		TracePackageImpl.init();
	}// initializeFactories

}// class
