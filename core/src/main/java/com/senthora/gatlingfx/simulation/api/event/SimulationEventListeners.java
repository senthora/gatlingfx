package com.senthora.gatlingfx.simulation.api.event;

import com.senthora.gatlingfx.simulation.internal.SimulationEventListenerRegistry;

/**
 * Entry point for registering simulation event listeners.
 */
public interface SimulationEventListeners {

    /**
     * Registers simulation event listener.
     *
     * @param listener simulation event listener
     *
     * @throws NullPointerException if {@code listener} is null
     */
    static void register(SimulationEventListener listener) {
        SimulationEventListenerRegistry.register(listener);
    }
}
