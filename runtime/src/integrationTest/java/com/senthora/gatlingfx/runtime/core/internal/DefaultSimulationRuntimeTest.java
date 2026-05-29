package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.*;
import com.senthora.gatlingfx.runtime.core.internal.support.FailedSimulation;
import com.senthora.gatlingfx.runtime.core.internal.support.OrderedSimulation;
import com.senthora.gatlingfx.runtime.core.internal.support.SuccessfulSimulation;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.internal.SimulationTestSupport;
import com.senthora.gatlingfx.support.MockWebServerTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @Test
    @DisplayName("Should return successful result when simulation succeeds")
    void should_ReturnSuccessfulResult_when_SimulationSucceeds() {
        assertThat(simulationRuntime().execute(List.of(SuccessfulSimulation.class)))
                .singleElement()
                .extracting(SimulationExecutionResult::result)
                .isEqualTo(SimulationResult.SUCCESS);
    }

    @Test
    @DisplayName("Should return failed result when simulation fails")
    void should_ReturnFailedResult_when_SimulationFails() {
        assertThat(simulationRuntime().execute(List.of(FailedSimulation.class)))
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
        assertThat(simulationRuntime().execute(simulationClasses))
                .extracting(SimulationExecutionResult::result)
                .containsExactly(SimulationResult.FAILURE, SimulationResult.SUCCESS);
    }

    @Test
    @DisplayName("Should throw RuntimeException when runtime execution crashes")
    void should_ThrowRuntimeException_when_RuntimeExecutionCrashes() {
        var runtime = simulationRuntime(RuntimeLogLevel.OFF, (args, gatlingRunner) -> {
            throw new RuntimeException("boom");
        });
        assertThatThrownBy(() -> runtime.execute(List.of(SuccessfulSimulation.class)))
                .isInstanceOf(SimulationRuntimeException.class);
    }

    @Test
    @DisplayName("Should stop executing remaining simulations when runtime execution crashes")
    void should_StopExecutingRemainingSimulations_when_RuntimeExecutionCrashes() {
        enqueueOkResponse();
        var executions = new AtomicInteger();

        var runtime = simulationRuntime(RuntimeLogLevel.OFF, (args, gatlingRunner) -> {
            if (executions.incrementAndGet() == 2) {
                throw new RuntimeException("boom");
            }
            return gatlingRunner.run();
        });
        List<Class<? extends BaseSimulation>> simulationClasses = List.of(
                FirstOrderedSimulation.class,
                SecondOrderedSimulation.class
        );
        assertThatThrownBy(() -> runtime.execute(simulationClasses))
                .isInstanceOf(SimulationRuntimeException.class);

        assertThat(OrderedSimulation.executionOrder)
                .containsExactly(1);
    }

    @Test
    @DisplayName("Should execute simulations in provided order when executing multiple simulations")
    void should_ExecuteSimulationsInProvidedOrder_when_ExecutingMultipleSimulations() {
        enqueueOkResponse();

        List<Class<? extends BaseSimulation>> simulationClasses = List.of(
                FirstOrderedSimulation.class,
                SecondOrderedSimulation.class
        );
        simulationRuntime().execute(simulationClasses);

        assertThat(OrderedSimulation.executionOrder)
                .containsExactly(1, 2);
    }

    private static SimulationRuntime simulationRuntime(
            RuntimeLogLevel logLevel,
            GatlingRunner gatlingRunner

    ) {
        var config = SimulationRuntimeConfig.create()
                .withLogLevel(logLevel)
                .build();

        return new DefaultSimulationRuntime(gatlingRunner, config);
    }

    private static SimulationRuntime simulationRuntime(GatlingRunner gatlingRunner) {
        return simulationRuntime(RuntimeLogLevel.ERROR, gatlingRunner);
    }

    private static SimulationRuntime simulationRuntime() {
        return simulationRuntime(new DefaultGatlingRunner());
    }

    public static final class FirstOrderedSimulation extends OrderedSimulation {

        @Override
        protected int orderNumber() {
            return 1;
        }
    }

    public static final class SecondOrderedSimulation extends OrderedSimulation {

        @Override
        protected int orderNumber() {
            return 2;
        }
    }
}
