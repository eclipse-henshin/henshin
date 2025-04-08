package org.eclipse.emf.henshin.monitoring.kieker.records;

import java.nio.BufferOverflowException;

import kieker.common.record.AbstractMonitoringRecord;
import kieker.common.record.IMonitoringRecord;
import kieker.common.record.io.IValueSerializer;
import kieker.common.util.registry.IRegistry;

public class UnitExecutionStartRecord  extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory{
	public static final int SIZE =(2*TYPE_SIZE_STRING)+TYPE_SIZE_LONG;
	public static final Class<?>[] TYPES = {String.class,String.class,Long.class};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7027226727312667899L;
	
	private String unitName;
	private String type;
	private long startTimeStamp;
	
	public UnitExecutionStartRecord(String unitName,String type){
		this.unitName= unitName;
		this.type=type;
		this.startTimeStamp=System.nanoTime();
	}
	
	public UnitExecutionStartRecord(Object[] arg0){
		this.unitName=(String)arg0[0];
		this.type=(String)arg0[1];
		this.startTimeStamp=(long)arg0[2];
	}
	
	@Override
	public int getSize() {
		return SIZE;
	}
	@Override
	public String[] getValueNames() {
		return new String[]{"unitName","type","startTimeStamp"};
	}
	
	@Override
	public Class<?>[] getValueTypes() {
		return UnitExecutionStartRecord.TYPES;
	}
	
	@Override
	public void initFromArray(Object[] arg0) {
		this.unitName=(String)arg0[0];
		this.type=(String)arg0[1];
		this.startTimeStamp=(long)arg0[2];
	}
	
	@Override
	public void registerStrings(IRegistry<String> registry) {
		registry.get(this.unitName);
		registry.get(this.type);
		registry.get(Long.toString(this.startTimeStamp));
	}
	
	@Override
	public void serialize(IValueSerializer serializer) throws BufferOverflowException {
		serializer.putString(this.unitName);
		serializer.putString(this.type);
		serializer.putLong(this.startTimeStamp);
	}
	
	@Override
	public Object[] toArray() {
		return new Object[]{this.unitName,this.type,this.startTimeStamp};
	}

	
	public String getUnitName() {
		return this.unitName;
	}

	public String getType() {
		return this.type;
	}

	public long getStartTimeStamp() {
		return this.startTimeStamp;
	}
	
}
