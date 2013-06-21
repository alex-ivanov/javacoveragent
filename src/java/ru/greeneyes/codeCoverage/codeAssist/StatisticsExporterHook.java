package ru.greeneyes.codeCoverage.codeAssist;

import ru.greeneyes.codeCoverage.calculator.InvokeStatistics;
import ru.greeneyes.codeCoverage.exporter.Exporter;

import java.lang.instrument.Instrumentation;

/**
 * @author ivanalx
 * @date 28.01.2009 15:17:29
 */
public class StatisticsExporterHook extends Thread {
	private InvokeStatistics invokeStatistics;
	private Exporter exporter;
	private final Configuration configuration;
	private Instrumentation instrumentation;

	public StatisticsExporterHook(InvokeStatistics invokeStatistics, Exporter exporter, Configuration configuration, Instrumentation instrumentation) {
		this.invokeStatistics = invokeStatistics;
		this.exporter = exporter;
		this.configuration = configuration;
		this.instrumentation = instrumentation;
	}

	public void run() {
		exporter.export(invokeStatistics, configuration, instrumentation);
	}
}
