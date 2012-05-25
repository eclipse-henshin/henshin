package org.eclipse.emf.henshin.interpreter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.henshin.interpreter.Assignment;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * Default {@link Assignment} implementation.
 * 
 * @author Christian Krause
 */
public class AssignmentImpl implements Assignment {

	// The target transformation unit (actually final):
	protected TransformationUnit unit;
	
	// Map for storing the assigned values:
	protected final Map<Object,Object> values = new HashMap<Object,Object>();
	
	// Whether this is a result assignment:
	protected final boolean isResultAssignment; 
	
	/**
	 * Default constructor.
	 * @param rule Rule to be matched.
	 */
	public AssignmentImpl(TransformationUnit unit) {
		this (unit, false);
	}

	/**
	 * Constructor.
	 */
	public AssignmentImpl(TransformationUnit unit, boolean isResultAssignment) {
		this.isResultAssignment = isResultAssignment;
		setUnit(unit);
	}
	
	/**
	 * Constructor which copies an assignment.
	 * @param assignment Assignment to be copied.
	 */
	public AssignmentImpl(Assignment assignment, boolean isResultAssignment) {
		this.isResultAssignment = isResultAssignment;
		setUnit(assignment.getUnit());
		copyParameterValues(assignment);
	}
	
	/*
	 * Set the internal unit for this unit application.
	 */
	protected void setUnit(TransformationUnit unit) {
		if (unit==null) {
			throw new NullPointerException("Transformation unit cannot be null");
		}
		this.unit = unit;
	}
	
	/*
	 * Copy the parameter values from an assignment into this assignment.
	 */
	protected void copyParameterValues(Assignment assignment) {
		for (Parameter param : unit.getParameters()) {
			setParameterValue(param, assignment.getParameterValue(param));
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Assignment#getUnit()
	 */
	@Override
	public TransformationUnit getUnit() {
		return unit;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Assignment#getParameterValue(org.eclipse.emf.henshin.model.Parameter)
	 */
	@Override
	public Object getParameterValue(Parameter param) {
		return values.get(param);
	}

	/*
	 * Set a value or remove a key.
	 */
	protected void setValue(Object key, Object value) {
		if (value==null) {
			values.remove(key);
		} else {
			values.put(key, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Assignment#setParameterValue(org.eclipse.emf.henshin.model.Parameter, java.lang.Object)
	 */
	@Override
	public void setParameterValue(Parameter param, Object value) {
		setValue(param, value);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Assignment#getParameterValues()
	 */
	@Override
	public List<Object> getParameterValues() {
		List<Object> paramValues = new ArrayList<Object>();
		for (Parameter param : unit.getParameters()) {
			if (values.containsKey(param)) {
				paramValues.add(values.get(param));
			}
		}
		return paramValues;
	}
	

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Assignment#clear()
	 */
	@Override
	public void clear() {
		values.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Assignment#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return values.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return values.hashCode();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Assignment) {
			Assignment a = (Assignment) obj;
			if (unit!=a.getUnit()) {
				return false;
			}
			for (Parameter param : unit.getParameters()) {
				Object v1 = values.get(param);
				Object v2 = a.getParameterValue(param);
				if ((v1==null && v2!=null) || (v1!=null && !v1.equals(v2))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isResultAssignment) {
			return "Assignment for unit '" + unit.getName() + "':\n" + toStringWithIndent("");
		} else {
			return "Result assignment for unit '" + unit.getName() + "':\n" + toStringWithIndent("");			
		}
	}
	
	/*
	 * toString helper.
	 */
	protected String toStringWithIndent(String indent) {
		if (unit.getParameters().isEmpty()) {
			return indent + "- no parameters";
		}
		String result = "";
		for (Parameter param : unit.getParameters()) {
			Object value = getParameterValue(param);
			if (value!=null) {
				result = result + indent + "- parameter '" + param.getName() + "' => " + objectToString(value) + "\n";
			}
		}
		return result;
		
	}
	
	/*
	 * Get a string representation of an object.
	 */
	protected String objectToString(Object object) {
		if (object instanceof String) {
			return "'" + object + "'";
			
		}
		if (object instanceof DynamicEObjectImpl) {
			EClass eclass = ((DynamicEObjectImpl) object).eClass();
			if (eclass!=null) {
				String type = eclass.getName();
				EPackage epackage = eclass.getEPackage();
				while (epackage!=null) {
					type = epackage.getName() + "." + type;
					epackage = epackage.getESuperPackage();
				}
				String args = "";
				for (EAttribute att : eclass.getEAllAttributes()) {
					args = args + ", " + att.getName() + "=" + objectToString(((DynamicEObjectImpl) object).eGet(att));
				}
				
				return type + "@" + Integer.toHexString(object.hashCode()) + " (dynamic" + args + ")";
			}
		}
		return String.valueOf(object); // object could be null
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Assignment#isResultAssignment()
	 */
	@Override
	public boolean isResultAssignment() {
		return isResultAssignment;
	}

}
