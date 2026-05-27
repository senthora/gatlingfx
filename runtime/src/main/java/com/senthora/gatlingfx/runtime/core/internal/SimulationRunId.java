package com.senthora.gatlingfx.runtime.core.internal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Unique identifier representing
 * a single GatlingFx runtime execution.
 *
 * @param value run identifier value
 */
record SimulationRunId(String value) {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    /**
     * Creates a new {@link SimulationRunId}.
     *
     * @throws NullPointerException if {@code value} is null
     * @throws IllegalArgumentException if {@code value} is blank
     */
    SimulationRunId {
        Objects.requireNonNull(value, "value must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("value must not be blank");
        }
    }

    /**
     * Creates a new unique {@link SimulationRunId}.
     */
    static SimulationRunId create() {
        return new SimulationRunId(FORMATTER.format(LocalDateTime.now()));
    }
}
