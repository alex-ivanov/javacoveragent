package com.coveragent.codeAssist;

import com.coveragent.calculator.CoverageCalculator;
import com.coveragent.calculator.threadLocal.ThreadLocalInterceptor;
import com.coveragent.utils.ClassUtils;
import gnu.trove.set.TLongSet;
import org.junit.Test;

import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.Collections;

import static com.coveragent.utils.ClassUtils.classAsBytes;
import static org.junit.Assert.assertEquals;

/**
 * User: dima
 * Date: Feb 8, 2009
 * Time: 2:33:05 PM
 */
public class ClassTransformerTest {
	private static final String SAMPLE_CLASS_NAME = SampleClass.class.getName();
	private static final ClassLoader DUMMY_LOADER = (ClassLoader) null;
	private static final Class DUMMY_CLASS = (Class) null;
	private static final ProtectionDomain DUMMY_DOMAIN = (ProtectionDomain) null;

	@Test
	public void shouldInstrumentClasses() throws Exception {
		ThreadLocalInterceptor statisticsHolder = CoverageCalculator.interceptor;
		statisticsHolder.reset();
		ClassTransformer transformer = new ClassTransformer(statisticsHolder);
		transformer.setIncludePrefix(Collections.singleton(SAMPLE_CLASS_NAME));

		// exercise
		byte[] bytes = transformer.transform(DUMMY_LOADER, SAMPLE_CLASS_NAME, DUMMY_CLASS, DUMMY_DOMAIN,
				classAsBytes(SAMPLE_CLASS_NAME));
		Class aClass = ClassUtils.createClass(bytes);
		Object sample = aClass.newInstance();
		Method method = aClass.getMethod("method");
		method.invoke(sample);

		statisticsHolder.grabAllStatistics();
		TLongSet set = statisticsHolder.visitedMethodSet();
		//constructor and method
		assertEquals(2, set.size());
		assertEquals("method()V", statisticsHolder.methodName(2));
		assertEquals(SAMPLE_CLASS_NAME, statisticsHolder.className(2));
		assertEquals("method()V", statisticsHolder.methodName(2));
		assertEquals(SAMPLE_CLASS_NAME, statisticsHolder.className(1));
		statisticsHolder.reset();
	}
}
