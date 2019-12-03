package ru.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Service implements Runnable {
    private Logger LOGGER = LoggerFactory.getLogger("Service");
    private final String name;
    private final int workTime;

    public Service(String name, int workTime) {
        this.name = name;
        this.workTime = workTime;
    }

    public String getName() {
        return name;
    }

    public void run() {
        try {
            long inTime = System.currentTimeMillis();
            LOGGER.info("{} /\\", name);
            TimeUnit.SECONDS.sleep(workTime);
            long outTime = System.currentTimeMillis();
            LOGGER.info("{} \\/  time = {}s", name, (outTime-inTime)/1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
