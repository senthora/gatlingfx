package com.senthora.gatlingfx.runtime.engine;

import com.senthora.gatlingfx.runtime.core.api.RuntimeLogLevel;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunner;
import com.senthora.gatlingfx.runtime.core.application.LoggingContext;

import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestExecutionResult;

import java.util.List;

/**
 * Internal utility that executes discovered
 * Gatling simulations and reports their
 * results to JUnit Platform.
 */
final class SimulationExecutor {

    private SimulationExecutor() {}

    /**
     * Executes all simulation descriptors
     * contained in the provided execution request.
     *
     * @param request JUnit execution request
     */
    static void execute(SimulationRunner runner, ExecutionRequest request) {
        var listener = request.getEngineExecutionListener();
        var descriptor = request.getRootTestDescriptor();

        listener.executionStarted(descriptor);

        for (var child : descriptor.getChildren()) {
            execute(runner, listener, (SimulationDescriptor) child);
        }
        var result = TestExecutionResult.successful();
        listener.executionFinished(descriptor, result);
    }

    private static void execute(
            SimulationRunner runner,
            EngineExecutionListener listener,
            SimulationDescriptor descriptor
    ) {
        listener.executionStarted(descriptor);
        try {
            execute(runner, descriptor);

            var result = TestExecutionResult.successful();
            listener.executionFinished(descriptor, result);
        }
        catch (Throwable cause) {
            var result = TestExecutionResult.failed(cause);
            listener.executionFinished(descriptor, result);
        }
    }

    private static void execute(SimulationRunner runner, SimulationDescriptor descriptor) {
        var simulationClass = descriptor.simulationClass();

        try (var ignore = LoggingContext.configure(RuntimeLogLevel.ERROR)) {
            var result = runner.run(List.of(simulationClass));

            if (!result.success()) {
                var message = "Simulation failed: " + simulationClass.getName();
                throw new AssertionError(message);
            }
        }
    }
}
