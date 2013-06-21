package ru.greeneyes.codeCoverage.exporter;

import ru.greeneyes.codeCoverage.calculator.InvokeStatistics;
import ru.greeneyes.codeCoverage.calculator.MethodNameStore;
import ru.greeneyes.codeCoverage.codeAssist.Configuration;

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
public class SimpleFileExporter implements Exporter{

	public void export(InvokeStatistics invokeStatistics, Configuration configuration, Instrumentation instrument) {
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
					System.err.println("Could'n rename old file to new one with name: " + file);
				}
			}
			FileWriter writer = new FileWriter(f);
			Map<String, Map<MethodNameStore,Boolean>> results = invokeStatistics.generateMethodInvokeStatistics();
			for (Map.Entry<String, Map<MethodNameStore, Boolean>> entry : results.entrySet()) {

				String className = entry.getKey().replace('/', '.');
				Class cl = classMap.get(className);
				String type = "class";
				if (cl.isInterface()) {
					type = "interface";
				}
				writer.write("Class|" + type  + " |" + className + "|\n");
				if (!cl.isInterface()) {
					Map<MethodNameStore, Boolean> methodStatistics = entry.getValue();
					for (Map.Entry<MethodNameStore, Boolean> method : methodStatistics.entrySet()) {
						String methodName = method.getKey().getName();
						String methodDesc = method.getKey().getDesc();
						writer.write("Method|" + className + "|" + methodName + "|" + methodDesc + "|" + method.getValue() + "|\n");
					}
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
