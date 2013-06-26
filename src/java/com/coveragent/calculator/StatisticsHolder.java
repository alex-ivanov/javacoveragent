package com.coveragent.calculator;

import java.lang.reflect.Method;

/**
 * author: alex
 * date  : 6/25/13
 */
public interface StatisticsHolder {
	public long rememberMethod(String clazz, String method);

	public void grabAllStatistics();

	public String className(long methodId);

	public String methodName(long methodId);

	public boolean isVisited(long methodId);

	public long lastMethodId();
}
