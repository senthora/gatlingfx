package com.senthora.gatlingfx.runtime.core.application;

import com.senthora.gatlingfx.runtime.core.api.SimulationRunResult;
import com.senthora.gatlingfx.runtime.core.internal.support.SuccessfulSimulation;
import com.senthora.gatlingfx.runtime.support.MockSimulationRunner;
import com.senthora.gatlingfx.runtime.support.MockSimulationScanner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GatlingFxTest {

    @Test
    @DisplayName("Should run provided simulation when simulation argument is supplied")
    void should_RunProvidedSimulation_when_SimulationArgumentSupplied() {
        var result = Mockito.mock(SimulationRunResult.class);

        Mockito.when(result.success()).thenReturn(true);

        MockSimulationRunner.with(result, () -> {
            var args = new String[] {
                    GatlingFxArguments.SIMULATION,
                    SuccessfulSimulation.class.getName()
            };
            assertThat(GatlingFx.run(args)).isZero();
        });
    }

    @Test
    @DisplayName("Should run all discovered simulations when no arguments are supplied")
    void should_RunAllDiscoveredSimulations_when_NoArgumentsAreSupplied() {
        var result = Mockito.mock(SimulationRunResult.class);

        Mockito.when(result.success()).thenReturn(true);

        var simulationClasses = List.of(
                FirstSimulation.class,
                SecondSimulation.class
        );
        MockSimulationScanner.with(simulationClasses, () ->
            MockSimulationRunner.with(result, () ->
                assertThat(GatlingFx.run(new String[0])).isZero()
            )
        );
    }

    static class FirstSimulation {}
    static class SecondSimulation {}
}
