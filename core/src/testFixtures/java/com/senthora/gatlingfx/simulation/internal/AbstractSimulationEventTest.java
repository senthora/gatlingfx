package com.senthora.gatlingfx.simulation.internal;

import com.senthora.gatlingfx.simulation.api.event.SimulationEvent;
import com.senthora.gatlingfx.simulation.api.event.SimulationEventListener;
import com.senthora.gatlingfx.simulation.api.event.SimulationEventListeners;

import org.junit.jupiter.api.AfterEach;

import java.util.List;

abstract class AbstractSimulationEventTest {

    @AfterEach
    void teardownSimulationEventTest() {
        SimulationEventListenerRegistry.clear();
    }

    static void registerListener(SimulationEventListener listener) {
        SimulationEventListeners.register(listener);
    }

    static void publishEvent(SimulationEvent event) {
        SimulationEventPublisher.publish(event);
    }

    static List<SimulationEventListener> registeredListeners() {
        return SimulationEventListenerRegistry.listeners();
    }
}
