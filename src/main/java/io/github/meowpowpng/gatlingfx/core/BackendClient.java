package io.github.meowpowpng.gatlingfx.core;

/**
 * Represents a backend system used by a simulation.
 */
public interface BackendClient {

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
    static BackendClient stub() {
        return new BackendClient() {

            @Override
            public void setup() {}

            @Override
            public void teardown() {}
        };
    }
}
