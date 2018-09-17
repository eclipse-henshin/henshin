package org.eclipse.emf.henshin.interpreter.matching.conditions; 

import java.util.List;
import java.util.Map;
import java.util.Observer;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugTarget;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugValue;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;

public class TestDebugApplicationCondition extends DebugApplicationCondition {

	public TestDebugApplicationCondition(HenshinDebugTarget debugTarget, List<Variable> variables,
			Map<Variable, DomainSlot> domainMap, EGraph graph, IFormula formula, Observer matchObserver,
			RuleInfo ruleInfo) {
		super(debugTarget, variables, domainMap, graph, formula, matchObserver, ruleInfo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setVariableBreakpoint(Variable variable) {
		getManager();

		new IMarker() {

			// Local variable to hold the node path
			String path = null;

			@Override
			public <T> T getAdapter(Class<T> adapter) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setAttributes(String[] attributeNames, Object[] values) throws CoreException {
				// TODO Auto-generated method stub
			}

			@Override
			public void setAttributes(Map<String, ? extends Object> attributes) throws CoreException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setAttribute(String attributeName, boolean value) throws CoreException {
				// TODO Auto-generated method stub
			}

			@Override
			public void setAttribute(String attributeName, Object value) throws CoreException {
				// Only set the path attribute, others are not necessary for
				// testing
				if (attributeName == "Path") {
					this.path = value.toString();
				}
			}

			@Override
			public void setAttribute(String attributeName, int value) throws CoreException {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isSubtypeOf(String superType) throws CoreException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getType() throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IResource getResource() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getCreationTime() throws CoreException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object[] getAttributes(String[] attributeNames) throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, Object> getAttributes() throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean getAttribute(String attributeName, boolean defaultValue) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public String getAttribute(String attributeName, String defaultValue) {
				// get the local path variable
				return this.path;
			}

			@Override
			public int getAttribute(String attributeName, int defaultValue) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getAttribute(String attributeName) throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean exists() {
				// for now let's always return true
				return true;
			}

			@Override
			public void delete() throws CoreException {
				// TODO Auto-generated method stub

			}
		};
	}

	/**
	 * Sets breakpoint for a given value. This is determined by the value itself
	 * and its corresponding index within the domain.
	 */
	public void setValueBreakpoint(HenshinDebugValue value, int index) {
		// get all breakpoints
		IBreakpointManager manager = getManager();

		// create breakpoint
		// This part for the marker is for automated tests only
		IMarker marker = new IMarker() {

			// Local variable to hold the node path
			String type = null;
			String valueString = null;
			int index = -1;

			@Override
			public <T> T getAdapter(Class<T> adapter) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setAttributes(String[] attributeNames, Object[] values) throws CoreException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setAttributes(Map<String, ? extends Object> attributes) throws CoreException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setAttribute(String attributeName, boolean value) throws CoreException {
				// TODO Auto-generated method stub
			}

			@Override
			public void setAttribute(String attributeName, Object value) throws CoreException {
				// Set type attribute
				if (attributeName == "Type") {
					this.type = value.toString();
				}
				// Set value string attribute
				if (attributeName == "ValueString") {
					this.valueString = value.toString();
				}
			}

			@Override
			public void setAttribute(String attributeName, int value) throws CoreException {
				// Set index in domain
				if (attributeName == "Index") {
					this.index = (int) value;
				}
			}

			@Override
			public boolean isSubtypeOf(String superType) throws CoreException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getType() throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IResource getResource() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getCreationTime() throws CoreException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object[] getAttributes(String[] attributeNames) throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, Object> getAttributes() throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean getAttribute(String attributeName, boolean defaultValue) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public String getAttribute(String attributeName, String defaultValue) {
				if (attributeName == "Type") {
					return this.type;
				}

				if (attributeName == "ValueString") {
					return this.valueString;
				}

				return null;
			}

			@Override
			public int getAttribute(String attributeName, int defaultValue) {
				if (attributeName == "Index") {
					return this.index;
				}

				return defaultValue;
			}

			@Override
			public Object getAttribute(String attributeName) throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean exists() {
				// for now let's always return true
				return true;
			}

			@Override
			public void delete() throws CoreException {
				// TODO Auto-generated method stub

			}
		};

		ValueBreakpoint breakpoint = new ValueBreakpoint();
		try {
			// set marker for breakpoint
			breakpoint.setMarker(marker);
			breakpoint.setEnabled(true);
			// configure breakpoint
			breakpoint.setType(value.getReferenceTypeName());
			breakpoint.setValueString(value.getValueString());
			breakpoint.setIndex(index);
			breakpoint.setDebugLevel(DebugLevel.VALUE);
			// add breakpoint to manager to keep track
			manager.addBreakpoint(breakpoint);
		} catch (CoreException e1) {
			System.out.println("Unable to create custom ValueBreakpoint.");
			e1.printStackTrace();
		}
	}

	public void setConstraintTypeBreakpoint(String constraintType) {
		// get all breakpoints
		IBreakpointManager manager = getManager();
		// create breakpoint
		IMarker marker = new IMarker() {

			// Local variable to hold the node path
			String type = null;

			@Override
			public <T> T getAdapter(Class<T> adapter) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setAttributes(String[] attributeNames, Object[] values) throws CoreException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setAttributes(Map<String, ? extends Object> attributes) throws CoreException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setAttribute(String attributeName, boolean value) throws CoreException {
				// TODO Auto-generated method stub
			}

			@Override
			public void setAttribute(String attributeName, Object value) throws CoreException {
				// Set type attribute
				if (attributeName == "Type") {
					this.type = value.toString();
				}
			}

			@Override
			public void setAttribute(String attributeName, int value) throws CoreException {
			}

			@Override
			public boolean isSubtypeOf(String superType) throws CoreException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getType() throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IResource getResource() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getCreationTime() throws CoreException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object[] getAttributes(String[] attributeNames) throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, Object> getAttributes() throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean getAttribute(String attributeName, boolean defaultValue) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public String getAttribute(String attributeName, String defaultValue) {
				if (attributeName == "Type") {
					return this.type;
				}

				return null;
			}

			@Override
			public int getAttribute(String attributeName, int defaultValue) {
				return 0;
			}

			@Override
			public Object getAttribute(String attributeName) throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean exists() {
				// for now let's always return true
				return true;
			}

			@Override
			public void delete() throws CoreException {
				// TODO Auto-generated method stub

			}
		};

		ConstraintTypeBreakpoint breakpoint = new ConstraintTypeBreakpoint();
		try {
			// set marker for breakpoint
			breakpoint.setMarker(marker);
			breakpoint.setEnabled(true);
			// configure breakpoint
			breakpoint.setType(ConstraintType.valueOf(constraintType));
			// add breakpoint to manager to keep track
			manager.addBreakpoint(breakpoint);
		} catch (CoreException e1) {
			System.out.println("Unable to create custom ConstraintTypeBreakpoint.");
			e1.printStackTrace();
		}
	}

	public void setConstraintInstanceBreakpoint(String constraintInstance) {
		// get all breakpoints
		IBreakpointManager manager = getManager();
		// create breakpoint
		IMarker marker = new IMarker() {

			// Local variable to hold the node path
			String constraintInstance = null;

			@Override
			public <T> T getAdapter(Class<T> adapter) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setAttributes(String[] attributeNames, Object[] values) throws CoreException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setAttributes(Map<String, ? extends Object> attributes) throws CoreException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setAttribute(String attributeName, boolean value) throws CoreException {
				// TODO Auto-generated method stub
			}

			@Override
			public void setAttribute(String attributeName, Object value) throws CoreException {
				// Set type attribute
				if (attributeName == "ConstraintInstance") {
					this.constraintInstance = value.toString();
				}
			}

			@Override
			public void setAttribute(String attributeName, int value) throws CoreException {
			}

			@Override
			public boolean isSubtypeOf(String superType) throws CoreException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getType() throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IResource getResource() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getCreationTime() throws CoreException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object[] getAttributes(String[] attributeNames) throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, Object> getAttributes() throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean getAttribute(String attributeName, boolean defaultValue) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public String getAttribute(String attributeName, String defaultValue) {
				if (attributeName == "ConstraintInstance") {
					return this.constraintInstance;
				}

				return null;
			}

			@Override
			public int getAttribute(String attributeName, int defaultValue) {
				return 0;
			}

			@Override
			public Object getAttribute(String attributeName) throws CoreException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean exists() {
				// for now let's always return true
				return true;
			}

			@Override
			public void delete() throws CoreException {
				// TODO Auto-generated method stub

			}
		};

		ConstraintInstanceBreakpoint breakpoint = new ConstraintInstanceBreakpoint();
		try {
			// set marker for breakpoint
			breakpoint.setMarker(marker);
			breakpoint.setEnabled(true);
			// configure breakpoint
			breakpoint.setConstraintInstance(removeRuntimeValuesFromConstraintInstance(constraintInstance));
			// add breakpoint to manager to keep track
			manager.addBreakpoint(breakpoint);
		} catch (CoreException e1) {
			System.out.println("Unable to create custom ConstraintInstanceBreakpoint.");
			e1.printStackTrace();
		}
	}
}