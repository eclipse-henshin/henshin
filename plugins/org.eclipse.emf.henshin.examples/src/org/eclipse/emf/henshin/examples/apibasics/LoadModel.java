package org.eclipse.emf.henshin.examples.apibasics;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.examples.apibasics.boxing.Boxing;
import org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingPackage;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class LoadModel {
	
	// The dir used by the HenshinResourceSet to resolve relative paths.
	private static final String BASEDIR = "src/org/eclipse/emf/henshin/examples/apibasics/models";
	
	public static void main(String[] args) {
				
		EObject modelRoot = loadModel();
		
		/* 
		 * If you registered the metamodel, you should have an object with the 
		 * type of your model root. In that case, you can cast to the concrete
		 * type if you need to access model elements.
		 */
		System.out.println("Type of the root: " + modelRoot.getClass());
		Boxing boxing = (Boxing) modelRoot;
		System.out.println("First item: " + boxing.getItems().get(0));		
	}
	
	public static EObject loadModel() {
		
		HenshinResourceSet rs = new HenshinResourceSet(BASEDIR);
		/*
		 * Metamodels need to be registered in order to use instance models in
		 * a static way (using the classes with their specific getters 
		 * generated for your model. This can be done by registering the 
		 * package of your model in the package registry of the ResourceSet 
		 * which you use to load instance models. 
		 * In that case, it does not matter if the loaded model has been 
		 * created as a static model (e.g. by using the api as shown in 
		 * CreateAndStoreModel.java) or if it is a dynamic model created by, 
		 * e.g., right clicking the root in the .ecore file and choosing 
		 * "Create Dynamic Instance...".
		 * 
		 * You might want to compare instanceStatic and instanceDynamic in a
		 * text editor to learn about the differences.
		 */ 
		rs.getPackageRegistry().put(BoxingPackage.eINSTANCE.getNsURI(), 
				BoxingPackage.eINSTANCE);
		Resource res = rs.getResource("instanceStatic.xmi");
		
		/* 
		 * Usually there is only one model root stored in an xmi file. So
		 * getContents() should return a list with one element.
		 */
		EObject modelRoot = res.getContents().get(0);
		 
		
		/* WARNING:
		 * If the metamodel is not registered before loading, you are working
		 * with dynamic models. In that case you can access all model elements 
		 * only by very generic reflective accessor methods. If you think you
		 * need dynamic EMF more info can be found here:
		 * https://www.ibm.com/developerworks/library/os-eclipse-dynamicemf
		 */
		
		return modelRoot;
	}
}
