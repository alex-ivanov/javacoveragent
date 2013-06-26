package com.coveragent.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

/**
 * author: alex
 * date  : 6/25/13
 */
@SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
public class ThreadLocalUtilsTest {
	@Test
	public void testAllExistedThreadLocalValues() throws Exception {
		final ThreadLocal<Integer> locals = new ThreadLocal<Integer>();
		final Object lock = new Object();
		Set<Integer> allValues = new HashSet<Integer>();
		List<Thread> threads = new ArrayList<Thread>(10);
		for (int i = 0; i < 10; i++) {
			allValues.add(i);
			final int finalI = i;
			threads.add(new Thread(new Runnable() {
				@Override
				public void run() {
					locals.set(finalI);
					synchronized (lock) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}));
		}
		for (Thread thread : threads) {
			thread.start();
		}
		//await to all threads startup
		Thread.sleep(100);

		List<Integer> result;
		synchronized (lock) {
			//all threads started
			result = ThreadLocalUtils.allExistedThreadLocalValues(locals);
			lock.notifyAll();
		}
		assertEquals(allValues.size(), result.size());
		assertEquals(allValues, new HashSet<Integer>(result));
	}

	@Test
	public void testGetValue() throws Exception {
		ThreadLocal<String> local = new ThreadLocal<String>();
		local.set("Hello world!");
		assertEquals("Hello world!", ThreadLocalUtils.getValue(local, Thread.currentThread()));
	}
}
