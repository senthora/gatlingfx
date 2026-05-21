package com.senthora.gatlingfx.http.api;

import java.util.Objects;

/**
 * Represents an immutable network address.
 * <p>
 * This value object is used to represent
 * routable TCP endpoints such as proxy servers,
 * upstream targets, and backend services.
 */
public record NetworkAddress(HttpHost host, int port) {

    /**
     * Creates a new network address.
     *
     * @param host network host
     * @param port network port
     *
     * @throws NullPointerException if {@code host} is null
     * @throws IllegalArgumentException if {@code port} is not positive
     */
    public NetworkAddress {
        Objects.requireNonNull(host, "host must not be null");
        if (port <= 0) {
            throw new IllegalArgumentException("port must be greater than zero");
        }
    }

    /**
     * Creates a new network address.
     *
     * @param host network host
     * @param port network port
     *
     * @throws NullPointerException if {@code host} is null
     * @throws IllegalArgumentException if {@code port} is not positive
     */
    public static NetworkAddress of(String host, int port) {
        return new NetworkAddress(HttpHost.of(host), port);
    }

    /**
     * Returns the address in
     * {@code host:port} format.
     *
     * @return formatted network address
     */
    public String value() {
        return host.value() + ':' + port;
    }
}
