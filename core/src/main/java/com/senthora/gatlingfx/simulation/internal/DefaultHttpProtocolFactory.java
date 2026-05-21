package com.senthora.gatlingfx.simulation.internal;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * Default implementation of {@link HttpProtocolFactory}.
 */
public final class DefaultHttpProtocolFactory implements HttpProtocolFactory {

    @Override
    public HttpProtocolBuilder baseUrl(HttpBaseUrl baseUrl) {
        return http.baseUrl(baseUrl.value());
    }
}
