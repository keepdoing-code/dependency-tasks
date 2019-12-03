package ru.one;

import java.util.Collections;

public class Worker {
    public static void main(String[] args) {
        ServiceExecutor executor = new ServiceExecutor(Collections.emptyList());
        executor.execute();
    }
}
