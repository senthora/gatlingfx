package com.senthora.gatlingfx.http.api;

/**
 * Base exception for HTTP client failures.
 */
public abstract class HttpClientException extends RuntimeException {

    /**
     * Creates a new HTTP client exception.
     *
     * @param message exception detail message
     * @param cause underlying failure cause
     */
    protected HttpClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
