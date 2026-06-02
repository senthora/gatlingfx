package com.senthora.gatlingfx.runtime.support;

import com.senthora.gatlingfx.runtime.core.api.*;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class MockRuntimeSession implements AutoCloseable {

    private final SimulationRunner runner;
    private final SimulationScanner scanner;
    private final MockedStatic<SimulationRunners> runnerMock;
    private final MockedStatic<SimulationScanners> scannerMock;

    private SimulationRuntimeConfig config;

    private MockRuntimeSession() {
        this.runner = Mockito.mock(SimulationRunner.class);
        this.scanner = Mockito.mock(SimulationScanner.class);
        this.runnerMock = Mockito.mockStatic(SimulationRunners.class);
        this.scannerMock = Mockito.mockStatic(SimulationScanners.class);

        runnerMock.when(() -> SimulationRunners.create(Mockito.any()))
                .thenAnswer(invocation -> {
                    config = invocation.getArgument(0);
                    return runner;
                });

        var scanner = Mockito.mock(SimulationScanner.class);

        Mockito.when(scanner.scan())
                .thenReturn(new SimulationDiscoveryResult(
                        List.of(),
                        List.of()
                ));

        scannerMock.when(SimulationScanners::create)
                .thenReturn(scanner);
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
        Mockito.when(scanner.scan()).thenReturn(result);
    }

    public void verifySimulationExecuted(Class<? extends BaseSimulation> clazz) {
        Mockito.verify(runner).run(clazz);
    }

    public void verifySimulationsExecuted(List<Class<? extends BaseSimulation>> classes) {
        Mockito.verify(runner).run(classes);
    }

    public void verifyFailFast(boolean expected) {
        assertThat(config.failFast()).isEqualTo(expected);
    }

    @Override
    public void close() {
        scannerMock.close();
        runnerMock.close();
    }
}
