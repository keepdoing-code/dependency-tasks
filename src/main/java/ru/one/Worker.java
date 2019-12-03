package ru.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Worker {

    private Logger LOGGER = LoggerFactory.getLogger("Service");

    private ExecutorService executorService;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        run(new LinkedList<>(), 2, new HashMap<>());
        TimeUnit.SECONDS.sleep(2);
    }

    public static void run(List<Service> list, int maxThreadCount, Map<String, List<String>> relations) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                System.out.println("Я буду работать в отдельном потоке, а не в главном.");
            }
        });
        future.get();
    }
}
