package com.senthora.gatlingfx.runtime.core.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimulationRuntimeConfigTest {

    @Nested
    @DisplayName("withFailFast")
    class WithFailFastMethodTests {

        @Test
        @DisplayName("Returns same builder instance")
        void should_ReturnSameBuilderInstance() {
            var builder = SimulationRuntimeConfig.create();

            Assertions.assertThat(builder.withFailFast(true)).isSameAs(builder);
        }

        @Test
        @DisplayName("Enables fail-fast mode when value is true")
        void should_EnableFailFast_when_ValueIsTrue() {
            var config = SimulationRuntimeConfig.create()
                    .withFailFast(true)
                    .build();

            Assertions.assertThat(config.failFast()).isTrue();
        }

        @Test
        @DisplayName("Disables fail-fast mode when value is false")
        void should_DisableFailFast_when_ValueIsFalse() {
            var config = SimulationRuntimeConfig.create()
                    .withFailFast(false)
                    .build();

            Assertions.assertThat(config.failFast()).isFalse();
        }
    }

    @Nested
    @DisplayName("build")
    class BuildMethodTests {

        @Test
        @DisplayName("Disables fail-fast mode by default")
        void should_DisableFailFastByDefault() {
            var config = SimulationRuntimeConfig.create().build();

            Assertions.assertThat(config.failFast()).isFalse();
        }
    }
}
