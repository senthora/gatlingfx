package com.senthora.gatlingfx.simulation.internal;

import com.senthora.gatlingfx.simulation.api.event.SimulationEvent;

import java.util.Objects;

/**
 * Synchronous dispatcher for simulation events.
 */
public final class SimulationEventPublisher {

    private SimulationEventPublisher() {}

    /**
     * Publishes simulation event.
     * <p>
     * <strong>API Note:</strong>
     * Listener failures are propagated to the caller.
     *
     * @param event simulation event
     *
     * @throws NullPointerException if {@code event} is null
     */
    public static void publish(SimulationEvent event) {
        Objects.requireNonNull(event, "event must not be null");

        for (var listener : SimulationEventListenerRegistry.listeners()) {
            listener.onEvent(event);
        }
    }
}
