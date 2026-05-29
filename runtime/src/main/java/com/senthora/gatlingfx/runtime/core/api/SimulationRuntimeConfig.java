package com.senthora.gatlingfx.runtime.core.api;

import java.util.Objects;

/**
 * Immutable configuration used for
 * simulation runtime execution.
 */
public final class SimulationRuntimeConfig {

    private final RuntimeLogLevel logLevel;

    private SimulationRuntimeConfig(Builder builder) {
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
     * Returns configured runtime logging level.
     */
    public RuntimeLogLevel logLevel() {
        return logLevel;
    }

    /**
     * Builder for {@link SimulationRuntimeConfig}.
     */
    public static final class Builder {

//        private boolean failFast;
        private RuntimeLogLevel logLevel = RuntimeLogLevel.INFO;

        private Builder() {}


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
         * Defaults runtime logging level to
         * {@link RuntimeLogLevel#INFO} when not configured.
         *
         * @return runtime configuration
         */
        public SimulationRuntimeConfig build() {
            return new SimulationRuntimeConfig(this);
        }
    }
}
