package com.senthora.gatlingfx.runtime.core.api;

import com.senthora.gatlingfx.runtime.core.internal.ClasspathSimulationScanner;

import java.util.List;

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
     * @return list of discovered simulation classes,
     * or an empty list if no simulations were discovered
     */
    static List<Class<?>> scan() {
        return ClasspathSimulationScanner.scan();
    }
}
