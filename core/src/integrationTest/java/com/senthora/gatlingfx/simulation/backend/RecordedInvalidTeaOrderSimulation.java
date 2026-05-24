package com.senthora.gatlingfx.simulation.backend;

import com.senthora.gatlingfx.simulation.api.SimulationAssertions;
import com.senthora.gatlingfx.simulation.api.SimulationBackend;
import com.senthora.gatlingfx.simulation.api.SimulationScenario;
import com.senthora.gatlingfx.simulation.support.TeaShopSimulation;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

@DisplayName("Should record invalid tea order")
public class RecordedInvalidTeaOrderSimulation extends TeaShopSimulation {

    @Override
    protected SimulationBackend backend() {
        return new RecordingSimulationBackend();
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(new SuccessfulTeaOrderScenario());
    }

    @Override
    protected void verify() {
        LifecycleRecorder.record(LifecycleRecorder.Event.VERIFY);
        SimulationAssertions.fail("invalid tea order");
    }
}
