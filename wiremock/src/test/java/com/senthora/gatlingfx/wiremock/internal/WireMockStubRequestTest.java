package com.senthora.gatlingfx.wiremock.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class WireMockStubRequestTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when method is null")
        void should_ThrowNullPointerException_when_MethodIsNull() {
            var thrown = catchThrowable(() -> new WireMockStubRequest(
                    null,
                    "/test",
                    null
            ));
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when method is blank")
        void should_ThrowIllegalArgumentException_when_MethodIsBlank() {
            var thrown = catchThrowable(() -> new WireMockStubRequest(
                    " ",
                    "/test",
                    null
            ));
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when URL parameters are both null")
        void should_ThrowIllegalArgumentException_when_UrlParametersAreBothNull() {
            var thrown = catchThrowable(() -> new WireMockStubRequest(
                    "GET",
                    null,
                    null
            ));
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when URL parameters are both provided")
        void should_ThrowIllegalArgumentException_when_UrlParametersAreBothProvided() {
            var thrown = catchThrowable(() -> new WireMockStubRequest(
                    "GET",
                    "/test",
                    "/test/.*"
            ));
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("exact")
    class ExactMethodTests {

        @Test
        @DisplayName("Should create exact request when exact URL is provided")
        void should_CreateExactRequest_when_ExactUrlIsProvided() {
            var result = WireMockStubRequest.exact("GET", "/test");

            assertThat(result.method()).isEqualTo("GET");
            assertThat(result.url()).isEqualTo("/test");
            assertThat(result.urlPattern()).isNull();
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when method is null")
        void should_ThrowNullPointerException_when_MethodIsNull() {
            var thrown = catchThrowable(() -> WireMockStubRequest.exact(
                    null,
                    "/test"
            ));
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when method is blank")
        void should_ThrowIllegalArgumentException_when_MethodIsBlank() {
            var thrown = catchThrowable(() -> WireMockStubRequest.exact(
                    " ",
                    "/test"
            ));
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw IllegalArgumentException when exact URL is null")
        void should_ThrowIllegalArgumentException_when_ExactUrlIsNull() {
            var thrown = catchThrowable(() -> WireMockStubRequest.exact(
                    "GET",
                    null
            ));
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("pattern")
    class PatternMethodTests {

        @Test
        @DisplayName("Should create pattern request when URL pattern is provided")
        void should_CreatePatternRequest_when_UrlPatternIsProvided() {
            var result = WireMockStubRequest.pattern("GET", "/test/.*");

            assertThat(result.method()).isEqualTo("GET");
            assertThat(result.url()).isNull();
            assertThat(result.urlPattern()).isEqualTo("/test/.*");
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw IllegalArgumentException when URL pattern is null")
        void should_ThrowIllegalArgumentException_when_UrlPatternIsNull() {
            var thrown = catchThrowable(() -> WireMockStubRequest.pattern(
                    "GET",
                    null
            ));
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when method is null")
        void should_ThrowNullPointerException_when_MethodIsNull() {
            var thrown = catchThrowable(() -> WireMockStubRequest.pattern(
                    null,
                    "/test/.*"
            ));
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when method is blank")
        void should_ThrowIllegalArgumentException_when_MethodIsBlank() {
            var thrown = catchThrowable(() -> WireMockStubRequest.pattern(
                    " ",
                    "/test/.*"
            ));
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
