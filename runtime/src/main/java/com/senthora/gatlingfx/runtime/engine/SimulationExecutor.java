package com.senthora.gatlingfx.runtime.engine;

import com.senthora.gatlingfx.runtime.core.api.SimulationRunner;

import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestExecutionResult;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Internal utility that executes discovered
 * Gatling simulations and reports their
 * results to JUnit Platform.
 */
final class SimulationExecutor {

    private static Supplier<SimulationRunner> runnerSupplier = SimulationRunner::create;

    private SimulationExecutor() {}

    static void setRunner(Supplier<SimulationRunner> supplier) {
        runnerSupplier = Objects.requireNonNull(supplier, "supplier must not be null");
    }

    /**
     * Executes all simulation descriptors
     * contained in the provided execution request.
     *
     * @param request JUnit execution request
     */
    static void execute(ExecutionRequest request) {
        var listener = request.getEngineExecutionListener();
        var descriptor = request.getRootTestDescriptor();

        listener.executionStarted(descriptor);

        for (var child : descriptor.getChildren()) {
            execute(listener, (SimulationDescriptor) child);
        }
        var result = TestExecutionResult.successful();
        listener.executionFinished(descriptor, result);
    }

    private static void execute(
            EngineExecutionListener listener,
            SimulationDescriptor descriptor
    ) {
        listener.executionStarted(descriptor);
        try {
            execute(descriptor);

            var result = TestExecutionResult.successful();
            listener.executionFinished(descriptor, result);
        }
        catch (Throwable cause) {
            var result = TestExecutionResult.failed(cause);
            listener.executionFinished(descriptor, result);
        }
    }

    private static void execute(SimulationDescriptor descriptor) {
        var simulationClass = descriptor.simulationClass();

        var runner = runnerSupplier.get();
        var result = runner.run(List.of(simulationClass));

        if (!result.success()) {
            var message = "Simulation failed: " + simulationClass.getName();
            throw new AssertionError(message);
        }
    }
}
