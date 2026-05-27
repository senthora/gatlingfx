package com.senthora.gatlingfx.runtime.core.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulationRunIdTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when value is null")
        void should_ThrowNullPointerException_when_ValueIsNull() {
            assertThatThrownBy(() -> new SimulationRunId(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when value is blank")
        void should_ThrowIllegalArgumentException_when_ValueIsBlank() {
            assertThatThrownBy(() -> new SimulationRunId(" "))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should return provided value when value is valid")
        void should_ReturnProvidedValue_when_ValueIsValid() {
            var runId = new SimulationRunId("run-123");

            assertThat(runId.value()).isEqualTo("run-123");
        }
    }

    @Nested
    @DisplayName("create")
    class CreateMethodTests {

        @Test
        @DisplayName("Should return non blank value when create is invoked")
        void should_ReturnNonBlankValue_when_CreateIsInvoked() {
            SimulationRunId runId = SimulationRunId.create();

            assertThat(runId.value()).isNotBlank();
        }
    }
}
