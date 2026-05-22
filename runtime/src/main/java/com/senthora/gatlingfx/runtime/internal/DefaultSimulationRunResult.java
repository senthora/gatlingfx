package com.senthora.gatlingfx.runtime.internal;

import com.senthora.gatlingfx.runtime.api.SimulationExecutionResult;
import com.senthora.gatlingfx.runtime.api.SimulationResult;
import com.senthora.gatlingfx.runtime.api.SimulationRunResult;

import java.util.List;
import java.util.Objects;

/**
 * Default {@link SimulationRunResult} implementation.
 */
public final class DefaultSimulationRunResult implements SimulationRunResult {

    private final List<SimulationExecutionResult> results;
    private final boolean success;

    DefaultSimulationRunResult(List<SimulationExecutionResult> results) {
        Objects.requireNonNull(results, "results must not be null");
        this.results = List.copyOf(results);
        this.success = results.stream().allMatch(result ->
                result.result() == SimulationResult.SUCCESS
        );
    }

    @Override
    public List<SimulationExecutionResult> simulations() {
        return results;
    }

    @Override
    public boolean success() {
        return success;
    }
}
