package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationDiscoveryResult;
import com.senthora.gatlingfx.runtime.support.TestSimulations;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClasspathSimulationScannerTest {

    private static SimulationDiscoveryResult result;

    @BeforeAll
    static void setupClasspathSimulationScannerTest() {
        result = ClasspathSimulationScanner.scan();
    }

    @Test
    @DisplayName("Should return supported simulations when annotated GatlingFx simulations exist")
    void should_ReturnSupportedSimulations_when_AnnotatedGatlingFxSimulationsExist() {
        assertThat(result.supported()).contains(
                TestSimulations.SupportedSimulation.class
        );
    }

    @Test
    @DisplayName("Should return unsupported simulations when non-GatlingFx simulations are discovered")
    void should_ReturnUnsupportedSimulations_when_NonGatlingFxSimulationsAreDiscovered() {
        assertThat(result.unsupported()).contains(
                TestSimulations.UnsupportedSimulation.class
        );
    }

    @Test
    @DisplayName("Should exclude non-annotated GatlingFx simulations when discovering simulations")
    void should_ExcludeNonAnnotatedGatlingFxSimulations_when_DiscoveringSimulations() {
        var expected = TestSimulations.NonAnnotatedSimulation.class;

        assertThat(result.supported()).doesNotContain(expected);
        assertThat(result.unsupported()).doesNotContain(expected);
    }

    @Test
    @DisplayName("Should exclude non-annotated non-simulation classes when discovering simulations")
    void should_ExcludeNonAnnotatedClasses_when_DiscoveringSimulations() {
        assertThat(result.unsupported()).doesNotContain(
                TestSimulations.NonSimulation.class
        );
    }
}
