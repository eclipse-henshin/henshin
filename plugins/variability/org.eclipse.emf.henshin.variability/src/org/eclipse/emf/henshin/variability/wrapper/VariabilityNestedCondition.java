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
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;

/**
 * This class wraps an instance of {@link org.eclipse.emf.henshin.model.NestedCondition} and adds variability awareness to it.
 * The variability awareness is enabled by adding an {@link org.eclipse.emf.henshin.model.Annotation} to the wrapped object. 
 * 
 * @author Stefan Schulz
 *
 */
public class VariabilityNestedCondition implements NestedCondition {
	final NestedCondition nestedCondition;
	final Annotation presenceCondition;
	
	static Annotation addVariabilityToNestedCondition(NestedCondition condition, boolean transactional) {
		EList<Annotation> annos = condition.getAnnotations();
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
			return VariabilityTransactionHelper.addAnnotation(condition, VariabilityConstants.PRESENCE_CONDITION, "");
		} else {
			return VariabilityHelper.addAnnotation(condition, VariabilityConstants.PRESENCE_CONDITION, "");
		}
	}
	
	/**
	 * Creates a new {@link org.eclipse.emf.henshin.model.NestedCondition} and makes it variability aware.
	 */
	VariabilityNestedCondition() {
		this(HenshinFactoryImpl.eINSTANCE.createNestedCondition());
	}

	/**
	 * Adds an {@link org.eclipse.emf.henshin.model.Annotation} to the given {@link org.eclipse.emf.henshin.model.NestedCondition} in order to enable variability awareness.
	 * @param condition
	 */
	VariabilityNestedCondition(NestedCondition condition) {
		this(condition, false);
	}
	
	/**
	 * Adds an {@link org.eclipse.emf.henshin.model.Annotation} to the given {@link org.eclipse.emf.henshin.model.NestedCondition} in order to enable variability awareness.
	 * @param condition
	 * @param transactional
	 */
	VariabilityNestedCondition(NestedCondition condition, boolean transactional) {
		this.nestedCondition = condition;
		this.presenceCondition = addVariabilityToNestedCondition(condition, transactional);
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
		return nestedCondition.eAdapters();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eDeliver()
	 */
	public boolean eDeliver() {
		return nestedCondition.eDeliver();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eSetDeliver(boolean)
	 */
	public void eSetDeliver(boolean deliver) {
		nestedCondition.eSetDeliver(deliver);
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eNotify(org.eclipse.emf.common.notify.Notification)
	 */
	public void eNotify(Notification notification) {
		nestedCondition.eNotify(notification);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eClass()
	 */
	public EClass eClass() {
		return nestedCondition.eClass();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eResource()
	 */
	public Resource eResource() {
		return nestedCondition.eResource();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainer()
	 */
	public EObject eContainer() {
		return nestedCondition.eContainer();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainingFeature()
	 */
	public EStructuralFeature eContainingFeature() {
		return nestedCondition.eContainingFeature();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainmentFeature()
	 */
	public EReference eContainmentFeature() {
		return nestedCondition.eContainmentFeature();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContents()
	 */
	public EList<EObject> eContents() {
		return nestedCondition.eContents();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eAllContents()
	 */
	public TreeIterator<EObject> eAllContents() {
		return nestedCondition.eAllContents();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eIsProxy()
	 */
	public boolean eIsProxy() {
		return nestedCondition.eIsProxy();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eCrossReferences()
	 */
	public EList<EObject> eCrossReferences() {
		return nestedCondition.eCrossReferences();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public Object eGet(EStructuralFeature feature) {
		return nestedCondition.eGet(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature, boolean)
	 */
	public Object eGet(EStructuralFeature feature, boolean resolve) {
		return nestedCondition.eGet(feature, resolve);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eSet(org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	public void eSet(EStructuralFeature feature, Object newValue) {
		nestedCondition.eSet(feature, newValue);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eIsSet(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean eIsSet(EStructuralFeature feature) {
		return nestedCondition.eIsSet(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eUnset(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void eUnset(EStructuralFeature feature) {
		nestedCondition.eUnset(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eInvoke(org.eclipse.emf.ecore.EOperation, org.eclipse.emf.common.util.EList)
	 */
	public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException {
		return nestedCondition.eInvoke(operation, arguments);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.ModelElement#getAnnotations()
	 */
	public EList<Annotation> getAnnotations() {
		return nestedCondition.getAnnotations();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NestedCondition#getConclusion()
	 */
	public Graph getConclusion() {
		return nestedCondition.getConclusion();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NestedCondition#getHost()
	 */
	public Graph getHost() {
		return nestedCondition.getHost();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NestedCondition#getMappings()
	 */
	public MappingList getMappings() {
		return nestedCondition.getMappings();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Formula#isFalse()
	 */
	public boolean isFalse() {
		return nestedCondition.isFalse();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NestedCondition#isNAC()
	 */
	public boolean isNAC() {
		return nestedCondition.isNAC();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NestedCondition#isPAC()
	 */
	public boolean isPAC() {
		return nestedCondition.isPAC();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Formula#isTrue()
	 */
	public boolean isTrue() {
		return nestedCondition.isTrue();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NestedCondition#setConclusion(org.eclipse.emf.henshin.model.Graph)
	 */
	public void setConclusion(Graph arg0) {
		nestedCondition.setConclusion(arg0);
	}
	
	@Override
	public int hashCode() {
		return nestedCondition.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return nestedCondition.equals(obj);
	}
}
