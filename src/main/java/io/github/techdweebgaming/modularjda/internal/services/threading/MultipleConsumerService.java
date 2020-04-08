package io.github.techdweebgaming.modularjda.internal.services.threading;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MultipleConsumerService<T> extends MultithreadedTasksService {

    public MultipleConsumerService(int numThreads, Collection<Consumer<T>> consumers, T argument) {
        super(numThreads, consumers.stream().map(t -> new RunnableAdapter(t, argument)).collect(Collectors.toList()));
    }
}
