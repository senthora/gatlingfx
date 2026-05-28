package com.senthora.gatlingfx.proxy;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.http.api.HttpScheme;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;

/**
 * Factory for creating protocol
 * configurations routed through a proxy server.
 */
public final class ProxyProtocols {

    private static final String TARGET_HEADER = "X-Proxy-Target";

    private ProxyProtocols() {}

    /**
     * Creates an HTTP protocol configuration
     * routed through the provided proxy server.
     *
     * @param proxy proxy server definition
     * @param upstream upstream target base URL
     */
    public static SimulationProtocol http(ProxyServer proxy, HttpBaseUrl upstream) {
        return create(HttpScheme.HTTP, proxy, upstream);
    }

    /**
     * Creates an HTTPS protocol configuration
     * routed through the provided proxy server.
     *
     * @param proxy proxy server definition
     * @param upstream upstream target base URL
     *
     * @return configured simulation protocol
     */
    public static SimulationProtocol https(ProxyServer proxy, HttpBaseUrl upstream) {
        return create(HttpScheme.HTTPS, proxy, upstream);
    }

    private static SimulationProtocol create(
            HttpScheme scheme,
            ProxyServer proxy,
            HttpBaseUrl upstream
    ) {
        return SimulationProtocol.create()
                .baseUrl(HttpBaseUrl.of(scheme, proxy.address()))
                .header(TARGET_HEADER, upstream.asString());
    }
}
