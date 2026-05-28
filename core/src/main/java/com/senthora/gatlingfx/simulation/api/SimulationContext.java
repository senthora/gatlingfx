package com.senthora.gatlingfx.simulation.api;

import com.senthora.gatlingfx.simulation.internal.DefaultSimulationContext;

import java.util.Optional;

/**
 * Context associated with a single simulation.
 * <p>
 * This context provides a stable object for
 * carrying simulation information beyond
 * the lifetime of the simulation itself.
 * <p>
 * <strong>API Note:</strong>
 * Primarily intended for use by
 * GatlingFx framework infrastructure.
 */
public interface SimulationContext {

    /**
     * Creates a new simulation context.
     *
     * @param simulationClass simulation class
     *
     * @throws NullPointerException if {@code simulationClass} is null
     */
    static SimulationContext create(Class<? extends BaseSimulation> simulationClass) {
        return new DefaultSimulationContext(simulationClass);
    }

    /**
     * Sets the {@link Throwable}
     * that caused simulation failure.
     * <p>
     * If a failure was already set, the provided
     * failure replaces the previous one.
     *
     * @param failure simulation execution failure
     *
     * @throws NullPointerException if {@code failure} is null
     */
    void setFailure(Throwable failure);

    /**
     * Returns {@link Throwable}
     * that caused simulation failure.
     */
    Optional<Throwable> failure();

    /**
     * Returns whether simulation failed.
     */
    boolean hasFailed();

    /**
     * Returns simulation class.
     */
    Class<? extends BaseSimulation> simulationClass();
}
