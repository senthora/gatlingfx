package com.senthora.gatlingfx.runtime.core.api;

/**
 * Factory for creating {@link SimulationScanner} instances.
 */
public interface SimulationScannerFactory {

    /**
     * Creates a new simulation scanner.
     */
    SimulationScanner create();
}
