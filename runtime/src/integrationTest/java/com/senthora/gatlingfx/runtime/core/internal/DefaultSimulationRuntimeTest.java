package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.*;
import com.senthora.gatlingfx.runtime.core.internal.support.FailedSimulation;
import com.senthora.gatlingfx.runtime.core.internal.support.OrderedSimulation;
import com.senthora.gatlingfx.runtime.core.internal.support.SimulationRuntimeBuilder;
import com.senthora.gatlingfx.runtime.core.internal.support.SuccessfulSimulation;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.internal.SimulationTestSupport;
import com.senthora.gatlingfx.support.MockWebServerTest;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultSimulationRuntimeTest extends MockWebServerTest {

    @BeforeEach
    void setupDefaultSimulationRuntimeTest() {
        enqueueOkResponse();
    }

    @AfterEach
    void teardownDefaultSimulationRuntimeTest() {
        OrderedSimulation.executionOrder.clear();

        SimulationTestSupport.resetListenerRegistry();

        SimulationContextRegistry.reset();
        SimulationContextRegistry.initialize();
    }

    @Nested
    @DisplayName("failure")
    class FailureTests {

        @Test
        @DisplayName("Should throw RuntimeException when runtime execution crashes")
        void should_ThrowRuntimeException_when_RuntimeExecutionCrashes() {
            GatlingRunner runner = (args, gatlingRunner) -> {
                throw new RuntimeException("boom");
            };
            SimulationRuntime runtime = simulationRuntime()
                    .withLogLevel(RuntimeLogLevel.OFF)
                    .withGatlingRunner(runner)
                    .build();

            assertThatThrownBy(() -> runtime.execute(List.of(SuccessfulSimulation.class)))
                    .isInstanceOf(SimulationRuntimeException.class);
        }

        @Test
        @DisplayName("Should stop executing remaining simulations when runtime execution crashes")
        void should_StopExecutingRemainingSimulations_when_RuntimeExecutionCrashes() {
            enqueueOkResponse();
            var executions = new AtomicInteger();

            GatlingRunner runner = (args, gatlingRunner) -> {
                if (executions.incrementAndGet() == 2) {
                    throw new RuntimeException("boom");
                }
                return gatlingRunner.run();
            };
            SimulationRuntime runtime = simulationRuntime()
                    .withLogLevel(RuntimeLogLevel.OFF)
                    .withGatlingRunner(runner)
                    .build();

            List<Class<? extends BaseSimulation>> simulationClasses = List.of(
                    OrderedSimulation.First.class,
                    OrderedSimulation.Second.class
            );
            assertThatThrownBy(() -> runtime.execute(simulationClasses))
                    .isInstanceOf(SimulationRuntimeException.class);

            assertThat(OrderedSimulation.executionOrder)
                    .containsExactly(1);
        }
    }

    @Nested
    @DisplayName("execution")
    class ExecutionTests {

        @Test
        @DisplayName("Should return successful result when simulation succeeds")
        void should_ReturnSuccessfulResult_when_SimulationSucceeds() {
            assertThat(simulationRuntime().build().execute(List.of(SuccessfulSimulation.class)))
                    .singleElement()
                    .extracting(SimulationExecutionResult::result)
                    .isEqualTo(SimulationResult.SUCCESS);
        }

        @Test
        @DisplayName("Should return failed result when simulation fails")
        void should_ReturnFailedResult_when_SimulationFails() {
            assertThat(simulationRuntime().build().execute(List.of(FailedSimulation.class)))
                    .singleElement()
                    .extracting(SimulationExecutionResult::result)
                    .isEqualTo(SimulationResult.FAILURE);
        }

        @Test
        @DisplayName("Should continue executing remaining simulations when simulation fails")
        void should_ContinueExecutingRemainingSimulations_when_SimulationFails() {
            enqueueOkResponse();

            List<Class<? extends BaseSimulation>> simulationClasses = List.of(
                    FailedSimulation.class,
                    SuccessfulSimulation.class
            );
            assertThat(simulationRuntime().build().execute(simulationClasses))
                    .extracting(SimulationExecutionResult::result)
                    .containsExactly(SimulationResult.FAILURE, SimulationResult.SUCCESS);
        }

        @Test
        @DisplayName("Should execute simulations in provided order when executing multiple simulations")
        void should_ExecuteSimulationsInProvidedOrder_when_ExecutingMultipleSimulations() {
            enqueueOkResponse();

            List<Class<? extends BaseSimulation>> simulationClasses = List.of(
                    OrderedSimulation.First.class,
                    OrderedSimulation.Second.class
            );
            simulationRuntime().build().execute(simulationClasses);

            assertThat(OrderedSimulation.executionOrder)
                    .containsExactly(1, 2);
        }
    }

    @Nested
    @DisplayName("configuration")
    class ConfigurationTests {

        @Test
        @DisplayName("Should stop executing remaining simulations when fail-fast enabled")
        void should_StopExecutingRemainingSimulations_when_FailFastEnabled() {
            enqueueOkResponse();

            List<Class<? extends BaseSimulation>> simulationClasses = List.of(
                    FailedSimulation.class,
                    OrderedSimulation.First.class
            );
            simulationRuntime()
                    .withFailFast(true)
                    .build()
                    .execute(simulationClasses);

            assertThat(OrderedSimulation.executionOrder).isEmpty();
        }

        @Test
        @DisplayName("Should continue executing remaining simulations when fail-fast disabled")
        void should_ContinueExecutingRemainingSimulations_when_FailFastDisabled() {
            enqueueOkResponse();

            List<Class<? extends BaseSimulation>> simulationClasses = List.of(
                    FailedSimulation.class,
                    OrderedSimulation.First.class
            );
            simulationRuntime()
                    .withFailFast(false)
                    .build()
                    .execute(simulationClasses);

            assertThat(OrderedSimulation.executionOrder)
                    .containsExactly(1);
        }

        @Test
        @DisplayName("Should suppress runtime logs when log level is off")
        void should_SuppressRuntimeLogs_when_LogLevelIsOff() {
            var runtime = simulationRuntime()
                    .withLogLevel(RuntimeLogLevel.OFF)
                    .build();

            var output = captureStdOut(() ->
                    runtime.execute(List.of(SuccessfulSimulation.class))
            );
            assertThat(output).isEmpty();
        }

        @Test
        @DisplayName("Should emit runtime logs when log level is not off")
        void should_EmitRuntimeLogs_when_LogLevelIsNotOff() {
            var runtime = simulationRuntime()
                    .withLogLevel(RuntimeLogLevel.INFO)
                    .build();

            var output = captureStdOut(() ->
                    runtime.execute(List.of(SuccessfulSimulation.class))
            );
            assertThat(output).isNotEmpty();
        }
    }

    private static SimulationRuntimeBuilder simulationRuntime() {
        return new SimulationRuntimeBuilder();
    }

    private static String captureStdOut(Runnable runnable) {
        var output = new ByteArrayOutputStream();
        var stream = new PrintStream(output);

        var previous = System.out;

        try {
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
