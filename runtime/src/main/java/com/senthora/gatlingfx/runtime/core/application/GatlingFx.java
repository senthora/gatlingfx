package com.senthora.gatlingfx.runtime.core.application;

import com.senthora.gatlingfx.runtime.core.api.SimulationRunResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunner;
import com.senthora.gatlingfx.runtime.core.api.SimulationScanner;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import picocli.CommandLine;

import java.util.List;
import java.util.stream.Collectors;

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
            var gatlingSimulation = resolveSimulationClass(simulationClassName.get());
            result = runner.run(gatlingSimulation);
        }
        else {
            var simulationClasses = SimulationScanner.scan();
            result = runner.run(asSimulations(simulationClasses));
        }
        return result.success() ? 0 : 1;
    }

    private static Class<? extends BaseSimulation> resolveSimulationClass(String className) {
        try {
            return asSimulation(Class.forName(className));
        }
        catch (ClassNotFoundException e) {
            var message = "Simulation class not found: " + className;
            throw new IllegalArgumentException(message, e);
        }
    }

    private static Class<? extends BaseSimulation> asSimulation(Class<?> clazz) {
        if (!BaseSimulation.class.isAssignableFrom(clazz)) {
            var message = "Class is not a GatlingFx simulation: " + clazz.getName();
            throw new IllegalArgumentException(message);
        }
        return clazz.asSubclass(BaseSimulation.class);
    }

    private static List<Class<? extends BaseSimulation>> asSimulations(List<Class<?>> classes) {
        return classes.stream()
                .map(GatlingFx::asSimulation)
                .collect(Collectors.toList());
    }

    private static GatlingFxArguments parseArgs(String[] args) {
        var arguments = new GatlingFxArguments();
        new CommandLine(arguments).parseArgs(args);

        return arguments;
    }
}
