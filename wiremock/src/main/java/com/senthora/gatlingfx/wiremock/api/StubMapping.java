package com.senthora.gatlingfx.wiremock.api;

import java.util.Objects;

/**
 * Defines a complete WireMock stub mapping.
 */
public record StubMapping(StubRequest request, StubResponse response) {

    /**
     * Creates a new stub mapping.
     *
     * @param request stub request definition
     * @param response stub response definition
     *
     * @throws NullPointerException if {@code request} or {@code response} is null
     */
    public StubMapping {
        Objects.requireNonNull(request, "request must not be null");
        Objects.requireNonNull(response, "response must not be null");
    }
}
