package com.senthora.gatlingfx.runtime.core.api;

import java.util.List;

/**
 * Internal runtime responsible for
 * executing Gatling simulations inside the JVM.
 */
public interface SimulationRuntime {

    /**
     * Executes the provided Gatling simulations.
     *
     * @param simulationClasses simulation classes to execute
     * @return execution results
     */
    List<SimulationExecutionResult> execute(List<Class<?>> simulationClasses);
}
