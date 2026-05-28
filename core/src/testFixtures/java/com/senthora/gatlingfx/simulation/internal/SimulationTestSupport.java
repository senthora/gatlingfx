package com.senthora.gatlingfx.simulation.internal;

import com.senthora.gatlingfx.simulation.api.event.SimulationEvent;
import com.senthora.gatlingfx.simulation.api.event.SimulationEventListener;
import com.senthora.gatlingfx.simulation.api.event.SimulationEventListeners;

import java.util.List;

public final class SimulationTestSupport {

    private SimulationTestSupport() { }

    public static void registerListener(SimulationEventListener listener) {
        SimulationEventListeners.register(listener);
    }

    public static void publishEvent(SimulationEvent event) {
        SimulationEventPublisher.publish(event);
    }

    public static List<SimulationEventListener> registeredListeners() {
        return SimulationEventListenerRegistry.listeners();
    }

    public static void resetListenerRegistry() {
        SimulationEventListenerRegistry.clear();
    }
}
