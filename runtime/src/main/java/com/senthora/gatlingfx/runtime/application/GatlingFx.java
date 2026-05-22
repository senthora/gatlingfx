package com.senthora.gatlingfx.runtime.application;

import com.senthora.gatlingfx.runtime.internal.DefaultSimulationRunner;

/**
 * GatlingFx application entrypoint.
 * <p>
 * This bootstrap class executes all discovered GatlingFx simulations
 * using the default simulation runner and terminates the JVM with a
 * non-zero exit code when at least one simulation fails.
 * <p>
 * Intended for command-line execution, Gradle integration,
 * CI pipelines, and IDE run configurations.
 */
public final class GatlingFx {

    public static void main(String[] args) {
        var result = new DefaultSimulationRunner().runAll();
        if (!result.success()) {
            System.exit(1);
        }
    }
}
