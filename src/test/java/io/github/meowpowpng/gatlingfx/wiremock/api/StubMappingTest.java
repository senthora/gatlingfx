package io.github.meowpowpng.gatlingfx.wiremock.api;

import io.github.meowpowpng.gatlingfx.http.HttpMethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StubMappingTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when request is null")
    void should_ThrowNullPointerException_when_RequestIsNull() {
        var response = new StubResponse(200, "", List.of());

        assertThatThrownBy(() -> new StubMapping(null, response))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when response is null")
    void should_ThrowNullPointerException_when_ResponseIsNull() {
        var request = new StubRequest(
                HttpMethod.GET,
                new StubRequest.ExactUrl("/requests"),
                List.of()
        );
        assertThatThrownBy(() -> new StubMapping(request, null))
                .isInstanceOf(NullPointerException.class);
    }
}
