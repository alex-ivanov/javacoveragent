package com.coveragent.codeAssist;

import com.coveragent.calculator.StatisticsHolder;
import com.coveragent.exporter.Exporter;

import java.lang.instrument.Instrumentation;

/**
 * @author ivanalx
 * @date 28.01.2009 15:17:29
 */
public class StatisticsExporterHook extends Thread {
	private StatisticsHolder invokeStatistics;
	private Exporter exporter;
	private final Configuration configuration;
	private Instrumentation instrumentation;

	public StatisticsExporterHook(StatisticsHolder invokeStatistics, Exporter exporter,
								  Configuration configuration, Instrumentation instrumentation)
	{
		this.invokeStatistics = invokeStatistics;
		this.exporter = exporter;
		this.configuration = configuration;
		this.instrumentation = instrumentation;
	}

	public void run() {
		invokeStatistics.grabAllStatistics();
		exporter.export(invokeStatistics, configuration, instrumentation);
	}
}
