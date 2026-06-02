package com.senthora.gatlingfx.runtime.core.application;

import com.senthora.gatlingfx.runtime.core.api.SimulationDiscoveryResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntimeConfig;
import com.senthora.gatlingfx.runtime.support.MockRuntimeSession;
import com.senthora.gatlingfx.runtime.support.TestSimulations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GatlingFxTest {

    @Nested
    @DisplayName("simulation")
    class SimulationTests {

        @Test
        @DisplayName("Should return zero exit code when valid simulation argument is supplied")
        void should_ReturnZeroExitCode_when_ValidSimulationArgumentSupplied() {
            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(true);

                var args = new String[]{
                        GatlingFxArguments.SIMULATION,
                        TestSimulations.SuccessfulSimulation.class.getName()
                };
                assertThat(GatlingFx.run(args)).isZero();
            }
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when simulation class does not exist")
        void should_ThrowIllegalArgumentException_when_SimulationClassDoesNotExist() {
            var args = new String[]{
                    GatlingFxArguments.SIMULATION,
                    "com.example.MissingSimulation"
            };
            assertThatThrownBy(() -> GatlingFx.run(args))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when class is not GatlingFx simulation")
        void should_ThrowIllegalArgumentException_when_ClassIsNotGatlingFxSimulation() {
            var args = new String[]{
                    GatlingFxArguments.SIMULATION,
                    Object.class.getName()
            };
            assertThatThrownBy(() -> GatlingFx.run(args))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should pass provided simulation to runner when simulation argument supplied")
        void should_PassProvidedSimulationToRunner_when_SimulationArgumentSupplied() {
            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(true);

                var args = new String[]{
                        GatlingFxArguments.SIMULATION,
                        TestSimulations.SuccessfulSimulation.class.getName()
                };
                GatlingFx.run(args);

                runtime.verifySimulationExecuted(
                        TestSimulations.SuccessfulSimulation.class
                );
            }
        }
    }

    @Nested
    @DisplayName("discovery")
    class DiscoveryTests {

        @Test
        @DisplayName("Should return zero exit code when unsupported simulations are discovered")
        void should_ReturnZeroExitCode_when_UnsupportedSimulationsAreDiscovered() {
            var discoveryResult = new SimulationDiscoveryResult(
                    List.of(TestSimulations.SuccessfulSimulation.class),
                    List.of(Object.class)
            );
            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(true);
                runtime.stubDiscoveryResult(discoveryResult);

                assertThat(runGatlingFxQuiet()).isZero();
            }
        }

        @Test
        @DisplayName("Should return zero exit code when only unsupported simulations are discovered")
        void should_ReturnZeroExitCode_when_OnlyUnsupportedSimulationsAreDiscovered() {
            var discoveryResult = new SimulationDiscoveryResult(
                    List.of(),
                    List.of(Object.class)
            );
            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(true);
                runtime.stubDiscoveryResult(discoveryResult);

                assertThat(runGatlingFxQuiet()).isZero();
            }
        }

        @Test
        @DisplayName("Should return zero exit code when no simulations are discovered")
        void should_ReturnZeroExitCode_when_NoSimulationsAreDiscovered() {
            var discoveryResult = new SimulationDiscoveryResult(
                    List.of(),
                    List.of()
            );
            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(true);
                runtime.stubDiscoveryResult(discoveryResult);

                assertThat(runGatlingFxQuiet()).isZero();
            }
        }

        @Test
        @DisplayName("Should pass supported simulations to runner when supported simulations discovered")
        void should_PassSupportedSimulationsToRunner_when_SupportedSimulationsDiscovered() {
            var discoveryResult = new SimulationDiscoveryResult(
                    List.of(TestSimulations.SuccessfulSimulation.class),
                    List.of()
            );
            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(true);
                runtime.stubDiscoveryResult(discoveryResult);

                assertThat(runGatlingFxQuiet()).isZero();

                runtime.verifySimulationsExecuted(List.of(
                        TestSimulations.SuccessfulSimulation.class
                ));
            }
        }

        @Test
        @DisplayName("Should exclude unsupported simulations when unsupported simulations discovered")
        void should_ExcludeUnsupportedSimulations_when_UnsupportedSimulationsDiscovered() {
            var discoveryResult = new SimulationDiscoveryResult(
                    List.of(TestSimulations.SuccessfulSimulation.class),
                    List.of(Object.class)
            );
            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(true);
                runtime.stubDiscoveryResult(discoveryResult);

                assertThat(runGatlingFxQuiet()).isZero();

                runtime.verifySimulationsExecuted(List.of(
                        TestSimulations.SuccessfulSimulation.class
                ));
            }
        }
    }

    @Nested
    @DisplayName("execution")
    class ExecutionTests {

        @Test
        @DisplayName("Should return non-zero exit code when simulation execution fails")
        void should_ReturnNonZeroExitCode_when_SimulationExecutionFails() {
            var discoveryResult = new SimulationDiscoveryResult(
                    List.of(TestSimulations.SuccessfulSimulation.class),
                    List.of()
            );
            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(false);
                runtime.stubDiscoveryResult(discoveryResult);

                assertThat(runGatlingFxQuiet()).isEqualTo(1);
            }
        }
    }

    @Nested
    @DisplayName("configuration")
    class ConfigurationTests {

        @Test
        @DisplayName("Should enable fail-fast when fail-fast argument supplied")
        void should_EnableFailFast_when_FailFastArgumentSupplied() {
            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(true);

                runGatlingFxQuiet(new String[]{
                        GatlingFxArguments.FAIL_FAST
                });
                runtime.verifyFailFast(true);
            }
        }

        @Test
        @DisplayName("Should configure default fail-fast when fail-fast argument not supplied")
        void should_ConfigureDefaultFailFast_when_FailFastArgumentNotSupplied() {
            var defaultConfig = SimulationRuntimeConfig.create().build();
            var defaultFailFast = defaultConfig.failFast();

            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(true);

                runGatlingFxQuiet();

                runtime.verifyFailFast(defaultFailFast);
            }
        }
    }

    @Nested
    @DisplayName("logging")
    class LoggingTests {

        @Test
        @DisplayName("Should suppress logs when quiet argument supplied")
        void should_SuppressInfoLogs_when_QuietArgumentSupplied() {
            var discoveryResult = new SimulationDiscoveryResult(
                    List.of(),
                    List.of()
            );
            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(true);
                runtime.stubDiscoveryResult(discoveryResult);

                var output = captureStdOut(() -> {
                    var args = new String[]{
                            GatlingFxArguments.QUIET
                    };
                    GatlingFx.run(args);
                });
                assertThat(output).isEmpty();
            }
        }

        @Test
        @DisplayName("Should emit logs when quiet argument not supplied")
        void should_EmitLogs_when_QuietArgumentNotSupplied() {
            var discoveryResult = new SimulationDiscoveryResult(
                    List.of(),
                    List.of()
            );
            try (var runtime = MockRuntimeSession.create()) {
                runtime.stubExecutionResult(true);
                runtime.stubDiscoveryResult(discoveryResult);

                var output = captureStdOut(() ->
                        GatlingFx.run(new String[0])
                );
                assertThat(output).isNotEmpty();
            }
        }

        private static String captureStdOut(Runnable runnable) {
            var output = new ByteArrayOutputStream();

            var previous = System.out;

            try (var stream = new PrintStream(output)) {
                System.setOut(stream);

                runnable.run();

                stream.flush();

                return output.toString(StandardCharsets.UTF_8);
            }
            finally {
                System.setOut(previous);
            }
        }
    }

    private static int runGatlingFxQuiet(String[] args) {
        var previous = System.out;

        try (var stream = new PrintStream(OutputStream.nullOutputStream())) {
            System.setOut(stream);

            return GatlingFx.run(args);
        }
        finally {
            System.setOut(previous);
        }
    }

    private static int runGatlingFxQuiet() {
        return runGatlingFxQuiet(new String[0]);
    }
}
