package com.senthora.gatlingfx.runtime.engine;

import com.senthora.gatlingfx.runtime.support.NamedTestSimulations;
import com.senthora.gatlingfx.support.TestSimulation;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;

import static org.assertj.core.api.Assertions.assertThat;

class SimulationDescriptorTest {

    @Test
    @DisplayName("Should return simulation class when class is requested")
    void should_ReturnSimulationClass_when_SimulationClassRequested() {
        var descriptor = descriptorFor(TestSimulation.class);
        var simulationClass = descriptor.simulationClass();

        assertThat(simulationClass).isEqualTo(TestSimulation.class);
    }

    @Test
    @DisplayName("Should return test type when it is requested")
    void should_ReturnTestType_when_TypeRequested() {
        var descriptor = descriptorFor(TestSimulation.class);
        var type = descriptor.getType();

        assertThat(type).isEqualTo(TestDescriptor.Type.TEST);
    }

    @Test
    @DisplayName("Should use simple class name when display name is missing")
    void should_UseSimpleClassName_when_DisplayNameAnnotationMissing() {
        var descriptor = descriptorFor(TestSimulation.class);
        var displayName = descriptor.getDisplayName();

        assertThat(displayName).isEqualTo("TestSimulation");
    }

    @Test
    @DisplayName("Should append custom display name to simulation class name")
    void should_AppendDisplayName_when_DisplayNameAnnotationPresent() {
        var descriptor = descriptorFor(
                NamedTestSimulations.HelloWorldSimulation.class
        );
        assertThat(descriptor.getDisplayName())
                .contains("HelloWorldSimulation")
                .contains("hello world");
    }

    @Test
    @DisplayName("Should preserve whitespace in custom display name")
    void should_PreserveDisplayNameFormatting_when_DisplayNameContainsWhitespace() {
        var descriptor = descriptorFor(
                NamedTestSimulations.DefinitelyNotTrimmedSimulation.class
        );
        assertThat(descriptor.getDisplayName())
                .contains("DefinitelyNotTrimmedSimulation")
                .contains("   not trimmed   ");
    }

    @Test
    @DisplayName("Should store descriptor unique identifier when it is created")
    void should_StoreUniqueId_when_DescriptorCreated() {
        var id = UniqueId.forEngine("gatlingfx").append(
                "simulation",
                "com.example.TestSimulation"
        );
        var descriptor = new SimulationDescriptor(id, TestSimulation.class);

        assertThat(descriptor.getUniqueId()).isEqualTo(id);
    }

    private static SimulationDescriptor descriptorFor(Class<? extends BaseSimulation> clazz) {
        return new SimulationDescriptor(UniqueId.forEngine("gatlingfx"), clazz);
    }
}
