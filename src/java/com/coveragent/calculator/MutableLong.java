package com.coveragent.calculator;

/**
 * author: alex
 * date  : 6/24/13
 */
public class MutableLong {
	private long amount;

	public void increment() {
		amount++;
	}

	public long currentAmount() {
		return amount;
	}
}
