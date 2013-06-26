package com.coveragent.codeAssist;

import com.coveragent.calculator.StatisticsHolder;
import com.coveragent.calculator.threadLocal.ThreadLocalInterceptor;
import com.coveragent.exporter.Exporter;
import com.coveragent.exporter.SimpleConsoleExporter;
import com.coveragent.exporter.SimpleFileExporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author ivanalx
 * @date 26.01.2009 15:09:11
 */
@SuppressWarnings("UnusedDeclaration")
public class CoverageAgent {
	private static final Map<String, Exporter> exporters = new HashMap<String, Exporter>();
	static {
		exporters.put("simple.console", new SimpleConsoleExporter());
		exporters.put("simple.file", new SimpleFileExporter());
	}

	public static void premain(String agentArgs, Instrumentation instrument) {
		Properties properties = loadProperties(agentArgs);
		Configuration configuration = new Configuration(properties);

		Exporter exporter = exporters.get(configuration.getExporterName());
		if (exporter == null) {
			System.err.println("Error in Coverage agent configuration. Please specify right name.");
			System.err.println("Available exporters are " + exporters.keySet());
		}

		System.err.println("WARNING! Code coverage is enabled");
		if (configuration.isCoverageLines()) {
			System.err.println("Coverage enabled for lines.");
		}
		if (configuration.isCoverageMethods()) {
			System.err.println("Coverage enabled for methods.");
		}

		StatisticsHolder holder = new ThreadLocalInterceptor();

		ClassTransformer transformer = new ClassTransformer(holder);
		transformer.setCoverageLine(configuration.isCoverageLines());
		transformer.setCoverageMethod(configuration.isCoverageMethods());
		transformer.setIncludePrefix(configuration.getIncludePrefix());
		transformer.setExcludePrefix(configuration.getExcludePrefix());
		instrument.addTransformer(transformer);

		Runtime.getRuntime().addShutdownHook(new StatisticsExporterHook(holder, exporter, configuration, instrument));
	}

	private static Properties loadProperties(String agentArgs) {
		Properties properties = new Properties();
		try {
			if (agentArgs != null && agentArgs.trim().length() != 0) {
				File propertiesFile = new File(agentArgs);
				if (!propertiesFile.exists()) {
					throw new RuntimeException("Code coverage agent can't load property file from " + agentArgs + ". File not exists.");
				}
				try {
					properties.load(new FileInputStream(propertiesFile));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		} catch (Exception e) { // TODO why catch Exception ?
			throw new RuntimeException(e);
		}
		return properties;
	}

}
