package org.eclipse.emf.henshin.interpreter.debug;

import java.util.Arrays;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition;

public class HenshinDebugValue extends HenshinDebugElement implements IValue {

	private EGraph graph;
	private Object value;
	private String declaredType;
	private String actualType;
	private String valueString;
	
	/**
	 * The children variables.
	 * null indicates that the value does not contain children variables
	 */
	private HenshinDebugVariable[] childrenVariables;
	
	/**
	 * creates a Debug Value from a value object.
	 * The value object should be one of the following:
	 * <ol>
	 * <li>an EObject</li>
	 * <li>a {@link List} of objects (in this case the same rules apply for the list elements)</li>
	 * <li>an object that provides a meaningful toString(), as that will be used for all other objects</>
	 * </ol>
	 * @param target the associated debug target
	 * @param graph an {@link EGraph} that is used to retrieve a string label for this value, or <code>null</code>
	 * @param valueObject the actual value. If null, the valueString will be used as value object
	 * @param declaredType the string representation of the value's declared type.
	 */
	public HenshinDebugValue(IDebugTarget target, EGraph graph, Object valueObject, String declaredType) {
		super(target);
		value = valueObject;
		this.graph = graph;
		
		// for null values
		if (value == null) {
			this.declaredType = String.valueOf(declaredType);
			value = valueString = actualType = "null";
			childrenVariables = null;
			return;
		}
		
		// is the value an EObject?
		if(value instanceof EObject) {
			EObject valueEObject = (EObject) value;
			
			this.declaredType = declaredType;
			
			if (isPrimitive(valueEObject)) {
				valueString = valueEObject.toString();
				actualType = ((EAttribute) valueEObject).getEType().getInstanceClassName();
				childrenVariables = null;
			} else {
				// initialize the children variable array (the children variables themselves get initialized in getVariables()
				List<EStructuralFeature> structuralFeatures = valueEObject.eClass().getEAllStructuralFeatures();
				valueString = DebugApplicationCondition.retrieveValueLabel(valueEObject, graph);
				actualType = valueEObject.eClass().getName();
				childrenVariables = new HenshinDebugVariable[structuralFeatures.size()];
			}
		}
		// is the value a List?
		else if (value instanceof List<?>) {
			List<?> valueList = (List<?>) value;
			this.declaredType = declaredType;
			valueString = declaredType + "[" + valueList.size() + "]";
			actualType = declaredType + "[]";
			childrenVariables = valueList.isEmpty() ? null : new HenshinDebugVariable[valueList.size()];
			
		} else {
			// value is some other object, so we just use toString
			// (Strings themselves are handled here as well)
			valueString = value.toString();
			this.declaredType = actualType = declaredType == null ? value.getClass().getName() : declaredType;
			childrenVariables = null;
		}
		
	}


	/**
	 * checks whether an EAttribute is of a primitive (meta) type
	 * @param attribute the attribute to check
	 * @return <code>true</code> if the attribute is primitive
	 */
	protected boolean isPrimitive(EObject object) {
		if (object instanceof EAttribute) {
			EAttribute attribute = (EAttribute) object;
			return 	attribute.getEType().getClassifierID() == EcorePackage.ESTRING || 
					attribute.getEType().getClassifierID() == EcorePackage.EINT || 
					attribute.getEType().getClassifierID() == EcorePackage.ELONG ||
					attribute.getEType().getClassifierID() == EcorePackage.EFLOAT ||
					attribute.getEType().getClassifierID() == EcorePackage.EDOUBLE ||
					attribute.getEType().getClassifierID() == EcorePackage.EBOOLEAN;
		}
		return false;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		// show the actual type (of the value eobject)
		return actualType;
	}

	@Override
	public String getValueString() throws DebugException {
		return valueString;
	}

	@Override
	public boolean isAllocated() throws DebugException {
		return true;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		if (hasVariables()) {
			if(value instanceof EObject) {
				EObject valueEObject = (EObject) value;
				// for each child variable: calculate its child variables
				List<EStructuralFeature> structuralFeatures = valueEObject.eClass().getEAllStructuralFeatures();

				for (int i = 0; i < structuralFeatures.size(); i++) {
					EStructuralFeature childFeature = structuralFeatures.get(i);
					String nameString = childFeature.getName();					
					Object childObj = valueEObject.eGet(childFeature);

					String childDeclaredType = childFeature.getEType().getName();
					
					if (isPrimitive(childFeature)) {
						childDeclaredType = childFeature.getEType().getInstanceClassName();
					}
					
					childrenVariables[i] =
							new HenshinDebugVariable(
									(HenshinDebugTarget) getDebugTarget(),
									nameString,
									new HenshinDebugValue(
											(HenshinDebugTarget) getDebugTarget(),
											graph,
											childObj,
											childDeclaredType));
				}
				
				// return the variables array
				return childrenVariables;
			}
			// is the value a List?
			else if (value instanceof List<?>) {
				List<?> valueList = (List<?>) value;
				
				for (int i = 0; i < valueList.size(); i++) {
					Object child = valueList.get(i);
					
					childrenVariables[i] = 
							new HenshinDebugVariable(
									(HenshinDebugTarget) getDebugTarget(),
									"["+i+"]",
									new HenshinDebugValue(
											(HenshinDebugTarget) getDebugTarget(),
											graph,
											child,
											declaredType));
				}
				return childrenVariables;
			}
			else {
				throw new IllegalStateException("unknown value type");
			}
			
			
		} else {
			// no variables --> return an empty list
			return new HenshinDebugVariable[0];
		}
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return childrenVariables != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualType == null) ? 0 : actualType.hashCode());
		result = prime * result + Arrays.hashCode(childrenVariables);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((valueString == null) ? 0 : valueString.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HenshinDebugValue other = (HenshinDebugValue) obj;
		if (actualType == null) {
			if (other.actualType != null)
				return false;
		} else if (!actualType.equals(other.actualType))
			return false;
		if (!Arrays.equals(childrenVariables, other.childrenVariables))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (valueString == null) {
			if (other.valueString != null)
				return false;
		} else if (!valueString.equals(other.valueString))
			return false;
		return true;
	}

	public String getDeclaredType() {
		return value instanceof List<?> ? declaredType + "[]" : declaredType;
	}
	

	
}
