package org.eclipse.emf.henshin.monitoring.kieker.records;

import java.nio.BufferOverflowException;

import kieker.common.record.AbstractMonitoringRecord;
import kieker.common.record.IMonitoringRecord;
import kieker.common.record.io.IValueSerializer;
import kieker.common.util.registry.IRegistry;

public class VariableInfoRecord extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory{
	public static final int SIZE =(3*TYPE_SIZE_STRING)+TYPE_SIZE_INT+TYPE_SIZE_LONG;
	public static final Class<?>[] TYPES = {String.class,String.class,String.class,Integer.class,Long.class};

	/**
	 * 
	 */
	private static final long serialVersionUID = -9148963790244209862L;

	private String variableId;
	private String name;
	private String type;
	private int searchplanIdx;
	private long domainSize;
	
	public VariableInfoRecord(String variableId, String name, String type, int searchplanIdx, long domainSize){
		this.variableId=variableId;
		this.name=name;
		this.type=type;
		this.searchplanIdx=searchplanIdx;
		this.domainSize=domainSize;
	}
	
	public VariableInfoRecord(Object[] arg0){
		this.variableId=(String) arg0[0];
		this.name=(String) arg0[1];
		this.type=(String) arg0[2];
		this.searchplanIdx=(Integer) arg0[3];
		this.domainSize=(Long) arg0[4];
	}
	
	@Override
	public int getSize() {
		return SIZE;
	}

	@Override
	public String[] getValueNames() {
		return new String[]{"variableId","name","type","searchplanIdx","domainSize"};
	}

	@Override
	public Class<?>[] getValueTypes() {
		return VariableInfoRecord.TYPES;
	}

	@Override
	public void initFromArray(Object[] arg0) {
		this.variableId=(String) arg0[0];
		this.name=(String) arg0[1];
		this.type=(String) arg0[2];
		this.searchplanIdx=(Integer) arg0[3];
		this.domainSize=(Long) arg0[4];
	}

	@Override
	public void registerStrings(IRegistry<String> registry) {
		registry.get(this.variableId);
		registry.get(this.name);
		registry.get(this.type);
		registry.get(Integer.toString(this.searchplanIdx));
		registry.get(Long.toString(this.domainSize));	
	}

	@Override
	public void serialize(IValueSerializer serializer) throws BufferOverflowException {
		serializer.putString(this.variableId);
		serializer.putString(this.name);
		serializer.putString(this.type);
		serializer.putInt(this.searchplanIdx);
		serializer.putLong(this.domainSize);
	}

	@Override
	public Object[] toArray() {
		return new Object[]{this.variableId,this.name,this.type,this.searchplanIdx,this.domainSize};
	}

	public String getVariableId() {
		return this.variableId;
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}

	public int getSearchplanIdx() {
		return this.searchplanIdx;
	}

	public long getDomainSize() {
		return this.domainSize;
	}


}
