package org.eclipse.emf.henshin.provider.descriptors;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.ModelElement;

/**
 * Provides a property descriptor to display {@link Annotation}s in the property sheet of a {@link ModelElement}.
 * 
 * @author Stefan Schulz
 *
 */
public class AnnotationPropertyDescriptor extends ItemPropertyDescriptor {

	private String annotationKey;

	public AnnotationPropertyDescriptor(AdapterFactory adapterFactory, ResourceLocator resourceLocator,
			String displayName, String description, EStructuralFeature feature, boolean isSettable, boolean multiLine,
			boolean sortChoices, Object staticImage, String category, String[] filterFlags, String annotationKey) {
		super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices,
				staticImage, category, filterFlags);
		this.annotationKey = annotationKey;
	}

	@Override
	public Object getPropertyValue(Object object) {
		if (object instanceof ModelElement) {
			for (Annotation annotation : ((ModelElement) object).getAnnotations()) {
				if (annotation.getKey().equals(annotationKey)) {
					return super.getPropertyValue(annotation);
				}
			}
		}
		return super.getPropertyValue(object);
	}
	
	@Override
	public Object getFeature(Object object) {
		return HenshinPackage.Literals.ANNOTATION__VALUE;
	}
	
	@Override
	protected Object getValue(EObject object, EStructuralFeature feature) {
		if (object instanceof ModelElement) {
			for (Annotation annotation : ((ModelElement) object).getAnnotations()) {
				if (annotation.getKey().equals(annotationKey)) {
					return super.getValue(annotation, feature);
				}
			}
		}
		return super.getValue(object, feature);
	}
	
	@Override
	public void setPropertyValue(Object object, Object value) {
		if (object instanceof ModelElement) {
			for (Annotation annotation : ((ModelElement) object).getAnnotations()) {
				if (annotation.getKey().equals(annotationKey)) {
					super.setPropertyValue(annotation, value);
				}
			}
		}
		super.setPropertyValue(object, value);
	}
	
	@Override
	public String getId(Object object) {
		return annotationKey + super.getId(object);
	}
}
