package org.eclipse.emf.henshin.migration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

public class DiagramTransformation {
	public static boolean transformDiagram(Map<EObject, EObject> correspondence, ResourceSet resourceSet, URI diagramUri) throws IOException {
		URI diagramBackupUri = diagramUri.appendFileExtension("bak");

		
		if (diagramUri.isFile()) {	// if the diagram exists, process it.
	        resourceSet.getResource(diagramUri, true);

	        for (Resource resource : resourceSet.getResources()) {
	            for (EObject root : resource.getContents()) {
	                if (root instanceof Diagram) {
	                    updateDiagramViews((Diagram) root, correspondence);
	                }
	            }
	        }
	        
	        // backup old file
	        File oldDiagramFile = new File(diagramUri.toFileString());
	        File oldDiagramFileBackup = new File(diagramBackupUri.toFileString());
	        if (!oldDiagramFile.renameTo(oldDiagramFileBackup)) { // renaming failed
	        	throw new FileNotFoundException();
	        }
	        
	        // write new file  
	        for (Resource resource : resourceSet.getResources()) {
	        	if (resource.getURI().equals(diagramUri)) {
	        		resource.save(null);
	        		System.out.println("diagram saved.");
	    	        return true;
	        	}
	        }
        }
		return false;
	}
	
	
    private static void updateDiagramViews(View view, Map<EObject, EObject> correspondence) {
        if (view.getElement()!=null) {
            EObject newElem = correspondence.get(view.getElement());
            
            if (newElem != null) {
            	System.out.println("<==" + view.getElement());
            	System.out.println("Found " + view.getElement() + " --> Updating ... " + newElem);
                view.setElement(newElem);
                System.out.println("==>" + view.getElement());
            } else {
            	System.out.println("Didn't find " + view.getElement() + "\t### " + view.getElement().hashCode());
            }
        }
        for (Object child : view.getChildren()) {
            updateDiagramViews((View) child, correspondence);
        }
    }
	
	
}
