package org.eclipse.emf.henshin.monitoring.kieker.util;

import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.emf.henshin.monitoring.ui.util.RuleVisualizationData;

public class RuleExecutionData {
	private HashMap<Integer,String> searchplanToId;
	private HashMap<String,String> idToName;
	private HashMap<String,Long> idToInitDomain;
	private HashMap<String,Long> idToBacktrack;
	private HashMap<String,Long> idToInvestigated;
	private HashMap<String,Long> idToSearchSpace; //Used to sum up search space
	private HashMap<String,HashMap<String,Restriction>> idToRestrictions; //id of the bound variables to the ids of the variables that are constrained 
	
	private RuleVisualizationData ruleVisualizations;
	
	
	public RuleExecutionData(String name){
		this.searchplanToId=new HashMap<Integer,String>();
		this.idToName=new HashMap<String,String>();
		this.idToInitDomain=new HashMap<String,Long>();
		this.idToBacktrack=new HashMap<String,Long>();
		this.idToInvestigated=new HashMap<String,Long>();
		this.idToSearchSpace=new HashMap<String,Long>();
		this.idToRestrictions=new HashMap<String,HashMap<String,Restriction>>();
		this.ruleVisualizations=new RuleVisualizationData(name);
	}
	
	/**
	 * Sets in ruleVisualizations the values necessary for the visualization
	 */
	public void setVisualizationData() {
		LinkedList<String> nodeNames=this.createNodeList();
		this.ruleVisualizations.setNodeNames(nodeNames);
		this.ruleVisualizations.setNumberOfBacktracking(this.createBacktrackingList());
		this.ruleVisualizations.setDataMap(this.crateContraintMap());
		this.ruleVisualizations.setInvestigated(this.createInvestigateList());
		this.ruleVisualizations.setDomainSize(this.createDomainSizeList());
	}
	
	/**
	 * Creates a 2 dimensional array containing the domain restrictions during matching. 
	 * 
	 * @return 2 dimensional array presenting the domains 
	 */
	private long[][] crateContraintMap() {
		long[][] dataMap=new long[this.searchplanToId.size()][this.searchplanToId.size()];
		for(int spalte=0;spalte<dataMap.length;spalte++){
			for(int zeile=0;zeile<dataMap[0].length;zeile++){
				if(zeile>0){
					if(spalte<=(zeile-1)){
						dataMap[spalte][zeile]=-1;
					}else{
						String matchedId=this.searchplanToId.get(zeile-1);
						if(idToRestrictions.containsKey(matchedId)){
							if(this.idToRestrictions.get(matchedId).containsKey(this.searchplanToId.get(spalte))){
								dataMap[spalte][zeile]=this.idToRestrictions.get(matchedId).get(this.searchplanToId.get(spalte)).getAverage();
							}else{
								dataMap[spalte][zeile]=dataMap[spalte][zeile-1];
							}
						}else{
							dataMap[spalte][zeile]=dataMap[spalte][zeile-1];
						}
					}	
				}else{
					dataMap[spalte][zeile]=this.idToInitDomain.get(this.searchplanToId.get(spalte));
				}
				
			}
		}
		return dataMap;
	}


	/**
	 * Creates a list of all sizes of the domains at the beginning of the matching in searchplan order. 
	 * @return List of initial domain sizes in searchplan order. 
	 */
	private LinkedList<Long> createDomainSizeList() {
		LinkedList<Long> domainSize=new LinkedList<Long>();
		
		for(int idx=0;idx<this.searchplanToId.size();idx++){
			String id=this.searchplanToId.get(idx);
			if(this.idToInitDomain.containsKey(id)){
				domainSize.add(this.idToInitDomain.get(id));
			}else{
				domainSize.add(0l);
			}
		}
		
		return domainSize;
	}

	/**
	 * Creates a list containing the number of investigated model elements per variable in searchplan order. 
	 * 
	 * @return List containing the number of investigated model elements in searchplan order. 
	 */
	private LinkedList<Long> createInvestigateList() {
		LinkedList<Long> investigated=new LinkedList<Long>();
		for(int idx=0;idx<this.searchplanToId.size();idx++){
			String id=this.searchplanToId.get(idx);
			if(this.idToInvestigated.containsKey(id)){
				investigated.add(this.idToInvestigated.get(id));
			}else{
				investigated.add(0l);
			}
		}
		return investigated;
	}

	/**
	 * Creates a list containing the number of backtracking per variable in searchplan order. 
	 * 
	 * @return List containing the number of backtracking in searchplan order. 
	 */
	private LinkedList<Long> createBacktrackingList() {
		LinkedList<Long> numberOfBacktracking=new LinkedList<Long>();
		
		for(int idx=0;idx<this.searchplanToId.size();idx++){
			String id=this.searchplanToId.get(idx);
			if(this.idToBacktrack.containsKey(id)){
				numberOfBacktracking.add(this.idToBacktrack.get(id));
			}else{
				numberOfBacktracking.add(0l);
			}
		}
		
		return numberOfBacktracking;
	}


	/**
	 * Creates a list containing the names of the nodes in searchplan order
	 * 
	 * @return List of node names in searchplan order 
	 */
	private LinkedList<String> createNodeList() {
		LinkedList<String> nodeNames=new LinkedList<String>();
		for(int idx=0;idx<this.searchplanToId.size();idx++){
			nodeNames.add(this.idToName.get(this.searchplanToId.get(idx)).split(":")[0]);
		}
		return nodeNames;
	}


	public void updateSearchSpace(String id,long size){
		if(this.idToSearchSpace.containsKey(id)){
			this.idToSearchSpace.put(id,this.idToSearchSpace.get(id)+size);
		}else{
			this.idToSearchSpace.put(id,size);
		}
	}
	
	public void updateInvestigated(String id,long size){
		if(this.idToInvestigated.containsKey(id)){
			this.idToInvestigated.put(id,this.idToInvestigated.get(id)+size);
		}else{
			this.idToInvestigated.put(id,size);
		}	
	}
	
	
	public void updateRuleNodeInfo(String id,String name,int order,long size){
		this.searchplanToId.put(order,id);
		this.idToName.put(id,name);
		this.idToInitDomain.put(id,size);
	}
	
	public void updateBacktracking(String id, long count){
		if(this.idToBacktrack.containsKey(id)){
			this.idToBacktrack.put(id,this.idToBacktrack.get(id)+count);
		}else{
			this.idToBacktrack.put(id,count);
		}
	}
	
	public void updateRestriction(String match,String restrictedVar,long newSize,long number){
		Restriction restrict=null;
		if(idToRestrictions.containsKey(match)){
			if(idToRestrictions.get(match).containsKey(restrictedVar)){
				restrict=(idToRestrictions.get(match).get(restrictedVar));
				restrict.updateRestriction(newSize,number);
			}else{
				restrict=new Restriction(newSize,number);
				idToRestrictions.get(match).put(restrictedVar, restrict);	
			}
		}else{
			restrict=new Restriction(newSize,number);
			HashMap<String,Restriction> map=new HashMap<String,Restriction>();
			map.put(restrictedVar, restrict);
			idToRestrictions.put(match,map);
		}
	}
	
	public RuleVisualizationData getRuleVisualizations() {
		return this.ruleVisualizations;
	}

}
