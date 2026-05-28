package com.senthora.gatlingfx.simulation.internal;

import com.senthora.gatlingfx.simulation.api.event.SimulationEvent;
import com.senthora.gatlingfx.simulation.api.event.SimulationEventListener;
import com.senthora.gatlingfx.simulation.api.event.SimulationEventListeners;

import org.junit.jupiter.api.AfterEach;

import java.util.List;

public abstract class AbstractSimulationEventTest {

    @AfterEach
    protected void teardownSimulationEventTest() {
        SimulationEventListenerRegistry.clear();
    }

    protected static void registerListener(SimulationEventListener listener) {
        SimulationEventListeners.register(listener);
    }

    protected static void publishEvent(SimulationEvent event) {
        SimulationEventPublisher.publish(event);
    }

    protected static List<SimulationEventListener> registeredListeners() {
        return SimulationEventListenerRegistry.listeners();
    }
}
