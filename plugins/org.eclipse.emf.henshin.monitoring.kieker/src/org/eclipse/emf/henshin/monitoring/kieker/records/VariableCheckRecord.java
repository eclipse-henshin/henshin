package org.eclipse.emf.henshin.monitoring.kieker.records;

import java.nio.BufferOverflowException;

import org.eclipse.emf.henshin.interpreter.monitoring.VariableCheck;

import kieker.common.record.AbstractMonitoringRecord;
import kieker.common.record.IMonitoringRecord;
import kieker.common.record.io.IValueSerializer;
import kieker.common.util.registry.IRegistry;

public class VariableCheckRecord extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory{
	public static final int SIZE =TYPE_SIZE_STRING+(2*TYPE_SIZE_LONG)+TYPE_SIZE_BOOLEAN;
	public static final Class<?>[] TYPES = {String.class,Long.class,Long.class,Boolean.class};

	/**
	 * 
	 */
	private static final long serialVersionUID = 8841784634179390954L;
	private String variableId;
	private long domainSize;
	private long checkedModelElements;
	private boolean initNewDomain;
	
	public VariableCheckRecord(VariableCheck varCheck){
		this.variableId=varCheck.getVariableId();
		this.domainSize=varCheck.getDomainSize();
		this.checkedModelElements=varCheck.getCheckedModelElements();
		this.initNewDomain=varCheck.isInitNewDomain();
	}
	
	public VariableCheckRecord(Object[] arg0){
		this.variableId=(String) arg0[0];
		this.domainSize=(Long) arg0[1];
		this.checkedModelElements=(Long) arg0[2];
		this.initNewDomain=(Boolean) arg0[3];
	}

	@Override
	public int getSize() {
		return SIZE;
	}

	@Override
	public String[] getValueNames() {
		return new String[]{"variableId","domainSize","checkedModelElements","initNewDomain"};
	}

	@Override
	public Class<?>[] getValueTypes() {
		return VariableCheckRecord.TYPES;
	}

	@Override
	public void initFromArray(Object[] arg0) {
		this.variableId=(String) arg0[0];
		this.domainSize=(Long) arg0[1];
		this.checkedModelElements=(Long) arg0[2];
		this.initNewDomain=(Boolean) arg0[3];
	}

	@Override
	public void registerStrings(IRegistry<String> registry) {
		registry.get(this.variableId);
		registry.get(Long.toString(this.domainSize));
		registry.get(Long.toString(this.checkedModelElements));
		registry.get(Boolean.toString(this.initNewDomain));
	}

	@Override
	public void serialize(IValueSerializer serializer) throws BufferOverflowException {
		serializer.putString(this.variableId);
		serializer.putLong(this.domainSize);
		serializer.putLong(this.checkedModelElements);
		serializer.putBoolean(this.initNewDomain);
	}

	@Override
	public Object[] toArray() {
		return new Object[]{this.variableId,this.domainSize,this.checkedModelElements,this.initNewDomain};
	}
	
	public void incCheckedModelElements(){
		this.checkedModelElements++;
	}
	
	public String getVariableId() {
		return this.variableId;
	}

	public long getDomainSize() {
		return this.domainSize;
	}

	public void setDomainSize(long domainSize) {
		this.domainSize = domainSize;
	}

	public long getCheckedModelElements() {
		return this.checkedModelElements;
	}

	public void setCheckedModelElements(long checkedModelElements) {
		this.checkedModelElements = checkedModelElements;
	}

	public boolean isInitNewDomain() {
		return this.initNewDomain;
	}


}
