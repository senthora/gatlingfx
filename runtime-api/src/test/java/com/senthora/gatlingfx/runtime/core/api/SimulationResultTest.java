package com.senthora.gatlingfx.runtime.core.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimulationResultTest {

    @Nested
    @DisplayName("isSuccess")
    class IsSuccessMethodTests {

        @Test
        @DisplayName("Returns true when the result is success")
        void should_ReturnTrue_when_ResultIsSuccess() {
            Assertions.assertThat(SimulationResult.SUCCESS.isSuccess()).isTrue();
        }

        @Test
        @DisplayName("Returns false when the result is failure")
        void should_ReturnFalse_when_ResultIsFailure() {
            Assertions.assertThat(SimulationResult.FAILURE.isSuccess()).isFalse();
        }
    }

    @Nested
    @DisplayName("isFailure")
    class IsFailureMethodTests {

        @Test
        @DisplayName("Returns true when the result is failure")
        void should_ReturnTrue_when_ResultIsFailure() {
            Assertions.assertThat(SimulationResult.FAILURE.isFailure()).isTrue();
        }

        @Test
        @DisplayName("Returns false when the result is success")
        void should_ReturnFalse_when_ResultIsSuccess() {
            Assertions.assertThat(SimulationResult.SUCCESS.isFailure()).isFalse();
        }
    }
}
