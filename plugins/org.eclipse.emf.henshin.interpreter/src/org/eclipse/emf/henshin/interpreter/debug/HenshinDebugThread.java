package org.eclipse.emf.henshin.interpreter.debug;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition.DebugState;

public class HenshinDebugThread extends HenshinDebugElement implements IThread {

	private DebugApplicationCondition applicationCondition;
	public HenshinDebugThread(IDebugTarget target, DebugApplicationCondition applicationCondition) {
		super(target);
		this.applicationCondition = applicationCondition;
	}

	@Override
	public boolean canResume() {
		return applicationCondition.isSuspended();
	}

	@Override
	public boolean canSuspend() {
        return applicationCondition.canSuspend();
	}

	@Override
	public boolean isSuspended() {
		return applicationCondition.isSuspended();
	}

	@Override
	public void resume() throws DebugException {
		applicationCondition.resume();
	}

	@Override
	public void suspend() throws DebugException {
		 applicationCondition.suspend();	
	}

	@Override
	public boolean canStepInto() {
		return applicationCondition.canStepInto();
	}

	@Override
	public boolean canStepOver() {
		return applicationCondition.canStepOver();
	}

	@Override
	public boolean canStepReturn() {
		return applicationCondition.canStepReturn();
	}

	@Override
	public boolean isStepping() {
        return applicationCondition.isStepping();
	}
	
	@Override
	public void stepInto() throws DebugException {
		applicationCondition.stepInto();
	}

	@Override
	public void stepOver() throws DebugException {
		applicationCondition.stepOver();
	}

	@Override
	public void stepReturn() throws DebugException {
		applicationCondition.stepReturn();
	}

	@Override
	public boolean canTerminate() {
		return applicationCondition.canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return applicationCondition.isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		applicationCondition.terminate();
	}
	
	@Override
	public IStackFrame[] getStackFrames() throws DebugException {
		// because of the way the array is created in DebugApplicationCondition,
		// it has to be reversed here
		IStackFrame[] stackFrames = applicationCondition.getStackFrames(this);
		List<IStackFrame> stackFrameList = Arrays.asList(stackFrames);
		Collections.reverse(stackFrameList);
		return stackFrameList.toArray(stackFrames);
	}

	@Override
	public boolean hasStackFrames() throws DebugException {
		return (applicationCondition.getCurrentDebugState() == DebugState.TERMINATED_TRUE)
		|| applicationCondition.isSuspended();
	}

	@Override
	public int getPriority() throws DebugException {
		return 0;
	}

	@Override
	public IStackFrame getTopStackFrame() throws DebugException {
		final IStackFrame[] stackFrames = getStackFrames();
		return (stackFrames.length > 0 ? stackFrames[0] : null);
	}

	@Override
	public String getName() throws DebugException {
		return applicationCondition.getName();
	}

	@Override
	public IBreakpoint[] getBreakpoints() {
		return applicationCondition.getBreakpoints();
	}

	@Override
	public void fireEvent(DebugEvent event) {
		// All events that are fired on the debug target should be fired on the thread as well.
		try {
			DebugEvent threadEvent = new DebugEvent(getTopStackFrame(), event.getKind(), event.getDetail());
			if (DebugPlugin.getDefault() != null)
				DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] {event,threadEvent});
		} catch (DebugException e) {
		}
	}
}
