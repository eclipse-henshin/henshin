package org.eclipse.emf.henshin.interpreter.monitoring;


public interface PerformanceMonitor {
	
	void addUnitExecutionStartRecord(String unitName,String type);
	
	void addBacktrackRecord(String varIdNewBinding);

	void addVariableCheckRecord(VariableCheck varCheckRecord);
	
	void addVariableInfoRecord(String variableId, String name, String type, int searchplanIdx, long domainSize);
	
	void addDomainRestrictionRecord(String matchedVariableId,String restrictedVariableId,long newDomainSize);

	void showVisualizations();
}
