package io.github.meowpowpng.gatlingfx.proxy;

/**
 * Immutable reverse proxy server definition
 * used by proxy-oriented simulations.
 * <p>
 * A proxy server represents the HTTP entry point
 * through which simulation requests are routed
 * before being forwarded to upstream services.
 */
public record ProxyServer(NetworkAddress address) {

    /**
     * Creates a new proxy server definition.
     *
     * @param address proxy server network address
     */
    public static ProxyServer of(NetworkAddress address) {
        return new ProxyServer(address);
    }
}
