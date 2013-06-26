package com.coveragent.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;

/**
 * User: dima
 * Date: Feb 8, 2009
 * Time: 4:26:15 PM
 */
public class ClassUtils {
	private ClassUtils() {
	}

	public static byte[] classAsBytes(final String className) throws ClassNotFoundException {
		try {
			URL resource = ClassUtils.class.getClassLoader().getResource(convertClassNameToFileName(className));
			BufferedInputStream stream = new BufferedInputStream(resource.openStream());
			byte[] result = new byte[resource.openConnection().getContentLength()];

			int i;
			int counter = 0;
			while ((i = stream.read()) != -1) {
				result[counter] = (byte) i;
				counter++;
			}

			return result;
		} catch (IOException e) {
			throw new ClassNotFoundException("", e);
		}
	}

	private static String convertClassNameToFileName(final String className) {
		return className.replace(".", "/") + ".class";
	}

	public static Class createClass(byte[] bytes) throws IllegalClassFormatException {
		return MyClassLoader.instance.createClass(bytes);
	}

	public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InterruptedException {
		byte[] bytes = classAsBytes("java.lang.Thread");
		Field field = Unsafe.class.getDeclaredField("theUnsafe");
		field.setAccessible(true);
		Unsafe unsafe = (Unsafe) field.get(null);

		unsafe.defineClass("java/lang/Thread", bytes, 0, bytes.length, null, null);

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello from " + Thread.currentThread().getName());
			}
		});
		t.start();
		Thread.sleep(1000);
	}

	private static final class MyClassLoader extends ClassLoader {
		public static final MyClassLoader instance = new MyClassLoader();

		public Class<?> createClass(byte[] bytes) throws IllegalClassFormatException {
			return defineClass(null, bytes, 0, bytes.length);
		}
	}
}
