package com.senthora.gatlingfx.wiremock.internal;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.http.api.SimpleHttpClient;
import com.senthora.gatlingfx.wiremock.api.StubMapping;
import com.senthora.gatlingfx.wiremock.api.StubRequest;
import com.senthora.gatlingfx.wiremock.api.StubResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.util.List;

import static com.senthora.gatlingfx.wiremock.internal.TestWireMockResponses.STATUS_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultWireMockBackendTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when HTTP client is null")
        void should_ThrowNullPointerException_when_HttpClientIsNull() {
            var request = new StubRequest(
                    "GET",
                    new StubRequest.ExactUrl("/test"),
                    List.of()
            );
            var response = new StubResponse(200, STATUS_OK, List.of());
            var mapping = new StubMapping(request, response);

            assertThatThrownBy(() -> new DefaultWireMockBackend(null, mapping))
                    .isInstanceOf(NullPointerException.class);

            assertThatThrownBy(() -> new DefaultWireMockBackend(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when default stub is null")
        void should_ThrowNullPointerException_when_DefaultStubIsNull() {
            var httpClient = Mockito.mock(SimpleHttpClient.class);

            assertThatThrownBy(() -> new DefaultWireMockBackend(httpClient, null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("stub")
    class StubMethodTests {

        @Test
        @DisplayName("Should return same client when mapping is added")
        void should_ReturnSameClient_when_MappingIsAdded() {
            var httpClient = Mockito.mock(SimpleHttpClient.class);
            var wireMockClient = new DefaultWireMockBackend(httpClient);

            var request = new StubRequest(
                    "GET",
                    new StubRequest.ExactUrl("/test"),
                    List.of()
            );
            var response = new StubResponse(200, STATUS_OK, List.of());
            var mapping = new StubMapping(request, response);

            assertThat(wireMockClient.stub(mapping)).isSameAs(wireMockClient);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when mapping is null")
        void should_ThrowNullPointerException_when_MappingIsNull() {
            var httpClient = Mockito.mock(SimpleHttpClient.class);
            var wireMockClient = new DefaultWireMockBackend(httpClient);

            assertThatThrownBy(() -> wireMockClient.stub(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("baseUrl")
    class BaseUrlMethodTests {

        @Test
        @DisplayName("Should return configured base URL when client is created")
        void should_ReturnConfiguredBaseUrl_when_ClientIsCreated() {
            var httpClient = Mockito.mock(SimpleHttpClient.class);
            Mockito.when(httpClient.baseUrl()).thenReturn(HttpBaseUrl.LOCALHOST);

            var wireMockClient = new DefaultWireMockBackend(httpClient);

            assertThat(wireMockClient.baseUrl()).isEqualTo(HttpBaseUrl.LOCALHOST);
        }
    }
}
