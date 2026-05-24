package com.senthora.gatlingfx.simulation.backend;

import com.senthora.gatlingfx.simulation.api.SimulationBackend;

final class RecordingSimulationBackend implements SimulationBackend {

    @Override
    public void setup() {
        LifecycleRecorder.record(LifecycleRecorder.Event.SETUP);
    }

    @Override
    public void teardown() {
        LifecycleRecorder.record(LifecycleRecorder.Event.TEARDOWN);
    }
}
