package com.coveragent.calculator.threadLocal;

import com.coveragent.calculator.Interceptor;
import com.coveragent.calculator.StatisticsHolder;
import com.coveragent.utils.ThreadLocalUtils;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * author: alex
 * date  : 6/24/13
 */
public class ThreadLocalInterceptor implements Interceptor, StatisticsSink, StatisticsHolder {
	private final ThreadLocal<ThreadLocalVisitStatistics> HOLDER = new ThreadLocal<ThreadLocalVisitStatistics>() {
		@Override
		protected ThreadLocalVisitStatistics initialValue() {
			return new ThreadLocalVisitStatistics(ThreadLocalInterceptor.this);
		}
	};

	private final TLongSet allMethodsVisitSet = new TLongHashSet();

	private final AtomicLong methodIdGenerator = new AtomicLong(0);

	private final Map<ClazzMethodHolder, Long> reversIdMap = new HashMap<ClazzMethodHolder, Long>();
	private final TLongObjectMap<ClazzMethodHolder> idToObjectMap = new TLongObjectHashMap<ClazzMethodHolder>();

	public void methodEntry(long methodId) {
		HOLDER.get().visitEntry(methodId);
	}

	@Override
	public synchronized void sink(ThreadLocalVisitStatistics statistics) {
		allMethodsVisitSet.addAll(statistics.visitSet());
	}

	@Override
	public synchronized void grabAllStatistics() {
		List<ThreadLocalVisitStatistics> data = ThreadLocalUtils.allExistedThreadLocalValues(HOLDER);
		for (ThreadLocalVisitStatistics statistics : data)
			allMethodsVisitSet.addAll(statistics.visitSet());
	}

	@Override
	public synchronized long rememberMethod(String clazz, String method) {
		ClazzMethodHolder holder = new ClazzMethodHolder(clazz, method);
		Long prevId = reversIdMap.get(holder);
		if (prevId != null)
			return prevId;

		long nextId = methodIdGenerator.incrementAndGet();
		idToObjectMap.put(nextId, holder);
		reversIdMap.put(holder, nextId);
		return nextId;
	}

	@Override
	public String className(long methodId) {
		ClazzMethodHolder holder = idToObjectMap.get(methodId);
		return holder == null ? null : holder.clazz;
	}

	@Override
	public String methodName(long methodId) {
		ClazzMethodHolder holder = idToObjectMap.get(methodId);
		return holder == null ? null : holder.method;
	}

	@Override
	public boolean isVisited(long methodId) {
		return allMethodsVisitSet.contains(methodId);
	}

	@Override
	public long lastMethodId() {
		return methodIdGenerator.get();
	}

	private static class ClazzMethodHolder {
		private final String clazz;
		private final String method;

		private ClazzMethodHolder(String clazz, String method) {
			this.clazz = clazz;
			this.method = method;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			ClazzMethodHolder that = (ClazzMethodHolder) o;

			if (!clazz.equals(that.clazz)) return false;
			if (!method.equals(that.method)) return false;

			return true;
		}

		@Override
		public int hashCode() {
			int result = clazz.hashCode();
			result = 31 * result + method.hashCode();
			return result;
		}
	}
}
