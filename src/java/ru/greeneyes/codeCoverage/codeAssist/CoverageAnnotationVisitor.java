package ru.greeneyes.codeCoverage.codeAssist;

import org.objectweb.asm.AnnotationVisitor;

/**
 * @author ivanalx
 * @date 26.01.2009 15:48:51
 */
public class CoverageAnnotationVisitor implements AnnotationVisitor { // TODO do we need it?
	public void visit(String name, Object value) {

	}

	public void visitEnum(String name, String desc, String value) {

	}

	public AnnotationVisitor visitAnnotation(String name, String desc) {
		return null;
	}

	public AnnotationVisitor visitArray(String name) {
		return null;
	}

	public void visitEnd() {

	}
}
