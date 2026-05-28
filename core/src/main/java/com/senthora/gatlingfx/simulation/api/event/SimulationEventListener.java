package com.senthora.gatlingfx.simulation.api.event;

/**
 * Listener for published simulation events.
 */
@FunctionalInterface
public interface SimulationEventListener {

    /**
     * Handles published simulation event.
     *
     * @param event published simulation event
     *
     * @throws NullPointerException if {@code event} is null
     */
    void onEvent(SimulationEvent event);
}
