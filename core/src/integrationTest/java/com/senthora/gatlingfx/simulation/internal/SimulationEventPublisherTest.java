package com.senthora.gatlingfx.simulation.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulationEventPublisherTest extends AbstractSimulationEventTest {

    @Test
    @DisplayName("Should publish event when listener is registered")
    void should_PublishEvent_when_ListenerIsRegistered() {
        var listener = new TestSimulationEventListener();
        var event = new TestSimulationEvent();

        registerListener(listener);
        publishEvent(event);

        assertThat(listener.events()).containsExactly(event);
    }

    @Test
    @DisplayName("Should publish event to all listeners when multiple listeners are registered")
    void should_PublishEventToAllListeners_when_MultipleListenersAreRegistered() {
        var first = new TestSimulationEventListener();
        var second = new TestSimulationEventListener();
        var event = new TestSimulationEvent();

        registerListener(first);
        registerListener(second);

        publishEvent(event);

        assertThat(first.events()).containsExactly(event);
        assertThat(second.events()).containsExactly(event);
    }

    @Test
    @DisplayName("Should publish events in registration order when multiple listeners are registered")
    void should_PublishEventsInRegistrationOrder_when_MultipleListenersAreRegistered() {
        var invocations = new ArrayList<String>();

        registerListener(event -> invocations.add("first"));
        registerListener(event -> invocations.add("second"));

        publishEvent(new TestSimulationEvent());

        assertThat(invocations).containsExactly("first", "second");
    }

    @Test
    @DisplayName("Should propagate listener failure when listener throws exception")
    void should_PropagateListenerFailure_when_ListenerThrowsException() {
        var expected = new RuntimeException();

        registerListener(event -> {
            throw expected;
        });
        assertThatThrownBy(() -> publishEvent(new TestSimulationEvent()))
                .isSameAs(expected);
    }
}
