package com.senthora.gatlingfx.runtime.core.application;

import com.senthora.gatlingfx.runtime.core.api.RuntimeLogLevel;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class LoggingContextTest {

    @Test
    @DisplayName("Should configure provided log level when context created")
    void should_ConfigureProvidedLogLevel_when_ContextCreated() {
        var logger = getLogger("com.senthora.gatlingfx");

        try (var ignored = LoggingContext.configure(RuntimeLogLevel.OFF)) {
            assertThat(logger.getLevel()).isEqualTo(Level.OFF);
        }
    }

    @Test
    @DisplayName("Should restore previous log level when context closed")
    void should_RestorePreviousLogLevel_when_ContextClosed() {
        var logger = getLogger("com.senthora.gatlingfx");

        var previousLevel = logger.getLevel();

        var context = LoggingContext.configure(RuntimeLogLevel.OFF);

        context.close();

        assertThat(logger.getLevel()).isEqualTo(previousLevel);
    }

    @Test
    @DisplayName("Should restore previous log level when nested contexts closed")
    void should_RestorePreviousLogLevel_when_NestedContextsClosed() {
        var logger = getLogger("com.senthora.gatlingfx");

        var originalLevel = logger.getLevel();

        var outer = LoggingContext.configure(RuntimeLogLevel.ERROR);
        var inner = LoggingContext.configure(RuntimeLogLevel.OFF);

        inner.close();

        assertThat(logger.getLevel()).isEqualTo(Level.ERROR);

        outer.close();

        assertThat(logger.getLevel()).isEqualTo(originalLevel);
    }

    @Test
    @DisplayName("Should throw NullPointerException when log level is null")
    void should_ThrowNullPointerException_when_LogLevelIsNull() {
        var thrown = catchThrowable(() -> {
            //noinspection EmptyTryBlock
            try (var ignored = LoggingContext.configure(null)) {}
        });
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @SuppressWarnings("SameParameterValue")
    private static Logger getLogger(String namespace) {
        return (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(namespace);
    }
}
