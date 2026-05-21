package com.senthora.gatlingfx.simulation.api;

/**
 * Represents a backend system used by a simulation.
 */
public interface SimulationBackend {

    /**
     * Prepares the backend before simulation starts.
     */
    void setup();

    /**
     * Cleans up backend state after simulation completes.
     */
    void teardown();

    /**
     * Returns a backend client that performs no operations.
     */
    static SimulationBackend stub() {
        return new SimulationBackend() {

            @Override
            public void setup() {}

            @Override
            public void teardown() {}
        };
    }
}
