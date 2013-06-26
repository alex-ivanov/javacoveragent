package com.coveragent.exporter;

import com.coveragent.calculator.StatisticsHolder;
import com.coveragent.codeAssist.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ivanalx
 * @date 29.01.2009 10:59:38
 */
public class SimpleFileExporter implements Exporter {

	public void export(StatisticsHolder statisticsHolder, Configuration configuration, Instrumentation instrument) {
		Class[] classes = instrument.getAllLoadedClasses();
		Map<String, Class> classMap = new HashMap<String, Class>();
		for (Class aClass : classes) {
			classMap.put(aClass.getName(), aClass);
		}
		String fileName = configuration.getExportFileName();
		File f = new File(fileName);
		try {
			if (f.exists()) {
				DateFormat df = new SimpleDateFormat(configuration.getExportFileRenamePattern());
				Date date = new Date();
				String s = df.format(date);
				File file = new File(f.getParentFile(), f.getName() + s);
				if (file.exists()) {
					file = new File(f.getParentFile(), f.getName() + s + date.getTime());
				}
				boolean b = f.renameTo(file);
				if (!b) {
					System.err.println("Can't rename old file to new one with name: " + file);
				}
			}
			FileWriter writer = new FileWriter(f);
			writer.append("class name;method name and description;is visited?\n");
			long lastMethodId = statisticsHolder.lastMethodId();
			for (int i = 0; i < lastMethodId; i++) {
				String clazz = statisticsHolder.className(i);
				String methodName = statisticsHolder.methodName(i);

				writer.append(clazz).append(";").append(methodName).append(";").append(";");
				writer.append(statisticsHolder.isVisited(i) ? "yes" : "no");
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
