package com.senthora.gatlingfx.simulation.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulationEventPublisherTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when event is null")
    void should_ThrowNullPointerException_when_EventIsNull() {
        assertThatThrownBy(() -> SimulationEventPublisher.publish(null))
                .isInstanceOf(NullPointerException.class);
    }
}
