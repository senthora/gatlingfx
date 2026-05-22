package com.senthora.gatlingfx.runtime.api;

import com.senthora.gatlingfx.runtime.internal.DefaultSimulationScanner;

import java.util.List;

/**
 * Discovers GatlingFx simulations
 * available on the runtime classpath.
 * <p>
 * A simulation is considered discoverable
 * when annotated with {@link GatlingSimulation}.
 * <p>
 * This component is intended for runtime integrations that
 * need to automatically locate and execute simulations
 * without requiring explicit registration.
 */
public interface SimulationScanner {

    /**
     * Creates a new simulation scanner.
     */
    static SimulationScanner create() {
        return new DefaultSimulationScanner();
    }

    /**
     * Scans the runtime classpath
     * for discoverable simulations.
     *
     * @return list of discovered simulation classes,
     * or an empty list if no simulations were discovered
     */
    List<Class<?>> scan();
}
