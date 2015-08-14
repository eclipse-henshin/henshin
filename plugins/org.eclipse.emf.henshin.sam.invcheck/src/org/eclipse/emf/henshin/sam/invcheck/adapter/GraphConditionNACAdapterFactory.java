package org.eclipse.emf.henshin.sam.invcheck.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;

public class GraphConditionNACAdapterFactory implements IAdapterFactory{
	
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == NegativeApplicationCondition.class) {
			return GCNACAdapter.getInstance((GraphCondition) adaptableObject);
		}
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] {NegativeApplicationCondition.class};
	}

}