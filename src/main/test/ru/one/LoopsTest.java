package ru.one;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoopsTest {
    private static final int MAX_THREAD_COUNT = 5;
    private List<Service> services = new ArrayList<>();
    private ServiceExecutor executor;

    private Service a;
    private Service b;
    private Service c;
    private Service d;
    private Service e;
    private Service f;
    private Service g;

    @Before
    public void setup() {
        /*
         * создали таски
         */
        a = Service.getInstance("A", new ArrayList<>());
        b = Service.getInstance("B", new ArrayList<>());
        c = Service.getInstance("C", new ArrayList<>());
        d = Service.getInstance("D", new ArrayList<>());
        e = Service.getInstance("E", new ArrayList<>());
        f = Service.getInstance("F", new ArrayList<>());
        g = Service.getInstance("G", new ArrayList<>());

        /*
         * связали их с циклической зависимостью
         * A->B->F->G->E->A
         * A->B->E->A
         */
        a.addDependency(b);
        a.addDependency(c);
        b.addDependency(d);
        b.addDependency(e);
        b.addDependency(f);
        e.addDependency(a); // loop dependency
        f.addDependency(g);
        g.addDependency(e);

        services.add(a);
        services.add(b);
        services.add(c);
        services.add(d);
        services.add(e);
        services.add(f);
        services.add(g);

        executor = new ServiceExecutor(services, MAX_THREAD_COUNT);
    }

    @Test(expected = RuntimeException.class)
    public void checkLoops() {
        executor.check();
    }

    @Test(expected = RuntimeException.class)
    public void executeWithLoop() throws ExecutionException, InterruptedException {
        executor.execute();
    }

    @Test
    public void checkWithoutLoops() {
        e.removeDependency(a);
        executor = new ServiceExecutor(services, MAX_THREAD_COUNT);
        executor.check();
    }

    @Test
    public void execute() throws ExecutionException, InterruptedException {
        e.removeDependency(a);
        executor = new ServiceExecutor(services, MAX_THREAD_COUNT);
        executor.execute();
    }
}
