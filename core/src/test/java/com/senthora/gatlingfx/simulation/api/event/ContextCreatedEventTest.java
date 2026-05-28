package com.senthora.gatlingfx.simulation.api.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContextCreatedEventTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when context is null")
    void should_ThrowNullPointerException_when_ContextIsNull() {
        assertThatThrownBy(() -> new ContextCreatedEvent(null))
                .isInstanceOf(NullPointerException.class);
    }
}
