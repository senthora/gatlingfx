package com.senthora.gatlingfx.runtime.internal;

import com.senthora.gatlingfx.runtime.api.SimulationExecutionResult;
import com.senthora.gatlingfx.runtime.api.SimulationResult;

import java.util.Objects;

/**
 * Default {@link SimulationExecutionResult} implementation.
 */
record DefaultSimulationExecutionResult(
        Class<?> simulationClass,
        SimulationResult result
) implements SimulationExecutionResult{

    /**
     * Creates a new executed simulation result.
     *
     * @param simulationClass executed simulation class
     * @param result simulation execution result
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    DefaultSimulationExecutionResult {
        Objects.requireNonNull(simulationClass, "simulationClass must not be null");
        Objects.requireNonNull(result, "result must not be null");
    }
}
