package io.github.meowpowpng.gatlingfx.wiremock.api;

import io.github.meowpowpng.gatlingfx.http.HttpHeader;
import io.github.meowpowpng.gatlingfx.http.HttpMethod;
import io.github.meowpowpng.gatlingfx.support.TestHeaders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StubRequestTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when method is null")
        void should_ThrowNullPointerException_when_MethodIsNull() {
            var url = new StubRequest.ExactUrl("/requests");

            assertThatThrownBy(() -> new StubRequest(null, url, List.of()))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when URL matcher is null")
        void should_ThrowNullPointerException_when_UrlMatcherIsNull() {
            assertThatThrownBy(() -> new StubRequest(HttpMethod.GET, null, List.of()))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when headers are null")
        void should_ThrowNullPointerException_when_HeadersAreNull() {
            var url = new StubRequest.ExactUrl("/requests");

            assertThatThrownBy(() -> new StubRequest(HttpMethod.GET, url, null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw NullPointerException when header is null")
        void should_ThrowNullPointerException_when_HeaderIsNull() {
            var url = new StubRequest.ExactUrl("/requests");
            var headers = new ArrayList<HttpHeader>();
            headers.add(null);

            assertThatThrownBy(() -> new StubRequest(HttpMethod.GET, url, headers))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should store defensive copy when constructed")
        void should_StoreDefensiveCopy_when_Constructed() {
            var originalHeader = TestHeaders.authorizationHeader();
            var headers = new ArrayList<>(List.of(originalHeader));
            var url = new StubRequest.ExactUrl("/requests");

            var stubRequest = new StubRequest(HttpMethod.GET, url, headers);

            headers.add(TestHeaders.jsonContentTypeHeader());

            assertThat(stubRequest.headers()).containsExactly(originalHeader);
        }

        @Test
        @DisplayName("Should return immutable headers when headers are accessed")
        void should_ReturnImmutableHeaders_when_HeadersAreAccessed() {
            var url = new StubRequest.ExactUrl("/requests");
            var headers = List.of(TestHeaders.authorizationHeader());
            var stubRequest = new StubRequest(HttpMethod.GET, url, headers);

            assertThatThrownBy(() -> stubRequest.headers()
                    .add(TestHeaders.jsonContentTypeHeader()))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("any")
    class AnyMethodTests {

        @Test
        @DisplayName("Should return matcher for any method and URL")
        void should_ReturnMatcherForAnyMethodAndUrl_when_AnyIsCalled() {
            var stubRequest = StubRequest.any();

            assertThat(stubRequest.method()).isEqualTo(HttpMethod.ANY);
            assertThat(stubRequest.url()).isEqualTo(new StubRequest.UrlPattern(".*"));
        }
    }

    @Nested
    @DisplayName("request")
    class RequestMethodTests {

        @Test
        @DisplayName("Should return matcher for exact URL when request is called")
        void should_ReturnMatcherForExactUrl_when_RequestIsCalled() {
            var path = "/requests";
            var stubRequest = StubRequest.request(HttpMethod.GET, path);

            assertThat(stubRequest.method()).isEqualTo(HttpMethod.GET);
            assertThat(stubRequest.url()).isEqualTo(new StubRequest.ExactUrl(path));
            assertThat(stubRequest.headers()).isEmpty();
        }
    }

    @Nested
    @DisplayName("requestMatching")
    class RequestMatchingMethodTests {

        @Test
        @DisplayName("Should return matcher for URL pattern when request is called")
        void should_ReturnMatcherForUrlPattern_when_RequestMatchingIsCalled() {
            var pattern = "/requests/.*";
            var stubRequest = StubRequest.requestMatching(HttpMethod.GET, pattern);

            assertThat(stubRequest.method()).isEqualTo(HttpMethod.GET);
            assertThat(stubRequest.url()).isEqualTo(new StubRequest.UrlPattern(pattern));
            assertThat(stubRequest.headers()).isEmpty();
        }
    }

    @Nested
    @DisplayName("withHeader")
    class WithHeaderMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when header is null")
        void should_ThrowNullPointerException_when_HeaderIsNull() {
            var stubRequest = StubRequest.request(HttpMethod.GET, "/requests");

            assertThatThrownBy(() -> stubRequest.withHeader(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should return request containing added header when header is added")
        void should_ReturnRequestContainingAddedHeader_when_HeaderIsAdded() {
            var header = TestHeaders.authorizationHeader();
            var stubRequest = StubRequest.request(HttpMethod.GET, "/requests");
            var actualRequest = stubRequest.withHeader(header);

            assertThat(actualRequest.headers()).containsExactly(header);
        }

        @Test
        @DisplayName("Should not mutate original request when header is added")
        void should_NotMutateOriginalRequest_when_HeaderIsAdded() {
            var header = TestHeaders.authorizationHeader();
            var stubRequest = StubRequest.request(HttpMethod.GET, "/requests");

            stubRequest.withHeader(header);

            assertThat(stubRequest.headers()).isEmpty();
        }
    }

    @Nested
    @DisplayName("willReturn")
    class WillReturnMethodTests {

        @Test
        @DisplayName("Should create stub mapping with configured response when method is called")
        void should_CreateStubMappingWithConfiguredResponse_when_WillReturnIsCalled() {
            var stubRequest = StubRequest.request(HttpMethod.GET, "/requests");

            var actualMapping = stubRequest.willReturn(200);
            var expectedResponse = new StubResponse(200, "", List.of());

            assertThat(actualMapping.response()).isEqualTo(expectedResponse);
        }
    }

    @Nested
    @DisplayName("willReturnJson")
    class WillReturnJsonMethodTests {

        @Test
        @DisplayName("Should create JSON response with JSON content type header")
        void should_CreateJsonResponse_when_WillReturnJsonIsCalled() {
            var stubRequest = StubRequest.request(HttpMethod.GET, "/requests");
            var jsonBody = "{\"status\":\"ok\"}";

            var actualMapping = stubRequest.willReturnJson(200, jsonBody);
            var expectedHeaders = List.of(TestHeaders.jsonContentTypeHeader());
            var expectedResponse = new StubResponse(200, jsonBody, expectedHeaders);

            assertThat(actualMapping.response()).isEqualTo(expectedResponse);
        }
    }

    @Nested
    @DisplayName("willReturnText")
    class WillReturnTextMethodTests {

        @Test
        @DisplayName("Should create plain text response with text content type header")
        void should_CreatePlainTextResponse_when_WillReturnTextIsCalled() {
            var stubRequest = StubRequest.request(HttpMethod.GET, "/requests");
            var textBody = "ok";

            var actualMapping = stubRequest.willReturnText(200, textBody);
            var expectedHeaders = List.of(TestHeaders.plainTextContentTypeHeader());
            var expectedResponse = new StubResponse(200, textBody, expectedHeaders);

            assertThat(actualMapping.response()).isEqualTo(expectedResponse);
        }
    }

    @Nested
    @DisplayName("ExactUrl")
    class ExactUrlTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when value is null")
        void should_ThrowNullPointerException_when_ValueIsNull() {
            assertThatThrownBy(() -> new StubRequest.ExactUrl(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when value is blank")
        void should_ThrowIllegalArgumentException_when_ValueIsBlank() {
            assertThatThrownBy(() -> new StubRequest.ExactUrl(" "))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("UrlPattern")
    class UrlPatternTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when value is null")
        void should_ThrowNullPointerException_when_ValueIsNull() {
            assertThatThrownBy(() -> new StubRequest.UrlPattern(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when value is blank")
        void should_ThrowIllegalArgumentException_when_ValueIsBlank() {
            assertThatThrownBy(() -> new StubRequest.UrlPattern(" "))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
