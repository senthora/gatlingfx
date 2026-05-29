package com.senthora.gatlingfx.simulation.backend;

import com.senthora.gatlingfx.runtime.core.api.*;
import com.senthora.gatlingfx.runtime.core.internal.DefaultGatlingRunner;
import com.senthora.gatlingfx.runtime.core.internal.DefaultSimulationRuntime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BackendLifecycleTest {

    private SimulationRuntime runtime;

    @BeforeEach
    void setupBackendLifecycleTest() {
        var runner = new DefaultGatlingRunner();
        var config = SimulationRuntimeConfig.create()
                .withLogLevel(RuntimeLogLevel.ERROR)
                .build();

        runtime = new DefaultSimulationRuntime(runner, config);

        LifecycleRecorder.clear();
    }

    @Test
    @DisplayName("Should execute backend lifecycle in correct order when simulation succeeds")
    void should_ExecuteBackendLifecycleInCorrectOrder_when_SimulationSucceeds() {
        var result = runtime.execute(List.of(
                RecordedSuccessfulTeaOrderSimulation.class
        ));
        assertThat(result)
                .singleElement()
                .extracting(SimulationExecutionResult::result)
                .isEqualTo(SimulationResult.SUCCESS);

        assertThat(LifecycleRecorder.events()).containsExactly(
                LifecycleRecorder.Event.SETUP,
                LifecycleRecorder.Event.REQUEST,
                LifecycleRecorder.Event.VERIFY,
                LifecycleRecorder.Event.TEARDOWN
        );
    }

    @Test
    @DisplayName("Should execute backend teardown when simulation execution fails")
    void should_ExecuteBackendTeardown_when_SimulationExecutionFails() {
        var result = runtime.execute(List.of(
                RecordedFailedTeaOrderSimulation.class
        ));
        assertThat(result)
                .singleElement()
                .extracting(SimulationExecutionResult::result)
                .isEqualTo(SimulationResult.FAILURE);

        assertThat(LifecycleRecorder.events())
                .contains(LifecycleRecorder.Event.TEARDOWN);
    }

    @Test
    @DisplayName("Should execute backend teardown when simulation verification fails")
    void should_ExecuteBackendTeardown_when_SimulationVerificationFails() {
        var result = runtime.execute(List.of(
                RecordedInvalidTeaOrderSimulation.class
        ));
        assertThat(result)
                .singleElement()
                .extracting(SimulationExecutionResult::result)
                .isEqualTo(SimulationResult.FAILURE);

        assertThat(LifecycleRecorder.events()).containsExactly(
                LifecycleRecorder.Event.SETUP,
                LifecycleRecorder.Event.REQUEST,
                LifecycleRecorder.Event.VERIFY,
                LifecycleRecorder.Event.TEARDOWN
        );
    }
}
