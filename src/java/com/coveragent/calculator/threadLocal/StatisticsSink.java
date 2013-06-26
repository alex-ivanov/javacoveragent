package com.coveragent.calculator.threadLocal;

/**
 * author: alex
 * date  : 6/24/13
 */
public interface StatisticsSink {
	public void sink(ThreadLocalVisitStatistics statistics);
}
