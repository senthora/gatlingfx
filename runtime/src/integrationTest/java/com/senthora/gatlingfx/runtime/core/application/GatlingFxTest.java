package com.senthora.gatlingfx.runtime.core.application;

import com.senthora.gatlingfx.runtime.core.api.SimulationDiscoveryResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunner;
import com.senthora.gatlingfx.runtime.core.internal.DefaultSimulationDiscoveryResult;
import com.senthora.gatlingfx.runtime.support.MockSimulationRunner;
import com.senthora.gatlingfx.runtime.support.MockSimulationScanner;
import com.senthora.gatlingfx.runtime.support.TestSimulations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GatlingFxTest {

    @Nested
    @DisplayName("simulation")
    class SimulationTests {

        @Test
        @DisplayName("Should return zero exit code when valid simulation argument is supplied")
        void should_ReturnZeroExitCode_when_ValidSimulationArgumentSupplied() {
            var result = Mockito.mock(SimulationRunResult.class);

            Mockito.when(result.success()).thenReturn(true);

            Runnable runnable = () -> {
                var args = new String[]{
                        GatlingFxArguments.SIMULATION,
                        TestSimulations.SuccessfulSimulation.class.getName()
                };
                assertThat(GatlingFx.run(args)).isZero();
            };
            MockSimulationRunner.with(result, runnable);
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
        @DisplayName("Should pass provided simulation to runner when simulation argument supplied")
        void should_PassProvidedSimulationToRunner_when_SimulationArgumentSupplied() {
            var result = Mockito.mock(SimulationRunResult.class);

            Mockito.when(result.success()).thenReturn(true);

            Consumer<SimulationRunner> consumer = runner -> {
                var args = new String[]{
                        GatlingFxArguments.SIMULATION,
                        TestSimulations.SuccessfulSimulation.class.getName()
                };
                GatlingFx.run(args);

                Mockito.verify(runner).run(TestSimulations.SuccessfulSimulation.class);
            };
            MockSimulationRunner.with(result, consumer);
        }
    }

    @Nested
    @DisplayName("discovery")
    class DiscoveryTests {

        @Test
        @DisplayName("Should return zero exit code when unsupported simulations are discovered")
        void should_ReturnZeroExitCode_when_UnsupportedSimulationsAreDiscovered() {
            var result = Mockito.mock(SimulationRunResult.class);

            Mockito.when(result.success()).thenReturn(true);

            var discoveryResult = new DefaultSimulationDiscoveryResult(
                    List.of(TestSimulations.SuccessfulSimulation.class),
                    List.of(Object.class)
            );
            withMockedRuntime(discoveryResult, result, () ->
                    assertThat(GatlingFx.run(new String[0])).isZero()
            );
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

        @Test
        @DisplayName("Should pass supported simulations to runner when supported simulations discovered")
        void should_PassSupportedSimulationsToRunner_when_SupportedSimulationsDiscovered() {
            var result = Mockito.mock(SimulationRunResult.class);

            Mockito.when(result.success()).thenReturn(true);

            var discoveryResult = new DefaultSimulationDiscoveryResult(
                    List.of(TestSimulations.SuccessfulSimulation.class),
                    List.of()
            );
            Consumer<SimulationRunner> consumer = runner -> {
                GatlingFx.run(new String[0]);

                Mockito.verify(runner).run(List.of(
                        TestSimulations.SuccessfulSimulation.class
                ));
            };
            withMockedRuntime(discoveryResult, result, consumer);
        }

        @Test
        @DisplayName("Should exclude unsupported simulations when unsupported simulations discovered")
        void should_ExcludeUnsupportedSimulations_when_UnsupportedSimulationsDiscovered() {
            var result = Mockito.mock(SimulationRunResult.class);

            Mockito.when(result.success()).thenReturn(true);

            var discoveryResult = new DefaultSimulationDiscoveryResult(
                    List.of(TestSimulations.SuccessfulSimulation.class),
                    List.of(Object.class)
            );

            Consumer<SimulationRunner> consumer = runner -> {
                GatlingFx.run(new String[0]);

                Mockito.verify(runner).run(List.of(TestSimulations.SuccessfulSimulation.class));
            };

            withMockedRuntime(discoveryResult, result, consumer);
        }
    }

    @Nested
    @DisplayName("execution")
    class ExecutionTests {

        @Test
        @DisplayName("Should return non-zero exit code when simulation execution fails")
        void should_ReturnNonZeroExitCode_when_SimulationExecutionFails() {
            var result = Mockito.mock(SimulationRunResult.class);

            Mockito.when(result.success()).thenReturn(false);

            var discoveryResult = new DefaultSimulationDiscoveryResult(
                    List.of(TestSimulations.SuccessfulSimulation.class),
                    List.of()
            );
            withMockedRuntime(discoveryResult, result, () ->
                    assertThat(GatlingFx.run(new String[0])).isEqualTo(1)
            );
        }
    }

    private static void withMockedRuntime(
            SimulationDiscoveryResult discoveryResult,
            SimulationRunResult runResult,
            Consumer<SimulationRunner> consumer
    ) {
        Runnable run = () -> MockSimulationRunner.with(runResult, consumer);
        MockSimulationScanner.with(discoveryResult, run);
    }

    private static void withMockedRuntime(
            SimulationDiscoveryResult discoveryResult,
            SimulationRunResult runResult,
            Runnable runnable
    ) {
        Consumer<SimulationRunner> consumer = ignored -> runnable.run();
        withMockedRuntime(discoveryResult, runResult, consumer);
    }
}
