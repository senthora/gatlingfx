package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationExecutionResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntimeException;
import com.senthora.gatlingfx.runtime.core.internal.support.FailedSimulation;
import com.senthora.gatlingfx.runtime.core.internal.support.SuccessfulSimulation;
import com.senthora.gatlingfx.support.MockWebServerTest;

import okhttp3.mockwebserver.MockResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultSimulationRuntimeTest extends MockWebServerTest {

    @BeforeEach
    void setupDefaultSimulationRuntimeTest() {
        server.enqueue(new MockResponse().setResponseCode(200));
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
        var gatlingRunner = new DefaultGatlingRunner();
        var runtime = new DefaultSimulationRuntime(gatlingRunner);

        List<Class<?>> simulationClasses = List.of(
                FailedSimulation.class,
                SuccessfulSimulation.class
        );
        assertThat(runtime.execute(simulationClasses))
                .extracting(SimulationExecutionResult::result)
                .containsExactly(SimulationResult.FAILURE, SimulationResult.SUCCESS);
    }

    @Test
    @DisplayName("Should return failed result when simulation assertion fails")
    void should_ReturnFailedResult_when_SimulationAssertionFails() {
        var runtime = new DefaultSimulationRuntime((args, gatlingRunner) -> {
            throw new AssertionError();
        });
        assertThat(runtime.execute(List.of(SuccessfulSimulation.class)))
                .singleElement()
                .extracting(SimulationExecutionResult::result)
                .isEqualTo(SimulationResult.FAILURE);
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
}
