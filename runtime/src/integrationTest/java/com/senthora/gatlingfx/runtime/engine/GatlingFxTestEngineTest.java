package com.senthora.gatlingfx.runtime.engine;

import com.senthora.gatlingfx.runtime.engine.support.GatlingFxEngineKit;
import com.senthora.gatlingfx.runtime.support.MockSimulationRunResult;
import com.senthora.gatlingfx.runtime.support.MockSimulationRunner;
import com.senthora.gatlingfx.runtime.support.TestSimulations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestDescriptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GatlingFxTestEngineTest {

    @Test
    @DisplayName("Should expose simulation descriptors when simulations are discovered")
    void should_ExposeSimulationDescriptors_when_SimulationsAreDiscovered() {
        var descriptor = GatlingFxEngineKit.engine()
                .discover()
                .getEngineDescriptor();

        var simulations = descriptor.getChildren();

        assertThat(simulations.iterator().next().getType())
                .isEqualTo(TestDescriptor.Type.TEST);
    }

    @Test
    @DisplayName("Should report successful simulation when simulation succeeds")
    void should_ReportSuccessfulSimulation_when_SimulationSucceeds() {
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
    @DisplayName("Should report failed simulation when simulation fails")
    void should_ReportFailedSimulation_when_SimulationFails() {
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
    @DisplayName("Should continue executing remaining simulations after simulation failure")
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
