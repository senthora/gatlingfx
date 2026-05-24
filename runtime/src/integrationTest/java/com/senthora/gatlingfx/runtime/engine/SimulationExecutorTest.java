package com.senthora.gatlingfx.runtime.engine;

import com.senthora.gatlingfx.runtime.engine.support.GatlingFxEngineKit;
import com.senthora.gatlingfx.runtime.support.MockSimulationRunResult;
import com.senthora.gatlingfx.runtime.support.MockSimulationRunner;
import com.senthora.gatlingfx.runtime.engine.support.TestSimulations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class SimulationExecutorTest {

    @Test
    @DisplayName("Should report successful simulations when simulation succeeds")
    void should_ReportSuccessfulSimulations_when_SimulationSucceeds() {
        MockSimulationRunner.with(MockSimulationRunResult.ALWAYS_SUCCESS, () -> {
            var results = GatlingFxEngineKit.engine()
                    .select(TestSimulations.SuccessfulSimulation.class)
                    .execute();

            results.testEvents().assertStatistics(stats -> stats
                    .started(1)
                    .succeeded(1)
                    .failed(0));
        });
    }

    @Test
    @DisplayName("Should report failed simulations when simulation fails")
    void should_ReportFailedSimulations_when_SimulationFails() {
        MockSimulationRunner.with(MockSimulationRunResult.ALWAYS_FAIL, () -> {
            var results = GatlingFxEngineKit.engine()
                    .select(TestSimulations.FailedSimulation.class)
                    .execute();

            results.testEvents().assertStatistics(stats -> stats
                    .started(1)
                    .succeeded(0)
                    .failed(1));
        });
    }

    @Test
    @DisplayName("Should continue executing remaining simulations when simulation fails")
    void should_ContinueExecutingRemainingSimulations_when_SimulationFails() {
        MockSimulationRunner.with(MockSimulationRunResult.ALWAYS_FAIL, () -> {
            List<Class<?>> selection = List.of(
                    TestSimulations.FailedSimulation.class,
                    TestSimulations.SuccessfulSimulation.class
            );
            var results = GatlingFxEngineKit.engine()
                    .select(selection)
                    .execute();

            results.testEvents().assertStatistics(stats -> stats
                    .started(2)
                    .succeeded(0)
                    .failed(2));
        });
    }
}
