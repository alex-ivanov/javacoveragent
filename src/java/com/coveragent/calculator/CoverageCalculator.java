package com.coveragent.calculator;

import com.coveragent.calculator.threadLocal.ThreadLocalInterceptor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Method;

/**
 * author: alex
 * date  : 6/21/13
 */
public class CoverageCalculator {
	public static final String CODE_COVERAGE_CALCULATOR_CLASS_NAME;

	public static final String CODE_COVERAGE_CALCULATOR_METHOD_METHOD_NAME;

	public static final String CODE_COVERAGE_CALCULATOR_METHOD_METHOD_DESC;

	static {
		Method addCalcMethod = new Method("visitMethod", Type.VOID_TYPE, new Type[]{Type.LONG_TYPE});
		CODE_COVERAGE_CALCULATOR_CLASS_NAME = CoverageCalculator.class.getName().replace('.', '/');

		CODE_COVERAGE_CALCULATOR_METHOD_METHOD_NAME = addCalcMethod.getName();
		CODE_COVERAGE_CALCULATOR_METHOD_METHOD_DESC = addCalcMethod.getDescriptor();
	}


	private static final Interceptor interceptor = new ThreadLocalInterceptor();

	/**
	 * The only one entry point for all generated code.
	 * @param methodId unique id of the method, generated on class instrumental stage
	 */
	public static void visitMethod(long methodId) {
		interceptor.methodEntry(methodId);
	}
}
