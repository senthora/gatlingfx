package com.senthora.gatlingfx.wiremock.internal;

import com.senthora.gatlingfx.wiremock.api.WireMockException;

/**
 * Exception thrown when WireMock
 * payload serialization or deserialization fails.
 */
final class WireMockCodecException extends WireMockException {

    /**
     * Creates a new WireMock codec exception.
     *
     * @param message exception detail message
     * @param cause underlying failure cause
     */
    WireMockCodecException(String message, Throwable cause) {
        super(message, cause);
    }
}
