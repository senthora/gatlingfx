package com.senthora.gatlingfx.runtime.internal;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Mutable execution state associated
 * with a running GatlingFx simulation.
 * <p>
 * Used internally to track whether runtime validation
 * failures occurred during simulation execution.
 */
final class SimulationExecutionContext {

    private final AtomicBoolean failed = new AtomicBoolean();

    boolean failed() {
        return failed.get();
    }

    void markFailed() {
        failed.set(true);
    }
}
