package com.coveragent.calculator.threadLocal;

import com.coveragent.calculator.MutableLong;
import gnu.trove.impl.hash.TLongHash;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;

import java.util.HashMap;
import java.util.Map;

/**
 * author: alex
 * date  : 6/24/13
 */
public class ThreadLocalVisitStatistics {
	private final TLongSet set = new TLongHashSet();
	private final StatisticsSink sink;

	public ThreadLocalVisitStatistics(StatisticsSink sink) {
		this.sink = sink;
	}

	public void visitEntry(long id) {
		set.add(id);
	}

	public TLongSet visitSet() {
		return set;
	}

	@Override
	protected void finalize() throws Throwable {
		sink.sink(this);
		super.finalize();
	}
}
