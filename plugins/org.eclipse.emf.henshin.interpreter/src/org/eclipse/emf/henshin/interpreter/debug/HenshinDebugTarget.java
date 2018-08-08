package org.eclipse.emf.henshin.interpreter.debug;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition;

public class HenshinDebugTarget extends HenshinDebugElement implements IDebugTarget {

	private ILaunch launch;
	
	/**
	 * The debug thread (containing the DebugApplicationCondition).
	 * Has to be set separately using {@link #initTarget(DebugApplicationCondition)}.
	 */
	private HenshinDebugThread debugThread;
	
	private IFile moduleFile;
	
	/**
	 * The DebugTarget's name (displays in the debug view)
	 */
	private String name;
	
	private IThread[] threads;
	
	/**
	 * HenshinDebugTarget Constructor. <br>
	 * NOTE: The associated debugThread has to be set separately
	 * using {@link #initTarget(DebugApplicationCondition)}.
	 * @param launch
	 * @param ruleName
	 */
	public HenshinDebugTarget(ILaunch launch, String ruleName) {
		super(null);
		this.launch = launch;
		if (ruleName != null && !ruleName.isEmpty()) {
			this.setName("Debugging Rule \"" + ruleName + "\"");
		} else {
			this.setName("HenshinDebugTarget");
		}
	}
	
	/**
	 * Connects the given DebugApplicationCondition to this DebugTarget
	 * @param applicationCondition the DebugApplicationCondition that will be used for debugging.
	 */
	public void initTarget(DebugApplicationCondition applicationCondition, IFile moduleFile) {
		this.debugThread = new HenshinDebugThread(this, applicationCondition);
		this.moduleFile = moduleFile;
		fireCreationEvent();
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}
	
	@Override
	public IDebugTarget getDebugTarget() {
		return this;
	}
	
	public IFile getModuleFile() {
		return this.moduleFile;
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
	public void breakpointAdded(IBreakpoint breakpoint) { }

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) { }

	@Override
	public boolean canDisconnect() {
		return false;
	}

	@Override
	public void disconnect() throws DebugException { }

	@Override
	public boolean isDisconnected() {
		return false;
	}

	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
		return null;
	}

	@Override
	public IProcess getProcess() {
		return null;
	}

	@Override
	public IThread[] getThreads() throws DebugException {
		if (threads == null) {
			threads = new IThread[]{debugThread};
		}
		return threads;
	}

	@Override
	public boolean hasThreads() throws DebugException {
		return true;
	}

	@Override
	public String getName() throws DebugException {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		// maybe add this in the future
		return false;
	}

	public void dispose() {
        fireTerminateEvent();
    }
	
	@Override
	public void fireEvent(DebugEvent event) {
		// All events that are fired on the debug target should be fired on the thread as well.
		try {
			DebugEvent threadEvent = new DebugEvent(getThreads()[0], event.getKind(), event.getDetail());
			if (DebugPlugin.getDefault() != null)
				DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] {event,threadEvent});
		} catch (DebugException e) {
		}
	}
}
