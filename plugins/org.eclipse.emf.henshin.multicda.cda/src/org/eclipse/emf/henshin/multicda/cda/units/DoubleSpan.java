package org.eclipse.emf.henshin.multicda.cda.units;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Jevgenij Huebert 18.05.2018 This interface provides usage of those
 *         reasons, that contains s2. (for instance Delete Delete Conflict
 *         Reason) All Reasons with same s1 span are summarized in one
 *         DoubleSpan object with a set of s2 spans
 */
public interface DoubleSpan {
	class Extensions {
		private static final Map<String, Set<Reason>> s2 = new HashMap<>();
		private static final Map<String, Reason> s1 = new HashMap<>();
	}

	default void init(DoubleSpan ds) {
		init(ds.getS1(), ds.getS2Set());
	}

	default void init(Reason s1, Reason s2) {
		HashSet<Reason> put = new HashSet<>();
		put.add(s2);
		init(s1, put);
	}

	default void init(Reason s1, Set<Reason> s2) {
		Extensions.s2.put(toString(), s2);
		Extensions.s1.put(toString(), s1);
	}

	default Set<Reason> getS2Set() {
		return Extensions.s2.get(toString());
	}

	default Reason getS1() {
		return Extensions.s1.get(toString());
	}

	default boolean isDoubleSpan() {
		return getS2Set() != null && !getS2Set().isEmpty();
	}
}
