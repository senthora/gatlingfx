package com.senthora.gatlingfx.runtime.core.api;

import java.util.ServiceLoader;

/**
 * Entrypoint for creating {@link SimulationRunner} instances.
 */
public final class SimulationRunners {

    private SimulationRunners() {}

    /**
     * Creates a new simulation runner.
     *
     * @param config simulation runtime configuration
     *
     * @throws NullPointerException if {@code config} is null
     * @throws IllegalStateException if no runtime implementation
     * is available on the classpath
     */
    public static SimulationRunner create(SimulationRuntimeConfig config) {
        return ServiceLoader
                .load(SimulationRunnerFactory.class)
                .findFirst()
                .orElseThrow()
                .create(config);
    }
}
