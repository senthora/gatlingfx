package com.senthora.gatlingfx.support;

import com.senthora.gatlingfx.http.api.HttpHeader;

import java.util.Map;

public final class TestHeaders {

    private TestHeaders() {}

    public static HttpHeader authorizationHeader() {
        return HttpHeader.of("Authorization", "Bearer token");
    }

    public static Map<String, String> authorizationHeaders() {
        return Map.of("Authorization", "Bearer token");
    }

    public static HttpHeader jsonContentTypeHeader() {
        return HttpHeader.of("Content-Type", "application/json");
    }

    public static Map<String, String> jsonContentTypeHeaders() {
        return Map.of("Content-Type", "application/json");
    }

    public static HttpHeader plainTextContentTypeHeader() {
        return HttpHeader.of("Content-Type", "text/plain");
    }
}
