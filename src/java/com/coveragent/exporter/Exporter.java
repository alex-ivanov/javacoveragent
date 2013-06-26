package com.coveragent.exporter;

import com.coveragent.calculator.StatisticsHolder;
import com.coveragent.codeAssist.Configuration;

import java.lang.instrument.Instrumentation;

/**
 * @author ivanalx
 * @date 28.01.2009 14:06:50
 */
public interface Exporter {
	public void export(StatisticsHolder invokeStatistics, Configuration configuration, Instrumentation instrument);
}
