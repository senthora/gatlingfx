package com.senthora.gatlingfx.runtime.core.application;

import com.senthora.gatlingfx.runtime.core.api.SimulationRunner;
import com.senthora.gatlingfx.runtime.core.api.SimulationScanner;

/**
 * GatlingFx application entrypoint.
 * <p>
 * This bootstrap class executes all discovered GatlingFx
 * simulations using the default simulation runner and terminates
 * the JVM with a non-zero exit code when at least one simulation fails.
 * <p>
 * <strong>API Note:</strong>
 * Intended for command-line execution, Gradle integration,
 * CI pipelines, and IDE run configurations.
 */
public final class GatlingFx {

    private GatlingFx() {}

    public static void main(String[] args) {
        var simulationClasses = SimulationScanner.scan();
        var result = SimulationRunner.create().run(simulationClasses);

        System.exit(result.success() ? 0 : 1);
    }
}
