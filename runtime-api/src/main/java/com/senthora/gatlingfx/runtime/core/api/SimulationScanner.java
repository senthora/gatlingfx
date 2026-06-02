package com.senthora.gatlingfx.runtime.core.api;

/**
 * Discovers classes annotated with {@link GatlingSimulation}
 * available on the runtime classpath.
 * <p>
 * Intended for runtime integrations that need to
 * automatically locate and execute simulations
 * without requiring explicit registration.
 */
public interface SimulationScanner {

    /**
     * Scans the runtime classpath
     * for discoverable simulations.
     *
     * @return the result of simulation discovery
     */
    SimulationDiscoveryResult scan();
}
