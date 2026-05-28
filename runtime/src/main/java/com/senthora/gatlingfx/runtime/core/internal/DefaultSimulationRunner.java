package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationRunResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunner;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntime;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

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
    public SimulationRunResult run(Class<? extends BaseSimulation> simulationClass) {
        return run(List.of(simulationClass));
    }

    @Override
    public SimulationRunResult run(List<Class<? extends BaseSimulation>> simulationClasses) {
        if (simulationClasses.isEmpty()) {
            return new DefaultSimulationRunResult(List.of());
        }
        var results = runtime.execute(simulationClasses);
        return new DefaultSimulationRunResult(results);
    }
}
