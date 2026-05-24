package com.senthora.gatlingfx.simulation.api;

import com.senthora.gatlingfx.simulation.internal.DefaultSimulationAssert;

import org.jspecify.annotations.Nullable;

/**
 * Entry point for creating fluent simulation assertions.
 * <p>
 * Provides lightweight assertion utilities
 * intended for validating simulation verification
 * state and post-execution behavior.
 */
public final class SimulationAssertions {

    private SimulationAssertions() {}

    /**
     * Creates a fluent assertion
     * chain for the provided value.
     *
     * @param actual asserted value
     */
    @SuppressWarnings("NullableProblems")
    public static <T> DefaultSimulationAssert<T> assertThat(@Nullable T actual) {
        return new DefaultSimulationAssert<>(actual);
    }

    /**
     * Fails the current simulation assertion immediately.
     *
     * @param message assertion failure message
     *
     * @throws AssertionError always
     */
    public static void fail(String message) {
        throw new AssertionError(message);
    }
}
