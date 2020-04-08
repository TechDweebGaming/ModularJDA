package io.github.techdweebgaming.modularjda.internal.services.threading;

import java.util.function.Consumer;

public class RunnableAdapter<T> implements Runnable {

    private Consumer<T> consumer;
    private T value;

    public RunnableAdapter(Consumer<T> consumer, T value) {
        this.consumer = consumer;
        this.value = value;
    }

    @Override
    public void run() {
        consumer.accept(value);
    }

}
