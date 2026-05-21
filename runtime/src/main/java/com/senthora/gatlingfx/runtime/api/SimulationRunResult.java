package com.senthora.gatlingfx.runtime.api;

/**
 * Result of a GatlingFx simulation execution.
 */
public interface SimulationRunResult {

    /**
     * Returns whether the simulation
     * completed successfully.
     *
     * @return {@code true} if successful
     */
    boolean successful();

    /**
     * Verifies that the simulation completed successfully.
     *
     * @throws AssertionError if the simulation failed
     */
    void assertSuccess();

    /**
     * Verifies that the simulation failed.
     *
     * @throws AssertionError if the simulation completed successfully
     */
    void assertFailure();
}
