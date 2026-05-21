package com.senthora.gatlingfx.wiremock.internal;

import com.senthora.gatlingfx.http.api.HttpMethod;
import com.senthora.gatlingfx.wiremock.api.WireMockException;

import java.net.http.HttpResponse;

/**
 * Exception thrown when communication with WireMock fails.
 */
final class WireMockNetworkException extends WireMockException {

    /**
     * Creates a new WireMock network exception.
     *
     * @param message exception detail message
     * @param cause underlying failure cause
     */
    WireMockNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new WireMock network exception.
     *
     * @param message exception detail message
     */
    WireMockNetworkException(String message) {
        super(message);
    }

    /**
     * Creates a network exception for failed WireMock requests.
     *
     * @param method failed HTTP request method
     * @param cause underlying failure cause
     */
    static WireMockNetworkException requestFailed(HttpMethod method, Throwable cause) {
        var message = "WireMock request failed (method=" + method.name() + ')';
        return new WireMockNetworkException(message, cause);
    }

    /**
     * Creates a network exception for interrupted WireMock requests.
     *
     * @param method interrupted HTTP request method
     * @param cause underlying interruption cause
     */
    static WireMockNetworkException requestInterrupted(HttpMethod method, Throwable cause) {
        var message = "WireMock request interrupted (method=" + method.name() + ')';
        return new WireMockNetworkException(message, cause);
    }

    /**
     * Creates a network exception for rejected WireMock requests.
     *
     * @param method rejected HTTP request method
     * @param response rejected HTTP response
     */
    static WireMockNetworkException requestRejected(HttpMethod method, HttpResponse<String> response) {
        var message = "WireMock request rejected (method=%s, code=%d, body=%s)";
        return new WireMockNetworkException(message.formatted(
                method.name(),
                response.statusCode(),
                response.body()
        ));
    }
}
