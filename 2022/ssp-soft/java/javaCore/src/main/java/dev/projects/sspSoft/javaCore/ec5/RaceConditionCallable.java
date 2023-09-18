package dev.projects.sspSoft.javaCore.ec5;

import java.util.concurrent.Callable;

public class RaceConditionCallable implements Callable<Integer> {
    private int count = 0;

    @Override
    public Integer call() throws Exception {
        return getNext();
    }

    private int getNext() {
        return count++;
    }
}
