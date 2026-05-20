package io.github.meowpowpng.gatlingfx.wiremock.internal;

import io.github.meowpowpng.gatlingfx.http.HttpMethod;
import io.github.meowpowpng.gatlingfx.support.TestHeaders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WireMockStubTest {

    @Test
    @DisplayName("Should create stub when request and response are provided")
    void should_CreateStub_when_RequestAndResponseAreProvided() {
        var request = WireMockStubRequest.exact(HttpMethod.GET, "/test");
        var response = new WireMockStubResponse(
                200,
                "{\"status\":\"ok\"}",
                TestHeaders.jsonContentTypeHeaders()
        );
        var result = new WireMockStub(request, response);

        assertThat(result.request()).isSameAs(request);
        assertThat(result.response()).isSameAs(response);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when request is null")
    void should_ThrowNullPointerException_when_RequestIsNull() {
        var response = new WireMockStubResponse(
                200,
                "{\"status\":\"ok\"}",
                TestHeaders.jsonContentTypeHeaders()
        );
        assertThatThrownBy(() -> new WireMockStub(null, response))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when response is null")
    void should_ThrowNullPointerException_when_ResponseIsNull() {
        var request = WireMockStubRequest.exact(HttpMethod.GET, "/test");

        assertThatThrownBy(() -> new WireMockStub(request, null))
                .isInstanceOf(NullPointerException.class);
    }
}
