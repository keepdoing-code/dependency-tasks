package ru.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service implements Runnable {
    private Logger LOGGER = LoggerFactory.getLogger("Service");
    private final String name;

    public Service(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void run() {
        long time = System.currentTimeMillis();
        try {
            LOGGER.info("{} started at - {}", name, time);
            Thread.sleep(1000);
            long nextTime = System.currentTimeMillis();
            LOGGER.info("{} stopped at - {}  used time - {}", name, nextTime, nextTime - time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
