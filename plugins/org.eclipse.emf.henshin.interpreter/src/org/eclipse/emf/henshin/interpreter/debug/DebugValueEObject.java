package org.eclipse.emf.henshin.interpreter.debug;

import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition;

public class DebugValueEObject extends HenshinDebugValue {

	private EObject value;

	public DebugValueEObject(IDebugTarget target, EGraph graph, String declaredType, EObject value, int indexInDomain) {
		super(target, graph, declaredType);
		this.value = value;
		this.indexInDomain = indexInDomain;

		EObject valueEObject = (EObject) value;

		if (valueEObject == null) {
			valueString = "";
			actualType = "";
			childrenVariables = null;
		} else if (isPrimitive(valueEObject)) {
			valueString = valueEObject.toString();
			actualType = ((EAttribute) valueEObject).getEType().getInstanceClassName();
			childrenVariables = null;
		} else {
			// initialize the children variable array (the children variables
			// themselves get initialized in getVariables())
			List<EStructuralFeature> structuralFeatures = valueEObject.eClass().getEAllStructuralFeatures();
			valueString = DebugApplicationCondition.retrieveValueLabel(valueEObject, graph);
			actualType = valueEObject.eClass().getName();
			childrenVariables = new HenshinDebugVariable[structuralFeatures.size()];
		}
	}
	
	@Override
	public IVariable[] getVariables() throws DebugException {
		if (hasVariables()) {
				EObject valueEObject = (EObject) value;
				// for each child variable: calculate its child variables
				List<EStructuralFeature> structuralFeatures = valueEObject.eClass().getEAllStructuralFeatures();

				for (int i = 0; i < structuralFeatures.size(); i++) {
					EStructuralFeature childFeature = structuralFeatures.get(i);
					String nameString = childFeature.getName();					
					String childDeclaredType = childFeature.getEType().getName();
					Object childObj = valueEObject.eGet(childFeature);
					
					if (isPrimitive(childFeature)) {
						childDeclaredType = childFeature.getEType().getInstanceClassName();
					}
					
					// get type of child object
					if (childObj instanceof EObject) {
						EObject childEObj = (EObject) childObj;
						// get whole domain (e.g. domain for :Client could be 'charles', 'bob', 'alice')
						List<EObject> domain = graph.getDomain(childEObj.eClass(), false);
						int index = domain.indexOf(childEObj);
						childrenVariables[i] =
								new HenshinDebugVariable(
										(HenshinDebugTarget) getDebugTarget(),
										nameString,
										new DebugValueEObject(getDebugTarget(), graph, childFeature.getEType().getName(), childEObj, index));
					} else if (childObj instanceof List<?>) {
						List<?> valueList = (List<?>) childObj;
						
						// ---------------------------------------------------
						childrenVariables[i] =
							new HenshinDebugVariable(
									(HenshinDebugTarget) getDebugTarget(),
									nameString,
									new DebugValueList(
											(HenshinDebugTarget) getDebugTarget(),
											graph,
											declaredType,
											valueList)
									);

					} else {
						// here we look at type 'Object'
						childrenVariables[i] =
								new HenshinDebugVariable(
										(HenshinDebugTarget) getDebugTarget(),
										nameString,
										new DebugValueObject(
												(HenshinDebugTarget) getDebugTarget(),
												graph,
												declaredType,
												childObj,
												i)
										);
					}

									
				}
				
				// return the variables array
				return childrenVariables;			
		} else {
			// no variables --> return an empty list
			return new HenshinDebugVariable[0];
		}
	}
	
	


}
