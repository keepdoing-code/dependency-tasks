package ru.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Worker {

    private static Logger LOGGER = LoggerFactory.getLogger("Service");

    private ExecutorService executorService;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LOGGER.info("Thread started");
        new Worker().run(new LinkedList<>(), 2, new HashMap<>());
        LOGGER.info("App end");
    }

    public void run(List<Service> list, int maxThreadCount, Map<String, List<String>> relations) throws ExecutionException, InterruptedException {
        executorService = Executors.newFixedThreadPool(maxThreadCount);
        CompletableFuture future = CompletableFuture.runAsync(new Service("-1-", 3), executorService);
        CompletableFuture future1 = CompletableFuture.runAsync(new Service("-2-", 4), executorService);
        CompletableFuture future2 = CompletableFuture.runAsync(new Service("-3-", 4), executorService).thenRun(new Service("-4-", 4));
        CompletableFuture futureAll = CompletableFuture.allOf(future, future1, future2);
        LOGGER.info("End init threads\nWaiting for thread end");
        futureAll.get();
        LOGGER.info("Threads end");
        executorService.shutdown();
    }
}
