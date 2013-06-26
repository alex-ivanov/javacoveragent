package com.coveragent.codeAssist;

import com.coveragent.calculator.StatisticsHolder;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * @author ivanalx
 * @date 26.01.2009 15:47:10
 */
public class CoverageClassVisitor extends ClassAdapter {
	private final StatisticsHolder statisticsHolder;
	private final ClassWriter writer;
	private final String className;

	private final boolean allowMethodCoverage;

	public CoverageClassVisitor(StatisticsHolder statisticsHolder, ClassWriter writer,
								String className, boolean allowMethodCoverage)
	{
		super(writer);
		this.statisticsHolder = statisticsHolder;

		this.writer = writer;
		this.className = className;
		this.allowMethodCoverage = allowMethodCoverage;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor methodVisitor = writer.visitMethod(access, name, desc, signature, exceptions);
		return new CoverageMethodVisitor(statisticsHolder, className, methodVisitor,
				name, desc, allowMethodCoverage);
	}
}
