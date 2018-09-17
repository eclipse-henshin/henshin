package org.eclipse.emf.henshin.interpreter.debug;

import java.util.Arrays;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

public class HenshinStackFrame extends HenshinDebugElement implements IStackFrame {

    private HenshinDebugThread debugThread;
    private IVariable[] variables;
	private String label;
	private int id;
    
	public HenshinStackFrame(HenshinDebugThread debugThread, IVariable[] variables, String label, int id) {
		super(debugThread == null ? null : (HenshinDebugTarget) debugThread.getDebugTarget());
		this.debugThread = debugThread;
		this.variables = variables;
		this.label = label;
		this.id = id;
	}

	@Override
	public boolean canStepInto() {
		return debugThread.canStepInto();
	}

	@Override
	public boolean canStepOver() {
		return debugThread.canStepOver();
	}

	@Override
	public boolean canStepReturn() {
		return debugThread.canStepReturn();
	}

	@Override
	public boolean isStepping() {
		return debugThread.isStepping();
	}

	@Override
	public void stepInto() throws DebugException {
		debugThread.stepInto();
	}

	@Override
	public void stepOver() throws DebugException {
		debugThread.stepOver();
	}

	@Override
	public void stepReturn() throws DebugException {
		debugThread.stepReturn();
	}

	@Override
	public boolean canResume() {
		return debugThread.canResume();
	}

	@Override
	public boolean canSuspend() {
		return debugThread.canSuspend();
	}

	@Override
	public boolean isSuspended() {
		return debugThread.isSuspended();
	}

	@Override
	public void resume() throws DebugException {
		debugThread.resume();
	}

	@Override
	public void suspend() throws DebugException {
		debugThread.suspend();
	}

	@Override
	public boolean canTerminate() {
		return debugThread.canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return debugThread.isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		debugThread.terminate();
	}

	@Override
	public IThread getThread() {
		return debugThread;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		return variables;
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return variables != null && variables.length != 0;
	}

	@Override
	public int getLineNumber() throws DebugException {
		return -1;
	}

	@Override
	public int getCharStart() throws DebugException {
		return -1;
	}

	@Override
	public int getCharEnd() throws DebugException {
		return -1;
	}

	@Override
	public String getName() throws DebugException {
		return label;
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		return null;
	}

	@Override
	public boolean hasRegisterGroups() throws DebugException {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + Arrays.hashCode(variables);
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
		HenshinStackFrame other = (HenshinStackFrame) obj;
		if (id != other.id)
			return false;
		if (!Arrays.equals(variables, other.variables))
			return false;
		return true;
	}

	
}
