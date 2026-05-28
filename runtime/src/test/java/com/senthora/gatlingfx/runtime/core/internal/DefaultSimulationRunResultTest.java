package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationResult;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultSimulationRunResultTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when results are null")
        void should_ThrowNullPointerException_when_ResultsAreNull() {
            assertThatThrownBy(() -> new DefaultSimulationRunResult(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("simulations")
    class SimulationsMethodTests {

        @Test
        @DisplayName("Should return provided simulation results when instance is created")
        void should_ReturnProvidedSimulationResults_when_InstanceIsCreated() {
            var result = new DefaultSimulationExecutionResult(
                    Object.class,
                    SimulationResult.SUCCESS
            );
            var runResult = new DefaultSimulationRunResult(
                    List.of(result)
            );
            assertThat(runResult.simulations()).containsExactly(result);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should return immutable simulation results when instance is created")
        void should_ReturnImmutableSimulationResults_when_InstanceIsCreated() {
            var result = new DefaultSimulationExecutionResult(
                    Object.class,
                    SimulationResult.SUCCESS
            );
            var runResult = new DefaultSimulationRunResult(
                    List.of(result)
            );
            assertThatThrownBy(() -> runResult.simulations().add(result))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("success")
    class SuccessMethodTests {

        @Test
        @DisplayName("Should return true when all simulations succeed")
        void should_ReturnTrue_when_AllSimulationsSucceed() {
            var result = new DefaultSimulationExecutionResult(
                    Object.class,
                    SimulationResult.SUCCESS
            );
            var runResult = new DefaultSimulationRunResult(
                    List.of(result)
            );
            assertThat(runResult.success()).isTrue();
        }

        @Test
        @DisplayName("Should return false when any simulation fails")
        void should_ReturnFalse_when_AnySimulationFails() {
            var result = new DefaultSimulationExecutionResult(
                    Object.class,
                    SimulationResult.FAILURE
            );
            var runResult = new DefaultSimulationRunResult(
                    List.of(result)
            );
            assertThat(runResult.success()).isFalse();
        }
    }
}
