package io.github.techdweebgaming.modularjda.internal.services.threading;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class MultithreadedTasksService implements Closeable {

    private ThreadPoolExecutor executor;
    private List<Future<?>> futures;

    public MultithreadedTasksService(int numThreads, Collection<Runnable> tasks) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
        futures = new ArrayList<>();

        for(Runnable task : tasks) futures.add(executor.submit(task));
    }

    public boolean complete() {
        for(Future<?> future : futures) {
            if(!future.isDone()) return false;
        }
        return true;
    }

    @Override
    public void close() {
        executor.shutdownNow();
    }
}
