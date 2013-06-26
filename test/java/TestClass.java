import java.util.List;
import java.util.ArrayList;

import com.test.test.TimeTestDemo;
import com.test.Calculator;

/**
 * @author ivanalx
 * @date 26.01.2009 19:37:53
 */
public class TestClass {

	private static class Result {
		private long instrumentTime;
		private long unInstrumentTime;

		private Result(long instrumentTime, long unInstrumentTime) {
			this.instrumentTime = instrumentTime;
			this.unInstrumentTime = unInstrumentTime;
		}

		public long getInstrumentTime() {
			return instrumentTime;
		}

		public long getUnInstrumentTime() {
			return unInstrumentTime;
		}

		@Override
		public String toString() {
			return "{" + instrumentTime + "," + unInstrumentTime + '}';
		}
	}


	public static Result calcResultInOneThread(Calculator instrumentTime, Calculator uninstrumentTime) {
		long start = System.nanoTime();
		for (int i = 0; i < 20; i++) {
			instrumentTime.recalcSomeThingLong(1000);
		}
		long stopInstrument = System.nanoTime() - start;
		start = System.nanoTime();
		for (int i = 0; i < 20; i++) {
			uninstrumentTime.recalcSomeThingLong(1000);
		}
		long stopUninstrument = System.nanoTime() - start;
		return new Result(stopInstrument, stopUninstrument);
	}


	private static long testThreaded(Calculator calculator) {
		long start = System.nanoTime();
		List<Thread> instrumentThread = new ArrayList<Thread>(40);
		for (int i = 0; i < 40; i++) {
			TestThreadRunner testThreadRunner = new TestThreadRunner(20, 1000, calculator);
			instrumentThread.add(testThreadRunner);
			testThreadRunner.start();
		}
		for (Thread anInstrumentThread : instrumentThread) {
			if (anInstrumentThread.isAlive()) {
				try {
					anInstrumentThread.join();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return System.nanoTime() - start;
	}

	public static Result testResultInMultipleThread(Calculator instrumentTime, Calculator uninstrumentTime) {
		return new Result(testThreaded(instrumentTime), testThreaded(uninstrumentTime));
	}

	public static void main(String[] args) {
		TimeTestDemo timeTestDemo = new TimeTestDemo();
		TimeTestDemo2 timeTestDemo2 = new TimeTestDemo2();
		List<Result> results = new ArrayList<Result>();
		for (int i = 0; i < 1; i++) {
			results.add(testResultInMultipleThread(timeTestDemo, timeTestDemo2));
			System.out.println("Stage: " + i);
		}
		double averageLossTime = 0;
		int number = 0;
		for (Result result : results) {
			number++;
			averageLossTime = (averageLossTime * (number - 1) +
					((double) (result.getInstrumentTime() - result.getUnInstrumentTime())) / result.getUnInstrumentTime()) / number;
		}
		System.out.println("Average loss: " + averageLossTime * 100);
		System.out.println(results);
	}
}
