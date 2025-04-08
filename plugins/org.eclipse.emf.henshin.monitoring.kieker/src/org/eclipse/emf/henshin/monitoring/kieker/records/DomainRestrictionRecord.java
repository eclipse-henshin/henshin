package org.eclipse.emf.henshin.monitoring.kieker.records;

import java.nio.BufferOverflowException;

import kieker.common.record.AbstractMonitoringRecord;
import kieker.common.record.IMonitoringRecord;
import kieker.common.record.io.IValueSerializer;
import kieker.common.util.registry.IRegistry;

public class DomainRestrictionRecord extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory{
	public static final int SIZE =(2*TYPE_SIZE_STRING)+(2*TYPE_SIZE_LONG);
	public static final Class<?>[] TYPES = {String.class,String.class,Long.class,Long.class};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7626491514784418972L;
	private String matchedVariableId;
	private String restrictedVariableId;
	private long newDomainSize;
	private long count;
	
	public DomainRestrictionRecord(String matchedVariableId,String restrictedVariableId,long newDomainSize){
		this.matchedVariableId=matchedVariableId;
		this.restrictedVariableId=restrictedVariableId;
		this.newDomainSize=newDomainSize;
		this.count=1;
	}
	
	public DomainRestrictionRecord(Object[] arg0){
		this.matchedVariableId=(String)arg0[0];
		this.restrictedVariableId=(String)arg0[1];
		this.newDomainSize=(long)arg0[2];
		this.count=(long)arg0[3];
	}
	

	@Override
	public int getSize() {
		return SIZE;
	}
	
	@Override
	public String[] getValueNames() {
		return new String[]{"matchedVariableId","restrictedVariableId","newDomainSize","count"};
	}
	
	@Override
	public Class<?>[] getValueTypes() {
		return DomainRestrictionRecord.TYPES;
	}
	
	@Override
	public void initFromArray(Object[] arg0) {
		this.matchedVariableId=(String)arg0[0];
		this.restrictedVariableId=(String)arg0[1];
		this.newDomainSize=(long)arg0[2];
		this.count=(long)arg0[3];
	}
	
	@Override
	public void registerStrings(IRegistry<String> registry) {
		registry.get(this.matchedVariableId);
		registry.get(this.restrictedVariableId);
		registry.get(Long.toString(this.newDomainSize));
		registry.get(Long.toString(this.count));
	}
	
	@Override
	public void serialize(IValueSerializer serializer) throws BufferOverflowException {
		serializer.putString(this.matchedVariableId);
		serializer.putString(this.restrictedVariableId);
		serializer.putLong(this.newDomainSize);
		serializer.putLong(this.count);
	}
	
	@Override
	public Object[] toArray() {
		return new Object[]{this.matchedVariableId,this.restrictedVariableId,this.newDomainSize,this.count};
	}
	
	public String getMatchedVariableId() {
		return this.matchedVariableId;
	}
	
	public String getRestrictedVariableId() {
		return this.restrictedVariableId;
	}
	
	public long getNewDomainSize() {
		return this.newDomainSize;
	}
	
	public void setNewDomainSize(long newDomainSize) {
		this.newDomainSize = newDomainSize;
	}

	public long getCount() {
		return this.count;
	}
	
	public void incCount(){
		this.count++;
	}

}
