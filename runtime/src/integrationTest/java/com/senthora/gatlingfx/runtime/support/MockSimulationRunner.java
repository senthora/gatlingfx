package com.senthora.gatlingfx.runtime.support;

import com.senthora.gatlingfx.runtime.core.api.SimulationRunResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunner;

import org.mockito.Mockito;

import java.util.function.Consumer;

public final class MockSimulationRunner {

    private MockSimulationRunner() {}

    @SuppressWarnings("unchecked")
    public static void with(SimulationRunResult result, Consumer<SimulationRunner> consumer) {
        var runner = Mockito.mock(SimulationRunner.class);

        Mockito.when(runner.run(Mockito.any(Class.class))).thenReturn(result);
        Mockito.when(runner.run(Mockito.anyList())).thenReturn(result);

        try (var mocked = Mockito.mockStatic(SimulationRunner.class)) {
            mocked.when(() -> SimulationRunner.create(Mockito.any())).thenReturn(runner);
            consumer.accept(runner);
        }
    }

    public static void with(SimulationRunResult result, Runnable runnable) {
        with(result, ignored -> runnable.run());
    }
}
