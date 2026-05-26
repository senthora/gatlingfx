package com.senthora.gatlingfx.runtime.engine;

import com.senthora.gatlingfx.runtime.engine.support.GatlingFxEngineKit;
import com.senthora.gatlingfx.runtime.engine.support.TestSimulations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestDescriptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimulationDiscoveryTest {

    @Test
    @DisplayName("Should expose descriptors when annotated simulations are discovered")
    void should_ExposeDescriptors_when_AnnotatedSimulationsAreDiscovered() {
        var descriptor = GatlingFxEngineKit.engine()
                .select(TestSimulations.SuccessfulSimulation.class)
                .discover()
                .getEngineDescriptor();

        assertThat(descriptor.getChildren())
                .extracting(TestDescriptor::getDisplayName)
                .containsExactly("SuccessfulSimulation");
    }

    @Test
    @DisplayName("Should ignore non-annotated classes when class selectors are provided")
    void should_IgnoreNonAnnotatedClasses_when_ClassSelectorsAreProvided() {
        List<Class<?>> selection = List.of(
                TestSimulations.SuccessfulSimulation.class,
                TestSimulations.NonAnnotatedSimulation.class
        );
        var descriptor = GatlingFxEngineKit.engine()
                .select(selection)
                .discover()
                .getEngineDescriptor();

        assertThat(descriptor.getChildren())
                .extracting(TestDescriptor::getDisplayName)
                .containsExactly("SuccessfulSimulation");
    }

    @Test
    @DisplayName("Should discover selected simulations when class selectors are provided")
    void should_DiscoverSelectedSimulations_when_ClassSelectorsAreProvided() {
        List<Class<?>> selection = List.of(
                TestSimulations.SuccessfulSimulation.class,
                TestSimulations.VerySuccessfulSimulation.class
        );
        var descriptor = GatlingFxEngineKit.engine()
                .select(selection)
                .discover()
                .getEngineDescriptor();

        assertThat(descriptor.getChildren())
                .extracting(TestDescriptor::getDisplayName)
                .containsExactly("SuccessfulSimulation", "VerySuccessfulSimulation");
    }

    @Test
    @DisplayName("Should fallback to classpath scanning when class selectors are absent")
    void should_FallbackToClasspathScanning_when_ClassSelectorsAreAbsent() {
        var descriptor = GatlingFxEngineKit.engine()
                .discover()
                .getEngineDescriptor();

        assertThat(descriptor.getChildren()).isNotEmpty();
    }

    @Test
    @DisplayName("Should ignore non-GatlingFx simulations when discovered")
    void should_IgnoreNonGatlingFxSimulations_when_Discovered() {
        List<Class<?>> selection = List.of(
                TestSimulations.SuccessfulSimulation.class,
                TestSimulations.NonSimulation.class
        );
        var descriptor = GatlingFxEngineKit.engine()
                .select(selection)
                .discover()
                .getEngineDescriptor();

        assertThat(descriptor.getChildren())
                .extracting(TestDescriptor::getDisplayName)
                .containsExactly("SuccessfulSimulation");
    }
}
