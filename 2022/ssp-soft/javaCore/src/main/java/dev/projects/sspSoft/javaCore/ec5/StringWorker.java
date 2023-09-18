package dev.projects.sspSoft.javaCore.ec5;

import java.util.concurrent.ArrayBlockingQueue;

public class StringWorker implements Runnable {
    private final Object mutex;
    private final StringConsumer stringConsumer;
    private final ArrayBlockingQueue<String> queue;

    public StringWorker(ArrayBlockingQueue<String> queue, Object mutex, StringConsumer consumer) {
        this.queue = queue;
        this.stringConsumer = consumer;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        try {
            while(true) {
                String value = queue.take();

                if (value.equals("EXIT")) {
                    break;
                }

                synchronized (stringConsumer) {
                    stringConsumer.join(value);
                }

                synchronized (mutex) {
                    mutex.notify();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
