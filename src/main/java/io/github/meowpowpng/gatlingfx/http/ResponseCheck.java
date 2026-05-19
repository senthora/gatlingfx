package io.github.meowpowpng.gatlingfx.http;

import io.gatling.javaapi.core.CheckBuilder;

/**
 * Represents a reusable response validation
 * executed as part of a simulation request.
 */
@FunctionalInterface
public interface ResponseCheck {

    /**
     * Builds the underlying Gatling response checks.
     *
     * @return Gatling response checks
     */
    CheckBuilder[] build();
}
