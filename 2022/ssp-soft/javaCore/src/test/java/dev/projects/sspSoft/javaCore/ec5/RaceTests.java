package dev.projects.sspSoft.javaCore.ec5;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class RaceTests {

    private final ThreadPoolExecutor executor = (ThreadPoolExecutor)(Executors.newFixedThreadPool(3));

    @Test
    public void raceConditionTest() throws ExecutionException, InterruptedException {
        RaceConditionCallable raceCondition = new RaceConditionCallable();

        Future<Integer> futureOne = executor.submit(raceCondition);
        Future<Integer> futureTwo = executor.submit(raceCondition);
        Future<Integer> futureThree = executor.submit(raceCondition);

        futureOne.get();
        futureTwo.get();
        futureThree.get();

        executor.shutdown();
    }
}
