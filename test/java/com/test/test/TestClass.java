package com.test.test;

/**
 * @author ivanalx
 * @date 26.01.2009 19:38:14
 */
public class TestClass {
	public static void main(String[] args) {
		long nanoTime = System.nanoTime();
		TimeTestDemo timeTestDemo = new TimeTestDemo();
		for (int j =0; j < 20; j++) {
			String s = timeTestDemo.recalcSomeThingLong(1000);
			System.out.println(s.length());
		}
		System.err.println("Time to recalc:" + (System.nanoTime() - nanoTime));
	}
}