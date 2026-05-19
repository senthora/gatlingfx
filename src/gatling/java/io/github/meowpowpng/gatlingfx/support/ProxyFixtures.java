package io.github.meowpowpng.gatlingfx.support;

import io.github.meowpowpng.gatlingfx.proxy.NetworkAddress;
import io.github.meowpowpng.gatlingfx.proxy.ProxyServer;

/**
 * Shared proxy-related fixtures used by
 * integration simulations and test support code.
 */
public final class ProxyFixtures {

    /**
     * Default local HTTP proxy server.
     */
    public static final ProxyServer HTTP_PROXY =
            ProxyServer.of(NetworkAddress.of("localhost", 8080));

    /**
     * Default local HTTPS proxy server.
     */
    public static final ProxyServer HTTPS_PROXY =
            ProxyServer.of(NetworkAddress.of("localhost", 8443));
    /**
     * Coffee upstream target used for
     * proxy routing verification.
     */
    @SuppressWarnings("HttpUrlsUsage")
    public static final NetworkAddress COFFEE = NetworkAddress.of(
            "http://coffee",
            5678
    );

    /**
     * Tea upstream target used for
     * proxy routing verification.
     */
    public static final NetworkAddress TEA = NetworkAddress.of(
            "http://tea",
            5678
    );

    private ProxyFixtures() {}
}
