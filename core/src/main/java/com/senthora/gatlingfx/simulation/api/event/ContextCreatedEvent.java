package com.senthora.gatlingfx.simulation.api.event;

import com.senthora.gatlingfx.simulation.api.SimulationContext;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

/**
 * This event signals that a simulation
 * run context has been created.
 */
@NullMarked
public record ContextCreatedEvent(SimulationContext context) implements SimulationEvent {

    public ContextCreatedEvent {
        Objects.requireNonNull(context, "context must not be null");
    }
}
