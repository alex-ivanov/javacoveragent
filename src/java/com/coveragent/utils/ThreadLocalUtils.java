package com.coveragent.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author: alex
 * date  : 6/25/13
 */
public class ThreadLocalUtils {
	public static <T> List<T> allExistedThreadLocalValues(ThreadLocal<T> variable) {
		Map<Thread,StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
		List<T> result = new ArrayList<T>();
		Set<Thread> allThreads = allStackTraces.keySet();
		for (Thread thread : allThreads) {
			T v = getValue(variable, thread);
			if (v != null)
				result.add(v);
		}
		return result;
	}

	public static <T> T getValue(ThreadLocal<T> variable, Thread key) {
		try {
			Field field = Thread.class.getDeclaredField("threadLocals");
			field.setAccessible(true);
			Object entryMap = field.get(key);
			if (entryMap == null)
				return null;
			Method getEntryMethod = entryMap.getClass().getDeclaredMethod("getEntry", ThreadLocal.class);
			getEntryMethod.setAccessible(true);
			Object entry = getEntryMethod.invoke(entryMap, variable);
			if (entry == null)
				return null;
			Field valueField = entry.getClass().getDeclaredField("value");
			valueField.setAccessible(true);

			return (T) valueField.get(entry);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
