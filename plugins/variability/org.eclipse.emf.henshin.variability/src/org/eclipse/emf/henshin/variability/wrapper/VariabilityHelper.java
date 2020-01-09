package org.eclipse.emf.henshin.variability.wrapper;

import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.ModelElement;

public class VariabilityHelper {
	private VariabilityHelper() {
		// This class should not be instantiated
	}

	static Annotation addAnnotation(ModelElement modelElement, String key, String value) {
		Annotation anno = HenshinFactory.eINSTANCE.createAnnotation();

		anno.setKey(key);
		anno.setValue(value);

		modelElement.getAnnotations().add(anno);
		return anno;
	}

	public static void setAnnotationValue(ModelElement modelElement, String key, String value) {
		Annotation anno = getAnnotation(modelElement, key);

		if (anno != null)
			anno.setValue(value);
	}

	private static Annotation getAnnotation(ModelElement modelElement, String key) {
		if (modelElement.getAnnotations() != null) {
			for (Annotation anno : modelElement.getAnnotations()) {
				if (anno.getKey().equals(key)) {
					return anno;
				}
			}
		}
		return null;
	}

}
