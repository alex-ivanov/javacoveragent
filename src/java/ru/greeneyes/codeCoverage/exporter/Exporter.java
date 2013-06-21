package ru.greeneyes.codeCoverage.exporter;

import ru.greeneyes.codeCoverage.calculator.InvokeStatistics;
import ru.greeneyes.codeCoverage.codeAssist.Configuration;

import java.lang.instrument.Instrumentation;

/**
 * @author ivanalx
 * @date 28.01.2009 14:06:50
 */
public interface Exporter {
	public void export(InvokeStatistics invokeStatistics, Configuration configuration, Instrumentation instrument);
}
