package io.github.meowpowpng.gatlingfx.proxy;

import java.util.Objects;

/**
 * Represents an immutable network address.
 * <p>
 * This value object is used to represent
 * routable TCP endpoints such as proxy servers,
 * upstream targets, and backend services.
 */
public record NetworkAddress(String host, int port) {

    /**
     * Creates a new network address.
     *
     * @param host network host
     * @param port network port
     *
     * @throws NullPointerException if {@code host} is null
     * @throws IllegalArgumentException if {@code host} is blank
     * @throws IllegalArgumentException if {@code port} is not positive
     */
    public NetworkAddress {
        Objects.requireNonNull(host, "Host must not be null");
        if (host.isBlank()) {
            throw new IllegalArgumentException("Host must not be blank");
        }
        if (port <= 0) {
            throw new IllegalArgumentException("Port must be greater than zero");
        }
    }

    /**
     * Creates a new network address.
     *
     * @param host network host
     * @param port network port
     *
     * @throws NullPointerException if {@code host} is null
     * @throws IllegalArgumentException if {@code host} is blank
     * @throws IllegalArgumentException if {@code port} is not positive
     */
    public static NetworkAddress of(String host, int port) {
        return new NetworkAddress(host, port);
    }

    /**
     * Returns the address in
     * {@code host:port} format.
     *
     * @return formatted network address
     */
    public String value() {
        return host + ':' + port;
    }
}
