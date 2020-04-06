package io.github.techdweebgaming.modularjda.internal.services;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

public class MultithreadedTasksService<T> implements Closeable {

    private ThreadPoolExecutor executor;
    private List<Future<?>> futures;

    public MultithreadedTasksService(int numThreads, Consumer<T> executable, Collection<T> collection) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
        futures = new ArrayList<>();

        for(T t : collection) {
            Future<?> future = executor.submit(() -> executable.accept(t));
            futures.add(future);
        }
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
