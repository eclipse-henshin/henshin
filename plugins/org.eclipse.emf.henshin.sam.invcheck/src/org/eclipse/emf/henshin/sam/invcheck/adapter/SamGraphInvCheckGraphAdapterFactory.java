package org.eclipse.emf.henshin.sam.invcheck.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;

public class SamGraphInvCheckGraphAdapterFactory implements IAdapterFactory {

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == GraphWithNacs.class) {
			return SamGraphInvCheckGraphAdapter.getInstance((Graph) adaptableObject);
		}
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] { GraphWithNacs.class };
	}

}