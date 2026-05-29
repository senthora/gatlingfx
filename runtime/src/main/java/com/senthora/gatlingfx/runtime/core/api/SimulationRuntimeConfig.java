package com.senthora.gatlingfx.runtime.core.api;

/**
 * Immutable configuration used for
 * simulation runtime execution.
 * <p>
 * Supports the following options:
 * <ul>
 *     <li>Fail-fast execution mode (default: {@code false})</li>
 * </ul>
 */
public final class SimulationRuntimeConfig {

    private final boolean failFast;

    private SimulationRuntimeConfig(Builder builder) {
        this.failFast = builder.failFast;
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
     * Builder for {@link SimulationRuntimeConfig}.
     */
    public static final class Builder {

        private boolean failFast;

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
         * Builds runtime configuration.
         *
         * @return runtime configuration
         */
        public SimulationRuntimeConfig build() {
            return new SimulationRuntimeConfig(this);
        }
    }
}
