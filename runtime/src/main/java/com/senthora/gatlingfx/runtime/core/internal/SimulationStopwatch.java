package com.senthora.gatlingfx.runtime.core.internal;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * Tracks simulation execution duration.
 */
final class SimulationStopwatch {

    private final Clock clock;
    private final Instant start;

    private SimulationStopwatch(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock must not be null");
        this.start = clock.instant();
    }

    /**
     * Starts a new simulation stopwatch
     * using the provided clock.
     *
     * @param clock simulation stopwatch clock
     *
     * @return started simulation stopwatch
     * @throws NullPointerException if {@code clock} is null
     */
    static SimulationStopwatch start(Clock clock) {
        return new SimulationStopwatch(clock);
    }

    /**
     * Starts a new simulation stopwatch
     * using the system UTC clock.
     *
     * @return started simulation stopwatch
     */
    static SimulationStopwatch start() {
        return new SimulationStopwatch(Clock.systemUTC());
    }

    /**
     * Returns elapsed simulation duration.
     */
    Duration elapsed() {
        return Duration.between(start, clock.instant());
    }
}
