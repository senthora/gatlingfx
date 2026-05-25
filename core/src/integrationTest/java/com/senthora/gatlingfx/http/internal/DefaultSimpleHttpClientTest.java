package com.senthora.gatlingfx.http.internal;

import com.senthora.gatlingfx.http.api.*;
import com.senthora.gatlingfx.support.MockWebServerTest;

import okhttp3.mockwebserver.MockResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultSimpleHttpClientTest extends MockWebServerTest {

    @Nested
    @DisplayName("get")
    class GetTests {

        @Test
        @DisplayName("Should execute GET request successfully when response is returned")
        void should_ExecuteGetRequestSuccessfully_when_ResponseIsReturned() throws Exception {
            enqueueOkResponse();

            var client = SimpleHttpClient.create(baseUrl);

            assertOkResponse(client.get("/users"));

            var request = server.takeRequest();

            assertThat(request.getMethod()).isEqualTo("GET");
            assertThat(request.getPath()).isEqualTo("/users");
        }
    }

    @Nested
    @DisplayName("post")
    class PostTests {

        @Test
        @DisplayName("Should execute POST request successfully when response is returned")
        void should_ExecutePostRequestSuccessfully_when_ResponseIsReturned() throws Exception {
            enqueueOkResponse();

            var client = SimpleHttpClient.create(baseUrl);

            assertOkResponse(client.post("/users"));

            var request = server.takeRequest();

            assertThat(request.getMethod()).isEqualTo("POST");
            assertThat(request.getPath()).isEqualTo("/users");
            assertThat(request.getBody().readUtf8()).isEmpty();
            assertThat(request.getHeader("Content-Type")).isNull();
        }

        @Test
        @DisplayName("Should execute POST request with body successfully when response is returned")
        void should_ExecutePostRequestWithBodySuccessfully_when_ResponseIsReturned() throws Exception {
            enqueueOkResponse();

            var client = SimpleHttpClient.create(baseUrl);

            var body = "{\"name\":\"alice\"}";
            assertOkResponse(client.post("/users", body));

            var request = server.takeRequest();

            assertThat(request.getMethod()).isEqualTo("POST");
            assertThat(request.getPath()).isEqualTo("/users");
            assertThat(request.getHeader("Content-Type"))
                    .isEqualTo("application/json");

            assertThat(request.getBody().readUtf8()).isEqualTo(body);
        }

        @Test
        @DisplayName("Should throw HttpClientNetworkException when request execution fails")
        void should_ThrowHttpClientNetworkException_when_RequestExecutionFails() {
            var address = new NetworkAddress("localhost", 9999);
            var baseUrl = HttpBaseUrl.of(HttpScheme.HTTP, address);

            var client = SimpleHttpClient.create(baseUrl);

            assertThatThrownBy(() -> client.get("/users"))
                    .isInstanceOf(HttpClientNetworkException.class);
        }
    }

    private static void enqueueOkResponse() {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("ok"));
    }

    private static void assertOkResponse(HttpResponse<String> response) {
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("ok");
    }
}
