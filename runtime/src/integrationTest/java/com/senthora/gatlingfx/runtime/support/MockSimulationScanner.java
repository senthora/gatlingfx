package com.senthora.gatlingfx.runtime.support;

import com.senthora.gatlingfx.runtime.core.api.SimulationScanner;

import org.mockito.Mockito;

import java.util.List;

public final class MockSimulationScanner {

    private MockSimulationScanner() {}

    public static void with(List<Class<?>> simulationClasses, Runnable runnable) {
        try (var mocked = Mockito.mockStatic(SimulationScanner.class)) {
            mocked.when(SimulationScanner::scan).thenReturn(simulationClasses);
            runnable.run();
        }
    }
}
