package org.eclipse.emf.henshin.variability.wrapper;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

/**
 * This cladss offers an interface to allow UI elements to modify the underlying models. 
 * 
 * @author Stefan Schulz
 *
 */
public class VariabilityTransactionHelper {

	static Annotation addAnnotation(ModelElement modelElement, String key, String value) {
		EditingDomain domain = TransactionUtil.getEditingDomain(modelElement);
		if (domain == null) {
			ResourceSet resSet = new ResourceSetImpl();
			resSet.createResource(modelElement.eResource().getURI());
			domain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(resSet);
		}
		Annotation anno = HenshinFactory.eINSTANCE.createAnnotation();

		anno.setKey(key);
		anno.setValue(value);

		Command command = AddCommand.create(domain, modelElement, HenshinPackage.Literals.ANNOTATION, anno);
		CommandStack stack = domain.getCommandStack();
		stack.execute(command);

		return anno;
	}

	public static void setAnnotationValue(ModelElement modelElement, String key, String value) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(modelElement);
		if (domain == null) {
			ResourceSet resSet = new ResourceSetImpl();
			resSet.createResource(modelElement.eResource().getURI());
			domain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(resSet);
		}
		for (Annotation anno : modelElement.getAnnotations()) {
			if (anno.getKey().equals(key)) {
				Command command = SetCommand.create(domain, anno, HenshinPackage.Literals.ANNOTATION__VALUE, value);
				CommandStack stack = domain.getCommandStack();
				stack.execute(command);
			}
		}
	}

}
