package io.github.meowpowpng.gatlingfx.proxy;

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
