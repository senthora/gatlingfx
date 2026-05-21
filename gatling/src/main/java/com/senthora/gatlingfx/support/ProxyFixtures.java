package com.senthora.gatlingfx.support;

import com.senthora.gatlingfx.http.api.HttpHost;
import com.senthora.gatlingfx.http.api.NetworkAddress;
import com.senthora.gatlingfx.proxy.ProxyServer;

/**
 * Shared proxy-related fixtures used by
 * integration simulations and test support code.
 */
public final class ProxyFixtures {

    /**
     * Default local HTTP proxy server.
     */
    public static final ProxyServer HTTP_PROXY =
            ProxyServer.of(new NetworkAddress(HttpHost.LOCALHOST, 8080));

    /**
     * Default local HTTPS proxy server.
     */
    public static final ProxyServer HTTPS_PROXY =
            ProxyServer.of(new NetworkAddress(HttpHost.LOCALHOST, 8443));
    /**
     * Coffee upstream target used for
     * proxy routing verification.
     */
    public static final NetworkAddress COFFEE = NetworkAddress.of("coffee", 5678);

    /**
     * Tea upstream target used for
     * proxy routing verification.
     */
    public static final NetworkAddress TEA = NetworkAddress.of("tea", 5678);

    private ProxyFixtures() {}
}
