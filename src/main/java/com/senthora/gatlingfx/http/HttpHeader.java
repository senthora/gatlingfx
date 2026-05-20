package com.senthora.gatlingfx.http;

import java.util.Objects;

/**
 * Immutable HTTP header.
 */
public record HttpHeader(String name, String value) {

    /**
     * Creates a new HTTP header.
     *
     * @param name header name
     * @param value header value
     *
     * @throws NullPointerException if {@code name} or {@code value} is null
     * @throws IllegalArgumentException if {@code name} is blank
     */
    public HttpHeader {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(value, "value must not be null");

        if (name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
    }

    /**
     * Creates a header from a predefined request header.
     *
     * @param header request header
     * @param value header value
     *
     * @throws NullPointerException if {@code header} or {@code value} is null
     */
    public static HttpHeader of(RequestHeader header, String value) {
        Objects.requireNonNull(header, "header must not be null");
        return new HttpHeader(header.headerName(), value);
    }
}
