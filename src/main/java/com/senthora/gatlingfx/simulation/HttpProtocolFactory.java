package com.senthora.gatlingfx.simulation;

import com.senthora.gatlingfx.http.HttpBaseUrl;

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
    HttpProtocolBuilder baseUrl(HttpBaseUrl baseUrl);
}
