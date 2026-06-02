package com.senthora.gatlingfx.runtime.core.api;

import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import java.util.List;
import java.util.Objects;

/**
 * Result of runtime simulation discovery.
 */
public record SimulationDiscoveryResult(
        List<Class<? extends BaseSimulation>> supported,
        List<Class<?>> unsupported
) {
    /**
     * Creates a new discovery result for given classes.
     *
     * @param supported supported GatlingFx simulations
     * @param unsupported discovered simulations
     * not supported by GatlingFx
     */
    public SimulationDiscoveryResult(
            List<Class<? extends BaseSimulation>> supported,
            List<Class<?>> unsupported
    ) {
        Objects.requireNonNull(supported, "supported must not be null");
        Objects.requireNonNull(unsupported, "unsupported must not be null");

        this.supported = List.copyOf(supported);
        this.unsupported = List.copyOf(unsupported);
    }
}
