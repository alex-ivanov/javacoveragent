package com.coveragent.codeAssist;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Dmitry Kandalov
 * @version $Id: $
 */
public class Configuration {
	private static final String INCLUDE_LINES = "OPTION.LINES.INCLUDE";
	private static final String INCLUDE_METHODS = "OPTION.METHODS.INCLUDE";
	private static final String INCLUDE_PREFIX = "INCLUDE.";
	private static final String EXCLUDE_PREFIX = "EXCLUDE.";
	private static final String EXPORTER_TYPE_PROPERTY_NAME = "EXPORTER.TYPE";
	private static final String FILE_NAME_PROPERTY = "SIMPLE.EXPORTER.FILE.NAME";
	private static final String OLD_FILE_RENAME_PATTERN = "SIMPLE.EXPORTER.RENAME.PATTERN";

	private final Set<String> includePrefix;
	private final Set<String> excludePrefix;
	private boolean coverageLines;
	private boolean coverageMethods;
	private String exporterName;
	private String fileName;
	private String renamePattern;

	public Configuration(Properties properties) {
		coverageLines = Boolean.valueOf(properties.getProperty(INCLUDE_LINES, "false"));
		coverageMethods = Boolean.valueOf(properties.getProperty(INCLUDE_METHODS, "true"));
		exporterName = properties.getProperty(EXPORTER_TYPE_PROPERTY_NAME, "simple.console");

		includePrefix = new HashSet<String>();
		excludePrefix = new HashSet<String>();
		Set<Object> keys = properties.keySet();
		for (Object o : keys) {
			String key = (String) o;
			if (key.startsWith(INCLUDE_PREFIX)) {
				String s = properties.getProperty(key);
				if (s.trim().length() > 0) {
					includePrefix.add(s);
				}
			}
			if (key.startsWith(EXCLUDE_PREFIX)) {
				String s = properties.getProperty(key);
				if (s.trim().length() > 0) {
					excludePrefix.add(s);
				}
			}
		}

		fileName = properties.getProperty(FILE_NAME_PROPERTY, "export.xml");
		renamePattern = properties.getProperty(OLD_FILE_RENAME_PATTERN, "_yyyyy_MMM_dd_HH_mm");
	}

	public Set<String> getIncludePrefix() {
		return includePrefix;
	}

	public Set<String> getExcludePrefix() {
		return excludePrefix;
	}

	public boolean isCoverageLines() {
		return coverageLines;
	}

	public boolean isCoverageMethods() {
		return coverageMethods;
	}

	public String getExporterName() {
		return exporterName;
	}

	public String getExportFileName() {
		return fileName;
	}

	public String getExportFileRenamePattern() {
		return renamePattern;
	}
}
