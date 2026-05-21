package com.senthora.gatlingfx.runtime.internal;

import org.jspecify.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Stores the currently active
 * GatlingFx simulation execution context.
 * <p>
 * This holder is used internally to propagate
 * runtime simulation failures across
 * Gatling execution threads.
 */
final class SimulationExecution {

    private static final AtomicReference<@Nullable SimulationExecutionContext> CURRENT = new AtomicReference<>();

    private SimulationExecution() {}

    static void set(SimulationExecutionContext context) {
        CURRENT.set(context);
    }

    static SimulationExecutionContext current() {
        return CURRENT.get();
    }
}
