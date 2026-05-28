package com.senthora.gatlingfx.runtime.engine;

import com.senthora.gatlingfx.runtime.engine.support.GatlingFxEngineKit;
import com.senthora.gatlingfx.runtime.support.MockSimulationRunResult;
import com.senthora.gatlingfx.runtime.support.MockSimulationRunner;
import com.senthora.gatlingfx.runtime.support.TestSimulations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class SimulationExecutorTest {

    @Test
    @DisplayName("Should report successful simulations when simulation succeeds")
    void should_ReportSuccessfulSimulations_when_SimulationSucceeds() {
        Runnable run = () -> {
            var results = GatlingFxEngineKit.engine()
                    .select(TestSimulations.SuccessfulSimulation.class)
                    .execute();

            results.testEvents().assertStatistics(stats -> stats
                    .started(1)
                    .succeeded(1)
                    .failed(0));
        };
        MockSimulationRunner.with(MockSimulationRunResult.ALWAYS_SUCCESS, run);
    }

    @Test
    @DisplayName("Should report failed simulations when simulation fails")
    void should_ReportFailedSimulations_when_SimulationFails() {
        Runnable run = () -> {
            var results = GatlingFxEngineKit.engine()
                    .select(TestSimulations.FailedSimulation.class)
                    .execute();

            results.testEvents().assertStatistics(stats -> stats
                    .started(1)
                    .succeeded(0)
                    .failed(1));
        };
        MockSimulationRunner.with(MockSimulationRunResult.ALWAYS_FAIL, run);
    }

    @Test
    @DisplayName("Should continue executing remaining simulations when simulation fails")
    void should_ContinueExecutingRemainingSimulations_when_SimulationFails() {
        Runnable run = () -> {
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
        };
        MockSimulationRunner.with(MockSimulationRunResult.ALWAYS_FAIL, run);
    }
}
