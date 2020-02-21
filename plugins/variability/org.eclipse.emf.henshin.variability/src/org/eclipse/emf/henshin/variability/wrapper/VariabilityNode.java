package org.eclipse.emf.henshin.variability.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;

/**
 * This class wraps an instance of {@link org.eclipse.emf.henshin.model.Node} and adds variability awareness to it.
 * The variability awareness is enabled by adding an {@link org.eclipse.emf.henshin.model.Annotation} to the wrapped object. 
 * 
 * @author Stefan Schulz
 *
 */
public class VariabilityNode implements Node, VariabilityGraphElement {
	final Node node;
	final Annotation presenceCondition;
	
	static Annotation addVariabilityToNode(Node node, boolean transactional) {
		EList<Annotation> annos = node.getAnnotations();
		Iterator<Annotation> it = annos.iterator();
		Annotation pc = null;
		while(it.hasNext()) {
			Annotation anno = it.next();
			if(anno.getKey().equals(VariabilityConstants.PRESENCE_CONDITION)) {
				pc = anno;
				break;
			}
		}
		
		if(pc != null) {
			return pc;
		} else if (transactional) {
			return VariabilityTransactionHelper.addAnnotation(node, VariabilityConstants.PRESENCE_CONDITION, "");
		} else {
			return VariabilityHelper.addAnnotation(node, VariabilityConstants.PRESENCE_CONDITION, "");
		}
	}
	
	/**
	 * Creates a new {@link org.eclipse.emf.henshin.model.Node} and makes it variability aware.
	 */
	VariabilityNode() {
		this(HenshinFactoryImpl.eINSTANCE.createNode());
	}
	
	/**
	 * Creates a new {@link org.eclipse.emf.henshin.model.Node}, add it to a given graph and make it variability aware.
	 * @param graph The graph.
	 * @param type The type of the node.
	 * @param name The name of the node.
	 */
	VariabilityNode(Graph graph, EClass type, String name) {
		this(HenshinFactoryImpl.eINSTANCE.createNode(graph, type, name));
	}
	
	/**
	 * Adds an {@link org.eclipse.emf.henshin.model.Annotation} to the given {@link org.eclipse.emf.henshin.model.Node} in order to enable variability awareness.
	 * @param node
	 */
	VariabilityNode(Node node) {
		this(node, false);
	}
	
	/**
	 * Adds an {@link org.eclipse.emf.henshin.model.Annotation} to the given {@link org.eclipse.emf.henshin.model.Node} in order to enable variability awareness.
	 * @param node
	 * @param transactional
	 */
	VariabilityNode(Node node, boolean transactional) {
		this.node = node;
		this.presenceCondition = addVariabilityToNode(node, transactional);
	}
	
	public GraphElement getGraphElement() {
		return node;
	}
	
	/**
	 * Returns the presence condition of the Attribute.
	 * @return the presence condition.
	 */
	public String getPresenceCondition() {
		return presenceCondition.getValue();
	}
	
	/**
	 * Sets the presence condition of the Attribute to the given String.
 	 * @param condition the presence condition to be set.
 	 */
	public void setPresenceCondition(String condition) {
		presenceCondition.setValue(condition);
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eAdapters()
	 */
	public EList<Adapter> eAdapters() {
		return node.eAdapters();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eDeliver()
	 */
	public boolean eDeliver() {
		return node.eDeliver();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eSetDeliver(boolean)
	 */
	public void eSetDeliver(boolean deliver) {
		node.eSetDeliver(deliver);
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eNotify(org.eclipse.emf.common.notify.Notification)
	 */
	public void eNotify(Notification notification) {
		node.eNotify(notification);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eClass()
	 */
	public EClass eClass() {
		return node.eClass();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eResource()
	 */
	public Resource eResource() {
		return node.eResource();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainer()
	 */
	public EObject eContainer() {
		return node.eContainer();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainingFeature()
	 */
	public EStructuralFeature eContainingFeature() {
		return node.eContainingFeature();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainmentFeature()
	 */
	public EReference eContainmentFeature() {
		return node.eContainmentFeature();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContents()
	 */
	public EList<EObject> eContents() {
		return node.eContents();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eAllContents()
	 */
	public TreeIterator<EObject> eAllContents() {
		return node.eAllContents();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eIsProxy()
	 */
	public boolean eIsProxy() {
		return node.eIsProxy();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eCrossReferences()
	 */
	public EList<EObject> eCrossReferences() {
		return node.eCrossReferences();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public Object eGet(EStructuralFeature feature) {
		return node.eGet(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature, boolean)
	 */
	public Object eGet(EStructuralFeature feature, boolean resolve) {
		return node.eGet(feature, resolve);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eSet(org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	public void eSet(EStructuralFeature feature, Object newValue) {
		node.eSet(feature, newValue);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eIsSet(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean eIsSet(EStructuralFeature feature) {
		return node.eIsSet(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eUnset(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void eUnset(EStructuralFeature feature) {
		node.eUnset(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eInvoke(org.eclipse.emf.ecore.EOperation, org.eclipse.emf.common.util.EList)
	 */
	public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException {
		return node.eInvoke(operation, arguments);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.GraphElement#getAction()
	 */
	public Action getAction() {
		return node.getAction();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getActionAttributes(org.eclipse.emf.henshin.model.Action)
	 */
	public EList<Attribute> getActionAttributes(Action arg0) {
		return node.getActionAttributes(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getActionNode()
	 */
	public Node getActionNode() {
		return node.getActionNode();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getAllEdges()
	 */
	public EList<Edge> getAllEdges() {
		return node.getAllEdges();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.ModelElement#getAnnotations()
	 */
	public EList<Annotation> getAnnotations() {
		return node.getAnnotations();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getAttribute(org.eclipse.emf.ecore.EAttribute)
	 */
	public Attribute getAttribute(EAttribute arg0) {
		return node.getAttribute(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getAttributes()
	 */
	public EList<Attribute> getAttributes() {
		return node.getAttributes();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NamedElement#getDescription()
	 */
	public String getDescription() {
		return node.getDescription();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getGraph()
	 */
	public Graph getGraph() {
		return node.getGraph();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getIncoming()
	 */
	public EList<Edge> getIncoming() {
		return node.getIncoming();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getIncoming(org.eclipse.emf.ecore.EReference, org.eclipse.emf.henshin.model.Node)
	 */
	public Edge getIncoming(EReference arg0, Node arg1) {
		return node.getIncoming(arg0, arg1);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getIncoming(org.eclipse.emf.ecore.EReference)
	 */
	public EList<Edge> getIncoming(EReference arg0) {
		return node.getIncoming(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NamedElement#getName()
	 */
	public String getName() {
		return node.getName();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getOutgoing()
	 */
	public EList<Edge> getOutgoing() {
		return node.getOutgoing();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getOutgoing(org.eclipse.emf.ecore.EReference, org.eclipse.emf.henshin.model.Node)
	 */
	public Edge getOutgoing(EReference arg0, Node arg1) {
		return node.getOutgoing(arg0, arg1);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getOutgoing(org.eclipse.emf.ecore.EReference)
	 */
	public EList<Edge> getOutgoing(EReference arg0) {
		return node.getOutgoing(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#getType()
	 */
	public EClass getType() {
		return node.getType();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.GraphElement#setAction(org.eclipse.emf.henshin.model.Action)
	 */
	public void setAction(Action arg0) {
		node.setAction(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NamedElement#setDescription(java.lang.String)
	 */
	public void setDescription(String arg0) {
		node.setDescription(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#setGraph(org.eclipse.emf.henshin.model.Graph)
	 */
	public void setGraph(Graph arg0) {
		node.setGraph(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NamedElement#setName(java.lang.String)
	 */
	public void setName(String arg0) {
		node.setName(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Node#setType(org.eclipse.emf.ecore.EClass)
	 */
	public void setType(EClass arg0) {
		node.setType(arg0);
	}
	
	@Override
	public int hashCode() {
		return node.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return node.equals(obj);
	}
}
