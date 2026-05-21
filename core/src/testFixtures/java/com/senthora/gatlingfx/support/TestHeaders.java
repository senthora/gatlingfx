package com.senthora.gatlingfx.support;

import com.senthora.gatlingfx.http.api.HttpHeader;
import com.senthora.gatlingfx.http.api.RequestHeader;

import java.util.Map;

public final class TestHeaders {

    private TestHeaders() {}

    public static HttpHeader authorizationHeader() {
        return HttpHeader.of(RequestHeader.AUTHORIZATION, "Bearer token");
    }

    public static Map<String, String> authorizationHeaders() {
        return Map.of(RequestHeader.AUTHORIZATION.headerName(), "Bearer token");
    }

    public static HttpHeader jsonContentTypeHeader() {
        return HttpHeader.of(RequestHeader.CONTENT_TYPE, "application/json");
    }

    public static Map<String, String> jsonContentTypeHeaders() {
        return Map.of(RequestHeader.CONTENT_TYPE.headerName(), "application/json");
    }

    public static HttpHeader plainTextContentTypeHeader() {
        return HttpHeader.of(RequestHeader.CONTENT_TYPE, "text/plain");
    }

    public static Map<String, String> plainTextContentTypeHeaders() {
        return Map.of(RequestHeader.CONTENT_TYPE.headerName(), "text/plain");
    }
}
