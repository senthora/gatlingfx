package com.senthora.gatlingfx.runtime.engine.support;

import com.senthora.gatlingfx.runtime.core.api.SimulationExecutionResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunResult;

import java.util.List;

public final class MockSimulationRunResult implements SimulationRunResult {

    public static final MockSimulationRunResult ALWAYS_SUCCESS =
            new MockSimulationRunResult(List.of(), true);

    public static final MockSimulationRunResult ALWAYS_FAIL =
            new MockSimulationRunResult(List.of(), false);

    private final List<SimulationExecutionResult> simulations;
    private final boolean success;

    private MockSimulationRunResult(
            List<SimulationExecutionResult> simulations,
            boolean success
    ) {
        this.simulations = simulations;
        this.success = success;
    }

    @Override
    public List<SimulationExecutionResult> simulations() {
        return simulations;
    }

    @Override
    public boolean success() {
        return success;
    }
}
