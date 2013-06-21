package ru.greeneyes.codeCoverage.codeAssist;

import ru.greeneyes.codeCoverage.calculator.CodeCoverageStore;

import org.objectweb.asm.*;

/**
 * @author ivanalx
 * @date 26.01.2009 15:48:26
 */
public class CoverageMethodVisitor extends MethodAdapter {
	private final MethodVisitor methodVisitor;
	private final int classId;
	private final int methodId;

	private final boolean allowMethodCoverage;
	private final boolean allowLineCoverage;

	public CoverageMethodVisitor(int classId, MethodVisitor writer,String name, String methodDesc , boolean allowMethodCoverage, boolean allowLineCoverage) {
		super(writer);

		this.methodVisitor = writer;
		this.classId = classId;
		this.methodId = CodeCoverageStore.registerMethod(classId, name, methodDesc);
		this.allowLineCoverage = allowLineCoverage;
		this.allowMethodCoverage = allowMethodCoverage;
	}

	@Override
	public void visitCode() {
		if (allowMethodCoverage) {
			methodVisitor.visitLdcInsn(this.classId);
			methodVisitor.visitLdcInsn(this.methodId);
			methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC,
					CodeCoverageStore.CODE_COVERAGE_CALCULATOR_CLASS_NAME,
					CodeCoverageStore.CODE_COVERAGE_CALCULATOR_METHOD_METHOD_NAME,
					CodeCoverageStore.CODE_COVERAGE_CALCULATOR_METHOD_METHOD_DESC);
		}
		methodVisitor.visitCode();
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		if (allowLineCoverage) {
			methodVisitor.visitLdcInsn(this.classId);
			methodVisitor.visitLdcInsn(line);
			methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC,
					CodeCoverageStore.CODE_COVERAGE_CALCULATOR_CLASS_NAME,
					CodeCoverageStore.CODE_COVERAGE_CALCULATOR_CODE_METHOD_NAME,
					CodeCoverageStore.CODE_COVERAGE_CALCULATOR_CODE_METHOD_DESC);
		}
		methodVisitor.visitLineNumber(line, start);
	}
}
