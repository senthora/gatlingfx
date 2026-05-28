package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationExecutionResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntimeException;
import com.senthora.gatlingfx.runtime.core.internal.support.FailedSimulation;
import com.senthora.gatlingfx.runtime.core.internal.support.OrderedSimulation;
import com.senthora.gatlingfx.runtime.core.internal.support.SuccessfulSimulation;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.internal.SimulationTestSupport;
import com.senthora.gatlingfx.support.MockWebServerTest;

import okhttp3.mockwebserver.MockResponse;

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
        server.enqueue(new MockResponse().setResponseCode(200));
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
        var gatlingRunner = new DefaultGatlingRunner();
        var runtime = new DefaultSimulationRuntime(gatlingRunner);

        assertThat(runtime.execute(List.of(SuccessfulSimulation.class)))
                .singleElement()
                .extracting(SimulationExecutionResult::result)
                .isEqualTo(SimulationResult.SUCCESS);
    }

    @Test
    @DisplayName("Should return failed result when simulation fails")
    void should_ReturnFailedResult_when_SimulationFails() {
        var gatlingRunner = new DefaultGatlingRunner();
        var runtime = new DefaultSimulationRuntime(gatlingRunner);

        assertThat(runtime.execute(List.of(FailedSimulation.class)))
                .singleElement()
                .extracting(SimulationExecutionResult::result)
                .isEqualTo(SimulationResult.FAILURE);
    }

    @Test
    @DisplayName("Should continue executing remaining simulations when simulation fails")
    void should_ContinueExecutingRemainingSimulations_when_SimulationFails() {
        server.enqueue(new MockResponse().setResponseCode(200));

        var gatlingRunner = new DefaultGatlingRunner();
        var runtime = new DefaultSimulationRuntime(gatlingRunner);

        List<Class<? extends BaseSimulation>> simulationClasses = List.of(
                FailedSimulation.class,
                SuccessfulSimulation.class
        );
        assertThat(runtime.execute(simulationClasses))
                .extracting(SimulationExecutionResult::result)
                .containsExactly(SimulationResult.FAILURE, SimulationResult.SUCCESS);
    }

    @Test
    @DisplayName("Should throw RuntimeException when runtime execution crashes")
    void should_ThrowRuntimeException_when_RuntimeExecutionCrashes() {
        var runtime = new DefaultSimulationRuntime((args, gatlingRunner) -> {
            throw new RuntimeException("boom");
        });
        assertThatThrownBy(() -> runtime.execute(List.of(SuccessfulSimulation.class)))
                .isInstanceOf(SimulationRuntimeException.class);
    }

    @Test
    @DisplayName("Should stop executing remaining simulations when runtime execution crashes")
    void should_StopExecutingRemainingSimulations_when_RuntimeExecutionCrashes() {
        server.enqueue(new MockResponse().setResponseCode(200));
        var executions = new AtomicInteger();

        var runtime = new DefaultSimulationRuntime((args, gatlingRunner) -> {
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
        server.enqueue(new MockResponse().setResponseCode(200));

        var gatlingRunner = new DefaultGatlingRunner();
        var runtime = new DefaultSimulationRuntime(gatlingRunner);

        List<Class<? extends BaseSimulation>> simulationClasses = List.of(
                FirstOrderedSimulation.class,
                SecondOrderedSimulation.class
        );
        runtime.execute(simulationClasses);

        assertThat(OrderedSimulation.executionOrder)
                .containsExactly(1, 2);
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
