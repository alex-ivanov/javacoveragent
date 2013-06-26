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
	public void export(StatisticsHolder invokeStatistics, Configuration configuration, Instrumentation instrument) {
	}
}
