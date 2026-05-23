package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationResult;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

class DefaultSimulationExecutionResultTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when simulation class is null")
    void should_ThrowNullPointerException_when_SimulationClassIsNull() {
        var thrown = catchThrowable(() -> new DefaultSimulationExecutionResult(
                null,
                SimulationResult.SUCCESS
        ));
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when result is null")
    void should_ThrowNullPointerException_when_ResultIsNull() {
        var thrown = catchThrowable(() -> new DefaultSimulationExecutionResult(
                Object.class,
                null
        ));
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should return provided simulation class when instance is created")
    void should_ReturnProvidedSimulationClass_when_InstanceIsCreated() {
        var result = new DefaultSimulationExecutionResult(
                Object.class,
                SimulationResult.SUCCESS
        );
        assertThat(result.simulationClass()).isEqualTo(Object.class);
    }

    @Test
    @DisplayName("Should return provided simulation result when instance is created")
    void should_ReturnProvidedSimulationResult_when_InstanceIsCreated() {
        var result = new DefaultSimulationExecutionResult(
                Object.class,
                SimulationResult.SUCCESS
        );
        assertThat(result.result()).isEqualTo(SimulationResult.SUCCESS);
    }
}
