package com.senthora.gatlingfx.runtime.core.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulationRuntimeConfigTest {

    @Nested
    @DisplayName("withFailFast")
    class WithFailFastMethodTests {

        @Test
        @DisplayName("Returns same builder instance")
        void should_ReturnSameBuilderInstance() {
            var builder = SimulationRuntimeConfig.create();

            assertThat(builder.withFailFast(true)).isSameAs(builder);
        }

        @Test
        @DisplayName("Enables fail-fast mode when value is true")
        void should_EnableFailFast_when_ValueIsTrue() {
            var config = SimulationRuntimeConfig.create()
                    .withFailFast(true)
                    .build();

            assertThat(config.failFast()).isTrue();
        }

        @Test
        @DisplayName("Disables fail-fast mode when value is false")
        void should_DisableFailFast_when_ValueIsFalse() {
            var config = SimulationRuntimeConfig.create()
                    .withFailFast(false)
                    .build();

            assertThat(config.failFast()).isFalse();
        }
    }

    @Nested
    @DisplayName("withLogLevel")
    class WithLogLevelMethodTests {

        @Test
        @DisplayName("Returns same builder instance")
        void should_ReturnSameBuilderInstance() {
            var builder = SimulationRuntimeConfig.create();

            assertThat(builder.withLogLevel(RuntimeLogLevel.INFO))
                    .isSameAs(builder);
        }

        @Test
        @DisplayName("Sets log level when level is provided")
        void should_SetLogLevel_when_LevelIsProvided() {
            var expected = RuntimeLogLevel.DEBUG;
            var config = SimulationRuntimeConfig.create()
                    .withLogLevel(expected)
                    .build();

            assertThat(config.logLevel()).isEqualTo(expected);
        }

        @Test
        @SuppressWarnings("WriteOnlyObject")
        @DisplayName("Throws NullPointerException when level is null")
        void should_ThrowNullPointerException_when_LevelIsNull() {
            assertThatThrownBy(() -> SimulationRuntimeConfig.create().withLogLevel(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("build")
    class BuildMethodTests {

        @Test
        @DisplayName("Defaults log level to info when log level is not configured")
        void should_DefaultLogLevelToInfo_when_LogLevelIsNotConfigured() {
            var config = SimulationRuntimeConfig.create().build();

            assertThat(config.logLevel()).isEqualTo(RuntimeLogLevel.INFO);
        }

        @Test
        @DisplayName("Disables fail-fast mode by default")
        void should_DisableFailFastByDefault() {
            var config = SimulationRuntimeConfig.create().build();

            assertThat(config.failFast()).isFalse();
        }
    }
}
