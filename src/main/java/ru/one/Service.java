package ru.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Service.class);
    private static Map<String, Service> services = new HashMap<>();
    private final String name;
    private final List<Service> dependencies;
    volatile private boolean started;

    private Service(String name, List<Service> dependencies) {
        this.name = name;
        this.dependencies = dependencies;
        this.started = false;
    }

    public static Service getInstance(String name, List<Service> dependencies) {
        if (!services.containsKey(name)) {
            synchronized (Service.class) {
                if (!services.containsKey(name)) {
                    Service service = new Service(name, dependencies);
                    services.put(name, service);
                    return service;
                }
            }
        }
        return services.get(name);
    }

    public List<Service> getDependencies() {
        return dependencies;
    }

    public void addDependency(Service service) {
        dependencies.add(service);
    }

    public void removeDependency(Service service) {
        dependencies.remove(service);
    }

    @Override
    public void run() {
        synchronized (this) {
            if (!started) {
                LOGGER.info("{} start", name);
                started = true;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("{} end", name);
            }
        }
    }
}
