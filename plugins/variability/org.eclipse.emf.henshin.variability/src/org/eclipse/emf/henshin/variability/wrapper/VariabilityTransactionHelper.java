package org.eclipse.emf.henshin.variability.wrapper;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

/**
 * This class offers an interface to allow UI elements to modify the underlying models. 
 * 
 * @author Stefan Schulz
 *
 */
public class VariabilityTransactionHelper {
	
	private VariabilityTransactionHelper() {
		// This class should not be instantiated
	}
	
	private static TransactionalEditingDomain getOrCreateEditingDomain(ModelElement modelElement) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(modelElement);
		if (domain == null) {
			ResourceSet resSet = new ResourceSetImpl();
			resSet.createResource(modelElement.eResource().getURI());
			domain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(resSet);
		}
		return domain;
	}

	static Annotation addAnnotation(ModelElement modelElement, String key, String value) {
		Annotation anno = HenshinFactory.eINSTANCE.createAnnotation();
		anno.setKey(key);
		anno.setValue(value);

		try {
			modelElement.getAnnotations().add(anno);
//			EditingDomain domain = getOrCreateEditingDomain(modelElement);
//			Command command = AddCommand.create(domain, modelElement, HenshinPackage.Literals.MODEL_ELEMENT__ANNOTATIONS, anno);
//			CommandStack stack = domain.getCommandStack();
//			stack.execute(command);
		} catch (IllegalStateException e) {
		}
		return anno;
	}

	public static void setAnnotationValue(ModelElement modelElement, String key, String value) {
		Annotation anno = getAnnotation(modelElement, key);

		if (anno != null && anno.getValue() != null && !anno.getValue().equals(value)) {
			EditingDomain domain = getOrCreateEditingDomain(modelElement);
			Command command = SetCommand.create(domain, anno, HenshinPackage.Literals.ANNOTATION__VALUE, value);
			CommandStack stack = domain.getCommandStack();
			try {
				stack.execute(command);
			} catch (IllegalStateException e) {
			}
		}
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
