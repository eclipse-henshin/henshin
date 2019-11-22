package org.eclipse.emf.henshin.variability.mergein.mining.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.henshin.variability.mergein.mining.labels.IEdgeLabel;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.INodeLabel;

import de.parsemis.miner.general.Fragment;

public class ResultFragmentSorter {

	public static void sort(List<Fragment<INodeLabel, IEdgeLabel>> fragmentList) {

		Collections.sort(fragmentList, new Comparator<Fragment>() {

			@Override
			public int compare(Fragment frag1, Fragment frag2) {
				int freq1 = Integer.parseInt(frag1.frequency().toString());
				int freq2 = Integer.parseInt(frag2.frequency().toString());
				if (freq1 == freq2) {
					int frag1size = frag1.getMaximalNonOverlappingSubSet().size();
					int frag2size = frag1.getMaximalNonOverlappingSubSet().size();
					if (frag1size == frag2size)
						return 0;
					return (frag1size > frag2size) ? 1 : -1;
				}
				return freq1 > freq2 ? 1 : -1;
			}
		});
	}
}
