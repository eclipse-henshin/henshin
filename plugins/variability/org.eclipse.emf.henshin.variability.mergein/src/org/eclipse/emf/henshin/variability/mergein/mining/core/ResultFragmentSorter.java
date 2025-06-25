package org.eclipse.emf.henshin.variability.mergein.mining.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.henshin.variability.mergein.mining.labels.IEdgeLabel;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.INodeLabel;

import de.parsemis.miner.general.Fragment;

public class ResultFragmentSorter {

	private static final Comparator<Fragment<INodeLabel, IEdgeLabel>> BY_FREQUENCY_THEN_MAXIMALNONOVERLAPPINGSUBSET_SIZE = Comparator
			.<Fragment<INodeLabel, IEdgeLabel>>comparingInt(f -> Integer.parseInt(f.frequency().toString()))
			.thenComparingInt(f -> f.getMaximalNonOverlappingSubSet().size());

	public static void sort(List<Fragment<INodeLabel, IEdgeLabel>> fragmentList) {
		Collections.sort(fragmentList, BY_FREQUENCY_THEN_MAXIMALNONOVERLAPPINGSUBSET_SIZE);
	}
}
