package io.github.techdweebgaming.modularjda.api.events;

import net.dv8tion.jda.api.events.GenericEvent;

import java.util.function.Consumer;

public class EventListenerContainerConsumer<T extends GenericEvent> extends EventListenerContainerBase<T> {

    private Consumer<T> consumer;
    private Class<T> clazz;

    public EventListenerContainerConsumer(Consumer<T> consumer, Class<T> clazz) {
        this.consumer = consumer;
        this.clazz = clazz;
    }

    public Class<T> getEventLiteral() {
        return clazz;
    }

    public void onEvent(T event) {
        consumer.accept(event);
    }

}
