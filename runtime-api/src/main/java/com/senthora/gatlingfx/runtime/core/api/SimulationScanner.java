package com.senthora.gatlingfx.runtime.core.api;

import com.senthora.gatlingfx.runtime.core.internal.ClasspathSimulationScanner;

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
    static SimulationDiscoveryResult scan() {
        return ClasspathSimulationScanner.scan();
    }
}
