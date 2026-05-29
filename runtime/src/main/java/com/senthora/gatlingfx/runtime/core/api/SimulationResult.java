package com.senthora.gatlingfx.runtime.core.api;

/**
 * Result of a GatlingFx simulation execution.
 * <p>
 * Represents the terminal outcome produced
 * after executing a single simulation.
 */
public enum SimulationResult {
    SUCCESS, FAILURE;

    /**
     * Returns whether this result
     * represents a successful execution.
     */
    public boolean isSuccess() {
        return this == SUCCESS;
    }

    /**
     * Returns whether this result
     * represents a failed execution.
     */
    public boolean isFailure() {
        return this == FAILURE;
    }
}
