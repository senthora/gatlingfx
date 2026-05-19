package io.github.meowpowpng.gatlingfx.proxy;

import io.github.meowpowpng.gatlingfx.core.SimulationProtocol;

/**
 * Factory for creating protocol
 * configurations routed through a proxy server.
 */
public final class ProxyProtocols {

    private static final String TARGET_HEADER = "X-Proxy-Target";

    private ProxyProtocols() {}

    private enum Scheme {
        HTTP("http"),
        HTTPS("https");

        private final String value;
        Scheme(String value) {
            this.value = value;
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
        return create(Scheme.HTTP, proxy, target);
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
        return create(Scheme.HTTPS, proxy, target);
    }

    private static SimulationProtocol create(
            Scheme scheme,
            ProxyServer proxy,
            NetworkAddress target
    ) {
        return SimulationProtocol.create()
                .baseUrl(scheme.value + "://" + proxy.address().value())
                .header(TARGET_HEADER, target.value());
    }
}
