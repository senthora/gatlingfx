package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import java.util.Objects;

/**
 * Internal utility that resolves
 * executable GatlingFx simulations.
 */
public final class SimulationResolver {

    private SimulationResolver() {}

    /**
     * Attempts to resolve the provided class
     * as a supported GatlingFx simulation.
     *
     * @param clazz class to resolve
     *
     * @return resolved simulation class
     * @throws NullPointerException if {@code clazz} is null
     * @throws IllegalArgumentException if the class
     * is not supported by GatlingFx
     */
    public static Class<? extends BaseSimulation> resolve(Class<?> clazz) {
        if (!isSupported(clazz)) {
            var message = "Class is not a GatlingFx simulation: " + clazz.getName();
            throw new IllegalArgumentException(message);
        }
        return resolveInternal(clazz);
    }

    /**
     * Returns whether the provided
     * class is supported by GatlingFx.
     *
     * @param clazz class to inspect
     *
     * @throws NullPointerException if {@code clazz} is null
     */
    public static boolean isSupported(Class<?> clazz) {
        Objects.requireNonNull(clazz, "clazz must not be null");
        return BaseSimulation.class.isAssignableFrom(clazz);
    }

    static Class<? extends BaseSimulation> resolveInternal(Class<?> clazz) {
        return clazz.asSubclass(BaseSimulation.class);
    }
}
