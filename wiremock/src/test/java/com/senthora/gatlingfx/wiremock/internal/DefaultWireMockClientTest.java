package com.senthora.gatlingfx.wiremock.internal;

import com.senthora.gatlingfx.http.HttpBaseUrl;
import com.senthora.gatlingfx.http.HttpHost;
import com.senthora.gatlingfx.http.HttpMethod;
import com.senthora.gatlingfx.http.HttpScheme;
import com.senthora.gatlingfx.wiremock.api.StubMapping;
import com.senthora.gatlingfx.wiremock.api.StubRequest;
import com.senthora.gatlingfx.wiremock.api.StubResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.senthora.gatlingfx.wiremock.internal.TestWireMockResponses.STATUS_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultWireMockClientTest {

    private static final HttpBaseUrl LOCALHOST_URL =
            HttpBaseUrl.of(HttpScheme.HTTPS, HttpHost.LOCALHOST);

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when base URL is null")
        void should_ThrowNullPointerException_when_BaseUrlIsNull() {
            var request = new StubRequest(
                    HttpMethod.GET,
                    new StubRequest.ExactUrl("/test"),
                    List.of()
            );
            var response = new StubResponse(200, STATUS_OK, List.of());
            var mapping = new StubMapping(request, response);

            assertThatThrownBy(() -> new DefaultWireMockClient(null, mapping))
                    .isInstanceOf(NullPointerException.class);

            assertThatThrownBy(() -> new DefaultWireMockClient(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when default stub is null")
        void should_ThrowNullPointerException_when_DefaultStubIsNull() {
            assertThatThrownBy(() -> new DefaultWireMockClient(LOCALHOST_URL, null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("stub")
    class StubMethodTests {

        @Test
        @DisplayName("Should return same client when mapping is added")
        void should_ReturnSameClient_when_MappingIsAdded() {
            var client = new DefaultWireMockClient(LOCALHOST_URL);

            var request = new StubRequest(
                    HttpMethod.GET,
                    new StubRequest.ExactUrl("/test"),
                    List.of()
            );
            var response = new StubResponse(200, STATUS_OK, List.of());
            var mapping = new StubMapping(request, response);

            assertThat(client.stub(mapping)).isSameAs(client);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when mapping is null")
        void should_ThrowNullPointerException_when_MappingIsNull() {
            var client = new DefaultWireMockClient(LOCALHOST_URL);

            assertThatThrownBy(() -> client.stub(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("baseUrl")
    class BaseUrlMethodTests {

        @Test
        @DisplayName("Should return configured base URL when client is created")
        void should_ReturnConfiguredBaseUrl_when_ClientIsCreated() {
            var client = new DefaultWireMockClient(LOCALHOST_URL);

            assertThat(client.baseUrl()).isEqualTo(LOCALHOST_URL);
        }
    }
}
