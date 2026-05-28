package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.support.TestSimulation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulationContextRegistryTest {

    @Nested
    @DisplayName("get")
    class GetMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when simulation class is null")
        void should_ThrowNullPointerException_when_SimulationClassIsNull() {
            assertThatThrownBy(() -> SimulationContextRegistry.get(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should return empty context when matching context was not registered")
        void should_ReturnEmptyContext_when_MatchingContextWasNotRegistered() {
            assertThat(SimulationContextRegistry.get(TestSimulation.class)).isEmpty();
        }
    }
}
