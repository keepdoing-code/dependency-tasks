package ru.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExecutor.class);
    private int weight;
    private int maxWeight;
    private final List<Service> services;
    private final ExecutorService executorService;

    public ServiceExecutor(List<Service> services, int maxThreadCount) {
        this.services = services;
        maxWeight = services.size() * services.size();
        executorService = Executors.newFixedThreadPool(maxThreadCount);
    }

    public void check() {
        check(services);
    }

    public void execute() throws ExecutionException, InterruptedException {
        check(services);
        execute(services);
    }

    private void execute(List<Service> services) throws ExecutionException, InterruptedException {
        if (services == null || services.isEmpty()) {
            return;
        }
        for (Service service : services) {
            List<Service> dependencies = service.getDependencies();
            execute(dependencies);
            CompletableFuture<Void> future = CompletableFuture.runAsync(service, executorService);
            future.get();
        }
    }

    private void check(List<Service> services) {
        if (services == null || services.isEmpty()) {
            return;
        }
        if (weight > maxWeight) {
            LOGGER.error("loop found");
            throw new RuntimeException("loop found");
        }
        for (Service service : services) {
            List<Service> dependencies = service.getDependencies();
            weight += dependencies.size();
            check(dependencies);
        }
    }
}
