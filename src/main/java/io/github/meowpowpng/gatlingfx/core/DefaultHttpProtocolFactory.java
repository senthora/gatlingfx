package io.github.meowpowpng.gatlingfx.core;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * Default implementation of {@link HttpProtocolFactory}.
 */
final class DefaultHttpProtocolFactory implements HttpProtocolFactory {

    @Override
    public HttpProtocolBuilder baseUrl(String baseUrl) {
        return http.baseUrl(baseUrl);
    }
}
