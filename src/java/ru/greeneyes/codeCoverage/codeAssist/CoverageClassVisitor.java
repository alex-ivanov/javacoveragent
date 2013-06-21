package ru.greeneyes.codeCoverage.codeAssist;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * @author ivanalx
 * @date 26.01.2009 15:47:10
 */
public class CoverageClassVisitor extends ClassAdapter {
	private final ClassWriter writer;
	private final int classId;

	private final boolean allowMethodCoverage;
	private final boolean allowLineCoverage;

	public CoverageClassVisitor(int classId, ClassWriter writer, boolean allowMethodCoverage, boolean allowLineCoverage) {
		super(writer);

		this.writer = writer;
		this.classId = classId;
		this.allowLineCoverage = allowLineCoverage;
		this.allowMethodCoverage = allowMethodCoverage;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor methodVisitor = writer.visitMethod(access, name, desc, signature, exceptions);
		return new CoverageMethodVisitor(classId, methodVisitor, name, desc, allowMethodCoverage, allowLineCoverage);
	}
}
