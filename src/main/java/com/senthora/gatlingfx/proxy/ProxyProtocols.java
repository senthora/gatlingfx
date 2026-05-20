package com.senthora.gatlingfx.proxy;

import com.senthora.gatlingfx.core.SimulationProtocol;
import com.senthora.gatlingfx.http.HttpBaseUrl;
import com.senthora.gatlingfx.http.HttpScheme;

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
     * @param target upstream target address
     */
    public static SimulationProtocol http(ProxyServer proxy, NetworkAddress target) {
        return create(HttpScheme.HTTP, proxy, target);
    }

    /**
     * Creates an HTTPS protocol configuration
     * routed through the provided proxy server.
     *
     * @param proxy proxy server definition
     * @param target upstream target address
     *
     * @return configured simulation protocol
     */
    public static SimulationProtocol https(ProxyServer proxy, NetworkAddress target) {
        return create(HttpScheme.HTTPS, proxy, target);
    }

    private static SimulationProtocol create(
            HttpScheme scheme,
            ProxyServer proxy,
            NetworkAddress target
    ) {
        return SimulationProtocol.create()
                .baseUrl(HttpBaseUrl.of(scheme, proxy.address()))
                .header(TARGET_HEADER, target.value());
    }
}
