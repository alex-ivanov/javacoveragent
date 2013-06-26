package com.coveragent.codeAssist;

import com.coveragent.calculator.CoverageCalculator;
import com.coveragent.calculator.StatisticsHolder;

import org.objectweb.asm.*;

/**
 * @author ivanalx
 * @date 26.01.2009 15:48:26
 */
public class CoverageMethodVisitor extends MethodAdapter {
	private final MethodVisitor methodVisitor;
	private final long methodId;

	private final boolean allowMethodCoverage;

	public CoverageMethodVisitor(StatisticsHolder statisticsHolder, String className, MethodVisitor writer,
								 String name, String methodDesc,
								 boolean allowMethodCoverage)
	{
		super(writer);

		this.methodVisitor = writer;
		this.methodId = statisticsHolder.rememberMethod(className, name + methodDesc);
		this.allowMethodCoverage = allowMethodCoverage;
	}

	@Override
	public void visitCode() {
		if (allowMethodCoverage) {
			methodVisitor.visitLdcInsn(this.methodId);
			methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC,
					CoverageCalculator.CODE_COVERAGE_CALCULATOR_CLASS_NAME,
					CoverageCalculator.CODE_COVERAGE_CALCULATOR_METHOD_METHOD_NAME,
					CoverageCalculator.CODE_COVERAGE_CALCULATOR_METHOD_METHOD_DESC);
		}
		methodVisitor.visitCode();
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		methodVisitor.visitLineNumber(line, start);
	}
}
