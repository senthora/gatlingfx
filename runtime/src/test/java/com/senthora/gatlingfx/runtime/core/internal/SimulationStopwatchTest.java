package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.support.TestClock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulationStopwatchTest {

    @Nested
    @DisplayName("start")
    class StartMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when clock is null")
        void should_ThrowNullPointerException_when_ClockIsNull() {
            assertThatThrownBy(() -> SimulationStopwatch.start(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should return started stopwatch when clock is valid")
        void should_ReturnStartedStopwatch_when_ClockIsValid() {
            assertThat(SimulationStopwatch.start(Clock.systemUTC())).isNotNull();
        }

        @Test
        @DisplayName("Should return started stopwatch when start is invoked")
        void should_ReturnStartedStopwatch_when_StartIsInvoked() {
            assertThat(SimulationStopwatch.start()).isNotNull();
        }
    }

    @Nested
    @DisplayName("elapsed")
    class ElapsedMethodTests {

        @Test
        @DisplayName("Should return zero duration when clock has not advanced")
        void should_ReturnZeroDuration_when_ClockHasNotAdvanced() {
            assertThat(SimulationStopwatch.start(TestClock.create()).elapsed())
                    .isEqualTo(Duration.ZERO);
        }

        @Test
        @DisplayName("Should return elapsed duration when clock advances")
        void should_ReturnElapsedDuration_when_ClockAdvances() {
            var clock = TestClock.create();
            var stopwatch = SimulationStopwatch.start(clock);

            clock.advance(Duration.ofSeconds(5));

            assertThat(stopwatch.elapsed()).isEqualTo(Duration.ofSeconds(5));
        }
    }
}
