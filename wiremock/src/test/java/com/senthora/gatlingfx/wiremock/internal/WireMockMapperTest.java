package com.senthora.gatlingfx.wiremock.internal;

import com.senthora.gatlingfx.support.TestHeaders;
import com.senthora.gatlingfx.wiremock.api.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.senthora.gatlingfx.wiremock.internal.TestWireMockResponses.responseOk;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WireMockMapperTest {

    @Nested
    @DisplayName("parseRequests")
    class ParseRequestsMethodTests {

        @Test
        @DisplayName("Should parse requests when JSON payload is valid")
        void should_ParseRequests_when_JsonPayloadIsValid() {
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
            var actual = WireMockMapper.parseRequests(json);
            var expected = new LoggedRequest("/test", TestHeaders.jsonContentTypeHeaders());

            assertThat(actual.requests()).containsExactly(expected);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when JSON payload is null")
        void should_ThrowNullPointerException_when_JsonPayloadIsNull() {
            assertThatThrownBy(() -> WireMockMapper.parseRequests(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw WireMockException when request parsing fails")
        void should_ThrowWireMockException_when_RequestParsingFails() {
            assertThatThrownBy(() -> WireMockMapper.parseRequests("invalid-json"))
                    .isInstanceOf(WireMockException.class);
        }
    }

    @Nested
    @DisplayName("toStub")
    class ToStubMethodTests {

        @Test
        @DisplayName("Should map exact URL stub when request uses exact URL")
        void should_MapExactUrlStub_when_RequestUsesExactUrl() {
            var headers = List.of(TestHeaders.jsonContentTypeHeader());
            var request = new StubRequest(
                    "GET",
                    new StubRequest.ExactUrl("/test"),
                    List.of()
            );
            var response = new StubResponse(
                    200,
                    TestWireMockResponses.STATUS_OK,
                    headers
            );
            var mapping = new StubMapping(request, response);
            var result = WireMockMapper.toStub(mapping);

            assertThat(result.request()).isEqualTo(
                    WireMockStubRequest.exact("GET", "/test")
            );
            assertThat(result.response()).isEqualTo(responseOk(headers));
        }

        @Test
        @DisplayName("Should map URL pattern stub when request uses URL pattern")
        void should_MapUrlPatternStub_when_RequestUsesUrlPattern() {
            var headers = List.of(TestHeaders.jsonContentTypeHeader());
            var request = new StubRequest(
                    "GET",
                    new StubRequest.UrlPattern("/test/.*"),
                    List.of()
            );
            var response = new StubResponse(
                    200,
                    TestWireMockResponses.STATUS_OK,
                    headers
            );
            var mapping = new StubMapping(request, response);
            var result = WireMockMapper.toStub(mapping);

            assertThat(result.request()).isEqualTo(
                    WireMockStubRequest.pattern("GET", "/test/.*")
            );
            assertThat(result.response()).isEqualTo(responseOk(headers));
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when mapping is null")
        void should_ThrowNullPointerException_when_MappingIsNull() {
            assertThatThrownBy(() -> WireMockMapper.toStub(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }
}
