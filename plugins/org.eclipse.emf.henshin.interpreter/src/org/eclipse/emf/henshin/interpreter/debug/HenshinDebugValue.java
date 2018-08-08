package org.eclipse.emf.henshin.interpreter.debug;

import java.util.Arrays;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.interpreter.EGraph;

public abstract class HenshinDebugValue extends HenshinDebugElement implements IValue {

	protected EGraph graph;
	protected String declaredType;
	protected String actualType;
	protected String valueString;
	protected int indexInDomain = -1;
	
	/**
	 * The children variables.
	 * null indicates that the value does not contain children variables
	 */
	protected HenshinDebugVariable[] childrenVariables;
		
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
	 * @param indexInDomain the index within the domain
	 */
	public HenshinDebugValue(IDebugTarget target, EGraph graph, String declaredType) {
		super(target);
		this.graph = graph;
		
		if (declaredType == null)
			throw new IllegalArgumentException("declaredType must not be null");
		
		this.declaredType = declaredType;
		
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
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
	public abstract IVariable[] getVariables() throws DebugException;


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
		result = prime * result + ((valueString == null) ? 0 : valueString.hashCode());
		return result;
	}

	/**
	 * checks whether an EAttribute is of a primitive (meta) type
	 * @param attribute the attribute to check
	 * @return <code>true</code> if the attribute is primitive
	 */
	public boolean isPrimitive(EObject object) {
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
		if (valueString == null) {
			if (other.valueString != null)
				return false;
		} else if (!valueString.equals(other.valueString))
			return false;
		
		return true;
	}

	public int getIndexInDomain() {
		return indexInDomain;
	}
}
