package com.senthora.gatlingfx.runtime.core.application;

import com.senthora.gatlingfx.runtime.core.api.SimulationRunResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunner;
import com.senthora.gatlingfx.runtime.core.api.SimulationScanner;

import io.gatling.javaapi.core.Simulation;
import picocli.CommandLine;

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

    // TODO: implement support for arguments;
    //      - fail-fast on first simulation fail (--fail-fast flag)
    private GatlingFx() {}

    public static void main(String[] args) {
        System.exit(run(args));
    }

    static int run(String[] args) {
        GatlingFxArguments arguments = parseArgs(args);
        SimulationRunner runner = SimulationRunner.create();

        SimulationRunResult result;

        var simulationClassName = arguments.simulationClassName();
        if (simulationClassName.isPresent()) {
            var simulationClass = resolveSimulationClass(simulationClassName.get());
            result = runner.run(simulationClass);
        }
        else {
            var simulationClasses = SimulationScanner.scan();
            result = runner.run(simulationClasses);
        }
        return result.success() ? 0 : 1;
    }

    private static Class<? extends Simulation> resolveSimulationClass(String className) {
        try {
            var simulationClass = Class.forName(className);

            if (!Simulation.class.isAssignableFrom(simulationClass)) {
                var message = "Class is not a Gatling simulation: " + className;
                throw new IllegalArgumentException(message);
            }
            return simulationClass.asSubclass(Simulation.class);
        }
        catch (ClassNotFoundException e) {
            var message = "Simulation class not found: " + className;
            throw new IllegalArgumentException(message, e);
        }
    }

    private static GatlingFxArguments parseArgs(String[] args) {
        var arguments = new GatlingFxArguments();
        new CommandLine(arguments).parseArgs(args);

        return arguments;
    }
}
