package com.coveragent.calculator.threadLocal;

import gnu.trove.TCollections;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;

/**
 * author: alex
 * date  : 6/24/13
 */
public class ThreadLocalVisitStatistics {
	private final TLongSet set = new TLongHashSet();

	public ThreadLocalVisitStatistics() {
	}

	public void visitEntry(long id) {
		set.add(id);
	}

	public TLongSet visitSet() {
		return TCollections.unmodifiableSet(set);
	}
}
