package com.senthora.gatlingfx.simulation.internal;

import com.senthora.gatlingfx.simulation.api.SimulationContext;
import com.senthora.gatlingfx.support.TestSimulation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultSimulationContextTest {

    @Nested
    @DisplayName("create")
    class CreateMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when simulation class is null")
        void should_ThrowNullPointerException_when_SimulationClassIsNull() {
            assertThatThrownBy(() -> SimulationContext.create(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should return simulation class when context is created")
        void should_ReturnSimulationClass_when_ContextIsCreated() {
            var expected = TestSimulation.class;
            var context = SimulationContext.create(expected);

            assertThat(context.simulationClass()).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("failure")
    class FailureMethodTests {

        @Test
        @DisplayName("Should return no failure when failure was not set")
        void should_ReturnNoFailure_when_FailureWasNotSet() {
            var context = SimulationContext.create(TestSimulation.class);

            assertThat(context.failure()).isEmpty();
        }

        @Test
        @DisplayName("Should return failure when failure was set")
        void should_ReturnFailure_when_FailureWasSet() {
            var context = SimulationContext.create(TestSimulation.class);
            var expected = new RuntimeException();

            context.setFailure(expected);

            assertThat(context.failure()).contains(expected);
        }
    }

    @Nested
    @DisplayName("hasFailed")
    class HasFailedMethodTests {

        @Test
        @DisplayName("Should return false when failure was not set")
        void should_ReturnFalse_when_FailureWasNotSet() {
            var context = SimulationContext.create(TestSimulation.class);

            assertThat(context.hasFailed()).isFalse();
        }

        @Test
        @DisplayName("Should return true when failure was set")
        void should_ReturnTrue_when_FailureWasSet() {
            var context = SimulationContext.create(TestSimulation.class);

            context.setFailure(new RuntimeException());

            assertThat(context.hasFailed()).isTrue();
        }
    }

    @Nested
    @DisplayName("setFailure")
    class SetFailureMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when failure is null")
        void should_ThrowNullPointerException_when_FailureIsNull() {
            var context = SimulationContext.create(TestSimulation.class);

            assertThatThrownBy(() -> context.setFailure(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should replace previous failure when new failure is set")
        void should_ReplacePreviousFailure_when_NewFailureIsSet() {
            var previous = new RuntimeException();
            var expected = new IllegalStateException();
            var context = SimulationContext.create(TestSimulation.class);

            context.setFailure(previous);
            context.setFailure(expected);

            assertThat(context.failure()).contains(expected);
        }
    }
}
