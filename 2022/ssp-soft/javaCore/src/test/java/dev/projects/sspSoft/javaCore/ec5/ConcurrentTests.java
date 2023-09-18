package dev.projects.sspSoft.javaCore.ec5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentTests {
    private final Object mutex = new Object();
    private final StringConsumer strConsumer = new StringConsumer();

    private final ArrayBlockingQueue<String> queueA = new ArrayBlockingQueue<>(128);
    private final ArrayBlockingQueue<String> queueB = new ArrayBlockingQueue<>(128);
    private final ArrayBlockingQueue<String> queueC = new ArrayBlockingQueue<>(128);

    private final ThreadPoolExecutor executor = (ThreadPoolExecutor)(Executors.newFixedThreadPool(3));

    @Test
    public void abcTest() throws InterruptedException {
        StringWorker workerA = new StringWorker(queueA, mutex, strConsumer);
        StringWorker workerB = new StringWorker(queueB, mutex, strConsumer);
        StringWorker workerC = new StringWorker(queueC, mutex, strConsumer);

        executor.execute(workerA);
        executor.execute(workerB);
        executor.execute(workerC);

        for (int strBlockIndex = 0; strBlockIndex < 5; strBlockIndex++) {
            queueA.put("A");

            synchronized (mutex) {
                mutex.wait(1000);
            }

            queueB.put("B");

            synchronized (mutex) {
                mutex.wait(1000);
            }

            queueC.put("C");

            synchronized (mutex) {
                mutex.wait(1000);
            }
        }

        queueA.put("EXIT");
        queueB.put("EXIT");
        queueC.put("EXIT");

        executor.shutdown();

        Assertions.assertEquals("ABCABCABCABCABC", strConsumer.toString());
    }
}
