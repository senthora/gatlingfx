package com.senthora.gatlingfx.simulation.backend;

import com.senthora.gatlingfx.runtime.core.api.*;
import com.senthora.gatlingfx.runtime.core.application.LoggingContext;
import com.senthora.gatlingfx.runtime.core.internal.DefaultGatlingRunner;
import com.senthora.gatlingfx.runtime.core.internal.DefaultSimulationRuntime;

import org.jspecify.annotations.NullUnmarked;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@NullUnmarked
class BackendLifecycleTest {

    private static LoggingContext loggingContext;
    private static SimulationRuntime runtime;

    @BeforeAll
    static void setupBackendLifecycleTests() {
        loggingContext = LoggingContext.configure(RuntimeLogLevel.ERROR);

        var runner = new DefaultGatlingRunner();
        var config = SimulationRuntimeConfig.create().build();

        runtime = new DefaultSimulationRuntime(runner, config);
    }

    @BeforeEach
    void setupBackendLifecycleTest() {
        LifecycleRecorder.clear();
    }

    @AfterAll
    static void teardownBackendLifecycleTests() {
        loggingContext.close();
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
