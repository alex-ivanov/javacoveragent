package com.test.test;

import java.math.BigInteger;

import com.test.Calculator;

/**
 * @author ivanalx
 * @date 27.01.2009 9:36:34
 */
public class TimeTestDemo implements Calculator {
	public void testNotInputed() {
		System.out.println("AAAAAAAAAAAAAAAAAAAA");
	}

	public String recalcSomeThingLong(int size) {
		BigInteger fib = BigInteger.valueOf(0);
		BigInteger fib1 = BigInteger.valueOf(1);
		for (int i =0; i < size; i++) {
			String tmpStr = fib1.toString();
			fib1 = fib.add(fib1);
			fib = new BigInteger(tmpStr);
		}
		return fib1.toString();
	}
}
