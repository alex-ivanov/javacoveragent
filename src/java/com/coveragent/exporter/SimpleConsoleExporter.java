package com.coveragent.exporter;

import com.coveragent.calculator.StatisticsHolder;
import com.coveragent.codeAssist.Configuration;

import java.lang.instrument.Instrumentation;

/**
 * @author ivanalx
 * @date 28.01.2009 15:13:30
 */
public class SimpleConsoleExporter implements Exporter {
	@Override
	public void export(StatisticsHolder statisticsHolder, Configuration configuration, Instrumentation instrument)
	{
		System.out.println("class name|method name and description|is visited?\n");
		long lastMethodId = statisticsHolder.lastMethodId();
		for (int i = 1; i <= lastMethodId; i++) {
			String clazz = statisticsHolder.className(i);
			String methodName = statisticsHolder.methodName(i);

			System.out.println(clazz + "|" + methodName + "|" + (statisticsHolder.isVisited(i) ? "yes" : "no"));
		}
	}
}
