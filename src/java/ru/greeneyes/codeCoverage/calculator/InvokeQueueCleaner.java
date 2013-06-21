package ru.greeneyes.codeCoverage.calculator;

/**
 * @author ivanalx
 * @date 28.01.2009 14:39:17
 */
public class InvokeQueueCleaner implements Runnable {
	private InvokeQueue queue;
	private InvokeStatistics invokeStatistics;
	private int amountOfCleaner;

	public InvokeQueueCleaner(InvokeQueue queue, InvokeStatistics invokeStatistics, int amount) {
		this.queue = queue;
		this.invokeStatistics = invokeStatistics;
		this.amountOfCleaner = amount;
	}

	public void run() {
		while (!Thread.interrupted()) {
			try {
				int amount = queue.size() / amountOfCleaner + 10;
				for (int i = 0; i < amount; i++) {
					InvokeEntry s = queue.poll();
					if (s != null) {
						switch (s.getType()) {
						case LINE: {
							invokeStatistics.visitLine(s.getClassId(), s.getCodeId());
							break;
						}
						case METHOD: {
							invokeStatistics.visitMethod(s.getClassId(), s.getCodeId());
							break;
						}
						}
					}
				}
				Thread.sleep(1);
			} catch (Exception e) {
				//ignore
			}
		}
	}

	public void setAmountOfCleaner(int amountOfCleaner) {
		this.amountOfCleaner = amountOfCleaner;
	}
}
