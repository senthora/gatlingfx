package com.senthora.gatlingfx.wiremock.internal;

import com.senthora.gatlingfx.wiremock.api.LoggedRequests;
import com.senthora.gatlingfx.wiremock.api.StubMapping;
import com.senthora.gatlingfx.wiremock.api.StubRequest;
import com.senthora.gatlingfx.wiremock.api.WireMockException;

/**
 * Maps public WireMock models to internal WireMock payloads.
 */
final class WireMockMapper {

    private WireMockMapper() {}

    /**
     * Parses logged requests from a WireMock JSON payload.
     *
     * @param json serialized JSON payload
     *
     * @return parsed logged requests
     * @throws NullPointerException if {@code json} is null
     * @throws WireMockException if request parsing fails
     */
    static LoggedRequests parseRequests(String json) {
        var requests = WireMockCodec.deserialize(json);

        return new LoggedRequests(requests.requests().stream()
                .map(WireMockLoggedRequestEntry::request)
                .toList()
        );
    }

    /**
     * Maps a public stub mapping to a WireMock stub payload.
     *
     * @param mapping stub mapping
     *
     * @return mapped WireMock stub payload
     */
    static WireMockStub toStub(StubMapping mapping) {
        var request = mapping.request();
        var response = mapping.response();

        var stubRequest = switch (request.url()) {
            case StubRequest.ExactUrl exact -> WireMockStubRequest.exact(
                    request.method(),
                    exact.value()
            );
            case StubRequest.UrlPattern pattern -> WireMockStubRequest.pattern(
                    request.method(),
                    pattern.value()
            );
        };
        var stubResponse = WireMockStubResponse.of(
                response.status(),
                response.body(),
                response.headers()
        );
        return new WireMockStub(stubRequest, stubResponse);
    }
}
