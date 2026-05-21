package com.senthora.gatlingfx.proxy;

import com.senthora.gatlingfx.http.api.NetworkAddress;

import java.util.Objects;

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
     *
     * @throws NullPointerException if {@code address} is null
     */
    public ProxyServer {
        Objects.requireNonNull(address, "address must not be null");
    }

    /**
     * Creates a new proxy server definition.
     *
     * @param address proxy server network address
     *
     * @throws NullPointerException if {@code address} is null
     */
    public static ProxyServer of(NetworkAddress address) {
        return new ProxyServer(address);
    }
}
