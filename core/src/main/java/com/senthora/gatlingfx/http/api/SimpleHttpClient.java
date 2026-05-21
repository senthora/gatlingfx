package com.senthora.gatlingfx.http.api;

import java.net.http.HttpResponse;

/**
 * Lightweight synchronous HTTP client
 * for simple infrastructure communication.
 */
public interface SimpleHttpClient {

    /**
     * Sends an HTTP GET request to the provided path.
     *
     * @param path target request path
     */
    HttpResponse<String> get(String path);

    /**
     * Sends an HTTP POST request without a request body.
     *
     * @param path target request path
     */
    HttpResponse<String> post(String path);

    /**
     * Sends an HTTP POST request with a request body.
     *
     * @param path target request path
     * @param body request body
     */
    HttpResponse<String> post(String path, String body);

    /**
     * Returns the configured base URL.
     */
    HttpBaseUrl baseUrl();
}
