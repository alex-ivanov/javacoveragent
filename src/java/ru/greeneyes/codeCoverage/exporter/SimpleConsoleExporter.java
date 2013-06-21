package ru.greeneyes.codeCoverage.exporter;

import ru.greeneyes.codeCoverage.calculator.InvokeStatistics;
import ru.greeneyes.codeCoverage.calculator.MethodNameStore;
import ru.greeneyes.codeCoverage.codeAssist.Configuration;

import java.lang.instrument.Instrumentation;
import java.util.Map;

/**
 * @author ivanalx
 * @date 28.01.2009 15:13:30
 */
public class SimpleConsoleExporter implements Exporter {
	public void export(InvokeStatistics invokeStatistics) {
		Map<String, Map<MethodNameStore, Boolean>> methodInvokeStatistics = invokeStatistics.generateMethodInvokeStatistics();
		System.err.println("Method invoke statistics:");
		for (Map.Entry<String, Map<MethodNameStore, Boolean>> entry : methodInvokeStatistics.entrySet()) {
			System.err.println("Class: " + entry.getKey());
			Map<MethodNameStore, Boolean> value = entry.getValue();
			for (Map.Entry<MethodNameStore, Boolean> methodEntry : value.entrySet()) {
				System.err.println("    " + methodEntry.getKey() + "               " + methodEntry.getValue());
			}
		}
	}

	public void export(InvokeStatistics invokeStatistics, Configuration configuration, Instrumentation instrument) {
		export(invokeStatistics);
	}
}
