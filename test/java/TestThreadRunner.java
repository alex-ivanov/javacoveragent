import com.test.Calculator;

/**
 * @author ivanalx
 * @date 28.01.2009 17:43:13
 */
public class TestThreadRunner extends Thread {
	private int invocationCount = 100;
	private int calcCount;
	private Calculator calculator;
	public TestThreadRunner(int invocationCount, int calcCount, Calculator calculator) {
		this.invocationCount = invocationCount;
		this.calcCount = calcCount;
		this.calculator = calculator;
	}

	@Override
	public void run() {
		while (invocationCount-- > 0) {
			calculator.recalcSomeThingLong(calcCount);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
