package com.senthora.gatlingfx.runtime.core.api;

import java.util.List;

/**
 * Internal runtime responsible for
 * executing Gatling simulations inside the JVM.
 */
public interface SimulationRuntime {

    /**
     * Executes the provided Gatling simulations.
     * <p>
     * <strong>API Note:</strong>
     * Simulation execution does not short-circuit when
     * individual simulations fail. All provided simulations
     * are executed before returning execution results.
     *
     * @param simulationClasses simulation classes to execute
     * @return execution results
     */
    List<SimulationExecutionResult> execute(List<Class<?>> simulationClasses);
}
