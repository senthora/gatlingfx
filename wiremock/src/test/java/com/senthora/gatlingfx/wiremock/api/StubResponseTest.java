package com.senthora.gatlingfx.wiremock.api;

import com.senthora.gatlingfx.http.api.HttpHeader;

import com.senthora.gatlingfx.support.TestHeaders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StubResponseTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when headers are null")
        void should_ThrowNullPointerException_when_HeadersAreNull() {
            assertThatThrownBy(() -> new StubResponse(200, "", null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when status is below valid HTTP range")
        void should_ThrowIllegalArgumentException_when_StatusIsBelowValidHttpRange() {
            assertThatThrownBy(() -> new StubResponse(99, "", List.of()))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when status is above valid HTTP range")
        void should_ThrowIllegalArgumentException_when_StatusIsAboveValidHttpRange() {
            assertThatThrownBy(() -> new StubResponse(600, "", List.of()))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should throw NullPointerException when header is null")
        void should_ThrowNullPointerException_when_HeaderIsNull() {
            var headers = new ArrayList<HttpHeader>();
            headers.add(null);

            assertThatThrownBy(() -> new StubResponse(200, "", headers))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should store defensive copy when constructed")
        void should_StoreDefensiveCopy_when_Constructed() {
            var originalHeader = TestHeaders.jsonContentTypeHeader();
            var headers = new ArrayList<>(List.of(originalHeader));

            var stubResponse = new StubResponse(200, "", headers);

            headers.add(TestHeaders.authorizationHeader());

            assertThat(stubResponse.headers()).containsExactly(originalHeader);
        }

        @Test
        @DisplayName("Should return immutable headers when headers are accessed")
        void should_ReturnImmutableHeaders_when_HeadersAreAccessed() {
            var headers = List.of(TestHeaders.jsonContentTypeHeader());
            var stubResponse = new StubResponse(200, "", headers);

            var newHeader = TestHeaders.authorizationHeader();

            assertThatThrownBy(() -> stubResponse.headers().add(newHeader))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("withHeader")
    class WithHeaderMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when header is null")
        void should_ThrowNullPointerException_when_HeaderIsNull() {
            var stubResponse = new StubResponse(200, "", List.of());

            assertThatThrownBy(() -> stubResponse.withHeader(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should return response containing added header when header is added")
        void should_ReturnResponseContainingAddedHeader_when_HeaderIsAdded() {
            var header = TestHeaders.authorizationHeader();
            var stubResponse = new StubResponse(200, "", List.of());

            var actualResponse = stubResponse.withHeader(header);

            assertThat(actualResponse.headers()).containsExactly(header);
        }

        @Test
        @DisplayName("Should not mutate original response when header is added")
        void should_NotMutateOriginalResponse_when_HeaderIsAdded() {
            var header = TestHeaders.authorizationHeader();
            var stubResponse = new StubResponse(200, "", List.of());

            stubResponse.withHeader(header);

            assertThat(stubResponse.headers()).isEmpty();
        }
    }
}
