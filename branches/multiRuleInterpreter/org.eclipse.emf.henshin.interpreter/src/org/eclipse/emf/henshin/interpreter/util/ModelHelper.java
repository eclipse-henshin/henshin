/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.UnaryFormula;

public class ModelHelper {
	
	public static String getUniqueNodeName(EClassifier type) {
		return type.getEPackage().getNsURI() + "#" + type.getName();
	}
	
	/**
	 * Checks whether the value of the given attribute corresponds to a
	 * parameter of the rule.
	 * 
	 * @param rule
	 *            The rule which contains the parameters.
	 * @param attribute
	 *            The attribute, which value should be checked.
	 * @return true, if the attribute value equals a parameter name.
	 */
	public static boolean attributeIsParameter(Rule rule, Attribute attribute) {
		String potentialParameter = attribute.getValue();
		boolean found = false;
		for (Parameter parameter : rule.getParameters()) {
			found = parameter.getName().equals(potentialParameter);
			if (found)
				break;
		}// for
		
		return found;
	}
	
	/**
	 * Checks whether the specified node is part of the mappings.
	 * 
	 * @param mappings
	 *            A list of mappings.
	 * @param node
	 *            The node which should be checked for origin or image in one of
	 *            the mappings.
	 * @return true, if the node is mapped
	 */
	public static boolean isNodeMapped(Collection<Mapping> mappings, Node node) {
		return getRemoteNode(mappings, node) != null;
	}
	
	/**
	 * Checks whether the specified edge is part of the mappings.
	 * 
	 * @param mappings
	 *            A list of mappings.
	 * @param node
	 *            The edge which should be checked for origin or image.
	 * @return true, if the edge is mapped
	 */
	public static boolean isEdgeMapped(List<Mapping> mappings, Edge edge) {
		Node sourceNode = edge.getSource();
		Node targetNode = edge.getTarget();
		
		Node remoteSourceNode = getRemoteNode(mappings, sourceNode);
		Node remoteTargetNode = getRemoteNode(mappings, targetNode);
		
		if (remoteSourceNode != null && remoteTargetNode != null) {
			for (Edge remoteEdge : remoteSourceNode.getOutgoing()) {
				if (remoteEdge.getTarget() == remoteTargetNode
						&& remoteEdge.getType() == edge.getType())
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns the image or origin of the specified node. If the node is not
	 * part of a mapping, null will be returned. If the node is part of multiple
	 * mappings, only the first remote node is returned.
	 * 
	 * @param mappings
	 * @param node
	 * @return
	 */
	public static Node getRemoteNode(Collection<Mapping> mappings, Node node) {
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin() == node)
				return mapping.getImage();
			if (mapping.getImage() == node)
				return mapping.getOrigin();
		}
		
		return null;
	}
	
	public static Collection<Node> getSourceNodes(Collection<Mapping> mappings, Node node) {
		Collection<Node> result = new ArrayList<Node>();
		
		for (Mapping mapping : mappings) {
			if (mapping.getImage() == node)
				result.add(mapping.getOrigin());
		}
		
		return result;
	}
	
	public static Collection<Node> getTargetNodes(Collection<Mapping> mappings, Node node) {
		Collection<Node> result = new ArrayList<Node>();
		
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin() == node)
				result.add(mapping.getImage());
		}
		
		return result;
	}
	
	/**
	 * Returns the nested application condition the conclusion graph belongs to.
	 * 
	 * @param formula
	 *            The topmost formula which shall be checked.
	 * @param graph
	 *            The conclusion graph.
	 * @return The direct nested application condition this graph belongs to.
	 */
	public static NestedCondition getParentCondition(Formula formula, Graph graph) {
		if (formula instanceof NestedCondition) {
			NestedCondition nestedCondition = (NestedCondition) formula;
			if (nestedCondition.getConclusion() == graph)
				return nestedCondition;
		} else if (formula instanceof BinaryFormula) {
			getParentCondition(((BinaryFormula) formula).getLeft(), graph);
			getParentCondition(((BinaryFormula) formula).getRight(), graph);
		} else if (formula instanceof UnaryFormula) {
			getParentCondition(((UnaryFormula) formula).getChild(), graph);
		}
		
		return null;
	}
	
	public static Object castDown(Object complexValue, String type) {
		if (complexValue instanceof Double) {
			if (type.equals("int")) {
				return ((Double) complexValue).intValue();
			} else if (type.equals("long")) {
				return ((Double) complexValue).longValue();
			} else if (type.equals("float")) {
				return ((Double) complexValue).floatValue();
			} else if (type.equals("short")) {
				return ((Double) complexValue).shortValue();
			} else if (type.equals("byte")) {
				return ((Double) complexValue).byteValue();
			}
		}
		
		return complexValue;
	}
	
	public static void registerFileExtension(String extension) {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(extension,
				new XMIResourceFactoryImpl());
	}
	
	/**
	 * Tries to open the Ecore file at the given URI location. If successful,
	 * the newly loaded EPackage instance is registered in the global EPackage
	 * registry (<code>EPackage.Registry</code>) and returned.
	 * 
	 * @param ecoreFileUri
	 * @return
	 */
	public static EPackage registerEPackageByEcoreFile(URI ecoreFileUri) {
		EPackage p = registerEPackageByEcoreFile(ecoreFileUri, null);
		if (p != null)
			EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
		return p;
	}// registerEPackageByEcoreFile
	
	public static void registerEPackage(EPackage ePackage) {
		EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
	}
	
	/**
	 * Tries to open the Ecore file at the given URI location in the context of
	 * the given ResourceSet. If successful, the newly loaded EPackage is
	 * registered in the local EPackage registry of the ResourceSet and
	 * returned.
	 * 
	 * @param ecoreFileUri
	 * @param rs
	 * @return
	 */
	public static EPackage registerEPackageByEcoreFile(URI ecoreFileUri, ResourceSet rs) {
		EPackage result = null;
		if (rs == null)
			rs = new ResourceSetImpl();
		Resource packageResource = rs.createResource(ecoreFileUri);
		if (packageResource != null) {
			try {
				packageResource.load(null);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}// try catch
			
			if ((packageResource.getContents() != null)
					&& (packageResource.getContents().size() > 0)) {
				EObject tmp = packageResource.getContents().get(0);
				if (tmp != null && tmp instanceof EPackage) {
					result = (EPackage) tmp;
					rs.getPackageRegistry().put(result.getNsURI(), result);
				}// if
			}// if
		}// if
		
		return result;
	}// registerEPackageByEcoreFile
	
	public static void saveFile(String filename, EObject root) {
		Resource resource = new XMLResourceImpl(URI.createFileURI(filename));
		resource.getContents().add(root);
		try {
			resource.save(null);
		} catch (IOException e) {
		}
	}
	
	public static EObject loadFile(String filename) {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.getResource(URI.createFileURI(filename), true);
		return resource.getContents().get(0);
	}
	
	public static Map<Node, EObject> createPrematch(TransformationUnit unit,
			Map<Parameter, Object> parameterValues) {
		
		Map<Node, EObject> prematch = new HashMap<Node, EObject>();
		
		Rule rule = null;
		if (unit instanceof Rule)
			rule = (Rule) unit;
		else if (unit instanceof AmalgamationUnit)
			rule = ((AmalgamationUnit) unit).getKernelRule();
		
		if (rule != null) {
			for (Parameter parameter : unit.getParameters()) {
				Node node = rule.getNodeByName(parameter.getName(), true);
				if (node != null)
					prematch.put(node, (EObject) parameterValues.get(parameter));
			}
		}
		return prematch;
	}
	
	/**
	 * Renames the given attribute value to the new
	 * 
	 * @param attribute
	 * @param oldName
	 * @param newName
	 */
	public static void renameParameterInAttribute(Attribute attribute, String oldName,
			String newName) {
		// TODO: do a real parse on the value
		if (attribute.getValue().equals(oldName))
			attribute.setValue(newName);
	}
}
