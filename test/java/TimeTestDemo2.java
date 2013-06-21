import java.math.BigInteger;

import com.test.Calculator;

/**
 * @author ivanalx
 * @date 27.01.2009 13:47:24
 */
public class TimeTestDemo2 implements Calculator {
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
