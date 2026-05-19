package io.github.meowpowpng.gatlingfx.core;

import io.gatling.javaapi.http.HttpProtocolBuilder;

/**
 * Factory for creating Gatling HTTP protocol builders.
 */
interface HttpProtocolFactory {

    /**
     * Creates a protocol builder configured
     * with the provided base URL.
     *
     * @param baseUrl protocol base URL
     */
    HttpProtocolBuilder baseUrl(String baseUrl);
}
