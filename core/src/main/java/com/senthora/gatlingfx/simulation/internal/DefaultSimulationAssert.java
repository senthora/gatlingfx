package com.senthora.gatlingfx.simulation.internal;

import com.senthora.gatlingfx.simulation.api.SimulationAssert;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Default {@link SimulationAssert} implementation.
 */
@NullMarked
public final class DefaultSimulationAssert<T> implements SimulationAssert<T> {

    private final @Nullable T actual;

    public DefaultSimulationAssert(@Nullable T actual) {
        this.actual = actual;
    }

    @Override
    public DefaultSimulationAssert<T> isEqualTo(@Nullable T expected) {
        if (!Objects.equals(actual, expected)) {
            var message = "Expected <%s> but was <%s>";
            throw new AssertionError(message.formatted(expected, actual));
        }
        return this;
    }

    @Override
    public DefaultSimulationAssert<T> isNotNull() {
        if (actual == null) {
            var message = "Expected value to be non-null";
            throw new AssertionError(message);
        }
        return this;
    }

    @Override
    public DefaultSimulationAssert<T> isNull() {
        if (actual != null) {
            var message = "Expected value to be null but was <%s>";
            throw new AssertionError(message.formatted(actual));
        }
        return this;
    }

    @Override
    public DefaultSimulationAssert<T> matches(Predicate<T> predicate, String message) {
        Objects.requireNonNull(predicate, "predicate must not be null");
        Objects.requireNonNull(message, "message must not be null");

        //noinspection DataFlowIssue
        if (!predicate.test(actual)) {
            throw new AssertionError(message);
        }
        return this;
    }
}
