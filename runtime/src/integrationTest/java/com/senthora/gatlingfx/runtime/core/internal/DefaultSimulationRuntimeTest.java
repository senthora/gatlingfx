package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationExecutionResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationResult;
import com.senthora.gatlingfx.runtime.core.internal.support.FailedSimulation;
import com.senthora.gatlingfx.runtime.core.internal.support.SuccessfulSimulation;
import com.senthora.gatlingfx.support.MockWebServerTest;

import okhttp3.mockwebserver.MockResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultSimulationRuntimeTest extends MockWebServerTest {

    @BeforeEach
    void setupDefaultSimulationRuntimeTests() {
        server.enqueue(new MockResponse().setResponseCode(200));
    }

    @Test
    @DisplayName("Should return successful result when simulation succeeds")
    void should_ReturnSuccessfulResult_when_SimulationSucceeds() {
        var runtime = new DefaultSimulationRuntime();

        assertThat(runtime.execute(List.of(SuccessfulSimulation.class)))
                .singleElement()
                .extracting(SimulationExecutionResult::result)
                .isEqualTo(SimulationResult.SUCCESS);
    }

    @Test
    @DisplayName("Should return failed result when simulation fails")
    void should_ReturnFailedResult_when_SimulationFails() {
        var runtime = new DefaultSimulationRuntime();

        assertThat(runtime.execute(List.of(FailedSimulation.class)))
                .singleElement()
                .extracting(SimulationExecutionResult::result)
                .isEqualTo(SimulationResult.FAILURE);
    }
}
