package org.eclipse.emf.henshin.monitoring.kieker.records;

import java.nio.BufferOverflowException;

import kieker.common.record.AbstractMonitoringRecord;
import kieker.common.record.IMonitoringRecord;
import kieker.common.record.io.IValueSerializer;
import kieker.common.util.registry.IRegistry;

public class BacktrackRecord extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory{
	public static final int SIZE =TYPE_SIZE_STRING+TYPE_SIZE_LONG;
	public static final Class<?>[] TYPES = {String.class,Long.class};

	/**
	 * 
	 */
	private static final long serialVersionUID = 5815543741055874703L;
	
	private String varIdNewBinding; //Variable for which a new binding must be looked for after it has already been bound once
	private long count;
	
	
	
	public BacktrackRecord(String varIdNewBinding){
		this.varIdNewBinding=varIdNewBinding;
		this.count=1;
	}
	
	public BacktrackRecord(Object[] arg0){
		this.varIdNewBinding=(String)arg0[0];
		this.count=(Long)arg0[1];
	}

	@Override
	public int getSize() {
		return SIZE;
	}

	@Override
	public String[] getValueNames() {
		return new String[]{"varIdNewBinding","count"};
	}

	@Override
	public Class<?>[] getValueTypes() {
		return BacktrackRecord.TYPES;
	}

	@Override
	public void initFromArray(Object[] arg0) {
		this.varIdNewBinding=(String)arg0[0];
		this.count=(Long)arg0[1];
	}

	@Override
	public void registerStrings(IRegistry<String> registry) {
		registry.get(this.varIdNewBinding);
		registry.get(Long.toString(this.count));
	}

	@Override
	public void serialize(IValueSerializer serializer) throws BufferOverflowException {
		serializer.putString(this.varIdNewBinding);
		serializer.putLong(this.count);
	}

	@Override
	public Object[] toArray() {
		return new Object[]{this.varIdNewBinding,this.count};
	}


	public String getVarIdNewBinding() {
		return this.varIdNewBinding;
	}
	
	public long getCount() {
		return this.count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
