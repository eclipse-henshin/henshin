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
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;

/**
 * This class wraps an instance of {@link org.eclipse.emf.henshin.model.Attribute} and adds variability awareness to it.
 * The variability awareness is enabled by adding an {@link org.eclipse.emf.henshin.model.Annotation} to the wrapped object. 
 * 
 * @author Stefan Schulz
 *
 */
public class VariabilityAttribute implements Attribute, VariabilityGraphElement {
	final Attribute attribute;
	final Annotation presenceCondition;
	
	static Annotation addVariabilityToAttribute(Attribute attribute, boolean transactional) {
		EList<Annotation> annos = attribute.getAnnotations();
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
		} else if (transactional){
			return VariabilityTransactionHelper.addAnnotation(attribute, VariabilityConstants.PRESENCE_CONDITION, "");
		} else {
			return VariabilityHelper.addAnnotation(attribute, VariabilityConstants.PRESENCE_CONDITION, "");
		}
	}
	
	/**
	 * Creates a new {@link org.eclipse.emf.henshin.model.Attribute} and makes it variability aware.
	 */
	VariabilityAttribute() {
		this(HenshinFactoryImpl.eINSTANCE.createAttribute());
	}
	
	/**
	 * Creates a new {@link org.eclipse.emf.henshin.model.Attribute}, adds it to the given {@link org.eclipse.emf.henshin.model.Node} and makes it variability aware. 
	 * @param node the Node.
	 * @param type Attribute type.
	 * @param value Attribute value.
	 */
	VariabilityAttribute(Node node, EAttribute type, String value) {
		this(HenshinFactoryImpl.eINSTANCE.createAttribute(node, type, value));
	}
	
	/**
	 * Adds an {@link org.eclipse.emf.henshin.model.Annotation} to the given {@link org.eclipse.emf.henshin.model.Attribute} in order to enable variability awareness.
	 * @param attribute
	 */
	VariabilityAttribute(Attribute attribute) {
		this(attribute, false);
	}
	
	/**
	 * Adds an {@link org.eclipse.emf.henshin.model.Annotation} to the given {@link org.eclipse.emf.henshin.model.Attribute} in order to enable variability awareness.
	 * @param attribute
	 * @param transactional
	 */
	VariabilityAttribute(Attribute attribute, boolean transactional) {
		this.attribute = attribute;
		this.presenceCondition = addVariabilityToAttribute(attribute, transactional);
	}
	
	public GraphElement getGraphElement() {
		return attribute;
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
		return attribute.eAdapters();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eDeliver()
	 */
	public boolean eDeliver() {
		return attribute.eDeliver();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eSetDeliver(boolean)
	 */
	public void eSetDeliver(boolean deliver) {
		attribute.eSetDeliver(deliver);
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eNotify(org.eclipse.emf.common.notify.Notification)
	 */
	public void eNotify(Notification notification) {
		attribute.eNotify(notification);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eClass()
	 */
	public EClass eClass() {
		return attribute.eClass();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eResource()
	 */
	public Resource eResource() {
		return attribute.eResource();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainer()
	 */
	public EObject eContainer() {
		return attribute.eContainer();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainingFeature()
	 */
	public EStructuralFeature eContainingFeature() {
		return attribute.eContainingFeature();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainmentFeature()
	 */
	public EReference eContainmentFeature() {
		return attribute.eContainmentFeature();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContents()
	 */
	public EList<EObject> eContents() {
		return attribute.eContents();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eAllContents()
	 */
	public TreeIterator<EObject> eAllContents() {
		return attribute.eAllContents();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eIsProxy()
	 */
	public boolean eIsProxy() {
		return attribute.eIsProxy();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eCrossReferences()
	 */
	public EList<EObject> eCrossReferences() {
		return attribute.eCrossReferences();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public Object eGet(EStructuralFeature feature) {
		return attribute.eGet(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature, boolean)
	 */
	public Object eGet(EStructuralFeature feature, boolean resolve) {
		return attribute.eGet(feature, resolve);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eSet(org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	public void eSet(EStructuralFeature feature, Object newValue) {
		attribute.eSet(feature, newValue);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eIsSet(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean eIsSet(EStructuralFeature feature) {
		return attribute.eIsSet(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eUnset(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void eUnset(EStructuralFeature feature) {
		attribute.eUnset(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eInvoke(org.eclipse.emf.ecore.EOperation, org.eclipse.emf.common.util.EList)
	 */
	public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException {
		return attribute.eInvoke(operation, arguments);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.GraphElement#getAction()
	 */
	public Action getAction() {
		return attribute.getAction();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Attribute#getActionAttribute()
	 */
	public Attribute getActionAttribute() {
		return attribute.getActionAttribute();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.ModelElement#getAnnotations()
	 */
	public EList<Annotation> getAnnotations() {
		return attribute.getAnnotations();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Attribute#getConstant()
	 */
	public Object getConstant() {
		return attribute.getConstant();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.GraphElement#getGraph()
	 */
	public Graph getGraph() {
		return attribute.getGraph();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Attribute#getNode()
	 */
	public Node getNode() {
		return attribute.getNode();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Attribute#getType()
	 */
	public EAttribute getType() {
		return attribute.getType();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Attribute#getValue()
	 */
	public String getValue() {
		return attribute.getValue();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Attribute#isNull()
	 */
	public boolean isNull() {
		return attribute.isNull();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.GraphElement#setAction(org.eclipse.emf.henshin.model.Action)
	 */
	public void setAction(Action arg0) {
		attribute.setAction(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Attribute#setNode(org.eclipse.emf.henshin.model.Node)
	 */
	public void setNode(Node arg0) {
		attribute.setNode(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Attribute#setType(org.eclipse.emf.ecore.EAttribute)
	 */
	public void setType(EAttribute arg0) {
		attribute.setType(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Attribute#setValue(java.lang.String)
	 */
	public void setValue(String arg0) {
		attribute.setValue(arg0);
	}
	
	@Override
	public int hashCode() {
		return attribute.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return attribute.equals(obj);
	}
}
