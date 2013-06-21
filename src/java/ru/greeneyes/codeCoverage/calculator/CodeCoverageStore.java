package ru.greeneyes.codeCoverage.calculator;

import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Method;

/**
 * @author ivanalx
 * @date 26.01.2009 14:41:04
 */
public class CodeCoverageStore {
	public static final String CODE_COVERAGE_CALCULATOR_CLASS_NAME;
	public static final String CODE_COVERAGE_CALCULATOR_CODE_METHOD_NAME;
	public static final String CODE_COVERAGE_CALCULATOR_CODE_METHOD_DESC;

	public static final String CODE_COVERAGE_CALCULATOR_METHOD_METHOD_NAME;

	public static final String CODE_COVERAGE_CALCULATOR_METHOD_METHOD_DESC;

	static {
		Method addCalc = new Method("visitLine", Type.VOID_TYPE, new Type[]{Type.INT_TYPE, Type.INT_TYPE});
		Method addCalcMethod = new Method("visitMethod", Type.VOID_TYPE, new Type[]{Type.INT_TYPE, Type.INT_TYPE});
		CODE_COVERAGE_CALCULATOR_CODE_METHOD_NAME = addCalc.getName();
		CODE_COVERAGE_CALCULATOR_CODE_METHOD_DESC = addCalc.getDescriptor();
		CODE_COVERAGE_CALCULATOR_CLASS_NAME = CodeCoverageStore.class.getName().replace('.', '/');

		CODE_COVERAGE_CALCULATOR_METHOD_METHOD_NAME = addCalcMethod.getName();
		CODE_COVERAGE_CALCULATOR_METHOD_METHOD_DESC = addCalcMethod.getDescriptor();
	}

	private static InvokeQueue invokeQueue;
	private static InvokeStatistics invokeStatistics;

    public static void init(InvokeQueue invokeQueue, InvokeStatistics invokeStatistics) {
        CodeCoverageStore.invokeQueue = invokeQueue;
        CodeCoverageStore.invokeStatistics = invokeStatistics;
    }
    
	private CodeCoverageStore() { }

	public static void visitLine(int classId, int codeLine) {
		invokeQueue.addCodelineInvoke(classId, codeLine);
	}

	public static void visitMethod(int classId, int methodId) {
		invokeQueue.addMethodInvoke(classId, methodId);
	}

	public static int registerClass(String className) {
		return invokeStatistics.registerClass(className);
	}

	public static int registerMethod(int classId, String methodName, String methodDesc) {
		return invokeStatistics.registerMethod(classId, methodName, methodDesc);
	}

	public static InvokeStatistics getInvokeStatistics() {
		return invokeStatistics;
	}

	public static InvokeQueue getInvokeQueue() {
		return invokeQueue;
	}
}
