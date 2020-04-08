package io.github.techdweebgaming.modularjda.internal.services.threading;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ConsumerOverCollectionService<T> extends MultithreadedTasksService {

    public ConsumerOverCollectionService(int numThreads, Consumer<T> executable, Collection<T> collection) {
        super(numThreads, collection.stream().map(t -> new RunnableAdapter(executable, t)).collect(Collectors.toList()));
    }
}
