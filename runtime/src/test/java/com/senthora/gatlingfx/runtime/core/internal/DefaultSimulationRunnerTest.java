package com.senthora.gatlingfx.runtime.core.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultSimulationRunnerTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when runtime is null")
    void should_ThrowNullPointerException_when_RuntimeIsNull() {
        assertThatThrownBy(() -> new DefaultSimulationRunner(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should return empty successful result when no simulations are provided")
    void should_ReturnEmptySuccessfulResult_when_NoSimulationsAreProvided() {
        var result = new DefaultSimulationRunResult(
                List.of()
        );
        assertThat(result.simulations()).isEmpty();
        assertThat(result.success()).isTrue();
    }
}
