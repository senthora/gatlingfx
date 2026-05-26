package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationDiscoveryResult;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import java.util.List;
import java.util.Objects;

/**
 * Default {@link SimulationDiscoveryResult} implementation.
 */
public record DefaultSimulationDiscoveryResult(
        List<Class<? extends BaseSimulation>> supported,
        List<Class<?>> unsupported
) implements SimulationDiscoveryResult {

    /**
     * Creates a new discovery result for given classes.
     *
     * @param supported supported GatlingFx simulations
     * @param unsupported discovered simulations
     * not supported by GatlingFx
     */
    public DefaultSimulationDiscoveryResult(
            List<Class<? extends BaseSimulation>> supported,
            List<Class<?>> unsupported
    ) {
        Objects.requireNonNull(supported, "supported must not be null");
        Objects.requireNonNull(unsupported, "unsupported must not be null");

        this.supported = List.copyOf(supported);
        this.unsupported = List.copyOf(unsupported);
    }
}
