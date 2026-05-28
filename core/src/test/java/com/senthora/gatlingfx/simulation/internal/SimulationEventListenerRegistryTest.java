package com.senthora.gatlingfx.simulation.internal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.senthora.gatlingfx.simulation.internal.SimulationTestSupport.*;

import static org.assertj.core.api.Assertions.*;

class SimulationEventListenerRegistryTest {

    @AfterEach
    void teardownSimulationEventListenerRegistryTest() {
        SimulationTestSupport.resetListenerRegistry();
    }

    @Nested
    @DisplayName("register")
    class RegisterMethodTests {

        @Test
        @DisplayName("Should throw NullPointerException when listener is null")
        void should_ThrowNullPointerException_when_ListenerIsNull() {
            assertThatThrownBy(() -> registerListener(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should register listener when listener is provided")
        void should_RegisterListener_when_ListenerIsProvided() {
            var listener = new TestSimulationEventListener();

            registerListener(listener);

            assertThat(registeredListeners()).contains(listener);
        }

        @Test
        @DisplayName("Should preserve registered listeners when additional listener is registered")
        void should_PreserveRegisteredListeners_when_AdditionalListenerIsRegistered() {
            var first = new TestSimulationEventListener();
            var second = new TestSimulationEventListener();

            registerListener(first);
            registerListener(second);

            assertThat(registeredListeners()).containsExactly(first, second);
        }
    }

    @Nested
    @DisplayName("listeners")
    class ListenersMethodTests {

        @Test
        @DisplayName("Should return registered listeners when listeners are registered")
        void should_ReturnRegisteredListeners_when_ListenersAreRegistered() {
            var expected = new TestSimulationEventListener();

            registerListener(expected);

            assertThat(registeredListeners()).containsExactly(expected);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should return immutable view when listeners are returned")
        void should_ReturnImmutableView_when_ListenersAreReturned() {
            var thrown = catchThrowable(() -> registeredListeners().add(
                    new TestSimulationEventListener()
            ));
            assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should return empty listeners when registry is empty")
        void should_ReturnEmptyListeners_when_RegistryIsEmpty() {
            assertThat(registeredListeners()).isEmpty();
        }

        @Test
        @DisplayName("Should reflect registry changes when listeners were already returned")
        void should_ReflectRegistryChanges_when_ListenersWereAlreadyReturned() {
            var expected = new TestSimulationEventListener();
            var listeners = registeredListeners();

            registerListener(expected);

            assertThat(listeners).containsExactly(expected);
        }
    }

    @Nested
    @DisplayName("clear")
    class ClearMethodTests {

        @Test
        @DisplayName("Should remove all registered listeners when registry is cleared")
        void should_RemoveAllRegisteredListeners_when_RegistryIsCleared() {
            registerListener(new TestSimulationEventListener());

            SimulationEventListenerRegistry.clear();

            assertThat(registeredListeners()).isEmpty();
        }
    }
}
