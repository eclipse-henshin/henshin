package org.eclipse.emf.henshin.mwe;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.mwe.SimpleEObjectCreator.SlotAssignment.Attribute;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent2;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;

public class SimpleEObjectCreator extends AbstractWorkflowComponent2 {
	
	Collection<SlotAssignment> assignments = new ArrayList<SlotAssignment>();
	
	public void addSlotAssignment(SlotAssignment assignemt) {
		assignments.add(assignemt);
	}
	
	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor, Issues issues) {
		for (SlotAssignment assignment : assignments) {
			EPackage pack = EPackage.Registry.INSTANCE.getEPackage(assignment.ePackage);			
			EClassifier eClassifier = pack.getEClassifier(assignment.eClassifier);			
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier; 
				EObject obj = pack.getEFactoryInstance().create(eClass);
				for(Attribute attr:assignment.attributes){
					EStructuralFeature feature = eClass.getEStructuralFeature(attr.name); 
					if(feature instanceof EAttribute){
						obj.eSet(feature, attr.value);
					}
				}
				ctx.set(assignment.slot, obj);
			} else if (eClassifier instanceof EDataType) {
				ctx.set(assignment.slot,
						pack.getEFactoryInstance().createFromString((EDataType) eClassifier,
								assignment.value));
			} else {
				throw new WorkflowInterruptedException("Unable to create " + assignment.ePackage
						+ "::" + assignment.eClassifier);
			}
		}
	}
	
	public static class SlotAssignment {
		String slot;
		String ePackage;
		String eClassifier;
		
		String value;
		
		Collection<Attribute> attributes = new ArrayList<Attribute>();
		
		public void setValue(String value) {
			this.value = value;
		}
		
		public void setePackage(String ePackage) {
			this.ePackage = ePackage;
		}
		
		public void setSlot(String slot) {
			this.slot = slot;
		}
		
		public void seteClassifier(String eClassifier) {
			this.eClassifier = eClassifier;
		}
		
		public void addAttribute(Attribute attr){
			this.attributes.add(attr);
		}
		
		public static class Attribute{
			String name;
			String value;
			
			public void setName(String name) {
				this.name = name;
			}
			
			public void setValue(String value) {
				this.value = value;
			}
			
		}
		
	}
	
	
	
	
}
