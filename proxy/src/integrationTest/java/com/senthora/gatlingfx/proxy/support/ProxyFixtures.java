package com.senthora.gatlingfx.proxy.support;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.http.api.HttpScheme;
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
            ProxyServer.of(NetworkAddress.localhost(8080));

    /**
     * Default local HTTPS proxy server.
     */
    public static final ProxyServer HTTPS_PROXY =
            ProxyServer.of(NetworkAddress.localhost(8443));

    /**
     * Coffee upstream target used for
     * proxy routing verification.
     */
    public static final HttpBaseUrl COFFEE = HttpBaseUrl.of(
            HttpScheme.HTTP,
            NetworkAddress.of("coffee", 5678)
    );

    /**
     * Tea upstream target used for
     * proxy routing verification.
     */
    public static final HttpBaseUrl TEA = HttpBaseUrl.of(
            HttpScheme.HTTP,
            NetworkAddress.of("tea", 5678)
    );

    private ProxyFixtures() {}
}
