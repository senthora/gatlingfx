package com.senthora.gatlingfx.wiremock.api;

import com.senthora.gatlingfx.http.api.HttpHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Defines a WireMock stub response.
 */
public record StubResponse(int status, String body, List<HttpHeader> headers) {

    /**
     * Creates a new stub response.
     *
     * @param status HTTP response status
     * @param body response body
     * @param headers response headers
     *
     * @throws NullPointerException if {@code headers} or any header is null
     * @throws IllegalArgumentException if {@code status} is not a valid HTTP status code
     */
    public StubResponse {
        Objects.requireNonNull(headers, "headers must not be null");
        if (status < 100 || status > 599) {
            throw new IllegalArgumentException("status must be a valid HTTP status code");
        }
        for (HttpHeader header : headers) {
            Objects.requireNonNull(header, "header must not be null");
        }
        headers = List.copyOf(headers);
    }

    /**
     * Adds a response header.
     *
     * @param header response header
     *
     * @return stub response
     *
     * @throws NullPointerException if {@code header} is null
     */
    public StubResponse withHeader(HttpHeader header) {
        Objects.requireNonNull(header, "header must not be null");

        var headers = new ArrayList<>(this.headers);
        headers.add(header);

        return new StubResponse(status, body, headers);
    }
}
