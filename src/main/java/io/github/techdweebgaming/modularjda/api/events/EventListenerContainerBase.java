package io.github.techdweebgaming.modularjda.api.events;

import net.dv8tion.jda.api.events.GenericEvent;

public abstract class EventListenerContainerBase<T extends GenericEvent> {

    abstract Class<T> getEventLiteral();

    abstract void onEvent(T event);

    protected void onEventRaw(GenericEvent rawEvent) {
        T event = getEventLiteral().cast(rawEvent);
        onEvent(getEventLiteral().cast(rawEvent));
    }

}
