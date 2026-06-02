package com.senthora.gatlingfx.runtime.core.api;

import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import java.util.List;

/**
 * Result of runtime simulation discovery.
 */
public interface SimulationDiscoveryResult {

    /**
     * Returns an immutable list of simulations
     * supported by GatlingFx runtime.
     */
    List<Class<? extends BaseSimulation>> supported();

    /**
     * Returns an immutable list of discovered
     * simulations not supported by GatlingFx.
     */
    List<Class<?>> unsupported();
}
