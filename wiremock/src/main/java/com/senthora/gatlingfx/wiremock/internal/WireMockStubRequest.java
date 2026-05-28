package com.senthora.gatlingfx.wiremock.internal;

import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * WireMock stub request matcher payload.
 * <p>
 * This DTO describes which incoming
 * requests a WireMock stub should match.
 */
record WireMockStubRequest(
        String method,
        @Nullable
        String url,
        @Nullable
        String urlPattern
) {
    /**
     * Creates a WireMock stub request payload.
     *
     * @throws NullPointerException if {@code method} is null
     * @throws IllegalArgumentException if {@code method} is blank or,
     * if neither or both {@code url} and {@code urlPattern} are provided
     */
    WireMockStubRequest {
        Objects.requireNonNull(method, "method must not be null");
        if (method.isBlank()) {
            throw new IllegalArgumentException("method must not be blank");
        }
        if ((url == null) == (urlPattern == null)) {
            throw new IllegalArgumentException("either url or urlPattern must be provided");
        }
    }

    /**
     * Creates a stub request payload
     * matching an exact URL.
     *
     * @param method HTTP request method
     * @param url exact request URL
     *
     * @return stub request payload
     * @throws NullPointerException if {@code method} is null
     * @throws IllegalArgumentException if {@code url} is null
     */
    static WireMockStubRequest exact(String method, String url) {
        return new WireMockStubRequest(method, url, null);
    }

    /**
     * Creates a stub request payload
     * matching a URL pattern.
     *
     * @param method HTTP request method
     * @param urlPattern request URL pattern
     *
     * @return stub request payload
     * @throws NullPointerException if {@code method} is null
     * @throws IllegalArgumentException if {@code urlPattern} is null
     */
    static WireMockStubRequest pattern(String method, String urlPattern) {
        return new WireMockStubRequest(method, null, urlPattern);
    }
}
