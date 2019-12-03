package ru.one;

import java.util.List;

public class ServiceExecutor {
    private int weight;
    private int maxWeight;
    private final List<Service> services;

    public ServiceExecutor(List<Service> services) {
        this.services = services;
        maxWeight = services.size() * services.size();
    }

    public void execute(){
        check();
        //todo add executor
    }

    public void check() {
        check(services);
    }

    private void check(List<Service> services) {
        if (services == null || services.isEmpty()) {
            return;
        }
        if (weight > maxWeight) {
            throw new RuntimeException("loop found");
        }
        for (Service service : services) {
            List<Service> dependencies = service.getDependencies();
            weight += dependencies.size();
            check(dependencies);
        }
    }
}
