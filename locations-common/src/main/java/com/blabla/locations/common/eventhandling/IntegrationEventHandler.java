package com.blabla.locations.common.eventhandling;

public interface IntegrationEventHandler<T> {
    void handle(T event);
}
