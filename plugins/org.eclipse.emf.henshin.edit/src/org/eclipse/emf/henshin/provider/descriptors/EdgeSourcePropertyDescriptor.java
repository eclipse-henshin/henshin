/**
 * 
 */
package org.eclipse.emf.henshin.provider.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

/**
 * Property descriptor for the <code>source</code> feature of model class
 * {@link Edge}. This descriptor collects nodes which are provided as a combo
 * box. In particular, only those nodes shall be provided, which are suitable as
 * target.
 * 
 * @author Stefan Jurack
 * 
 */
public class EdgeSourcePropertyDescriptor extends ItemPropertyDescriptor {

	/**
	 * @param adapterFactory
	 * @param resourceLocator
	 * @param displayName
	 * @param description
	 * @param feature
	 */
	public EdgeSourcePropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName,
			String description, EStructuralFeature feature) {
		super(adapterFactory, resourceLocator, displayName, description,
				feature, true, false, true, null, null, null);
	}// constructor

	/**
	 * Collects all nodes, which are provided by the combo box in a related
	 * property sheet.<br>
	 * If the given <code>object</code>, which is an Edge instance, is equipped
	 * with a type, only those nodes are collected, which are in the same graph
	 * and of the required source type. If no type is given, all nodes in the
	 * same graph are provided.
	 * 
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getComboBoxObjects(Object)
	 * 
	 */
	@Override
	protected Collection<?> getComboBoxObjects(Object object) {

		if (object instanceof Edge) {
			Edge edge = (Edge) object;
			Graph graph = edge.getGraph();
			EObject edgeType = edge.getType();
			Collection<Node> nodeList = null;

			if (edgeType != null) {
				EObject srcNodeType = edgeType.eContainer();
				nodeList = new ArrayList<Node>();
				for (Node node : graph.getNodes()) {
					if (node.getType().equals(srcNodeType))
						nodeList.add(node);
				}// for

			} else {
				nodeList = graph.getNodes();
			}// if else

			return Collections.unmodifiableCollection(nodeList);
		}// if
		return super.getComboBoxObjects(object);
	}// getComboBoxObjects

}// class
