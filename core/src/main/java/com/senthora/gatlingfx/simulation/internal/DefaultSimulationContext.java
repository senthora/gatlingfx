package com.senthora.gatlingfx.simulation.internal;

import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationContext;

import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * Default {@link SimulationContext} implementation.
 */
public final class DefaultSimulationContext implements SimulationContext {

    private final Class<? extends BaseSimulation> simulationClass;

    private @Nullable Throwable failure;

    /**
     * Creates a new simulation context.
     *
     * @param simulationClass simulation class
     *
     * @throws NullPointerException if {@code simulationClass} is null
     */
    public DefaultSimulationContext(Class<? extends BaseSimulation> simulationClass) {
        Objects.requireNonNull(simulationClass, "simulationClass must not be null");
        this.simulationClass = simulationClass;
    }

    @Override
    public void setFailure(Throwable failure) {
        Objects.requireNonNull(failure, "failure must not be null");
        this.failure = failure;
    }

    @Override
    public Optional<Throwable> failure() {
        return Optional.ofNullable(failure);
    }

    @Override
    public boolean hasFailed() {
        return failure != null;
    }

    @Override
    public Class<? extends BaseSimulation> simulationClass() {
        return simulationClass;
    }

    @Override
    public String toString() {
        return simulationClass.getName();
    }
}
