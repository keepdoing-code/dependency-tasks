package ru.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Service implements Runnable{
    private static final Logger LOGGER = LoggerFactory.getLogger(Service.class);
    private static volatile Service service;
    private final String name;
    private final List<Service> dependencies;
    private boolean started;

    private Service(String name, List<Service> dependencies) {
        this.name = name;
        this.dependencies = dependencies;
        this.started = false;
    }

    public static Service getInstance(String name, List<Service> dependencies) {
        if (service == null) {
            synchronized (Service.class) {
                if (service == null) {
                    return new Service(name, dependencies);
                }
            }
        }
        return service;
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
        if (!started){
            LOGGER.info("{} start", name);
            try {
                Thread.sleep(10);
                LOGGER.info("{} is working", name);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("{} end", name);
            started = true;
        }
    }
}
