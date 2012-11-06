/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.migration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

public class DiagramTransformation {
	
	public static boolean transformDiagram(Map<EObject, EObject> correspondence, Map<Rule, Rule> amalgamationCorrespondence, ResourceSet resourceSet, URI diagramUri) throws IOException {
		if (diagramUri == null) {
			return false;
		}
		URI diagramBackupUri = diagramUri.appendFileExtension("bak");

		
        for (Resource resource : resourceSet.getResources()) {
            for (EObject root :  resource.getContents()) {
                if (root instanceof Diagram) {
                    updateDiagramViews((Diagram) root, correspondence, amalgamationCorrespondence);
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
		return false;
	}
	
	
    private static void updateDiagramViews(View view, Map<EObject, EObject> correspondence, Map<Rule, Rule> amalgamationCorrespondence) {
    	
    	// get element
        if (view.getElement()!=null) {
            EObject newElem = correspondence.get(view.getElement());
            
            
            if (newElem != null) {
            	if (newElem instanceof Rule) {	// might be a rule formerly contained in an amalgamationUnit
            		// XXX: possibly remove this altogether?
            		// No benefit, as edge to edge mapping is completely lost during the amalgamationUnit
            		// building/copying process, so amalgamationUnit layouts will always be broken
            		// if this is removed, the kernel rule's layout will be conserved
            		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ amalgamationUnit? " + newElem);
            		System.out.println("$$$$$$$$$$$$$$$$$$$$$ k:" + amalgamationCorrespondence.containsKey(newElem) + " v:" + amalgamationCorrespondence.containsValue(newElem));
            		System.out.println("$$$$$$$$$$$$$$$$$$$$$   " + amalgamationCorrespondence.get(newElem));
            		if (amalgamationCorrespondence.get(newElem) != null) {
            			System.out.println("found amalgamation unit correspondence, updating: " + newElem + " -> " + amalgamationCorrespondence.get(newElem));
            			newElem = amalgamationCorrespondence.get(newElem);
            		}
            	}
            	System.out.println("<==" + view.getElement());
            	System.out.println("Found " + view.getElement() + " --> Updating ... " + newElem);
                view.setElement(newElem);
                System.out.println("==>" + view.getElement());
            } else {
            	System.out.println("Didn't find " + view.getElement() + "\t### " + view.getElement().hashCode());
            }
            
            // source edges
            System.out.println("\t source edges: (" + view.getSourceEdges().size() + ")" );
            for (int i = 0; i < view.getSourceEdges().size(); i++) {
            	System.out.println("\t" + view.getSourceEdges().get(i));
          	
            	Edge se = (Edge) view.getSourceEdges().get(i);
            	System.out.println("\t el: " + se.getElement());
            	
            	if (se != null) {
            		EObject newEdge = correspondence.get(se.getElement());
	            	if (newEdge != null) {
	            		System.out.println("\ts" + se.getElement() + " --> " + newEdge);
	            		se.setElement(newEdge);
	            	}
            	}
            }
            
            // target edges
            System.out.println("\t target edges: (" + view.getTargetEdges().size() + ")");
            for (int i = 0; i < view.getTargetEdges().size(); i++) {
            	Edge te = (Edge) view.getTargetEdges().get(i);
            	
            	if (te != null) {
	            	EObject newEdge = correspondence.get(te.getElement());
	            	if (newEdge != null) {
	            		System.out.println("\tt" + te.getElement() + " --> " + newEdge);
	            		te.setElement(newEdge);
	            	}
            	}
            }
            
            // annotations (e.g. for hiding components)
            System.out.println("\t annotations (" + view.getEAnnotations().size() + ")");
            for (EAnnotation eAnn : view.getEAnnotations()) {
            	for (int i = 0; i < eAnn.getReferences().size(); i++) {
            		EObject eo = eAnn.getReferences().get(i);
            	
            		EObject newAnnoRef = correspondence.get(eo);
            		if (newAnnoRef != null) {
                		System.out.println("\t" + eo + " --> " + newAnnoRef);
                		eAnn.getReferences().remove(i);
                		eAnn.getReferences().add(i, newAnnoRef);
            		} else {
            			System.out.println("\t couldn't update annotation: " + eo);
            		}
            	}

            }
            
        }
        for (Object child : view.getChildren()) {
            updateDiagramViews((View) child, correspondence, amalgamationCorrespondence);
        }
    }
	

	
}
