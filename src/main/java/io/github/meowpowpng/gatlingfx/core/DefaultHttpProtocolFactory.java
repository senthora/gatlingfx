package io.github.meowpowpng.gatlingfx.core;

import io.github.meowpowpng.gatlingfx.http.HttpBaseUrl;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * Default implementation of {@link HttpProtocolFactory}.
 */
final class DefaultHttpProtocolFactory implements HttpProtocolFactory {

    @Override
    public HttpProtocolBuilder baseUrl(HttpBaseUrl baseUrl) {
        return http.baseUrl(baseUrl.value());
    }
}
