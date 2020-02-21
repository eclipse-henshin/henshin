package org.eclipse.emf.henshin.variability.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;

/**
 * This class wraps an instance of {@link org.eclipse.emf.henshin.model.Edge} and adds variability awareness to it.
 * The variability awareness is enabled by adding an {@link org.eclipse.emf.henshin.model.Annotation} to the wrapped object. 
 * 
 * @author Stefan Schulz
 *
 */
public class VariabilityEdge implements Edge, VariabilityGraphElement {
	final Edge edge;
	final Annotation presenceCondition;
	
	static Annotation addVariabilityToEdge(Edge edge, boolean transactional) {
		EList<Annotation> annos = edge.getAnnotations();
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
			return VariabilityTransactionHelper.addAnnotation(edge, VariabilityConstants.PRESENCE_CONDITION, "");
		} else {
			return VariabilityHelper.addAnnotation(edge, VariabilityConstants.PRESENCE_CONDITION, "");
		}
	}
	
	/**
	 * Creates a new {@link org.eclipse.emf.henshin.model.Edge} and makes it variability aware.
	 */
	VariabilityEdge() {
		this(HenshinFactoryImpl.eINSTANCE.createEdge());
	}
	
	/**
	 * Creates a new {@link org.eclipse.emf.henshin.model.Edge} between two {@link org.eclipse.emf.henshin.model.Node}s and makes it variability aware. 
	 * The edge is automatically added to the graph of the source node if not <code>null</code>.
	 * @param source Source node.
	 * @param target Target node.
	 * @param type Edge type.
	 * @return The created edge.
	 */
	VariabilityEdge(Node source, Node target, EReference type) {
		this(HenshinFactoryImpl.eINSTANCE.createEdge(source, target, type));
	}
	
	/**
	 * Adds an {@link org.eclipse.emf.henshin.model.Annotation} to the given {@link org.eclipse.emf.henshin.model.Edge} in order to enable variability awareness.
	 * @param edge
	 */
	VariabilityEdge(Edge edge) {
		this(edge, false);
	}
	
	/**
	 * Adds an {@link org.eclipse.emf.henshin.model.Annotation} to the given {@link org.eclipse.emf.henshin.model.Edge} in order to enable variability awareness.
	 * @param edge
	 * @param transactional
	 */
	VariabilityEdge(Edge edge, boolean transactional) {
		this.edge = edge;
		this.presenceCondition = addVariabilityToEdge(edge, transactional);
	}
	
	public GraphElement getGraphElement() {
		return edge;
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
		return edge.eAdapters();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eDeliver()
	 */
	public boolean eDeliver() {
		return edge.eDeliver();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eSetDeliver(boolean)
	 */
	public void eSetDeliver(boolean deliver) {
		edge.eSetDeliver(deliver);
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eNotify(org.eclipse.emf.common.notify.Notification)
	 */
	public void eNotify(Notification notification) {
		edge.eNotify(notification);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eClass()
	 */
	public EClass eClass() {
		return edge.eClass();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eResource()
	 */
	public Resource eResource() {
		return edge.eResource();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainer()
	 */
	public EObject eContainer() {
		return edge.eContainer();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainingFeature()
	 */
	public EStructuralFeature eContainingFeature() {
		return edge.eContainingFeature();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainmentFeature()
	 */
	public EReference eContainmentFeature() {
		return edge.eContainmentFeature();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContents()
	 */
	public EList<EObject> eContents() {
		return edge.eContents();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eAllContents()
	 */
	public TreeIterator<EObject> eAllContents() {
		return edge.eAllContents();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eIsProxy()
	 */
	public boolean eIsProxy() {
		return edge.eIsProxy();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eCrossReferences()
	 */
	public EList<EObject> eCrossReferences() {
		return edge.eCrossReferences();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public Object eGet(EStructuralFeature feature) {
		return edge.eGet(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature, boolean)
	 */
	public Object eGet(EStructuralFeature feature, boolean resolve) {
		return edge.eGet(feature, resolve);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eSet(org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	public void eSet(EStructuralFeature feature, Object newValue) {
		edge.eSet(feature, newValue);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eIsSet(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean eIsSet(EStructuralFeature feature) {
		return edge.eIsSet(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eUnset(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void eUnset(EStructuralFeature feature) {
		edge.eUnset(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eInvoke(org.eclipse.emf.ecore.EOperation, org.eclipse.emf.common.util.EList)
	 */
	public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException {
		return edge.eInvoke(operation, arguments);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.GraphElement#getAction()
	 */
	public Action getAction() {
		return edge.getAction();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#getActionEdge()
	 */
	public Edge getActionEdge() {
		return edge.getActionEdge();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.ModelElement#getAnnotations()
	 */
	public EList<Annotation> getAnnotations() {
		return edge.getAnnotations();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#getGraph()
	 */
	public Graph getGraph() {
		return edge.getGraph();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#getIndex()
	 */
	public String getIndex() {
		return edge.getIndex();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#getIndexConstant()
	 */
	public Integer getIndexConstant() {
		return edge.getIndexConstant();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#getSource()
	 */
	public Node getSource() {
		return edge.getSource();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#getTarget()
	 */
	public Node getTarget() {
		return edge.getTarget();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#getType()
	 */
	public EReference getType() {
		return edge.getType();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.GraphElement#setAction(org.eclipse.emf.henshin.model.Action)
	 */
	public void setAction(Action arg0) {
		edge.setAction(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#setGraph(org.eclipse.emf.henshin.model.Graph)
	 */
	public void setGraph(Graph arg0) {
		edge.setGraph(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#setIndex(java.lang.String)
	 */
	public void setIndex(String arg0) {
		edge.setIndex(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#setSource(org.eclipse.emf.henshin.model.Node)
	 */
	public void setSource(Node arg0) {
		edge.setSource(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#setTarget(org.eclipse.emf.henshin.model.Node)
	 */
	public void setTarget(Node arg0) {
		edge.setTarget(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Edge#setType(org.eclipse.emf.ecore.EReference)
	 */
	public void setType(EReference arg0) {
		edge.setType(arg0);
	}
	
	@Override
	public int hashCode() {
		return edge.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return edge.equals(obj);
	}
}
