package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationContext;
import com.senthora.gatlingfx.simulation.api.event.ContextCreatedEvent;
import com.senthora.gatlingfx.simulation.api.event.SimulationEventListeners;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Registry for simulation contexts.
 * <p>
 * Simulation contexts are registered through
 * published simulation events and remain available
 * throughout the lifetime of the registry.
 */
final class SimulationContextRegistry {

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean();
    private static final Map<Class<? extends BaseSimulation>, SimulationContext>
            CONTEXTS = new ConcurrentHashMap<>();

    /**
     * Initializes context registry.
     * <p>
     * If the registry was reset, initialization
     * must be performed again before published
     * events are processed. Repeated initialization
     * calls have no effect until the registry is reset.
     *
     * @see #reset()
     */
    static void initialize() {
        if (INITIALIZED.compareAndSet(false, true)) {
            registerListeners();
        }
    }

    /**
     * Returns simulation context associated
     * with the provided simulation class.
     *
     * @param simulationClass simulation class
     *
     * @throws NullPointerException if {@code simulationClass} is null
     */
    static Optional<SimulationContext> get(Class<? extends BaseSimulation> simulationClass) {
        Objects.requireNonNull(simulationClass, "simulationClass must not be null");
        return Optional.ofNullable(CONTEXTS.get(simulationClass));
    }

    /**
     * Resets registry state.
     * <p>
     * Registered simulation contexts are removed,
     * and initialization must be performed again
     * before published events are processed.
     *
     * @see #initialize()
     */
    static void reset() {
        INITIALIZED.set(false);
        CONTEXTS.clear();
    }

    private static void registerListeners() {
        SimulationEventListeners.register(event -> {
            if (event instanceof ContextCreatedEvent(SimulationContext context)) {
                registerContext(context);
            }
        });
    }

    private static void registerContext(SimulationContext context) {
        var simulationClass = context.simulationClass();
        var previous = CONTEXTS.putIfAbsent(simulationClass, context);

        if (previous != null) {
            var message = "Context already registered for class ";
            throw new IllegalStateException(message + simulationClass.getName());
        }
    }
}
