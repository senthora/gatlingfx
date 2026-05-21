package com.senthora.gatlingfx.runtime.internal;

import com.senthora.gatlingfx.runtime.api.SimulationRunResult;

/**
 * Result of a GatlingFx simulation execution.
 */
public final class DefaultSimulationRunResult implements SimulationRunResult {

    private final boolean successful;

    DefaultSimulationRunResult(boolean successful) {
        this.successful = successful;
    }

    @Override
    public boolean successful() {
        return successful;
    }

    @Override
    public void assertSuccess() {
        if (!successful) {
            throw new AssertionError("Expected simulation to complete successfully");
        }
    }

    @Override
    public void assertFailure() {
        if (successful) {
            throw new AssertionError("Expected simulation to fail");
        }
    }
}
