package org.eclipse.emf.henshin.interpreter;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public interface Change {
	
	public interface ObjectChange extends Change {
		
		EObject getObject();

		boolean isCreate();
		
	}

	public interface AttributeChange extends Change {
		
		EObject getObject();
		
		EAttribute getAttribute();
		
		Object getOldValue();
		
		Object getNewValue();
		
	}

	public interface ReferenceChange extends Change {
		
		EObject getSource();
		
		EObject getTarget();
		
		EReference getReference();
		
		boolean isCreate();
		
	}

	public interface ComplexChange extends Change {
		
		List<Change> getChanges();
		
	}
	
	EGraph getEGraph();
	
	void applyAndReverse();
	
}
