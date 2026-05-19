package io.github.meowpowpng.gatlingfx.proxy;

import io.github.meowpowpng.gatlingfx.core.SimulationProtocol;

/**
 * Factory for creating protocol
 * configurations routed through a proxy server.
 */
public final class ProxyProtocols {

    private static final String TARGET_HEADER = "X-Proxy-Target";

    private ProxyProtocols() {}

    private enum Protocol {
        HTTP("http"),
        HTTPS("https");

        private final String scheme;
        Protocol(String scheme) {
            this.scheme = scheme;
        }
    }

    /**
     * Creates an HTTP protocol configuration
     * routed through the provided proxy server.
     *
     * @param proxy proxy server definition
     * @param target upstream target address
     */
    public static SimulationProtocol http(ProxyServer proxy, NetworkAddress target) {
        return create(Protocol.HTTP, proxy, target);
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
        return create(Protocol.HTTPS, proxy, target);
    }

    private static SimulationProtocol create(
            Protocol protocol,
            ProxyServer proxy,
            NetworkAddress target
    ) {
        return SimulationProtocol.create()
                .baseUrl(protocol.scheme + "://" + proxy.address().value())
                .header(TARGET_HEADER, target.value());
    }
}
