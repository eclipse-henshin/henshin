package org.eclipse.emf.henshin.monitoring.kieker.monitoring;

import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.BasicApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.monitoring.PerformanceMonitor;
import org.eclipse.emf.henshin.interpreter.monitoring.VariableCheck;
import org.eclipse.emf.henshin.monitoring.kieker.analysis.PerformanceAnalysis;
import org.eclipse.emf.henshin.monitoring.kieker.records.BacktrackRecord;
import org.eclipse.emf.henshin.monitoring.kieker.records.DomainRestrictionRecord;
import org.eclipse.emf.henshin.monitoring.kieker.records.UnitExecutionStartRecord;
import org.eclipse.emf.henshin.monitoring.kieker.records.UnitExecutionStopRecord;
import org.eclipse.emf.henshin.monitoring.kieker.records.VariableCheckRecord;
import org.eclipse.emf.henshin.monitoring.kieker.records.VariableInfoRecord;

import kieker.common.configuration.Configuration;
import kieker.common.record.AbstractMonitoringRecord;
import kieker.monitoring.core.configuration.ConfigurationFactory;
import kieker.monitoring.core.controller.IMonitoringController;
import kieker.monitoring.core.controller.MonitoringController;

public class PerformanceMonitorImpl extends BasicApplicationMonitor implements PerformanceMonitor {
	
	private final IMonitoringController MONITORING_CONTROLLER;
	
	private HashMap<String,BacktrackRecord> varIdToBacktrackRecord=null;
	private HashMap<String,VariableCheckRecord> varIdToVariableCheckRecord=null;
	private HashMap<String,DomainRestrictionRecord> varIdtoDomainRestrictionRecord=null;
	
	public PerformanceMonitorImpl() {
		super();
		MONITORING_CONTROLLER=createNewController();
	}

	/**
	 * Decides on the basis of the record type, how it should be handled
	 * 
	 * @param record Monitoring record to be stored
	 */
	private void addRecord(AbstractMonitoringRecord record){
		if((record instanceof UnitExecutionStartRecord)||(record instanceof UnitExecutionStopRecord)||(record instanceof VariableInfoRecord)){
			MONITORING_CONTROLLER.newMonitoringRecord(record);
		}else if(record instanceof BacktrackRecord){
			handleRecord((BacktrackRecord)record);
		}else if(record instanceof VariableCheckRecord){
			handleRecord((VariableCheckRecord)record);
		}else if(record instanceof DomainRestrictionRecord){
			handleRecord((DomainRestrictionRecord)record);
		}
			
	}
	
	@Override
	public void addUnitExecutionStartRecord(String unitName, String type) {
		addRecord(new UnitExecutionStartRecord(unitName,type));
	}

	@Override
	public void addBacktrackRecord(String varIdNewBinding) {
		addRecord(new BacktrackRecord(varIdNewBinding));
	}

	@Override
	public void addVariableCheckRecord(VariableCheck varCheckRecord) {
		addRecord(new VariableCheckRecord(varCheckRecord));
	}

	@Override
	public void addVariableInfoRecord(String variableId, String name, String type, int searchplanIdx, long domainSize) {
		addRecord(new VariableInfoRecord(variableId, name, type, searchplanIdx, domainSize));
	}

	@Override
	public void addDomainRestrictionRecord(String matchedVariableId, String restrictedVariableId, long newDomainSize) {
		addRecord(new DomainRestrictionRecord(matchedVariableId, restrictedVariableId, newDomainSize));
	}
	
	/**
	 * Aggregates per rule, the information from a DomainRestrictionRecord. If there is an overflow, the record is saved and a new one is 
	 * created for aggregating the values.
	 * 
	 * @param record New DomainRestrictionRecord which should be added
	 */
	private void handleRecord(DomainRestrictionRecord record) {
		String key=record.getMatchedVariableId()+record.getRestrictedVariableId();
		if (varIdtoDomainRestrictionRecord==null){
			varIdtoDomainRestrictionRecord=new HashMap<String,DomainRestrictionRecord>();
			varIdtoDomainRestrictionRecord.put(key,record);
		}else{
			if(varIdtoDomainRestrictionRecord.containsKey(key)){
				long newDomainSize=record.getNewDomainSize()+varIdtoDomainRestrictionRecord.get(key).getNewDomainSize();
				if((newDomainSize<0)||(varIdtoDomainRestrictionRecord.get(key).getCount()==Long.MAX_VALUE)){
					//overflow
					MONITORING_CONTROLLER.newMonitoringRecord(varIdtoDomainRestrictionRecord.get(key));
					varIdtoDomainRestrictionRecord.put(key,record);
				}else{
					varIdtoDomainRestrictionRecord.get(key).setNewDomainSize(newDomainSize);
					varIdtoDomainRestrictionRecord.get(key).incCount();
				}
			}else{
				varIdtoDomainRestrictionRecord.put(key,record);
			}
		}
		
	}

	/**
	 * Aggregates per rule, the information from a VariableCheckRecord. If there is an overflow, the record is saved and a new one is 
	 * created for aggregating the values.
	 * 
	 * @param record New VariableCheckRecord which should be added
	 */
	private void handleRecord(VariableCheckRecord record) {
		if (varIdToVariableCheckRecord==null){
			varIdToVariableCheckRecord=new HashMap<String,VariableCheckRecord>();
			varIdToVariableCheckRecord.put(record.getVariableId(),record);
		}else{
			if(varIdToVariableCheckRecord.containsKey(record.getVariableId())){
				long newDomainSize=record.getDomainSize()+varIdToVariableCheckRecord.get(record.getVariableId()).getDomainSize();
				long newCheckedElements=record.getCheckedModelElements()+varIdToVariableCheckRecord.get(record.getVariableId()).getCheckedModelElements();
				if((newDomainSize<0)||(newCheckedElements<0)){
					//overflow
					MONITORING_CONTROLLER.newMonitoringRecord(varIdToVariableCheckRecord.get(record.getVariableId()));
					varIdToVariableCheckRecord.put(record.getVariableId(),record);
				}else{
					varIdToVariableCheckRecord.get(record.getVariableId()).setDomainSize(newDomainSize);
					varIdToVariableCheckRecord.get(record.getVariableId()).setCheckedModelElements(newCheckedElements);
				}
			}else{
				varIdToVariableCheckRecord.put(record.getVariableId(),record);
			}
		}
	}

	/**
	 * Aggregates per rule, the information from a BacktrackRecord. If there is an overflow, the record is saved and a new one is 
	 * created for aggregating the values.
	 * 
	 * @param record New BacktrackRecord which should be added
	 */
	private void handleRecord(BacktrackRecord record) {
		if (varIdToBacktrackRecord==null){
			varIdToBacktrackRecord=new HashMap<String,BacktrackRecord>();
			varIdToBacktrackRecord.put(record.getVarIdNewBinding(),record);
		}else{
			if(varIdToBacktrackRecord.containsKey(record.getVarIdNewBinding())){
				long newCount=record.getCount()+varIdToBacktrackRecord.get(record.getVarIdNewBinding()).getCount();
				if(newCount<0){
					//overflow
					MONITORING_CONTROLLER.newMonitoringRecord(varIdToBacktrackRecord.get(record.getVarIdNewBinding()));
					varIdToBacktrackRecord.put(record.getVarIdNewBinding(),record);
				}else{
					varIdToBacktrackRecord.get(record.getVarIdNewBinding()).setCount(newCount);
				}
			}else{
				varIdToBacktrackRecord.put(record.getVarIdNewBinding(),record);
			}
		}
	}

	@Override
	public void notifyExecute(UnitApplication application, boolean success) {
		addAggregatedRecords();
		addRecord(new UnitExecutionStopRecord());
	}

	@Override
	public void notifyUndo(UnitApplication application, boolean success) {	
		addAggregatedRecords();
		addRecord(new UnitExecutionStopRecord());
	}

	@Override
	public void notifyRedo(UnitApplication application, boolean success) {
		addAggregatedRecords();
		addRecord(new UnitExecutionStopRecord());
	}
	
	/**
	 * Stores all records that were used to aggregate the data.
	 * 
	 */
	private void addAggregatedRecords() {
		if(varIdToBacktrackRecord!=null){
			for(Entry<String, BacktrackRecord> btRec:varIdToBacktrackRecord.entrySet()){
				MONITORING_CONTROLLER.newMonitoringRecord(btRec.getValue());
			}
			varIdToBacktrackRecord=null;
		}
		if(varIdToVariableCheckRecord!=null){
			for(Entry<String, VariableCheckRecord> vcRec:varIdToVariableCheckRecord.entrySet()){
				MONITORING_CONTROLLER.newMonitoringRecord(vcRec.getValue());
			}
			varIdToVariableCheckRecord=null;
		}
		if(varIdtoDomainRestrictionRecord!=null){
			for(Entry<String, DomainRestrictionRecord> drRec:varIdtoDomainRestrictionRecord.entrySet()){
				MONITORING_CONTROLLER.newMonitoringRecord(drRec.getValue());
			}
			varIdtoDomainRestrictionRecord=null;
		}
	}
	
	/**
	 * Stops the monitoring and starts the analysis. The results of the analysis are displayed in a GUI.
	 */
	public void showVisualizations(){
		if(!MONITORING_CONTROLLER.isMonitoringTerminated()){
			MONITORING_CONTROLLER.terminateMonitoring();
		}
		PerformanceAnalysis.runPerformanceAnalysis();
	}
	
	private IMonitoringController createNewController() {
		Configuration config=ConfigurationFactory.createSingletonConfiguration();
		config.setProperty("kieker.monitoring.writer.filesystem.AsciiFileWriter.customStoragePath","");
		config.setProperty("kieker.monitoring.writer.filesystem.AsciiFileWriter.maxLogFiles",-1);
		config.setProperty("kieker.monitoring.writer.filesystem.AsciiFileWriter.flush",false);
		config.setProperty("kieker.monitoring.writer.filesystem.AsciiFileWriter.maxLogSize",-1);
		config.setProperty("kieker.monitoring.writer.filesystem.AsciiFileWriter.flushMapfile",true);
		config.setProperty("kieker.monitoring.writer.filesystem.AsciiFileWriter.maxEntriesInFile",25000);
		config.setProperty("kieker.monitoring.writer.filesystem.AsciiFileWriter.shouldCompress",false);
		return MonitoringController.createInstance(config);
	}

}
