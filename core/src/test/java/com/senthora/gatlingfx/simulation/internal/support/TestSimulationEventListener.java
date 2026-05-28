package com.senthora.gatlingfx.simulation.internal.support;

import com.senthora.gatlingfx.simulation.api.event.SimulationEvent;
import com.senthora.gatlingfx.simulation.api.event.SimulationEventListener;

import java.util.ArrayList;
import java.util.List;

public final class TestSimulationEventListener implements SimulationEventListener {

    private final List<SimulationEvent> events = new ArrayList<>();

    @Override
    public void onEvent(SimulationEvent event) {
        events.add(event);
    }

    public List<SimulationEvent> events() {
        return events;
    }
}
