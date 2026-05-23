package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.internal.support.TestSimulations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClasspathSimulationScannerTest {

    @Test
    @DisplayName("Should return annotated simulations when simulations exist")
    void should_ReturnAnnotatedSimulations_when_SimulationsExist() {
        assertThat(ClasspathSimulationScanner.scan())
                .contains(TestSimulations.AnnotatedSimulation.class);
    }

    @Test
    @DisplayName("Should exclude non-annotated classes when scanning classpath")
    void should_ExcludeNonAnnotatedClasses_when_ScanningClasspath() {
        assertThat(ClasspathSimulationScanner.scan())
                .doesNotContain(TestSimulations.StandardSimulation.class);
    }
}
