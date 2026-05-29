package com.senthora.gatlingfx.runtime.core.api;

import java.util.Objects;

/**
 * Immutable configuration used for
 * simulation runtime execution.
 * <p>
 * Supports the following options:
 * <ul>
 *     <li>Fail-fast execution mode (default: {@code false})</li>
 *     <li>Runtime logging level (default: {@code INFO})</li>
 * </ul>
 */
public final class SimulationRuntimeConfig {

    private final boolean failFast;
    private final RuntimeLogLevel logLevel;

    private SimulationRuntimeConfig(Builder builder) {
        this.failFast = builder.failFast;
        this.logLevel = builder.logLevel;
    }

    /**
     * Creates a new runtime configuration builder.
     *
     * @return runtime configuration builder
     */
    public static Builder create() {
        return new Builder();
    }

    /**
     * Returns whether runtime execution
     * should stop after the first failure.
     *
     * @return {@code true} if fail-fast mode is enabled
     */
    public boolean failFast() {
        return failFast;
    }

    /**
     * Returns configured runtime logging level.
     */
    public RuntimeLogLevel logLevel() {
        return logLevel;
    }

    /**
     * Builder for {@link SimulationRuntimeConfig}.
     */
    public static final class Builder {

        private boolean failFast;
        private RuntimeLogLevel logLevel = RuntimeLogLevel.INFO;

        private Builder() {}

        /**
         * Enables or disables fail-fast execution mode.
         *
         * @param value whether to enable or disable fail-fast execution mode
         *
         * @return builder instance
         */
        public Builder withFailFast(boolean value) {
            this.failFast = value;
            return this;
        }

        /**
         * Sets runtime logging level.
         *
         * @param level runtime logging level
         *
         * @return builder instance
         * @throws NullPointerException if {@code level} is null
         */
        public Builder withLogLevel(RuntimeLogLevel level) {
            this.logLevel = Objects.requireNonNull(level, "level must not be null");
            return this;
        }

        /**
         * Builds runtime configuration.
         * <p>
         * <strong>API Note:</strong>
         * Defaults fail-fast mode to disabled and runtime
         * logging level to {@link RuntimeLogLevel#INFO}
         * when not configured.
         */
        public SimulationRuntimeConfig build() {
            return new SimulationRuntimeConfig(this);
        }
    }
}
