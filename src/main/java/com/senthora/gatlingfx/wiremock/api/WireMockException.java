package com.senthora.gatlingfx.wiremock.api;

/**
 * Base exception for WireMock-related failures.
 */
public abstract class WireMockException extends RuntimeException {

    /**
     * Creates a new WireMock exception.
     *
     * @param message exception detail message
     * @param cause underlying failure cause
     */
    protected WireMockException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new WireMock exception.
     *
     * @param message exception detail message
     */
    protected WireMockException(String message) {
        super(message);
    }
}
