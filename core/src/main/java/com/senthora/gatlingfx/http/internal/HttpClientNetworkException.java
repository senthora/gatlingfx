package com.senthora.gatlingfx.http.internal;

import com.senthora.gatlingfx.http.api.HttpClientException;
import com.senthora.gatlingfx.http.api.HttpMethod;

/**
 * Exception thrown when HTTP client communication fails.
 */
public final class HttpClientNetworkException extends HttpClientException {

    /**
     * Creates a new HTTP client network exception.
     *
     * @param message exception detail message
     * @param cause underlying failure cause
     */
    public HttpClientNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception for failed HTTP requests.
     *
     * @param method failed HTTP request method
     * @param cause underlying failure cause
     */
    public static HttpClientNetworkException requestFailed(
            HttpMethod method,
            Throwable cause
    ) {
        var message = "HTTP request failed (method=%s)";
        return new HttpClientNetworkException(
                message.formatted(method.name()),
                cause
        );
    }

    /**
     * Creates an exception for interrupted HTTP requests.
     *
     * @param method interrupted HTTP request method
     * @param cause underlying interruption cause
     */
    public static HttpClientNetworkException requestInterrupted(
            HttpMethod method,
            Throwable cause
    ) {
        var message = "HTTP request interrupted (method=%s)";
        return new HttpClientNetworkException(
                message.formatted(method.name()),
                cause
        );
    }
}
