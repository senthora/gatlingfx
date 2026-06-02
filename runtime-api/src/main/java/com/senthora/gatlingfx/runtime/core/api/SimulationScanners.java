package com.senthora.gatlingfx.runtime.core.api;

import java.util.ServiceLoader;

/**
 * Entrypoint for creating {@link SimulationScanner} instances.
 */
public final class SimulationScanners {

    private SimulationScanners() {}

    /**
     * Creates a new simulation scanner.
     *
     * @throws IllegalStateException if no runtime implementation
     * is available on the classpath
     */
    public static SimulationScanner create() {
        return ServiceLoader
                .load(SimulationScannerFactory.class)
                .findFirst()
                .orElseThrow()
                .create();
    }
}
