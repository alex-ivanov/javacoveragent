package ru.greeneyes.codeCoverage.codeAssist;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Set;
import java.util.Collections;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import ru.greeneyes.codeCoverage.calculator.CodeCoverageStore;

/**
 * @author ivanalx
 * @date 26.01.2009 15:10:31
 */
public class ClassTransformer implements ClassFileTransformer {
	private volatile boolean coverageLine = false;
	private volatile boolean coverageMethod = true;

	private Set<String> includePrefix = Collections.emptySet();
	private Set<String> excludePrefix = Collections.emptySet();

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
	                        ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		boolean include = shouldInclude(className);
		if (include) {
			ClassReader reader = new ClassReader(classfileBuffer);
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);

			int id = CodeCoverageStore.registerClass(className);
			reader.accept(new CoverageClassVisitor(id, writer, coverageMethod, coverageLine), ClassReader.EXPAND_FRAMES);
			return writer.toByteArray();
		} else {
			return null;
		}
	}

	private boolean shouldInclude(String className) {
		boolean include = false;
		for (String s : includePrefix) {
			if (className.startsWith(s)) {
				include = true;
				break;
			}
		}
		for (String s : excludePrefix) {
			if (className.startsWith(s)) {
				include = false;
				break;
			}
		}
		return include;
	}

	public void setCoverageLine(boolean coverageLine) {
		this.coverageLine = coverageLine;
	}

	public void setCoverageMethod(boolean coverageMethod) {
		this.coverageMethod = coverageMethod;
	}

	public void setIncludePrefix(Set<String> includePrefix) {
		this.includePrefix = includePrefix;
	}

	public void setExcludePrefix(Set<String> excludePrefix) {
		this.excludePrefix = excludePrefix;
	}

}
