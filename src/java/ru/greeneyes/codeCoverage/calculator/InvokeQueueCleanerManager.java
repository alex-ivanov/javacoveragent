package ru.greeneyes.codeCoverage.calculator;

/**
 * @author ivanalx
 * @date 28.01.2009 15:01:18
 */
public class InvokeQueueCleanerManager {
    public static void start(InvokeQueue queue, InvokeStatistics statistics, int cleanerAmount) {
        for (int i = 0; i < cleanerAmount; i++) {
            InvokeQueueCleaner cleaner = new InvokeQueueCleaner(queue, statistics, cleanerAmount);
            Thread t = new Thread(cleaner);
            t.setDaemon(true);
            t.setName("CodeCoverageQueueCleaner");
            t.start();
        }
    }

    private InvokeQueueCleanerManager() { }
}
