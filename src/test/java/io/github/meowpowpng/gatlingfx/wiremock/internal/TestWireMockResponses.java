package io.github.meowpowpng.gatlingfx.wiremock.internal;

import io.github.meowpowpng.gatlingfx.http.HttpHeader;

import java.util.List;
import java.util.Map;

final class TestWireMockResponses {

    static final String STATUS_OK = "{\"status\":\"ok\"}";

    private TestWireMockResponses() {}

    static WireMockStubResponse responseOk(Map<String, String> headers) {
        return new WireMockStubResponse(200, STATUS_OK, headers);
    }

    static WireMockStubResponse responseOk(List<HttpHeader> headers) {
        return WireMockStubResponse.of(200, STATUS_OK, headers);
    }
}
