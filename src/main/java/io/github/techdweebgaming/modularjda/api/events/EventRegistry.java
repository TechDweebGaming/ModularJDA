package io.github.techdweebgaming.modularjda.api.events;

import io.github.techdweebgaming.modularjda.internal.services.threading.MultipleConsumerService;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EventRegistry implements EventListener {

    private static class EventRegistryLoader {
        private static final EventRegistry INSTANCE = new EventRegistry();
    }

    public static EventRegistry getInstance() {
        return EventRegistryLoader.INSTANCE;
    }

    private List<EventListenerContainerBase<?>> listeners;

    private EventRegistry() {
        listeners = new ArrayList<>();
    }

    @Override
    public void onEvent(GenericEvent event) {
            List<Consumer<GenericEvent>> consumers = listeners.stream()
                    .filter(listener -> listener.getEventLiteral().isInstance(event))
                    .map(listener -> getConsumer(listener))
                    .collect(Collectors.toList());
            try(MultipleConsumerService<EventListenerContainerBase<GenericEvent>> service = new MultipleConsumerService(12, consumers, event)) {
                while(!service.complete());
            }
    }

    public void registerListener(EventListenerContainerBase listener) {
        listeners.add(listener);
    }

    private static Consumer<GenericEvent> getConsumer(EventListenerContainerBase container) {
        return (event) -> container.onEventRaw(event);
    }
}
