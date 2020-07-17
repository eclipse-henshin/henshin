package org.eclipse.emf.henshin.monitoring.kieker.analysis;

import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.emf.henshin.monitoring.kieker.records.BacktrackRecord;
import org.eclipse.emf.henshin.monitoring.kieker.records.DomainRestrictionRecord;
import org.eclipse.emf.henshin.monitoring.kieker.records.UnitExecutionStartRecord;
import org.eclipse.emf.henshin.monitoring.kieker.records.UnitExecutionStopRecord;
import org.eclipse.emf.henshin.monitoring.kieker.records.VariableCheckRecord;
import org.eclipse.emf.henshin.monitoring.kieker.records.VariableInfoRecord;
import org.eclipse.emf.henshin.monitoring.kieker.util.RuleExecutionData;

import kieker.analysis.IProjectContext;
import kieker.analysis.plugin.annotation.InputPort;
import kieker.analysis.plugin.filter.AbstractFilterPlugin;
import kieker.common.configuration.Configuration;
import kieker.common.record.AbstractMonitoringRecord;

public class PerformanceFilter extends AbstractFilterPlugin {
	public static final String INPUT_PORT_NAME_PERFORMANCE_VISUALIZATION = "performanceVisualizationFilter";
	public static final String CONFIG_PROPERTY_NAME_VALID_OUTPUT = "validOutput"; 
	private LinkedList<RuleExecutionData> ruleCalls;
	private LinkedList<AbstractMonitoringRecord> startStopRecords;
	private HashMap<UnitExecutionStartRecord,Integer> startRecordToRuleCallsIdx;
	private int countUnnamed;
	private boolean spComplete=false;


	public PerformanceFilter(Configuration configuration, IProjectContext projectContext,LinkedList<RuleExecutionData> ruleCalls,LinkedList<AbstractMonitoringRecord> startStopRecords, HashMap<UnitExecutionStartRecord,Integer> startRecordToRuleCallsIdx) {
		super(configuration, projectContext);
		this.ruleCalls=ruleCalls;
		this.startStopRecords=startStopRecords;
		this.countUnnamed=0;
		this.startRecordToRuleCallsIdx=startRecordToRuleCallsIdx;
	}
	
	@InputPort(name=PerformanceFilter.INPUT_PORT_NAME_PERFORMANCE_VISUALIZATION,
	description="Filter records for performance visualizations",
	eventTypes={ VariableInfoRecord.class, DomainRestrictionRecord.class, BacktrackRecord.class,
			VariableCheckRecord.class,UnitExecutionStartRecord.class,UnitExecutionStopRecord.class})
	public void newPerformanceRecordFilter(final AbstractMonitoringRecord record) {	
		
		if(record instanceof UnitExecutionStartRecord){
			if(((UnitExecutionStartRecord)record).getType().equals("Rule")){
				this.ruleCalls.add(new RuleExecutionData(((UnitExecutionStartRecord)record).getUnitName()));
				this.startRecordToRuleCallsIdx.put(((UnitExecutionStartRecord)record),this.ruleCalls.size()-1);
				spComplete=false;
			}
			this.startStopRecords.add(record);
		}else if(record instanceof UnitExecutionStopRecord){
			this.startStopRecords.add(record);
			spComplete=true;
		}else if(record instanceof BacktrackRecord){
			this.ruleCalls.getLast().updateBacktracking(((BacktrackRecord) record).getVarIdNewBinding(),((BacktrackRecord) record).getCount());
			spComplete=true;
		}else if(record instanceof VariableInfoRecord){
			if(!spComplete){
				VariableInfoRecord varRec=((VariableInfoRecord)record);
				String name="";
				if(varRec.getName().trim().isEmpty()){
					name="unnamed"+countUnnamed+":"+varRec.getType();
					countUnnamed++;
				}else{
					name=varRec.getName()+":"+varRec.getType();
				}
				long domainSize=varRec.getDomainSize();
				if(domainSize!=-1){
					this.ruleCalls.getLast().updateRuleNodeInfo(varRec.getVariableId(),name,varRec.getSearchplanIdx(),domainSize);
				}
			}
		}else if(record instanceof VariableCheckRecord){
			VariableCheckRecord varRec=((VariableCheckRecord) record);
			long domainSize=varRec.getDomainSize();
			if(varRec.isInitNewDomain()&&(domainSize!=-1)){
				this.ruleCalls.getLast().updateSearchSpace(varRec.getVariableId(),domainSize);
			}
			this.ruleCalls.getLast().updateInvestigated(varRec.getVariableId(),varRec.getCheckedModelElements());
			spComplete=true;
		}else if(record instanceof DomainRestrictionRecord){
			DomainRestrictionRecord domRec=(DomainRestrictionRecord) record;
			this.ruleCalls.getLast().updateRestriction(domRec.getMatchedVariableId(),domRec.getRestrictedVariableId(),domRec.getNewDomainSize(),domRec.getCount());
			spComplete=true;
		}
	}

	@Override
	public Configuration getCurrentConfiguration() {
		final Configuration configuration = new Configuration();
		return configuration;
	}

}
