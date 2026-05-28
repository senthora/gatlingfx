package com.senthora.gatlingfx.simulation.internal;

import com.senthora.gatlingfx.simulation.api.event.SimulationEventListener;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Registry for simulation event listeners.
 */
public final class SimulationEventListenerRegistry {

    private static final List<SimulationEventListener> LISTENERS = new CopyOnWriteArrayList<>();

    private SimulationEventListenerRegistry() {}

    /**
     * Registers simulation event listener.
     *
     * @param listener simulation event listener
     *
     * @throws NullPointerException if {@code listener} is null
     */
    public static void register(SimulationEventListener listener) {
        Objects.requireNonNull(listener, "listener must not be null");
        LISTENERS.add(listener);
    }

    /**
     * Returns an immutable view of
     * registered simulation event listeners.
     */
    static List<SimulationEventListener> listeners() {
        return Collections.unmodifiableList(LISTENERS);
    }

    /**
     * Removes all registered simulation event listeners.
     */
    static void clear() {
        LISTENERS.clear();
    }
}
