package com.senthora.gatlingfx.wiremock.internal;

import com.senthora.gatlingfx.http.HttpHeader;
import com.senthora.gatlingfx.support.TestHeaders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.senthora.gatlingfx.wiremock.internal.TestWireMockResponses.responseOk;
import static org.assertj.core.api.Assertions.*;

class WireMockStubResponseTest {

    private static final String STATUS_OK = "{\"status\":\"ok\"}";

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @DisplayName("Should create response when response parameters are provided")
        void should_CreateResponse_when_ResponseParametersAreProvided() {
            var headers = TestHeaders.jsonContentTypeHeaders();
            var result = responseOk(headers);

            assertThat(result.status()).isEqualTo(200);
            assertThat(result.body()).isEqualTo(STATUS_OK);
            assertThat(result.headers()).isEqualTo(headers);
        }

        @Test
        @DisplayName("Should create immutable headers when response is created")
        void should_CreateImmutableHeaders_when_ResponseIsCreated() {
            var headers = new HashMap<>(TestHeaders.jsonContentTypeHeaders());
            var result = responseOk(headers);

            headers.put("Authorization", "Bearer token");

            assertThat(result.headers()).containsExactlyEntriesOf(
                    TestHeaders.jsonContentTypeHeaders()
            );
            assertThatThrownBy(() -> result.headers().put("Authorization", "Bearer token"))
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when status is invalid")
        void should_ThrowIllegalArgumentException_when_StatusIsInvalid() {
            var thrown = catchThrowable(() -> new WireMockStubResponse(
                    99,
                    STATUS_OK,
                    TestHeaders.jsonContentTypeHeaders()
            ));
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when body is null")
        void should_ThrowNullPointerException_when_BodyIsNull() {
            var thrown = catchThrowable(() -> new WireMockStubResponse(
                    200,
                    null,
                    TestHeaders.jsonContentTypeHeaders()
            ));
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw NullPointerException when headers are null")
        void should_ThrowNullPointerException_when_HeadersAreNull() {
            var thrown = catchThrowable(() -> responseOk((Map<String, String>) null));
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw NullPointerException when header name is null")
        void should_ThrowNullPointerException_when_HeaderNameIsNull() {
            var headers = new HashMap<String, String>();
            headers.put(null, "application/json");

            var thrown = catchThrowable(() -> responseOk(headers));
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw NullPointerException when header value is null")
        void should_ThrowNullPointerException_when_HeaderValueIsNull() {
            var headers = new HashMap<String, String>();
            headers.put("Content-Type", null);

            var thrown = catchThrowable(() -> responseOk(headers));
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("of")
    class OfMethodTests {

        @Test
        @DisplayName("Should create response from HTTP headers when HTTP headers are provided")
        void should_CreateResponseFromHttpHeaders_when_HttpHeadersAreProvided() {
            var result = responseOk(List.of(TestHeaders.jsonContentTypeHeader()));

            assertThat(result.status()).isEqualTo(200);
            assertThat(result.body()).isEqualTo(STATUS_OK);
            assertThat(result.headers()).isEqualTo(TestHeaders.jsonContentTypeHeaders());
        }

        @Test
        @DisplayName("Should throw IllegalStateException when header names are duplicated")
        void should_ThrowIllegalStateException_when_HeaderNamesAreDuplicated() {
            var headers = List.of(
                    TestHeaders.jsonContentTypeHeader(),
                    TestHeaders.plainTextContentTypeHeader()
            );
            var thrown = catchThrowable(() -> responseOk(headers));
            assertThat(thrown).isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("Should throw NullPointerException when HTTP headers are null")
        void should_ThrowNullPointerException_when_HttpHeadersAreNull() {
            var thrown = catchThrowable(() -> responseOk((List<HttpHeader>) null));
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }
    }
}
