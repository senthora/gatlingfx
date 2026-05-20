package io.github.meowpowpng.gatlingfx.http;

/**
 * Common HTTP request headers.
 */
public enum RequestHeader {
    /**
     * Standard HTTP {@code Host} header.
     */
    HOST("Host"),

    /**
     * Standard HTTP {@code Connection} header.
     */
    CONNECTION("Connection"),

    /**
     * Proxy forwarding header containing the original client IP chain.
     */
    X_FORWARDED_FOR("X-Forwarded-For"),

    /**
     * Proxy forwarding header containing the original request host.
     */
    X_FORWARDED_HOST("X-Forwarded-Host"),

    /**
     * Proxy forwarding header containing the original request protocol.
     */
    X_FORWARDED_PROTO("X-Forwarded-Proto"),

    /**
     * Proxy forwarding header containing the direct client IP address.
     */
    X_REAL_IP("X-Real-IP"),

    /**
     * Standard HTTP {@code Content-Type} header.
     */
    CONTENT_TYPE("Content-Type");

    private final String value;

    RequestHeader(String value) {
        this.value = value;
    }

    /**
     * Returns the raw HTTP header name.
     */
    public String headerName() {
        return value;
    }
}
