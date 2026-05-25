package com.senthora.gatlingfx.wiremock.api;

import com.senthora.gatlingfx.support.TestHeaders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoggedRequestTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when url is null")
        void should_ThrowNullPointerException_when_UrlIsNull() {
            assertThatThrownBy(() -> new LoggedRequest(null, Map.of()))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when headers are null")
        void should_ThrowNullPointerException_when_HeadersAreNull() {
            assertThatThrownBy(() -> new LoggedRequest("/requests", null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw NullPointerException when header name is null")
        void should_ThrowNullPointerException_when_HeaderNameIsNull() {
            var headers = new HashMap<String, String>();
            headers.put(null, "application/json");

            assertThatThrownBy(() -> new LoggedRequest("/requests", headers))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw NullPointerException when header value is null")
        void should_ThrowNullPointerException_when_HeaderValueIsNull() {
            var headers = new HashMap<String, String>();
            headers.put("Content-Type", null);

            assertThatThrownBy(() -> new LoggedRequest("/requests", headers))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should store immutable defensive copy of headers")
        void should_StoreImmutableDefensiveCopy_when_Constructed() {
            var originalHeader = Map.entry("Content-Type", "application/json");
            var headers = new HashMap<String, String>();
            headers.put(originalHeader.getKey(), originalHeader.getValue());

            var loggedRequest = new LoggedRequest("/requests", headers);

            headers.put("Authorization", "Bearer token");

            assertThat(loggedRequest.headers()).containsExactly(originalHeader);
            assertThatThrownBy(() -> loggedRequest.headers().put("Accept", "application/json"))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("header")
    class HeaderMethodTests {

        @Test
        @DisplayName("Should return matching header value when header exists")
        void should_ReturnMatchingHeaderValue_when_HeaderExists() {
            var headers = TestHeaders.authorizationHeaders();
            var loggedRequest = new LoggedRequest("/requests", headers);

            var requestHeader = loggedRequest.header("Authorization");
            assertThat(requestHeader).contains("Bearer token");
        }

        @Test
        @DisplayName("Should match header case insensitively when header exists")
        void should_MatchHeaderCaseInsensitively_when_HeaderExists() {
            var headers = TestHeaders.authorizationHeaders();
            var loggedRequest = new LoggedRequest("/requests", headers);

            var requestHeader = loggedRequest.header("Authorization");
            assertThat(requestHeader).contains("Bearer token");
        }

        @Test
        @DisplayName("Should return empty optional when header does not exist")
        void should_ReturnEmptyOptional_when_HeaderDoesNotExist() {
            var headers = Map.of("Content-Type", "application/json");
            var loggedRequest = new LoggedRequest("/requests", headers);

            assertThat(loggedRequest.header("Authorization")).isEmpty();
        }
    }
}
