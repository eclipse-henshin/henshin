package org.eclipse.emf.henshin.monitoring.kieker.analysis;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.eclipse.emf.henshin.monitoring.kieker.records.UnitExecutionStartRecord;
import org.eclipse.emf.henshin.monitoring.kieker.records.UnitExecutionStopRecord;
import org.eclipse.emf.henshin.monitoring.kieker.util.RuleExecutionData;
import org.eclipse.emf.henshin.monitoring.ui.HenshinPerformanceView;
import org.eclipse.emf.henshin.monitoring.ui.util.RuleVisualizationData;
import org.eclipse.emf.henshin.monitoring.ui.util.UnitVisualizationData;

import kieker.analysis.AnalysisController;
import kieker.analysis.IAnalysisController;
import kieker.analysis.exception.AnalysisConfigurationException;
import kieker.analysis.plugin.reader.filesystem.FSReader;
import kieker.common.configuration.Configuration;
import kieker.common.record.AbstractMonitoringRecord;

public class PerformanceAnalysis {
	
	private static FSReader reader;
	private static LinkedList<RuleExecutionData> ruleCalls;
	private static LinkedList<UnitVisualizationData> unitCallData;
	private static LinkedList<AbstractMonitoringRecord> startStopRecords;
	private static HashMap<UnitExecutionStartRecord,Integer> startRecordToRuleCallsIdx;
	


	/**
	 * Starts analysis and calls view.
	 */
	public static void runPerformanceAnalysis(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ruleCalls=new LinkedList<RuleExecutionData>();
		unitCallData=new LinkedList<UnitVisualizationData>();
		startStopRecords=new LinkedList<AbstractMonitoringRecord>();
		startRecordToRuleCallsIdx=new HashMap<UnitExecutionStartRecord,Integer>();
		
		String kiekerDir=getLogFilePath();
		collectRecords(kiekerDir);		

		updateUnitCalls(0,unitCallData);
		
		LinkedList<RuleVisualizationData> ruleVisualizationData=new LinkedList<RuleVisualizationData>();
		for(RuleExecutionData rule:ruleCalls){
			rule.setVisualizationData();
			ruleVisualizationData.add(rule.getRuleVisualizations());
		}
			
		//Open GUI
		HenshinPerformanceView.showHenshinPerformanceView(ruleVisualizationData,unitCallData);	
	}
	
	
	/**
	 * Extracts the call hierarchy from a list of UnitExecutionStartRecord and UnitExecutionStopRecord records.
	 *  
	 * @param listIdx Index of the current record to be processed
	 * @param callTree Data structure presenting the call hierarchy
	 * 
	 * @return Index of the current record to be processed after the call of updateUnitCalls
	 */
	private static int updateUnitCalls(int listIdx,LinkedList<UnitVisualizationData> callTree){
		final String ruleType="Rule";
		for(;listIdx<startStopRecords.size()-1;listIdx++){
			if((startStopRecords.get(listIdx) instanceof UnitExecutionStartRecord)&&(startStopRecords.get(listIdx+1) instanceof UnitExecutionStopRecord)){
				//Start-Stop: Simple transformation rule execution or Empty Unit
				UnitExecutionStartRecord startRec=(UnitExecutionStartRecord) startStopRecords.get(listIdx);
				UnitExecutionStopRecord stopRec=(UnitExecutionStopRecord) startStopRecords.get(listIdx+1);
				UnitVisualizationData data=new UnitVisualizationData(startRec.getUnitName(),startRec.getStartTimeStamp(),stopRec.getStopTimeStamp(),startRec.getType());
				if(startRec.getType().equals(ruleType)){
					data.setTabIdx(startRecordToRuleCallsIdx.get(startRec)+1);
				}
				callTree.add(data);
				listIdx++; //Start and Stop record are processed
				
			}else if((startStopRecords.get(listIdx) instanceof UnitExecutionStartRecord)&&(startStopRecords.get(listIdx+1) instanceof UnitExecutionStartRecord)){
				//Start-Start: One step deeper into the call hierarchy
				UnitExecutionStartRecord startRec=(UnitExecutionStartRecord) startStopRecords.get(listIdx); //Recorde der andere Aufruft
				LinkedList<UnitVisualizationData> deeperCall=new LinkedList<UnitVisualizationData>();
				int idxAfter=updateUnitCalls((listIdx+1),deeperCall);
				
				//It is expected that idxAfter after the recursion points to the stop record which belongs to the start record at the beginning of the recursion.
				if(!(startStopRecords.get(idxAfter) instanceof UnitExecutionStopRecord)){
					System.err.println("There is an error in the log files please rerun the transformation.");
					return startStopRecords.size();
				}
				UnitExecutionStopRecord stopRec=(UnitExecutionStopRecord) startStopRecords.get(idxAfter);
				UnitVisualizationData data=new UnitVisualizationData(startRec.getUnitName(),startRec.getStartTimeStamp(),stopRec.getStopTimeStamp(),startRec.getType());
				data.setCalls(deeperCall);
				callTree.add(data);
				listIdx=idxAfter; //Start and Stop record are processed
			}else if((startStopRecords.get(listIdx) instanceof UnitExecutionStopRecord)&& (listIdx>0)){
				if((startStopRecords.get(listIdx-1) instanceof UnitExecutionStopRecord)){
					//Start-Start: One step back in the call hierarchy
					return listIdx;
				}
			}else{
				System.err.println("There is an error in the log files please rerun the transformation.");
				return startStopRecords.size();
			}
		}
		return listIdx;
	}
	

	/**
	 * Looks for the path in the temp directory which contains the latest monitoring data  
	 * 
	 * 
	 * @return Path to the directory containing the latest monitoring data
	 */
	private static String getLogFilePath(){
		String kiekerPath="";
		File fileTemp = new File(System.getProperty("java.io.tmpdir"));
		long modified=-1;
		if(fileTemp.isDirectory()){
			for(String tmpFiles:fileTemp.list()){
				if(tmpFiles.startsWith("kieker-")){
					File kiekerDir = new File((System.getProperty("java.io.tmpdir")+tmpFiles));
					if(modified<kiekerDir.lastModified()){
						kiekerPath=System.getProperty("java.io.tmpdir")+tmpFiles;
						modified=kiekerDir.lastModified();
					}	
				}
				
			}
		}
		return kiekerPath;
	}
	
	/**
	 * Collects the data records from given directory
	 * @param kiekerDir Path to directory containing the monitoring records
	 */
	private static void collectRecords(String kiekerDir){
		if((new File(kiekerDir)).exists()){
			final Configuration fsReaderConfig = new Configuration(); 
			fsReaderConfig.setProperty(FSReader.CONFIG_PROPERTY_NAME_INPUTDIRS,kiekerDir);
			Configuration filterConfig = new Configuration();
			IAnalysisController analysisController = new AnalysisController();
			reader = new FSReader(fsReaderConfig,analysisController);
			final PerformanceFilter performanceFilter = new PerformanceFilter(filterConfig,analysisController,ruleCalls,startStopRecords,startRecordToRuleCallsIdx);	
			try{
				analysisController.connect(reader, FSReader.OUTPUT_PORT_NAME_RECORDS,performanceFilter,PerformanceFilter.INPUT_PORT_NAME_PERFORMANCE_VISUALIZATION);
				analysisController.run();		
			}catch(IllegalStateException | AnalysisConfigurationException e) {
					e.printStackTrace();
			}
		}
	}
}
