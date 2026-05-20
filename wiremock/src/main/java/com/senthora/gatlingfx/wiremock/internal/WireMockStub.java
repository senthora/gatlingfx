package com.senthora.gatlingfx.wiremock.internal;

import java.util.Objects;

/**
 * WireMock stub mapping payload.
 * <p>
 * This DTO represents a complete
 * WireMock stub definition containing both
 * the request matcher and configured response.
 */
record WireMockStub(WireMockStubRequest request, WireMockStubResponse response) {

    /**
     * Creates a WireMock stub mapping payload.
     *
     * @param request WireMock request matcher payload
     * @param response WireMock response payload
     *
     * @throws NullPointerException if {@code request} or {@code response} is null
     */
    public WireMockStub {
        Objects.requireNonNull(request, "request must not be null");
        Objects.requireNonNull(response, "response must not be null");
    }
}
