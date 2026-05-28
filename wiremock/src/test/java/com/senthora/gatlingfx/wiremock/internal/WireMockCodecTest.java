package com.senthora.gatlingfx.wiremock.internal;

import com.senthora.gatlingfx.support.TestHeaders;
import com.senthora.gatlingfx.wiremock.api.LoggedRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.senthora.gatlingfx.wiremock.internal.TestWireMockResponses.responseOk;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WireMockCodecTest {

    @Nested
    @DisplayName("serialize")
    class SerializeMethodTests {

        @Test
        @DisplayName("Should serialize stub when stub payload is valid")
        void should_SerializeStub_when_StubPayloadIsValid() throws Exception {
            var stub = new WireMockStub(
                    WireMockStubRequest.exact("GET", "/test"),
                    responseOk(TestHeaders.jsonContentTypeHeaders())
            );
            var result = WireMockCodec.serialize(stub);
            var mapper = new ObjectMapper();
            var json = mapper.readTree(result);

            var requestMethod = json.get("request").get("method").asText();
            var requestUrl = json.get("request").get("url").asText();
            var responseStatus = json.get("response").get("status").asInt();
            var responseHeaders = json.get("response").get("headers").get("Content-Type").asText();

            assertThat(requestMethod).isEqualTo("GET");
            assertThat(requestUrl).isEqualTo("/test");
            assertThat(responseStatus).isEqualTo(200);
            assertThat(responseHeaders).isEqualTo("application/json");
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when stub payload is null")
        void should_ThrowNullPointerException_when_StubPayloadIsNull() {
            assertThatThrownBy(() -> WireMockCodec.serialize(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("deserialize")
    class DeserializeMethodTests {

        @Test
        @DisplayName("Should deserialize logged requests when JSON payload is valid")
        void should_DeserializeLoggedRequests_when_JsonPayloadIsValid() {
            //@formatter:off
            var json = """
            {
              "requests": [
                {
                  "request": {
                    "url": "/test",
                    "headers": {
                      "Content-Type": "application/json"
                    }
                  }
                }
              ]
            }
            """;
            //@formatter:on
            var result = WireMockCodec.deserialize(json);
            var expected = new WireMockLoggedRequestEntry(
                    new LoggedRequest("/test", TestHeaders.jsonContentTypeHeaders())
            );
            assertThat(result.requests()).containsExactly(expected);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when JSON payload is null")
        void should_ThrowNullPointerException_when_JsonPayloadIsNull() {
            assertThatThrownBy(() -> WireMockCodec.deserialize(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw WireMockCodecException when deserialization fails")
        void should_ThrowWireMockCodecException_when_DeserializationFails() {
            assertThatThrownBy(() -> WireMockCodec.deserialize("invalid-json"))
                    .isInstanceOf(WireMockCodecException.class);
        }
    }
}
