package com.coveragent.codeAssist;

import java.security.ProtectionDomain;

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
/*
	@Test
	public void shouldInstrumentClasses() throws Exception {
		// setup
		InvokeStatistics invokeStatistics = new InvokeStatistics();
		InvokeQueue invokeQueue = new InvokeQueue();
		CodeCoverageStore.init(invokeQueue, invokeStatistics);
		ClassTransformer transformer = new ClassTransformer(statisticsHolder);
		transformer.setIncludePrefix(Collections.singleton(SAMPLE_CLASS_NAME));

		// exercise
		byte[] bytes = transformer.transform(DUMMY_LOADER, SAMPLE_CLASS_NAME, DUMMY_CLASS, DUMMY_DOMAIN, classAsBytes(SAMPLE_CLASS_NAME));
		Class aClass = ClassUtils.createClass(bytes);
		Object sample = aClass.newInstance();
		Method method = aClass.getMethod("method");
		method.invoke(sample);

		// verify
		InvokeQueue queue = CodeCoverageStore.getInvokeQueue();
		assertEquals(2, queue.size());

		InvokeEntry invokeEntry = queue.poll();
		assertEquals(InvokeType.METHOD, invokeEntry.getType());
		assertEquals(0, invokeEntry.getClassId());
		assertEquals(0, invokeEntry.getCodeId());

		invokeEntry = queue.poll();
		assertEquals(InvokeType.METHOD, invokeEntry.getType());
		assertEquals(0, invokeEntry.getClassId());
		assertEquals(1, invokeEntry.getCodeId());
	}
	*/
}
