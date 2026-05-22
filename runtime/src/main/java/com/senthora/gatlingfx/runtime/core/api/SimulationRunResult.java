package com.senthora.gatlingfx.runtime.core.api;

import java.util.List;

/**
 * Result of a GatlingFx simulation execution.
 */
public interface SimulationRunResult {

    /**
     * Returns an immutable list
     * of executed simulation results.
     *
     * @return list of executed simulation results,
     * or an empty list if no simulations were executed
     */
    List<SimulationExecutionResult> simulations();

    /**
     * Returns whether all simulations
     * completed successfully.
     *
     * @return {@code true} if all simulations completed successfully,
     * or if no simulations were executed, otherwise {@code false}
     */
    boolean success();
}
