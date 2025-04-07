package org.eclipse.emf.henshin.monitoring.kieker.records;

import java.nio.BufferOverflowException;

import kieker.common.record.AbstractMonitoringRecord;
import kieker.common.record.IMonitoringRecord;
import kieker.common.record.io.IValueSerializer;
import kieker.common.util.registry.IRegistry;

public class UnitExecutionStopRecord  extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2125110953262556592L;
	public static final int SIZE =TYPE_SIZE_LONG;
	public static final Class<?>[] TYPES = {Long.class};
	
	private long stopTimeStamp;
	
	public UnitExecutionStopRecord(){
		this.stopTimeStamp=System.nanoTime();
	}
	
	public UnitExecutionStopRecord(Object[] arg0){
		this.stopTimeStamp=(long)arg0[0];
	}
	
	@Override
	public int getSize() {
		return SIZE;
	}
	@Override
	public String[] getValueNames() {
		return new String[]{"stopTimeStamp"};
	}
	@Override
	public Class<?>[] getValueTypes() {
		return UnitExecutionStopRecord.TYPES;
	}
	@Override
	public void initFromArray(Object[] arg0) {
		this.stopTimeStamp=(long)arg0[0];	
	}
	
	@Override
	public void registerStrings(IRegistry<String> registry) {
		registry.get(Long.toString(this.stopTimeStamp));
	}
	
	@Override
	public void serialize(IValueSerializer serializer) throws BufferOverflowException {
		serializer.putLong(this.stopTimeStamp);
	}
	
	@Override
	public Object[] toArray() {
		return new Object[]{this.stopTimeStamp};
	}

	public long getStopTimeStamp() {
		return this.stopTimeStamp;
	}


}
