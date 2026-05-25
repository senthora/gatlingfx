package com.senthora.gatlingfx.http.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpBaseUrlTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when scheme is null")
        void should_ThrowNullPointerException_when_SchemeIsNull() {
            NetworkAddress address = NetworkAddress.localhost( 8080);

            assertThatThrownBy(() -> new HttpBaseUrl(null, address))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when address is null")
        void should_ThrowNullPointerException_when_AddressIsNull() {
            assertThatThrownBy(() -> new HttpBaseUrl(HttpScheme.HTTP, null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("of")
    class OfMethodTests {

        @Test
        @DisplayName("Should create base URL from provided scheme and address")
        void should_CreateBaseUrl_when_ValidArgumentsAreProvided() {
            var scheme = HttpScheme.HTTP;
            var address = NetworkAddress.localhost( 8080);

            var result = HttpBaseUrl.of(scheme, address);

            assertThat(result.scheme()).isEqualTo(scheme);
            assertThat(result.address()).isEqualTo(address);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when scheme is null")
        void should_ThrowNullPointerException_when_SchemeIsNull() {
            assertThatThrownBy(() -> HttpBaseUrl.of(null, "example.com"))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when host is null")
        void should_ThrowNullPointerException_when_HostIsNull() {
            assertThatThrownBy(() -> HttpBaseUrl.of(HttpScheme.HTTP, (NetworkAddress) null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should create base URL using HTTP default port")
        void should_CreateBaseUrlUsingHttpDefaultPort_when_SchemeIsHttp() {
            var expected = new HttpBaseUrl(
                    HttpScheme.HTTP,
                    NetworkAddress.of("example.com", 80)
            );
            var actual = HttpBaseUrl.of(HttpScheme.HTTP, "example.com");

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("Should create base URL using HTTPS default port")
        void should_CreateBaseUrlUsingHttpsDefaultPort_when_SchemeIsHttps() {
            var expected = new HttpBaseUrl(
                    HttpScheme.HTTPS,
                    NetworkAddress.of("example.com", 443)
            );
            var actual = HttpBaseUrl.of(HttpScheme.HTTPS, "example.com");

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("value")
    class ValueMethodTests {

        @Test
        @DisplayName("Should return URL in scheme host and port format")
        void should_ReturnUrlInSchemeHostAndPortFormat_when_Invoked() {
            var address = NetworkAddress.localhost( 8080);
            var baseUrl = HttpBaseUrl.of(HttpScheme.HTTP, address);

            var expected = "http://localhost:8080";
            assertThat(baseUrl.asString()).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("uri")
    class UriMethodTests {

        @Test
        @DisplayName("Should create URI using provided path")
        void should_CreateUriUsingProvidedPath_when_PathStartsWithSlash() {
            var address = NetworkAddress.localhost( 8080);
            var baseUrl = HttpBaseUrl.of(HttpScheme.HTTP, address);

            var expected = URI.create("http://localhost:8080/users");
            assertThat(baseUrl.uri("/users")).isEqualTo(expected);
        }

        @Test
        @DisplayName("Should normalize path when leading slash is missing")
        void should_NormalizePath_when_LeadingSlashIsMissing() {
            var address = NetworkAddress.localhost( 8080);
            var baseUrl = HttpBaseUrl.of(HttpScheme.HTTP, address);

            var expected = URI.create("http://localhost:8080/users");
            assertThat(baseUrl.uri("users")).isEqualTo(expected);
        }
    }
}
