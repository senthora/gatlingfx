package com.senthora.gatlingfx.runtime.core.api;

import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import java.util.List;

/**
 * Internal runtime responsible for
 * executing GatlingFx simulations inside the JVM.
 */
public interface SimulationRuntime {

    /**
     * Executes the provided GatlingFx simulations.
     * <p>
     * Simulation execution does not short-circuit
     * when individual simulations fail unless fail-fast
     * execution is enabled. All provided simulations
     * are executed before returning execution results,
     * unless an unexpected runtime failure occurs.
     * <p>
     * Simulation failures, including Gatling assertion
     * failures, are reported through returned execution
     * results. Unexpected runtime failures during
     * simulation execution terminate processing and
     * result in {@link SimulationRuntimeException}.
     * <p>
     * Simulations are executed sequentially in the order provided.
     *
     * @param simulationClasses simulation classes to execute
     *
     * @return execution results
     * @throws SimulationRuntimeException if an unexpected
     * failure occurs during simulation execution
     */
    List<SimulationExecutionResult> execute(List<Class<? extends BaseSimulation>> simulationClasses);
}
