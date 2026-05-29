package com.senthora.gatlingfx.runtime.core.application;

import com.senthora.gatlingfx.runtime.core.api.*;
import com.senthora.gatlingfx.runtime.core.internal.SimulationResolver;

import picocli.CommandLine;

import java.util.List;

/**
 * GatlingFx application entrypoint.
 * <p>
 * This bootstrap class executes GatlingFx simulations and
 * provides a command-line entrypoint that terminates the JVM
 * with an exit code representing the simulation execution result.
 * <p>
 * <strong>API Note:</strong>
 * Intended for command-line execution, Gradle integration,
 * CI pipelines, and IDE run configurations.
 */
public final class GatlingFx {

    private GatlingFx() {}

    /**
     * Executes GatlingFx simulations and terminates
     * the JVM using the resulting process exit code.
     * <p>
     * Exits with {@code 0} when execution
     * succeeds and {@code 1} when execution fails.
     */
    public static void main(String[] args) {
        System.exit(run(args));
    }

    /**
     * Executes GatlingFx simulations using
     * the provided command-line arguments.
     *
     * @param args command-line arguments
     *
     * @return {@code 0} when execution succeeds, otherwise {@code 1}
     * @throws IllegalArgumentException if the requested simulation
     * class does not exist or is not a valid GatlingFx simulation
     * @throws CommandLine.ParameterException if command-line arguments are invalid
     */
    static int run(String[] args) {
        var arguments = parseArgs(args);
        var config = createConfig(arguments);
        var runner = SimulationRunner.create(config);

        SimulationRunResult result;

        var simulationClassName = arguments.simulationClassName();
        if (simulationClassName.isPresent()) {
            var simulationClass = findClass(simulationClassName.get());
            var simulation = SimulationResolver.resolve(simulationClass);

            result = runner.run(simulation);
        }
        else {
            var discoveryResult = SimulationScanner.scan();

            result = runner.run(discoveryResult.supported());

            var unsupportedSimulations = discoveryResult.unsupported();
            if (!unsupportedSimulations.isEmpty()) {
                warnUnsupportedSimulations(unsupportedSimulations);
            }
        }
        return result.success() ? 0 : 1;
    }

    private static GatlingFxArguments parseArgs(String[] args) {
        var arguments = new GatlingFxArguments();
        new CommandLine(arguments).parseArgs(args);

        return arguments;
    }

    private static SimulationRuntimeConfig createConfig(GatlingFxArguments arguments) {
        var logLevel = arguments.quietLogs() ?
                RuntimeLogLevel.WARN : RuntimeLogLevel.INFO;

        return SimulationRuntimeConfig.create()
                .withLogLevel(logLevel)
                .withFailFast(arguments.failFast())
                .build();
    }

    private static Class<?> findClass(String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            var message = "Simulation class not found: " + className;
            throw new IllegalArgumentException(message, e);
        }
    }

    private static void warnUnsupportedSimulations(List<Class<?>> simulations) {
        StringBuilder sb = new StringBuilder();

        sb.append("WARN: Skipping unsupported Gatling simulations:");

        simulations.forEach(simulation ->
                sb.append("\n- ").append(simulation.getName())
        );
        System.out.println(sb);
    }
}
