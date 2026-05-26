package com.senthora.gatlingfx.runtime.core.application;

import com.senthora.gatlingfx.runtime.core.api.SimulationDiscoveryResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunResult;
import com.senthora.gatlingfx.runtime.core.internal.DefaultSimulationDiscoveryResult;
import com.senthora.gatlingfx.runtime.core.internal.support.SuccessfulSimulation;
import com.senthora.gatlingfx.runtime.support.MockSimulationRunner;
import com.senthora.gatlingfx.runtime.support.MockSimulationScanner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GatlingFxTest {

    @Test
    @DisplayName("Should run provided simulation when simulation argument is supplied")
    void should_RunProvidedSimulation_when_SimulationArgumentSupplied() {
        var result = Mockito.mock(SimulationRunResult.class);

        Mockito.when(result.success()).thenReturn(true);

        Runnable runnable = () -> {
            var args = new String[]{
                    GatlingFxArguments.SIMULATION,
                    SuccessfulSimulation.class.getName()
            };
            assertThat(GatlingFx.run(args)).isZero();
        };
        MockSimulationRunner.with(result, runnable);
    }

    @Test
    @DisplayName("Should run supported discovered simulations when unsupported simulations are discovered")
    void should_RunSupportedDiscoveredSimulations_when_UnsupportedSimulationsAreDiscovered() {
        var result = Mockito.mock(SimulationRunResult.class);

        Mockito.when(result.success()).thenReturn(true);

        var discoveryResult = new DefaultSimulationDiscoveryResult(
                List.of(SuccessfulSimulation.class),
                List.of(Object.class)
        );
        withMockedRuntime(discoveryResult, result, () ->
                assertThat(GatlingFx.run(new String[0])).isZero()
        );
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when simulation class does not exist")
    void should_ThrowIllegalArgumentException_when_SimulationClassDoesNotExist() {
        var result = Mockito.mock(SimulationRunResult.class);

        Mockito.when(result.success()).thenReturn(true);

        Runnable runnable = () -> {
            var args = new String[]{
                    GatlingFxArguments.SIMULATION,
                    "com.example.MissingSimulation"
            };
            assertThatThrownBy(() -> GatlingFx.run(args))
                    .isInstanceOf(IllegalArgumentException.class);
        };
        MockSimulationRunner.with(result, runnable);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when class is not GatlingFx simulation")
    void should_ThrowIllegalArgumentException_when_ClassIsNotGatlingFxSimulation() {
        var result = Mockito.mock(SimulationRunResult.class);

        Mockito.when(result.success()).thenReturn(true);

        Runnable runnable = () -> {
            var args = new String[]{
                    GatlingFxArguments.SIMULATION,
                    Object.class.getName()
            };
            assertThatThrownBy(() -> GatlingFx.run(args))
                    .isInstanceOf(IllegalArgumentException.class);
        };
        MockSimulationRunner.with(result, runnable);
    }

    @Test
    @DisplayName("Should return zero exit code when only unsupported simulations are discovered")
    void should_ReturnZeroExitCode_when_OnlyUnsupportedSimulationsAreDiscovered() {
        var result = Mockito.mock(SimulationRunResult.class);

        Mockito.when(result.success()).thenReturn(true);

        var discoveryResult = new DefaultSimulationDiscoveryResult(
                List.of(),
                List.of(Object.class)
        );
        withMockedRuntime(discoveryResult, result, () ->
                assertThat(GatlingFx.run(new String[0])).isZero()
        );
    }

    @Test
    @DisplayName("Should return non-zero exit code when simulation execution fails")
    void should_ReturnNonZeroExitCode_when_SimulationExecutionFails() {
        var result = Mockito.mock(SimulationRunResult.class);

        Mockito.when(result.success()).thenReturn(false);

        var discoveryResult = new DefaultSimulationDiscoveryResult(
                List.of(SuccessfulSimulation.class),
                List.of()
        );
        withMockedRuntime(discoveryResult, result, () ->
                assertThat(GatlingFx.run(new String[0])).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("Should return zero exit code when no simulations are discovered")
    void should_ReturnZeroExitCode_when_NoSimulationsAreDiscovered() {
        var result = Mockito.mock(SimulationRunResult.class);

        Mockito.when(result.success()).thenReturn(true);

        var discoveryResult = new DefaultSimulationDiscoveryResult(
                List.of(),
                List.of()
        );
        withMockedRuntime(discoveryResult, result, () ->
                assertThat(GatlingFx.run(new String[0])).isZero()
        );
    }

    private static void withMockedRuntime(
            SimulationDiscoveryResult discoveryResult,
            SimulationRunResult runResult,
            Runnable executable
    ) {
        MockSimulationScanner.with(discoveryResult, () ->
                MockSimulationRunner.with(runResult, executable)
        );
    }
}
