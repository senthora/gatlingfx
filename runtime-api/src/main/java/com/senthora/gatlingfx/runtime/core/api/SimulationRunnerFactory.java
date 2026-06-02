package com.senthora.gatlingfx.runtime.core.api;

/**
 * Factory for creating {@link SimulationRunner} instances.
 */
public interface SimulationRunnerFactory {

    /**
     * Creates a new simulation runner.
     *
     * @param config simulation runtime configuration
     *
     * @throws NullPointerException if {@code config} is null
     */
    SimulationRunner create(SimulationRuntimeConfig config);
}
