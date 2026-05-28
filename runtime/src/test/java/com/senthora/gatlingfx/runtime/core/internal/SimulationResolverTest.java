package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.support.TestSimulation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.senthora.gatlingfx.runtime.core.internal.SimulationResolver.isSupported;
import static com.senthora.gatlingfx.runtime.core.internal.SimulationResolver.resolve;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulationResolverTest {

    @Nested
    @DisplayName("isSupported")
    class IsSupportedMethodTests {

        @Test
        @DisplayName("Should return true when class extends BaseSimulation")
        void should_ReturnTrue_when_ClassExtendsBaseSimulation() {
            assertThat(isSupported(TestSimulation.class)).isTrue();
        }

        @Test
        @DisplayName("Should return false when class does not extend BaseSimulation")
        void should_ReturnFalse_when_ClassDoesNotExtendBaseSimulation() {
            assertThat(isSupported(String.class)).isFalse();
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when class is null")
        void should_ThrowNullPointerException_when_IsSupportedClassIsNull() {
            assertThatThrownBy(() -> isSupported(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("resolve")
    class ResolveMethodTests {

        @Test
        @DisplayName("Should return resolved simulation class when class extends BaseSimulation")
        void should_ReturnResolvedSimulationClass_when_ClassExtendsBaseSimulation() {
            assertThat(resolve(TestSimulation.class)).isEqualTo(TestSimulation.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when class is not supported")
        void should_ThrowIllegalArgumentException_when_ClassIsNotSupported() {
            assertThatThrownBy(() -> resolve(String.class))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when class is null")
        void should_ThrowNullPointerException_when_ResolveClassIsNull() {
            assertThatThrownBy(() -> resolve(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }
}
