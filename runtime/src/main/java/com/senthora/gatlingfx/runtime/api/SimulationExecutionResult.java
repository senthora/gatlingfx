package com.senthora.gatlingfx.runtime.api;

/**
 * Result of a single GatlingFx simulation execution.
 */
public interface SimulationExecutionResult {

    /**
     * Returns the executed simulation class.
     */
    Class<?> simulationClass();

    /**
     * Returns the simulation execution result.
     */
    SimulationResult result();
}
