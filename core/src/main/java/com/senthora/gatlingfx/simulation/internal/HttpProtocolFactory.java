package com.senthora.gatlingfx.simulation.internal;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;

import io.gatling.javaapi.http.HttpProtocolBuilder;

/**
 * Factory for creating Gatling HTTP protocol builders.
 */
public interface HttpProtocolFactory {

    /**
     * Creates a protocol builder configured
     * with the provided base URL.
     *
     * @param baseUrl protocol base URL
     */
    HttpProtocolBuilder baseUrl(HttpBaseUrl baseUrl);
}
