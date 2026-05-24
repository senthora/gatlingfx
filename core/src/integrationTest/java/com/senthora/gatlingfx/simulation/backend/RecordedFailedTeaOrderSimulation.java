package com.senthora.gatlingfx.simulation.backend;

import com.senthora.gatlingfx.simulation.api.SimulationBackend;
import com.senthora.gatlingfx.simulation.api.SimulationScenario;
import com.senthora.gatlingfx.simulation.support.TeaShopSimulation;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

@DisplayName("Should record failed tea order")
public class RecordedFailedTeaOrderSimulation extends TeaShopSimulation {

    @Override
    protected SimulationBackend backend() {
        return new RecordingSimulationBackend();
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(new FailedTeaOrderScenario());
    }

    @Override
    protected void verify() {
        LifecycleRecorder.record(LifecycleRecorder.Event.VERIFY);
    }
}
