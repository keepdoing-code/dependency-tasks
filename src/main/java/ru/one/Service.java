package ru.one;

import java.util.List;

public class Service implements Runnable{
    private static volatile Service service;
    private final String name;
    private final List<Service> dependencies;

    private Service(String name, List<Service> dependencies) {
        this.name = name;
        this.dependencies = dependencies;
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

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        System.out.println(name + " is Started");
    }
}
