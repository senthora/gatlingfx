package com.senthora.gatlingfx.simulation.api;

import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

/**
 * Fluent assertion chain used for validating
 * simulation state and verification results.
 *
 * @param <T> asserted value type
 */
public interface SimulationAssert<T> {

    /**
     * Verifies that the actual value
     * is equal to the expected value.
     *
     * @param expected expected value
     * @return current assertion chain
     *
     * @throws AssertionError if assertion fails
     */
    SimulationAssert<T> isEqualTo(@Nullable T expected);

    /**
     * Verifies that the actual value is not null.
     *
     * @return current assertion chain
     *
     * @throws AssertionError if assertion fails
     */
    SimulationAssert<T> isNotNull();

    /**
     * Verifies that the actual value is null.
     *
     * @return current assertion chain
     *
     * @throws AssertionError if assertion fails
     */
    SimulationAssert<T> isNull();

    /**
     * Verifies that the actual value
     * satisfies the provided predicate.
     *
     * @param predicate validation predicate
     * @param message failure message
     * @return current assertion chain
     *
     * @throws NullPointerException if predicate or message is null
     * @throws AssertionError if assertion fails
     */
    SimulationAssert<T> matches(Predicate<T> predicate, String message);
}
