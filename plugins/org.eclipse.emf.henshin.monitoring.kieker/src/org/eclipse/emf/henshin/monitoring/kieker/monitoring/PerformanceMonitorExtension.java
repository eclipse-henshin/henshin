package org.eclipse.emf.henshin.monitoring.kieker.monitoring;

import org.eclipse.emf.henshin.interpreter.monitoring.PerformanceMonitor;
import org.eclipse.emf.henshin.interpreter.ui.extension.IMonitoringUI;

public class PerformanceMonitorExtension implements IMonitoringUI {

	private PerformanceMonitorImpl monitor;
	
	public PerformanceMonitorExtension() {
		this.monitor=new PerformanceMonitorImpl();
	}

	@Override
	public PerformanceMonitor getPerformanceMonitor() {
		return this.monitor;
	}

}
