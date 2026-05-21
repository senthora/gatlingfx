package com.senthora.gatlingfx.runtime.api;

import com.senthora.gatlingfx.runtime.internal.RecordingValidate;

import io.gatling.javaapi.core.CheckBuilder;

/**
 * GatlingFx-aware wrapper around Gatling check builders.
 * <p>
 * Wrapped checks propagate runtime validation
 * failures to the GatlingFx execution context.
 *
 * @param <X> extracted value type
 */
public interface SimulationCheck<X> {

    /**
     * Wraps the provided Gatling check builder
     * with GatlingFx runtime failure recording.
     *
     * @param validate underlying Gatling check builder
     * @param <X> extracted value type
     *
     * @return wrapped simulation check
     */
    static <X> SimulationCheck<X> from(CheckBuilder.Validate<X> validate) {
        return new RecordingValidate<>(validate);
    }

    /**
     * Validates that the extracted value
     * matches the expected value.
     *
     * @param expected expected value
     *
     * @return finalized simulation check
     */
    CheckBuilder.Final is(X expected);
}
