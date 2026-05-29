package com.senthora.gatlingfx.runtime.support;

import com.senthora.gatlingfx.runtime.core.api.*;
import com.senthora.gatlingfx.runtime.core.internal.DefaultSimulationDiscoveryResult;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class MockRuntimeSession implements AutoCloseable {

    private final SimulationRunner runner;
    private final MockedStatic<SimulationRunner> runnerMock;
    private final MockedStatic<SimulationScanner> scannerMock;

    private SimulationRuntimeConfig config;

    private MockRuntimeSession() {
        this.runner = Mockito.mock(SimulationRunner.class);
        this.runnerMock = Mockito.mockStatic(SimulationRunner.class);
        this.scannerMock = Mockito.mockStatic(SimulationScanner.class);

        runnerMock.when(() -> SimulationRunner.create(Mockito.any()))
                .thenAnswer(invocation -> {
                    config = invocation.getArgument(0);
                    return runner;
                });

        scannerMock.when(SimulationScanner::scan)
                .thenReturn(new DefaultSimulationDiscoveryResult(
                        List.of(),
                        List.of()
                ));
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
        scannerMock.when(SimulationScanner::scan).thenReturn(result);
    }

    public void verifySimulationExecuted(Class<? extends BaseSimulation> clazz) {
        Mockito.verify(runner).run(clazz);
    }

    public void verifySimulationsExecuted(List<Class<? extends BaseSimulation>> classes) {
        Mockito.verify(runner).run(classes);
    }

    public void verifyLogLevel(RuntimeLogLevel expected) {
        assertThat(config.logLevel()).isEqualTo(expected);
    }

    public void verifyFailFast(boolean expected) {
        assertThat(config.failFast()).isEqualTo(expected);
    }

    @Override
    public void close() {
        if (scannerMock != null) {
            scannerMock.close();
        }
        runnerMock.close();
    }
}
