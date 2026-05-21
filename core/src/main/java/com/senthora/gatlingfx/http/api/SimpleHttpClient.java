package com.senthora.gatlingfx.http.api;

import com.senthora.gatlingfx.http.internal.DefaultSimpleHttpClient;

import java.net.http.HttpResponse;

/**
 * Lightweight synchronous HTTP client
 * for simple infrastructure communication.
 */
public interface SimpleHttpClient {

    /**
     * Creates a new HTTP client instance.
     *
     * @param baseUrl target base URL
     *
     * @throws NullPointerException if {@code baseUrl} is null
     */
    static SimpleHttpClient create(HttpBaseUrl baseUrl) {
        return new DefaultSimpleHttpClient(baseUrl);
    }

    /**
     * Sends an HTTP GET request to the provided path.
     *
     * @param path target request path
     *
     * @throws NullPointerException if {@code path} is null
     * @throws HttpClientException if request execution fails
     */
    HttpResponse<String> get(String path);

    /**
     * Sends an HTTP POST request without a request body.
     *
     * @param path target request path
     *
     * @throws NullPointerException if {@code path} is null
     * @throws HttpClientException if request execution fails
     */
    HttpResponse<String> post(String path);

    /**
     * Sends an HTTP POST request with a request body.
     *
     * @param path target request path
     * @param body request body
     *
     * @throws NullPointerException if {@code path} or {@code body} is null
     * @throws HttpClientException if request execution fails
     */
    HttpResponse<String> post(String path, String body);

    /**
     * Returns the configured base URL.
     */
    HttpBaseUrl baseUrl();
}
