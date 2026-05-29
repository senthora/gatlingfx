package com.senthora.gatlingfx.runtime.support;

import com.senthora.gatlingfx.runtime.core.api.SimulationDiscoveryResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunner;
import com.senthora.gatlingfx.runtime.core.api.SimulationScanner;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

public final class MockRuntimeSession implements AutoCloseable {

    private final SimulationRunner runner;
    private final MockedStatic<SimulationRunner> runnerMock;

    private MockedStatic<SimulationScanner> scannerMock;

    private MockRuntimeSession() {
        this.runner = Mockito.mock(SimulationRunner.class);
        this.runnerMock = Mockito.mockStatic(SimulationRunner.class);

        runnerMock.when(() -> SimulationRunner.create(Mockito.any()))
                .thenReturn(runner);
    }

    public static MockRuntimeSession create() {
        return new MockRuntimeSession();
    }

    @SuppressWarnings("unchecked")
    public void stubExecutionResult(boolean success) {
        var result = Mockito.mock(SimulationRunResult.class);
        Mockito.when(result.success()).thenReturn(success);

        Mockito.when(runner.run(Mockito.any(Class.class))).thenReturn(result);
        Mockito.when(runner.run(Mockito.anyList())).thenReturn(result);
    }

    public void stubDiscoveryResult(SimulationDiscoveryResult result) {
        scannerMock = Mockito.mockStatic(SimulationScanner.class);

        scannerMock.when(SimulationScanner::scan).thenReturn(result);
    }

    public void verifySimulationExecuted(Class<? extends BaseSimulation> clazz) {
        Mockito.verify(runner).run(clazz);
    }

    public void verifySimulationsExecuted(List<Class<? extends BaseSimulation>> classes) {
        Mockito.verify(runner).run(classes);
    }

    @Override
    public void close() {
        if (scannerMock != null) {
            scannerMock.close();
        }
        runnerMock.close();
    }
}
