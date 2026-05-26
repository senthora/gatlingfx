package com.senthora.gatlingfx.runtime.support;

import com.senthora.gatlingfx.runtime.core.api.SimulationDiscoveryResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationScanner;

import org.mockito.Mockito;

public final class MockSimulationScanner {

    private MockSimulationScanner() {}

    public static void with(SimulationDiscoveryResult result, Runnable runnable) {
        try (var mocked = Mockito.mockStatic(SimulationScanner.class)) {
            mocked.when(SimulationScanner::scan).thenReturn(result);
            runnable.run();
        }
    }
}
