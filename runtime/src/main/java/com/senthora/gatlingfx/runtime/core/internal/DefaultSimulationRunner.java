package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.*;

import java.util.List;
import java.util.Objects;

/**
 * Default {@link SimulationRunner} implementation.
 */
public final class DefaultSimulationRunner implements SimulationRunner {

    private final SimulationRuntime runtime;

    public DefaultSimulationRunner(SimulationRuntime runtime) {
        this.runtime = Objects.requireNonNull(runtime, "runtime must not be null");
    }

    @Override
    public SimulationRunResult run(Class<?> simulationClass) {
        return run(List.of(simulationClass));
    }

    @Override
    public SimulationRunResult run(List<Class<?>> simulationClasses) {
        if (simulationClasses.isEmpty()) {
            return new DefaultSimulationRunResult(List.of());
        }
        var results = runtime.execute(simulationClasses);
        return new DefaultSimulationRunResult(results);
    }
}
