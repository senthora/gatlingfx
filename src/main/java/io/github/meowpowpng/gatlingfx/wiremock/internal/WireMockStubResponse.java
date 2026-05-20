package io.github.meowpowpng.gatlingfx.wiremock.internal;

import io.github.meowpowpng.gatlingfx.http.HttpHeader;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * WireMock stub response payload.
 * <p>
 * This internal DTO describes the response
 * WireMock should return for a matching request.
 */
@JsonInclude(NON_NULL)
record WireMockStubResponse(int status, String body, Map<String, String> headers) {

    /**
     * Creates a WireMock stub response payload.
     *
     * @param status HTTP response status
     * @param body response body
     * @param headers response headers
     *
     * @throws NullPointerException if {@code body},
     * {@code headers}, any header name, or value is null
     * @throws IllegalArgumentException if {@code status} is not a valid HTTP status code
     */
    public WireMockStubResponse {
        Objects.requireNonNull(body, "body must not be null");
        Objects.requireNonNull(headers, "headers must not be null");

        if (status < 100 || status > 599) {
            throw new IllegalArgumentException("status must be a valid HTTP status code");
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            Objects.requireNonNull(entry.getKey(), "header name must not be null");
            Objects.requireNonNull(entry.getValue(), "header value must not be null");
        }
        headers = Map.copyOf(headers);
    }

    /**
     * Creates a WireMock stub response
     * payload from HTTP headers.
     *
     * @param status HTTP response status
     * @param body response body
     * @param headers response headers
     *
     * @return stub response payload
     *
     * @throws NullPointerException if {@code body}, {@code headers}, or any header is null
     * @throws IllegalArgumentException if {@code status} is not a valid HTTP status code
     */
    public static WireMockStubResponse of(int status, String body, List<HttpHeader> headers) {
        Objects.requireNonNull(headers, "headers must not be null");

        var collector = Collectors.toUnmodifiableMap(HttpHeader::name, HttpHeader::value);
        var mappedHeaders = headers.stream().collect(collector);

        return new WireMockStubResponse(status, body, mappedHeaders);
    }
}
