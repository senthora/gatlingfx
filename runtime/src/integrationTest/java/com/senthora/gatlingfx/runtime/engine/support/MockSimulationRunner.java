package com.senthora.gatlingfx.runtime.engine.support;

import com.senthora.gatlingfx.runtime.core.api.SimulationRunResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunner;

import java.util.List;

public final class MockSimulationRunner implements SimulationRunner {

    private final SimulationRunResult result;

    private MockSimulationRunner(SimulationRunResult result) {
        this.result = result;
    }

    public static MockSimulationRunner with(SimulationRunResult result) {
        return new MockSimulationRunner(result);
    }

    @Override
    public SimulationRunResult run(Class<?> simulationClass) {
        return result;
    }

    @Override
    public SimulationRunResult run(List<Class<?>> simulationClasses) {
        return result;
    }
}
