package com.senthora.gatlingfx.core;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpRequestActionBuilder;

import java.util.Objects;

import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * Represents a reusable Gatling scenario that
 * can be composed into larger simulations.
 * <p>
 * Implementations typically encapsulate a specific interaction
 * flow, validation behavior, or infrastructure-oriented test scenario.
 */
public interface SimulationScenario {

    /**
     * Builds the underlying Gatling scenario definition.
     *
     * @return scenario builder representing this simulation scenario
     */
    ScenarioBuilder build();

    /**
     * Creates a basic HTTP GET request builder for the provided path.
     *
     * @param path request path
     *
     * @return configured HTTP GET request builder
     *
     * @throws NullPointerException if {@code path} is null
     * @throws IllegalArgumentException if {@code path} is blank
     */
    default HttpRequestActionBuilder get(String path) {
        Objects.requireNonNull(path, "path must not be null");
        if (path.isBlank()) {
            throw new IllegalArgumentException("path must not be blank");
        }
        return http("GET " + path).get(path);
    }
}
